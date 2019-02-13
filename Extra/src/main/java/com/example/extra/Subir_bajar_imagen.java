package com.comfacesar.ServiAmigo.Extra;


import org.jibble.simpleftp.SimpleFTP;

import java.io.File;
import java.io.IOException;

public class Subir_bajar_imagen {
    public void subir_imagen(String archivo)
    {
        SimpleFTP ftp = new SimpleFTP();


// Connect to an FTP server on port 21.
        try {
            ftp.connect(WebService.getHOSTING() , 21, "andis", "123");
            // Set binary mode.
            ftp.bin();
            // Change to a new working directory on the FTP server.
            ftp.cwd("upload");

            // Upload some files.
                        ftp.stor(new File(archivo));

            // Quit from the FTP server.
                        ftp.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
