package com.github.bartimaeusnek.MyMilightRemote;

import lombok.*;

@RequiredArgsConstructor
public enum BasicCommands {
    DISCO_SPEED_SLOWER(67),
    DISCO_SPEED_FASTER(68),
    GROUP_001_ON(69),
    GROUP_002_ON(71),
    GROUP_003_ON(73),
    GROUP_004_ON(75),
    GROUP_ALL_ON(66),
    GROUP_001_OFF(70),
    GROUP_002_OFF(72),
    GROUP_003_OFF(74),
    GROUP_004_OFF(76),
    GROUP_ALL_OFF(65),
    DISCO(77),
    COLOR_BIT(64),
    COLOR_WHITE(194),
    ;

    @Getter
    private final int command;
}
