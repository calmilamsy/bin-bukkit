/*     */ package org.bukkit.map;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Image;
/*     */ import java.awt.image.BufferedImage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MapPalette
/*     */ {
/*  18 */   private static Color c(int r, int g, int b) { return new Color(r, g, b); }
/*     */ 
/*     */   
/*     */   private static double getDistance(Color c1, Color c2) {
/*  22 */     double rmean = (c1.getRed() + c2.getRed()) / 2.0D;
/*  23 */     double r = (c1.getRed() - c2.getRed());
/*  24 */     double g = (c1.getGreen() - c2.getGreen());
/*  25 */     int b = c1.getBlue() - c2.getBlue();
/*  26 */     double weightR = 2.0D + rmean / 256.0D;
/*  27 */     double weightG = 4.0D;
/*  28 */     double weightB = 2.0D + (255.0D - rmean) / 256.0D;
/*  29 */     return weightR * r * r + weightG * g * g + weightB * b * b;
/*     */   }
/*     */   private static final Color[] colors = { 
/*  32 */       new Color(false, false, false, false), new Color(false, false, false, false), new Color(false, false, false, false), new Color(false, false, false, false), c(89, 125, 39), c(109, 153, 48), c(27, 178, 56), c(109, 153, 48), c(174, 164, 115), c(213, 201, 140), c(247, 233, 163), c(213, 201, 140), c(117, 117, 117), c(144, 144, 144), c(167, 167, 167), c(144, 144, 144), c(180, 0, 0), c(220, 0, 0), c(255, 0, 0), c(220, 0, 0), c(112, 112, 180), c(138, 138, 220), c(160, 160, 255), c(138, 138, 220), c(117, 117, 117), c(144, 144, 144), c(167, 167, 167), c(144, 144, 144), c(0, 87, 0), c(0, 106, 0), c(0, 124, 0), c(0, 106, 0), c(180, 180, 180), c(220, 220, 220), c(255, 255, 255), c(220, 220, 220), c(115, 118, 129), c(141, 144, 158), c(164, 168, 184), c(141, 144, 158), c(129, 74, 33), c(157, 91, 40), c(183, 106, 47), c(157, 91, 40), c(79, 79, 79), c(96, 96, 96), c(112, 112, 112), c(96, 96, 96), c(45, 45, 180), c(55, 55, 220), c(64, 64, 255), c(55, 55, 220), c(73, 58, 35), c(89, 71, 43), c(104, 83, 50), c(89, 71, 43) };
/*     */ 
/*     */   
/*     */   public static final byte TRANSPARENT = 0;
/*     */ 
/*     */   
/*     */   public static final byte LIGHT_GREEN = 4;
/*     */ 
/*     */   
/*     */   public static final byte LIGHT_BROWN = 8;
/*     */ 
/*     */   
/*     */   public static final byte GRAY_1 = 12;
/*     */ 
/*     */   
/*     */   public static final byte RED = 16;
/*     */ 
/*     */   
/*     */   public static final byte PALE_BLUE = 20;
/*     */ 
/*     */   
/*     */   public static final byte GRAY_2 = 24;
/*     */ 
/*     */   
/*     */   public static final byte DARK_GREEN = 28;
/*     */ 
/*     */   
/*     */   public static final byte WHITE = 32;
/*     */ 
/*     */   
/*     */   public static final byte LIGHT_GRAY = 36;
/*     */ 
/*     */   
/*     */   public static final byte BROWN = 40;
/*     */ 
/*     */   
/*     */   public static final byte DARK_GRAY = 44;
/*     */   
/*     */   public static final byte BLUE = 48;
/*     */   
/*     */   public static final byte DARK_BROWN = 52;
/*     */ 
/*     */   
/*     */   public static BufferedImage resizeImage(Image image) {
/*  76 */     BufferedImage result = new BufferedImage('', '', 2);
/*  77 */     Graphics2D graphics = result.createGraphics();
/*  78 */     graphics.drawImage(image, 0, 0, 128, 128, null);
/*  79 */     graphics.dispose();
/*  80 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] imageToBytes(Image image) {
/*  89 */     BufferedImage temp = new BufferedImage(image.getWidth(null), image.getHeight(null), 2);
/*  90 */     Graphics2D graphics = temp.createGraphics();
/*  91 */     graphics.drawImage(image, 0, 0, null);
/*  92 */     graphics.dispose();
/*     */     
/*  94 */     int[] pixels = new int[temp.getWidth() * temp.getHeight()];
/*  95 */     temp.getRGB(0, 0, temp.getWidth(), temp.getHeight(), pixels, 0, temp.getWidth());
/*     */     
/*  97 */     byte[] result = new byte[temp.getWidth() * temp.getHeight()];
/*  98 */     for (int i = 0; i < pixels.length; i++) {
/*  99 */       result[i] = matchColor(new Color(pixels[i]));
/*     */     }
/* 101 */     return result;
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
/* 112 */   public static byte matchColor(int r, int g, int b) { return matchColor(new Color(r, g, b)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte matchColor(Color color) {
/* 121 */     if (color.getAlpha() < 128) return 0;
/*     */     
/* 123 */     int index = 0;
/* 124 */     double best = -1.0D;
/*     */     
/* 126 */     for (int i = 4; i < colors.length; i++) {
/* 127 */       double distance = getDistance(color, colors[i]);
/* 128 */       if (distance < best || best == -1.0D) {
/* 129 */         best = distance;
/* 130 */         index = i;
/*     */       } 
/*     */     } 
/*     */     
/* 134 */     return (byte)index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Color getColor(byte index) {
/* 143 */     if (index < 0 || index >= colors.length) {
/* 144 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 146 */     return colors[index];
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\map\MapPalette.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */