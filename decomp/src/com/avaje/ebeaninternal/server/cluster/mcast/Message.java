package com.avaje.ebeaninternal.server.cluster.mcast;

import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;
import java.io.IOException;

public interface Message {
  void writeBinaryMessage(BinaryMessageList paramBinaryMessageList) throws IOException;
  
  boolean isControlMessage();
  
  String getToHostPort();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cluster\mcast\Message.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */