package com.aiyaz.workmanagerdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.work.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val dataKey = "intVal"
    }

    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        textView = findViewById(R.id.textView)
        button.setOnClickListener {
            setOneTimeWorkRequest()
        }
    }

    private fun setOneTimeWorkRequest() {
        val workManager = WorkManager.getInstance(applicationContext)

        val data: Data = Data.Builder()
            .putInt(dataKey, 60000)
            .build()

        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val uploadRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .setConstraints(constraints)
            .setInputData(data)
            .build()

        val uploadWorkerMoreRequest = OneTimeWorkRequest.Builder(UploadWorkerMore::class.java)
            .build()

        val uploadWorkMoreAgainRequest = OneTimeWorkRequest.Builder(UploadWorkMoreAgain::class.java)
            .build()


//        workManager.enqueue(uploadRequest)

        /**
         * chaining request belpw
         * below is sequential chaining
         */

       /* workManager.beginWith(uploadRequest)
            .then(uploadWorkerMoreRequest)
            .then(uploadWorkMoreAgainRequest)
            .enqueue()*/


        /**
         * this below is example of paraller with sequential chaining.
         */

        val parallelWorkReq = OneTimeWorkRequest.Builder(UploadWorkParallel::class.java)
            .build()

        val parallelWorks = mutableListOf<OneTimeWorkRequest>()
        parallelWorks.add(uploadRequest)
        parallelWorks.add(parallelWorkReq)

        workManager.beginWith(parallelWorkReq)
            .then(uploadWorkerMoreRequest)
            .then(uploadWorkMoreAgainRequest)
            .enqueue()

        workManager.getWorkInfoByIdLiveData(uploadRequest.id).observe(this, Observer {
            textView.text = it.state.name
            if(it.state.isFinished){
                val dataObj = it.outputData
                val str = dataObj.getString(UploadWorker.KEY_WORKER)
            }
        })
    }
}