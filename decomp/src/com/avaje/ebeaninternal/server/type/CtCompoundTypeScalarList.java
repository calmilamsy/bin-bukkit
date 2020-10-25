/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebean.config.CompoundTypeProperty;
/*    */ import com.avaje.ebeaninternal.server.query.SplitName;
/*    */ import java.util.ArrayList;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class CtCompoundTypeScalarList
/*    */ {
/* 38 */   private final LinkedHashMap<String, ScalarType<?>> scalarProps = new LinkedHashMap();
/*    */   
/* 40 */   private final LinkedHashMap<String, CtCompoundProperty> compoundProperties = new LinkedHashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<CtCompoundProperty> getNonScalarProperties() {
/* 47 */     List<CtCompoundProperty> nonScalarProps = new ArrayList<CtCompoundProperty>();
/*    */     
/* 49 */     for (String propKey : this.compoundProperties.keySet()) {
/* 50 */       if (!this.scalarProps.containsKey(propKey)) {
/* 51 */         nonScalarProps.add(this.compoundProperties.get(propKey));
/*    */       }
/*    */     } 
/*    */     
/* 55 */     return nonScalarProps;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addCompoundProperty(String propName, CtCompoundType<?> t, CompoundTypeProperty<?, ?> prop) {
/* 63 */     CtCompoundProperty parent = null;
/* 64 */     String[] split = SplitName.split(propName);
/* 65 */     if (split[false] != null) {
/* 66 */       parent = (CtCompoundProperty)this.compoundProperties.get(split[0]);
/*    */     }
/*    */     
/* 69 */     CtCompoundProperty p = new CtCompoundProperty(propName, parent, t, prop);
/* 70 */     this.compoundProperties.put(propName, p);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 77 */   public void addScalarType(String propName, ScalarType<?> scalar) { this.scalarProps.put(propName, scalar); }
/*    */ 
/*    */ 
/*    */   
/* 81 */   public CtCompoundProperty getCompoundType(String propName) { return (CtCompoundProperty)this.compoundProperties.get(propName); }
/*    */ 
/*    */ 
/*    */   
/* 85 */   public Set<Map.Entry<String, ScalarType<?>>> entries() { return this.scalarProps.entrySet(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\CtCompoundTypeScalarList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */