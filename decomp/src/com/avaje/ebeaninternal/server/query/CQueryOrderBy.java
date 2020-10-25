/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import com.avaje.ebean.OrderBy;
/*    */ import com.avaje.ebeaninternal.api.SpiQuery;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;
/*    */ import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
/*    */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CQueryOrderBy
/*    */ {
/*    */   private final BeanDescriptor<?> desc;
/*    */   private final SpiQuery<?> query;
/*    */   
/* 46 */   public static String parse(BeanDescriptor<?> desc, SpiQuery<?> query) { return (new CQueryOrderBy(desc, query)).parseInternal(); }
/*    */ 
/*    */   
/*    */   private CQueryOrderBy(BeanDescriptor<?> desc, SpiQuery<?> query) {
/* 50 */     this.desc = desc;
/* 51 */     this.query = query;
/*    */   }
/*    */ 
/*    */   
/*    */   private String parseInternal() {
/* 56 */     OrderBy<?> orderBy = this.query.getOrderBy();
/* 57 */     if (orderBy == null) {
/* 58 */       return null;
/*    */     }
/*    */     
/* 61 */     StringBuilder sb = new StringBuilder();
/*    */     
/* 63 */     List<OrderBy.Property> properties = orderBy.getProperties();
/* 64 */     if (properties.isEmpty())
/*    */     {
/* 66 */       return null;
/*    */     }
/* 68 */     for (int i = 0; i < properties.size(); i++) {
/* 69 */       if (i > 0) {
/* 70 */         sb.append(", ");
/*    */       }
/* 72 */       OrderBy.Property p = (OrderBy.Property)properties.get(i);
/* 73 */       String expression = parseProperty(p);
/* 74 */       sb.append(expression);
/*    */     } 
/* 76 */     return sb.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   private String parseProperty(OrderBy.Property p) {
/* 81 */     String propName = p.getProperty();
/* 82 */     ElPropertyValue el = this.desc.getElGetValue(propName);
/* 83 */     if (el == null) {
/* 84 */       return p.toStringFormat();
/*    */     }
/*    */     
/* 87 */     BeanProperty beanProperty = el.getBeanProperty();
/* 88 */     if (beanProperty instanceof BeanPropertyAssoc) {
/* 89 */       BeanPropertyAssoc<?> ap = (BeanPropertyAssoc)beanProperty;
/* 90 */       IdBinder idBinder = ap.getTargetDescriptor().getIdBinder();
/* 91 */       return idBinder.getOrderBy(el.getElName(), p.isAscending());
/*    */     } 
/*    */     
/* 94 */     return p.toStringFormat();
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\CQueryOrderBy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */