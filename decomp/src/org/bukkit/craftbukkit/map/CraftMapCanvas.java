/*    */ package org.bukkit.craftbukkit.map;
/*    */ import java.awt.Image;
/*    */ import java.util.Arrays;
/*    */ import org.bukkit.map.MapCanvas;
/*    */ import org.bukkit.map.MapCursorCollection;
/*    */ import org.bukkit.map.MapView;
/*    */ 
/*    */ public class CraftMapCanvas implements MapCanvas {
/*    */   private final byte[] buffer;
/*    */   private final CraftMapView mapView;
/*    */   
/*    */   protected CraftMapCanvas(CraftMapView mapView) {
/* 13 */     this.buffer = new byte[16384];
/*    */ 
/*    */     
/* 16 */     this.cursors = new MapCursorCollection();
/*    */ 
/*    */     
/* 19 */     this.mapView = mapView;
/* 20 */     Arrays.fill(this.buffer, (byte)-1);
/*    */   }
/*    */   private byte[] base; private MapCursorCollection cursors;
/*    */   
/* 24 */   public CraftMapView getMapView() { return this.mapView; }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public MapCursorCollection getCursors() { return this.cursors; }
/*    */ 
/*    */ 
/*    */   
/* 32 */   public void setCursors(MapCursorCollection cursors) { this.cursors = cursors; }
/*    */ 
/*    */   
/*    */   public void setPixel(int x, int y, byte color) {
/* 36 */     if (x < 0 || y < 0 || x >= 128 || y >= 128)
/* 37 */       return;  if (this.buffer[y * 128 + x] != color) {
/* 38 */       this.buffer[y * 128 + x] = color;
/* 39 */       this.mapView.worldMap.a(x, y, y);
/*    */     } 
/*    */   }
/*    */   
/*    */   public byte getPixel(int x, int y) {
/* 44 */     if (x < 0 || y < 0 || x >= 128 || y >= 128) return 0; 
/* 45 */     return this.buffer[y * 128 + x];
/*    */   }
/*    */   
/*    */   public byte getBasePixel(int x, int y) {
/* 49 */     if (x < 0 || y < 0 || x >= 128 || y >= 128) return 0; 
/* 50 */     return this.base[y * 128 + x];
/*    */   }
/*    */ 
/*    */   
/* 54 */   protected void setBase(byte[] base) { this.base = base; }
/*    */ 
/*    */ 
/*    */   
/* 58 */   protected byte[] getBuffer() { return this.buffer; }
/*    */ 
/*    */   
/*    */   public void drawImage(int x, int y, Image image) {
/* 62 */     byte[] bytes = MapPalette.imageToBytes(image);
/* 63 */     for (int x2 = 0; x2 < image.getWidth(null); x2++) {
/* 64 */       for (int y2 = 0; y2 < image.getHeight(null); y2++)
/* 65 */         setPixel(x + x2, y + y2, bytes[y2 * image.getWidth(null) + x2]); 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void drawText(int x, int y, MapFont font, String text) { // Byte code:
/*    */     //   0: iload_1
/*    */     //   1: istore #5
/*    */     //   3: bipush #44
/*    */     //   5: istore #6
/*    */     //   7: aload_3
/*    */     //   8: aload #4
/*    */     //   10: invokevirtual isValid : (Ljava/lang/String;)Z
/*    */     //   13: ifne -> 26
/*    */     //   16: new java/lang/IllegalArgumentException
/*    */     //   19: dup
/*    */     //   20: ldc 'text contains invalid characters'
/*    */     //   22: invokespecial <init> : (Ljava/lang/String;)V
/*    */     //   25: athrow
/*    */     //   26: iconst_0
/*    */     //   27: istore #7
/*    */     //   29: iload #7
/*    */     //   31: aload #4
/*    */     //   33: invokevirtual length : ()I
/*    */     //   36: if_icmpge -> 211
/*    */     //   39: aload #4
/*    */     //   41: iload #7
/*    */     //   43: invokevirtual charAt : (I)C
/*    */     //   46: istore #8
/*    */     //   48: iload #8
/*    */     //   50: bipush #10
/*    */     //   52: if_icmpne -> 70
/*    */     //   55: iload #5
/*    */     //   57: istore_1
/*    */     //   58: iload_2
/*    */     //   59: aload_3
/*    */     //   60: invokevirtual getHeight : ()I
/*    */     //   63: iconst_1
/*    */     //   64: iadd
/*    */     //   65: iadd
/*    */     //   66: istore_2
/*    */     //   67: goto -> 205
/*    */     //   70: iload #8
/*    */     //   72: sipush #167
/*    */     //   75: if_icmpne -> 119
/*    */     //   78: aload #4
/*    */     //   80: bipush #59
/*    */     //   82: iload #7
/*    */     //   84: invokevirtual indexOf : (II)I
/*    */     //   87: istore #9
/*    */     //   89: iload #9
/*    */     //   91: iflt -> 119
/*    */     //   94: aload #4
/*    */     //   96: iload #7
/*    */     //   98: iconst_1
/*    */     //   99: iadd
/*    */     //   100: iload #9
/*    */     //   102: invokevirtual substring : (II)Ljava/lang/String;
/*    */     //   105: invokestatic parseByte : (Ljava/lang/String;)B
/*    */     //   108: istore #6
/*    */     //   110: iload #9
/*    */     //   112: istore #7
/*    */     //   114: goto -> 205
/*    */     //   117: astore #10
/*    */     //   119: aload_3
/*    */     //   120: aload #4
/*    */     //   122: iload #7
/*    */     //   124: invokevirtual charAt : (I)C
/*    */     //   127: invokevirtual getChar : (C)Lorg/bukkit/map/MapFont$CharacterSprite;
/*    */     //   130: astore #9
/*    */     //   132: iconst_0
/*    */     //   133: istore #10
/*    */     //   135: iload #10
/*    */     //   137: aload_3
/*    */     //   138: invokevirtual getHeight : ()I
/*    */     //   141: if_icmpge -> 195
/*    */     //   144: iconst_0
/*    */     //   145: istore #11
/*    */     //   147: iload #11
/*    */     //   149: aload #9
/*    */     //   151: invokevirtual getWidth : ()I
/*    */     //   154: if_icmpge -> 189
/*    */     //   157: aload #9
/*    */     //   159: iload #10
/*    */     //   161: iload #11
/*    */     //   163: invokevirtual get : (II)Z
/*    */     //   166: ifeq -> 183
/*    */     //   169: aload_0
/*    */     //   170: iload_1
/*    */     //   171: iload #11
/*    */     //   173: iadd
/*    */     //   174: iload_2
/*    */     //   175: iload #10
/*    */     //   177: iadd
/*    */     //   178: iload #6
/*    */     //   180: invokevirtual setPixel : (IIB)V
/*    */     //   183: iinc #11, 1
/*    */     //   186: goto -> 147
/*    */     //   189: iinc #10, 1
/*    */     //   192: goto -> 135
/*    */     //   195: iload_1
/*    */     //   196: aload #9
/*    */     //   198: invokevirtual getWidth : ()I
/*    */     //   201: iconst_1
/*    */     //   202: iadd
/*    */     //   203: iadd
/*    */     //   204: istore_1
/*    */     //   205: iinc #7, 1
/*    */     //   208: goto -> 29
/*    */     //   211: return
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #71	-> 0
/*    */     //   #72	-> 3
/*    */     //   #73	-> 7
/*    */     //   #74	-> 16
/*    */     //   #77	-> 26
/*    */     //   #78	-> 39
/*    */     //   #79	-> 48
/*    */     //   #80	-> 55
/*    */     //   #81	-> 58
/*    */     //   #82	-> 67
/*    */     //   #83	-> 70
/*    */     //   #84	-> 78
/*    */     //   #85	-> 89
/*    */     //   #87	-> 94
/*    */     //   #88	-> 110
/*    */     //   #89	-> 114
/*    */     //   #91	-> 117
/*    */     //   #95	-> 119
/*    */     //   #96	-> 132
/*    */     //   #97	-> 144
/*    */     //   #98	-> 157
/*    */     //   #99	-> 169
/*    */     //   #97	-> 183
/*    */     //   #96	-> 189
/*    */     //   #103	-> 195
/*    */     //   #77	-> 205
/*    */     //   #105	-> 211
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   119	0	10	ex	Ljava/lang/NumberFormatException;
/*    */     //   89	30	9	j	I
/*    */     //   147	42	11	c	I
/*    */     //   135	60	10	r	I
/*    */     //   48	157	8	ch	C
/*    */     //   132	73	9	sprite	Lorg/bukkit/map/MapFont$CharacterSprite;
/*    */     //   29	182	7	i	I
/*    */     //   0	212	0	this	Lorg/bukkit/craftbukkit/map/CraftMapCanvas;
/*    */     //   0	212	1	x	I
/*    */     //   0	212	2	y	I
/*    */     //   0	212	3	font	Lorg/bukkit/map/MapFont;
/*    */     //   0	212	4	text	Ljava/lang/String;
/*    */     //   3	209	5	xStart	I
/*    */     //   7	205	6	color	B
/*    */     // Exception table:
/*    */     //   from	to	target	type
/*    */     //   94	114	117	java/lang/NumberFormatException }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\map\CraftMapCanvas.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */