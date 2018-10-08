package rules;

/**
 * @title: Alphabet
 * @description: Class for Alphabet, e.g.: A3 -> abc
 * @author: Felix
 **/

public class Alphabet extends ProductionRule {

    private int terminalSymbolCount;   //  e.g.: A3 -> abc, thus count is 3

    public Alphabet() {
    }

    public Alphabet(String lhs, String rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.terminalSymbolCount = rhs.length();
    }

    public int getTerminalSymbolCount() {
        return terminalSymbolCount;
    }

    public void setTerminalSymbolCount(int terminalSymbolCount) {
        this.terminalSymbolCount = terminalSymbolCount;
    }

    @Override
    public String toString() {
        return "Alphabet{" +
                "terminalSymbolCount=" + terminalSymbolCount +
                ", lhs='" + lhs + '\'' +
                ", rhs='" + rhs + '\'' +
                '}';
    }
}
