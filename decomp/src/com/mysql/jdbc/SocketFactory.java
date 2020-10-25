package com.mysql.jdbc;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Properties;

public interface SocketFactory {
  Socket afterHandshake() throws SocketException, IOException;
  
  Socket beforeHandshake() throws SocketException, IOException;
  
  Socket connect(String paramString, int paramInt, Properties paramProperties) throws SocketException, IOException;
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\SocketFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */