package com.pedro.solutions.dialysisnotes.ui.pdf_generator

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.net.Uri
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.pedro.solutions.dialysisnotes.R
import com.pedro.solutions.dialysisnotes.data.dialysis.Dialysis
import com.pedro.solutions.dialysisnotes.data.dialysis.DialysisDAO
import com.pedro.solutions.dialysisnotes.ui.Utils
import org.koin.java.KoinJavaComponent.inject
import kotlin.math.min

class GeneratePDFWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    val TAG = this.javaClass.simpleName
    override fun doWork(): Result {
        return try {

            val directory = Uri.parse(inputData.getString("file_path"))
            val patientName = inputData.getString("patient_name")
            val startInterval = inputData.getLong("start_interval", 0)
            val endInterval = inputData.getLong("end_interval", 0)
            if (startInterval == 0L || endInterval == 0L) return Result.failure()

            val dialysisDao: DialysisDAO by inject(DialysisDAO::class.java)

            val allDialysis = dialysisDao.getDialysisBetweenInterval(startInterval, endInterval)
            Log.d(TAG, "allDialysis=${allDialysis.size} startInterval=$startInterval endInterval=$endInterval")
            if(allDialysis.isEmpty()) return Result.failure()


            generatePdf(allDialysis, patientName, startInterval, endInterval, directory)
            Result.success()
        } catch (throwable: Throwable) {
            Log.d(TAG, "Error while trying to generate a PDF ${throwable.message}")
            Result.failure()
        }
    }

    private fun generatePdf(
        allDialysis: List<Dialysis>,
        patientName: String?,
        startInterval: Long,
        endInterval: Long,
        directory: Uri?
    ) {
        val linesPerPage = min(30, allDialysis.size)
        var numberOfPages = (allDialysis.size / linesPerPage)
        if (numberOfPages == 0) numberOfPages++
        Log.d(
            TAG, "generatePdf called numberOfPages=$numberOfPages linesPerPage=$linesPerPage"
        )

        var dialysisIdx = 0
        var yPos = 200f
        val document = PdfDocument()

        while (numberOfPages > 0) {
            val pageInfo = PageInfo.Builder(2480, 3508, 1).create()

            val page = document.startPage(pageInfo)
            val canvas = page.canvas

            canvas.drawText(getHeaderText(startInterval, endInterval), 500f, yPos, getHeaderPaint())
            yPos += 100

            canvas.drawText("Paciente: ${patientName ?: ""}", 100f, yPos, patientNamePaint())
            yPos += 100
            var tmpLinesPerPage = linesPerPage
            while (tmpLinesPerPage > 0) {
                canvas.drawText(
                    getDialysisString(allDialysis[dialysisIdx++]), 100f, yPos, patientNamePaint()
                )
                yPos += 50
                tmpLinesPerPage--
            }
            document.finishPage(page)

            numberOfPages--
        }

        try {
            Log.d(TAG, "Trying to save PDF at: $directory")
            applicationContext.contentResolver.openOutputStream(directory!!).use {
                document.writeTo(it)
            }
        } catch (e: Exception) {
            Log.d(TAG, "Error while trying to save a PDF")
            e.message?.let {
                Log.d(TAG, it)
            }
        } finally {
            document.close()
        }
    }

    private fun getDialysisString(dialysis: Dialysis): String {
        val createdAt = Utils.getDateAndTimeFromMillis(
            dialysis.createdAt,
            Utils.DATE_FORMAT_DEFAULT_ONLY_DATE,
            Utils.getDefaultLocale(applicationContext)
        )

        return "$createdAt ${applicationContext.getString(R.string.uf_inicial)} ${dialysis.initialUf}${
            applicationContext.getString(
                R.string.uf_final
            )
        } ${dialysis.finalUf} ${applicationContext.getString(R.string.observations)} ${dialysis.notes}"
    }

    private fun getHeaderText(startInterval: Long, endInterval: Long): String {
        val start = Utils.getDateAndTimeFromMillis(
            startInterval,
            Utils.DATE_FORMAT_DEFAULT_ONLY_DATE,
            Utils.getDefaultLocale(applicationContext)
        )
        val end = Utils.getDateAndTimeFromMillis(
            endInterval,
            Utils.DATE_FORMAT_DEFAULT_ONLY_DATE,
            Utils.getDefaultLocale(applicationContext)
        )
        return "Lista de Diálises de $start a $end"
    }

    private fun getHeaderPaint(): Paint {
        val text = Paint()
        text.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        text.textSize = 55f
        return text
    }

    private fun patientNamePaint(): Paint {
        val text = Paint()
        text.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        text.textSize = 30f

        return text
    }
}