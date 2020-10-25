/*     */ package com.avaje.ebeaninternal.server.persist;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.BindParams;
/*     */ import com.avaje.ebeaninternal.server.core.Message;
/*     */ import com.avaje.ebeaninternal.server.type.DataBind;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarType;
/*     */ import com.avaje.ebeaninternal.server.type.TypeManager;
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.CallableStatement;
/*     */ import java.sql.Date;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.PersistenceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Binder
/*     */ {
/*  43 */   private static final Logger logger = Logger.getLogger(Binder.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final TypeManager typeManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   public Binder(TypeManager typeManager) { this.typeManager = typeManager; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind(BindValues bindValues, DataBind dataBind, StringBuilder bindBuf) throws SQLException {
/*  64 */     String logPrefix = "";
/*     */     
/*  66 */     ArrayList<BindValues.Value> list = bindValues.values();
/*  67 */     for (int i = 0; i < list.size(); i++) {
/*  68 */       BindValues.Value bindValue = (BindValues.Value)list.get(i);
/*  69 */       if (bindValue.isComment()) {
/*  70 */         if (bindBuf != null) {
/*  71 */           bindBuf.append(bindValue.getName());
/*  72 */           if (logPrefix.equals("")) {
/*  73 */             logPrefix = ", ";
/*     */           }
/*     */         } 
/*     */       } else {
/*  77 */         Object val = bindValue.getValue();
/*  78 */         int dt = bindValue.getDbType();
/*  79 */         bindObject(dataBind, val, dt);
/*     */         
/*  81 */         if (bindBuf != null) {
/*  82 */           bindBuf.append(logPrefix);
/*  83 */           if (logPrefix.equals("")) {
/*  84 */             logPrefix = ", ";
/*     */           }
/*  86 */           bindBuf.append(bindValue.getName());
/*  87 */           bindBuf.append("=");
/*  88 */           if (isLob(dt)) {
/*  89 */             bindBuf.append("[LOB]");
/*     */           } else {
/*  91 */             bindBuf.append(String.valueOf(val));
/*     */           } 
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
/*     */   public String bind(BindParams bindParams, DataBind dataBind) throws SQLException {
/* 104 */     StringBuilder bindLog = new StringBuilder();
/* 105 */     bind(bindParams, dataBind, bindLog);
/* 106 */     return bindLog.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   public void bind(BindParams bindParams, DataBind dataBind, StringBuilder bindLog) throws SQLException { bind(bindParams.positionedParameters(), dataBind, bindLog); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind(List<BindParams.Param> list, DataBind dataBind, StringBuilder bindLog) throws SQLException {
/* 124 */     CallableStatement cstmt = null;
/*     */     
/* 126 */     if (dataBind.getPstmt() instanceof CallableStatement) {
/* 127 */       cstmt = (CallableStatement)dataBind.getPstmt();
/*     */     }
/*     */ 
/*     */     
/* 131 */     Object value = null;
/*     */     try {
/* 133 */       for (int i = 0; i < list.size(); i++) {
/*     */         
/* 135 */         BindParams.Param param = (BindParams.Param)list.get(i);
/*     */         
/* 137 */         if (param.isOutParam() && cstmt != null) {
/* 138 */           cstmt.registerOutParameter(dataBind.nextPos(), param.getType());
/* 139 */           if (param.isInParam()) {
/* 140 */             dataBind.decrementPos();
/*     */           }
/*     */         } 
/* 143 */         if (param.isInParam()) {
/* 144 */           value = param.getInValue();
/* 145 */           if (bindLog != null) {
/* 146 */             if (param.isEncryptionKey()) {
/* 147 */               bindLog.append("****");
/*     */             } else {
/* 149 */               bindLog.append(value);
/*     */             } 
/* 151 */             bindLog.append(", ");
/*     */           } 
/* 153 */           if (value == null) {
/*     */             
/* 155 */             bindObject(dataBind, null, param.getType());
/*     */           } else {
/* 157 */             bindObject(dataBind, value);
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/* 162 */     } catch (SQLException ex) {
/* 163 */       logger.warning(Message.msg("fetch.bind.error", "" + (dataBind.currentPos() - 1), value));
/* 164 */       throw ex;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bindObject(DataBind dataBind, Object value) throws SQLException {
/* 173 */     if (value == null) {
/*     */       
/* 175 */       bindObject(dataBind, null, 1111);
/*     */     }
/*     */     else {
/*     */       
/* 179 */       ScalarType<?> type = this.typeManager.getScalarType(value.getClass());
/* 180 */       if (type == null) {
/*     */         
/* 182 */         String msg = "No ScalarType registered for " + value.getClass();
/* 183 */         throw new PersistenceException(msg);
/*     */       } 
/* 185 */       if (!type.isJdbcNative())
/*     */       {
/* 187 */         value = type.toJdbcType(value);
/*     */       }
/*     */       
/* 190 */       int dbType = type.getJdbcType();
/* 191 */       bindObject(dataBind, value, dbType);
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
/*     */   public void bindObject(DataBind dataBind, Object data, int dbType) throws SQLException {
/* 210 */     if (data == null) {
/* 211 */       dataBind.setNull(dbType);
/*     */       
/*     */       return;
/*     */     } 
/* 215 */     switch (dbType) {
/*     */       case -1:
/* 217 */         bindLongVarChar(dataBind, data);
/*     */         return;
/*     */       
/*     */       case -4:
/* 221 */         bindLongVarBinary(dataBind, data);
/*     */         return;
/*     */       
/*     */       case 2005:
/* 225 */         bindClob(dataBind, data);
/*     */         return;
/*     */       
/*     */       case 2004:
/* 229 */         bindBlob(dataBind, data);
/*     */         return;
/*     */     } 
/*     */ 
/*     */     
/* 234 */     bindSimpleData(dataBind, dbType, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void bindSimpleData(DataBind b, int dataType, Object data) throws SQLException {
/*     */     try {
/*     */       Boolean bitBool, bo;
/* 245 */       switch (dataType) {
/*     */         case 16:
/* 247 */           bo = (Boolean)data;
/* 248 */           b.setBoolean(bo.booleanValue());
/*     */           return;
/*     */         
/*     */         case -7:
/* 252 */           bitBool = (Boolean)data;
/* 253 */           b.setBoolean(bitBool.booleanValue());
/*     */           return;
/*     */         
/*     */         case 12:
/* 257 */           b.setString((String)data);
/*     */           return;
/*     */         
/*     */         case 1:
/* 261 */           b.setString(data.toString());
/*     */           return;
/*     */         
/*     */         case -6:
/* 265 */           b.setByte(((Byte)data).byteValue());
/*     */           return;
/*     */         
/*     */         case 5:
/* 269 */           b.setShort(((Short)data).shortValue());
/*     */           return;
/*     */         
/*     */         case 4:
/* 273 */           b.setInt(((Integer)data).intValue());
/*     */           return;
/*     */         
/*     */         case -5:
/* 277 */           b.setLong(((Long)data).longValue());
/*     */           return;
/*     */         
/*     */         case 7:
/* 281 */           b.setFloat(((Float)data).floatValue());
/*     */           return;
/*     */ 
/*     */         
/*     */         case 6:
/* 286 */           b.setDouble(((Double)data).doubleValue());
/*     */           return;
/*     */         
/*     */         case 8:
/* 290 */           b.setDouble(((Double)data).doubleValue());
/*     */           return;
/*     */         
/*     */         case 2:
/* 294 */           b.setBigDecimal((BigDecimal)data);
/*     */           return;
/*     */         
/*     */         case 3:
/* 298 */           b.setBigDecimal((BigDecimal)data);
/*     */           return;
/*     */ 
/*     */         
/*     */         case 92:
/* 303 */           b.setTime((Time)data);
/*     */           return;
/*     */ 
/*     */         
/*     */         case 91:
/* 308 */           b.setDate((Date)data);
/*     */           return;
/*     */ 
/*     */         
/*     */         case 93:
/* 313 */           b.setTimestamp((Timestamp)data);
/*     */           return;
/*     */         
/*     */         case -2:
/* 317 */           b.setBytes((byte[])data);
/*     */           return;
/*     */         
/*     */         case -3:
/* 321 */           b.setBytes((byte[])data);
/*     */           return;
/*     */         
/*     */         case 1111:
/* 325 */           b.setObject(data);
/*     */           return;
/*     */ 
/*     */         
/*     */         case 2000:
/* 330 */           b.setObject(data);
/*     */           return;
/*     */       } 
/*     */       
/* 334 */       String msg = Message.msg("persist.bind.datatype", "" + dataType, "" + b.currentPos());
/* 335 */       throw new SQLException(msg);
/*     */     
/*     */     }
/* 338 */     catch (Exception e) {
/* 339 */       String dataClass = "Data is null?";
/* 340 */       if (data != null) {
/* 341 */         dataClass = data.getClass().getName();
/*     */       }
/* 343 */       String m = "Error with property[" + b.currentPos() + "] dt[" + dataType + "]";
/* 344 */       m = m + "data[" + data + "][" + dataClass + "]";
/* 345 */       throw new PersistenceException(m, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void bindLongVarChar(DataBind b, Object data) throws SQLException {
/* 355 */     String sd = (String)data;
/* 356 */     b.setClob(sd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void bindLongVarBinary(DataBind b, Object data) throws SQLException {
/* 365 */     byte[] bytes = (byte[])data;
/* 366 */     b.setBlob(bytes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void bindClob(DataBind b, Object data) throws SQLException {
/* 374 */     String sd = (String)data;
/* 375 */     b.setClob(sd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void bindBlob(DataBind b, Object data) throws SQLException {
/* 383 */     byte[] bytes = (byte[])data;
/* 384 */     b.setBlob(bytes);
/*     */   }
/*     */   
/*     */   private boolean isLob(int dbType) {
/* 388 */     switch (dbType) {
/*     */       case 2005:
/* 390 */         return true;
/*     */       case -1:
/* 392 */         return true;
/*     */       case 2004:
/* 394 */         return true;
/*     */       case -4:
/* 396 */         return true;
/*     */     } 
/*     */     
/* 399 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\Binder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */