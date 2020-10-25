/*     */ package com.avaje.ebeaninternal.server.ddl;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.deploy.TableJoin;
/*     */ import com.avaje.ebeaninternal.server.deploy.TableJoinColumn;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CreateIntersectionTable
/*     */ {
/*     */   private static final String NEW_LINE = "\n";
/*     */   private final DdlGenContext ctx;
/*     */   private final BeanPropertyAssocMany<?> manyProp;
/*     */   private final TableJoin intersectionTableJoin;
/*     */   private final TableJoin tableJoin;
/*     */   private StringBuilder sb;
/*     */   private StringBuilder pkeySb;
/*     */   private int foreignKeyCount;
/*     */   private int maxFkeyLength;
/*     */   
/*     */   public CreateIntersectionTable(DdlGenContext ctx, BeanPropertyAssocMany<?> manyProp) {
/*  24 */     this.sb = new StringBuilder();
/*     */     
/*  26 */     this.pkeySb = new StringBuilder();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  33 */     this.ctx = ctx;
/*  34 */     this.manyProp = manyProp;
/*  35 */     this.intersectionTableJoin = manyProp.getIntersectionTableJoin();
/*  36 */     this.tableJoin = manyProp.getTableJoin();
/*  37 */     this.maxFkeyLength = ctx.getDdlSyntax().getMaxConstraintNameLength() - 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public void build() {
/*  42 */     String createTable = buildCreateTable();
/*  43 */     this.ctx.addCreateIntersectionTable(createTable);
/*     */     
/*  45 */     this.foreignKeyCount = 0;
/*  46 */     buildFkConstraints();
/*     */   }
/*     */ 
/*     */   
/*     */   private void buildFkConstraints() {
/*  51 */     BeanDescriptor<?> localDesc = this.manyProp.getBeanDescriptor();
/*  52 */     String fk1 = buildFkConstraints(localDesc, this.intersectionTableJoin.columns(), true);
/*  53 */     this.ctx.addIntersectionTableFk(fk1);
/*     */     
/*  55 */     BeanDescriptor<?> targetDesc = this.manyProp.getTargetDescriptor();
/*  56 */     String fk2 = buildFkConstraints(targetDesc, this.tableJoin.columns(), false);
/*  57 */     this.ctx.addIntersectionTableFk(fk2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getFkNameSuffix() {
/*  65 */     this.foreignKeyCount++;
/*     */     
/*  67 */     if (this.foreignKeyCount > 9) {
/*  68 */       return "_" + this.foreignKeyCount;
/*     */     }
/*  70 */     return "_0" + this.foreignKeyCount;
/*     */   }
/*     */ 
/*     */   
/*     */   private String getFkNameWithSuffix(String fkName) {
/*  75 */     if (fkName.length() > this.maxFkeyLength) {
/*  76 */       fkName = fkName.substring(0, this.maxFkeyLength);
/*     */     }
/*  78 */     return fkName + getFkNameSuffix();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String buildFkConstraints(BeanDescriptor<?> desc, TableJoinColumn[] columns, boolean direction) {
/*  84 */     StringBuilder fkBuf = new StringBuilder();
/*     */     
/*  86 */     String fkName = "fk_" + this.intersectionTableJoin.getTable() + "_" + desc.getBaseTable();
/*     */     
/*  88 */     fkName = getFkNameWithSuffix(fkName);
/*     */     
/*  90 */     fkBuf.append("alter table ");
/*  91 */     fkBuf.append(this.intersectionTableJoin.getTable());
/*  92 */     fkBuf.append(" add constraint ").append(fkName);
/*     */     
/*  94 */     fkBuf.append(" foreign key (");
/*     */     
/*  96 */     for (i = 0; i < columns.length; i++) {
/*  97 */       if (i > 0) {
/*  98 */         fkBuf.append(", ");
/*     */       }
/* 100 */       String col = direction ? columns[i].getForeignDbColumn() : columns[i].getLocalDbColumn();
/* 101 */       fkBuf.append(col);
/*     */     } 
/* 103 */     fkBuf.append(") references ").append(desc.getBaseTable()).append(" (");
/*     */     
/* 105 */     for (i = 0; i < columns.length; i++) {
/* 106 */       if (i > 0) {
/* 107 */         fkBuf.append(", ");
/*     */       }
/* 109 */       String col = !direction ? columns[i].getForeignDbColumn() : columns[i].getLocalDbColumn();
/* 110 */       fkBuf.append(col);
/*     */     } 
/* 112 */     fkBuf.append(")");
/*     */     
/* 114 */     String fkeySuffix = this.ctx.getDdlSyntax().getForeignKeySuffix();
/* 115 */     if (fkeySuffix != null) {
/* 116 */       fkBuf.append(" ");
/* 117 */       fkBuf.append(fkeySuffix);
/*     */     } 
/* 119 */     fkBuf.append(";").append("\n");
/*     */     
/* 121 */     return fkBuf.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private String buildCreateTable() {
/* 126 */     BeanDescriptor<?> localDesc = this.manyProp.getBeanDescriptor();
/* 127 */     BeanDescriptor<?> targetDesc = this.manyProp.getTargetDescriptor();
/*     */     
/* 129 */     this.sb.append("create table ");
/* 130 */     this.sb.append(this.intersectionTableJoin.getTable());
/* 131 */     this.sb.append(" (").append("\n");
/*     */     
/* 133 */     TableJoinColumn[] columns = this.intersectionTableJoin.columns();
/* 134 */     for (i = 0; i < columns.length; i++)
/*     */     {
/* 136 */       addColumn(localDesc, columns[i].getForeignDbColumn(), columns[i].getLocalDbColumn());
/*     */     }
/*     */     
/* 139 */     TableJoinColumn[] otherColumns = this.tableJoin.columns();
/* 140 */     for (int i = 0; i < otherColumns.length; i++)
/*     */     {
/* 142 */       addColumn(targetDesc, otherColumns[i].getLocalDbColumn(), otherColumns[i].getForeignDbColumn());
/*     */     }
/*     */     
/* 145 */     this.sb.append("  constraint pk_").append(this.intersectionTableJoin.getTable());
/* 146 */     this.sb.append(" primary key (").append(this.pkeySb.toString().substring(2));
/* 147 */     this.sb.append("))").append("\n").append(";").append("\n");
/*     */     
/* 149 */     return this.sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private void addColumn(BeanDescriptor<?> desc, String column, String findPropColumn) {
/* 154 */     this.pkeySb.append(", ");
/* 155 */     this.pkeySb.append(column);
/*     */     
/* 157 */     writeColumn(column);
/*     */     
/* 159 */     BeanProperty p = desc.getIdBinder().findBeanProperty(findPropColumn);
/* 160 */     if (p == null) {
/* 161 */       throw new RuntimeException("Could not find id property for " + findPropColumn);
/*     */     }
/*     */     
/* 164 */     String columnDefn = this.ctx.getColumnDefn(p);
/* 165 */     this.sb.append(columnDefn);
/* 166 */     this.sb.append(" not null");
/* 167 */     this.sb.append(",").append("\n");
/*     */   }
/*     */ 
/*     */   
/* 171 */   private void writeColumn(String columnName) { this.sb.append("  ").append(this.ctx.pad(columnName, 30)).append(" "); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\ddl\CreateIntersectionTable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */