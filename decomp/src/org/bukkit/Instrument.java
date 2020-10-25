/*    */ package org.bukkit;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public static enum Instrument
/*    */ {
/*  8 */   PIANO(false),
/*  9 */   BASS_DRUM(true),
/* 10 */   SNARE_DRUM(2),
/* 11 */   STICKS(3),
/* 12 */   BASS_GUITAR(4);
/*    */   
/*    */   static  {
/* 15 */     types = new HashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 30 */     for (Instrument instrument : values())
/* 31 */       types.put(Byte.valueOf(instrument.getType()), instrument); 
/*    */   }
/*    */   
/*    */   private final byte type;
/*    */   private static final Map<Byte, Instrument> types;
/*    */   
/*    */   Instrument(byte type) { this.type = type; }
/*    */   
/*    */   public byte getType() { return this.type; }
/*    */   
/*    */   public static Instrument getByType(byte type) { return (Instrument)types.get(Byte.valueOf(type)); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\Instrument.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */