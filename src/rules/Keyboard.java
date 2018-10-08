package rules;

/**
 * @title: Keyboard
 * @description: Class for Special, e.g.: K5 -> qwert
 * @author: Felix
 * @date: 7/26/2018 23:42
 **/

public class Keyboard extends ProductionRule {

    private int terminalSymbolCount;   //  e.g.: K5 -> qwert, thus count is 5

    public Keyboard() {
    }

    public Keyboard(String lhs, String rhs) {
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
        return "Keyboard{" +
                "terminalSymbolCount=" + terminalSymbolCount +
                ", lhs='" + lhs + '\'' +
                ", rhs='" + rhs + '\'' +
                '}';
    }
}
