/*     */ package com.avaje.ebeaninternal.server.deploy.meta;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.Message;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanCascadeInfo;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanTable;
/*     */ import java.util.ArrayList;
/*     */ import javax.persistence.JoinColumn;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeployTableJoin
/*     */ {
/*     */   private boolean importedPrimaryKey;
/*     */   private String table;
/*  53 */   private String type = "join";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   private ArrayList<DeployBeanProperty> properties = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   private ArrayList<DeployTableJoinColumn> columns = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private BeanCascadeInfo cascadeInfo = new BeanCascadeInfo();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   public String toString() { return this.type + " " + this.table + " " + this.columns; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   public boolean isImportedPrimaryKey() { return this.importedPrimaryKey; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   public void setImportedPrimaryKey(boolean importedPrimaryKey) { this.importedPrimaryKey = importedPrimaryKey; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   public boolean hasJoinColumns() { return (this.columns.size() > 0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   public BeanCascadeInfo getCascadeInfo() { return this.cascadeInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColumns(DeployTableJoinColumn[] cols, boolean reverse) {
/* 115 */     this.columns = new ArrayList();
/* 116 */     for (int i = 0; i < cols.length; i++) {
/* 117 */       addJoinColumn(cols[i].copy(reverse));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 125 */   public void addJoinColumn(DeployTableJoinColumn pair) { this.columns.add(pair); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addJoinColumn(boolean order, JoinColumn jc, BeanTable beanTable) {
/* 135 */     if (!"".equals(jc.table())) {
/* 136 */       setTable(jc.table());
/*     */     }
/* 138 */     addJoinColumn(new DeployTableJoinColumn(order, jc, beanTable));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addJoinColumn(boolean order, JoinColumn[] jcArray, BeanTable beanTable) {
/* 145 */     for (int i = 0; i < jcArray.length; i++) {
/* 146 */       addJoinColumn(order, jcArray[i], beanTable);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 154 */   public DeployTableJoinColumn[] columns() { return (DeployTableJoinColumn[])this.columns.toArray(new DeployTableJoinColumn[this.columns.size()]); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 162 */   public DeployBeanProperty[] properties() { return (DeployBeanProperty[])this.properties.toArray(new DeployBeanProperty[this.properties.size()]); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 169 */   public String getTable() { return this.table; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 176 */   public void setTable(String table) { this.table = table; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 183 */   public String getType() { return this.type; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 190 */   public boolean isOuterJoin() { return this.type.equals("left outer join"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setType(String joinType) {
/* 197 */     joinType = joinType.toUpperCase();
/* 198 */     if (joinType.equalsIgnoreCase("join")) {
/* 199 */       this.type = "join";
/* 200 */     } else if (joinType.indexOf("LEFT") > -1) {
/* 201 */       this.type = "left outer join";
/* 202 */     } else if (joinType.indexOf("OUTER") > -1) {
/* 203 */       this.type = "left outer join";
/* 204 */     } else if (joinType.indexOf("INNER") > -1) {
/* 205 */       this.type = "join";
/*     */     } else {
/* 207 */       throw new RuntimeException(Message.msg("join.type.unknown", joinType));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public DeployTableJoin createInverse(String tableName) {
/* 213 */     DeployTableJoin inverse = new DeployTableJoin();
/*     */     
/* 215 */     return copyTo(inverse, true, tableName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DeployTableJoin copyTo(DeployTableJoin destJoin, boolean reverse, String tableName) {
/* 221 */     destJoin.setTable(tableName);
/* 222 */     destJoin.setType(this.type);
/* 223 */     destJoin.setColumns(columns(), reverse);
/*     */     
/* 225 */     return destJoin;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\meta\DeployTableJoin.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */