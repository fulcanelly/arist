package me.fulcanelly.deco.utils.expr;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor
public class AssignVariable implements Expression {

    @NonNull String varName;
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
        return varName + " = " + value.toString();
    }
    
}
