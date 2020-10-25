/*     */ package com.avaje.ebeaninternal.server.lib.sql;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
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
/*     */ class BusyConnectionBuffer
/*     */ {
/*     */   private PooledConnection[] slots;
/*     */   private int growBy;
/*     */   private int size;
/*     */   private int pos;
/*     */   
/*     */   protected BusyConnectionBuffer(int capacity, int growBy) {
/*  49 */     this.pos = -1;
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
/*  61 */     this.slots = new PooledConnection[capacity];
/*  62 */     this.growBy = growBy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setCapacity(int newCapacity) {
/*  69 */     if (newCapacity > this.slots.length) {
/*  70 */       PooledConnection[] current = this.slots;
/*  71 */       this.slots = new PooledConnection[newCapacity];
/*  72 */       System.arraycopy(current, 0, this.slots, 0, current.length);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  77 */   public String toString() { return Arrays.toString(this.slots); }
/*     */ 
/*     */ 
/*     */   
/*  81 */   protected int getCapacity() { return this.slots.length; }
/*     */ 
/*     */ 
/*     */   
/*  85 */   protected int size() { return this.size; }
/*     */ 
/*     */ 
/*     */   
/*  89 */   protected boolean isEmpty() { return (this.size == 0); }
/*     */ 
/*     */   
/*     */   protected int add(PooledConnection pc) {
/*  93 */     if (this.size == this.slots.length)
/*     */     {
/*  95 */       setCapacity(this.slots.length + this.growBy);
/*     */     }
/*  97 */     this.size++;
/*  98 */     int slot = nextEmptySlot();
/*  99 */     pc.setSlotId(slot);
/* 100 */     this.slots[slot] = pc;
/* 101 */     return this.size;
/*     */   }
/*     */   
/*     */   protected boolean remove(PooledConnection pc) {
/* 105 */     this.size--;
/* 106 */     int slotId = pc.getSlotId();
/* 107 */     if (this.slots[slotId] != pc) {
/* 108 */       return false;
/*     */     }
/* 110 */     this.slots[slotId] = null;
/* 111 */     return true;
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
/*     */   protected List<PooledConnection> getShallowCopy() {
/* 123 */     ArrayList<PooledConnection> tmp = new ArrayList<PooledConnection>();
/* 124 */     for (int i = 0; i < this.slots.length; i++) {
/* 125 */       if (this.slots[i] != null) {
/* 126 */         tmp.add(this.slots[i]);
/*     */       }
/*     */     } 
/* 129 */     return Collections.unmodifiableList(tmp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int nextEmptySlot() {
/* 138 */     while (++this.pos < this.slots.length) {
/* 139 */       if (this.slots[this.pos] == null) {
/* 140 */         return this.pos;
/*     */       }
/*     */     } 
/*     */     
/* 144 */     this.pos = -1;
/* 145 */     while (++this.pos < this.slots.length) {
/* 146 */       if (this.slots[this.pos] == null) {
/* 147 */         return this.pos;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 152 */     throw new RuntimeException("No Empty Slot Found?");
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\sql\BusyConnectionBuffer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */