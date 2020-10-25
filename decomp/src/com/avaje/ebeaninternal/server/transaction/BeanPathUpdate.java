/*    */ package com.avaje.ebeaninternal.server.transaction;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import java.io.Serializable;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
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
/*    */ public class BeanPathUpdate
/*    */ {
/* 30 */   private final Map<String, BeanPathUpdateIds> map = new LinkedHashMap();
/*    */ 
/*    */   
/*    */   public void add(BeanDescriptor<?> desc, String path, Object id) {
/* 34 */     String key = desc.getFullName() + ":" + path;
/* 35 */     BeanPathUpdateIds pathIds = (BeanPathUpdateIds)this.map.get(key);
/* 36 */     if (pathIds == null) {
/* 37 */       pathIds = new BeanPathUpdateIds(desc, path);
/* 38 */       this.map.put(key, pathIds);
/*    */     } 
/* 40 */     pathIds.addId((Serializable)id);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\transaction\BeanPathUpdate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */