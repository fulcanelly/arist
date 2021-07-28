package me.fulcanelly.deco;


import java.io.FileInputStream;

import javassist.ClassPool;
import javassist.bytecode.Mnemonic;
import lombok.SneakyThrows;

public class App {
    

    @SneakyThrows
    public static void main(String[] args) {
            
        var pool = ClassPool.getDefault();
        var clazz = pool.makeClass(new FileInputStream("ok.class"));
        for (var meth : clazz.getDeclaredMethods()) {
            System.out.println( meth.getReturnType().getName() + " " + meth.getName() + "(..)");
            
            var codeIterator = meth.getMethodInfo().getCodeAttribute().iterator();
            while (codeIterator.hasNext()) {
                int index = codeIterator.next();
                int op = codeIterator.byteAt(index);
                System.out.println("    " + Mnemonic.OPCODE[op]);
            }

            System.out.println("");
            
        }
    }

    
}
