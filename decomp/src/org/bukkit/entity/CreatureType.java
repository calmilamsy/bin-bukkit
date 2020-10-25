/*    */ package org.bukkit.entity;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public static enum CreatureType {
/*  8 */   CHICKEN("Chicken"),
/*  9 */   COW("Cow"),
/* 10 */   CREEPER("Creeper"),
/* 11 */   GHAST("Ghast"),
/* 12 */   GIANT("Giant"),
/* 13 */   MONSTER("Monster"),
/* 14 */   PIG("Pig"),
/* 15 */   PIG_ZOMBIE("PigZombie"),
/* 16 */   SHEEP("Sheep"),
/* 17 */   SKELETON("Skeleton"),
/* 18 */   SLIME("Slime"),
/* 19 */   SPIDER("Spider"),
/* 20 */   SQUID("Squid"),
/* 21 */   ZOMBIE("Zombie"),
/* 22 */   WOLF("Wolf");
/*    */   private String name;
/*    */   
/*    */   static  {
/* 26 */     mapping = new HashMap();
/*    */ 
/*    */     
/* 29 */     for (CreatureType type : EnumSet.allOf(CreatureType.class))
/* 30 */       mapping.put(type.name, type); 
/*    */   }
/*    */   
/*    */   private static final Map<String, CreatureType> mapping;
/*    */   
/* 35 */   CreatureType(String name) { this.name = name; }
/*    */ 
/*    */ 
/*    */   
/* 39 */   public String getName() { return this.name; }
/*    */ 
/*    */ 
/*    */   
/* 43 */   public static CreatureType fromName(String name) { return (CreatureType)mapping.get(name); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\entity\CreatureType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */