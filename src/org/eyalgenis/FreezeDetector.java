package org.eyalgenis;

import java.io.*;
import java.util.ArrayList;

public class FreezeDetector implements Runnable {
    String path;
    File[] videoFiles;
    ArrayList<Video> validVideos = new ArrayList<>();

    @Override
    public void run() {

        videoFiles = getMp4Files();
        for (File f : this.videoFiles) {

            String inputFileName = f.toString();
            String outputFileName = inputFileName + "_out";

            String command = "ffmpeg -i " + inputFileName + " -lavfi freezedetect=\"n=0.003\" " + outputFileName;

            String output = executeCommand(command);
            System.out.println(output);

            ArrayList<Double> points = getPointsFromFilter(output);
            double duration = getVideoDuration(inputFileName);
            Video video = new Video();
            video.setStart(0);
            video.setEnd(duration);
            video.createValidVideoFromPoints(points);
            validVideos.add(video);
        }
    }

    private ArrayList<Double> getPointsFromFilter(String output) {
        String[] lines = output.split(System.getProperty("line.separator"));
        ArrayList<Double> points = new ArrayList<>();

        int i=0, j=1;
        while(i < lines.length) {

            if(lines[i].contains("lavfi.freezedetect.freeze_")) {
                String value = lines[i].split(": ")[1].split("kbits")[0];
                double start = Double.valueOf(value);
                points.add(j, start);
                j++;
            }

            i++;
        }

        return points;
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


    public static String executeCommand(String command) {
        StringBuffer output = new StringBuffer();
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();
    }


    public static void main(String[] args) {
        FreezeDetector fd = new FreezeDetector();
        fd.run();
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ArrayList<Video> getValidVideos() {
        return validVideos;
    }
}
