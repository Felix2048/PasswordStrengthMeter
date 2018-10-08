package rules;

/**
 * @title: Special
 * @description: Class for Special, e.g.: S2 -> @!
 * @author: Felix
 * @date: 7/26/2018 23:45
 **/

public class Special extends ProductionRule {

    private int terminalSymbolCount;   //  e.g.: S2 -> @!, thus count is 2

    public Special() {
    }

    public Special(String lhs, String rhs) {
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
        return "Special{" +
                "terminalSymbolCount=" + terminalSymbolCount +
                ", lhs='" + lhs + '\'' +
                ", rhs='" + rhs + '\'' +
                '}';
    }
}
