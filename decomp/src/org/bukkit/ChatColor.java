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
/*     */ public static enum ChatColor
/*     */ {
/*  14 */   BLACK(false),
/*     */ 
/*     */ 
/*     */   
/*  18 */   DARK_BLUE(true),
/*     */ 
/*     */ 
/*     */   
/*  22 */   DARK_GREEN(2),
/*     */ 
/*     */ 
/*     */   
/*  26 */   DARK_AQUA(3),
/*     */ 
/*     */ 
/*     */   
/*  30 */   DARK_RED(4),
/*     */ 
/*     */ 
/*     */   
/*  34 */   DARK_PURPLE(5),
/*     */ 
/*     */ 
/*     */   
/*  38 */   GOLD(6),
/*     */ 
/*     */ 
/*     */   
/*  42 */   GRAY(7),
/*     */ 
/*     */ 
/*     */   
/*  46 */   DARK_GRAY(8),
/*     */ 
/*     */ 
/*     */   
/*  50 */   BLUE(9),
/*     */ 
/*     */ 
/*     */   
/*  54 */   GREEN(10),
/*     */ 
/*     */ 
/*     */   
/*  58 */   AQUA(11),
/*     */ 
/*     */ 
/*     */   
/*  62 */   RED(12),
/*     */ 
/*     */ 
/*     */   
/*  66 */   LIGHT_PURPLE(13),
/*     */ 
/*     */ 
/*     */   
/*  70 */   YELLOW(14),
/*     */ 
/*     */ 
/*     */   
/*  74 */   WHITE(15);
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
/* 122 */     for (ChatColor color : values())
/* 123 */       colors.put(Integer.valueOf(color.getCode()), color); 
/*     */   }
/*     */   
/*     */   private final int code;
/*     */   private static final Map<Integer, ChatColor> colors;
/*     */   
/*     */   ChatColor(int code) { this.code = code; }
/*     */   
/*     */   public int getCode() { return this.code; }
/*     */   
/*     */   public String toString() { return String.format("§%x", new Object[] { Integer.valueOf(this.code) }); }
/*     */   
/*     */   public static ChatColor getByCode(int code) { return (ChatColor)colors.get(Integer.valueOf(code)); }
/*     */   
/*     */   public static String stripColor(String input) {
/*     */     if (input == null)
/*     */       return null; 
/*     */     return input.replaceAll("(?i)§[0-F]", "");
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\ChatColor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */