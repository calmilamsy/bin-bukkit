package org.bukkit.mixin.accessor;

import net.minecraft.level.LevelProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LevelProperties.class)
public interface LevelPropertiesAccessor {

    @Accessor
    String getLevelName();
}
