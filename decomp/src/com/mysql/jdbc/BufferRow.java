/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.sql.Date;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Calendar;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BufferRow
/*     */   extends ResultSetRow
/*     */ {
/*     */   private Buffer rowFromServer;
/*  58 */   private int homePosition = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   private int preNullBitmaskHomePosition = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   private int lastRequestedIndex = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int lastRequestedPos;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Field[] metadata;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isBinaryEncoded;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean[] isNull;
/*     */ 
/*     */ 
/*     */   
/*     */   private List openStreams;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BufferRow(Buffer buf, Field[] fields, boolean isBinaryEncoded, ExceptionInterceptor exceptionInterceptor) throws SQLException {
/* 102 */     super(exceptionInterceptor);
/*     */     
/* 104 */     this.rowFromServer = buf;
/* 105 */     this.metadata = fields;
/* 106 */     this.isBinaryEncoded = isBinaryEncoded;
/* 107 */     this.homePosition = this.rowFromServer.getPosition();
/* 108 */     this.preNullBitmaskHomePosition = this.homePosition;
/*     */     
/* 110 */     if (fields != null) {
/* 111 */       setMetadata(fields);
/*     */     }
/*     */   }
/*     */   
/*     */   public void closeOpenStreams() {
/* 116 */     if (this.openStreams != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 122 */       Iterator iter = this.openStreams.iterator();
/*     */       
/* 124 */       while (iter.hasNext()) {
/*     */         
/*     */         try {
/* 127 */           ((InputStream)iter.next()).close();
/* 128 */         } catch (IOException e) {}
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 133 */       this.openStreams.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   private int findAndSeekToOffset(int index) throws SQLException {
/* 138 */     if (!this.isBinaryEncoded) {
/*     */       
/* 140 */       if (index == 0) {
/* 141 */         this.lastRequestedIndex = 0;
/* 142 */         this.lastRequestedPos = this.homePosition;
/* 143 */         this.rowFromServer.setPosition(this.homePosition);
/*     */         
/* 145 */         return 0;
/*     */       } 
/*     */       
/* 148 */       if (index == this.lastRequestedIndex) {
/* 149 */         this.rowFromServer.setPosition(this.lastRequestedPos);
/*     */         
/* 151 */         return this.lastRequestedPos;
/*     */       } 
/*     */       
/* 154 */       int startingIndex = 0;
/*     */       
/* 156 */       if (index > this.lastRequestedIndex) {
/* 157 */         if (this.lastRequestedIndex >= 0) {
/* 158 */           startingIndex = this.lastRequestedIndex;
/*     */         } else {
/* 160 */           startingIndex = 0;
/*     */         } 
/*     */         
/* 163 */         this.rowFromServer.setPosition(this.lastRequestedPos);
/*     */       } else {
/* 165 */         this.rowFromServer.setPosition(this.homePosition);
/*     */       } 
/*     */       
/* 168 */       for (int i = startingIndex; i < index; i++) {
/* 169 */         this.rowFromServer.fastSkipLenByteArray();
/*     */       }
/*     */       
/* 172 */       this.lastRequestedIndex = index;
/* 173 */       this.lastRequestedPos = this.rowFromServer.getPosition();
/*     */       
/* 175 */       return this.lastRequestedPos;
/*     */     } 
/*     */     
/* 178 */     return findAndSeekToOffsetForBinaryEncoding(index);
/*     */   }
/*     */ 
/*     */   
/*     */   private int findAndSeekToOffsetForBinaryEncoding(int index) throws SQLException {
/* 183 */     if (index == 0) {
/* 184 */       this.lastRequestedIndex = 0;
/* 185 */       this.lastRequestedPos = this.homePosition;
/* 186 */       this.rowFromServer.setPosition(this.homePosition);
/*     */       
/* 188 */       return 0;
/*     */     } 
/*     */     
/* 191 */     if (index == this.lastRequestedIndex) {
/* 192 */       this.rowFromServer.setPosition(this.lastRequestedPos);
/*     */       
/* 194 */       return this.lastRequestedPos;
/*     */     } 
/*     */     
/* 197 */     int startingIndex = 0;
/*     */     
/* 199 */     if (index > this.lastRequestedIndex) {
/* 200 */       if (this.lastRequestedIndex >= 0) {
/* 201 */         startingIndex = this.lastRequestedIndex;
/*     */       } else {
/*     */         
/* 204 */         startingIndex = 0;
/* 205 */         this.lastRequestedPos = this.homePosition;
/*     */       } 
/*     */       
/* 208 */       this.rowFromServer.setPosition(this.lastRequestedPos);
/*     */     } else {
/* 210 */       this.rowFromServer.setPosition(this.homePosition);
/*     */     } 
/*     */     
/* 213 */     for (int i = startingIndex; i < index; i++) {
/* 214 */       if (!this.isNull[i]) {
/*     */ 
/*     */ 
/*     */         
/* 218 */         int curPosition = this.rowFromServer.getPosition();
/*     */         
/* 220 */         switch (this.metadata[i].getMysqlType()) {
/*     */           case 6:
/*     */             break;
/*     */ 
/*     */           
/*     */           case 1:
/* 226 */             this.rowFromServer.setPosition(curPosition + 1);
/*     */             break;
/*     */           
/*     */           case 2:
/*     */           case 13:
/* 231 */             this.rowFromServer.setPosition(curPosition + 2);
/*     */             break;
/*     */           
/*     */           case 3:
/*     */           case 9:
/* 236 */             this.rowFromServer.setPosition(curPosition + 4);
/*     */             break;
/*     */           
/*     */           case 8:
/* 240 */             this.rowFromServer.setPosition(curPosition + 8);
/*     */             break;
/*     */           
/*     */           case 4:
/* 244 */             this.rowFromServer.setPosition(curPosition + 4);
/*     */             break;
/*     */           
/*     */           case 5:
/* 248 */             this.rowFromServer.setPosition(curPosition + 8);
/*     */             break;
/*     */           
/*     */           case 11:
/* 252 */             this.rowFromServer.fastSkipLenByteArray();
/*     */             break;
/*     */ 
/*     */           
/*     */           case 10:
/* 257 */             this.rowFromServer.fastSkipLenByteArray();
/*     */             break;
/*     */           
/*     */           case 7:
/*     */           case 12:
/* 262 */             this.rowFromServer.fastSkipLenByteArray();
/*     */             break;
/*     */           
/*     */           case 0:
/*     */           case 15:
/*     */           case 16:
/*     */           case 246:
/*     */           case 249:
/*     */           case 250:
/*     */           case 251:
/*     */           case 252:
/*     */           case 253:
/*     */           case 254:
/*     */           case 255:
/* 276 */             this.rowFromServer.fastSkipLenByteArray();
/*     */             break;
/*     */ 
/*     */           
/*     */           default:
/* 281 */             throw SQLError.createSQLException(Messages.getString("MysqlIO.97") + this.metadata[i].getMysqlType() + Messages.getString("MysqlIO.98") + (i + 1) + Messages.getString("MysqlIO.99") + this.metadata.length + Messages.getString("MysqlIO.100"), "S1000", this.exceptionInterceptor);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       } 
/*     */     } 
/* 293 */     this.lastRequestedIndex = index;
/* 294 */     this.lastRequestedPos = this.rowFromServer.getPosition();
/*     */     
/* 296 */     return this.lastRequestedPos;
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream getBinaryInputStream(int columnIndex) throws SQLException {
/* 301 */     if (this.isBinaryEncoded && 
/* 302 */       isNull(columnIndex)) {
/* 303 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 307 */     findAndSeekToOffset(columnIndex);
/*     */     
/* 309 */     long length = this.rowFromServer.readFieldLength();
/*     */     
/* 311 */     int offset = this.rowFromServer.getPosition();
/*     */     
/* 313 */     if (length == -1L) {
/* 314 */       return null;
/*     */     }
/*     */     
/* 317 */     InputStream stream = new ByteArrayInputStream(this.rowFromServer.getByteBuffer(), offset, (int)length);
/*     */ 
/*     */     
/* 320 */     if (this.openStreams == null) {
/* 321 */       this.openStreams = new LinkedList();
/*     */     }
/*     */     
/* 324 */     return stream;
/*     */   }
/*     */   
/*     */   public byte[] getColumnValue(int index) throws SQLException {
/* 328 */     findAndSeekToOffset(index);
/*     */     
/* 330 */     if (!this.isBinaryEncoded) {
/* 331 */       return this.rowFromServer.readLenByteArray(0);
/*     */     }
/*     */     
/* 334 */     if (this.isNull[index]) {
/* 335 */       return null;
/*     */     }
/*     */     
/* 338 */     switch (this.metadata[index].getMysqlType()) {
/*     */       case 6:
/* 340 */         return null;
/*     */       
/*     */       case 1:
/* 343 */         return new byte[] { this.rowFromServer.readByte() };
/*     */       
/*     */       case 2:
/*     */       case 13:
/* 347 */         return this.rowFromServer.getBytes(2);
/*     */       
/*     */       case 3:
/*     */       case 9:
/* 351 */         return this.rowFromServer.getBytes(4);
/*     */       
/*     */       case 8:
/* 354 */         return this.rowFromServer.getBytes(8);
/*     */       
/*     */       case 4:
/* 357 */         return this.rowFromServer.getBytes(4);
/*     */       
/*     */       case 5:
/* 360 */         return this.rowFromServer.getBytes(8);
/*     */       
/*     */       case 0:
/*     */       case 7:
/*     */       case 10:
/*     */       case 11:
/*     */       case 12:
/*     */       case 15:
/*     */       case 16:
/*     */       case 246:
/*     */       case 249:
/*     */       case 250:
/*     */       case 251:
/*     */       case 252:
/*     */       case 253:
/*     */       case 254:
/*     */       case 255:
/* 377 */         return this.rowFromServer.readLenByteArray(0);
/*     */     } 
/*     */     
/* 380 */     throw SQLError.createSQLException(Messages.getString("MysqlIO.97") + this.metadata[index].getMysqlType() + Messages.getString("MysqlIO.98") + (index + 1) + Messages.getString("MysqlIO.99") + this.metadata.length + Messages.getString("MysqlIO.100"), "S1000", this.exceptionInterceptor);
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
/*     */   public int getInt(int columnIndex) throws SQLException {
/* 392 */     findAndSeekToOffset(columnIndex);
/*     */     
/* 394 */     long length = this.rowFromServer.readFieldLength();
/*     */     
/* 396 */     int offset = this.rowFromServer.getPosition();
/*     */     
/* 398 */     if (length == -1L) {
/* 399 */       return 0;
/*     */     }
/*     */     
/* 402 */     return StringUtils.getInt(this.rowFromServer.getByteBuffer(), offset, offset + (int)length);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong(int columnIndex) throws SQLException {
/* 407 */     findAndSeekToOffset(columnIndex);
/*     */     
/* 409 */     long length = this.rowFromServer.readFieldLength();
/*     */     
/* 411 */     int offset = this.rowFromServer.getPosition();
/*     */     
/* 413 */     if (length == -1L) {
/* 414 */       return 0L;
/*     */     }
/*     */     
/* 417 */     return StringUtils.getLong(this.rowFromServer.getByteBuffer(), offset, offset + (int)length);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getNativeDouble(int columnIndex) throws SQLException {
/* 422 */     if (isNull(columnIndex)) {
/* 423 */       return 0.0D;
/*     */     }
/*     */     
/* 426 */     findAndSeekToOffset(columnIndex);
/*     */     
/* 428 */     int offset = this.rowFromServer.getPosition();
/*     */     
/* 430 */     return getNativeDouble(this.rowFromServer.getByteBuffer(), offset);
/*     */   }
/*     */   
/*     */   public float getNativeFloat(int columnIndex) throws SQLException {
/* 434 */     if (isNull(columnIndex)) {
/* 435 */       return 0.0F;
/*     */     }
/*     */     
/* 438 */     findAndSeekToOffset(columnIndex);
/*     */     
/* 440 */     int offset = this.rowFromServer.getPosition();
/*     */     
/* 442 */     return getNativeFloat(this.rowFromServer.getByteBuffer(), offset);
/*     */   }
/*     */   
/*     */   public int getNativeInt(int columnIndex) throws SQLException {
/* 446 */     if (isNull(columnIndex)) {
/* 447 */       return 0;
/*     */     }
/*     */     
/* 450 */     findAndSeekToOffset(columnIndex);
/*     */     
/* 452 */     int offset = this.rowFromServer.getPosition();
/*     */     
/* 454 */     return getNativeInt(this.rowFromServer.getByteBuffer(), offset);
/*     */   }
/*     */   
/*     */   public long getNativeLong(int columnIndex) throws SQLException {
/* 458 */     if (isNull(columnIndex)) {
/* 459 */       return 0L;
/*     */     }
/*     */     
/* 462 */     findAndSeekToOffset(columnIndex);
/*     */     
/* 464 */     int offset = this.rowFromServer.getPosition();
/*     */     
/* 466 */     return getNativeLong(this.rowFromServer.getByteBuffer(), offset);
/*     */   }
/*     */   
/*     */   public short getNativeShort(int columnIndex) throws SQLException {
/* 470 */     if (isNull(columnIndex)) {
/* 471 */       return 0;
/*     */     }
/*     */     
/* 474 */     findAndSeekToOffset(columnIndex);
/*     */     
/* 476 */     int offset = this.rowFromServer.getPosition();
/*     */     
/* 478 */     return getNativeShort(this.rowFromServer.getByteBuffer(), offset);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Timestamp getNativeTimestamp(int columnIndex, Calendar targetCalendar, TimeZone tz, boolean rollForward, MySQLConnection conn, ResultSetImpl rs) throws SQLException {
/* 484 */     if (isNull(columnIndex)) {
/* 485 */       return null;
/*     */     }
/*     */     
/* 488 */     findAndSeekToOffset(columnIndex);
/*     */     
/* 490 */     long length = this.rowFromServer.readFieldLength();
/*     */     
/* 492 */     int offset = this.rowFromServer.getPosition();
/*     */     
/* 494 */     return getNativeTimestamp(this.rowFromServer.getByteBuffer(), offset, (int)length, targetCalendar, tz, rollForward, conn, rs);
/*     */   }
/*     */ 
/*     */   
/*     */   public Reader getReader(int columnIndex) throws SQLException {
/* 499 */     InputStream stream = getBinaryInputStream(columnIndex);
/*     */     
/* 501 */     if (stream == null) {
/* 502 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 506 */       return new InputStreamReader(stream, this.metadata[columnIndex].getCharacterSet());
/*     */     }
/* 508 */     catch (UnsupportedEncodingException e) {
/* 509 */       SQLException sqlEx = SQLError.createSQLException("", this.exceptionInterceptor);
/*     */       
/* 511 */       sqlEx.initCause(e);
/*     */       
/* 513 */       throw sqlEx;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getString(int columnIndex, String encoding, MySQLConnection conn) throws SQLException {
/* 519 */     if (this.isBinaryEncoded && 
/* 520 */       isNull(columnIndex)) {
/* 521 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 525 */     findAndSeekToOffset(columnIndex);
/*     */     
/* 527 */     long length = this.rowFromServer.readFieldLength();
/*     */     
/* 529 */     if (length == -1L) {
/* 530 */       return null;
/*     */     }
/*     */     
/* 533 */     if (length == 0L) {
/* 534 */       return "";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 540 */     int offset = this.rowFromServer.getPosition();
/*     */     
/* 542 */     return getString(encoding, conn, this.rowFromServer.getByteBuffer(), offset, (int)length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Time getTimeFast(int columnIndex, Calendar targetCalendar, TimeZone tz, boolean rollForward, MySQLConnection conn, ResultSetImpl rs) throws SQLException {
/* 549 */     if (isNull(columnIndex)) {
/* 550 */       return null;
/*     */     }
/*     */     
/* 553 */     findAndSeekToOffset(columnIndex);
/*     */     
/* 555 */     long length = this.rowFromServer.readFieldLength();
/*     */     
/* 557 */     int offset = this.rowFromServer.getPosition();
/*     */     
/* 559 */     return getTimeFast(columnIndex, this.rowFromServer.getByteBuffer(), offset, (int)length, targetCalendar, tz, rollForward, conn, rs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Timestamp getTimestampFast(int columnIndex, Calendar targetCalendar, TimeZone tz, boolean rollForward, MySQLConnection conn, ResultSetImpl rs) throws SQLException {
/* 566 */     if (isNull(columnIndex)) {
/* 567 */       return null;
/*     */     }
/*     */     
/* 570 */     findAndSeekToOffset(columnIndex);
/*     */     
/* 572 */     long length = this.rowFromServer.readFieldLength();
/*     */     
/* 574 */     int offset = this.rowFromServer.getPosition();
/*     */     
/* 576 */     return getTimestampFast(columnIndex, this.rowFromServer.getByteBuffer(), offset, (int)length, targetCalendar, tz, rollForward, conn, rs);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFloatingPointNumber(int index) throws SQLException {
/* 582 */     if (this.isBinaryEncoded) {
/* 583 */       switch (this.metadata[index].getSQLType()) {
/*     */         case 2:
/*     */         case 3:
/*     */         case 6:
/*     */         case 8:
/* 588 */           return true;
/*     */       } 
/* 590 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 594 */     findAndSeekToOffset(index);
/*     */     
/* 596 */     long length = this.rowFromServer.readFieldLength();
/*     */     
/* 598 */     if (length == -1L) {
/* 599 */       return false;
/*     */     }
/*     */     
/* 602 */     if (length == 0L) {
/* 603 */       return false;
/*     */     }
/*     */     
/* 606 */     int offset = this.rowFromServer.getPosition();
/* 607 */     byte[] buffer = this.rowFromServer.getByteBuffer();
/*     */     
/* 609 */     for (int i = 0; i < (int)length; i++) {
/* 610 */       char c = (char)buffer[offset + i];
/*     */       
/* 612 */       if (c == 'e' || c == 'E') {
/* 613 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 617 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isNull(int index) throws SQLException {
/* 621 */     if (!this.isBinaryEncoded) {
/* 622 */       findAndSeekToOffset(index);
/*     */       
/* 624 */       return (this.rowFromServer.readFieldLength() == -1L);
/*     */     } 
/*     */     
/* 627 */     return this.isNull[index];
/*     */   }
/*     */   
/*     */   public long length(int index) throws SQLException {
/* 631 */     findAndSeekToOffset(index);
/*     */     
/* 633 */     long length = this.rowFromServer.readFieldLength();
/*     */     
/* 635 */     if (length == -1L) {
/* 636 */       return 0L;
/*     */     }
/*     */     
/* 639 */     return length;
/*     */   }
/*     */ 
/*     */   
/* 643 */   public void setColumnValue(int index, byte[] value) throws SQLException { throw new OperationNotSupportedException(); }
/*     */ 
/*     */   
/*     */   public ResultSetRow setMetadata(Field[] f) throws SQLException {
/* 647 */     super.setMetadata(f);
/*     */     
/* 649 */     if (this.isBinaryEncoded) {
/* 650 */       setupIsNullBitmask();
/*     */     }
/*     */     
/* 653 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setupIsNullBitmask() {
/* 662 */     if (this.isNull != null) {
/*     */       return;
/*     */     }
/*     */     
/* 666 */     this.rowFromServer.setPosition(this.preNullBitmaskHomePosition);
/*     */     
/* 668 */     int nullCount = (this.metadata.length + 9) / 8;
/*     */     
/* 670 */     byte[] nullBitMask = new byte[nullCount];
/*     */     
/* 672 */     for (i = 0; i < nullCount; i++) {
/* 673 */       nullBitMask[i] = this.rowFromServer.readByte();
/*     */     }
/*     */     
/* 676 */     this.homePosition = this.rowFromServer.getPosition();
/*     */     
/* 678 */     this.isNull = new boolean[this.metadata.length];
/*     */     
/* 680 */     int nullMaskPos = 0;
/* 681 */     int bit = 4;
/*     */     
/* 683 */     for (int i = 0; i < this.metadata.length; i++) {
/*     */       
/* 685 */       this.isNull[i] = ((nullBitMask[nullMaskPos] & bit) != 0);
/*     */       
/* 687 */       if ((bit <<= 1 & 0xFF) == 0) {
/* 688 */         bit = 1;
/*     */         
/* 690 */         nullMaskPos++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Date getDateFast(int columnIndex, MySQLConnection conn, ResultSetImpl rs, Calendar targetCalendar) throws SQLException {
/* 697 */     if (isNull(columnIndex)) {
/* 698 */       return null;
/*     */     }
/*     */     
/* 701 */     findAndSeekToOffset(columnIndex);
/*     */     
/* 703 */     long length = this.rowFromServer.readFieldLength();
/*     */     
/* 705 */     int offset = this.rowFromServer.getPosition();
/*     */     
/* 707 */     return getDateFast(columnIndex, this.rowFromServer.getByteBuffer(), offset, (int)length, conn, rs, targetCalendar);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getNativeDate(int columnIndex, MySQLConnection conn, ResultSetImpl rs, Calendar cal) throws SQLException {
/* 713 */     if (isNull(columnIndex)) {
/* 714 */       return null;
/*     */     }
/*     */     
/* 717 */     findAndSeekToOffset(columnIndex);
/*     */     
/* 719 */     long length = this.rowFromServer.readFieldLength();
/*     */     
/* 721 */     int offset = this.rowFromServer.getPosition();
/*     */     
/* 723 */     return getNativeDate(columnIndex, this.rowFromServer.getByteBuffer(), offset, (int)length, conn, rs, cal);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getNativeDateTimeValue(int columnIndex, Calendar targetCalendar, int jdbcType, int mysqlType, TimeZone tz, boolean rollForward, MySQLConnection conn, ResultSetImpl rs) throws SQLException {
/* 731 */     if (isNull(columnIndex)) {
/* 732 */       return null;
/*     */     }
/*     */     
/* 735 */     findAndSeekToOffset(columnIndex);
/*     */     
/* 737 */     long length = this.rowFromServer.readFieldLength();
/*     */     
/* 739 */     int offset = this.rowFromServer.getPosition();
/*     */     
/* 741 */     return getNativeDateTimeValue(columnIndex, this.rowFromServer.getByteBuffer(), offset, (int)length, targetCalendar, jdbcType, mysqlType, tz, rollForward, conn, rs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Time getNativeTime(int columnIndex, Calendar targetCalendar, TimeZone tz, boolean rollForward, MySQLConnection conn, ResultSetImpl rs) throws SQLException {
/* 749 */     if (isNull(columnIndex)) {
/* 750 */       return null;
/*     */     }
/*     */     
/* 753 */     findAndSeekToOffset(columnIndex);
/*     */     
/* 755 */     long length = this.rowFromServer.readFieldLength();
/*     */     
/* 757 */     int offset = this.rowFromServer.getPosition();
/*     */     
/* 759 */     return getNativeTime(columnIndex, this.rowFromServer.getByteBuffer(), offset, (int)length, targetCalendar, tz, rollForward, conn, rs);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 764 */   public int getBytesSize() { return this.rowFromServer.getBufLength(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\BufferRow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */