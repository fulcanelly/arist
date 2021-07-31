package me.fulcanelly.deco.utils;


import java.lang.annotation.Native;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.management.RuntimeErrorException;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.bytecode.ConstPool;
import javassist.bytecode.Descriptor;
import javassist.bytecode.Mnemonic;
import lombok.SneakyThrows;
import me.fulcanelly.deco.utils.expr.AddExpression;
import me.fulcanelly.deco.utils.expr.AssignVariable;
import me.fulcanelly.deco.utils.expr.GetField;
import me.fulcanelly.deco.utils.expr.InvokeMethod;
import me.fulcanelly.deco.utils.expr.InvokeStatic;
import me.fulcanelly.deco.utils.expr.LiteralValue;
import me.fulcanelly.deco.utils.expr.LoadVariableEpression;
import me.fulcanelly.deco.utils.expr.PrimitiveConversion;
import me.fulcanelly.deco.utils.expr.ReturnExpression;
import me.fulcanelly.deco.utils.expr.VoidReturnStmt;
import me.fulcanelly.deco.utils.naming.local.NaiveVarNameGen;
import me.fulcanelly.deco.utils.naming.local.VariableNameGenerator;

public class MethodDecompilerVisitor {

    ExpressionCollector expcollect = new ExpressionCollector();
    VariableNameGenerator namegen = new NaiveVarNameGen();

    final CtMethod method;
    final ConstPool cPool;
    final CtClass klass;
    final List<ParamInfo> params;

    public MethodDecompilerVisitor(CtMethod method, CtClass clazz) {
        this.method = method;
        this.klass = clazz;
        this.params = generateParamNames();
        this.cPool = clazz.getClassFile()
            .getConstPool();
    } 

    @SneakyThrows
    List<ParamInfo> generateParamNames() {
        var paramTyepes = method.getParameterTypes();
        return IntStream.range(0, paramTyepes.length).boxed()
            .map(index -> 
                new ParamInfo(paramTyepes[index], namegen.generateOrObtain(index + 1, paramTyepes[index]))
            )
            .collect(Collectors.toList());
    } 

    protected boolean isMethod(int ...modifiers) {
        var methodMods = method.getModifiers();
        for (var modifier : modifiers) {
            if ((methodMods & modifier) == 0) {
                return false;
            }
        }
        return true;
    }

    protected boolean isLocalVariableIsThis(int index) {
        return index == 0 && !isMethod(Modifier.STATIC);
    }

    public void visitAload(int index) {
        expcollect.push(
            LoadVariableEpression.builder()
                .name(
                    isLocalVariableIsThis(index) ? "this" : namegen.generateOrObtain(index, null)
                )
                .build()  
        );
    }

    public void visitAdd() {
        expcollect.push(
            new AddExpression()
        );
    }

    @SneakyThrows
    public void visitInvokevirtual(int index) {
        System.out.println(cPool.getMethodrefName(index));

        System.out.println();

        var params = Descriptor.getParameterTypes(
                cPool.getMethodrefType(index), 
                klass.getClassPool()
            );

        expcollect.push(
            new InvokeMethod(cPool.getMethodrefName(index), params.length)
        );
    }

    public void visitReturn() {
        expcollect.push(
            new ReturnExpression()
        );
    }

    public void visitAstore(int index) {
        expcollect.push(
            new AssignVariable(namegen.generateOrObtain(index, null))
        );
    }

    public void visitGetField(int index) {
        expcollect.push(
            new GetField(cPool.getFieldrefName(index))
        );
    }

    public void visitConversion(int opcode) {
        var mnemo = Mnemonic.OPCODE[opcode];
        var target = mnemo.substring(mnemo.length() - 1);
        expcollect.push(
            new PrimitiveConversion(target)
        );

    }

    @SneakyThrows
    public String getMethodTitle() {
        return method.getReturnType().getName() + " " +
            method.getName() + "(" +
            params.stream()
                .map(Object::toString)    
                .collect(Collectors.joining(", "))
            + ")";
    }

    public void visitEnd() {
        System.out.println(getMethodTitle());
        System.out.println( 
            expcollect.list.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"))
            );
    }

    public void visitVoidReturn() {
        expcollect.push(
            new VoidReturnStmt()
        );
    }

    @SneakyThrows
    public void visitInvokeStatic(int index) {
        var params = Descriptor.getParameterTypes(
                cPool.getMethodrefType(index), 
                klass.getClassPool()
            );
        expcollect.push(
            new InvokeStatic(cPool.getMethodrefClassName(index), cPool.getMethodrefName(index), params.length)
        );
    }

    public void visitLoadConst(Object value) {
        expcollect.push(
            new LiteralValue(value)
        );
    }


    
}