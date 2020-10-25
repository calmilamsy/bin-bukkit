/*     */ package jline;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStreamReader;
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
/*     */ public class ANSIBuffer
/*     */ {
/*     */   private boolean ansiEnabled = true;
/*  18 */   private final StringBuffer ansiBuffer = new StringBuffer();
/*  19 */   private final StringBuffer plainBuffer = new StringBuffer();
/*     */ 
/*     */   
/*     */   public ANSIBuffer() {}
/*     */ 
/*     */   
/*  25 */   public ANSIBuffer(String str) { append(str); }
/*     */ 
/*     */ 
/*     */   
/*  29 */   public void setAnsiEnabled(boolean ansi) { this.ansiEnabled = ansi; }
/*     */ 
/*     */ 
/*     */   
/*  33 */   public boolean getAnsiEnabled() { return this.ansiEnabled; }
/*     */ 
/*     */ 
/*     */   
/*  37 */   public String getAnsiBuffer() { return this.ansiBuffer.toString(); }
/*     */ 
/*     */ 
/*     */   
/*  41 */   public String getPlainBuffer() { return this.plainBuffer.toString(); }
/*     */ 
/*     */ 
/*     */   
/*  45 */   public String toString(boolean ansi) { return ansi ? getAnsiBuffer() : getPlainBuffer(); }
/*     */ 
/*     */ 
/*     */   
/*  49 */   public String toString() { return toString(this.ansiEnabled); }
/*     */ 
/*     */   
/*     */   public ANSIBuffer append(String str) {
/*  53 */     this.ansiBuffer.append(str);
/*  54 */     this.plainBuffer.append(str);
/*     */     
/*  56 */     return this;
/*     */   }
/*     */   
/*     */   public ANSIBuffer attrib(String str, int code) {
/*  60 */     this.ansiBuffer.append(ANSICodes.attrib(code)).append(str).append(ANSICodes.attrib(0));
/*     */     
/*  62 */     this.plainBuffer.append(str);
/*     */     
/*  64 */     return this;
/*     */   }
/*     */ 
/*     */   
/*  68 */   public ANSIBuffer red(String str) { return attrib(str, 31); }
/*     */ 
/*     */ 
/*     */   
/*  72 */   public ANSIBuffer blue(String str) { return attrib(str, 34); }
/*     */ 
/*     */ 
/*     */   
/*  76 */   public ANSIBuffer green(String str) { return attrib(str, 32); }
/*     */ 
/*     */ 
/*     */   
/*  80 */   public ANSIBuffer black(String str) { return attrib(str, 30); }
/*     */ 
/*     */ 
/*     */   
/*  84 */   public ANSIBuffer yellow(String str) { return attrib(str, 33); }
/*     */ 
/*     */ 
/*     */   
/*  88 */   public ANSIBuffer magenta(String str) { return attrib(str, 35); }
/*     */ 
/*     */ 
/*     */   
/*  92 */   public ANSIBuffer cyan(String str) { return attrib(str, 36); }
/*     */ 
/*     */ 
/*     */   
/*  96 */   public ANSIBuffer bold(String str) { return attrib(str, 1); }
/*     */ 
/*     */ 
/*     */   
/* 100 */   public ANSIBuffer underscore(String str) { return attrib(str, 4); }
/*     */ 
/*     */ 
/*     */   
/* 104 */   public ANSIBuffer blink(String str) { return attrib(str, 5); }
/*     */ 
/*     */ 
/*     */   
/* 108 */   public ANSIBuffer reverse(String str) { return attrib(str, 7); }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ANSICodes
/*     */   {
/*     */     static final int OFF = 0;
/*     */ 
/*     */     
/*     */     static final int BOLD = 1;
/*     */ 
/*     */     
/*     */     static final int UNDERSCORE = 4;
/*     */ 
/*     */     
/*     */     static final int BLINK = 5;
/*     */ 
/*     */     
/*     */     static final int REVERSE = 7;
/*     */ 
/*     */     
/*     */     static final int CONCEALED = 8;
/*     */ 
/*     */     
/*     */     static final int FG_BLACK = 30;
/*     */ 
/*     */     
/*     */     static final int FG_RED = 31;
/*     */ 
/*     */     
/*     */     static final int FG_GREEN = 32;
/*     */ 
/*     */     
/*     */     static final int FG_YELLOW = 33;
/*     */ 
/*     */     
/*     */     static final int FG_BLUE = 34;
/*     */ 
/*     */     
/*     */     static final int FG_MAGENTA = 35;
/*     */ 
/*     */     
/*     */     static final int FG_CYAN = 36;
/*     */     
/*     */     static final int FG_WHITE = 37;
/*     */     
/*     */     static final char ESC = '\033';
/*     */ 
/*     */     
/* 157 */     public static String setmode(int mode) { return "\033[=" + mode + "h"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 166 */     public static String resetmode(int mode) { return "\033[=" + mode + "l"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 173 */     public static String clrscr() { return "\033[2J"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 181 */     public static String clreol() { return "\033[K"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 190 */     public static String left(int n) { return "\033[" + n + "D"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 199 */     public static String right(int n) { return "\033[" + n + "C"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 208 */     public static String up(int n) { return "\033[" + n + "A"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 216 */     public static String down(int n) { return "\033[" + n + "B"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 225 */     public static String gotoxy(int row, int column) { return "\033[" + row + ";" + column + "H"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 232 */     public static String save() { return "\033[s"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 239 */     public static String restore() { return "\033[u"; }
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
/*     */     
/* 280 */     public static String attrib(int attr) { return "\033[" + attr + "m"; }
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
/* 387 */     public static String setkey(String code, String value) { return "\033[" + code + ";" + value + "p"; }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws Exception {
/* 393 */     BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
/*     */     
/* 395 */     System.out.print(ANSICodes.setkey("97", "97;98;99;13") + ANSICodes.attrib(0));
/*     */     
/* 397 */     System.out.flush();
/*     */     
/*     */     String line;
/*     */     
/* 401 */     while ((line = reader.readLine()) != null)
/* 402 */       System.out.println("GOT: " + line); 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\jline\ANSIBuffer.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.0.4
 */