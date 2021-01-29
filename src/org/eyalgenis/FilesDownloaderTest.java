package org.eyalgenis;

import org.junit.Test;

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
    }
}