package com.aiyaz.workmanagerdemo

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class UploadWorkMoreAgain(context: Context, params: WorkerParameters) : Worker(context, params) {


    override fun doWork(): Result {
        try {

            for (i in 0 until 300) {
                Log.i("TestLog", "Uploading $i")
            }
            return Result.success()
        }catch (e : Exception){
            return Result.failure()
        }
    }
}