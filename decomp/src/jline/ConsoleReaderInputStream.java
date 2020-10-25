/*     */ package jline;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.SequenceInputStream;
/*     */ import java.util.Enumeration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConsoleReaderInputStream
/*     */   extends SequenceInputStream
/*     */ {
/*  19 */   private static InputStream systemIn = System.in;
/*     */ 
/*     */   
/*  22 */   public static void setIn() throws IOException { setIn(new ConsoleReader()); }
/*     */ 
/*     */ 
/*     */   
/*  26 */   public static void setIn(ConsoleReader reader) { System.setIn(new ConsoleReaderInputStream(reader)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   public static void restoreIn() throws IOException { System.setIn(systemIn); }
/*     */ 
/*     */ 
/*     */   
/*  37 */   public ConsoleReaderInputStream(ConsoleReader reader) { super(new ConsoleEnumeration(reader)); }
/*     */   private static class ConsoleEnumeration implements Enumeration { private final ConsoleReader reader; private ConsoleReaderInputStream.ConsoleLineInputStream next;
/*     */     private ConsoleReaderInputStream.ConsoleLineInputStream prev;
/*     */     
/*     */     public ConsoleEnumeration(ConsoleReader reader) {
/*  42 */       this.next = null;
/*  43 */       this.prev = null;
/*     */ 
/*     */       
/*  46 */       this.reader = reader;
/*     */     }
/*     */     
/*     */     public Object nextElement() {
/*  50 */       if (this.next != null) {
/*  51 */         InputStream n = this.next;
/*  52 */         this.prev = this.next;
/*  53 */         this.next = null;
/*     */         
/*  55 */         return n;
/*     */       } 
/*     */       
/*  58 */       return new ConsoleReaderInputStream.ConsoleLineInputStream(this.reader);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasMoreElements() {
/*  63 */       if (this.prev != null && this.prev.wasNull == true) {
/*  64 */         return false;
/*     */       }
/*     */       
/*  67 */       if (this.next == null) {
/*  68 */         this.next = (ConsoleReaderInputStream.ConsoleLineInputStream)nextElement();
/*     */       }
/*     */       
/*  71 */       return (this.next != null);
/*     */     } }
/*     */   private static class ConsoleLineInputStream extends InputStream { private final ConsoleReader reader;
/*     */     private String line;
/*     */     
/*     */     public ConsoleLineInputStream(ConsoleReader reader) {
/*  77 */       this.line = null;
/*  78 */       this.index = 0;
/*  79 */       this.eol = false;
/*  80 */       this.wasNull = false;
/*     */ 
/*     */       
/*  83 */       this.reader = reader;
/*     */     }
/*     */     private int index; private boolean eol; protected boolean wasNull;
/*     */     public int read() throws IOException {
/*  87 */       if (this.eol) {
/*  88 */         return -1;
/*     */       }
/*     */       
/*  91 */       if (this.line == null) {
/*  92 */         this.line = this.reader.readLine();
/*     */       }
/*     */       
/*  95 */       if (this.line == null) {
/*  96 */         this.wasNull = true;
/*  97 */         return -1;
/*     */       } 
/*     */       
/* 100 */       if (this.index >= this.line.length()) {
/* 101 */         this.eol = true;
/* 102 */         return 10;
/*     */       } 
/*     */       
/* 105 */       return this.line.charAt(this.index++);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\jline\ConsoleReaderInputStream.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.0.4
 */