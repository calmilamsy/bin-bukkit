/*     */ package org.ibex.nestedvm.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InodeCache
/*     */ {
/*  10 */   private static final Object PLACEHOLDER = new Object();
/*     */   
/*     */   private static final short SHORT_PLACEHOLDER = -2;
/*     */   private static final short SHORT_NULL = -1;
/*     */   private static final int LOAD_FACTOR = 2;
/*     */   private final int maxSize;
/*     */   private final int totalSlots;
/*     */   private final int maxUsedSlots;
/*     */   private final Object[] keys;
/*     */   private final short[] next;
/*     */   private final short[] prev;
/*     */   private final short[] inodes;
/*     */   private final short[] reverse;
/*     */   private int size;
/*     */   private int usedSlots;
/*     */   private short mru;
/*     */   private short lru;
/*     */   
/*  28 */   public InodeCache() { this(1024); }
/*     */   public InodeCache(int paramInt) {
/*  30 */     this.maxSize = paramInt;
/*  31 */     this.totalSlots = paramInt * 2 * 2 + 3;
/*  32 */     this.maxUsedSlots = this.totalSlots / 2;
/*  33 */     if (this.totalSlots > 32767) throw new IllegalArgumentException("cache size too large"); 
/*  34 */     this.keys = new Object[this.totalSlots];
/*  35 */     this.next = new short[this.totalSlots];
/*  36 */     this.prev = new short[this.totalSlots];
/*  37 */     this.inodes = new short[this.totalSlots];
/*  38 */     this.reverse = new short[this.totalSlots];
/*  39 */     clear();
/*     */   }
/*     */   
/*  42 */   private static void fill(Object[] paramArrayOfObject, Object paramObject) { for (byte b = 0; b < paramArrayOfObject.length; ) { paramArrayOfObject[b] = paramObject; b++; }
/*  43 */      } private static void fill(short[] paramArrayOfShort, short paramShort) { for (byte b = 0; b < paramArrayOfShort.length; ) { paramArrayOfShort[b] = paramShort; b++; }
/*     */      } public final void clear() {
/*  45 */     this.size = this.usedSlots = 0;
/*  46 */     this.mru = this.lru = -1;
/*  47 */     fill(this.keys, null);
/*  48 */     fill(this.inodes, (short)-1);
/*  49 */     fill(this.reverse, (short)-1);
/*     */   }
/*     */   
/*     */   public final short get(Object paramObject) {
/*  53 */     int n, i = paramObject.hashCode() & 0x7FFFFFFF;
/*  54 */     int j = i % this.totalSlots;
/*  55 */     int k = j;
/*  56 */     byte b = 1;
/*  57 */     boolean bool = true;
/*     */     
/*  59 */     int m = -1;
/*     */     Object object;
/*  61 */     while ((object = this.keys[j]) != null) {
/*  62 */       if (object == PLACEHOLDER) {
/*  63 */         if (m == -1) m = j; 
/*  64 */       } else if (object.equals(paramObject)) {
/*  65 */         n = this.inodes[j];
/*  66 */         if (j == this.mru) return n; 
/*  67 */         if (this.lru == j) {
/*  68 */           this.lru = this.next[this.lru];
/*     */         } else {
/*  70 */           short s1 = this.prev[j];
/*  71 */           short s2 = this.next[j];
/*  72 */           this.next[s1] = s2;
/*  73 */           this.prev[s2] = s1;
/*     */         } 
/*  75 */         this.prev[j] = this.mru;
/*  76 */         this.next[this.mru] = (short)j;
/*  77 */         this.mru = (short)j;
/*  78 */         return n;
/*     */       } 
/*  80 */       j = Math.abs((k + (bool ? 1 : -1) * b * b) % this.totalSlots);
/*  81 */       if (!bool) b++; 
/*  82 */       bool = !bool ? 1 : 0;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  87 */     if (m == -1) {
/*     */       
/*  89 */       n = j;
/*  90 */       if (this.usedSlots == this.maxUsedSlots) {
/*  91 */         clear();
/*  92 */         return get(paramObject);
/*     */       } 
/*  94 */       this.usedSlots++;
/*     */     } else {
/*     */       
/*  97 */       n = m;
/*     */     } 
/*     */     
/* 100 */     if (this.size == this.maxSize) {
/*     */       
/* 102 */       this.keys[this.lru] = PLACEHOLDER;
/* 103 */       this.inodes[this.lru] = -2;
/* 104 */       this.lru = this.next[this.lru];
/*     */     } else {
/* 106 */       if (this.size == 0) this.lru = (short)n; 
/* 107 */       this.size++;
/*     */     } 
/*     */ 
/*     */     
/* 111 */     int i1 = i & 0x7FFF; while (true) {
/* 112 */       j = i1 % this.totalSlots;
/* 113 */       k = j;
/* 114 */       b = 1;
/* 115 */       bool = true;
/* 116 */       m = -1;
/*     */       short s;
/* 118 */       while ((s = this.reverse[j]) != -1) {
/* 119 */         short s1 = this.inodes[s];
/* 120 */         if (s1 == -2) {
/* 121 */           if (m == -1) m = j; 
/* 122 */         } else if (s1 == i1) {
/*     */           i1++; continue;
/*     */         } 
/* 125 */         j = Math.abs((k + (bool ? 1 : -1) * b * b) % this.totalSlots);
/* 126 */         if (!bool) b++; 
/* 127 */         bool = !bool ? 1 : 0;
/*     */       } 
/*     */       
/* 130 */       if (m != -1) j = m; 
/*     */       break;
/*     */     } 
/* 133 */     this.keys[n] = paramObject;
/* 134 */     this.reverse[j] = (short)n;
/* 135 */     this.inodes[n] = (short)i1;
/* 136 */     if (this.mru != -1) {
/* 137 */       this.prev[n] = this.mru;
/* 138 */       this.next[this.mru] = (short)n;
/*     */     } 
/* 140 */     this.mru = (short)n;
/* 141 */     return (short)i1;
/*     */   }
/*     */   
/*     */   public Object reverse(short paramShort) {
/* 145 */     short s1 = paramShort % this.totalSlots;
/* 146 */     short s = s1;
/* 147 */     byte b = 1;
/* 148 */     boolean bool = true;
/*     */     short s2;
/* 150 */     while ((s2 = this.reverse[s1]) != -1) {
/* 151 */       if (this.inodes[s2] == paramShort) return this.keys[s2]; 
/* 152 */       int i = Math.abs((s + (bool ? 1 : -1) * b * b) % this.totalSlots);
/* 153 */       if (!bool) b++; 
/* 154 */       bool = !bool ? 1 : 0;
/*     */     } 
/* 156 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\ibex\nestedv\\util\InodeCache.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.0.4
 */