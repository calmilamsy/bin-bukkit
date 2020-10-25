/*     */ package org.bukkit.craftbukkit.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.server.Chunk;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ 
/*     */ public class LongHashtable<V>
/*     */   extends LongHash {
/*   9 */   Object[][][] values = new Object[256][][];
/*  10 */   Entry cache = null;
/*     */   
/*     */   public void put(int msw, int lsw, V value) {
/*  13 */     put(toLong(msw, lsw), value);
/*  14 */     if (value instanceof Chunk) {
/*  15 */       Chunk c = (Chunk)value;
/*  16 */       if (msw != c.x || lsw != c.z) {
/*  17 */         MinecraftServer.log.info("Chunk (" + c.x + ", " + c.z + ") stored at  (" + msw + ", " + lsw + ")");
/*  18 */         Throwable x = new Throwable();
/*  19 */         x.fillInStackTrace();
/*  20 */         x.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public V get(int msw, int lsw) {
/*  26 */     V value = (V)get(toLong(msw, lsw));
/*  27 */     if (value instanceof Chunk) {
/*  28 */       Chunk c = (Chunk)value;
/*  29 */       if (msw != c.x || lsw != c.z) {
/*  30 */         MinecraftServer.log.info("Chunk (" + c.x + ", " + c.z + ") stored at  (" + msw + ", " + lsw + ")");
/*  31 */         Throwable x = new Throwable();
/*  32 */         x.fillInStackTrace();
/*  33 */         x.printStackTrace();
/*     */       } 
/*     */     } 
/*  36 */     return value;
/*     */   }
/*     */   
/*     */   public void put(long key, V value) {
/*  40 */     int mainIdx = (int)(key & 0xFFL);
/*  41 */     Object[][] outer = this.values[mainIdx];
/*  42 */     if (outer == null) this.values[mainIdx] = outer = new Object[256][];
/*     */     
/*  44 */     int outerIdx = (int)(key >> 32 & 0xFFL);
/*  45 */     Object[] inner = outer[outerIdx];
/*     */     
/*  47 */     if (inner == null) {
/*  48 */       outer[outerIdx] = inner = new Object[5];
/*  49 */       inner[0] = this.cache = new Entry(key, value);
/*     */     } else {
/*     */       int i;
/*  52 */       for (i = 0; i < inner.length; i++) {
/*  53 */         if (inner[i] == null || ((Entry)inner[i]).key == key) {
/*  54 */           inner[i] = this.cache = new Entry(key, value);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*  59 */       outer[outerIdx] = inner = Java15Compat.Arrays_copyOf(inner, i + i);
/*  60 */       inner[i] = new Entry(key, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  65 */   public V get(long key) { return (V)(containsKey(key) ? this.cache.value : null); }
/*     */ 
/*     */   
/*     */   public boolean containsKey(long key) {
/*  69 */     if (this.cache != null && this.cache.key == key) return true;
/*     */     
/*  71 */     int outerIdx = (int)(key >> 32 & 0xFFL);
/*  72 */     Object[][] outer = this.values[(int)(key & 0xFFL)];
/*  73 */     if (outer == null) return false;
/*     */     
/*  75 */     Object[] inner = outer[outerIdx];
/*  76 */     if (inner == null) return false;
/*     */     
/*  78 */     for (int i = 0; i < inner.length; i++) {
/*  79 */       Entry e = (Entry)inner[i];
/*  80 */       if (e == null)
/*  81 */         return false; 
/*  82 */       if (e.key == key) {
/*  83 */         this.cache = e;
/*  84 */         return true;
/*     */       } 
/*     */     } 
/*  87 */     return false;
/*     */   }
/*     */   
/*     */   public void remove(long key) {
/*  91 */     Object[][] outer = this.values[(int)(key & 0xFFL)];
/*  92 */     if (outer == null)
/*     */       return; 
/*  94 */     Object[] inner = outer[(int)(key >> 32 & 0xFFL)];
/*  95 */     if (inner == null)
/*     */       return; 
/*  97 */     for (int i = 0; i < inner.length; i++) {
/*  98 */       if (inner[i] != null)
/*     */       {
/* 100 */         if (((Entry)inner[i]).key == key) {
/* 101 */           for (; ++i < inner.length && 
/* 102 */             inner[i] != null; i++) {
/* 103 */             inner[i - 1] = inner[i];
/*     */           }
/*     */           
/* 106 */           inner[i - 1] = null;
/* 107 */           this.cache = null;
/*     */           return;
/*     */         }  } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public ArrayList<V> values() {
/* 114 */     ArrayList<V> ret = new ArrayList<V>();
/*     */     
/* 116 */     for (Object[][] outer : this.values) {
/* 117 */       if (outer != null)
/*     */       {
/* 119 */         for (Object[] inner : outer) {
/* 120 */           if (inner != null)
/*     */           {
/* 122 */             for (Object entry : inner) {
/* 123 */               if (entry == null)
/*     */                 break; 
/* 125 */               ret.add(((Entry)entry).value);
/*     */             }  } 
/*     */         }  } 
/*     */     } 
/* 129 */     return ret;
/*     */   }
/*     */   
/*     */   private class Entry {
/*     */     long key;
/*     */     Object value;
/*     */     
/*     */     Entry(long k, Object v) {
/* 137 */       this.key = k;
/* 138 */       this.value = v;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukki\\util\LongHashtable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */