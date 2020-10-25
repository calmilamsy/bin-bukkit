/*     */ package com.avaje.ebeaninternal.server.ddl;
/*     */ 
/*     */ import com.avaje.ebean.Transaction;
/*     */ import com.avaje.ebean.config.NamingConvention;
/*     */ import com.avaje.ebean.config.ServerConfig;
/*     */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.LineNumberReader;
/*     */ import java.io.PrintStream;
/*     */ import java.io.StringReader;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.PersistenceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DdlGenerator
/*     */ {
/*  32 */   private static final Logger logger = Logger.getLogger(DdlGenerator.class.getName());
/*     */   
/*     */   private final SpiEbeanServer server;
/*     */   private final DatabasePlatform dbPlatform;
/*     */   
/*     */   public DdlGenerator(SpiEbeanServer server, DatabasePlatform dbPlatform, ServerConfig serverConfig) {
/*  38 */     this.out = System.out;
/*     */     
/*  40 */     this.summaryLength = 80;
/*     */     
/*  42 */     this.debug = true;
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
/*  53 */     this.server = server;
/*  54 */     this.dbPlatform = dbPlatform;
/*  55 */     this.generateDdl = serverConfig.isDdlGenerate();
/*  56 */     this.runDdl = serverConfig.isDdlRun();
/*  57 */     this.namingConvention = serverConfig.getNamingConvention();
/*     */   }
/*     */   private PrintStream out; private int summaryLength; private boolean debug;
/*     */   private boolean generateDdl;
/*     */   private boolean runDdl;
/*     */   
/*     */   public void execute(boolean online) {
/*  64 */     generateDdl();
/*  65 */     if (online)
/*  66 */       runDdl(); 
/*     */   }
/*     */   
/*     */   private String dropContent;
/*     */   private String createContent;
/*     */   private NamingConvention namingConvention;
/*     */   
/*     */   public void generateDdl() {
/*  74 */     if (this.generateDdl) {
/*  75 */       writeDrop(getDropFileName());
/*  76 */       writeCreate(getCreateFileName());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void runDdl() {
/*  85 */     if (this.runDdl) {
/*     */       try {
/*  87 */         if (this.dropContent == null) {
/*  88 */           this.dropContent = readFile(getDropFileName());
/*     */         }
/*  90 */         if (this.createContent == null) {
/*  91 */           this.createContent = readFile(getCreateFileName());
/*     */         }
/*  93 */         runScript(true, this.dropContent);
/*  94 */         runScript(false, this.createContent);
/*     */       }
/*  96 */       catch (IOException e) {
/*  97 */         String msg = "Error reading drop/create script from file system";
/*  98 */         throw new RuntimeException(msg, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeDrop(String dropFile) {
/*     */     try {
/* 107 */       String c = generateDropDdl();
/* 108 */       writeFile(dropFile, c);
/*     */     }
/* 110 */     catch (IOException e) {
/* 111 */       String msg = "Error generating Drop DDL";
/* 112 */       throw new PersistenceException(msg, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeCreate(String createFile) {
/*     */     try {
/* 120 */       String c = generateCreateDdl();
/* 121 */       writeFile(createFile, c);
/*     */     }
/* 123 */     catch (IOException e) {
/* 124 */       String msg = "Error generating Create DDL";
/* 125 */       throw new PersistenceException(msg, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String generateDropDdl() {
/* 131 */     DdlGenContext ctx = createContext();
/*     */     
/* 133 */     DropTableVisitor drop = new DropTableVisitor(ctx);
/* 134 */     VisitorUtil.visit(this.server, drop);
/*     */     
/* 136 */     DropSequenceVisitor dropSequence = new DropSequenceVisitor(ctx);
/* 137 */     VisitorUtil.visit(this.server, dropSequence);
/*     */     
/* 139 */     ctx.flush();
/* 140 */     this.dropContent = ctx.getContent();
/* 141 */     return this.dropContent;
/*     */   }
/*     */ 
/*     */   
/*     */   public String generateCreateDdl() {
/* 146 */     DdlGenContext ctx = createContext();
/* 147 */     CreateTableVisitor create = new CreateTableVisitor(ctx);
/* 148 */     VisitorUtil.visit(this.server, create);
/*     */     
/* 150 */     CreateSequenceVisitor createSequence = new CreateSequenceVisitor(ctx);
/* 151 */     VisitorUtil.visit(this.server, createSequence);
/*     */     
/* 153 */     AddForeignKeysVisitor fkeys = new AddForeignKeysVisitor(ctx);
/* 154 */     VisitorUtil.visit(this.server, fkeys);
/*     */     
/* 156 */     ctx.flush();
/* 157 */     this.createContent = ctx.getContent();
/* 158 */     return this.createContent;
/*     */   }
/*     */ 
/*     */   
/* 162 */   protected String getDropFileName() { return this.server.getName() + "-drop.sql"; }
/*     */ 
/*     */ 
/*     */   
/* 166 */   protected String getCreateFileName() { return this.server.getName() + "-create.sql"; }
/*     */ 
/*     */ 
/*     */   
/* 170 */   protected DdlGenContext createContext() { return new DdlGenContext(this.dbPlatform, this.namingConvention); }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeFile(String fileName, String fileContent) throws IOException {
/* 175 */     File f = new File(fileName);
/*     */     
/* 177 */     fw = new FileWriter(f);
/*     */     try {
/* 179 */       fw.write(fileContent);
/* 180 */       fw.flush();
/*     */     } finally {
/* 182 */       fw.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected String readFile(String fileName) throws IOException {
/* 188 */     File f = new File(fileName);
/* 189 */     if (!f.exists()) {
/* 190 */       return null;
/*     */     }
/*     */     
/* 193 */     StringBuilder buf = new StringBuilder();
/*     */     
/* 195 */     FileReader fr = new FileReader(f);
/* 196 */     lr = new LineNumberReader(fr);
/*     */     try {
/* 198 */       String s = null;
/* 199 */       while ((s = lr.readLine()) != null) {
/* 200 */         buf.append(s).append("\n");
/*     */       }
/*     */     } finally {
/* 203 */       lr.close();
/*     */     } 
/*     */     
/* 206 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void runScript(boolean expectErrors, String content) {
/* 214 */     StringReader sr = new StringReader(content);
/* 215 */     List<String> statements = parseStatements(sr);
/*     */     
/* 217 */     t = this.server.createTransaction();
/*     */     try {
/* 219 */       Connection connection = t.getConnection();
/*     */       
/* 221 */       this.out.println("runScript");
/* 222 */       this.out.flush();
/*     */       
/* 224 */       runStatements(expectErrors, statements, connection);
/*     */       
/* 226 */       this.out.println("... end of script");
/* 227 */       this.out.flush();
/*     */       
/* 229 */       t.commit();
/*     */     }
/* 231 */     catch (Exception e) {
/* 232 */       String msg = "Error: " + e.getMessage();
/* 233 */       throw new PersistenceException(msg, e);
/*     */     } finally {
/* 235 */       t.end();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void runStatements(boolean expectErrors, List<String> statements, Connection c) {
/* 244 */     for (int i = 0; i < statements.size(); i++) {
/* 245 */       String xOfy = (i + 1) + " of " + statements.size();
/* 246 */       runStatement(expectErrors, xOfy, (String)statements.get(i), c);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void runStatement(boolean expectErrors, String oneOf, String stmt, Connection c) {
/* 255 */     pstmt = null;
/*     */ 
/*     */     
/*     */     try {
/* 259 */       stmt = stmt.trim();
/* 260 */       if (stmt.endsWith(";")) {
/* 261 */         stmt = stmt.substring(0, stmt.length() - 1);
/* 262 */       } else if (stmt.endsWith("/")) {
/* 263 */         stmt = stmt.substring(0, stmt.length() - 1);
/*     */       } 
/*     */       
/* 266 */       if (this.debug) {
/* 267 */         this.out.println("executing " + oneOf + " " + getSummary(stmt));
/* 268 */         this.out.flush();
/*     */       } 
/*     */       
/* 271 */       pstmt = c.prepareStatement(stmt);
/* 272 */       pstmt.execute();
/*     */     }
/* 274 */     catch (Exception e) {
/* 275 */       if (expectErrors) {
/* 276 */         this.out.println(" ... ignoring error executing " + getSummary(stmt) + "  error: " + e.getMessage());
/* 277 */         e.printStackTrace();
/* 278 */         this.out.flush();
/*     */       } else {
/* 280 */         String msg = "Error executing stmt[" + stmt + "] error[" + e.getMessage() + "]";
/* 281 */         throw new RuntimeException(msg, e);
/*     */       } 
/*     */     } finally {
/* 284 */       if (pstmt != null) {
/*     */         try {
/* 286 */           pstmt.close();
/* 287 */         } catch (SQLException e) {
/* 288 */           logger.log(Level.SEVERE, "Error closing pstmt", e);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<String> parseStatements(StringReader reader) {
/*     */     try {
/* 301 */       BufferedReader br = new BufferedReader(reader);
/*     */       
/* 303 */       ArrayList<String> statements = new ArrayList<String>();
/*     */       
/* 305 */       StringBuilder sb = new StringBuilder();
/*     */       String s;
/* 307 */       while ((s = br.readLine()) != null) {
/* 308 */         s = s.trim();
/* 309 */         int semiPos = s.indexOf(';');
/* 310 */         if (semiPos == -1) {
/* 311 */           sb.append(s).append(" "); continue;
/*     */         } 
/* 313 */         if (semiPos == s.length() - 1) {
/*     */           
/* 315 */           sb.append(s);
/* 316 */           statements.add(sb.toString().trim());
/* 317 */           sb = new StringBuilder();
/*     */           
/*     */           continue;
/*     */         } 
/* 321 */         String preSemi = s.substring(0, semiPos);
/* 322 */         sb.append(preSemi);
/* 323 */         statements.add(sb.toString().trim());
/* 324 */         sb = new StringBuilder();
/* 325 */         sb.append(s.substring(semiPos + 1));
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 330 */       return statements;
/* 331 */     } catch (IOException e) {
/* 332 */       throw new PersistenceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getSummary(String s) throws IOException {
/* 337 */     if (s.length() > this.summaryLength) {
/* 338 */       return s.substring(0, this.summaryLength).trim() + "...";
/*     */     }
/* 340 */     return s;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\ddl\DdlGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */