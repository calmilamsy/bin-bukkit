/*     */ package com.avaje.ebeaninternal.server.ddl;
/*     */ 
/*     */ import com.avaje.ebean.config.dbplatform.DbDdlSyntax;
/*     */ import com.avaje.ebean.config.dbplatform.IdType;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompound;
/*     */ import com.avaje.ebeaninternal.server.deploy.TableJoin;
/*     */ import com.avaje.ebeaninternal.server.deploy.TableJoinColumn;
/*     */ import com.avaje.ebeaninternal.server.deploy.id.ImportedId;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CreateTableColumnVisitor
/*     */   extends BaseTablePropertyVisitor
/*     */ {
/*  22 */   private static final Logger logger = Logger.getLogger(CreateTableColumnVisitor.class.getName());
/*     */   
/*     */   private final DdlGenContext ctx;
/*     */   
/*     */   private final DbDdlSyntax ddl;
/*     */   
/*     */   private final CreateTableVisitor parent;
/*     */   
/*     */   public CreateTableColumnVisitor(CreateTableVisitor parent, DdlGenContext ctx) {
/*  31 */     this.parent = parent;
/*  32 */     this.ctx = ctx;
/*  33 */     this.ddl = ctx.getDdlSyntax();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitMany(BeanPropertyAssocMany<?> p) {
/*  39 */     if (p.isManyToMany() && 
/*  40 */       p.getMappedBy() == null) {
/*     */ 
/*     */ 
/*     */       
/*  44 */       TableJoin intersectionTableJoin = p.getIntersectionTableJoin();
/*     */ 
/*     */       
/*  47 */       String intTable = intersectionTableJoin.getTable();
/*  48 */       if (this.ctx.isProcessIntersectionTable(intTable))
/*     */       {
/*     */ 
/*     */         
/*  52 */         (new CreateIntersectionTable(this.ctx, p)).build();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   public void visitCompoundScalar(BeanPropertyCompound compound, BeanProperty p) { visitScalar(p); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitCompound(BeanPropertyCompound p) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   public void visitEmbeddedScalar(BeanProperty p, BeanPropertyAssocOne<?> embedded) { visitScalar(p); }
/*     */ 
/*     */ 
/*     */   
/*     */   private StringBuilder createUniqueConstraintBuffer(String table, String column) {
/*  77 */     String uqConstraintName = "uq_" + table + "_" + column;
/*     */     
/*  79 */     if (uqConstraintName.length() > this.ddl.getMaxConstraintNameLength()) {
/*  80 */       uqConstraintName = uqConstraintName.substring(0, this.ddl.getMaxConstraintNameLength());
/*     */     }
/*     */     
/*  83 */     StringBuilder constraintExpr = new StringBuilder();
/*  84 */     constraintExpr.append("constraint ").append(uqConstraintName).append(" unique (");
/*     */ 
/*     */ 
/*     */     
/*  88 */     return constraintExpr;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitOneImported(BeanPropertyAssocOne<?> p) {
/*  94 */     ImportedId importedId = p.getImportedId();
/*     */     
/*  96 */     TableJoinColumn[] columns = p.getTableJoin().columns();
/*  97 */     if (columns.length == 0) {
/*  98 */       String msg = "No join columns for " + p.getFullBeanName();
/*  99 */       throw new RuntimeException(msg);
/*     */     } 
/*     */     
/* 102 */     StringBuilder constraintExpr = createUniqueConstraintBuffer(p.getBeanDescriptor().getBaseTable(), columns[0].getLocalDbColumn());
/*     */     
/* 104 */     for (int i = 0; i < columns.length; i++) {
/*     */       
/* 106 */       String dbCol = columns[i].getLocalDbColumn();
/*     */       
/* 108 */       if (i > 0) {
/* 109 */         constraintExpr.append(", ");
/*     */       }
/* 111 */       constraintExpr.append(dbCol);
/*     */       
/* 113 */       if (!this.parent.isDbColumnWritten(dbCol)) {
/*     */ 
/*     */ 
/*     */         
/* 117 */         this.parent.writeColumnName(dbCol, p);
/*     */         
/* 119 */         BeanProperty importedProperty = importedId.findMatchImport(dbCol);
/* 120 */         if (importedProperty != null) {
/*     */           
/* 122 */           String columnDefn = this.ctx.getColumnDefn(importedProperty);
/* 123 */           this.ctx.write(columnDefn);
/*     */         } else {
/*     */           
/* 126 */           throw new RuntimeException("Imported BeanProperty not found?");
/*     */         } 
/*     */         
/* 129 */         if (!p.isNullable()) {
/* 130 */           this.ctx.write(" not null");
/*     */         }
/* 132 */         this.ctx.write(",").writeNewLine();
/*     */       } 
/* 134 */     }  constraintExpr.append(")");
/*     */     
/* 136 */     if (p.isOneToOne() && 
/* 137 */       this.ddl.isAddOneToOneUniqueContraint()) {
/* 138 */       this.parent.addUniqueConstraint(constraintExpr.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitScalar(BeanProperty p) {
/* 147 */     if (p.isSecondaryTable()) {
/*     */       return;
/*     */     }
/*     */     
/* 151 */     if (this.parent.isDbColumnWritten(p.getDbColumn())) {
/*     */       return;
/*     */     }
/*     */     
/* 155 */     this.parent.writeColumnName(p.getDbColumn(), p);
/*     */     
/* 157 */     String columnDefn = this.ctx.getColumnDefn(p);
/* 158 */     this.ctx.write(columnDefn);
/*     */     
/* 160 */     if (isIdentity(p)) {
/* 161 */       writeIdentity();
/*     */     }
/*     */     
/* 164 */     if (p.isId() && this.ddl.isInlinePrimaryKeyConstraint()) {
/* 165 */       this.ctx.write(" primary key");
/*     */     }
/* 167 */     else if (!p.isNullable() || p.isDDLNotNull()) {
/* 168 */       this.ctx.write(" not null");
/*     */     } 
/*     */     
/* 171 */     if (p.isUnique() && !p.isId()) {
/* 172 */       this.parent.addUniqueConstraint(createUniqueConstraint(p));
/*     */     }
/*     */     
/* 175 */     this.parent.addCheckConstraint(p);
/*     */     
/* 177 */     this.ctx.write(",").writeNewLine();
/*     */   }
/*     */ 
/*     */   
/*     */   private String createUniqueConstraint(BeanProperty p) {
/* 182 */     StringBuilder expr = createUniqueConstraintBuffer(p.getBeanDescriptor().getBaseTable(), p.getDbColumn());
/* 183 */     expr.append(p.getDbColumn()).append(")");
/*     */     
/* 185 */     return expr.toString();
/*     */   }
/*     */   
/*     */   protected void writeIdentity() {
/* 189 */     String identity = this.ddl.getIdentity();
/* 190 */     if (identity != null && identity.length() > 0) {
/* 191 */       this.ctx.write(" ").write(identity);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isIdentity(BeanProperty p) {
/* 197 */     if (p.isId()) {
/*     */       try {
/* 199 */         IdType idType = p.getBeanDescriptor().getIdType();
/*     */         
/* 201 */         if (idType.equals(IdType.IDENTITY)) {
/*     */           
/* 203 */           int jdbcType = p.getScalarType().getJdbcType();
/* 204 */           if (jdbcType == 4 || jdbcType == -5 || jdbcType == 5)
/*     */           {
/* 206 */             return true;
/*     */           }
/*     */         } 
/* 209 */       } catch (Exception e) {
/* 210 */         String msg = "Error determining identity on property " + p.getFullBeanName();
/* 211 */         logger.log(Level.SEVERE, msg, e);
/*     */       } 
/*     */     }
/* 214 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\ddl\CreateTableColumnVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */