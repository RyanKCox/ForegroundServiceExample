package com.revature.foregroundserviceexample.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import com.revature.foregroundserviceexample.R
import java.lang.Exception

const val NOTIFICATION_CHANNEL_GENERAL = "Checking"
const val INTENT_COMMAND = "Command"
const val INTENT_COMMAND_REPLY = "Reply"

//Foreground Service
class MyService: Service(){

    //For non-bound service, return null
    override fun onBind(p0: Intent?): IBinder? = null

    //Override Start
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val command = intent?.getStringExtra(INTENT_COMMAND)

        if (command == "Stop"){
            stopService()
            return START_NOT_STICKY
        } else if (command == INTENT_COMMAND_REPLY){
            Toast.makeText(this,"Clicked Reply",Toast.LENGTH_LONG).show()
        }

        showNotification()

        return START_NOT_STICKY

        //return super.onStartCommand(intent, flags, startId)
    }

    private fun stopService(){
        stopForeground(true)
        stopSelf()
    }

    private fun showNotification(){
        val manager =
            getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager

        val replyIntent =
            Intent(this,MyService::class.java).apply {
                putExtra(INTENT_COMMAND,INTENT_COMMAND_REPLY)
        }
        val replyPendingIntent =
            PendingIntent.getService(this,2,
                replyIntent,PendingIntent.FLAG_UPDATE_CURRENT)

        val exitIntent =
            Intent(this,MyService::class.java).apply {
                putExtra(INTENT_COMMAND,"Stop")
            }
        val exitPendingIntent =
            PendingIntent.getService(this,3,
                exitIntent,PendingIntent.FLAG_CANCEL_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            //Create notification channel
             try {

                 with(
                     NotificationChannel(
                         NOTIFICATION_CHANNEL_GENERAL,
                         "Hello World!",
                         NotificationManager.IMPORTANCE_DEFAULT
                     )
                 )
                 {
                     enableLights(true)
                     setShowBadge(true)
                     enableVibration(true)
                     setSound(null,null)
                     description = "Hello Description"
                     lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                     //startForeground(this.id,this.)

                     manager.createNotificationChannel(this)

                 }
             } catch (e:Exception) {
                 Log.d("Notification Error",
                     "Show Notification: ${e.localizedMessage}")
             }

            //Create notification
            with(
                NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_GENERAL)
            ){
                setContentTitle("First")
                setContentText("Notification text")
                setSmallIcon(R.drawable.ic_launcher_foreground)
                setContentIntent(replyPendingIntent)
                setContentIntent(exitPendingIntent)
                addAction(0,"Reply", replyPendingIntent)
                addAction(0,"Close",exitPendingIntent)
                startForeground(1,build())
            }
        }
        //use code for older verions
    }

}