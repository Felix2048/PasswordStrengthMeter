package entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * @title: Guess
 * @description:
 * @author: Felix
 * @date: 7/28/2018 15:51
 **/

public class Guess implements Serializable {

    private String password;

    private double probability;

    public Guess() {
    }

    public Guess(String password, double probability) {
        this.password = password;
        this.probability = probability;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guess guess = (Guess) o;
        return Double.compare(guess.probability, probability) == 0 &&
                Objects.equals(password, guess.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(password, probability);
    }

    @Override
    public String toString() {
        return "Guess{" +
                "password='" + password + '\'' +
                ", probability=" + probability +
                '}';
    }
}
