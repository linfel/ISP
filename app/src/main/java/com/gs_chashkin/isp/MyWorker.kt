package com.gs_chashkin.isp

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.gs_chashkin.isp.di.WorkerSomeClass
import com.gs_chashkin.isp.presentation.ConnectionChecker
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class MyWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val workerSomeClass: WorkerSomeClass): Worker(context, workerParams) {

    override fun doWork(): Result {
        for (attempt in 0..10){
            Log.d("MyWorker", "${workerSomeClass.doAThing()}Attempt to connect. Attempts: $attempt")
            Thread.sleep(1000)
            if(ConnectionChecker.isOnline()){
                return Result.success()
            }
        }
        return Result.failure()
    }

}