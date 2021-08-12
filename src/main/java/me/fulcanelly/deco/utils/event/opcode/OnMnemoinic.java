package me.fulcanelly.deco.utils.event.opcode;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME) 
public @interface OnMnemoinic {
    String mnemonic();
}
