package me.fulcanelly.deco.utils.event;

public interface Acceptor {
    boolean isFits(int opcode);

    int getCount();

    void apply(Object object);
}