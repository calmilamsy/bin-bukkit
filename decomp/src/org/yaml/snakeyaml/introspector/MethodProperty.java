/*    */ package org.yaml.snakeyaml.introspector;
/*    */ 
/*    */ import java.beans.PropertyDescriptor;
/*    */ import org.yaml.snakeyaml.error.YAMLException;
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
/*    */ 
/*    */ public class MethodProperty
/*    */   extends GenericProperty
/*    */ {
/*    */   private final PropertyDescriptor property;
/*    */   private final boolean readable;
/*    */   private final boolean writable;
/*    */   
/*    */   public MethodProperty(PropertyDescriptor property) {
/* 30 */     super(property.getName(), property.getPropertyType(), (property.getReadMethod() == null) ? null : property.getReadMethod().getGenericReturnType());
/* 31 */     this.property = property;
/* 32 */     this.readable = (property.getReadMethod() != null);
/* 33 */     this.writable = (property.getWriteMethod() != null);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 38 */   public void set(Object object, Object value) throws Exception { this.property.getWriteMethod().invoke(object, new Object[] { value }); }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object get(Object object) {
/*    */     try {
/* 44 */       this.property.getReadMethod().setAccessible(true);
/* 45 */       return this.property.getReadMethod().invoke(object, new Object[0]);
/* 46 */     } catch (Exception e) {
/* 47 */       throw new YAMLException("Unable to find getter for property '" + this.property.getName() + "' on object " + object + ":" + e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 54 */   public boolean isWritable() { return this.writable; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 59 */   public boolean isReadable() { return this.readable; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\introspector\MethodProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */