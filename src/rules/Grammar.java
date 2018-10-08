package rules;

import java.io.Serializable;

/**
 * @title: Grammar
 * @description: Class for grammar production rule, implemented Serializable, e.g.: S -> A3D3S1
 * @author: Felix
 **/

public class Grammar extends ProductionRule implements Serializable{

    private int count = 1;

    private double probability = Double.POSITIVE_INFINITY;

    private int nonterminalSymbolCount;   //  e.g.: S -> A4,D3,S2, thus count is 3

    public Grammar() {
    }

    public Grammar(String rhs) {
        this.lhs = "S";
        this.rhs = rhs;
        int nonterminalSymbolCount = 0;
        for(int i = 0; i < rhs.length(); i++) {
            if (Character.isAlphabetic(rhs.indexOf(i))) {
                nonterminalSymbolCount++;
            }
        }
        this.nonterminalSymbolCount = nonterminalSymbolCount;
    }

    public int getNonterminalSymbolCount() {
        return nonterminalSymbolCount;
    }

    public void setNonterminalSymbolCount(int nonterminalSymbolCount) {
        this.nonterminalSymbolCount = nonterminalSymbolCount;
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

    @Override
    public String toString() {
        return "Grammar{" +
                "count=" + count +
                ", probability=" + probability +
                ", nonterminalSymbolCount=" + nonterminalSymbolCount +
                ", lhs='" + lhs + '\'' +
                ", rhs='" + rhs + '\'' +
                '}';
    }
}
