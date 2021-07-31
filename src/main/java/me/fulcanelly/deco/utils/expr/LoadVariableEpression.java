package me.fulcanelly.deco.utils.expr;

import javassist.CtClass;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class LoadVariableEpression implements Expression {

    String name;
    CtClass type;

    @Override
    public int getCapacity() {
        return 0;
    }

    @Override
    public void collect(Expression... exprs) {        
    }

    @Override
    public String toString() {
        return name;
    }

}