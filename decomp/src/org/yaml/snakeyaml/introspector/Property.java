/*    */ package org.yaml.snakeyaml.introspector;
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
/*    */ public abstract class Property
/*    */   extends Object
/*    */   implements Comparable<Property>
/*    */ {
/*    */   private final String name;
/*    */   private final Class<?> type;
/*    */   
/*    */   public Property(String name, Class<?> type) {
/* 25 */     this.name = name;
/* 26 */     this.type = type;
/*    */   }
/*    */ 
/*    */   
/* 30 */   public Class<?> getType() { return this.type; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 36 */   public String getName() { return this.name; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   public String toString() { return getName() + " of " + getType(); }
/*    */ 
/*    */ 
/*    */   
/* 45 */   public int compareTo(Property o) { return this.name.compareTo(o.name); }
/*    */ 
/*    */ 
/*    */   
/* 49 */   public boolean isWritable() { return true; }
/*    */ 
/*    */ 
/*    */   
/* 53 */   public boolean isReadable() { return true; }
/*    */   
/*    */   public abstract Class<?>[] getActualTypeArguments();
/*    */   
/*    */   public abstract void set(Object paramObject1, Object paramObject2) throws Exception;
/*    */   
/*    */   public abstract Object get(Object paramObject);
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\introspector\Property.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */