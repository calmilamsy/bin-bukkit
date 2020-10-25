/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class DataWatcher
/*     */ {
/*  25 */   private static final HashMap a = new HashMap();
/*     */   static  {
/*  27 */     a.put(Byte.class, Integer.valueOf(0));
/*  28 */     a.put(Short.class, Integer.valueOf(1));
/*  29 */     a.put(Integer.class, Integer.valueOf(2));
/*  30 */     a.put(Float.class, Integer.valueOf(3));
/*  31 */     a.put(String.class, Integer.valueOf(4));
/*  32 */     a.put(ItemStack.class, Integer.valueOf(5));
/*  33 */     a.put(ChunkCoordinates.class, Integer.valueOf(6));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   private final Map b = new HashMap();
/*     */ 
/*     */   
/*     */   private boolean c;
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int paramInt, Object paramObject) {
/*  51 */     Integer integer = (Integer)a.get(paramObject.getClass());
/*  52 */     if (integer == null) {
/*  53 */       throw new IllegalArgumentException("Unknown data type: " + paramObject.getClass());
/*     */     }
/*  55 */     if (paramInt > 31) {
/*  56 */       throw new IllegalArgumentException("Data value id is too big with " + paramInt + "! (Max is " + '\037' + ")");
/*     */     }
/*  58 */     if (this.b.containsKey(Integer.valueOf(paramInt))) {
/*  59 */       throw new IllegalArgumentException("Duplicate id value for " + paramInt + "!");
/*     */     }
/*     */     
/*  62 */     WatchableObject watchableObject = new WatchableObject(integer.intValue(), paramInt, paramObject);
/*  63 */     this.b.put(Integer.valueOf(paramInt), watchableObject);
/*     */   }
/*     */ 
/*     */   
/*  67 */   public byte a(int paramInt) { return ((Byte)((WatchableObject)this.b.get(Integer.valueOf(paramInt))).b()).byteValue(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   public int b(int paramInt) { return ((Integer)((WatchableObject)this.b.get(Integer.valueOf(paramInt))).b()).intValue(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   public String c(int paramInt) { return (String)((WatchableObject)this.b.get(Integer.valueOf(paramInt))).b(); }
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
/*     */   public void watch(int paramInt, Object paramObject) {
/*  95 */     WatchableObject watchableObject = (WatchableObject)this.b.get(Integer.valueOf(paramInt));
/*     */ 
/*     */     
/*  98 */     if (!paramObject.equals(watchableObject.b())) {
/*  99 */       watchableObject.a(paramObject);
/* 100 */       watchableObject.a(true);
/* 101 */       this.c = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   public boolean a() { return this.c; }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void a(List paramList, DataOutputStream paramDataOutputStream) {
/* 116 */     if (paramList != null) {
/* 117 */       for (WatchableObject watchableObject : paramList) {
/* 118 */         a(paramDataOutputStream, watchableObject);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 123 */     paramDataOutputStream.writeByte(127);
/*     */   }
/*     */ 
/*     */   
/*     */   public ArrayList b() {
/* 128 */     ArrayList arrayList = null;
/*     */     
/* 130 */     if (this.c) {
/* 131 */       for (WatchableObject watchableObject : this.b.values()) {
/* 132 */         if (watchableObject.d()) {
/* 133 */           watchableObject.a(false);
/*     */           
/* 135 */           if (arrayList == null) {
/* 136 */             arrayList = new ArrayList();
/*     */           }
/* 138 */           arrayList.add(watchableObject);
/*     */         } 
/*     */       } 
/*     */     }
/* 142 */     this.c = false;
/*     */     
/* 144 */     return arrayList;
/*     */   }
/*     */   
/*     */   public void a(DataOutputStream paramDataOutputStream) {
/* 148 */     for (WatchableObject watchableObject : this.b.values()) {
/* 149 */       a(paramDataOutputStream, watchableObject);
/*     */     }
/*     */ 
/*     */     
/* 153 */     paramDataOutputStream.writeByte(127);
/*     */   }
/*     */   private static void a(DataOutputStream paramDataOutputStream, WatchableObject paramWatchableObject) {
/*     */     ItemStack itemStack;
/*     */     ChunkCoordinates chunkCoordinates;
/* 158 */     int i = (paramWatchableObject.c() << 5 | paramWatchableObject.a() & 0x1F) & 0xFF;
/* 159 */     paramDataOutputStream.writeByte(i);
/*     */ 
/*     */     
/* 162 */     switch (paramWatchableObject.c()) {
/*     */       case 0:
/* 164 */         paramDataOutputStream.writeByte(((Byte)paramWatchableObject.b()).byteValue());
/*     */         break;
/*     */       case 1:
/* 167 */         paramDataOutputStream.writeShort(((Short)paramWatchableObject.b()).shortValue());
/*     */         break;
/*     */       case 2:
/* 170 */         paramDataOutputStream.writeInt(((Integer)paramWatchableObject.b()).intValue());
/*     */         break;
/*     */       case 3:
/* 173 */         paramDataOutputStream.writeFloat(((Float)paramWatchableObject.b()).floatValue());
/*     */         break;
/*     */       case 4:
/* 176 */         Packet.a((String)paramWatchableObject.b(), paramDataOutputStream);
/*     */         break;
/*     */       case 5:
/* 179 */         itemStack = (ItemStack)paramWatchableObject.b();
/* 180 */         paramDataOutputStream.writeShort((itemStack.getItem()).id);
/* 181 */         paramDataOutputStream.writeByte(itemStack.count);
/* 182 */         paramDataOutputStream.writeShort(itemStack.getData());
/*     */         break;
/*     */       
/*     */       case 6:
/* 186 */         chunkCoordinates = (ChunkCoordinates)paramWatchableObject.b();
/* 187 */         paramDataOutputStream.writeInt(chunkCoordinates.x);
/* 188 */         paramDataOutputStream.writeInt(chunkCoordinates.y);
/* 189 */         paramDataOutputStream.writeInt(chunkCoordinates.z);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static List a(DataInputStream paramDataInputStream) {
/* 197 */     ArrayList arrayList = null;
/*     */     
/* 199 */     byte b1 = paramDataInputStream.readByte();
/*     */     
/* 201 */     while (b1 != Byte.MAX_VALUE) {
/*     */       short s2; int k; byte b4; int j; short s1; int i;
/* 203 */       if (arrayList == null) {
/* 204 */         arrayList = new ArrayList();
/*     */       }
/*     */ 
/*     */       
/* 208 */       byte b2 = (b1 & 0xE0) >> 5;
/* 209 */       byte b3 = b1 & 0x1F;
/*     */       
/* 211 */       WatchableObject watchableObject = null;
/* 212 */       switch (b2) {
/*     */         case 0:
/* 214 */           watchableObject = new WatchableObject(b2, b3, Byte.valueOf(paramDataInputStream.readByte()));
/*     */           break;
/*     */         case 1:
/* 217 */           watchableObject = new WatchableObject(b2, b3, Short.valueOf(paramDataInputStream.readShort()));
/*     */           break;
/*     */         case 2:
/* 220 */           watchableObject = new WatchableObject(b2, b3, Integer.valueOf(paramDataInputStream.readInt()));
/*     */           break;
/*     */         case 3:
/* 223 */           watchableObject = new WatchableObject(b2, b3, Float.valueOf(paramDataInputStream.readFloat()));
/*     */           break;
/*     */         case 4:
/* 226 */           watchableObject = new WatchableObject(b2, b3, Packet.a(paramDataInputStream, 64));
/*     */           break;
/*     */         case 5:
/* 229 */           s1 = paramDataInputStream.readShort();
/* 230 */           b4 = paramDataInputStream.readByte();
/* 231 */           s2 = paramDataInputStream.readShort();
/* 232 */           watchableObject = new WatchableObject(b2, b3, new ItemStack(s1, b4, s2));
/*     */           break;
/*     */         
/*     */         case 6:
/* 236 */           i = paramDataInputStream.readInt();
/* 237 */           j = paramDataInputStream.readInt();
/* 238 */           k = paramDataInputStream.readInt();
/* 239 */           watchableObject = new WatchableObject(b2, b3, new ChunkCoordinates(i, j, k));
/*     */           break;
/*     */       } 
/*     */       
/* 243 */       arrayList.add(watchableObject);
/*     */       
/* 245 */       b1 = paramDataInputStream.readByte();
/*     */     } 
/*     */     
/* 248 */     return arrayList;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\DataWatcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */