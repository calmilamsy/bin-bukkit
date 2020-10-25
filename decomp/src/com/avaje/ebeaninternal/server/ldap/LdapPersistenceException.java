/*    */ package com.avaje.ebeaninternal.server.ldap;
/*    */ 
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
/*    */ public class LdapPersistenceException
/*    */   extends PersistenceException
/*    */ {
/*    */   private static final long serialVersionUID = -3170359404117927668L;
/*    */   
/* 29 */   public LdapPersistenceException(Throwable e) { super(e); }
/*    */ 
/*    */ 
/*    */   
/* 33 */   public LdapPersistenceException(String msg, Throwable e) { super(msg, e); }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public LdapPersistenceException(String msg) { super(msg); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\ldap\LdapPersistenceException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */