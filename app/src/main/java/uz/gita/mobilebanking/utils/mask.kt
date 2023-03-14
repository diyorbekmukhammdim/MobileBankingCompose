package uz.gita.mobilebanking.utils

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

// Created by Jamshid Isoqov on 1/8/2023

const val mask = "90 123 45 67"


object PhoneMaskTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        Log.d("TTT", "filter: PhoneMaskTransformation 1")
        val trimmed =
            if (text.text.length >= 9) text.text.substring(0..8) else text.text

        val annotatedString = AnnotatedString.Builder().run {
            for (i in trimmed.indices) {
                Log.d("TTT", "filter: PhoneMaskTransformation 2")
                append(trimmed[i])
                if (i == 1 || i == 4 || i == 6) {
                    append(" ")
                }
            }
            Log.d("TTT", "filter: PhoneMaskTransformation 3")
            pushStyle(SpanStyle(color = Color.LightGray))
            append(mask.takeLast(mask.length - length))
            toAnnotatedString()
        }
        val phoneNumberOffsetTranslator: OffsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                Log.d("TTT", "originalToTransformed: PhoneMaskTransformation 4")
                if (offset <= 1) return offset
                if (offset <= 4) return offset + 1
                if (offset <= 6) return offset + 2
                if (offset <= 9) return offset + 3
                return 12
            }

            override fun transformedToOriginal(offset: Int): Int {
                Log.d("TTT", "transformedToOriginal: PhoneMaskTransformation 5")
                if (offset <= 1) return offset
                if (offset <= 4) return offset - 1
                if (offset <= 6) return offset - 2
                if (offset <= 9) return offset - 3
                return 9
            }
        }
        Log.d("TTT", "filter: PhoneMaskTransformation 6")

        return TransformedText(annotatedString, phoneNumberOffsetTranslator)
    }
}