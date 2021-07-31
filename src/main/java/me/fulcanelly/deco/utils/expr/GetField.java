package me.fulcanelly.deco.utils.expr;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Data
public class GetField implements Expression {
    
    Expression ofWhat;
    @NonNull String fieldName;

    @Override
    public int getCapacity() {
        return 1;
    }

    @Override
    public void collect(Expression... exprs) {
        ofWhat = exprs[0];
    }

    @Override
    public String toString() {
        return ofWhat.toString() + "." + fieldName;
    }
    
}
