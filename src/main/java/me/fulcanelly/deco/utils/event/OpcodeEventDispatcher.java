package me.fulcanelly.deco.utils.event;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import me.fulcanelly.deco.utils.MethodDecompilerVisitor;
import me.fulcanelly.deco.utils.event.opcode.*;

@AllArgsConstructor
public class OpcodeEventDispatcher {
    
    Class<?> clazz;
    Acceptor[] acceptors;

    void apply(Object object, int opcode) {
      

        
    }


    void simpleApply(Object object, int opcode) {
        for (var acceptor : acceptors) {
            if (acceptor.isFits(opcode)) {
                acceptor.apply(object);
                return ;
            }
        }
    }

    void functionalApply(Object object, int opcode) {
        Stream.of(acceptors)//.parallel()
            .filter(acceptor -> acceptor.isFits(opcode))
            .limit(1).forEach(it -> it.apply(object));
    }

    void loh() {
        cehckTimeOf(() -> {
            functionalApply(null, 251);
        }, 80);

        cehckTimeOf(() -> {
            simpleApply(null, 251);
        }, 80);

        var linear = linelize(acceptors);


        cehckTimeOf(() -> {
            linear[251].apply(null);
        }, 80);
    } 

    Acceptor[] linelize(Acceptor[] acceptors) {
        var result = new Acceptor[255];

        for (int i = 0; i < 255; i++) {
            for (var acceptor : acceptors) {
                if (acceptor.isFits(i)) {
                    result[i] = acceptor;
                    break;
                }
            }
            
        }
        //for () {

        //}
        return result;
    }


    void cehckTimeOf(Runnable runnable, int times) {
        var startTime = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            runnable.run();
        }
        var elapsed = System.currentTimeMillis() - startTime;
        System.out.printf("%d times runed, took time %d / 1000 sec\n", times, elapsed );
    }

    @SneakyThrows
    public static void main(String[] args) {
        Stream.generate(() -> mock(Acceptor.class));
        doReturn(false).when(mock(Acceptor.class)).isFits(0);

        var testSet = Stream.iterate(0, it -> it + 1)
            .takeWhile(it -> it < 255)
            .map(index ->
                Stream.of(mock(Acceptor.class))
                    .peek(acceptor -> 
                        doReturn(index > 250 ? true : false)
                        .when(acceptor).isFits(index)
                    )
                    .peek(acceptor -> 
                        doAnswer(it -> {
                        //    System.out.println(it.getArguments()[0]);
                            return null;
                        }).when(acceptor).apply(null)
                    )
                    .findAny().get()
                )
            .toArray(Acceptor[]::new);
        //testSet; 
            new OpcodeEventDispatcher(null, testSet ).loh();
        //System.out.println( Arrays.toString(testSet));
     //   System.out.println(OpcodeEventDispatcher.class.getDeclaredMethod("loh").invoke(null));
    }
/*

    <T extends OnCode & InRange & OnMnemoinic >
    List<T> isMethodEventHadler(Method method) {
        return Stream.of(method.getAnnotations())
            .filter(it -> {
                if (it instanceof OnCode) {
                    return true;
                }
                if (it instanceof InRange) {
                    return true;
                }
                if (it instanceof OnMnemoinic) {
                    return true;
                }
                return false;
            })
            .collect(Collectors.toList());
    }

    void test() {
        var it = isMethodEventHadler(null).get();  
        it.from();
        it.opcode();
    }*/
}
