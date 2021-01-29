package org.eyalgenis;

import java.util.ArrayList;
import java.util.LinkedList;

public class Video extends VideoPeriod {
    LinkedList<VideoPeriod> periods;
    double longest;
    double validTime;
    double validPrecentage;

    public void createValidVideoFromPoints(ArrayList<Double> points) {
        LinkedList<VideoPeriod> sections = new LinkedList<>();

        int i=0;
        while(i < points.size()) {
            VideoPeriod s = new VideoPeriod();
            s.setStart(points.get(i));
            if (i++ < points.size()) {
                s.setEnd(points.get(i));
                sections.add(s);
                i++;
            } else {
                s.setEnd(end);
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
}
