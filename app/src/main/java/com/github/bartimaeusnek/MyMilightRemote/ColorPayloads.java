package com.github.bartimaeusnek.MyMilightRemote;

import lombok.*;

@RequiredArgsConstructor
public enum ColorPayloads {
    violet(0),
    blue(16),
    lightblue(32),
    aqua(48),
    mint(64),
    seafoam(80),
    green(96),
    lime(112),
    yellow(128),
    lightorange(144),
    //orange(160),
    candle(165),
    red(176),
    pink(192),
    fuchsia(208),
    lilac(224),
    lavender(240),
    white(255),
    none(0),
    ;

    @Getter
    private final int payload;
}
