package rules;

/**
 * @title: Capital
 * @description: Class for Capital, e.g.: C3 -> ABC
 * @author: Felix
 **/

public class Capital extends ProductionRule {

    private int terminalSymbolCount;   //  e.g.: C2 -> QQ, thus count is 2

    public Capital() {
    }

    public Capital(String lhs, String rhs) {
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
        return "Capital{" +
                "terminalSymbolCount=" + terminalSymbolCount +
                ", lhs='" + lhs + '\'' +
                ", rhs='" + rhs + '\'' +
                '}';
    }
}
