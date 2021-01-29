package org.eyalgenis;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class FilesDownloader implements Runnable {
    String[] urls;

    @Override
    public void run() {
        for(String url : this.urls) {

            String fileName = getFileNameFromURL(url);

            try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
                 FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
                byte dataBuffer[] = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
            } catch (IOException e) {
                // handle exception
            }
        }
    }

    private static String getFileNameFromURL(String url) {
        String[] pipeDelimited = url.split("/");
        return pipeDelimited[pipeDelimited.length-1];
    }

    public String[] getUrls() {
        return urls;
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }
}
