package org.eyalgenis;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VideoPeriod {
    double start;
    double end;

    public VideoPeriod(double start) {
        this.start = start;
    }

    public VideoPeriod(double start, double end) {
        this.start = start;
        this.end = end;
    }

    public VideoPeriod() {
    }

    public double calcDuration() {
        return end - start;
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public double getEnd() {
        return end;
    }

    public void setEnd(double end) {
        this.end = end;
    }

    public boolean comparePeriod(VideoPeriod p, double diff) {
        if(Math.abs(this.getStart()-p.getStart()) <= diff ) {
            return true;
        } else {
            return false;
        }
    }

    public JSONArray createJSON() throws JSONException {
        return new JSONArray().put(start).put(end);
    }
}
