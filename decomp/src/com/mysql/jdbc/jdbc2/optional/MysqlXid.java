/*     */ package com.mysql.jdbc.jdbc2.optional;
/*     */ 
/*     */ import javax.transaction.xa.Xid;
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
/*     */ public class MysqlXid
/*     */   implements Xid
/*     */ {
/*     */   int hash;
/*     */   byte[] myBqual;
/*     */   int myFormatId;
/*     */   byte[] myGtrid;
/*     */   
/*     */   public MysqlXid(byte[] gtrid, byte[] bqual, int formatId) {
/*  36 */     this.hash = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  45 */     this.myGtrid = gtrid;
/*  46 */     this.myBqual = bqual;
/*  47 */     this.myFormatId = formatId;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object another) {
/*  52 */     if (another instanceof Xid) {
/*  53 */       Xid anotherAsXid = (Xid)another;
/*     */       
/*  55 */       if (this.myFormatId != anotherAsXid.getFormatId()) {
/*  56 */         return false;
/*     */       }
/*     */       
/*  59 */       byte[] otherBqual = anotherAsXid.getBranchQualifier();
/*  60 */       byte[] otherGtrid = anotherAsXid.getGlobalTransactionId();
/*     */       
/*  62 */       if (otherGtrid != null && otherGtrid.length == this.myGtrid.length) {
/*  63 */         int length = otherGtrid.length;
/*     */         
/*  65 */         for (int i = 0; i < length; i++) {
/*  66 */           if (otherGtrid[i] != this.myGtrid[i]) {
/*  67 */             return false;
/*     */           }
/*     */         } 
/*     */         
/*  71 */         if (otherBqual != null && otherBqual.length == this.myBqual.length) {
/*  72 */           length = otherBqual.length;
/*     */           
/*  74 */           for (int i = 0; i < length; i++) {
/*  75 */             if (otherBqual[i] != this.myBqual[i]) {
/*  76 */               return false;
/*     */             }
/*     */           } 
/*     */         } else {
/*  80 */           return false;
/*     */         } 
/*     */         
/*  83 */         return true;
/*     */       } 
/*  85 */       return false;
/*     */     } 
/*     */     
/*  88 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  93 */   public byte[] getBranchQualifier() { return this.myBqual; }
/*     */ 
/*     */ 
/*     */   
/*  97 */   public int getFormatId() { return this.myFormatId; }
/*     */ 
/*     */ 
/*     */   
/* 101 */   public byte[] getGlobalTransactionId() { return this.myGtrid; }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 105 */     if (this.hash == 0) {
/* 106 */       for (int i = 0; i < this.myGtrid.length; i++) {
/* 107 */         this.hash = 33 * this.hash + this.myGtrid[i];
/*     */       }
/*     */     }
/*     */     
/* 111 */     return this.hash;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\jdbc2\optional\MysqlXid.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */