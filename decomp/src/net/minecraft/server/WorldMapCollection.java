/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldMapCollection
/*     */ {
/*     */   private IDataManager a;
/*     */   private Map b;
/*     */   private List c;
/*     */   private Map d;
/*     */   
/*     */   public WorldMapCollection(IDataManager paramIDataManager) {
/*  23 */     this.b = new HashMap();
/*  24 */     this.c = new ArrayList();
/*  25 */     this.d = new HashMap();
/*     */ 
/*     */     
/*  28 */     this.a = paramIDataManager;
/*  29 */     b();
/*     */   }
/*     */   
/*     */   public WorldMapBase a(Class paramClass, String paramString) {
/*  33 */     WorldMapBase worldMapBase = (WorldMapBase)this.b.get(paramString);
/*  34 */     if (worldMapBase != null) return worldMapBase;
/*     */     
/*  36 */     if (this.a != null) {
/*     */       try {
/*  38 */         File file = this.a.b(paramString);
/*  39 */         if (file != null && file.exists()) {
/*     */           try {
/*  41 */             worldMapBase = (WorldMapBase)paramClass.getConstructor(new Class[] { String.class }).newInstance(new Object[] { paramString });
/*  42 */           } catch (Exception exception) {
/*  43 */             throw new RuntimeException("Failed to instantiate " + paramClass.toString(), exception);
/*     */           } 
/*     */           
/*  46 */           FileInputStream fileInputStream = new FileInputStream(file);
/*  47 */           NBTTagCompound nBTTagCompound = CompressedStreamTools.a(fileInputStream);
/*  48 */           fileInputStream.close();
/*     */           
/*  50 */           worldMapBase.a(nBTTagCompound.k("data"));
/*     */         } 
/*  52 */       } catch (Exception exception) {
/*  53 */         exception.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  57 */     if (worldMapBase != null) {
/*  58 */       this.b.put(paramString, worldMapBase);
/*  59 */       this.c.add(worldMapBase);
/*     */     } 
/*  61 */     return worldMapBase;
/*     */   }
/*     */   
/*     */   public void a(String paramString, WorldMapBase paramWorldMapBase) {
/*  65 */     if (paramWorldMapBase == null) throw new RuntimeException("Can't set null data"); 
/*  66 */     if (this.b.containsKey(paramString)) {
/*  67 */       this.c.remove(this.b.remove(paramString));
/*     */     }
/*  69 */     this.b.put(paramString, paramWorldMapBase);
/*  70 */     this.c.add(paramWorldMapBase);
/*     */   }
/*     */   
/*     */   public void a() {
/*  74 */     for (byte b1 = 0; b1 < this.c.size(); b1++) {
/*  75 */       WorldMapBase worldMapBase = (WorldMapBase)this.c.get(b1);
/*  76 */       if (worldMapBase.b()) {
/*  77 */         a(worldMapBase);
/*  78 */         worldMapBase.a(false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void a(WorldMapBase paramWorldMapBase) {
/*  84 */     if (this.a == null)
/*     */       return;  try {
/*  86 */       File file = this.a.b(paramWorldMapBase.a);
/*  87 */       if (file != null) {
/*  88 */         NBTTagCompound nBTTagCompound1 = new NBTTagCompound();
/*  89 */         paramWorldMapBase.b(nBTTagCompound1);
/*     */         
/*  91 */         NBTTagCompound nBTTagCompound2 = new NBTTagCompound();
/*  92 */         nBTTagCompound2.a("data", nBTTagCompound1);
/*     */         
/*  94 */         FileOutputStream fileOutputStream = new FileOutputStream(file);
/*  95 */         CompressedStreamTools.a(nBTTagCompound2, fileOutputStream);
/*  96 */         fileOutputStream.close();
/*     */       } 
/*  98 */     } catch (Exception exception) {
/*  99 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void b() {
/*     */     try {
/* 105 */       this.d.clear();
/* 106 */       if (this.a == null)
/* 107 */         return;  File file = this.a.b("idcounts");
/* 108 */       if (file != null && file.exists()) {
/* 109 */         DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
/* 110 */         NBTTagCompound nBTTagCompound = CompressedStreamTools.a(dataInputStream);
/* 111 */         dataInputStream.close();
/*     */         
/* 113 */         for (NBTBase nBTBase : nBTTagCompound.c()) {
/* 114 */           if (nBTBase instanceof NBTTagShort) {
/* 115 */             NBTTagShort nBTTagShort = (NBTTagShort)nBTBase;
/* 116 */             String str = nBTTagShort.b();
/* 117 */             short s = nBTTagShort.a;
/* 118 */             this.d.put(str, Short.valueOf(s));
/*     */           } 
/*     */         } 
/*     */       } 
/* 122 */     } catch (Exception exception) {
/* 123 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public int a(String paramString) {
/* 128 */     Short short = (Short)this.d.get(paramString);
/* 129 */     if (short == null) {
/* 130 */       short = Short.valueOf((short)0);
/*     */     } else {
/* 132 */       Short short1, short2 = short = (short1 = short).valueOf((short)(short.shortValue() + 1)); short1;
/*     */     } 
/*     */     
/* 135 */     this.d.put(paramString, short);
/* 136 */     if (this.a == null) return short.shortValue(); 
/*     */     try {
/* 138 */       File file = this.a.b("idcounts");
/* 139 */       if (file != null) {
/* 140 */         NBTTagCompound nBTTagCompound = new NBTTagCompound();
/*     */         
/* 142 */         for (String str : this.d.keySet()) {
/* 143 */           short s = ((Short)this.d.get(str)).shortValue();
/* 144 */           nBTTagCompound.a(str, s);
/*     */         } 
/*     */         
/* 147 */         DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file));
/* 148 */         CompressedStreamTools.a(nBTTagCompound, dataOutputStream);
/* 149 */         dataOutputStream.close();
/*     */       } 
/* 151 */     } catch (Exception exception) {
/* 152 */       exception.printStackTrace();
/*     */     } 
/* 154 */     return short.shortValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\WorldMapCollection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */