/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
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
/*     */ public abstract class NBTBase
/*     */ {
/*  18 */   private String a = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void a(DataOutput paramDataOutput);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void a(DataInput paramDataInput);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract byte a();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String b() {
/*  45 */     if (this.a == null) return ""; 
/*  46 */     return this.a;
/*     */   }
/*     */   
/*     */   public NBTBase a(String paramString) {
/*  50 */     this.a = paramString;
/*  51 */     return this;
/*     */   }
/*     */   
/*     */   public static NBTBase b(DataInput paramDataInput) {
/*  55 */     byte b = paramDataInput.readByte();
/*  56 */     if (b == 0) return new NBTTagEnd();
/*     */     
/*  58 */     NBTBase nBTBase = a(b);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  63 */     nBTBase.a = paramDataInput.readUTF();
/*  64 */     nBTBase.a(paramDataInput);
/*  65 */     return nBTBase;
/*     */   }
/*     */   
/*     */   public static void a(NBTBase paramNBTBase, DataOutput paramDataOutput) {
/*  69 */     paramDataOutput.writeByte(paramNBTBase.a());
/*  70 */     if (paramNBTBase.a() == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  75 */     paramDataOutput.writeUTF(paramNBTBase.b());
/*     */     
/*  77 */     paramNBTBase.a(paramDataOutput);
/*     */   }
/*     */   
/*     */   public static NBTBase a(byte paramByte) {
/*  81 */     switch (paramByte) {
/*     */       case 0:
/*  83 */         return new NBTTagEnd();
/*     */       case 1:
/*  85 */         return new NBTTagByte();
/*     */       case 2:
/*  87 */         return new NBTTagShort();
/*     */       case 3:
/*  89 */         return new NBTTagInt();
/*     */       case 4:
/*  91 */         return new NBTTagLong();
/*     */       case 5:
/*  93 */         return new NBTTagFloat();
/*     */       case 6:
/*  95 */         return new NBTTagDouble();
/*     */       case 7:
/*  97 */         return new NBTTagByteArray();
/*     */       case 8:
/*  99 */         return new NBTTagString();
/*     */       case 9:
/* 101 */         return new NBTTagList();
/*     */       case 10:
/* 103 */         return new NBTTagCompound();
/*     */     } 
/* 105 */     return null;
/*     */   }
/*     */   
/*     */   public static String b(byte paramByte) {
/* 109 */     switch (paramByte) {
/*     */       case 0:
/* 111 */         return "TAG_End";
/*     */       case 1:
/* 113 */         return "TAG_Byte";
/*     */       case 2:
/* 115 */         return "TAG_Short";
/*     */       case 3:
/* 117 */         return "TAG_Int";
/*     */       case 4:
/* 119 */         return "TAG_Long";
/*     */       case 5:
/* 121 */         return "TAG_Float";
/*     */       case 6:
/* 123 */         return "TAG_Double";
/*     */       case 7:
/* 125 */         return "TAG_Byte_Array";
/*     */       case 8:
/* 127 */         return "TAG_String";
/*     */       case 9:
/* 129 */         return "TAG_List";
/*     */       case 10:
/* 131 */         return "TAG_Compound";
/*     */     } 
/* 133 */     return "UNKNOWN";
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\NBTBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */