/*    */ package com.avaje.ebean.config.dbplatform;
/*    */ 
/*    */ import com.avaje.ebean.BackgroundExecutor;
/*    */ import com.avaje.ebean.config.GlobalProperties;
/*    */ import javax.sql.DataSource;
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
/*    */ public class H2Platform
/*    */   extends DatabasePlatform
/*    */ {
/*    */   public H2Platform() {
/* 39 */     boolean useIdentity = GlobalProperties.getBoolean("ebean.h2platform.useIdentity", false);
/*    */     
/* 41 */     IdType idType = useIdentity ? IdType.IDENTITY : IdType.SEQUENCE;
/* 42 */     this.dbIdentity.setIdType(idType);
/*    */     
/* 44 */     this.dbIdentity.setSupportsGetGeneratedKeys(true);
/* 45 */     this.dbIdentity.setSupportsSequence(true);
/* 46 */     this.dbIdentity.setSupportsIdentity(true);
/*    */     
/* 48 */     this.openQuote = "\"";
/* 49 */     this.closeQuote = "\"";
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 54 */     this.dbDdlSyntax.setDropIfExists("if exists");
/* 55 */     this.dbDdlSyntax.setDisableReferentialIntegrity("SET REFERENTIAL_INTEGRITY FALSE");
/* 56 */     this.dbDdlSyntax.setEnableReferentialIntegrity("SET REFERENTIAL_INTEGRITY TRUE");
/* 57 */     this.dbDdlSyntax.setForeignKeySuffix("on delete restrict on update restrict");
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
/* 68 */   public IdGenerator createSequenceIdGenerator(BackgroundExecutor be, DataSource ds, String seqName, int batchSize) { return new H2SequenceIdGenerator(be, ds, seqName, batchSize); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\dbplatform\H2Platform.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */