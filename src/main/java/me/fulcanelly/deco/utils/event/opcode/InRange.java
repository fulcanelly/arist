package me.fulcanelly.deco.utils.event.opcode;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import lombok.Data;
import lombok.SneakyThrows;

import java.lang.annotation.RetentionPolicy;

@Repeatable(InRanges.class)
@Retention(RetentionPolicy.RUNTIME) 
public @interface InRange {
    int from();
    int to();
}

@Retention(RetentionPolicy.RUNTIME) 
@interface InRanges {
    InRange[] value();
}
