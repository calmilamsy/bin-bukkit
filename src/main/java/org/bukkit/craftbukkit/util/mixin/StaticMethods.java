package org.bukkit.craftbukkit.util.mixin;

import joptsimple.OptionSet;

import java.util.function.Consumer;

public class StaticMethods {

    public static class MinecraftServer {

        public static Consumer<OptionSet> main;
    }
}
