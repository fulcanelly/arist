package me.fulcanelly.deco.utils.naming.local;

import javassist.CtClass;

public interface VariableNameGenerator {

    String generateOrObtain(int index, CtClass type);

}