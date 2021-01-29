package org.eyalgenis;

import java.util.ArrayList;

public class VideosAnalyzer implements Runnable {
    ArrayList<Video> videos;
    private static final double diff = 0.5;
    boolean synced;

    @Override
    public void run() {

        for(Video v : videos) {
            calcLongestPeriodAndValidTime(v);
            calcValidPrecentage(v);
        }

        synced = true;
        outerloop:
        for (int i = 0; i < videos.size() - 1; i++) {
            for (int k = i + 1; k < videos.size(); k++) {
                if (!videos.get(i).compareVideo(videos.get(k), diff)) {
                    synced = false;
                    break outerloop;
                }
            }
        }



    }

    private void calcValidPrecentage(Video v) {
        double totalTime = v.calcDuration();
        double validPrecentage = v.getValidTime() / totalTime;
        v.setValidPrecentage(validPrecentage);
    }

    private void calcLongestPeriodAndValidTime(Video v) {
        double max = 0;
        double time = 0;
        for(VideoPeriod s : v.getPeriods()) {
            double temp = s.calcDuration();
            time += temp;
            if (temp > max) {
                max = temp;
            }
        }
        v.setLongest(max);
        v.setValidTime(time);
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }

    public boolean isSynced() {
        return synced;
    }

    public void setSynced(boolean synced) {
        this.synced = synced;
    }
}
