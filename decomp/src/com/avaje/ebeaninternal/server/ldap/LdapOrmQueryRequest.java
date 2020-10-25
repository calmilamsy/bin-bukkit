/*    */ package com.avaje.ebeaninternal.server.ldap;
/*    */ 
/*    */ import com.avaje.ebean.QueryIterator;
/*    */ import com.avaje.ebean.QueryResultVisitor;
/*    */ import com.avaje.ebean.bean.BeanCollection;
/*    */ import com.avaje.ebeaninternal.api.SpiQuery;
/*    */ import com.avaje.ebeaninternal.server.core.SpiOrmQueryRequest;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
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
/*    */ public class LdapOrmQueryRequest<T>
/*    */   extends Object
/*    */   implements SpiOrmQueryRequest<T>
/*    */ {
/*    */   private final SpiQuery<T> query;
/*    */   private final BeanDescriptor<T> desc;
/*    */   private final LdapOrmQueryEngine queryEngine;
/*    */   
/*    */   public LdapOrmQueryRequest(SpiQuery<T> query, BeanDescriptor<T> desc, LdapOrmQueryEngine queryEngine) {
/* 40 */     this.query = query;
/* 41 */     this.desc = desc;
/* 42 */     this.queryEngine = queryEngine;
/*    */   }
/*    */ 
/*    */   
/* 46 */   public BeanDescriptor<T> getBeanDescriptor() { return this.desc; }
/*    */ 
/*    */ 
/*    */   
/* 50 */   public SpiQuery<T> getQuery() { return this.query; }
/*    */ 
/*    */ 
/*    */   
/* 54 */   public Object findId() { return this.queryEngine.findId(this); }
/*    */ 
/*    */ 
/*    */   
/* 58 */   public List<Object> findIds() { throw new RuntimeException("Not Implemented yet"); }
/*    */ 
/*    */ 
/*    */   
/* 62 */   public List<T> findList() { return this.queryEngine.findList(this); }
/*    */ 
/*    */ 
/*    */   
/* 66 */   public void findVisit(QueryResultVisitor<T> visitor) { throw new RuntimeException("Not Implemented yet"); }
/*    */ 
/*    */ 
/*    */   
/* 70 */   public QueryIterator<T> findIterate() { throw new RuntimeException("Not Implemented yet"); }
/*    */ 
/*    */ 
/*    */   
/* 74 */   public Map<?, ?> findMap() { throw new RuntimeException("Not Implemented yet"); }
/*    */ 
/*    */ 
/*    */   
/* 78 */   public int findRowCount() { throw new RuntimeException("Not Implemented yet"); }
/*    */ 
/*    */ 
/*    */   
/* 82 */   public Set<?> findSet() { throw new RuntimeException("Not Implemented yet"); }
/*    */ 
/*    */ 
/*    */   
/* 86 */   public T getFromPersistenceContextOrCache() { return null; }
/*    */ 
/*    */ 
/*    */   
/* 90 */   public BeanCollection<T> getFromQueryCache() { return null; }
/*    */   
/*    */   public void initTransIfRequired() {}
/*    */   
/*    */   public void rollbackTransIfRequired() {}
/*    */   
/*    */   public void endTransIfRequired() {}
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\ldap\LdapOrmQueryRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */