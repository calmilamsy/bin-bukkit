/*     */ package org.bukkit;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Note
/*     */ {
/*     */   private final byte note;
/*     */   
/*     */   public enum Tone
/*     */   {
/*  15 */     F(-1, true),
/*  16 */     G(true, true),
/*  17 */     A(3, true),
/*  18 */     B(5, false),
/*  19 */     C(6, true),
/*  20 */     D(8, true),
/*  21 */     E(10, false);
/*     */     private final boolean sharpable;
/*     */     private final byte id; private static final Map<Byte, Tone> tones; public static final byte TONES_COUNT; Tone(byte id, boolean sharpable) { this.id = id; this.sharpable = sharpable; } public byte getId() { return getId(false); } public byte getId(boolean sharped) { byte tempId = (byte)((sharped && this.sharpable) ? (this.id + 1) : this.id); while (tempId < 0)
/*     */         tempId = (byte)(tempId + TONES_COUNT); 
/*  25 */       return (byte)(tempId % TONES_COUNT); } static  { tones = new HashMap();
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
/* 102 */       lowest = F.id;
/* 103 */       byte highest = F.id;
/* 104 */       for (Tone tone : values()) {
/* 105 */         byte id = tone.id;
/* 106 */         tones.put(Byte.valueOf(id), tone);
/* 107 */         if (id < lowest) {
/* 108 */           lowest = id;
/*     */         }
/* 110 */         if (tone.isSharpable()) {
/* 111 */           id = (byte)(id + 1);
/* 112 */           tones.put(Byte.valueOf(id), tone);
/*     */         } 
/* 114 */         if (id > highest) {
/* 115 */           highest = id;
/*     */         }
/*     */       } 
/*     */       
/* 119 */       TONES_COUNT = (byte)(highest - lowest + 1);
/* 120 */       tones.put(Byte.valueOf((byte)(TONES_COUNT - 1)), F); }
/*     */     
/*     */     public boolean isSharpable() { return this.sharpable; }
/*     */     public boolean isSharped(byte id) {
/*     */       if (id == getId(false))
/*     */         return false; 
/*     */       if (id == getId(true))
/*     */         return true; 
/*     */       throw new IllegalArgumentException("The id isn't matching to the tone.");
/*     */     }
/*     */     
/*     */     public static Tone getToneById(byte id) { return (Tone)tones.get(Byte.valueOf(id)); } }
/*     */   
/*     */   public Note(byte note) {
/* 134 */     if (note < 0 || note > 24) {
/* 135 */       throw new IllegalArgumentException("The note value has to be between 0 and 24.");
/*     */     }
/* 137 */     this.note = note;
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
/*     */   public Note(byte octave, Tone note, boolean sharped) {
/* 152 */     if (sharped && !note.isSharpable()) {
/* 153 */       throw new IllegalArgumentException("This tone could not be sharped.");
/*     */     }
/* 155 */     if (octave < 0 || octave > 2 || (octave == 2 && (note != Tone.F || !sharped))) {
/* 156 */       throw new IllegalArgumentException("Tone and octave have to be between F#0 and F#2");
/*     */     }
/* 158 */     this.note = (byte)(octave * Tone.TONES_COUNT + note.getId(sharped));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 167 */   public byte getId() { return this.note; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 176 */   public int getOctave() { return this.note / Tone.TONES_COUNT; }
/*     */ 
/*     */ 
/*     */   
/* 180 */   private byte getToneByte() { return (byte)(this.note % Tone.TONES_COUNT); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 189 */   public Tone getTone() { return Tone.getToneById(getToneByte()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSharped() {
/* 198 */     byte note = getToneByte();
/* 199 */     return Tone.getToneById(note).isSharped(note);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\Note.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */