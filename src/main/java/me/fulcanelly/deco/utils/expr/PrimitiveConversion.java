package me.fulcanelly.deco.utils.expr;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class PrimitiveConversion implements Expression {

    Expression what;
    String targetType;

    protected static Map<String, String> typeUncover = Map.of(
        "f", "float",
        "d", "double",
        "l", "long",
        "i", "int",
        "b", "byte",
        "c", "char",
        "s", "short"
    );

    public PrimitiveConversion(String oneChartargetType) {
        targetType = typeUncover.get(oneChartargetType);
    }

    @Override
    public int getCapacity() {
        return 1;
    }

    @Override
    public void collect(Expression... exprs) {
        what = exprs[0];
    }

    
    @Override
    public String toString() {
        return "(" + targetType + ")" + what;
    }
    
}
