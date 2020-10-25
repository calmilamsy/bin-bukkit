/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class NBTTagCompound
/*     */   extends NBTBase
/*     */ {
/*  12 */   private Map a = new HashMap();
/*     */   
/*     */   void a(DataOutput paramDataOutput) {
/*  15 */     for (NBTBase nBTBase : this.a.values()) {
/*  16 */       NBTBase.a(nBTBase, paramDataOutput);
/*     */     }
/*  18 */     paramDataOutput.writeByte(0);
/*     */   }
/*     */   
/*     */   void a(DataInput paramDataInput) {
/*  22 */     this.a.clear();
/*     */     NBTBase nBTBase;
/*  24 */     while ((nBTBase = NBTBase.b(paramDataInput)).a() != 0) {
/*  25 */       this.a.put(nBTBase.b(), nBTBase);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  30 */   public Collection c() { return this.a.values(); }
/*     */ 
/*     */ 
/*     */   
/*  34 */   public byte a() { return 10; }
/*     */ 
/*     */ 
/*     */   
/*  38 */   public void a(String paramString, NBTBase paramNBTBase) { this.a.put(paramString, paramNBTBase.a(paramString)); }
/*     */ 
/*     */ 
/*     */   
/*  42 */   public void a(String paramString, byte paramByte) { this.a.put(paramString, (new NBTTagByte(paramByte)).a(paramString)); }
/*     */ 
/*     */ 
/*     */   
/*  46 */   public void a(String paramString, short paramShort) { this.a.put(paramString, (new NBTTagShort(paramShort)).a(paramString)); }
/*     */ 
/*     */ 
/*     */   
/*  50 */   public void a(String paramString, int paramInt) { this.a.put(paramString, (new NBTTagInt(paramInt)).a(paramString)); }
/*     */ 
/*     */ 
/*     */   
/*  54 */   public void setLong(String paramString, long paramLong) { this.a.put(paramString, (new NBTTagLong(paramLong)).a(paramString)); }
/*     */ 
/*     */ 
/*     */   
/*  58 */   public void a(String paramString, float paramFloat) { this.a.put(paramString, (new NBTTagFloat(paramFloat)).a(paramString)); }
/*     */ 
/*     */ 
/*     */   
/*  62 */   public void a(String paramString, double paramDouble) { this.a.put(paramString, (new NBTTagDouble(paramDouble)).a(paramString)); }
/*     */ 
/*     */ 
/*     */   
/*  66 */   public void setString(String paramString1, String paramString2) { this.a.put(paramString1, (new NBTTagString(paramString2)).a(paramString1)); }
/*     */ 
/*     */ 
/*     */   
/*  70 */   public void a(String paramString, byte[] paramArrayOfByte) { this.a.put(paramString, (new NBTTagByteArray(paramArrayOfByte)).a(paramString)); }
/*     */ 
/*     */ 
/*     */   
/*  74 */   public void a(String paramString, NBTTagCompound paramNBTTagCompound) { this.a.put(paramString, paramNBTTagCompound.a(paramString)); }
/*     */ 
/*     */ 
/*     */   
/*  78 */   public void a(String paramString, boolean paramBoolean) { a(paramString, paramBoolean ? 1 : 0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   public boolean hasKey(String paramString) { return this.a.containsKey(paramString); }
/*     */ 
/*     */   
/*     */   public byte c(String paramString) {
/*  90 */     if (!this.a.containsKey(paramString)) return 0; 
/*  91 */     return ((NBTTagByte)this.a.get(paramString)).a;
/*     */   }
/*     */   
/*     */   public short d(String paramString) {
/*  95 */     if (!this.a.containsKey(paramString)) return 0; 
/*  96 */     return ((NBTTagShort)this.a.get(paramString)).a;
/*     */   }
/*     */   
/*     */   public int e(String paramString) {
/* 100 */     if (!this.a.containsKey(paramString)) return 0; 
/* 101 */     return ((NBTTagInt)this.a.get(paramString)).a;
/*     */   }
/*     */   
/*     */   public long getLong(String paramString) {
/* 105 */     if (!this.a.containsKey(paramString)) return 0L; 
/* 106 */     return ((NBTTagLong)this.a.get(paramString)).a;
/*     */   }
/*     */   
/*     */   public float g(String paramString) {
/* 110 */     if (!this.a.containsKey(paramString)) return 0.0F; 
/* 111 */     return ((NBTTagFloat)this.a.get(paramString)).a;
/*     */   }
/*     */   
/*     */   public double h(String paramString) {
/* 115 */     if (!this.a.containsKey(paramString)) return 0.0D; 
/* 116 */     return ((NBTTagDouble)this.a.get(paramString)).a;
/*     */   }
/*     */   
/*     */   public String getString(String paramString) {
/* 120 */     if (!this.a.containsKey(paramString)) return ""; 
/* 121 */     return ((NBTTagString)this.a.get(paramString)).a;
/*     */   }
/*     */   
/*     */   public byte[] j(String paramString) {
/* 125 */     if (!this.a.containsKey(paramString)) return new byte[0]; 
/* 126 */     return ((NBTTagByteArray)this.a.get(paramString)).a;
/*     */   }
/*     */   
/*     */   public NBTTagCompound k(String paramString) {
/* 130 */     if (!this.a.containsKey(paramString)) return new NBTTagCompound(); 
/* 131 */     return (NBTTagCompound)this.a.get(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagList l(String paramString) {
/* 136 */     if (!this.a.containsKey(paramString)) return new NBTTagList(); 
/* 137 */     return (NBTTagList)this.a.get(paramString);
/*     */   }
/*     */ 
/*     */   
/* 141 */   public boolean m(String paramString) { return (c(paramString) != 0); }
/*     */ 
/*     */ 
/*     */   
/* 145 */   public String toString() { return "" + this.a.size() + " entries"; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\NBTTagCompound.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */