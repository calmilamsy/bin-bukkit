/*     */ package com.avaje.ebeaninternal.api;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.cluster.BinaryMessage;
/*     */ import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ public final class TransactionEventTable
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2236555729767483264L;
/*  18 */   private final Map<String, TableIUD> map = new HashMap();
/*     */ 
/*     */   
/*  21 */   public String toString() { return "TransactionEventTable " + this.map.values(); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeBinaryMessage(BinaryMessageList msgList) throws IOException {
/*  26 */     for (TableIUD tableIud : this.map.values()) {
/*  27 */       tableIud.writeBinaryMessage(msgList);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void readBinaryMessage(DataInput dataInput) throws IOException {
/*  33 */     TableIUD tableIud = TableIUD.readBinaryMessage(dataInput);
/*  34 */     this.map.put(tableIud.getTable(), tableIud);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(TransactionEventTable table) {
/*  40 */     for (TableIUD iud : table.values()) {
/*  41 */       add(iud);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(String table, boolean insert, boolean update, boolean delete) {
/*  47 */     table = table.toUpperCase();
/*     */     
/*  49 */     add(new TableIUD(table, insert, update, delete, null));
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(TableIUD newTableIUD) {
/*  54 */     TableIUD existingTableIUD = (TableIUD)this.map.put(newTableIUD.getTable(), newTableIUD);
/*  55 */     if (existingTableIUD != null) {
/*  56 */       newTableIUD.add(existingTableIUD);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  61 */   public boolean isEmpty() { return this.map.isEmpty(); }
/*     */ 
/*     */ 
/*     */   
/*  65 */   public Collection<TableIUD> values() { return this.map.values(); }
/*     */ 
/*     */   
/*     */   public static class TableIUD
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -1958317571064162089L;
/*     */     private String table;
/*     */     private boolean insert;
/*     */     private boolean update;
/*     */     private boolean delete;
/*     */     
/*     */     private TableIUD(String table, boolean insert, boolean update, boolean delete) {
/*  78 */       this.table = table;
/*  79 */       this.insert = insert;
/*  80 */       this.update = update;
/*  81 */       this.delete = delete;
/*     */     }
/*     */ 
/*     */     
/*     */     public static TableIUD readBinaryMessage(DataInput dataInput) throws IOException {
/*  86 */       String table = dataInput.readUTF();
/*  87 */       boolean insert = dataInput.readBoolean();
/*  88 */       boolean update = dataInput.readBoolean();
/*  89 */       boolean delete = dataInput.readBoolean();
/*     */       
/*  91 */       return new TableIUD(table, insert, update, delete);
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeBinaryMessage(BinaryMessageList msgList) throws IOException {
/*  96 */       BinaryMessage msg = new BinaryMessage(this.table.length() + 10);
/*  97 */       DataOutputStream os = msg.getOs();
/*  98 */       os.writeInt(2);
/*  99 */       os.writeUTF(this.table);
/* 100 */       os.writeBoolean(this.insert);
/* 101 */       os.writeBoolean(this.update);
/* 102 */       os.writeBoolean(this.delete);
/*     */       
/* 104 */       msgList.add(msg);
/*     */     }
/*     */ 
/*     */     
/* 108 */     public String toString() { return "TableIUD " + this.table + " i:" + this.insert + " u:" + this.update + " d:" + this.delete; }
/*     */ 
/*     */     
/*     */     private void add(TableIUD other) {
/* 112 */       if (other.insert) {
/* 113 */         this.insert = true;
/*     */       }
/* 115 */       if (other.update) {
/* 116 */         this.update = true;
/*     */       }
/* 118 */       if (other.delete) {
/* 119 */         this.delete = true;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 124 */     public String getTable() { return this.table; }
/*     */ 
/*     */ 
/*     */     
/* 128 */     public boolean isInsert() { return this.insert; }
/*     */ 
/*     */ 
/*     */     
/* 132 */     public boolean isUpdate() { return this.update; }
/*     */ 
/*     */ 
/*     */     
/* 136 */     public boolean isDelete() { return this.delete; }
/*     */ 
/*     */ 
/*     */     
/* 140 */     public boolean isUpdateOrDelete() { return (this.update || this.delete); }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\api\TransactionEventTable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */