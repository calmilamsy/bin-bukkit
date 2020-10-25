/*    */ package com.avaje.ebeaninternal.server.bean;
/*    */ 
/*    */ import com.avaje.ebean.Query;
/*    */ import com.avaje.ebean.bean.BeanCollection;
/*    */ import com.avaje.ebean.common.BeanList;
/*    */ import com.avaje.ebean.event.BeanFinder;
/*    */ import com.avaje.ebean.event.BeanQueryRequest;
/*    */ import com.avaje.ebean.meta.MetaQueryStatistic;
/*    */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.query.CQueryPlan;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import javax.persistence.PersistenceException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BFQueryStatisticFinder
/*    */   extends Object
/*    */   implements BeanFinder<MetaQueryStatistic>
/*    */ {
/* 25 */   public MetaQueryStatistic find(BeanQueryRequest<MetaQueryStatistic> request) { throw new RuntimeException("Not Supported yet"); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BeanCollection<MetaQueryStatistic> findMany(BeanQueryRequest<MetaQueryStatistic> request) {
/* 33 */     Query.Type queryType = request.getQuery().getType();
/* 34 */     if (!queryType.equals(Query.Type.LIST)) {
/* 35 */       throw new PersistenceException("Only findList() supported at this stage.");
/*    */     }
/*    */     
/* 38 */     BeanList<MetaQueryStatistic> list = new BeanList<MetaQueryStatistic>();
/*    */     
/* 40 */     SpiEbeanServer server = (SpiEbeanServer)request.getEbeanServer();
/* 41 */     build(list, server);
/*    */     
/* 43 */     String orderBy = request.getQuery().order().toStringFormat();
/* 44 */     if (orderBy == null) {
/* 45 */       orderBy = "beanType, origQueryPlanHash, autofetchTuned";
/*    */     }
/* 47 */     server.sort(list, orderBy);
/*    */     
/* 49 */     return list;
/*    */   }
/*    */ 
/*    */   
/*    */   private void build(List<MetaQueryStatistic> list, SpiEbeanServer server) {
/* 54 */     for (BeanDescriptor<?> desc : server.getBeanDescriptors()) {
/* 55 */       desc.clearQueryStatistics();
/* 56 */       build(list, desc);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void build(List<MetaQueryStatistic> list, BeanDescriptor<?> desc) {
/* 62 */     Iterator<CQueryPlan> it = desc.queryPlans();
/* 63 */     while (it.hasNext()) {
/* 64 */       CQueryPlan queryPlan = (CQueryPlan)it.next();
/* 65 */       list.add(queryPlan.createMetaQueryStatistic(desc.getFullName()));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\bean\BFQueryStatisticFinder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */