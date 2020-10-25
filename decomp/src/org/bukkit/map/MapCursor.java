/*     */ package org.bukkit.map;
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
/*     */ public final class MapCursor
/*     */ {
/*     */   private byte x;
/*     */   private byte y;
/*     */   private byte direction;
/*     */   private byte type;
/*     */   private boolean visible;
/*     */   
/*     */   public MapCursor(byte x, byte y, byte direction, byte type, boolean visible) {
/*  21 */     this.x = x;
/*  22 */     this.y = y;
/*  23 */     setDirection(direction);
/*  24 */     setRawType(type);
/*  25 */     this.visible = visible;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   public byte getX() { return this.x; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  41 */   public byte getY() { return this.y; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  49 */   public byte getDirection() { return this.direction; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   public Type getType() { return Type.byValue(this.type); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   public byte getRawType() { return this.type; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   public boolean isVisible() { return this.visible; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public void setX(byte x) { this.x = x; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   public void setY(byte y) { this.y = y; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDirection(byte direction) {
/*  97 */     if (direction < 0 || direction > 15) {
/*  98 */       throw new IllegalArgumentException("Direction must be in the range 0-15");
/*     */     }
/* 100 */     this.direction = direction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   public void setType(Type type) { setRawType(type.value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRawType(byte type) {
/* 116 */     if (type < 0 || type > 15) {
/* 117 */       throw new IllegalArgumentException("Type must be in the range 0-15");
/*     */     }
/* 119 */     this.type = type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   public void setVisible(boolean visible) { this.visible = visible; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Type
/*     */   {
/* 136 */     WHITE_POINTER(false),
/* 137 */     GREEN_POINTER(true),
/* 138 */     RED_POINTER(2),
/* 139 */     BLUE_POINTER(3),
/* 140 */     WHITE_CROSS(4);
/*     */     
/*     */     private byte value;
/*     */ 
/*     */     
/* 145 */     Type(int value) { this.value = (byte)value; }
/*     */ 
/*     */ 
/*     */     
/* 149 */     public byte getValue() { return this.value; }
/*     */ 
/*     */     
/*     */     public static Type byValue(byte value) {
/* 153 */       for (Type t : values()) {
/* 154 */         if (t.value == value) return t; 
/*     */       } 
/* 156 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\map\MapCursor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */