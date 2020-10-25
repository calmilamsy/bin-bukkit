/*    */ package org.bukkit.craftbukkit.scheduler;
/*    */ 
/*    */ public class ObjectContainer<T>
/*    */   extends Object
/*    */ {
/*    */   T object;
/*    */   
/*  8 */   public void setObject(T object) { this.object = object; }
/*    */ 
/*    */ 
/*    */   
/* 12 */   public T getObject() { return (T)this.object; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\scheduler\ObjectContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */