package me.fulcanelly.deco.utils.naming.local;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import javassist.CtClass;

public class NaiveVarNameGen implements VariableNameGenerator {

    @Override
    public String generateOrObtain(int index, CtClass type) {
        List<Character> chars = new LinkedList<>(
            List.of((char)((index % 26) + 'a'))
        );

        if (index >= 26) {
            //todo
        }
       
        return new String(
            ArrayUtils.toPrimitive(
                chars.toArray(Character[]::new)
            )
        );
    }
    
}