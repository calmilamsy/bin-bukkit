/*     */ package com.avaje.ebeaninternal.server.lib.sql;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ class FreeConnectionBuffer
/*     */ {
/*     */   private PooledConnection[] conns;
/*     */   private int removeIndex;
/*     */   private int addIndex;
/*     */   private int size;
/*     */   
/*  51 */   protected FreeConnectionBuffer(int capacity) { this.conns = new PooledConnection[capacity]; }
/*     */ 
/*     */ 
/*     */   
/*  55 */   protected int getCapacity() { return this.conns.length; }
/*     */ 
/*     */ 
/*     */   
/*  59 */   protected int size() { return this.size; }
/*     */ 
/*     */ 
/*     */   
/*  63 */   protected boolean isEmpty() { return (this.size == 0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void add(PooledConnection pc) {
/*  70 */     this.conns[this.addIndex] = pc;
/*  71 */     this.addIndex = inc(this.addIndex);
/*  72 */     this.size++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PooledConnection remove() {
/*  79 */     PooledConnection[] items = this.conns;
/*  80 */     PooledConnection pc = items[this.removeIndex];
/*  81 */     items[this.removeIndex] = null;
/*  82 */     this.removeIndex = inc(this.removeIndex);
/*  83 */     this.size--;
/*  84 */     return pc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<PooledConnection> getShallowCopy() {
/*  92 */     List<PooledConnection> copy = new ArrayList<PooledConnection>(this.conns.length);
/*  93 */     for (int i = 0; i < this.conns.length; i++) {
/*  94 */       if (this.conns[i] != null) {
/*  95 */         copy.add(this.conns[i]);
/*     */       }
/*     */     } 
/*  98 */     return copy;
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
/*     */   protected void setShallowCopy(List<PooledConnection> copy) {
/* 112 */     this.removeIndex = 0;
/* 113 */     this.addIndex = 0;
/* 114 */     this.size = 0;
/*     */ 
/*     */     
/* 117 */     for (i = 0; i < this.conns.length; i++) {
/* 118 */       this.conns[i] = null;
/*     */     }
/*     */ 
/*     */     
/* 122 */     for (int i = 0; i < copy.size(); i++) {
/* 123 */       add((PooledConnection)copy.get(i));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setCapacity(int newCapacity) {
/* 132 */     if (newCapacity > this.conns.length) {
/*     */       
/* 134 */       List<PooledConnection> copy = getShallowCopy();
/*     */ 
/*     */       
/* 137 */       this.removeIndex = 0;
/* 138 */       this.addIndex = 0;
/* 139 */       this.size = 0;
/*     */       
/* 141 */       this.conns = new PooledConnection[newCapacity];
/*     */ 
/*     */       
/* 144 */       for (int i = 0; i < copy.size(); i++) {
/* 145 */         add((PooledConnection)copy.get(i));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 154 */   private final int inc(int i) { return (++i == this.conns.length) ? 0 : i; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\sql\FreeConnectionBuffer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */