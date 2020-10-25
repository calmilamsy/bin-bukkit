/*    */ package org.yaml.snakeyaml.util;
/*    */ 
/*    */ import java.util.ArrayList;
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
/*    */ 
/*    */ 
/*    */ public class ArrayStack<T>
/*    */   extends Object
/*    */ {
/*    */   private ArrayList<T> stack;
/*    */   
/* 25 */   public ArrayStack(int initSize) { this.stack = new ArrayList(initSize); }
/*    */ 
/*    */ 
/*    */   
/* 29 */   public void push(T obj) { this.stack.add(obj); }
/*    */ 
/*    */ 
/*    */   
/* 33 */   public T pop() { return (T)this.stack.remove(this.stack.size() - 1); }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public boolean isEmpty() { return this.stack.isEmpty(); }
/*    */ 
/*    */ 
/*    */   
/* 41 */   public void clear() { this.stack.clear(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyam\\util\ArrayStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */