/*     */ package org.bukkit.map;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MapFont
/*     */ {
/*  10 */   private final HashMap<Character, CharacterSprite> chars = new HashMap();
/*  11 */   private int height = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean malleable = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChar(char ch, CharacterSprite sprite) {
/*  21 */     if (!this.malleable) {
/*  22 */       throw new IllegalStateException("this font is not malleable");
/*     */     }
/*     */     
/*  25 */     this.chars.put(Character.valueOf(ch), sprite);
/*  26 */     if (sprite.getHeight() > this.height) {
/*  27 */       this.height = sprite.getHeight();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  37 */   public CharacterSprite getChar(char ch) { return (CharacterSprite)this.chars.get(Character.valueOf(ch)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth(String text) {
/*  46 */     if (!isValid(text)) {
/*  47 */       throw new IllegalArgumentException("text contains invalid characters");
/*     */     }
/*     */     
/*  50 */     int result = 0;
/*  51 */     for (int i = 0; i < text.length(); i++) {
/*  52 */       result += ((CharacterSprite)this.chars.get(Character.valueOf(text.charAt(i)))).getWidth();
/*     */     }
/*  54 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   public int getHeight() { return this.height; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(String text) {
/*  71 */     for (int i = 0; i < text.length(); i++) {
/*  72 */       char ch = text.charAt(i);
/*  73 */       if (ch != '§' && ch != '\n' && 
/*  74 */         this.chars.get(Character.valueOf(ch)) == null) return false; 
/*     */     } 
/*  76 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class CharacterSprite
/*     */   {
/*     */     private final int width;
/*     */     
/*     */     private final int height;
/*     */     
/*     */     private final boolean[] data;
/*     */     
/*     */     public CharacterSprite(int width, int height, boolean[] data) {
/*  89 */       this.width = width;
/*  90 */       this.height = height;
/*  91 */       this.data = data;
/*     */       
/*  93 */       if (data.length != width * height) {
/*  94 */         throw new IllegalArgumentException("size of data does not match dimensions");
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean get(int row, int col) {
/* 105 */       if (row < 0 || col < 0 || row >= this.height || col >= this.width) return false; 
/* 106 */       return this.data[row * this.width + col];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 114 */     public int getWidth() { return this.width; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 122 */     public int getHeight() { return this.height; }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\map\MapFont.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */