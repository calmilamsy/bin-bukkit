/*     */ package jline;
/*     */ 
/*     */ import java.util.LinkedList;
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
/*     */ public class ArgumentCompletor
/*     */   implements Completor
/*     */ {
/*     */   final Completor[] completors;
/*     */   final ArgumentDelimiter delim;
/*     */   boolean strict;
/*     */   
/*  67 */   public ArgumentCompletor(Completor completor) { this(new Completor[] { completor }); }
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
/*  79 */   public ArgumentCompletor(List completors) { this((Completor[])completors.toArray(new Completor[completors.size()])); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   public ArgumentCompletor(Completor[] completors) { this(completors, new WhitespaceArgumentDelimiter()); }
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
/* 101 */   public ArgumentCompletor(Completor completor, ArgumentDelimiter delim) { this(new Completor[] { completor }, delim); }
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
/*     */   public ArgumentCompletor(Completor[] completors, ArgumentDelimiter delim) {
/*     */     this.strict = true;
/* 115 */     this.completors = completors;
/* 116 */     this.delim = delim;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 124 */   public void setStrict(boolean strict) { this.strict = strict; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   public boolean getStrict() { return this.strict; }
/*     */ 
/*     */   
/*     */   public int complete(String buffer, int cursor, List candidates) {
/*     */     Completor comp;
/* 137 */     ArgumentList list = this.delim.delimit(buffer, cursor);
/* 138 */     int argpos = list.getArgumentPosition();
/* 139 */     int argIndex = list.getCursorArgumentIndex();
/*     */     
/* 141 */     if (argIndex < 0) {
/* 142 */       return -1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 148 */     if (argIndex >= this.completors.length) {
/* 149 */       comp = this.completors[this.completors.length - 1];
/*     */     } else {
/* 151 */       comp = this.completors[argIndex];
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 156 */     for (int i = 0; getStrict() && i < argIndex; i++) {
/* 157 */       Completor sub = this.completors[(i >= this.completors.length) ? (this.completors.length - 1) : i];
/*     */       
/* 159 */       String[] args = list.getArguments();
/* 160 */       String arg = (args == null || i >= args.length) ? "" : args[i];
/*     */       
/* 162 */       List subCandidates = new LinkedList();
/*     */       
/* 164 */       if (sub.complete(arg, arg.length(), subCandidates) == -1) {
/* 165 */         return -1;
/*     */       }
/*     */       
/* 168 */       if (subCandidates.size() == 0) {
/* 169 */         return -1;
/*     */       }
/*     */     } 
/*     */     
/* 173 */     int ret = comp.complete(list.getCursorArgument(), argpos, candidates);
/*     */     
/* 175 */     if (ret == -1) {
/* 176 */       return -1;
/*     */     }
/*     */     
/* 179 */     int pos = ret + list.getBufferPosition() - argpos;
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
/* 191 */     if (cursor != buffer.length() && this.delim.isDelimiter(buffer, cursor)) {
/* 192 */       for (int i = 0; i < candidates.size(); i++) {
/* 193 */         String val = candidates.get(i).toString();
/*     */ 
/*     */         
/* 196 */         while (val.length() > 0 && this.delim.isDelimiter(val, val.length() - 1)) {
/* 197 */           val = val.substring(0, val.length() - 1);
/*     */         }
/*     */         
/* 200 */         candidates.set(i, val);
/*     */       } 
/*     */     }
/*     */     
/* 204 */     ConsoleReader.debug("Completing " + buffer + "(pos=" + cursor + ") " + "with: " + candidates + ": offset=" + pos);
/*     */ 
/*     */     
/* 207 */     return pos;
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
/*     */   public static interface ArgumentDelimiter
/*     */   {
/*     */     ArgumentCompletor.ArgumentList delimit(String param1String, int param1Int);
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
/*     */     boolean isDelimiter(String param1String, int param1Int);
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
/*     */   public static abstract class AbstractArgumentDelimiter
/*     */     implements ArgumentDelimiter
/*     */   {
/* 249 */     private char[] quoteChars = { '\'', '"' };
/* 250 */     private char[] escapeChars = { '\\' };
/*     */ 
/*     */     
/* 253 */     public void setQuoteChars(char[] quoteChars) { this.quoteChars = quoteChars; }
/*     */ 
/*     */ 
/*     */     
/* 257 */     public char[] getQuoteChars() { return this.quoteChars; }
/*     */ 
/*     */ 
/*     */     
/* 261 */     public void setEscapeChars(char[] escapeChars) { this.escapeChars = escapeChars; }
/*     */ 
/*     */ 
/*     */     
/* 265 */     public char[] getEscapeChars() { return this.escapeChars; }
/*     */ 
/*     */     
/*     */     public ArgumentCompletor.ArgumentList delimit(String buffer, int cursor) {
/* 269 */       List args = new LinkedList();
/* 270 */       StringBuffer arg = new StringBuffer();
/* 271 */       int argpos = -1;
/* 272 */       int bindex = -1;
/*     */       
/* 274 */       for (int i = 0; buffer != null && i <= buffer.length(); i++) {
/*     */ 
/*     */         
/* 277 */         if (i == cursor) {
/* 278 */           bindex = args.size();
/*     */ 
/*     */           
/* 281 */           argpos = arg.length();
/*     */         } 
/*     */         
/* 284 */         if (i == buffer.length() || isDelimiter(buffer, i)) {
/* 285 */           if (arg.length() > 0) {
/* 286 */             args.add(arg.toString());
/* 287 */             arg.setLength(0);
/*     */           } 
/*     */         } else {
/* 290 */           arg.append(buffer.charAt(i));
/*     */         } 
/*     */       } 
/*     */       
/* 294 */       return new ArgumentCompletor.ArgumentList((String[])args.toArray(new String[args.size()]), bindex, argpos, cursor);
/*     */     }
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
/*     */     public boolean isDelimiter(String buffer, int pos) {
/* 311 */       if (isQuoted(buffer, pos)) {
/* 312 */         return false;
/*     */       }
/*     */       
/* 315 */       if (isEscaped(buffer, pos)) {
/* 316 */         return false;
/*     */       }
/*     */       
/* 319 */       return isDelimiterChar(buffer, pos);
/*     */     }
/*     */ 
/*     */     
/* 323 */     public boolean isQuoted(String buffer, int pos) { return false; }
/*     */ 
/*     */     
/*     */     public boolean isEscaped(String buffer, int pos) {
/* 327 */       if (pos <= 0) {
/* 328 */         return false;
/*     */       }
/*     */       
/* 331 */       for (int i = 0; this.escapeChars != null && i < this.escapeChars.length; 
/* 332 */         i++) {
/* 333 */         if (buffer.charAt(pos) == this.escapeChars[i]) {
/* 334 */           return !isEscaped(buffer, pos - 1);
/*     */         }
/*     */       } 
/*     */       
/* 338 */       return false;
/*     */     }
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
/*     */     public abstract boolean isDelimiterChar(String param1String, int param1Int);
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
/*     */   public static class WhitespaceArgumentDelimiter
/*     */     extends AbstractArgumentDelimiter
/*     */   {
/* 367 */     public boolean isDelimiterChar(String buffer, int pos) { return Character.isWhitespace(buffer.charAt(pos)); }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ArgumentList
/*     */   {
/*     */     private String[] arguments;
/*     */ 
/*     */ 
/*     */     
/*     */     private int cursorArgumentIndex;
/*     */ 
/*     */ 
/*     */     
/*     */     private int argumentPosition;
/*     */ 
/*     */ 
/*     */     
/*     */     private int bufferPosition;
/*     */ 
/*     */ 
/*     */     
/*     */     public ArgumentList(String[] arguments, int cursorArgumentIndex, int argumentPosition, int bufferPosition) {
/* 392 */       this.arguments = arguments;
/* 393 */       this.cursorArgumentIndex = cursorArgumentIndex;
/* 394 */       this.argumentPosition = argumentPosition;
/* 395 */       this.bufferPosition = bufferPosition;
/*     */     }
/*     */ 
/*     */     
/* 399 */     public void setCursorArgumentIndex(int cursorArgumentIndex) { this.cursorArgumentIndex = cursorArgumentIndex; }
/*     */ 
/*     */ 
/*     */     
/* 403 */     public int getCursorArgumentIndex() { return this.cursorArgumentIndex; }
/*     */ 
/*     */     
/*     */     public String getCursorArgument() {
/* 407 */       if (this.cursorArgumentIndex < 0 || this.cursorArgumentIndex >= this.arguments.length)
/*     */       {
/* 409 */         return null;
/*     */       }
/*     */       
/* 412 */       return this.arguments[this.cursorArgumentIndex];
/*     */     }
/*     */ 
/*     */     
/* 416 */     public void setArgumentPosition(int argumentPosition) { this.argumentPosition = argumentPosition; }
/*     */ 
/*     */ 
/*     */     
/* 420 */     public int getArgumentPosition() { return this.argumentPosition; }
/*     */ 
/*     */ 
/*     */     
/* 424 */     public void setArguments(String[] arguments) { this.arguments = arguments; }
/*     */ 
/*     */ 
/*     */     
/* 428 */     public String[] getArguments() { return this.arguments; }
/*     */ 
/*     */ 
/*     */     
/* 432 */     public void setBufferPosition(int bufferPosition) { this.bufferPosition = bufferPosition; }
/*     */ 
/*     */ 
/*     */     
/* 436 */     public int getBufferPosition() { return this.bufferPosition; }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\jline\ArgumentCompletor.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.0.4
 */