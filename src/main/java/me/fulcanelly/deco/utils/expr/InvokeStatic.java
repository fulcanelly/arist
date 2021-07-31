package me.fulcanelly.deco.utils.expr;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang.StringUtils;
import java.util.List;

@RequiredArgsConstructor
public class InvokeStatic implements Expression {

    Expression[] args;

    @NonNull String className;
    @NonNull String methodName;
    @NonNull Integer requires;

    
    @Override
    public int getCapacity() {
        return requires;
    }

    @Override
    public void collect(Expression... exprs) {
        args = exprs;
    }
        
    @Override
    public String toString() {
        return String.format(
                "%s.%s(%s)", className, methodName,
                StringUtils.join(List.of(args), ", ")
            );
    }
}
