/*    */ package com.avaje.ebeaninternal.server.core;
/*    */ 
/*    */ import com.avaje.ebean.config.ServerConfig;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConfigBuilder
/*    */ {
/*    */   public ServerConfig build(String serverName) {
/* 16 */     ServerConfig config = new ServerConfig();
/* 17 */     config.setName(serverName);
/*    */     
/* 19 */     config.loadFromProperties();
/*    */     
/* 21 */     return config;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\ConfigBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */