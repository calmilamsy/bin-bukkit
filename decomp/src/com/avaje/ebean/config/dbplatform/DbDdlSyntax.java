/*     */ package com.avaje.ebean.config.dbplatform;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DbDdlSyntax
/*     */ {
/*     */   private boolean renderIndexForFkey = true;
/*     */   private boolean inlinePrimaryKeyConstraint = false;
/*     */   private boolean addOneToOneUniqueContraint = false;
/*  15 */   private int maxConstraintNameLength = 32;
/*     */   
/*  17 */   private int columnNameWidth = 25;
/*     */   
/*     */   private String dropTableCascade;
/*     */   
/*     */   private String dropIfExists;
/*  22 */   private String newLine = "\r\n";
/*     */   
/*  24 */   private String identity = "auto_increment";
/*     */   
/*  26 */   private String pkPrefix = "pk_";
/*     */ 
/*     */   
/*     */   private String disableReferentialIntegrity;
/*     */ 
/*     */   
/*     */   private String enableReferentialIntegrity;
/*     */   
/*     */   private String foreignKeySuffix;
/*     */ 
/*     */   
/*     */   public String getPrimaryKeyName(String tableName) {
/*  38 */     String pk = this.pkPrefix + tableName;
/*  39 */     if (pk.length() > this.maxConstraintNameLength)
/*     */     {
/*  41 */       pk = pk.substring(0, this.maxConstraintNameLength);
/*     */     }
/*  43 */     return pk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   public String getIdentity() { return this.identity; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   public void setIdentity(String identity) { this.identity = identity; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   public int getColumnNameWidth() { return this.columnNameWidth; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   public void setColumnNameWidth(int columnNameWidth) { this.columnNameWidth = columnNameWidth; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   public String getNewLine() { return this.newLine; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   public void setNewLine(String newLine) { this.newLine = newLine; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   public String getPkPrefix() { return this.pkPrefix; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   public void setPkPrefix(String pkPrefix) { this.pkPrefix = pkPrefix; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   public String getDisableReferentialIntegrity() { return this.disableReferentialIntegrity; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   public void setDisableReferentialIntegrity(String disableReferentialIntegrity) { this.disableReferentialIntegrity = disableReferentialIntegrity; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   public String getEnableReferentialIntegrity() { return this.enableReferentialIntegrity; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   public void setEnableReferentialIntegrity(String enableReferentialIntegrity) { this.enableReferentialIntegrity = enableReferentialIntegrity; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 134 */   public boolean isRenderIndexForFkey() { return this.renderIndexForFkey; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 141 */   public void setRenderIndexForFkey(boolean renderIndexForFkey) { this.renderIndexForFkey = renderIndexForFkey; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   public String getDropIfExists() { return this.dropIfExists; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 157 */   public void setDropIfExists(String dropIfExists) { this.dropIfExists = dropIfExists; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 164 */   public String getDropTableCascade() { return this.dropTableCascade; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 171 */   public void setDropTableCascade(String dropTableCascade) { this.dropTableCascade = dropTableCascade; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 178 */   public String getForeignKeySuffix() { return this.foreignKeySuffix; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 185 */   public void setForeignKeySuffix(String foreignKeySuffix) { this.foreignKeySuffix = foreignKeySuffix; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 192 */   public int getMaxConstraintNameLength() { return this.maxConstraintNameLength; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 199 */   public void setMaxConstraintNameLength(int maxFkeyLength) { this.maxConstraintNameLength = maxFkeyLength; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 208 */   public boolean isAddOneToOneUniqueContraint() { return this.addOneToOneUniqueContraint; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 215 */   public void setAddOneToOneUniqueContraint(boolean addOneToOneUniqueContraint) { this.addOneToOneUniqueContraint = addOneToOneUniqueContraint; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 222 */   public boolean isInlinePrimaryKeyConstraint() { return this.inlinePrimaryKeyConstraint; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 229 */   public void setInlinePrimaryKeyConstraint(boolean inlinePrimaryKeyConstraint) { this.inlinePrimaryKeyConstraint = inlinePrimaryKeyConstraint; }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIndexName(String table, String propName, int ixCount) {
/* 234 */     StringBuilder buffer = new StringBuilder();
/* 235 */     buffer.append("ix_");
/* 236 */     buffer.append(table);
/* 237 */     buffer.append("_");
/* 238 */     buffer.append(propName);
/*     */     
/* 240 */     addSuffix(buffer, ixCount);
/*     */     
/* 242 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getForeignKeyName(String table, String propName, int fkCount) {
/* 247 */     StringBuilder buffer = new StringBuilder();
/* 248 */     buffer.append("fk_");
/* 249 */     buffer.append(table);
/* 250 */     buffer.append("_");
/* 251 */     buffer.append(propName);
/*     */     
/* 253 */     addSuffix(buffer, fkCount);
/*     */     
/* 255 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addSuffix(StringBuilder buffer, int count) {
/* 265 */     String suffixNr = Integer.toString(count);
/* 266 */     int suffixLen = suffixNr.length() + 1;
/*     */     
/* 268 */     if (buffer.length() + suffixLen > this.maxConstraintNameLength) {
/* 269 */       buffer.setLength(this.maxConstraintNameLength - suffixLen);
/*     */     }
/* 271 */     buffer.append("_");
/* 272 */     buffer.append(suffixNr);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\dbplatform\DbDdlSyntax.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */