package com.example.extra;

import java.util.jar.Manifest;

import sun.rmi.runtime.Log;

public class Config {
    private static int version_android = 0;
    private static String imei = null;
    public void iniciar_config(Activity activity)
    {
        version_android = android.os.Build.VERSION.SDK_INT;
        TelephonyManager mngr = (TelephonyManager) activity.getBaseContext().getSystemService(activity.getBaseContext().TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //para versiones con android 6.0 o superior.
            int permissionCheck = ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.READ_PHONE_STATE);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                Log.i("Mensaje", "Se tiene permiso!");
                imei = Settings.Secure.getString(activity.getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            } else {
                Log.i("Mensaje", "No se tiene permiso.");
                ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.READ_PHONE_STATE }, 225);
                imei = "";
            }
        } else{
            //para versiones inferiores a android 6.0.
            imei = Settings.Secure.getString(activity.getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        int tipo_movil = mngr.getPhoneType();
    }

    public static String getImei() {
        return imei;
    }
}
