/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
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
/*     */ public class RowDataStatic
/*     */   implements RowData
/*     */ {
/*     */   private Field[] metadata;
/*     */   private int index;
/*     */   ResultSetImpl owner;
/*     */   private List rows;
/*     */   
/*     */   public RowDataStatic(List rows) {
/*  53 */     this.index = -1;
/*  54 */     this.rows = rows;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   public void addRow(ResultSetRow row) { this.rows.add(row); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   public void afterLast() { this.index = this.rows.size(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   public void beforeFirst() { this.index = -1; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   public void beforeLast() { this.index = this.rows.size() - 2; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSetRow getAt(int atIndex) throws SQLException {
/* 103 */     if (atIndex < 0 || atIndex >= this.rows.size()) {
/* 104 */       return null;
/*     */     }
/*     */     
/* 107 */     return ((ResultSetRow)this.rows.get(atIndex)).setMetadata(this.metadata);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   public int getCurrentRowNumber() { return this.index; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 123 */   public ResultSetInternalMethods getOwner() { return this.owner; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   public boolean hasNext() { return (this.index + 1 < this.rows.size()); }
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
/* 143 */   public boolean isAfterLast() { return (this.index >= this.rows.size()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 152 */   public boolean isBeforeFirst() { return (this.index == -1 && this.rows.size() != 0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 161 */   public boolean isDynamic() { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 170 */   public boolean isEmpty() { return (this.rows.size() == 0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 179 */   public boolean isFirst() { return (this.index == 0); }
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
/*     */   public boolean isLast() {
/* 192 */     if (this.rows.size() == 0) {
/* 193 */       return false;
/*     */     }
/*     */     
/* 196 */     return (this.index == this.rows.size() - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 206 */   public void moveRowRelative(int rowsToMove) { this.index += rowsToMove; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSetRow next() throws SQLException {
/* 215 */     this.index++;
/*     */     
/* 217 */     if (this.index < this.rows.size()) {
/* 218 */       ResultSetRow row = (ResultSetRow)this.rows.get(this.index);
/*     */       
/* 220 */       return row.setMetadata(this.metadata);
/*     */     } 
/*     */     
/* 223 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 233 */   public void removeRow(int atIndex) { this.rows.remove(atIndex); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 243 */   public void setCurrentRow(int newIndex) { this.index = newIndex; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 250 */   public void setOwner(ResultSetImpl rs) { this.owner = rs; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 259 */   public int size() { return this.rows.size(); }
/*     */ 
/*     */ 
/*     */   
/* 263 */   public boolean wasEmpty() { return (this.rows != null && this.rows.size() == 0); }
/*     */ 
/*     */ 
/*     */   
/* 267 */   public void setMetadata(Field[] metadata) { this.metadata = metadata; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\RowDataStatic.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */