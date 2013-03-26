package org.example.asteroides;

import org.example.asteroides.activity.AcercaDe;
import org.example.asteroides.activity.Asteroides;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;


public class ReceptorSMS extends BroadcastReceiver {

    @Override

    public void onReceive(Context context, Intent intent) {

          // Sacamos información del intent

          String estado = "", numero = "";

          Bundle extras = intent.getExtras();

//          if(extras != null) {
//
//                 estado = extras.getString(TelephonyManager.EXTRA_STATE);
//
//                 if(estado.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
//
//                        numero = extras.getString( TelephonyManager.EXTRA_INCOMING_NUMBER);
//
//                 }
//
//          }

          String info = estado + " "+ numero;

          Log.d("ReceptorAnuncio", info + " intent="+ intent);

          // Creamos Notificación

          NotificationManager nm = (NotificationManager) context.getSystemService( Context.NOTIFICATION_SERVICE);

          Notification notificacion = new Notification( R.drawable.ic_launcher,"SMS entrante", System.currentTimeMillis());

          Intent i = new Intent(context, Asteroides.class);
          i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          PendingIntent intencionPendiente = PendingIntent.getActivity( context, 0, i, 0);
          notificacion.setLatestEventInfo(context, "Llamada entrante", info, intencionPendiente);

          nm.notify(1, notificacion);

    }

}
