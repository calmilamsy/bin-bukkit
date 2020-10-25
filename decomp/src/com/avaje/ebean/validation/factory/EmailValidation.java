/*     */ package com.avaje.ebean.validation.factory;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.regex.Pattern;
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
/*     */ public class EmailValidation
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2664585768077565394L;
/*     */   private static final String wsp = "[ \\t]";
/*     */   private static final String fwsp = "[ \\t]*";
/*     */   private static final String dquote = "\\\"";
/*     */   private static final String noWsCtl = "\\x01-\\x08\\x0B\\x0C\\x0E-\\x1F\\x7F";
/*     */   private static final String asciiText = "[\\x01-\\x09\\x0B\\x0C\\x0E-\\x7F]";
/*     */   private static final String quotedPair = "(\\\\[\\x01-\\x09\\x0B\\x0C\\x0E-\\x7F])";
/*     */   private static final String atext = "[a-zA-Z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~]";
/*     */   private static final String atom = "[ \\t]*[a-zA-Z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~]+[ \\t]*";
/*     */   private static final String dotAtomText = "[a-zA-Z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~]+(\\.[a-zA-Z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~]+)*";
/*     */   private static final String dotAtom = "[ \\t]*([a-zA-Z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~]+(\\.[a-zA-Z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~]+)*)[ \\t]*";
/*     */   private static final String qtext = "[\\x01-\\x08\\x0B\\x0C\\x0E-\\x1F\\x7F\\x21\\x23-\\x5B\\x5D-\\x7E]";
/*     */   private static final String qcontent = "([\\x01-\\x08\\x0B\\x0C\\x0E-\\x1F\\x7F\\x21\\x23-\\x5B\\x5D-\\x7E]|(\\\\[\\x01-\\x09\\x0B\\x0C\\x0E-\\x7F]))";
/*     */   private static final String quotedString = "\\\"([ \\t]*([\\x01-\\x08\\x0B\\x0C\\x0E-\\x1F\\x7F\\x21\\x23-\\x5B\\x5D-\\x7E]|(\\\\[\\x01-\\x09\\x0B\\x0C\\x0E-\\x7F])))*[ \\t]*\\\"";
/*     */   private static final String word = "(([ \\t]*[a-zA-Z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~]+[ \\t]*)|(\\\"([ \\t]*([\\x01-\\x08\\x0B\\x0C\\x0E-\\x1F\\x7F\\x21\\x23-\\x5B\\x5D-\\x7E]|(\\\\[\\x01-\\x09\\x0B\\x0C\\x0E-\\x7F])))*[ \\t]*\\\"))";
/*     */   private static final String phrase = "(([ \\t]*[a-zA-Z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~]+[ \\t]*)|(\\\"([ \\t]*([\\x01-\\x08\\x0B\\x0C\\x0E-\\x1F\\x7F\\x21\\x23-\\x5B\\x5D-\\x7E]|(\\\\[\\x01-\\x09\\x0B\\x0C\\x0E-\\x7F])))*[ \\t]*\\\"))+";
/*     */   private static final String letter = "[a-zA-Z]";
/*     */   private static final String letDig = "[a-zA-Z0-9]";
/*     */   private static final String letDigHyp = "[a-zA-Z0-9-]";
/*     */   private static final String rfcLabel = "[a-zA-Z0-9]([a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?";
/*     */   private static final String rfc1035DomainName = "[a-zA-Z0-9]([a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(\\.[a-zA-Z0-9]([a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\\.[a-zA-Z]{2,6}";
/*     */   private static final String dtext = "[\\x01-\\x08\\x0B\\x0C\\x0E-\\x1F\\x7F\\x21-\\x5A\\x5E-\\x7E]";
/*     */   private static final String dcontent = "[\\x01-\\x08\\x0B\\x0C\\x0E-\\x1F\\x7F\\x21-\\x5A\\x5E-\\x7E]|(\\\\[\\x01-\\x09\\x0B\\x0C\\x0E-\\x7F])";
/*     */   private static final String domainLiteral = "\\[([ \\t]*[\\x01-\\x08\\x0B\\x0C\\x0E-\\x1F\\x7F\\x21-\\x5A\\x5E-\\x7E]|(\\\\[\\x01-\\x09\\x0B\\x0C\\x0E-\\x7F])+)*[ \\t]*\\]";
/*     */   private static final String rfc2822Domain = "([ \\t]*([a-zA-Z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~]+(\\.[a-zA-Z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~]+)*)[ \\t]*|\\[([ \\t]*[\\x01-\\x08\\x0B\\x0C\\x0E-\\x1F\\x7F\\x21-\\x5A\\x5E-\\x7E]|(\\\\[\\x01-\\x09\\x0B\\x0C\\x0E-\\x7F])+)*[ \\t]*\\])";
/*     */   private static final String localPart = "(([ \\t]*([a-zA-Z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~]+(\\.[a-zA-Z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~]+)*)[ \\t]*)|(\\\"([ \\t]*([\\x01-\\x08\\x0B\\x0C\\x0E-\\x1F\\x7F\\x21\\x23-\\x5B\\x5D-\\x7E]|(\\\\[\\x01-\\x09\\x0B\\x0C\\x0E-\\x7F])))*[ \\t]*\\\"))";
/* 126 */   private static final EmailValidation DEFAULT_VALIDATOR = create(false, false);
/*     */ 
/*     */   
/*     */   private final Pattern localPattern;
/*     */ 
/*     */ 
/*     */   
/*     */   public static EmailValidation create(boolean allowDomainLiterals, boolean allowQuotedIdentifiers) {
/* 134 */     String domain = allowDomainLiterals ? "([ \\t]*([a-zA-Z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~]+(\\.[a-zA-Z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~]+)*)[ \\t]*|\\[([ \\t]*[\\x01-\\x08\\x0B\\x0C\\x0E-\\x1F\\x7F\\x21-\\x5A\\x5E-\\x7E]|(\\\\[\\x01-\\x09\\x0B\\x0C\\x0E-\\x7F])+)*[ \\t]*\\])" : "[a-zA-Z0-9]([a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(\\.[a-zA-Z0-9]([a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\\.[a-zA-Z]{2,6}";
/* 135 */     String addrSpec = "(([ \\t]*([a-zA-Z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~]+(\\.[a-zA-Z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~]+)*)[ \\t]*)|(\\\"([ \\t]*([\\x01-\\x08\\x0B\\x0C\\x0E-\\x1F\\x7F\\x21\\x23-\\x5B\\x5D-\\x7E]|(\\\\[\\x01-\\x09\\x0B\\x0C\\x0E-\\x7F])))*[ \\t]*\\\"))@" + domain;
/* 136 */     String angleAddr = "<" + addrSpec + ">";
/* 137 */     String nameAddr = "((([ \\t]*[a-zA-Z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~]+[ \\t]*)|(\\\"([ \\t]*([\\x01-\\x08\\x0B\\x0C\\x0E-\\x1F\\x7F\\x21\\x23-\\x5B\\x5D-\\x7E]|(\\\\[\\x01-\\x09\\x0B\\x0C\\x0E-\\x7F])))*[ \\t]*\\\"))+)?[ \\t]*" + angleAddr;
/* 138 */     String mailbox = nameAddr + "|" + addrSpec;
/* 139 */     String patternString = allowQuotedIdentifiers ? mailbox : addrSpec;
/*     */     
/* 141 */     return new EmailValidation(patternString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 147 */   public EmailValidation(String pattern) { this.localPattern = Pattern.compile(pattern); }
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
/* 160 */   public boolean isValid(String email) { return (email != null && this.localPattern.matcher(email).matches()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 167 */   public static boolean isValidEmail(String email) { return DEFAULT_VALIDATOR.isValid(email); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 175 */     System.out.println("... test with default settings");
/* 176 */     test(null, "\"John Smith\" <john.smith@u.washington.edu>");
/* 177 */     test(null, "<john.smith@u.washington.edu>");
/* 178 */     test(null, "john.smith@u.washington.edu");
/*     */     
/* 180 */     EmailValidation allowValidator = create(true, true);
/* 181 */     System.out.println("... test with allow literals and domains");
/* 182 */     test(allowValidator, "\"John Smith\" <john.smith@u.washington.edu>");
/* 183 */     test(allowValidator, "<john.smith@u.washington.edu>");
/* 184 */     test(allowValidator, "john.smith@u.washington.edu");
/*     */   }
/*     */   
/*     */   private static void test(EmailValidation validator, String email) {
/* 188 */     if (validator == null) {
/* 189 */       validator = DEFAULT_VALIDATOR;
/*     */     }
/* 191 */     if (validator.isValid(email)) {
/* 192 */       System.out.println(email + " is valid");
/*     */     } else {
/* 194 */       System.out.println(email + " is Invalid!");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\validation\factory\EmailValidation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */