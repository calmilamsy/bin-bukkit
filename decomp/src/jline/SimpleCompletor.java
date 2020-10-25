/*     */ package jline;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.SortedSet;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.TreeSet;
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
/*     */ public class SimpleCompletor
/*     */   implements Completor, Cloneable
/*     */ {
/*     */   SortedSet candidates;
/*     */   String delimiter;
/*     */   final SimpleCompletorFilter filter;
/*     */   
/*  44 */   public SimpleCompletor(String candidateString) { this(new String[] { candidateString }); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   public SimpleCompletor(String[] candidateStrings) { this(candidateStrings, null); }
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleCompletor(String[] strings, SimpleCompletorFilter filter) {
/*  59 */     this.filter = filter;
/*  60 */     setCandidateStrings(strings);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   public SimpleCompletor(Reader reader) throws IOException { this(getStrings(reader)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   public SimpleCompletor(InputStream in) throws IOException { this(getStrings(new InputStreamReader(in))); }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] getStrings(Reader in) throws IOException {
/*  80 */     Reader reader = (in instanceof BufferedReader) ? in : new BufferedReader(in);
/*     */ 
/*     */     
/*  83 */     List words = new LinkedList();
/*     */     
/*     */     String line;
/*  86 */     while ((line = ((BufferedReader)reader).readLine()) != null) {
/*  87 */       StringTokenizer tok = new StringTokenizer(line);
/*  88 */       for (; tok.hasMoreTokens(); words.add(tok.nextToken()));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  93 */     return (String[])words.toArray(new String[words.size()]);
/*     */   }
/*     */   
/*     */   public int complete(String buffer, int cursor, List clist) {
/*  97 */     String start = (buffer == null) ? "" : buffer;
/*     */     
/*  99 */     SortedSet matches = this.candidates.tailSet(start);
/*     */     
/* 101 */     for (Iterator i = matches.iterator(); i.hasNext(); ) {
/* 102 */       String can = (String)i.next();
/*     */       
/* 104 */       if (!can.startsWith(start)) {
/*     */         break;
/*     */       }
/*     */       
/* 108 */       if (this.delimiter != null) {
/* 109 */         int index = can.indexOf(this.delimiter, cursor);
/*     */         
/* 111 */         if (index != -1) {
/* 112 */           can = can.substring(0, index + 1);
/*     */         }
/*     */       } 
/*     */       
/* 116 */       clist.add(can);
/*     */     } 
/*     */     
/* 119 */     if (clist.size() == 1) {
/* 120 */       clist.set(0, (String)clist.get(0) + " ");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 125 */     return (clist.size() == 0) ? -1 : 0;
/*     */   }
/*     */ 
/*     */   
/* 129 */   public void setDelimiter(String delimiter) { this.delimiter = delimiter; }
/*     */ 
/*     */ 
/*     */   
/* 133 */   public String getDelimiter() { return this.delimiter; }
/*     */ 
/*     */   
/*     */   public void setCandidates(SortedSet candidates) {
/* 137 */     if (this.filter != null) {
/* 138 */       TreeSet filtered = new TreeSet();
/*     */       
/* 140 */       for (Iterator i = candidates.iterator(); i.hasNext(); ) {
/* 141 */         String element = (String)i.next();
/* 142 */         element = this.filter.filter(element);
/*     */         
/* 144 */         if (element != null) {
/* 145 */           filtered.add(element);
/*     */         }
/*     */       } 
/*     */       
/* 149 */       this.candidates = filtered;
/*     */     } else {
/* 151 */       this.candidates = candidates;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 156 */   public SortedSet getCandidates() { return Collections.unmodifiableSortedSet(this.candidates); }
/*     */ 
/*     */ 
/*     */   
/* 160 */   public void setCandidateStrings(String[] strings) { setCandidates(new TreeSet(Arrays.asList(strings))); }
/*     */ 
/*     */   
/*     */   public void addCandidateString(String candidateString) {
/* 164 */     String string = (this.filter == null) ? candidateString : this.filter.filter(candidateString);
/*     */ 
/*     */     
/* 167 */     if (string != null) {
/* 168 */       this.candidates.add(string);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 173 */   public Object clone() throws CloneNotSupportedException { return super.clone(); }
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
/*     */   public static class NoOpFilter
/*     */     implements SimpleCompletorFilter
/*     */   {
/* 191 */     public String filter(String element) { return element; }
/*     */   }
/*     */   
/*     */   public static interface SimpleCompletorFilter {
/*     */     String filter(String param1String);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\jline\SimpleCompletor.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.0.4
 */