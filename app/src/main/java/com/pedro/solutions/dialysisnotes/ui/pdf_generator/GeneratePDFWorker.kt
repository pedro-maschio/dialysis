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
import com.pedro.solutions.dialysisnotes.DialysisApplication
import com.pedro.solutions.dialysisnotes.data.Dialysis
import kotlin.math.min

class GeneratePDFWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    val TAG = this.javaClass.simpleName
    override fun doWork(): Result {
        return try {

            val directory = Uri.parse(inputData.getString("file_path"))
            val startInterval = inputData.getLong("start_interval", 0)
            val endInterval = inputData.getLong("end_interval", 0)
            if (startInterval == 0L || endInterval == 0L) return Result.failure()

            val dialysisDao = (applicationContext as DialysisApplication).database.dialysisDao()

            val allDialysis = dialysisDao.getDialysisBetweenInterval(startInterval, endInterval)

            generatePdf(allDialysis, directory)
            Result.success()
        } catch (throwable: Throwable) {
            Log.d(TAG, "Error while trying to generate a PDF")
            Result.failure()
        }
    }

    private fun generatePdf(allDialysis: List<Dialysis>, directory: Uri?) {
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

            val text = Paint()
            text.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            text.textSize = 15f

            var tmpLinesPerPage = linesPerPage
            while (tmpLinesPerPage > 0) {
                canvas.drawText(allDialysis[dialysisIdx++].initialUf.toString(), 200f, yPos, text)
                yPos += 50
                tmpLinesPerPage--
            }
            document.finishPage(page)

            numberOfPages--
        }

        try {
            applicationContext.contentResolver.openOutputStream(directory!!).use {
                document.writeTo(it)
            }
        } catch (e: Exception) {
            Log.d(TAG, "Error while trying to save a PDF")
            e.message?.let {
                Log.d(TAG, it)
            }
        }
        document.close()
    }
}