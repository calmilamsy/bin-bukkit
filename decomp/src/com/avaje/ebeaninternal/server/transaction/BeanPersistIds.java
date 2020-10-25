/*     */ package com.avaje.ebeaninternal.server.transaction;
/*     */ 
/*     */ import com.avaje.ebean.event.BeanPersistListener;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.server.cluster.BinaryMessage;
/*     */ import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequest;
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
/*     */ public class BeanPersistIds
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8389469180931531409L;
/*     */   private BeanDescriptor<?> beanDescriptor;
/*     */   private final String descriptorId;
/*     */   private ArrayList<Serializable> insertIds;
/*     */   private ArrayList<Serializable> updateIds;
/*     */   private ArrayList<Serializable> deleteIds;
/*     */   
/*     */   public BeanPersistIds(BeanDescriptor<?> desc) {
/*  66 */     this.beanDescriptor = desc;
/*  67 */     this.descriptorId = desc.getDescriptorId();
/*     */   }
/*     */ 
/*     */   
/*     */   public static BeanPersistIds readBinaryMessage(SpiEbeanServer server, DataInput dataInput) throws IOException {
/*  72 */     String descriptorId = dataInput.readUTF();
/*  73 */     BeanDescriptor<?> desc = server.getBeanDescriptorById(descriptorId);
/*  74 */     BeanPersistIds bp = new BeanPersistIds(desc);
/*  75 */     bp.read(dataInput);
/*  76 */     return bp;
/*     */   }
/*     */ 
/*     */   
/*     */   private void read(DataInput dataInput) throws IOException {
/*  81 */     IdBinder idBinder = this.beanDescriptor.getIdBinder();
/*     */     
/*  83 */     int iudType = dataInput.readInt();
/*  84 */     ArrayList<Serializable> idList = readIdList(dataInput, idBinder);
/*     */     
/*  86 */     switch (iudType) {
/*     */       case 0:
/*  88 */         this.insertIds = idList;
/*     */         return;
/*     */       case 1:
/*  91 */         this.updateIds = idList;
/*     */         return;
/*     */       case 2:
/*  94 */         this.deleteIds = idList;
/*     */         return;
/*     */     } 
/*     */     
/*  98 */     throw new RuntimeException("Invalid iudType " + iudType);
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
/*     */   
/*     */   public void writeBinaryMessage(BinaryMessageList msgList) throws IOException {
/* 113 */     writeIdList(this.beanDescriptor, 0, this.insertIds, msgList);
/* 114 */     writeIdList(this.beanDescriptor, 1, this.updateIds, msgList);
/* 115 */     writeIdList(this.beanDescriptor, 2, this.deleteIds, msgList);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ArrayList<Serializable> readIdList(DataInput dataInput, IdBinder idBinder) throws IOException {
/* 121 */     int count = dataInput.readInt();
/* 122 */     if (count < 1) {
/* 123 */       return null;
/*     */     }
/* 125 */     ArrayList<Serializable> idList = new ArrayList<Serializable>(count);
/* 126 */     for (int i = 0; i < count; i++) {
/* 127 */       Object id = idBinder.readData(dataInput);
/* 128 */       idList.add((Serializable)id);
/*     */     } 
/* 130 */     return idList;
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
/*     */ 
/*     */   
/*     */   private void writeIdList(BeanDescriptor<?> desc, int iudType, ArrayList<Serializable> idList, BinaryMessageList msgList) throws IOException {
/* 146 */     IdBinder idBinder = desc.getIdBinder();
/*     */     
/* 148 */     int count = (idList == null) ? 0 : idList.size();
/* 149 */     if (count > 0) {
/* 150 */       int loop = 0;
/* 151 */       int i = 0;
/* 152 */       int eof = idList.size();
/*     */       do {
/* 154 */         loop++;
/* 155 */         int endOfLoop = Math.min(eof, loop * 100);
/*     */         
/* 157 */         BinaryMessage m = new BinaryMessage(endOfLoop * 4 + 20);
/*     */         
/* 159 */         DataOutputStream os = m.getOs();
/* 160 */         os.writeInt(1);
/* 161 */         os.writeUTF(this.descriptorId);
/* 162 */         os.writeInt(iudType);
/* 163 */         os.writeInt(count);
/*     */         
/* 165 */         for (; i < endOfLoop; i++) {
/* 166 */           Serializable idValue = (Serializable)idList.get(i);
/* 167 */           idBinder.writeData(os, idValue);
/*     */         } 
/*     */         
/* 170 */         os.flush();
/* 171 */         msgList.add(m);
/*     */       }
/* 173 */       while (i < eof);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 178 */     StringBuilder sb = new StringBuilder();
/* 179 */     if (this.beanDescriptor != null) {
/* 180 */       sb.append(this.beanDescriptor.getFullName());
/*     */     } else {
/* 182 */       sb.append("descId:").append(this.descriptorId);
/*     */     } 
/* 184 */     if (this.insertIds != null) {
/* 185 */       sb.append(" insertIds:").append(this.insertIds);
/*     */     }
/* 187 */     if (this.updateIds != null) {
/* 188 */       sb.append(" updateIds:").append(this.updateIds);
/*     */     }
/* 190 */     if (this.deleteIds != null) {
/* 191 */       sb.append(" deleteIds:").append(this.deleteIds);
/*     */     }
/* 193 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public void addId(PersistRequest.Type type, Serializable id) {
/* 197 */     switch (type) {
/*     */       case INSERT:
/* 199 */         addInsertId(id);
/*     */         break;
/*     */       case UPDATE:
/* 202 */         addUpdateId(id);
/*     */         break;
/*     */       case DELETE:
/* 205 */         addDeleteId(id);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addInsertId(Serializable id) {
/* 214 */     if (this.insertIds == null) {
/* 215 */       this.insertIds = new ArrayList();
/*     */     }
/* 217 */     this.insertIds.add(id);
/*     */   }
/*     */   
/*     */   private void addUpdateId(Serializable id) {
/* 221 */     if (this.updateIds == null) {
/* 222 */       this.updateIds = new ArrayList();
/*     */     }
/* 224 */     this.updateIds.add(id);
/*     */   }
/*     */   
/*     */   private void addDeleteId(Serializable id) {
/* 228 */     if (this.deleteIds == null) {
/* 229 */       this.deleteIds = new ArrayList();
/*     */     }
/* 231 */     this.deleteIds.add(id);
/*     */   }
/*     */ 
/*     */   
/* 235 */   public BeanDescriptor<?> getBeanDescriptor() { return this.beanDescriptor; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 243 */   public String getDescriptorId() { return this.descriptorId; }
/*     */ 
/*     */ 
/*     */   
/* 247 */   public List<Serializable> getInsertIds() { return this.insertIds; }
/*     */ 
/*     */ 
/*     */   
/* 251 */   public List<Serializable> getUpdateIds() { return this.updateIds; }
/*     */ 
/*     */ 
/*     */   
/* 255 */   public List<Serializable> getDeleteIds() { return this.deleteIds; }
/*     */ 
/*     */ 
/*     */   
/* 259 */   public void setBeanDescriptor(BeanDescriptor<?> beanDescriptor) { this.beanDescriptor = beanDescriptor; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyCacheAndListener() {
/* 268 */     BeanPersistListener<?> listener = this.beanDescriptor.getPersistListener();
/*     */ 
/*     */     
/* 271 */     this.beanDescriptor.queryCacheClear();
/*     */     
/* 273 */     if (this.insertIds != null && 
/* 274 */       listener != null)
/*     */     {
/* 276 */       for (int i = 0; i < this.insertIds.size(); i++) {
/* 277 */         listener.remoteInsert(this.insertIds.get(i));
/*     */       }
/*     */     }
/*     */     
/* 281 */     if (this.updateIds != null) {
/* 282 */       for (int i = 0; i < this.updateIds.size(); i++) {
/* 283 */         Serializable id = (Serializable)this.updateIds.get(i);
/*     */ 
/*     */         
/* 286 */         this.beanDescriptor.cacheRemove(id);
/* 287 */         if (listener != null)
/*     */         {
/* 289 */           listener.remoteInsert(id);
/*     */         }
/*     */       } 
/*     */     }
/* 293 */     if (this.deleteIds != null)
/* 294 */       for (int i = 0; i < this.deleteIds.size(); i++) {
/* 295 */         Serializable id = (Serializable)this.deleteIds.get(i);
/*     */ 
/*     */         
/* 298 */         this.beanDescriptor.cacheRemove(id);
/* 299 */         if (listener != null)
/*     */         {
/* 301 */           listener.remoteInsert(id);
/*     */         }
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\transaction\BeanPersistIds.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */