/*     */ package com.avaje.ebeaninternal.server.transaction;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.server.cluster.BinaryMessage;
/*     */ import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
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
/*     */ public class BeanPathUpdateIds
/*     */ {
/*     */   private BeanDescriptor<?> beanDescriptor;
/*     */   private final String descriptorId;
/*     */   private String path;
/*     */   private ArrayList<Serializable> ids;
/*     */   
/*     */   public BeanPathUpdateIds(BeanDescriptor<?> desc, String path) {
/*  49 */     this.beanDescriptor = desc;
/*  50 */     this.descriptorId = desc.getDescriptorId();
/*  51 */     this.path = path;
/*     */   }
/*     */   
/*     */   public String toString() {
/*  55 */     StringBuilder sb = new StringBuilder();
/*  56 */     if (this.beanDescriptor != null) {
/*  57 */       sb.append(this.beanDescriptor.getFullName());
/*     */     } else {
/*  59 */       sb.append("descId:").append(this.descriptorId);
/*     */     } 
/*  61 */     sb.append(" path:").append(this.path);
/*  62 */     sb.append(" ids:").append(this.ids);
/*  63 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static BeanPathUpdateIds readBinaryMessage(SpiEbeanServer server, DataInput dataInput) throws IOException {
/*  68 */     String descriptorId = dataInput.readUTF();
/*  69 */     String path = dataInput.readUTF();
/*  70 */     BeanDescriptor<?> desc = server.getBeanDescriptorById(descriptorId);
/*  71 */     BeanPathUpdateIds bp = new BeanPathUpdateIds(desc, path);
/*  72 */     bp.read(dataInput);
/*  73 */     return bp;
/*     */   }
/*     */ 
/*     */   
/*     */   private void read(DataInput dataInput) throws IOException {
/*  78 */     IdBinder idBinder = this.beanDescriptor.getIdBinder();
/*  79 */     this.ids = readIdList(dataInput, idBinder);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ArrayList<Serializable> readIdList(DataInput dataInput, IdBinder idBinder) throws IOException {
/*  85 */     int count = dataInput.readInt();
/*  86 */     if (count < 1) {
/*  87 */       return null;
/*     */     }
/*  89 */     ArrayList<Serializable> idList = new ArrayList<Serializable>(count);
/*  90 */     for (int i = 0; i < count; i++) {
/*  91 */       Object id = idBinder.readData(dataInput);
/*  92 */       idList.add((Serializable)id);
/*     */     } 
/*  94 */     return idList;
/*     */   }
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
/*     */   public void writeBinaryMessage(BinaryMessageList msgList) throws IOException {
/* 108 */     IdBinder idBinder = this.beanDescriptor.getIdBinder();
/*     */     
/* 110 */     int count = (this.ids == null) ? 0 : this.ids.size();
/* 111 */     if (count > 0) {
/* 112 */       int loop = 0;
/* 113 */       int i = 0;
/* 114 */       int eof = this.ids.size();
/*     */       do {
/* 116 */         loop++;
/* 117 */         int endOfLoop = Math.min(eof, loop * 100);
/*     */         
/* 119 */         BinaryMessage m = new BinaryMessage(endOfLoop * 4 + 20);
/*     */         
/* 121 */         DataOutputStream os = m.getOs();
/* 122 */         os.writeInt(4);
/* 123 */         os.writeUTF(this.descriptorId);
/* 124 */         os.writeUTF(this.path);
/* 125 */         os.writeInt(count);
/*     */         
/* 127 */         for (; i < endOfLoop; i++) {
/* 128 */           Serializable idValue = (Serializable)this.ids.get(i);
/* 129 */           idBinder.writeData(os, idValue);
/*     */         } 
/*     */         
/* 132 */         os.flush();
/* 133 */         msgList.add(m);
/*     */       }
/* 135 */       while (i < eof);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 140 */   public void addId(Serializable id) { this.ids.add(id); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 145 */   public BeanDescriptor<?> getBeanDescriptor() { return this.beanDescriptor; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 153 */   public String getDescriptorId() { return this.descriptorId; }
/*     */ 
/*     */ 
/*     */   
/* 157 */   public String getPath() { return this.path; }
/*     */ 
/*     */ 
/*     */   
/* 161 */   public List<Serializable> getIds() { return this.ids; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\transaction\BeanPathUpdateIds.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */