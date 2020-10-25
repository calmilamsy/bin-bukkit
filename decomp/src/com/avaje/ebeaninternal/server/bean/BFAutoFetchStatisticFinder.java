/*    */ package com.avaje.ebeaninternal.server.bean;
/*    */ 
/*    */ import com.avaje.ebean.Query;
/*    */ import com.avaje.ebean.bean.BeanCollection;
/*    */ import com.avaje.ebean.common.BeanList;
/*    */ import com.avaje.ebean.event.BeanFinder;
/*    */ import com.avaje.ebean.event.BeanQueryRequest;
/*    */ import com.avaje.ebean.meta.MetaAutoFetchStatistic;
/*    */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*    */ import com.avaje.ebeaninternal.api.SpiQuery;
/*    */ import com.avaje.ebeaninternal.server.autofetch.AutoFetchManager;
/*    */ import com.avaje.ebeaninternal.server.autofetch.Statistics;
/*    */ import java.util.Iterator;
/*    */ import javax.persistence.PersistenceException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BFAutoFetchStatisticFinder
/*    */   extends Object
/*    */   implements BeanFinder<MetaAutoFetchStatistic>
/*    */ {
/*    */   public MetaAutoFetchStatistic find(BeanQueryRequest<MetaAutoFetchStatistic> request) {
/* 29 */     SpiQuery<MetaAutoFetchStatistic> query = (SpiQuery)request.getQuery();
/*    */     try {
/* 31 */       String queryPointKey = (String)query.getId();
/*    */       
/* 33 */       SpiEbeanServer server = (SpiEbeanServer)request.getEbeanServer();
/* 34 */       AutoFetchManager manager = server.getAutoFetchManager();
/*    */       
/* 36 */       Statistics stats = manager.getStatistics(queryPointKey);
/* 37 */       if (stats != null) {
/* 38 */         return stats.createPublicMeta();
/*    */       }
/* 40 */       return null;
/*    */     
/*    */     }
/* 43 */     catch (Exception e) {
/* 44 */       throw new PersistenceException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BeanCollection<MetaAutoFetchStatistic> findMany(BeanQueryRequest<MetaAutoFetchStatistic> request) {
/* 53 */     Query.Type queryType = request.getQuery().getType();
/* 54 */     if (!queryType.equals(Query.Type.LIST)) {
/* 55 */       throw new PersistenceException("Only findList() supported at this stage.");
/*    */     }
/*    */     
/* 58 */     SpiEbeanServer server = (SpiEbeanServer)request.getEbeanServer();
/* 59 */     AutoFetchManager manager = server.getAutoFetchManager();
/*    */     
/* 61 */     BeanList<MetaAutoFetchStatistic> list = new BeanList<MetaAutoFetchStatistic>();
/*    */     
/* 63 */     Iterator<Statistics> it = manager.iterateStatistics();
/* 64 */     while (it.hasNext()) {
/* 65 */       Statistics stats = (Statistics)it.next();
/*    */       
/* 67 */       list.add(stats.createPublicMeta());
/*    */     } 
/*    */     
/* 70 */     String orderBy = request.getQuery().order().toStringFormat();
/* 71 */     if (orderBy == null) {
/* 72 */       orderBy = "beanType";
/*    */     }
/* 74 */     server.sort(list, orderBy);
/*    */ 
/*    */     
/* 77 */     return list;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\bean\BFAutoFetchStatisticFinder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */