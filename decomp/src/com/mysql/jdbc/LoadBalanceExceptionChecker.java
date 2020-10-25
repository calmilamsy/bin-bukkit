package com.mysql.jdbc;

import java.sql.SQLException;

public interface LoadBalanceExceptionChecker extends Extension {
  boolean shouldExceptionTriggerFailover(SQLException paramSQLException);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\LoadBalanceExceptionChecker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */