package me.fulcanelly.deco.utils;

import javassist.CtClass;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor @Data
public class ParamInfo {

    CtClass type;
    String name;

    @Override
    public String toString() {
        return type.getName() + " " + name;
    }
}
