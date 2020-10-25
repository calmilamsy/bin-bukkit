/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.io.StringWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.io.Writer;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLXML;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLOutputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.sax.SAXResult;
/*     */ import javax.xml.transform.sax.SAXSource;
/*     */ import javax.xml.transform.stax.StAXResult;
/*     */ import javax.xml.transform.stax.StAXSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.DefaultHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JDBC4MysqlSQLXML
/*     */   implements SQLXML
/*     */ {
/*     */   private XMLInputFactory inputFactory;
/*     */   private XMLOutputFactory outputFactory;
/*     */   private String stringRep;
/*     */   private ResultSetInternalMethods owningResultSet;
/*     */   private int columnIndexOfXml;
/*     */   private boolean fromResultSet;
/*     */   private boolean isClosed;
/*     */   private boolean workingWithResult;
/*     */   private DOMResult asDOMResult;
/*     */   private SAXResult asSAXResult;
/*     */   private SimpleSaxToReader saxToReaderConverter;
/*     */   private StringWriter asStringWriter;
/*     */   private ByteArrayOutputStream asByteArrayOutputStream;
/*     */   private ExceptionInterceptor exceptionInterceptor;
/*     */   
/*     */   protected JDBC4MysqlSQLXML(ResultSetInternalMethods owner, int index, ExceptionInterceptor exceptionInterceptor) {
/*  87 */     this.isClosed = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 104 */     this.owningResultSet = owner;
/* 105 */     this.columnIndexOfXml = index;
/* 106 */     this.fromResultSet = true;
/* 107 */     this.exceptionInterceptor = exceptionInterceptor;
/*     */   }
/*     */   protected JDBC4MysqlSQLXML(ExceptionInterceptor exceptionInterceptor) {
/*     */     this.isClosed = false;
/* 111 */     this.fromResultSet = false;
/* 112 */     this.exceptionInterceptor = exceptionInterceptor;
/*     */   }
/*     */   
/*     */   public void free() throws SQLException {
/* 116 */     this.stringRep = null;
/* 117 */     this.asDOMResult = null;
/* 118 */     this.asSAXResult = null;
/* 119 */     this.inputFactory = null;
/* 120 */     this.outputFactory = null;
/* 121 */     this.owningResultSet = null;
/* 122 */     this.workingWithResult = false;
/* 123 */     this.isClosed = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getString() throws SQLException {
/* 128 */     checkClosed();
/* 129 */     checkWorkingWithResult();
/*     */     
/* 131 */     if (this.fromResultSet) {
/* 132 */       return this.owningResultSet.getString(this.columnIndexOfXml);
/*     */     }
/*     */     
/* 135 */     return this.stringRep;
/*     */   }
/*     */   
/*     */   private void checkClosed() throws SQLException {
/* 139 */     if (this.isClosed) {
/* 140 */       throw SQLError.createSQLException("SQLXMLInstance has been free()d", this.exceptionInterceptor);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkWorkingWithResult() throws SQLException {
/* 146 */     if (this.workingWithResult) {
/* 147 */       throw SQLError.createSQLException("Can't perform requested operation after getResult() has been called to write XML data", "S1009", this.exceptionInterceptor);
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setString(String str) throws SQLException {
/* 181 */     checkClosed();
/* 182 */     checkWorkingWithResult();
/*     */     
/* 184 */     this.stringRep = str;
/* 185 */     this.fromResultSet = false;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() throws SQLException {
/* 189 */     checkClosed();
/* 190 */     checkWorkingWithResult();
/*     */     
/* 192 */     if (!this.fromResultSet) {
/* 193 */       return (this.stringRep == null || this.stringRep.length() == 0);
/*     */     }
/*     */     
/* 196 */     return false;
/*     */   }
/*     */   
/*     */   public InputStream getBinaryStream() throws SQLException {
/* 200 */     checkClosed();
/* 201 */     checkWorkingWithResult();
/*     */     
/* 203 */     return this.owningResultSet.getBinaryStream(this.columnIndexOfXml);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reader getCharacterStream() throws SQLException {
/* 232 */     checkClosed();
/* 233 */     checkWorkingWithResult();
/*     */     
/* 235 */     return this.owningResultSet.getCharacterStream(this.columnIndexOfXml);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Source getSource(Class clazz) throws SQLException {
/* 287 */     checkClosed();
/* 288 */     checkWorkingWithResult();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 294 */     if (clazz == null || clazz.equals(SAXSource.class)) {
/*     */       
/* 296 */       InputSource inputSource = null;
/*     */       
/* 298 */       if (this.fromResultSet) {
/* 299 */         inputSource = new InputSource(this.owningResultSet.getCharacterStream(this.columnIndexOfXml));
/*     */       } else {
/*     */         
/* 302 */         inputSource = new InputSource(new StringReader(this.stringRep));
/*     */       } 
/*     */       
/* 305 */       return new SAXSource(inputSource);
/* 306 */     }  if (clazz.equals(DOMSource.class)) {
/*     */       try {
/* 308 */         DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
/*     */         
/* 310 */         builderFactory.setNamespaceAware(true);
/* 311 */         DocumentBuilder builder = builderFactory.newDocumentBuilder();
/*     */         
/* 313 */         InputSource inputSource = null;
/*     */         
/* 315 */         if (this.fromResultSet) {
/* 316 */           inputSource = new InputSource(this.owningResultSet.getCharacterStream(this.columnIndexOfXml));
/*     */         } else {
/*     */           
/* 319 */           inputSource = new InputSource(new StringReader(this.stringRep));
/*     */         } 
/*     */ 
/*     */         
/* 323 */         return new DOMSource(builder.parse(inputSource));
/* 324 */       } catch (Throwable t) {
/* 325 */         SQLException sqlEx = SQLError.createSQLException(t.getMessage(), "S1009", this.exceptionInterceptor);
/*     */         
/* 327 */         sqlEx.initCause(t);
/*     */         
/* 329 */         throw sqlEx;
/*     */       } 
/*     */     }
/* 332 */     if (clazz.equals(StreamSource.class)) {
/* 333 */       Reader reader = null;
/*     */       
/* 335 */       if (this.fromResultSet) {
/* 336 */         reader = this.owningResultSet.getCharacterStream(this.columnIndexOfXml);
/*     */       } else {
/*     */         
/* 339 */         reader = new StringReader(this.stringRep);
/*     */       } 
/*     */       
/* 342 */       return new StreamSource(reader);
/* 343 */     }  if (clazz.equals(StAXSource.class)) {
/*     */       try {
/* 345 */         Reader reader = null;
/*     */         
/* 347 */         if (this.fromResultSet) {
/* 348 */           reader = this.owningResultSet.getCharacterStream(this.columnIndexOfXml);
/*     */         } else {
/*     */           
/* 351 */           reader = new StringReader(this.stringRep);
/*     */         } 
/*     */         
/* 354 */         return new StAXSource(this.inputFactory.createXMLStreamReader(reader));
/*     */       }
/* 356 */       catch (XMLStreamException ex) {
/* 357 */         SQLException sqlEx = SQLError.createSQLException(ex.getMessage(), "S1009", this.exceptionInterceptor);
/*     */         
/* 359 */         sqlEx.initCause(ex);
/*     */         
/* 361 */         throw sqlEx;
/*     */       } 
/*     */     }
/* 364 */     throw SQLError.createSQLException("XML Source of type \"" + clazz.toString() + "\" Not supported.", "S1009", this.exceptionInterceptor);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OutputStream setBinaryStream() throws SQLException {
/* 390 */     checkClosed();
/* 391 */     checkWorkingWithResult();
/*     */     
/* 393 */     this.workingWithResult = true;
/*     */     
/* 395 */     return setBinaryStreamInternal();
/*     */   }
/*     */ 
/*     */   
/*     */   private OutputStream setBinaryStreamInternal() throws SQLException {
/* 400 */     this.asByteArrayOutputStream = new ByteArrayOutputStream();
/*     */     
/* 402 */     return this.asByteArrayOutputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Writer setCharacterStream() throws SQLException {
/* 431 */     checkClosed();
/* 432 */     checkWorkingWithResult();
/*     */     
/* 434 */     this.workingWithResult = true;
/*     */     
/* 436 */     return setCharacterStreamInternal();
/*     */   }
/*     */ 
/*     */   
/*     */   private Writer setCharacterStreamInternal() throws SQLException {
/* 441 */     this.asStringWriter = new StringWriter();
/*     */     
/* 443 */     return this.asStringWriter;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Result setResult(Class clazz) throws SQLException {
/* 492 */     checkClosed();
/* 493 */     checkWorkingWithResult();
/*     */     
/* 495 */     this.workingWithResult = true;
/* 496 */     this.asDOMResult = null;
/* 497 */     this.asSAXResult = null;
/* 498 */     this.saxToReaderConverter = null;
/* 499 */     this.stringRep = null;
/* 500 */     this.asStringWriter = null;
/* 501 */     this.asByteArrayOutputStream = null;
/*     */     
/* 503 */     if (clazz == null || clazz.equals(SAXResult.class)) {
/* 504 */       this.saxToReaderConverter = new SimpleSaxToReader();
/*     */       
/* 506 */       this.asSAXResult = new SAXResult(this.saxToReaderConverter);
/*     */       
/* 508 */       return this.asSAXResult;
/* 509 */     }  if (clazz.equals(DOMResult.class)) {
/*     */       
/* 511 */       this.asDOMResult = new DOMResult();
/* 512 */       return this.asDOMResult;
/*     */     } 
/* 514 */     if (clazz.equals(StreamResult.class))
/* 515 */       return new StreamResult(setCharacterStreamInternal()); 
/* 516 */     if (clazz.equals(StAXResult.class)) {
/*     */       try {
/* 518 */         if (this.outputFactory == null) {
/* 519 */           this.outputFactory = XMLOutputFactory.newInstance();
/*     */         }
/*     */         
/* 522 */         return new StAXResult(this.outputFactory.createXMLEventWriter(setCharacterStreamInternal()));
/*     */       }
/* 524 */       catch (XMLStreamException ex) {
/* 525 */         SQLException sqlEx = SQLError.createSQLException(ex.getMessage(), "S1009", this.exceptionInterceptor);
/*     */         
/* 527 */         sqlEx.initCause(ex);
/*     */         
/* 529 */         throw sqlEx;
/*     */       } 
/*     */     }
/* 532 */     throw SQLError.createSQLException("XML Result of type \"" + clazz.toString() + "\" Not supported.", "S1009", this.exceptionInterceptor);
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
/*     */   private Reader binaryInputStreamStreamToReader(ByteArrayOutputStream out) {
/*     */     try {
/* 547 */       String encoding = "UTF-8";
/*     */       
/*     */       try {
/* 550 */         ByteArrayInputStream bIn = new ByteArrayInputStream(out.toByteArray());
/*     */         
/* 552 */         XMLStreamReader reader = this.inputFactory.createXMLStreamReader(bIn);
/*     */ 
/*     */         
/* 555 */         int eventType = 0;
/*     */         
/* 557 */         while ((eventType = reader.next()) != 8) {
/* 558 */           if (eventType == 7) {
/* 559 */             String possibleEncoding = reader.getEncoding();
/*     */             
/* 561 */             if (possibleEncoding != null) {
/* 562 */               encoding = possibleEncoding;
/*     */             }
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 568 */       } catch (Throwable t) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 573 */       return new StringReader(new String(out.toByteArray(), encoding));
/* 574 */     } catch (UnsupportedEncodingException badEnc) {
/* 575 */       throw new RuntimeException(badEnc);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String readerToString(Reader reader) throws SQLException {
/* 580 */     StringBuffer buf = new StringBuffer();
/*     */     
/* 582 */     int charsRead = 0;
/*     */     
/* 584 */     char[] charBuf = new char[512];
/*     */     
/*     */     try {
/* 587 */       while ((charsRead = reader.read(charBuf)) != -1) {
/* 588 */         buf.append(charBuf, 0, charsRead);
/*     */       }
/* 590 */     } catch (IOException ioEx) {
/* 591 */       SQLException sqlEx = SQLError.createSQLException(ioEx.getMessage(), "S1009", this.exceptionInterceptor);
/*     */       
/* 593 */       sqlEx.initCause(ioEx);
/*     */       
/* 595 */       throw sqlEx;
/*     */     } 
/*     */     
/* 598 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Reader serializeAsCharacterStream() throws SQLException {
/* 603 */     checkClosed();
/* 604 */     if (this.workingWithResult) {
/*     */       
/* 606 */       if (this.stringRep != null) {
/* 607 */         return new StringReader(this.stringRep);
/*     */       }
/*     */       
/* 610 */       if (this.asDOMResult != null) {
/* 611 */         return new StringReader(domSourceToString());
/*     */       }
/*     */       
/* 614 */       if (this.asStringWriter != null) {
/* 615 */         return new StringReader(this.asStringWriter.toString());
/*     */       }
/*     */       
/* 618 */       if (this.asSAXResult != null) {
/* 619 */         return this.saxToReaderConverter.toReader();
/*     */       }
/*     */       
/* 622 */       if (this.asByteArrayOutputStream != null) {
/* 623 */         return binaryInputStreamStreamToReader(this.asByteArrayOutputStream);
/*     */       }
/*     */     } 
/*     */     
/* 627 */     return this.owningResultSet.getCharacterStream(this.columnIndexOfXml);
/*     */   }
/*     */   
/*     */   protected String domSourceToString() throws SQLException {
/*     */     try {
/* 632 */       DOMSource source = new DOMSource(this.asDOMResult.getNode());
/* 633 */       Transformer identity = TransformerFactory.newInstance().newTransformer();
/*     */       
/* 635 */       StringWriter stringOut = new StringWriter();
/* 636 */       Result result = new StreamResult(stringOut);
/* 637 */       identity.transform(source, result);
/*     */       
/* 639 */       return stringOut.toString();
/* 640 */     } catch (Throwable t) {
/* 641 */       SQLException sqlEx = SQLError.createSQLException(t.getMessage(), "S1009", this.exceptionInterceptor);
/*     */       
/* 643 */       sqlEx.initCause(t);
/*     */       
/* 645 */       throw sqlEx;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String serializeAsString() throws SQLException {
/* 650 */     checkClosed();
/* 651 */     if (this.workingWithResult) {
/*     */       
/* 653 */       if (this.stringRep != null) {
/* 654 */         return this.stringRep;
/*     */       }
/*     */       
/* 657 */       if (this.asDOMResult != null) {
/* 658 */         return domSourceToString();
/*     */       }
/*     */       
/* 661 */       if (this.asStringWriter != null) {
/* 662 */         return this.asStringWriter.toString();
/*     */       }
/*     */       
/* 665 */       if (this.asSAXResult != null) {
/* 666 */         return readerToString(this.saxToReaderConverter.toReader());
/*     */       }
/*     */       
/* 669 */       if (this.asByteArrayOutputStream != null) {
/* 670 */         return readerToString(binaryInputStreamStreamToReader(this.asByteArrayOutputStream));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 675 */     return this.owningResultSet.getString(this.columnIndexOfXml);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class SimpleSaxToReader
/*     */     extends DefaultHandler
/*     */   {
/* 700 */     StringBuffer buf = new StringBuffer();
/*     */ 
/*     */     
/* 703 */     public void startDocument() throws SQLException { this.buf.append("<?xml version='1.0' encoding='UTF-8'?>"); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void endDocument() throws SQLException {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void startElement(String namespaceURI, String sName, String qName, Attributes attrs) throws SAXException {
/* 713 */       this.buf.append("<");
/* 714 */       this.buf.append(qName);
/*     */       
/* 716 */       if (attrs != null) {
/* 717 */         for (int i = 0; i < attrs.getLength(); i++) {
/* 718 */           this.buf.append(" ");
/* 719 */           this.buf.append(attrs.getQName(i)).append("=\"");
/* 720 */           escapeCharsForXml(attrs.getValue(i), true);
/* 721 */           this.buf.append("\"");
/*     */         } 
/*     */       }
/*     */       
/* 725 */       this.buf.append(">");
/*     */     }
/*     */ 
/*     */     
/*     */     public void characters(char[] buf, int offset, int len) throws SAXException {
/* 730 */       if (!this.inCDATA) {
/* 731 */         escapeCharsForXml(buf, offset, len, false);
/*     */       } else {
/* 733 */         this.buf.append(buf, offset, len);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 739 */     public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException { characters(ch, start, length); }
/*     */ 
/*     */     
/*     */     private boolean inCDATA = false;
/*     */     
/*     */     public void startCDATA() throws SQLException {
/* 745 */       this.buf.append("<![CDATA[");
/* 746 */       this.inCDATA = true;
/*     */     }
/*     */     
/*     */     public void endCDATA() throws SQLException {
/* 750 */       this.inCDATA = false;
/* 751 */       this.buf.append("]]>");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void comment(char[] ch, int start, int length) throws SAXException {
/* 757 */       this.buf.append("<!--");
/* 758 */       for (int i = 0; i < length; i++) {
/* 759 */         this.buf.append(ch[start + i]);
/*     */       }
/* 761 */       this.buf.append("-->");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 766 */     Reader toReader() throws SQLException { return new StringReader(this.buf.toString()); }
/*     */ 
/*     */     
/*     */     private void escapeCharsForXml(String str, boolean isAttributeData) {
/* 770 */       if (str == null) {
/*     */         return;
/*     */       }
/*     */       
/* 774 */       int strLen = str.length();
/*     */       
/* 776 */       for (int i = 0; i < strLen; i++) {
/* 777 */         escapeCharsForXml(str.charAt(i), isAttributeData);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void escapeCharsForXml(char[] buf, int offset, int len, boolean isAttributeData) {
/* 784 */       if (buf == null) {
/*     */         return;
/*     */       }
/*     */       
/* 788 */       for (int i = 0; i < len; i++) {
/* 789 */         escapeCharsForXml(buf[offset + i], isAttributeData);
/*     */       }
/*     */     }
/*     */     
/*     */     private void escapeCharsForXml(char c, boolean isAttributeData) {
/* 794 */       switch (c) {
/*     */         case '<':
/* 796 */           this.buf.append("&lt;");
/*     */           return;
/*     */         
/*     */         case '>':
/* 800 */           this.buf.append("&gt;");
/*     */           return;
/*     */         
/*     */         case '&':
/* 804 */           this.buf.append("&amp;");
/*     */           return;
/*     */ 
/*     */         
/*     */         case '"':
/* 809 */           if (!isAttributeData) {
/* 810 */             this.buf.append("\"");
/*     */           } else {
/*     */             
/* 813 */             this.buf.append("&quot;");
/*     */           } 
/*     */           return;
/*     */ 
/*     */         
/*     */         case '\r':
/* 819 */           this.buf.append("&#xD;");
/*     */           return;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 825 */       if ((c >= '\001' && c <= '\037' && c != '\t' && c != '\n') || (c >= '' && c <= '') || c == ' ' || (isAttributeData && (c == '\t' || c == '\n'))) {
/*     */ 
/*     */         
/* 828 */         this.buf.append("&#x");
/* 829 */         this.buf.append(Integer.toHexString(c).toUpperCase());
/* 830 */         this.buf.append(";");
/*     */       } else {
/*     */         
/* 833 */         this.buf.append(c);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\JDBC4MysqlSQLXML.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.4
 */