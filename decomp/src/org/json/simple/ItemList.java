package org.json.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ItemList {
  private String sp = ",";
  
  List items = new ArrayList();
  
  public ItemList() {}
  
  public ItemList(String paramString) { split(paramString, this.sp, this.items); }
  
  public ItemList(String paramString1, String paramString2) {
    this.sp = paramString1;
    split(paramString1, paramString2, this.items);
  }
  
  public ItemList(String paramString1, String paramString2, boolean paramBoolean) { split(paramString1, paramString2, this.items, paramBoolean); }
  
  public List getItems() { return this.items; }
  
  public String[] getArray() { return (String[])this.items.toArray(); }
  
  public void split(String paramString1, String paramString2, List paramList, boolean paramBoolean) {
    if (paramString1 == null || paramString2 == null)
      return; 
    if (paramBoolean) {
      StringTokenizer stringTokenizer = new StringTokenizer(paramString1, paramString2);
      while (stringTokenizer.hasMoreTokens())
        paramList.add(stringTokenizer.nextToken().trim()); 
    } else {
      split(paramString1, paramString2, paramList);
    } 
  }
  
  public void split(String paramString1, String paramString2, List paramList) {
    if (paramString1 == null || paramString2 == null)
      return; 
    int i = 0;
    byte b = 0;
    do {
      b = i;
      i = paramString1.indexOf(paramString2, i);
      if (i == -1)
        break; 
      paramList.add(paramString1.substring(b, i).trim());
      i += paramString2.length();
    } while (i != -1);
    paramList.add(paramString1.substring(b).trim());
  }
  
  public void setSP(String paramString) { this.sp = paramString; }
  
  public void add(int paramInt, String paramString) {
    if (paramString == null)
      return; 
    this.items.add(paramInt, paramString.trim());
  }
  
  public void add(String paramString) {
    if (paramString == null)
      return; 
    this.items.add(paramString.trim());
  }
  
  public void addAll(ItemList paramItemList) { this.items.addAll(paramItemList.items); }
  
  public void addAll(String paramString) { split(paramString, this.sp, this.items); }
  
  public void addAll(String paramString1, String paramString2) { split(paramString1, paramString2, this.items); }
  
  public void addAll(String paramString1, String paramString2, boolean paramBoolean) { split(paramString1, paramString2, this.items, paramBoolean); }
  
  public String get(int paramInt) { return (String)this.items.get(paramInt); }
  
  public int size() { return this.items.size(); }
  
  public String toString() { return toString(this.sp); }
  
  public String toString(String paramString) {
    StringBuffer stringBuffer = new StringBuffer();
    for (byte b = 0; b < this.items.size(); b++) {
      if (!b) {
        stringBuffer.append(this.items.get(b));
      } else {
        stringBuffer.append(paramString);
        stringBuffer.append(this.items.get(b));
      } 
    } 
    return stringBuffer.toString();
  }
  
  public void clear() { this.items.clear(); }
  
  public void reset() {
    this.sp = ",";
    this.items.clear();
  }
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\json\simple\ItemList.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.0.4
 */