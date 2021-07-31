package me.fulcanelly.deco.utils.expr;

import lombok.Data;

@Data
public class AddExpression implements Expression {

    Expression left, right;

    @Override
    public int getCapacity() {
        return 2;
    }

    @Override
    public void collect(Expression... exprs) {
        left = exprs[0];
        right = exprs[1];
    }


    @Override
    public String toString() {
        return String.format("(%s + %s)", left.toString(), right.toString());
    }
    
}