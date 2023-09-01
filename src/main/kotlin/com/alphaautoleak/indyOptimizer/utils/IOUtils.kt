package com.alphaautoleak.indyOptimizer.utils

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

class IOUtils {

    companion object {

        @Throws(IOException::class)
        fun toByteArray(inputStream: InputStream): ByteArray
        {
            val outputStream = ByteArrayOutputStream()

            val buffer = ByteArray(1024)
            var len: Int
            while (inputStream.read(buffer).also { len = it } != -1)
            {
                outputStream.write(buffer, 0, len)
            }
            return outputStream.toByteArray()
        }
    }


}