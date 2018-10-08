package rules;

import java.io.Serializable;
import java.util.Objects;

/**
 * @title: Segments
 * @description: Class for segment production rule, implemented Serializable, e.g.: A3 -> abc, S2 -> @!
 * @author: Felix
 **/

public class Segment implements Serializable {

    private int count = 1;

    private double probability = Double.POSITIVE_INFINITY;

    private ProductionRule rule;

    public Segment() {
    }

    public Segment(ProductionRule productionRule) {
        this.rule = productionRule;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void addCount() {
        this.count++;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public ProductionRule getRule() {
        return rule;
    }

    public void setRule(ProductionRule rule) {
        this.rule = rule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Segment that = (Segment) o;
        return getCount() == that.getCount() &&
                Double.compare(that.getProbability(), getProbability()) == 0 &&
                Objects.equals(getRule(), that.getRule());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getRule());
    }

    @Override
    public String toString() {
        return "Segment{" +
                "count=" + count +
                ", probability=" + probability +
                ", rule=" + rule +
                '}';
    }
}
