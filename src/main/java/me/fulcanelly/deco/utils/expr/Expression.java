package me.fulcanelly.deco.utils.expr;

public interface Expression {
    int getCapacity();
    
    void collect(Expression ...exprs);
}