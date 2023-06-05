package me.jishuna.spells.api;

import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;

public class GlobalSettings {

    @ConfigEntry("max-parts-per-spell")
    @Comment("The maximum number of parts that can be used in a single spell.")
    public static int MAX_PARTS = 10;

}
