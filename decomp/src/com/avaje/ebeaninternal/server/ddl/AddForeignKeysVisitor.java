/*     */ package com.avaje.ebeaninternal.server.ddl;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompound;
/*     */ import com.avaje.ebeaninternal.server.deploy.TableJoin;
/*     */ import com.avaje.ebeaninternal.server.deploy.TableJoinColumn;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AddForeignKeysVisitor
/*     */   extends AbstractBeanVisitor
/*     */ {
/*     */   final DdlGenContext ctx;
/*     */   final FkeyPropertyVisitor pv;
/*     */   
/*     */   public AddForeignKeysVisitor(DdlGenContext ctx) {
/*  20 */     this.ctx = ctx;
/*  21 */     this.pv = new FkeyPropertyVisitor(this, ctx);
/*     */   }
/*     */   
/*     */   public boolean visitBean(BeanDescriptor<?> descriptor) {
/*  25 */     if (!descriptor.isInheritanceRoot())
/*     */     {
/*  27 */       return false;
/*     */     }
/*  29 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  34 */   public void visitBeanEnd(BeanDescriptor<?> descriptor) { visitInheritanceProperties(descriptor, this.pv); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitBegin() {}
/*     */ 
/*     */   
/*  41 */   public void visitEnd() { this.ctx.addIntersectionFkeys(); }
/*     */ 
/*     */ 
/*     */   
/*  45 */   public PropertyVisitor visitProperty(BeanProperty p) { return this.pv; }
/*     */ 
/*     */   
/*     */   public static class FkeyPropertyVisitor
/*     */     extends BaseTablePropertyVisitor
/*     */   {
/*     */     final DdlGenContext ctx;
/*     */     
/*     */     final AddForeignKeysVisitor parent;
/*     */     
/*     */     public FkeyPropertyVisitor(AddForeignKeysVisitor parent, DdlGenContext ctx) {
/*  56 */       this.parent = parent;
/*  57 */       this.ctx = ctx;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void visitEmbeddedScalar(BeanProperty p, BeanPropertyAssocOne<?> embedded) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void visitOneImported(BeanPropertyAssocOne<?> p) {
/*  71 */       String baseTable = p.getBeanDescriptor().getBaseTable();
/*     */       
/*  73 */       TableJoin tableJoin = p.getTableJoin();
/*     */       
/*  75 */       TableJoinColumn[] columns = tableJoin.columns();
/*     */ 
/*     */       
/*  78 */       String tableName = p.getBeanDescriptor().getBaseTable();
/*  79 */       String fkName = this.ctx.getDdlSyntax().getForeignKeyName(tableName, p.getName(), this.ctx.incrementFkCount());
/*     */       
/*  81 */       this.ctx.write("alter table ").write(baseTable).write(" add ");
/*  82 */       if (fkName != null) {
/*  83 */         this.ctx.write("constraint ").write(fkName).write(" ");
/*     */       }
/*  85 */       this.ctx.write("foreign key (");
/*  86 */       for (i = 0; i < columns.length; i++) {
/*  87 */         if (i > 0) {
/*  88 */           this.ctx.write(",");
/*     */         }
/*  90 */         this.ctx.write(columns[i].getLocalDbColumn());
/*     */       } 
/*  92 */       this.ctx.write(")");
/*     */       
/*  94 */       this.ctx.write(" references ");
/*  95 */       this.ctx.write(tableJoin.getTable());
/*  96 */       this.ctx.write(" (");
/*  97 */       for (i = 0; i < columns.length; i++) {
/*  98 */         if (i > 0) {
/*  99 */           this.ctx.write(",");
/*     */         }
/* 101 */         this.ctx.write(columns[i].getForeignDbColumn());
/*     */       } 
/* 103 */       this.ctx.write(")");
/*     */       
/* 105 */       String fkeySuffix = this.ctx.getDdlSyntax().getForeignKeySuffix();
/* 106 */       if (fkeySuffix != null) {
/* 107 */         this.ctx.write(" ").write(fkeySuffix);
/*     */       }
/* 109 */       this.ctx.write(";").writeNewLine();
/*     */       
/* 111 */       if (this.ctx.getDdlSyntax().isRenderIndexForFkey()) {
/*     */ 
/*     */         
/* 114 */         this.ctx.write("create index ");
/*     */         
/* 116 */         String idxName = this.ctx.getDdlSyntax().getIndexName(tableName, p.getName(), this.ctx.incrementIxCount());
/* 117 */         if (idxName != null) {
/* 118 */           this.ctx.write(idxName);
/*     */         }
/*     */         
/* 121 */         this.ctx.write(" on ").write(baseTable).write(" (");
/* 122 */         for (int i = 0; i < columns.length; i++) {
/* 123 */           if (i > 0) {
/* 124 */             this.ctx.write(",");
/*     */           }
/* 126 */           this.ctx.write(columns[i].getLocalDbColumn());
/*     */         } 
/* 128 */         this.ctx.write(");").writeNewLine();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void visitScalar(BeanProperty p) {}
/*     */     
/*     */     public void visitCompound(BeanPropertyCompound p) {}
/*     */     
/*     */     public void visitCompoundScalar(BeanPropertyCompound compound, BeanProperty p) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\ddl\AddForeignKeysVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */