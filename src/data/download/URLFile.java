package data.download;

/**
 * URL.java Downloads a URL file
 *
 * @author Pedro Contreras. <br>
 * Copyright (c) 2013. All rights reserved. <br>
 * Project name: movies. <br>
 * Created on: 2 Jul 2013. <br>
 */
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class URLFile {
	
	
    public void download(String address, String fileOut, String no_file) {   	
        OutputStream  out  = null;
        URLConnection conn = null;
        InputStream   in   = null;

        try {
            URL url = new URL(address);
            out     = new BufferedOutputStream(new FileOutputStream(fileOut));
            conn    = url.openConnection();
            in      = conn.getInputStream();
            byte[] buffer = new byte[1024];

            int numRead;
            long numWritten = 0;

            while ((numRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, numRead);
                numWritten += numRead;
            }

            System.out.println(fileOut + "\t" + numWritten);
        } 
        catch (MalformedURLException e) {
    		//if there are problem with the url, we replace the file with the no_file  
    		try {
                Files.copy(new File(no_file).toPath(), new File(fileOut).toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
    	}
        catch (Exception exception) { 
            exception.printStackTrace();
        } 
        
        
        finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } 
            catch (IOException ioe) {
            }
        }
    }

    public  void download(String address) {
        int lastSlashIndex = address.lastIndexOf('/');
        if (lastSlashIndex >= 0 &&
        lastSlashIndex < address.length() - 1) {
            download(address, address.substring(lastSlashIndex + 1), null);
        } 
        else {
            System.err.println("Could not figure out local file name for "+address);
        }
    }
    /**
    public static void main(String[] args) {
                    download("http://ia.media-imdb.com/images/M/MV5BMTUwMDIwNDMyN15BMl5BanBnXkFtZTcwNTAyNTEzMQ@@._V1._SY314_CR3,0,214,314_.jpg", "/Users/pedro/Downloads/a.jpg");
    }
     */
}