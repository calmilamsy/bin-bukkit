/*     */ package jline;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CandidateListCompletionHandler
/*     */   implements CompletionHandler
/*     */ {
/*  31 */   private static ResourceBundle loc = ResourceBundle.getBundle(CandidateListCompletionHandler.class.getName());
/*     */ 
/*     */   
/*     */   private boolean eagerNewlines = true;
/*     */ 
/*     */   
/*  37 */   public void setAlwaysIncludeNewline(boolean eagerNewlines) { this.eagerNewlines = eagerNewlines; }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean complete(ConsoleReader reader, List candidates, int pos) throws IOException {
/*  42 */     CursorBuffer buf = reader.getCursorBuffer();
/*     */ 
/*     */     
/*  45 */     if (candidates.size() == 1) {
/*  46 */       String value = candidates.get(0).toString();
/*     */ 
/*     */       
/*  49 */       if (value.equals(buf.toString())) {
/*  50 */         return false;
/*     */       }
/*     */       
/*  53 */       setBuffer(reader, value, pos);
/*     */       
/*  55 */       return true;
/*  56 */     }  if (candidates.size() > 1) {
/*  57 */       String value = getUnambiguousCompletions(candidates);
/*  58 */       String bufString = buf.toString();
/*  59 */       setBuffer(reader, value, pos);
/*     */     } 
/*     */     
/*  62 */     if (this.eagerNewlines)
/*  63 */       reader.printNewline(); 
/*  64 */     printCandidates(reader, candidates, this.eagerNewlines);
/*     */ 
/*     */     
/*  67 */     reader.drawLine();
/*     */     
/*  69 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setBuffer(ConsoleReader reader, String value, int offset) throws IOException {
/*  75 */     while ((reader.getCursorBuffer()).cursor > offset && reader.backspace());
/*     */ 
/*     */ 
/*     */     
/*  79 */     reader.putString(value);
/*  80 */     reader.setCursorPosition(offset + value.length());
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
/*     */   public static final void printCandidates(ConsoleReader reader, Collection candidates, boolean eagerNewlines) throws IOException {
/*  93 */     Set distinct = new HashSet(candidates);
/*     */     
/*  95 */     if (distinct.size() > reader.getAutoprintThreshhold()) {
/*  96 */       if (!eagerNewlines)
/*  97 */         reader.printNewline(); 
/*  98 */       reader.printString(MessageFormat.format(loc.getString("display-candidates"), new Object[] { new Integer(candidates.size()) }) + " ");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 103 */       reader.flushConsole();
/*     */ 
/*     */ 
/*     */       
/* 107 */       String noOpt = loc.getString("display-candidates-no");
/* 108 */       String yesOpt = loc.getString("display-candidates-yes");
/*     */       
/*     */       int c;
/* 111 */       while ((c = reader.readCharacter(new char[] { yesOpt.charAt(0), noOpt.charAt(0) })) != -1) {
/* 112 */         if (noOpt.startsWith(new String(new char[] { (char)c }))) {
/*     */           
/* 114 */           reader.printNewline(); return;
/*     */         } 
/* 116 */         if (yesOpt.startsWith(new String(new char[] { (char)c }))) {
/*     */           break;
/*     */         }
/*     */         
/* 120 */         reader.beep();
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     if (distinct.size() != candidates.size()) {
/* 128 */       Collection copy = new ArrayList();
/*     */       
/* 130 */       for (Iterator i = candidates.iterator(); i.hasNext(); ) {
/* 131 */         Object next = i.next();
/*     */         
/* 133 */         if (!copy.contains(next)) {
/* 134 */           copy.add(next);
/*     */         }
/*     */       } 
/*     */       
/* 138 */       candidates = copy;
/*     */     } 
/*     */     
/* 141 */     reader.printNewline();
/* 142 */     reader.printColumns(candidates);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String getUnambiguousCompletions(List candidates) {
/* 153 */     if (candidates == null || candidates.size() == 0) {
/* 154 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 158 */     String[] strings = (String[])candidates.toArray(new String[candidates.size()]);
/*     */ 
/*     */     
/* 161 */     String first = strings[0];
/* 162 */     StringBuffer candidate = new StringBuffer();
/*     */     
/* 164 */     for (int i = 0; i < first.length() && 
/* 165 */       startsWith(first.substring(0, i + 1), strings); i++) {
/* 166 */       candidate.append(first.charAt(i));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 172 */     return candidate.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean startsWith(String starts, String[] candidates) {
/* 181 */     for (int i = 0; i < candidates.length; i++) {
/* 182 */       if (!candidates[i].startsWith(starts)) {
/* 183 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 187 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\jline\CandidateListCompletionHandler.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.0.4
 */