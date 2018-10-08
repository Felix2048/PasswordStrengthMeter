package rules;

/**
 * @title: Digit
 * @description: Class for Digit, e.g.: D3 -> 123
 * @author: Felix
 **/

public class Digit extends ProductionRule {

    private int terminalSymbolCount;   //  e.g.: D3 -> 123, thus count is 3

    public Digit() {
    }

    public Digit(String lhs, String rhs) {
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
        return "Digit{" +
                "terminalSymbolCount=" + terminalSymbolCount +
                ", lhs='" + lhs + '\'' +
                ", rhs='" + rhs + '\'' +
                '}';
    }
}
