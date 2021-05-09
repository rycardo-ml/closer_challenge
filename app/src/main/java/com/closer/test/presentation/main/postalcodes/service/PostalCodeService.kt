package com.closer.test.presentation.main.postalcodes.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.room.withTransaction
import com.closer.test.R
import com.closer.test.presentation.main.MainActivity
import com.closer.test.util.database.AppDatabase
import com.closer.test.util.model.PostalCode
import com.closer.test.util.network.PostalCodeAPI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.regex.Pattern
import javax.inject.Inject

private const val TAG = "PostalCodeService"

@AndroidEntryPoint
class PostalCodeService: Service() {

    private val mBinder = Binder()

    @Inject
    lateinit var postalCodeAPI: PostalCodeAPI

    @Inject
    lateinit var database: AppDatabase

    private var running = false

    override fun onCreate() {
        super.onCreate()

        running = false
        Log.d(TAG, "onCreate $postalCodeAPI | $database")

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val notification = NotificationCompat.Builder(this, "postalcode")
            .setContentTitle("Closer")
            .setContentText("Downloading postal codes")
            .setSmallIcon(R.drawable.ic_error)
            .setContentIntent(pendingIntent)
            .build()

        createNotificationChannel("postalcode", "Closer", "Downloading postal codes")
        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        Log.d(TAG, "onStartCommand [$this] [$flags] [$startId")

        if (running) {
            return START_NOT_STICKY
        }

        running = true
        downloadData()

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        running = false
    }

    override fun onBind(intent: Intent?): IBinder? {
        return mBinder
    }

    private fun downloadData() {
        Log.d(TAG, "downloadData")

        CoroutineScope(Dispatchers.IO).launch {
            val responseBody = postalCodeAPI.downloadPostalCodeFile()

            Log.d(TAG, "fetchPostalCode before read data")

            val stream: InputStream = responseBody.body()!!.byteStream()

            val pattern = Pattern.compile(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")
            val postalCodes = createLines(stream).map {
                convertLineToPostalCode(it, pattern)
            }

            Log.d(TAG, "fetchPostalCode before save")

            val postalCodeDAO = database.postalCodeDAO()
            database.withTransaction {
                postalCodeDAO.deleteAll()
                postalCodeDAO.insert(*postalCodes.toTypedArray())
            }

            Log.d(TAG, "fetchPostalCode after save")

            sendSuccessBroadcast()
            stopSelf()
        }
    }

    private fun convertLineToPostalCode(line: String, pattern: Pattern): PostalCode {
        val splitData = pattern.split(line)

        return PostalCode(
            splitData[0].toLong(),
            splitData[1].toLong(),
            splitData[2].toLong(),
            splitData[3],

            splitData[4].toLongOrNull(),
            splitData[5],
            splitData[6],
            splitData[7],
            splitData[8],
            splitData[9],
            splitData[10],
            splitData[11],
            splitData[12],
            splitData[13],

            splitData[14].toLong(),
            splitData[15].toLong(),
            splitData[16]
        )
    }

    private fun createLines(stream: InputStream): List<String> {
        val br = BufferedReader(InputStreamReader(stream))

        var line: String?
        var processLine = false
        val list = mutableListOf<String>()
        while (br.readLine().also { line = it } != null) {
            if (!processLine) {
                processLine = true
                continue
            }

            list.add(line!!)
        }

        return list
    }

    private fun sendSuccessBroadcast() {
        val intent = Intent("POSTAL_CODE_RECEIVER")

        intent.putExtra("STATUS", "OK")

        LocalBroadcastManager
            .getInstance(this)
            .sendBroadcast(intent)
    }

    private fun sendErrorBroadcast() {
        val intent = Intent("POSTAL_CODE_RECEIVER")

        intent.putExtra("STATUS", "ERROR")

        LocalBroadcastManager
            .getInstance(this)
            .sendBroadcast(intent)
    }


    private fun createNotificationChannel(channelId: String, title: String, description: String) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, title, importance)
            channel.description = description

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NotificationManager::class.java) ?: return
            notificationManager.createNotificationChannel(channel)
        }
    }

    inner class Binder : android.os.Binder() {
        val service: PostalCodeService
            get() = this@PostalCodeService
    }
}