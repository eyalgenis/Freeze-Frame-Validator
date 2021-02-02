package org.eyalgenis;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

public class Video extends VideoPeriod {
    LinkedList<VideoPeriod> periods;
    double longest;
    double validTime;
    double validPrecentage;

    public Video() {
        this.start = 0;
    }

    public void createValidVideoFromPoints(ArrayList<Double> points) {
        this.periods = new LinkedList<>();

        int i=0;
        while(i < points.size()) {
            VideoPeriod period = new VideoPeriod();
            period.setStart(points.get(i));
            if (i++ < points.size()) {
                period.setEnd(points.get(i));
                periods.add(period);
                i++;
            } else {
                period.setEnd(end);
            }
        }
    }

    public LinkedList<VideoPeriod> getPeriods() {
        return periods;
    }

    public boolean compareVideo(Video v, double diff) {
        int i=0;
        LinkedList<VideoPeriod> periods2 = v.getPeriods();
        while(i < periods2.size()) {
            if(!periods.get(i).comparePeriod(periods2.get(i), diff)) {
                return false;
            }
        }
        return true;
    }

    public double getLongest() {
        return longest;
    }

    public void setLongest(double longest) {
        this.longest = longest;
    }

    public double getValidTime() {
        return validTime;
    }

    public void setValidTime(double validTime) {
        this.validTime = validTime;
    }

    public double getValidPrecentage() {
        return validPrecentage;
    }

    public void setValidPrecentage(double validPrecentage) {
        this.validPrecentage = validPrecentage;
    }

    public JSONObject createVideoJSON() throws JSONException {
        JSONArray pjson = new JSONArray();
        for(VideoPeriod p : periods) {
            pjson.put(p.createJSON());
        }

        JSONObject json = new JSONObject()
                .put("longest_valid_period", longest)
                .put("valid_video_percentage", validPrecentage)
                .put("valid_periods", pjson);

        return json;
    }
}
