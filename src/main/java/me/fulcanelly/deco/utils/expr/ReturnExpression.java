package me.fulcanelly.deco.utils.expr;

import lombok.Data;

@Data
public class ReturnExpression implements Expression {

    Expression value;

    @Override
    public int getCapacity() {
        return 1;
    }

    @Override
    public void collect(Expression... exprs) {
        value = exprs[0];
    }

    @Override
    public String toString() {
        return "return " + value.toString() + ";";
    }

}