/*    */ package com.avaje.ebeaninternal.server.ddl;
/*    */ 
/*    */ import com.avaje.ebean.config.dbplatform.DbDdlSyntax;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DropSequenceVisitor
/*    */   implements BeanVisitor
/*    */ {
/* 14 */   private static final Logger logger = Logger.getLogger(DropSequenceVisitor.class.getName());
/*    */   
/*    */   private final DdlGenContext ctx;
/*    */   
/*    */   private final DbDdlSyntax ddlSyntax;
/*    */   
/*    */   private final boolean supportsSequence;
/*    */   
/*    */   public DropSequenceVisitor(DdlGenContext ctx) {
/* 23 */     this.ctx = ctx;
/* 24 */     this.ddlSyntax = ctx.getDdlSyntax();
/* 25 */     this.supportsSequence = ctx.getDbPlatform().getDbIdentity().isSupportsSequence();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean visitBean(BeanDescriptor<?> descriptor) {
/* 31 */     if (!descriptor.isInheritanceRoot()) {
/* 32 */       return false;
/*    */     }
/* 34 */     if (descriptor.getSequenceName() != null) {
/*    */       
/* 36 */       if (!this.supportsSequence) {
/*    */         
/* 38 */         String msg = "Not dropping sequence " + descriptor.getSequenceName() + " on Bean " + descriptor.getName() + " as DatabasePlatform does not support sequences";
/*    */         
/* 40 */         logger.finer(msg);
/* 41 */         return false;
/*    */       } 
/*    */       
/* 44 */       this.ctx.write("drop sequence ");
/* 45 */       if (this.ddlSyntax.getDropIfExists() != null) {
/* 46 */         this.ctx.write(this.ddlSyntax.getDropIfExists()).write(" ");
/*    */       }
/* 48 */       this.ctx.write(descriptor.getSequenceName());
/* 49 */       this.ctx.write(";").writeNewLine().writeNewLine();
/*    */     } 
/* 51 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void visitBeanEnd(BeanDescriptor<?> descriptor) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void visitBegin() {}
/*    */ 
/*    */   
/*    */   public void visitEnd() {}
/*    */ 
/*    */   
/* 66 */   public PropertyVisitor visitProperty(BeanProperty p) { return null; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\ddl\DropSequenceVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */