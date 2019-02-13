package com.comfacesar.ServiAmigo.Extra;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FttpClient {
    private String hosting = "localhost";
    private FTPClient ftpClient;
    private final String carpeta_trabajo = "upload";
    private String ruta = hosting + "/" +  carpeta_trabajo;
    private String ruta_archivo_subido;
    private String archivo;
    private String archivo_nombre;
    private void conexion()
    {
        // Creando nuestro objeto ClienteFTP
        ftpClient = new FTPClient();
        try
        {
            URL url = new URL(WebService.getUrl());
            ftpClient.connect(InetAddress.getByName(url.getHost()));
            ftpClient.login("andis","123");
        }
        catch (UnknownHostException ex)
        {
        }
        catch (IOException ex)
        {
        }
        //Verificar conexión con el servidor.
        int reply = ftpClient.getReplyCode();
        if(FTPReply.isPositiveCompletion(reply))
        {
            try {
                //Verificar si se cambia de direcotirio de trabajo
                ftpClient.changeWorkingDirectory(carpeta_trabajo);//Cambiar directorio de trabajo
            }
            catch (IOException ex) {

            }
            //Activar que se envie cualquier tipo de archivo
            try {
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            }
            catch (IOException ex) {

            }
            ftpClient.enterLocalPassiveMode();
        }
    }


    public boolean subir(File archivo, String extencion)
    {
        conexion();
        BufferedInputStream buffIn = null;
        boolean subido = false;
        try
        {
            buffIn = new BufferedInputStream(new FileInputStream(archivo));//Ruta del archivo para enviar
            try
            {
                Calendar calendar = Calendar.getInstance ();
                archivo_nombre = "img_"
                        + calendar.get (Calendar.YEAR) + "-" + (calendar.get (Calendar.MONTH) + 1) + "-" + calendar.get (Calendar.DAY_OF_MONTH) + calendar.get (Calendar.MINUTE) + calendar.get (Calendar.SECOND)
                        + "." +extencion;
                subido = ftpClient.storeFile(archivo_nombre, buffIn);
                buffIn.close (); //Cerrar envio de arcivos al FTP
                ftpClient.logout(); //Cerrar sesión
                ftpClient.disconnect();//Desconectarse del servidor
            }
            catch (IOException ex)
            {
                Logger.getLogger (FttpClient.class.getName()).log (Level.SEVERE, null, ex);
            }
        }
        catch (FileNotFoundException ex)
        {
        }
        return subido;
    }

    public boolean bajar_iamgen_producto(String archivo_descargar,String ruta_descargar)
    {
        conexion();
        FileOutputStream fos = null;
        boolean bajado = false;
        try
        {
            fos = new FileOutputStream(ruta_descargar);
            bajado =ftpClient.retrieveFile(archivo_descargar,fos);
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger (FttpClient.class.getName()).log (Level.SEVERE, null, ex);
            return bajado;
        }
        catch (IOException ex)
        {
            Logger.getLogger (FttpClient.class.getName()).log (Level.SEVERE, null, ex);
            return bajado;
        }
        try {
            fos.close (); //Cerrar envio de arcivos al FTP
            ftpClient.logout(); //Cerrar sesión
            ftpClient.disconnect();//Desconectarse del servidor
        }
        catch (IOException ex)
        {
            Logger.getLogger (FttpClient.class.getName()).log (Level.SEVERE, null, ex);
            return bajado;
        }
        return bajado;
    }

    public boolean bajar(String archivo_descargar,String ruta_descargar)
    {
        conexion();
        FileOutputStream fos = null;
        boolean bajado = false;
        try
        {
            fos = new FileOutputStream(ruta_descargar);
            bajado =ftpClient.retrieveFile(archivo_descargar,fos);
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger (FttpClient.class.getName()).log (Level.SEVERE, null, ex);
            return bajado;
        }
        catch (IOException ex)
        {
            Logger.getLogger (FttpClient.class.getName()).log (Level.SEVERE, null, ex);
            return bajado;
        }
        try {
            fos.close (); //Cerrar envio de arcivos al FTP
            ftpClient.logout(); //Cerrar sesión
            ftpClient.disconnect();//Desconectarse del servidor
        }
        catch (IOException ex)
        {
            Logger.getLogger (FttpClient.class.getName()).log (Level.SEVERE, null, ex);
            return bajado;
        }
        return bajado;
    }

    public boolean eliminar(String ruta_eliminar)
    {
        archivo = ruta_eliminar.replaceFirst (WebService.getRAIZ() + "/" + carpeta_trabajo + "/", "");
        conexion();
        boolean eliminado = false;
        try
        {
            eliminado = ftpClient.deleteFile (archivo);
        }
        catch (IOException ex)
        {
            Logger.getLogger (FttpClient.class.getName()).log (Level.SEVERE, null, ex);
            return eliminado;
        }
        try
        {
            ftpClient.logout(); //Cerrar sesión
            ftpClient.disconnect();//Desconectarse del servidor
        }
        catch (IOException ex)
        {
            Logger.getLogger (FttpClient.class.getName()).log (Level.SEVERE, null, ex);
            return eliminado;
        }
        return eliminado;
    }

    public String getRuta()
    {
        return ruta;
    }

    public String getRuta_archivo_subido()
    {
        return archivo_nombre;
    }
}
