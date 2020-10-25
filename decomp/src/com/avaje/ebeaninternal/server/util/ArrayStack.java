/*     */ package com.avaje.ebeaninternal.server.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.EmptyStackException;
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
/*     */ public class ArrayStack<E>
/*     */   extends Object
/*     */ {
/*     */   private final ArrayList<E> list;
/*     */   
/*  38 */   public ArrayStack(int size) { this.list = new ArrayList(size); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   public ArrayStack() { this.list = new ArrayList(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E push(E item) {
/*  52 */     this.list.add(item);
/*  53 */     return item;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E pop() {
/*  61 */     int len = this.list.size();
/*  62 */     E obj = (E)peek();
/*  63 */     this.list.remove(len - 1);
/*  64 */     return obj;
/*     */   }
/*     */   
/*     */   protected E peekZero(boolean retNull) {
/*  68 */     int len = this.list.size();
/*  69 */     if (len == 0) {
/*  70 */       if (retNull) {
/*  71 */         return null;
/*     */       }
/*  73 */       throw new EmptyStackException();
/*     */     } 
/*  75 */     return (E)this.list.get(len - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   public E peek() { return (E)peekZero(false); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public E peekWithNull() { return (E)peekZero(true); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   public boolean isEmpty() { return this.list.isEmpty(); }
/*     */ 
/*     */ 
/*     */   
/* 101 */   public int size() { return this.list.size(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\serve\\util\ArrayStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */