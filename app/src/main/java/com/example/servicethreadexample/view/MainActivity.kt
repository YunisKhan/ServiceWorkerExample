package com.example.servicethreadexample.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.example.servicethreadexample.R
import com.example.servicethreadexample.service.ServiceWorker
import com.example.servicethreadexample.service.Task

import kotlinx.android.synthetic.main.content_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class MainActivity : AppCompatActivity() {

    private val mServiceWorker1: ServiceWorker<Bitmap> = ServiceWorker("service_worker_1")
    private val mServiceWorker2: ServiceWorker<Bitmap> = ServiceWorker("service_worker_2")

    companion object {

        private const val IMAGE_1   = ""
        private const val IMAGE_2   = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1.setOnClickListener {

            if (IMAGE_1 == "") {

                showToast("Image url is empty")
            } else {

                fetchImage1AndSet()
            }
        }

        button2.setOnClickListener {

            if (IMAGE_2 == "") {

                showToast("Image url is empty")
            } else {

                fetchImage2AndSet()
            }
        }
    }

    private fun fetchImage1AndSet() {

        mServiceWorker1.addTask(
            object : Task<Bitmap> {

                override fun onExecuteTask(): Bitmap {

                    return fetchBitmap(IMAGE_1)
                }

                override fun onTaskComplete(result: Bitmap) {

                    imageview_1.setImageBitmap(result)
                }
            }
        )
    }

    private fun fetchImage2AndSet() {

        mServiceWorker2.addTask(
            object : Task<Bitmap> {

                override fun onExecuteTask(): Bitmap {

                    return fetchBitmap(IMAGE_2)
                }

                override fun onTaskComplete(result: Bitmap) {

                    imageview_2.setImageBitmap(result)
                }
            }
        )
    }

    private fun showToast(str: String) {

        Toast.makeText(this@MainActivity, str, Toast.LENGTH_LONG).show()
    }

    private fun fetchBitmap(url: String): Bitmap {

        //Fetching image1 through okhttp
        val request: Request    = Request.Builder().url(url).build()
        val response:Response   = OkHttpClient().newCall(request).execute()
        val bitmap: Bitmap      = BitmapFactory.decodeStream(response.body?.byteStream())

        return bitmap
    }
}
