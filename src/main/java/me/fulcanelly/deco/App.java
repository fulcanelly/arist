package me.fulcanelly.deco;

import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;

import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.ConstPool;
import javassist.bytecode.Descriptor;
import javassist.bytecode.Mnemonic;
import lombok.SneakyThrows;
import me.fulcanelly.deco.utils.MethodDecompilerVisitor;

class ClassVisitor {

    CtClass clazz;
    ConstPool cPool;

    ClassVisitor(CtClass clazz) {
        this.clazz = clazz;
        cPool = clazz.getClassFile().getConstPool();
    }

    boolean inRange(int num, int start, int end) {
        return num >= start && num <= end;
    }


    void mapAndVisit(MethodDecompilerVisitor visitor, CodeIterator iter, int index, int opcode) {

        
        ///loads
        if (inRange(opcode, 21, 25)) {
            visitor.visitAload(iter.byteAt(index + 1));
        }

        if (inRange(opcode, 26, 45)) {
            visitor.visitAload((opcode - 26) % 4);
        }

        //storeas
        if (inRange(opcode, 59, 78)) {
            visitor.visitAstore((opcode - 59) % 4);
        }

        //math
        if (inRange(opcode, 96, 99)) {
            visitor.visitAdd();
        }

        //conversions
        if (inRange(opcode, 133, 147)) {
            visitor.visitConversion(opcode);
        }

        //returns
        if (inRange(opcode, 172, 176)) {
            visitor.visitReturn();
        }

        if (opcode == 177) {
            visitor.visitVoidReturn();
        }

        //getfield
        if (opcode == 180) {
            visitor.visitGetField(iter.s16bitAt(index + 1));
        }

        //invokevirtual & invokeinterface
        if (opcode == 182 || opcode == 185) {
            visitor.visitInvokevirtual(iter.s16bitAt(index + 1));
        }

        //invoke static
        if (opcode == 184) {
            visitor.visitInvokeStatic(iter.s16bitAt(index + 1));
        }

        //consts 
        if (opcode == 1) {
            visitor.visitLoadConst(null);
        }
        
        // if (opcode == 25 )
        // new CodeIterator(iter);
        // iter.byteAt(iter.get)
    }

    @SneakyThrows
    void visitMethod(CtMethod meth) {

        System.out.println(meth.getName());
    
        var codeIterator = meth.getMethodInfo()
            .getCodeAttribute()
            .iterator();

        var decom = new MethodDecompilerVisitor(meth, clazz);

        while (codeIterator.hasNext()) {
            int index = codeIterator.next();
            int opcode = codeIterator.byteAt(index);
            System.out.println("    " + Mnemonic.OPCODE[opcode]);
            mapAndVisit(decom, codeIterator, index, opcode);
        }
        decom.visitEnd();

    }

    @SneakyThrows
    void accept() {
        for (var meth : clazz.getDeclaredMethods()) {
            visitMethod(meth);
        }
    }
}

public class App {

    /**
     * what exactly should i get
     * 
     * aload_0 getfield areturn
     * 
     * ->
     * 
     * { exp return: { field: this }}
     */
    @SneakyThrows
    public static void main(String[] args) {

        var pool = ClassPool.getDefault();
        
        var clazz = pool.makeClass(new FileInputStream("ok.class"));
        new ClassVisitor(clazz).accept();;
    }


}
