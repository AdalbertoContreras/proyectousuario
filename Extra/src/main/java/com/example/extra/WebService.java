package com.comfacesar.ServiAmigo.Extra;

public class WebService {
    private static final String HOSTING = "172.20.10.2";
    private static final String RAIZ = "http://"+ HOSTING;
    private static final String url = RAIZ + "/WScomfacesar/index.php";

    public static String getUrl() {
        return url;
    }

    public static String getHOSTING() {
        return HOSTING;
    }

    public static String getRAIZ() {
        return RAIZ;
    }
}
