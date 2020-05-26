package com.example.servicethreadexample.service

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.example.servicethreadexample.model.TaskResult
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ServiceWorker<T>(name: String) {

    companion object {

        private const val EXECUTE_IN_UI = 0
    }

    private val mName: String       = name
    private val mExecutor: Executor = Executors.newSingleThreadExecutor()
    private val mUIHandler: Handler = object : Handler(Looper.getMainLooper()) {

        override fun handleMessage(msg: Message) {

            when(msg.what) {

                EXECUTE_IN_UI -> {


                    val result: TaskResult<T> = msg.obj as TaskResult<T>
                    (result.task).onTaskComplete(result.result)
                }
            }
        }
    }

    fun addTask(task: Task<T>) {

        mExecutor.execute {

            val result = task.onExecuteTask()

            val msg: Message = mUIHandler.obtainMessage(EXECUTE_IN_UI)

            msg.obj = TaskResult(task, result)
            mUIHandler.sendMessage(msg)
        }
    }
}