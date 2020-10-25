/*    */ package com.mysql.jdbc;
/*    */ 
/*    */ import java.sql.RowIdLifetime;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JDBC4DatabaseMetaDataUsingInfoSchema
/*    */   extends DatabaseMetaDataUsingInfoSchema
/*    */ {
/* 37 */   public JDBC4DatabaseMetaDataUsingInfoSchema(MySQLConnection connToSet, String databaseToSet) throws SQLException { super(connToSet, databaseToSet); }
/*    */ 
/*    */ 
/*    */   
/* 41 */   public RowIdLifetime getRowIdLifetime() throws SQLException { return RowIdLifetime.ROWID_UNSUPPORTED; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 62 */   public boolean isWrapperFor(Class<?> iface) throws SQLException { return iface.isInstance(this); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public <T> T unwrap(Class<T> iface) throws SQLException {
/*    */     try {
/* 83 */       return (T)iface.cast(this);
/* 84 */     } catch (ClassCastException cce) {
/* 85 */       throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", this.conn.getExceptionInterceptor());
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 91 */   protected int getJDBC4FunctionNoTableConstant() { return 1; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\JDBC4DatabaseMetaDataUsingInfoSchema.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.4
 */