package com.mysql.jdbc;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface BalanceStrategy extends Extension {
  ConnectionImpl pickConnection(LoadBalancingConnectionProxy paramLoadBalancingConnectionProxy, List<String> paramList, Map<String, ConnectionImpl> paramMap, long[] paramArrayOfLong, int paramInt) throws SQLException;
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\BalanceStrategy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */