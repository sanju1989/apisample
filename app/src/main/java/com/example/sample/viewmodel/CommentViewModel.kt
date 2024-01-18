package com.example.sample.viewmodel

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sample.BUFFER_SIZE
import com.example.sample.CommentResponse
import com.example.sample.IMAGE_DIRECTORY
import com.example.sample.repository.CommentRepository
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream


class CommentViewModel : BaseViewModel() {

    var commentResponse = MutableLiveData<CommentResponse>()

    fun postData(
        first_name: String,
        last_name:String,
        email: String,
        password:String,
        phone_no:String,
        device_token:String,
        device_type:String,
        countryCode:String,
        userfile: String,
    ) {
        isLoading.value = true
        CommentRepository.postData({
            commentResponse.value = it
            isLoading.value = false
        }, {
            apiError.value = it
            isLoading.value = false
        }, {
            onFailure.value = it
            isLoading.value = false
        }, first_name,last_name,email,password,phone_no,device_token,device_type,countryCode,userfile)
    }

    fun getFilePathFromURI(context: Context, contentUri: Uri): String? {
        //copy file and send new file path
        val fileName: String = getFileName(contentUri)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
            val wallpaperDirectory1: File = File(
                java.lang.String.valueOf(context.getExternalFilesDir(Environment.DIRECTORY_DCIM + IMAGE_DIRECTORY))
            )
            // have the object build the directory structure, if needed.
            if (!wallpaperDirectory1.exists()) {
                wallpaperDirectory1.mkdirs()
            }
            if (!TextUtils.isEmpty(fileName)) {
                val copyFile = File(wallpaperDirectory1.toString() + File.separator + fileName)
                // create folder if not exists
                copy(context, contentUri, copyFile)
                return copyFile.absolutePath
            }
        } else {
            val wallpaperDirectory2 = File(
                Environment.getExternalStorageDirectory()
                    .toString() + IMAGE_DIRECTORY
            )
            // have the object build the directory structure, if needed.
            if (!wallpaperDirectory2.exists()) {
                wallpaperDirectory2.mkdirs()
            }
            if (!TextUtils.isEmpty(fileName)) {
                val copyFile = File(wallpaperDirectory2.toString() + File.separator + fileName)
                // create folder if not exists
                copy(context, contentUri, copyFile)
                return copyFile.absolutePath
            }
        }
        return null
    }

    private fun getFileName(uri: Uri): String {
        var fileName: String = ""
        val path = uri.path
        val cut = path!!.lastIndexOf('/')
        if (cut != -1) {
            fileName = path.substring(cut + 1)
        }
        return fileName
    }

    private fun copy(context: Context, srcUri: Uri?, dstFile: File?) {
        try {
            val inputStream = context.contentResolver.openInputStream(srcUri!!) ?: return
            val outputStream: OutputStream = FileOutputStream(dstFile)
            copystream(inputStream, outputStream)
            inputStream.close()
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(Exception::class, IOException::class)
    fun copystream(input: InputStream?, output: OutputStream?): Int {
        val buffer = ByteArray(BUFFER_SIZE)
        val `in` = BufferedInputStream(input, BUFFER_SIZE)
        val out = BufferedOutputStream(output, BUFFER_SIZE)
        var count = 0
        var n = 0
        try {
            while (`in`.read(buffer, 0, BUFFER_SIZE).also {
                    n = it
                } != -1) {
                out.write(buffer, 0, n)
                count += n
            }
            out.flush()
        } finally {
            try {
                out.close()
            } catch (e: IOException) {
                Log.e(e.message, e.toString())
            }
            try {
                `in`.close()
            } catch (e: IOException) {
                Log.e(e.message, e.toString())
            }
        }
        return count
    }
}