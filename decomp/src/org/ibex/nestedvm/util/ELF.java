/*     */ package org.ibex.nestedvm.util;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ public class ELF
/*     */ {
/*     */   private static final int ELF_MAGIC = 2135247942;
/*     */   public static final int ELFCLASSNONE = 0;
/*     */   public static final int ELFCLASS32 = 1;
/*     */   public static final int ELFCLASS64 = 2;
/*     */   public static final int ELFDATANONE = 0;
/*     */   public static final int ELFDATA2LSB = 1;
/*     */   public static final int ELFDATA2MSB = 2;
/*     */   public static final int SHT_SYMTAB = 2;
/*     */   public static final int SHT_STRTAB = 3;
/*     */   public static final int SHT_NOBITS = 8;
/*     */   public static final int SHF_WRITE = 1;
/*     */   public static final int SHF_ALLOC = 2;
/*     */   public static final int SHF_EXECINSTR = 4;
/*     */   public static final int PF_X = 1;
/*     */   public static final int PF_W = 2;
/*     */   public static final int PF_R = 4;
/*     */   public static final int PT_LOAD = 1;
/*     */   public static final short ET_EXEC = 2;
/*     */   public static final short EM_MIPS = 8;
/*     */   private Seekable data;
/*     */   public ELFIdent ident;
/*     */   public ELFHeader header;
/*     */   public PHeader[] pheaders;
/*     */   public SHeader[] sheaders;
/*     */   private byte[] stringTable;
/*     */   private boolean sectionReaderActive;
/*     */   private Symtab _symtab;
/*     */   
/*     */   private void readFully(byte[] paramArrayOfByte) throws IOException {
/*  51 */     int i = paramArrayOfByte.length;
/*  52 */     int j = 0;
/*  53 */     while (i > 0) {
/*  54 */       int k = this.data.read(paramArrayOfByte, j, i);
/*  55 */       if (k == -1) throw new IOException("EOF"); 
/*  56 */       j += k;
/*  57 */       i -= k;
/*     */     } 
/*     */   }
/*     */   
/*     */   private int readIntBE() throws IOException {
/*  62 */     byte[] arrayOfByte = new byte[4];
/*  63 */     readFully(arrayOfByte);
/*  64 */     return (arrayOfByte[0] & 0xFF) << 24 | (arrayOfByte[1] & 0xFF) << 16 | (arrayOfByte[2] & 0xFF) << 8 | (arrayOfByte[3] & 0xFF) << 0;
/*     */   }
/*     */   private int readInt() throws IOException {
/*  67 */     int i = readIntBE();
/*  68 */     if (this.ident != null && this.ident.data == 1)
/*  69 */       i = i << 24 & 0xFF000000 | i << 8 & 0xFF0000 | i >>> 8 & 0xFF00 | i >> 24 & 0xFF; 
/*  70 */     return i;
/*     */   }
/*     */   
/*     */   private short readShortBE() throws IOException {
/*  74 */     byte[] arrayOfByte = new byte[2];
/*  75 */     readFully(arrayOfByte);
/*  76 */     return (short)((arrayOfByte[0] & 0xFF) << 8 | (arrayOfByte[1] & 0xFF) << 0);
/*     */   }
/*     */   private short readShort() throws IOException {
/*  79 */     short s = readShortBE();
/*  80 */     if (this.ident != null && this.ident.data == 1)
/*  81 */       s = (short)((s << 8 & 0xFF00 | s >> 8 & 0xFF) & 0xFFFF); 
/*  82 */     return s;
/*     */   }
/*     */   
/*     */   private byte readByte() throws IOException {
/*  86 */     byte[] arrayOfByte = new byte[1];
/*  87 */     readFully(arrayOfByte);
/*  88 */     return arrayOfByte[0];
/*     */   }
/*     */   public class ELFIdent { public byte klass;
/*     */     public byte data;
/*     */     public byte osabi;
/*     */     public byte abiversion;
/*     */     private final ELF this$0;
/*     */     
/*     */     ELFIdent(ELF this$0) throws IOException {
/*  97 */       this.this$0 = this$0;
/*  98 */       if (this$0.readIntBE() != 2135247942) throw new ELF.ELFException(this$0, "Bad Magic");
/*     */       
/* 100 */       this.klass = this$0.readByte();
/* 101 */       if (this.klass != 1) throw new ELF.ELFException(this$0, "org.ibex.nestedvm.util.ELF does not suport 64-bit binaries");
/*     */       
/* 103 */       this.data = this$0.readByte();
/* 104 */       if (this.data != 1 && this.data != 2) throw new ELF.ELFException(this$0, "Unknown byte order");
/*     */       
/* 106 */       this$0.readByte();
/* 107 */       this.osabi = this$0.readByte();
/* 108 */       this.abiversion = this$0.readByte();
/* 109 */       for (byte b = 0; b < 7; ) { this$0.readByte(); b++; }
/*     */     
/*     */     } }
/*     */   public class ELFHeader { public short type;
/*     */     public short machine;
/*     */     public int version;
/*     */     public int entry;
/*     */     public int phoff;
/*     */     public int shoff;
/*     */     public int flags;
/*     */     public short ehsize;
/*     */     public short phentsize;
/*     */     public short phnum;
/*     */     public short shentsize;
/*     */     public short shnum;
/*     */     public short shstrndx;
/*     */     private final ELF this$0;
/*     */     
/*     */     ELFHeader(ELF this$0) throws IOException {
/* 128 */       this.this$0 = this$0;
/* 129 */       this.type = this$0.readShort();
/* 130 */       this.machine = this$0.readShort();
/* 131 */       this.version = this$0.readInt();
/* 132 */       if (this.version != 1) throw new ELF.ELFException(this$0, "version != 1"); 
/* 133 */       this.entry = this$0.readInt();
/* 134 */       this.phoff = this$0.readInt();
/* 135 */       this.shoff = this$0.readInt();
/* 136 */       this.flags = this$0.readInt();
/* 137 */       this.ehsize = this$0.readShort();
/* 138 */       this.phentsize = this$0.readShort();
/* 139 */       this.phnum = this$0.readShort();
/* 140 */       this.shentsize = this$0.readShort();
/* 141 */       this.shnum = this$0.readShort();
/* 142 */       this.shstrndx = this$0.readShort();
/*     */     } }
/*     */   
/*     */   public class PHeader { public int type;
/*     */     public int offset;
/*     */     public int vaddr;
/*     */     public int paddr;
/*     */     public int filesz;
/*     */     public int memsz;
/*     */     public int flags;
/*     */     public int align;
/*     */     private final ELF this$0;
/*     */     
/*     */     PHeader(ELF this$0) throws IOException {
/* 156 */       this.this$0 = this$0;
/* 157 */       this.type = this$0.readInt();
/* 158 */       this.offset = this$0.readInt();
/* 159 */       this.vaddr = this$0.readInt();
/* 160 */       this.paddr = this$0.readInt();
/* 161 */       this.filesz = this$0.readInt();
/* 162 */       this.memsz = this$0.readInt();
/* 163 */       this.flags = this$0.readInt();
/* 164 */       this.align = this$0.readInt();
/* 165 */       if (this.filesz > this.memsz) throw new ELF.ELFException(this$0, "ELF inconsistency: filesz > memsz (" + ELF.toHex(this.filesz) + " > " + ELF.toHex(this.memsz) + ")"); 
/*     */     }
/*     */     
/* 168 */     public boolean writable() { return ((this.flags & 0x2) != 0); }
/*     */ 
/*     */     
/* 171 */     public InputStream getInputStream() throws IOException { return new BufferedInputStream(new ELF.SectionInputStream(this.this$0, this.offset, this.offset + this.filesz)); } }
/*     */ 
/*     */   
/*     */   public class SHeader {
/*     */     int nameidx;
/*     */     public String name;
/*     */     public int type;
/*     */     public int flags;
/*     */     public int addr;
/*     */     public int offset;
/*     */     public int size;
/*     */     public int link;
/*     */     public int info;
/*     */     public int addralign;
/*     */     public int entsize;
/*     */     private final ELF this$0;
/*     */     
/*     */     SHeader(ELF this$0) throws IOException {
/* 189 */       this.this$0 = this$0;
/* 190 */       this.nameidx = this$0.readInt();
/* 191 */       this.type = this$0.readInt();
/* 192 */       this.flags = this$0.readInt();
/* 193 */       this.addr = this$0.readInt();
/* 194 */       this.offset = this$0.readInt();
/* 195 */       this.size = this$0.readInt();
/* 196 */       this.link = this$0.readInt();
/* 197 */       this.info = this$0.readInt();
/* 198 */       this.addralign = this$0.readInt();
/* 199 */       this.entsize = this$0.readInt();
/*     */     }
/*     */ 
/*     */     
/* 203 */     public InputStream getInputStream() throws IOException { return new BufferedInputStream(new ELF.SectionInputStream(this.this$0, this.offset, (this.type == 8) ? 0 : (this.offset + this.size))); }
/*     */ 
/*     */ 
/*     */     
/* 207 */     public boolean isText() { return this.name.equals(".text"); }
/* 208 */     public boolean isData() { return (this.name.equals(".data") || this.name.equals(".sdata") || this.name.equals(".rodata") || this.name.equals(".ctors") || this.name.equals(".dtors")); }
/* 209 */     public boolean isBSS() { return (this.name.equals(".bss") || this.name.equals(".sbss")); }
/*     */   }
/*     */   
/* 212 */   public ELF(String paramString) throws IOException, ELFException { this(new Seekable.File(paramString, false)); }
/*     */   public ELF(Seekable paramSeekable) throws IOException, ELFException {
/* 214 */     this.data = paramSeekable;
/* 215 */     this.ident = new ELFIdent(this);
/* 216 */     this.header = new ELFHeader(this);
/* 217 */     this.pheaders = new PHeader[this.header.phnum]; short s;
/* 218 */     for (s = 0; s < this.header.phnum; s++) {
/* 219 */       paramSeekable.seek(this.header.phoff + s * this.header.phentsize);
/* 220 */       this.pheaders[s] = new PHeader(this);
/*     */     } 
/* 222 */     this.sheaders = new SHeader[this.header.shnum];
/* 223 */     for (s = 0; s < this.header.shnum; s++) {
/* 224 */       paramSeekable.seek(this.header.shoff + s * this.header.shentsize);
/* 225 */       this.sheaders[s] = new SHeader(this);
/*     */     } 
/* 227 */     if (this.header.shstrndx < 0 || this.header.shstrndx >= this.header.shnum) throw new ELFException(this, "Bad shstrndx"); 
/* 228 */     paramSeekable.seek((this.sheaders[this.header.shstrndx]).offset);
/* 229 */     this.stringTable = new byte[(this.sheaders[this.header.shstrndx]).size];
/* 230 */     readFully(this.stringTable);
/*     */     
/* 232 */     for (s = 0; s < this.header.shnum; s++) {
/* 233 */       SHeader sHeader = this.sheaders[s];
/* 234 */       sHeader.name = getString(sHeader.nameidx);
/*     */     } 
/*     */   }
/*     */   
/* 238 */   private String getString(int paramInt) { return getString(paramInt, this.stringTable); }
/*     */   private String getString(int paramInt, byte[] paramArrayOfByte) {
/* 240 */     StringBuffer stringBuffer = new StringBuffer();
/* 241 */     if (paramInt < 0 || paramInt >= paramArrayOfByte.length) return "<invalid strtab entry>"; 
/* 242 */     for (; paramInt >= 0 && paramInt < paramArrayOfByte.length && paramArrayOfByte[paramInt] != 0; stringBuffer.append((char)paramArrayOfByte[paramInt++]));
/* 243 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   public SHeader sectionWithName(String paramString) {
/* 247 */     for (byte b = 0; b < this.sheaders.length; b++) {
/* 248 */       if ((this.sheaders[b]).name.equals(paramString))
/* 249 */         return this.sheaders[b]; 
/* 250 */     }  return null;
/*     */   }
/*     */   public class ELFException extends IOException { private final ELF this$0;
/* 253 */     ELFException(ELF this$0, String param1String) { super(param1String); this.this$0 = this$0; } }
/*     */   
/*     */   private class SectionInputStream extends InputStream { private int pos;
/*     */     
/*     */     SectionInputStream(ELF this$0, int param1Int1, int param1Int2) throws IOException {
/* 258 */       this.this$0 = this$0;
/* 259 */       if (this$0.sectionReaderActive)
/* 260 */         throw new IOException("Section reader already active"); 
/* 261 */       this$0.sectionReaderActive = true;
/* 262 */       this.pos = param1Int1;
/* 263 */       this$0.data.seek(this.pos);
/* 264 */       this.maxpos = param1Int2;
/*     */     }
/*     */     private int maxpos; private final ELF this$0;
/* 267 */     private int bytesLeft() throws IOException { return this.maxpos - this.pos; }
/*     */     public int read() throws IOException {
/* 269 */       byte[] arrayOfByte = new byte[1];
/* 270 */       return (read(arrayOfByte, 0, 1) == -1) ? -1 : (arrayOfByte[0] & 0xFF);
/*     */     }
/*     */     public int read(byte[] param1ArrayOfByte, int param1Int1, int param1Int2) throws IOException {
/* 273 */       int i = this.this$0.data.read(param1ArrayOfByte, param1Int1, Math.min(param1Int2, bytesLeft())); if (i > 0) this.pos += i;  return i;
/*     */     }
/* 275 */     public void close() { this.this$0.sectionReaderActive = false; } }
/*     */ 
/*     */ 
/*     */   
/*     */   public Symtab getSymtab() throws IOException {
/* 280 */     if (this._symtab != null) return this._symtab;
/*     */     
/* 282 */     if (this.sectionReaderActive) throw new ELFException(this, "Can't read the symtab while a section reader is active");
/*     */     
/* 284 */     SHeader sHeader1 = sectionWithName(".symtab");
/* 285 */     if (sHeader1 == null || sHeader1.type != 2) return null;
/*     */     
/* 287 */     SHeader sHeader2 = sectionWithName(".strtab");
/* 288 */     if (sHeader2 == null || sHeader2.type != 3) return null;
/*     */     
/* 290 */     byte[] arrayOfByte = new byte[sHeader2.size];
/* 291 */     DataInputStream dataInputStream = new DataInputStream(sHeader2.getInputStream());
/* 292 */     dataInputStream.readFully(arrayOfByte);
/* 293 */     dataInputStream.close();
/*     */     
/* 295 */     return this._symtab = new Symtab(this, sHeader1.offset, sHeader1.size, arrayOfByte);
/*     */   }
/*     */   public class Symtab { public ELF.Symbol[] symbols;
/*     */     private final ELF this$0;
/*     */     
/*     */     Symtab(ELF this$0, int param1Int1, int param1Int2, byte[] param1ArrayOfByte) throws IOException {
/* 301 */       this.this$0 = this$0;
/* 302 */       this$0.data.seek(param1Int1);
/* 303 */       int i = param1Int2 / 16;
/* 304 */       this.symbols = new ELF.Symbol[i];
/* 305 */       for (byte b = 0; b < i; ) { this.symbols[b] = new ELF.Symbol(this$0, param1ArrayOfByte); b++; }
/*     */     
/*     */     }
/*     */     public ELF.Symbol getSymbol(String param1String) {
/* 309 */       ELF.Symbol symbol = null;
/* 310 */       for (byte b = 0; b < this.symbols.length; b++) {
/* 311 */         if ((this.symbols[b]).name.equals(param1String))
/* 312 */           if (symbol == null) {
/* 313 */             symbol = this.symbols[b];
/*     */           } else {
/* 315 */             System.err.println("WARNING: Multiple symbol matches for " + param1String);
/*     */           }  
/*     */       } 
/* 318 */       return symbol;
/*     */     }
/*     */     
/*     */     public ELF.Symbol getGlobalSymbol(String param1String) {
/* 322 */       for (byte b = 0; b < this.symbols.length; b++) {
/* 323 */         if ((this.symbols[b]).binding == 1 && (this.symbols[b]).name.equals(param1String))
/* 324 */           return this.symbols[b]; 
/* 325 */       }  return null;
/*     */     } }
/*     */   
/*     */   public class Symbol {
/*     */     public String name;
/*     */     public int addr;
/*     */     public int size;
/*     */     public byte info;
/*     */     public byte type;
/*     */     public byte binding;
/*     */     public byte other;
/*     */     public short shndx;
/*     */     public ELF.SHeader sheader;
/*     */     public static final int STT_FUNC = 2;
/*     */     public static final int STB_GLOBAL = 1;
/*     */     private final ELF this$0;
/*     */     
/*     */     Symbol(ELF this$0, byte[] param1ArrayOfByte) throws IOException {
/* 343 */       this.this$0 = this$0;
/* 344 */       this.name = this$0.getString(this$0.readInt(), param1ArrayOfByte);
/* 345 */       this.addr = this$0.readInt();
/* 346 */       this.size = this$0.readInt();
/* 347 */       this.info = this$0.readByte();
/* 348 */       this.type = (byte)(this.info & 0xF);
/* 349 */       this.binding = (byte)(this.info >> 4);
/* 350 */       this.other = this$0.readByte();
/* 351 */       this.shndx = this$0.readShort();
/*     */     }
/*     */   }
/*     */   
/* 355 */   private static String toHex(int paramInt) { return "0x" + Long.toString(paramInt & 0xFFFFFFFFL, 16); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\ibex\nestedv\\util\ELF.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.0.4
 */