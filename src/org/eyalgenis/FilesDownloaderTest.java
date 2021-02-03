package org.eyalgenis;

import org.junit.Test;

import java.io.File;
import java.io.FilenameFilter;

import static org.junit.Assert.*;

public class FilesDownloaderTest {
    @Test
    public void downloadFilesFromURLsTest() {
        String[] urls = {
                "https://storage.googleapis.com/hiring_process_data/freeze_frame_input_a.mp4",
                "https://storage.googleapis.com/hiring_process_data/freeze_frame_input_b.mp4",
                "https://storage.googleapis.com/hiring_process_data/freeze_frame_input_c.mp4"
        };

        FilesDownloader downloader = new FilesDownloader();
        downloader.setUrls(urls);
        downloader.run();

        File[] files = getMp4Files();
        assertEquals(urls.length, files.length);
    }

    public File[] getMp4Files() {
        File dir = new File(".");
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".mp4");
            }
        });

        return files;
    }
}