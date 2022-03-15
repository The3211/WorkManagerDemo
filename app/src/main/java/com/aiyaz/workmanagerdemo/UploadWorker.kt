package com.aiyaz.workmanagerdemo

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class UploadWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    companion object{
        const val KEY_WORKER = "key_worker"
    }

    override fun doWork(): Result {
        try {
            val count = inputData.getInt(MainActivity.dataKey,10)
            for (i in 0 until count) {
                Log.i("TestLog", "Uploading $i")
            }

            val time = SimpleDateFormat("dd/M/yyyy hh:mm:ss",Locale.getDefault())
            val currentDate = time.format(Date())

            val outputData : Data = Data.Builder()
                .putString(KEY_WORKER,currentDate)
                .build()

            return Result.success(outputData)
        }catch (e : Exception){
            return Result.failure()
        }
    }
}