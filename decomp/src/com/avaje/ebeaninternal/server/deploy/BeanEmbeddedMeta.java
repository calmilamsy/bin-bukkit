/*    */ package com.avaje.ebeaninternal.server.deploy;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BeanEmbeddedMeta
/*    */ {
/*    */   final BeanProperty[] properties;
/*    */   
/* 28 */   public BeanEmbeddedMeta(BeanProperty[] properties) { this.properties = properties; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 35 */   public BeanProperty[] getProperties() { return this.properties; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isEmbeddedVersion() {
/* 42 */     for (int i = 0; i < this.properties.length; i++) {
/* 43 */       if (this.properties[i].isVersion()) {
/* 44 */         return true;
/*    */       }
/*    */     } 
/* 47 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\BeanEmbeddedMeta.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */