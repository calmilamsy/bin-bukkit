/*     */ package com.avaje.ebeaninternal.server.ddl;
/*     */ 
/*     */ import com.avaje.ebean.config.dbplatform.DbDdlSyntax;
/*     */ import com.avaje.ebean.config.dbplatform.DbType;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompound;
/*     */ import com.avaje.ebeaninternal.server.deploy.CompoundUniqueContraint;
/*     */ import com.avaje.ebeaninternal.server.deploy.InheritInfo;
/*     */ import com.avaje.ebeaninternal.server.deploy.parse.SqlReservedWords;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CreateTableVisitor
/*     */   extends AbstractBeanVisitor
/*     */ {
/*  23 */   protected static final Logger logger = Logger.getLogger(CreateTableVisitor.class.getName());
/*     */   
/*     */   final DdlGenContext ctx;
/*     */   
/*     */   final PropertyVisitor pv;
/*     */   
/*     */   final DbDdlSyntax ddl;
/*     */   
/*     */   final int columnNameWidth;
/*     */   
/*     */   private final Set<String> wroteColumns;
/*     */   
/*     */   private ArrayList<String> checkConstraints;
/*     */   
/*     */   private ArrayList<String> uniqueConstraints;
/*     */   
/*     */   private String table;
/*     */   
/*     */   private String schema;
/*     */ 
/*     */   
/*  44 */   public Set<String> getWroteColumns() { return this.wroteColumns; }
/*     */   public CreateTableVisitor(DdlGenContext ctx) {
/*     */     this.wroteColumns = new HashSet();
/*     */     this.checkConstraints = new ArrayList();
/*     */     this.uniqueConstraints = new ArrayList();
/*  49 */     this.ctx = ctx;
/*  50 */     this.ddl = ctx.getDdlSyntax();
/*  51 */     this.columnNameWidth = this.ddl.getColumnNameWidth();
/*  52 */     this.pv = new CreateTableColumnVisitor(this, ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   public boolean isDbColumnWritten(String dbColumn) { return this.wroteColumns.contains(dbColumn.toLowerCase()); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   public void addDbColumnWritten(String dbColumn) { this.wroteColumns.add(dbColumn.toLowerCase()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeTableName(BeanDescriptor<?> descriptor) {
/*  71 */     String tableName = descriptor.getBaseTable();
/*  72 */     int dotPos = tableName.lastIndexOf('.');
/*  73 */     if (dotPos > -1) {
/*  74 */       this.schema = tableName.substring(0, dotPos);
/*  75 */       this.table = tableName.substring(dotPos + 1);
/*     */     } else {
/*  77 */       this.table = tableName;
/*     */     } 
/*     */     
/*  80 */     if (SqlReservedWords.isKeyword(this.table)) {
/*  81 */       logger.warning("Table name [" + this.table + "] is a suspected SQL reserved word for bean " + descriptor.getFullName());
/*     */     }
/*     */     
/*  84 */     this.ctx.write(tableName);
/*     */   }
/*     */ 
/*     */   
/*  88 */   protected String getTable() { return this.table; }
/*     */ 
/*     */ 
/*     */   
/*  92 */   protected String getSchema() { return this.schema; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeColumnName(String columnName, BeanProperty p) {
/* 101 */     addDbColumnWritten(columnName);
/*     */     
/* 103 */     if (SqlReservedWords.isKeyword(columnName)) {
/* 104 */       String propName = (p == null) ? "(Unknown)" : p.getFullBeanName();
/* 105 */       logger.warning("Column name [" + columnName + "] is a suspected SQL reserved word for property " + propName);
/*     */     } 
/*     */     
/* 108 */     this.ctx.write("  ").write(columnName, this.columnNameWidth).write(" ");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addCheckConstraint(BeanProperty p, String prefix, String constraintExpression) {
/* 119 */     if (p != null && constraintExpression != null) {
/*     */ 
/*     */       
/* 122 */       String s = "constraint " + getConstraintName(prefix, p) + " " + constraintExpression;
/*     */ 
/*     */       
/* 125 */       this.checkConstraints.add(s);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 130 */   protected String getConstraintName(String prefix, BeanProperty p) { return prefix + this.table + "_" + p.getDbColumn(); }
/*     */ 
/*     */ 
/*     */   
/* 134 */   protected void addUniqueConstraint(String constraintExpression) { this.uniqueConstraints.add(constraintExpression); }
/*     */ 
/*     */ 
/*     */   
/* 138 */   protected void addCheckConstraint(String constraintExpression) { this.checkConstraints.add(constraintExpression); }
/*     */ 
/*     */ 
/*     */   
/* 142 */   protected void addCheckConstraint(BeanProperty p) { addCheckConstraint(p, "ck_", p.getDbConstraintExpression()); }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean visitBean(BeanDescriptor<?> descriptor) {
/* 147 */     this.wroteColumns.clear();
/*     */     
/* 149 */     if (!descriptor.isInheritanceRoot()) {
/* 150 */       return false;
/*     */     }
/*     */     
/* 153 */     this.ctx.write("create table ");
/* 154 */     writeTableName(descriptor);
/* 155 */     this.ctx.write(" (").writeNewLine();
/*     */     
/* 157 */     InheritInfo inheritInfo = descriptor.getInheritInfo();
/* 158 */     if (inheritInfo != null && inheritInfo.isRoot()) {
/* 159 */       String discColumn = inheritInfo.getDiscriminatorColumn();
/* 160 */       int discType = inheritInfo.getDiscriminatorType();
/* 161 */       int discLength = inheritInfo.getDiscriminatorLength();
/* 162 */       DbType dbType = this.ctx.getDbTypeMap().get(discType);
/* 163 */       String discDbType = dbType.renderType(discLength, 0);
/*     */       
/* 165 */       writeColumnName(discColumn, null);
/* 166 */       this.ctx.write(discDbType);
/* 167 */       this.ctx.write(" not null,");
/* 168 */       this.ctx.writeNewLine();
/*     */     } 
/*     */ 
/*     */     
/* 172 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitBeanEnd(BeanDescriptor<?> descriptor) {
/* 177 */     visitInheritanceProperties(descriptor, this.pv);
/*     */     
/* 179 */     if (this.checkConstraints.size() > 0) {
/* 180 */       for (String checkConstraint : this.checkConstraints) {
/* 181 */         this.ctx.write("  ").write(checkConstraint).write(",").writeNewLine();
/*     */       }
/* 183 */       this.checkConstraints = new ArrayList();
/*     */     } 
/*     */     
/* 186 */     if (this.uniqueConstraints.size() > 0) {
/* 187 */       for (String constraint : this.uniqueConstraints) {
/* 188 */         this.ctx.write("  ").write(constraint).write(",").writeNewLine();
/*     */       }
/* 190 */       this.uniqueConstraints = new ArrayList();
/*     */     } 
/*     */     
/* 193 */     CompoundUniqueContraint[] compoundUniqueConstraints = descriptor.getCompoundUniqueConstraints();
/* 194 */     if (compoundUniqueConstraints != null) {
/* 195 */       String table = descriptor.getBaseTable();
/* 196 */       for (int i = 0; i < compoundUniqueConstraints.length; i++) {
/* 197 */         String constraint = createUniqueConstraint(table, i, compoundUniqueConstraints[i]);
/* 198 */         this.ctx.write("  ").write(constraint).write(",").writeNewLine();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 203 */     BeanProperty[] ids = descriptor.propertiesId();
/*     */     
/* 205 */     if (ids.length == 0) {
/*     */       
/* 207 */       this.ctx.removeLast().removeLast();
/* 208 */     } else if (ids.length > 1 || this.ddl.isInlinePrimaryKeyConstraint()) {
/*     */ 
/*     */       
/* 211 */       this.ctx.removeLast().removeLast();
/*     */     }
/*     */     else {
/*     */       
/* 215 */       String pkName = this.ddl.getPrimaryKeyName(this.table);
/* 216 */       this.ctx.write("  constraint ").write(pkName).write(" primary key (");
/*     */       
/* 218 */       VisitorUtil.visit(ids, new AbstractPropertyVisitor()
/*     */           {
/*     */             public void visitEmbeddedScalar(BeanProperty p, BeanPropertyAssocOne<?> embedded)
/*     */             {
/* 222 */               CreateTableVisitor.this.ctx.write(p.getDbColumn()).write(", ");
/*     */             }
/*     */ 
/*     */ 
/*     */             
/* 227 */             public void visitScalar(BeanProperty p) { CreateTableVisitor.this.ctx.write(p.getDbColumn()).write(", "); }
/*     */ 
/*     */ 
/*     */             
/*     */             public void visitCompoundScalar(BeanPropertyCompound compound, BeanProperty p) {
/* 232 */               CreateTableVisitor.this.ctx.write(p.getDbColumn()).write(", ");
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 237 */       this.ctx.removeLast().write(")");
/*     */     } 
/*     */ 
/*     */     
/* 241 */     this.ctx.write(")").writeNewLine();
/* 242 */     this.ctx.write(";").writeNewLine().writeNewLine();
/* 243 */     this.ctx.flush();
/*     */   }
/*     */ 
/*     */   
/*     */   private String createUniqueConstraint(String table, int idx, CompoundUniqueContraint uc) {
/* 248 */     String uqConstraintName = "uq_" + table + "_" + (idx + 1);
/*     */     
/* 250 */     StringBuilder sb = new StringBuilder();
/* 251 */     sb.append("constraint ").append(uqConstraintName).append(" unique (");
/*     */ 
/*     */ 
/*     */     
/* 255 */     String[] columns = uc.getColumns();
/*     */     
/* 257 */     for (int i = 0; i < columns.length; i++) {
/* 258 */       if (i > 0) {
/* 259 */         sb.append(",");
/*     */       }
/* 261 */       sb.append(columns[i]);
/*     */     } 
/* 263 */     sb.append(")");
/*     */     
/* 265 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/* 269 */   public void visitBeanDescriptorEnd() { this.ctx.write(");").writeNewLine().writeNewLine(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 274 */   public PropertyVisitor visitProperty(BeanProperty p) { return this.pv; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitBegin() {}
/*     */ 
/*     */   
/*     */   public void visitEnd() {
/* 282 */     this.ctx.addIntersectionCreateTables();
/* 283 */     this.ctx.flush();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\ddl\CreateTableVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */