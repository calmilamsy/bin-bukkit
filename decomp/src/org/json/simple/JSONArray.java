package org.json.simple;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JSONArray extends ArrayList implements List, JSONAware, JSONStreamAware {
  private static final long serialVersionUID = 3957988303675231981L;
  
  public static void writeJSONString(List paramList, Writer paramWriter) throws IOException {
    if (paramList == null) {
      paramWriter.write("null");
      return;
    } 
    boolean bool = true;
    Iterator iterator = paramList.iterator();
    paramWriter.write(91);
    while (iterator.hasNext()) {
      if (bool) {
        bool = false;
      } else {
        paramWriter.write(44);
      } 
      Object object = iterator.next();
      if (object == null) {
        paramWriter.write("null");
        continue;
      } 
      JSONValue.writeJSONString(object, paramWriter);
    } 
    paramWriter.write(93);
  }
  
  public void writeJSONString(Writer paramWriter) throws IOException { writeJSONString(this, paramWriter); }
  
  public static String toJSONString(List paramList) {
    if (paramList == null)
      return "null"; 
    boolean bool = true;
    StringBuffer stringBuffer = new StringBuffer();
    Iterator iterator = paramList.iterator();
    stringBuffer.append('[');
    while (iterator.hasNext()) {
      if (bool) {
        bool = false;
      } else {
        stringBuffer.append(',');
      } 
      Object object = iterator.next();
      if (object == null) {
        stringBuffer.append("null");
        continue;
      } 
      stringBuffer.append(JSONValue.toJSONString(object));
    } 
    stringBuffer.append(']');
    return stringBuffer.toString();
  }
  
  public String toJSONString() { return toJSONString(this); }
  
  public String toString() { return toJSONString(); }
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\json\simple\JSONArray.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.0.4
 */