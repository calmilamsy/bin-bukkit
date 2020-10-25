/*     */ package org.bukkit.event;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Event
/*     */   implements Serializable
/*     */ {
/*     */   private final Type type;
/*     */   private final String name;
/*     */   
/*     */   protected Event(Type type) {
/*  15 */     exAssert((type != null), "type is null");
/*  16 */     exAssert((type != Type.CUSTOM_EVENT), "use Event(String) to make custom events");
/*  17 */     this.type = type;
/*  18 */     this.name = null;
/*     */   }
/*     */   
/*     */   protected Event(String name) {
/*  22 */     exAssert((name != null), "name is null");
/*  23 */     this.type = Type.CUSTOM_EVENT;
/*  24 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   public final Type getType() { return this.type; }
/*     */ 
/*     */   
/*     */   private void exAssert(boolean b, String s) {
/*  37 */     if (!b) {
/*  38 */       throw new IllegalArgumentException(s);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   public final String getEventName() { return (this.type != Type.CUSTOM_EVENT) ? this.type.toString() : this.name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Priority
/*     */   {
/*  60 */     Lowest,
/*     */ 
/*     */ 
/*     */     
/*  64 */     Low,
/*     */ 
/*     */ 
/*     */     
/*  68 */     Normal,
/*     */ 
/*     */ 
/*     */     
/*  72 */     High,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     Highest,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  83 */     Monitor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Category
/*     */   {
/*  96 */     PLAYER,
/*     */ 
/*     */ 
/*     */     
/* 100 */     ENTITY,
/*     */ 
/*     */ 
/*     */     
/* 104 */     BLOCK,
/*     */ 
/*     */ 
/*     */     
/* 108 */     LIVING_ENTITY,
/*     */ 
/*     */ 
/*     */     
/* 112 */     WEATHER,
/*     */ 
/*     */ 
/*     */     
/* 116 */     VEHICLE,
/*     */ 
/*     */ 
/*     */     
/* 120 */     WORLD,
/*     */ 
/*     */ 
/*     */     
/* 124 */     SERVER,
/*     */ 
/*     */ 
/*     */     
/* 128 */     INVENTORY,
/*     */ 
/*     */ 
/*     */     
/* 132 */     MISCELLANEOUS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Type
/*     */   {
/* 151 */     PLAYER_JOIN(Event.Category.PLAYER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 157 */     PLAYER_LOGIN(Event.Category.PLAYER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 163 */     PLAYER_PRELOGIN(Event.Category.PLAYER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 169 */     PLAYER_RESPAWN(Event.Category.PLAYER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 175 */     PLAYER_KICK(Event.Category.PLAYER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 181 */     PLAYER_CHAT(Event.Category.PLAYER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 187 */     PLAYER_COMMAND_PREPROCESS(Event.Category.PLAYER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 193 */     PLAYER_QUIT(Event.Category.PLAYER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 199 */     PLAYER_MOVE(Event.Category.PLAYER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 206 */     PLAYER_VELOCITY(Event.Category.PLAYER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 212 */     PLAYER_ANIMATION(Event.Category.PLAYER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 218 */     PLAYER_TOGGLE_SNEAK(Event.Category.PLAYER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 224 */     PLAYER_INTERACT(Event.Category.PLAYER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 230 */     PLAYER_INTERACT_ENTITY(Event.Category.PLAYER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 236 */     PLAYER_EGG_THROW(Event.Category.PLAYER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 242 */     PLAYER_TELEPORT(Event.Category.PLAYER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 248 */     PLAYER_PORTAL(Event.Category.PLAYER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 254 */     PLAYER_ITEM_HELD(Event.Category.PLAYER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 260 */     PLAYER_DROP_ITEM(Event.Category.PLAYER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 266 */     PLAYER_PICKUP_ITEM(Event.Category.PLAYER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 272 */     PLAYER_BUCKET_EMPTY(Event.Category.PLAYER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 278 */     PLAYER_BUCKET_FILL(Event.Category.PLAYER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 284 */     PLAYER_INVENTORY(Event.Category.PLAYER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 290 */     PLAYER_BED_ENTER(Event.Category.PLAYER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 296 */     PLAYER_BED_LEAVE(Event.Category.PLAYER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 302 */     PLAYER_FISH(Event.Category.PLAYER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 313 */     BLOCK_DAMAGE(Event.Category.BLOCK),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 322 */     BLOCK_CANBUILD(Event.Category.BLOCK),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 329 */     BLOCK_FROMTO(Event.Category.BLOCK),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 336 */     BLOCK_IGNITE(Event.Category.BLOCK),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 345 */     BLOCK_PHYSICS(Event.Category.BLOCK),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 351 */     BLOCK_PLACE(Event.Category.BLOCK),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 357 */     BLOCK_DISPENSE(Event.Category.BLOCK),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 363 */     BLOCK_BURN(Event.Category.BLOCK),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 369 */     LEAVES_DECAY(Event.Category.BLOCK),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 375 */     SIGN_CHANGE(Event.Category.BLOCK),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 383 */     REDSTONE_CHANGE(Event.Category.BLOCK),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 389 */     BLOCK_BREAK(Event.Category.BLOCK),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 395 */     BLOCK_FORM(Event.Category.BLOCK),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 401 */     BLOCK_SPREAD(Event.Category.BLOCK),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 407 */     BLOCK_FADE(Event.Category.BLOCK),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 413 */     BLOCK_PISTON_EXTEND(Event.Category.BLOCK),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 419 */     BLOCK_PISTON_RETRACT(Event.Category.BLOCK),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 430 */     INVENTORY_OPEN(Event.Category.INVENTORY),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 436 */     INVENTORY_CLOSE(Event.Category.INVENTORY),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 442 */     INVENTORY_CLICK(Event.Category.INVENTORY),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 448 */     INVENTORY_CHANGE(Event.Category.INVENTORY),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 454 */     INVENTORY_TRANSACTION(Event.Category.INVENTORY),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 460 */     FURNACE_SMELT(Event.Category.INVENTORY),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 466 */     FURNACE_BURN(Event.Category.INVENTORY),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 477 */     PLUGIN_ENABLE(Event.Category.SERVER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 483 */     PLUGIN_DISABLE(Event.Category.SERVER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 489 */     SERVER_COMMAND(Event.Category.SERVER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 495 */     MAP_INITIALIZE(Event.Category.SERVER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 509 */     CHUNK_LOAD(Event.Category.WORLD),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 515 */     CHUNK_UNLOAD(Event.Category.WORLD),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 523 */     CHUNK_POPULATED(Event.Category.WORLD),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 529 */     ITEM_SPAWN(Event.Category.WORLD),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 535 */     SPAWN_CHANGE(Event.Category.WORLD),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 541 */     WORLD_SAVE(Event.Category.WORLD),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 547 */     WORLD_INIT(Event.Category.WORLD),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 553 */     WORLD_LOAD(Event.Category.WORLD),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 559 */     WORLD_UNLOAD(Event.Category.WORLD),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 565 */     PORTAL_CREATE(Event.Category.WORLD),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 576 */     PAINTING_PLACE(Event.Category.ENTITY),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 582 */     PAINTING_BREAK(Event.Category.ENTITY),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 588 */     ENTITY_PORTAL_ENTER(Event.Category.ENTITY),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 600 */     CREATURE_SPAWN(Event.Category.LIVING_ENTITY),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 606 */     ENTITY_DAMAGE(Event.Category.LIVING_ENTITY),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 612 */     ENTITY_DEATH(Event.Category.LIVING_ENTITY),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 618 */     ENTITY_COMBUST(Event.Category.LIVING_ENTITY),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 624 */     ENTITY_EXPLODE(Event.Category.LIVING_ENTITY),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 637 */     EXPLOSION_PRIME(Event.Category.LIVING_ENTITY),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 643 */     ENTITY_TARGET(Event.Category.LIVING_ENTITY),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 650 */     ENTITY_INTERACT(Event.Category.LIVING_ENTITY),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 656 */     CREEPER_POWER(Event.Category.LIVING_ENTITY),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 662 */     PIG_ZAP(Event.Category.LIVING_ENTITY),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 668 */     ENTITY_TAME(Event.Category.LIVING_ENTITY),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 674 */     PROJECTILE_HIT(Event.Category.ENTITY),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 681 */     ENTITY_REGAIN_HEALTH(Event.Category.LIVING_ENTITY),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 692 */     LIGHTNING_STRIKE(Event.Category.WEATHER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 698 */     WEATHER_CHANGE(Event.Category.WEATHER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 704 */     THUNDER_CHANGE(Event.Category.WEATHER),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 715 */     VEHICLE_CREATE(Event.Category.VEHICLE),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 721 */     VEHICLE_DESTROY(Event.Category.VEHICLE),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 727 */     VEHICLE_DAMAGE(Event.Category.VEHICLE),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 733 */     VEHICLE_COLLISION_ENTITY(Event.Category.VEHICLE),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 739 */     VEHICLE_COLLISION_BLOCK(Event.Category.VEHICLE),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 745 */     VEHICLE_ENTER(Event.Category.VEHICLE),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 751 */     VEHICLE_EXIT(Event.Category.VEHICLE),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 757 */     VEHICLE_MOVE(Event.Category.VEHICLE),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 763 */     VEHICLE_UPDATE(Event.Category.VEHICLE),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 771 */     CUSTOM_EVENT(Event.Category.MISCELLANEOUS);
/*     */     
/*     */     private final Event.Category category;
/*     */ 
/*     */     
/* 776 */     Type(Event.Category category) { this.category = category; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 785 */     public Event.Category getCategory() { return this.category; }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Result
/*     */   {
/* 796 */     DENY,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 801 */     DEFAULT,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 807 */     ALLOW;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\Event.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */