package rules;

import java.io.Serializable;
import java.util.Objects;

/**
 * @title: ProductionRule
 * @description: Base class for production rule
 * @author: Felix
 **/

public class ProductionRule implements Serializable {

    protected String lhs;   //  e.g.: S -> A3,D4, thus lhs is S

    protected String rhs;   //  e.g.: D2 -> 66, thus rhs is 66

    public ProductionRule() {
    }

    public ProductionRule(String lhs, String rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public String getLhs() {
        return lhs;
    }

    public void setLhs(String lhs) {
        this.lhs = lhs;
    }

    public String getRhs() {
        return rhs;
    }

    public void setRhs(String rhs) {
        this.rhs = rhs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductionRule productionRule = (ProductionRule) o;
        return Objects.equals(getLhs(), productionRule.getLhs()) &&
                Objects.equals(getRhs(), productionRule.getRhs());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getLhs(), getRhs());
    }

    @Override
    public String toString() {
        return "ProductionRule{" +
                "lhs='" + lhs + '\'' +
                ", rhs='" + rhs + '\'' +
                '}';
    }
}
