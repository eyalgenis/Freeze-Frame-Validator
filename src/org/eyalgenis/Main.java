package org.eyalgenis;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        FilesDownloader downloader = new FilesDownloader();
        downloader.setUrls(args);
        downloader.run();

        FreezeDetector detector = new FreezeDetector();
        detector.setPath(System.getProperty("user.dir"));
        detector.run();

        ArrayList<Video> videos = detector.getValidVideos();

        VideosAnalyzer analyzer = new VideosAnalyzer();
        analyzer.setVideos(videos);
        analyzer.run();

        String outputString = analyzer.getOutputJson().toString();
        System.out.println(outputString);
    }
}
