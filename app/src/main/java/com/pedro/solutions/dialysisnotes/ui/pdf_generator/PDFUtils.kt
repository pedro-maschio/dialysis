package com.pedro.solutions.dialysisnotes.ui.pdf_generator

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.pedro.solutions.dialysisnotes.R
import com.pedro.solutions.dialysisnotes.data.Dialysis

class PDFUtils {
    companion object {
        private fun generatePDF(data: List<Dialysis>, patient: String, context: Context) {
            val pdfDocument = PdfDocument()

            val paint = Paint()
            val title = Paint()

            for(dialysisItem in data) {
                //
            }

            val pageInfo = PdfDocument.PageInfo.Builder(792, 1120, 1).create()

            val myPage = pdfDocument.startPage(pageInfo)

            val canvas = myPage.canvas

            title.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            title.textSize = 15F
            title.color = ContextCompat.getColor(context, R.color.black)
            canvas.drawText("This is just a test", 100f, 200f, title)

            pdfDocument.finishPage(myPage)
            
        }
    }
}