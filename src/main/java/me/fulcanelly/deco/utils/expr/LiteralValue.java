package me.fulcanelly.deco.utils.expr;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LiteralValue implements Expression {

    Object value;

    @Override
    public int getCapacity() {
        return 0;
    }

    @Override
    public void collect(Expression... exprs) {        
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
    
}
