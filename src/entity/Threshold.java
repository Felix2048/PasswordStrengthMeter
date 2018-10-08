package entity;

/**
 * @title: Threshold
 * @description:
 * @author: Felix
 * @date: 7/29/2018 0:35
 **/

public class Threshold {

    private double guessCount;

    private double probability;

    private int timeInHours;

    private String info;

    public Threshold() {
    }

    public Threshold(double guessCount, double probability, int timeInHours, String info) {
        this.guessCount = guessCount;
        this.probability = probability;
        this.timeInHours = timeInHours;
        this.info = info;
    }

    public double getGuessCount() {
        return guessCount;
    }

    public void setGuessCount(double guessCount) {
        this.guessCount = guessCount;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public int getTimeInHours() {
        return timeInHours;
    }

    public void setTimeInHours(int timeInHours) {
        this.timeInHours = timeInHours;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
