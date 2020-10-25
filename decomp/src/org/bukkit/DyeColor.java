/*     */ package org.bukkit;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public static enum DyeColor
/*     */ {
/*  14 */   WHITE(false),
/*     */ 
/*     */ 
/*     */   
/*  18 */   ORANGE(true),
/*     */ 
/*     */ 
/*     */   
/*  22 */   MAGENTA(2),
/*     */ 
/*     */ 
/*     */   
/*  26 */   LIGHT_BLUE(3),
/*     */ 
/*     */ 
/*     */   
/*  30 */   YELLOW(4),
/*     */ 
/*     */ 
/*     */   
/*  34 */   LIME(5),
/*     */ 
/*     */ 
/*     */   
/*  38 */   PINK(6),
/*     */ 
/*     */ 
/*     */   
/*  42 */   GRAY(7),
/*     */ 
/*     */ 
/*     */   
/*  46 */   SILVER(8),
/*     */ 
/*     */ 
/*     */   
/*  50 */   CYAN(9),
/*     */ 
/*     */ 
/*     */   
/*  54 */   PURPLE(10),
/*     */ 
/*     */ 
/*     */   
/*  58 */   BLUE(11),
/*     */ 
/*     */ 
/*     */   
/*  62 */   BROWN(12),
/*     */ 
/*     */ 
/*     */   
/*  66 */   GREEN(13),
/*     */ 
/*     */ 
/*     */   
/*  70 */   RED(14),
/*     */ 
/*     */ 
/*     */   
/*  74 */   BLACK(15);
/*     */   
/*     */   static  {
/*  77 */     colors = new HashMap();
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
/* 103 */     for (DyeColor color : values())
/* 104 */       colors.put(Byte.valueOf(color.getData()), color); 
/*     */   }
/*     */   
/*     */   private final byte data;
/*     */   private static final Map<Byte, DyeColor> colors;
/*     */   
/*     */   DyeColor(byte data) { this.data = data; }
/*     */   
/*     */   public byte getData() { return this.data; }
/*     */   
/*     */   public static DyeColor getByData(byte data) { return (DyeColor)colors.get(Byte.valueOf(data)); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\DyeColor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */