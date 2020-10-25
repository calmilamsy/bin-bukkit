/*    */ package com.avaje.ebeaninternal.api;
/*    */ 
/*    */ import com.avaje.ebean.Ebean;
/*    */ import com.avaje.ebean.EbeanServer;
/*    */ import com.avaje.ebean.TxScope;
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
/*    */ public class HelpScopeTrans
/*    */ {
/*    */   public static ScopeTrans createScopeTrans(TxScope txScope) {
/* 36 */     EbeanServer server = Ebean.getServer(txScope.getServerName());
/* 37 */     SpiEbeanServer iserver = (SpiEbeanServer)server;
/* 38 */     return iserver.createScopeTrans(txScope);
/*    */   }
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
/* 53 */   public static void onExitScopeTrans(Object returnOrThrowable, int opCode, ScopeTrans scopeTrans) { scopeTrans.onExit(returnOrThrowable, opCode); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\api\HelpScopeTrans.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */