package org.eyalgenis;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        FilesDownloader downloader = new FilesDownloader();
        downloader.setUrls(args);
        downloader.run();

        FreezeDetector detector = new FreezeDetector();
        detector.run();

        ArrayList<Video> videos = detector.getValidVideos();

        VideosAnalyzer analyzer = new VideosAnalyzer();
        analyzer.setVideos(videos);
        analyzer.run();

        String outputString = analyzer.getOutputJson().toString();
        System.out.println(outputString);
    }
}
