/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ import javax.persistence.PersistenceException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnumToDbIntegerMap
/*    */   extends EnumToDbValueMap<Integer>
/*    */ {
/* 15 */   public int getDbType() { return 4; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumToDbIntegerMap add(Object beanValue, String stringDbValue) {
/*    */     try {
/* 22 */       Integer value = Integer.valueOf(stringDbValue);
/* 23 */       addInternal(beanValue, value);
/*    */       
/* 25 */       return this;
/*    */     }
/* 27 */     catch (Exception e) {
/* 28 */       String msg = "Error converted enum type[" + beanValue.getClass().getName();
/* 29 */       msg = msg + "] enum value[" + beanValue + "] string value [" + stringDbValue + "]";
/* 30 */       msg = msg + " to an Integer.";
/* 31 */       throw new PersistenceException(msg, e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void bind(DataBind b, Object value) throws SQLException {
/* 37 */     if (value == null) {
/* 38 */       b.setNull(4);
/*    */     } else {
/* 40 */       Integer s = (Integer)getDbValue(value);
/* 41 */       b.setInt(s.intValue());
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object read(DataReader dataReader) throws SQLException {
/* 48 */     Integer i = dataReader.getInt();
/* 49 */     if (i == null) {
/* 50 */       return null;
/*    */     }
/* 52 */     return getBeanValue(i);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\EnumToDbIntegerMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */