package org.eyalgenis;

import java.io.*;
import java.util.ArrayList;

public class FreezeDetector implements Runnable {

    File[] videoFiles;
    ArrayList<Video> validVideos = new ArrayList<>();

    @Override
    public void run() {

        videoFiles = getMp4Files();
        for (File f : this.videoFiles) {

            String inputFile = f.toString();
            String output = null;

            try {
                output = executeCommand(inputFile).toString();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ArrayList<Double> points = getPointsFromFilter(output);
            Video video = new Video();
            video.setEnd(points.get(points.size()-1));
            video.createValidVideoFromPoints(points);
            validVideos.add(video);
        }
    }

    private ArrayList<Double> getPointsFromFilter(String output) {

        String[] lines = output.split(System.getProperty("line.separator"));
        ArrayList<Double> points = new ArrayList<>();
        points.add(0.0);
        double duration = 0;

        int i=0;
        while(i < lines.length) {

            if(lines[i].contains("Duration:")) {
                String value = lines[i].split(":")[3].split(",")[0];
                duration = Double.valueOf(value);
            }

            if(lines[i].contains("lavfi.freezedetect.freeze_start")
                    || lines[i].contains("lavfi.freezedetect.freeze_end")) {
                String value = lines[i].split(": ")[1].split("kbits")[0];
                double point = Double.valueOf(value);
                points.add(point);
            }

            i++;
        }

        points.add(duration);
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


    public static OutputStream executeCommand(String inputFile) throws IOException {

        String outputFile = inputFile + "_out.mp4";
        String command = "ffmpeg -i " + inputFile + " -lavfi freezedetect=n=0.003 " + outputFile;
        System.out.println("Launching command: "+command);
        ProcessBuilder pb = new ProcessBuilder("ffmpeg", "-i", inputFile, "-lavfi","freezedetect=\"n=0.003\"", outputFile);
        Process proc = pb.start();

        PipeStream out = new PipeStream(proc .getInputStream());
        PipeStream err = new PipeStream(proc .getErrorStream());
        out.start();
        err.start();

        try {
            proc .waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Exit value is: "+proc .exitValue());
        return (err.getOs() != null ? err.getOs() : out.getOs());
    }

    public ArrayList<Video> getValidVideos() {
        return validVideos;
    }

    private static class PipeStream extends Thread {
        InputStream is;
        OutputStream os;

        public PipeStream(InputStream is) {
            this.is = is;
            this.os = new ByteArrayOutputStream();
        }

        public void run() {
            byte[] buffer=new byte[1024];
            int len;
            try {
                while ((len=is.read(buffer))>=0){
                    os.write(buffer,0,len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public OutputStream getOs() {
            return os;
        }
    }
}
