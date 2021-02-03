package org.eyalgenis;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class VideosAnalyzerTest {

    @Test
    public void analyzeVideosTest() throws JSONException {

        VideosAnalyzer va = new VideosAnalyzer();
        ArrayList<Video> videos = new ArrayList<>();

        for(int i=0; i < 3; i++) {
            Video v = new Video();

            LinkedList<VideoPeriod> periods = new LinkedList<>();

            double k=0;
            for(int j=0; j < 2; j++) {
                VideoPeriod p = new VideoPeriod();
                p.setStart(k);
                p.setEnd(k+2);
                k+=5;
                periods.add(p);
            }

            v.setPeriods(periods);
            int index = v.getPeriods().size()-1;
            v.setEnd(v.getPeriods().get(index).getEnd());
            videos.add(v);
        }

        va.setVideos(videos);
        va.run();
        JSONObject json = va.getOutputJson();
        String synced = json.get("all_videos_freeze_frame_synced").toString();
        assertEquals(synced, "true");
    }
}