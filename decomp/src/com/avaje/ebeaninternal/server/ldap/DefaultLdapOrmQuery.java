/*    */ package com.avaje.ebeaninternal.server.ldap;
/*    */ 
/*    */ import com.avaje.ebean.EbeanServer;
/*    */ import com.avaje.ebean.ExpressionFactory;
/*    */ import com.avaje.ebeaninternal.server.querydefn.DefaultOrmQuery;
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
/*    */ public class DefaultLdapOrmQuery<T>
/*    */   extends DefaultOrmQuery<T>
/*    */ {
/*    */   private static final long serialVersionUID = -4344629258591773124L;
/*    */   
/* 31 */   public DefaultLdapOrmQuery(Class<T> beanType, EbeanServer server, ExpressionFactory expressionFactory, String query) { super(beanType, server, expressionFactory, query); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\ldap\DefaultLdapOrmQuery.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */