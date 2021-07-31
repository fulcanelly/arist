package me.fulcanelly.deco.utils;

import java.util.LinkedList;
import java.util.List;

import me.fulcanelly.deco.utils.expr.Expression;

public class ExpressionCollector {

    List<Expression> list = new LinkedList<>();

    static public <T> List<T> getLastFew(List<T> list, int count) {
        synchronized (list) {
            var size = list.size();
            return list.subList(size - count, size);
        }
    }

    //todo: add switchable logging 
    void push(Expression expr) {
        var count = expr.getCapacity();
       // System.out.printf("expects: %d size: %d = ", count, list.size(), list);

        if (count > list.size()) {
            System.out.println(list);
            System.out.printf("exprected: %d size: %d\n", count, list.size());
            throw new RuntimeException("not enough providen ");
        }

        var required = getLastFew(list, count);
        
        var copyList = (list.subList(0, list.size() - count)); //..new LinkedList<>(list);
        //copyList.removeAll(required);
        expr.collect(required.toArray(Expression[]::new));
        copyList.add(expr);
       // System.out.printf("%s -> %s\n", list, copyList);
        list = copyList;
    } 

}