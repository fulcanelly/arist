package me.fulcanelly.deco.utils.expr;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Data
public class InvokeMethod implements Expression {

    Expression target;
    Expression[] args;
    
    @NonNull String methodName;
    @NonNull Integer requires;

    @Override
    public int getCapacity() {
        return requires + 1;
    }

    @Override
    public void collect(Expression... exprs) {
        target = exprs[0];
        args = Arrays.copyOfRange(exprs, 1, exprs.length);        
    }

    @Override
    public String toString() {
        return String.format(
            "%s.%s(%s)", 
            target.toString(), 
            methodName,
            StringUtils.join(List.of(args), ", ")
        );
    }
    
}
