package org.json.simple;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JSONObject extends HashMap implements Map, JSONAware, JSONStreamAware {
  private static final long serialVersionUID = -503443796854799292L;
  
  public static void writeJSONString(Map paramMap, Writer paramWriter) throws IOException {
    if (paramMap == null) {
      paramWriter.write("null");
      return;
    } 
    boolean bool = true;
    Iterator iterator = paramMap.entrySet().iterator();
    paramWriter.write(123);
    while (iterator.hasNext()) {
      if (bool) {
        bool = false;
      } else {
        paramWriter.write(44);
      } 
      Map.Entry entry = (Map.Entry)iterator.next();
      paramWriter.write(34);
      paramWriter.write(escape(String.valueOf(entry.getKey())));
      paramWriter.write(34);
      paramWriter.write(58);
      JSONValue.writeJSONString(entry.getValue(), paramWriter);
    } 
    paramWriter.write(125);
  }
  
  public void writeJSONString(Writer paramWriter) throws IOException { writeJSONString(this, paramWriter); }
  
  public static String toJSONString(Map paramMap) {
    if (paramMap == null)
      return "null"; 
    StringBuffer stringBuffer = new StringBuffer();
    boolean bool = true;
    Iterator iterator = paramMap.entrySet().iterator();
    stringBuffer.append('{');
    while (iterator.hasNext()) {
      if (bool) {
        bool = false;
      } else {
        stringBuffer.append(',');
      } 
      Map.Entry entry = (Map.Entry)iterator.next();
      toJSONString(String.valueOf(entry.getKey()), entry.getValue(), stringBuffer);
    } 
    stringBuffer.append('}');
    return stringBuffer.toString();
  }
  
  public String toJSONString() { return toJSONString(this); }
  
  private static String toJSONString(String paramString, Object paramObject, StringBuffer paramStringBuffer) {
    paramStringBuffer.append('"');
    if (paramString == null) {
      paramStringBuffer.append("null");
    } else {
      JSONValue.escape(paramString, paramStringBuffer);
    } 
    paramStringBuffer.append('"').append(':');
    paramStringBuffer.append(JSONValue.toJSONString(paramObject));
    return paramStringBuffer.toString();
  }
  
  public String toString() { return toJSONString(); }
  
  public static String toString(String paramString, Object paramObject) {
    StringBuffer stringBuffer = new StringBuffer();
    toJSONString(paramString, paramObject, stringBuffer);
    return stringBuffer.toString();
  }
  
  public static String escape(String paramString) { return JSONValue.escape(paramString); }
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\json\simple\JSONObject.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.0.4
 */