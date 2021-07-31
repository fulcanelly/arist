package me.fulcanelly.deco.utils.expr;

import lombok.Data;

@Data
public class VoidReturnStmt implements Expression {

    @Override
    public int getCapacity() {
        return 0;
    }

    @Override
    public void collect(Expression... exprs) {}

    @Override
    public String toString() {
        return "return;";
    }

}
