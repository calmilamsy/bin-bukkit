package org.bukkit.craftbukkit.mixin;

import joptsimple.OptionSet;
import net.minecraft.server.MinecraftServer;
import org.bukkit.craftbukkit.Main;
import org.bukkit.craftbukkit.util.mixin.StaticMethods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class MixinMinecraftServer {

    @Shadow public static void main(String[] strings) {}

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "<clinit>", at = @At("HEAD"))
    private static void addStaticMethods(CallbackInfo ci) {
        StaticMethods.MinecraftServer.main = MixinMinecraftServer::main;
    }

    @Inject(method = "main([Ljava/lang/String;)V", at = @At("HEAD"), cancellable = true)
    private static void main(String[] strings, CallbackInfo ci) {
        if (initialHijack) {
            initialHijack = false;
            Main.main(strings);
            ci.cancel();
        }
    }

    private static void main(OptionSet options) {
        MixinMinecraftServer.options = options;
        main((String[]) null);
    }

    private static boolean initialHijack = true;
    private static OptionSet options;
}
