/*    */ package com.avaje.ebean.config.ldap;
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
/*    */ 
/*    */ public class LdapConfig
/*    */ {
/*    */   private LdapContextFactory contextFactory;
/*    */   private boolean vanillaMode;
/*    */   
/* 38 */   public LdapContextFactory getContextFactory() { return this.contextFactory; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 45 */   public void setContextFactory(LdapContextFactory contextFactory) { this.contextFactory = contextFactory; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 52 */   public boolean isVanillaMode() { return this.vanillaMode; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 59 */   public void setVanillaMode(boolean vanillaMode) { this.vanillaMode = vanillaMode; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\ldap\LdapConfig.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */