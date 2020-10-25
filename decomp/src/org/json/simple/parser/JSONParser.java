package org.json.simple.parser;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONParser {
  public static final int S_INIT = 0;
  
  public static final int S_IN_FINISHED_VALUE = 1;
  
  public static final int S_IN_OBJECT = 2;
  
  public static final int S_IN_ARRAY = 3;
  
  public static final int S_PASSED_PAIR_KEY = 4;
  
  public static final int S_IN_PAIR_VALUE = 5;
  
  public static final int S_END = 6;
  
  public static final int S_IN_ERROR = -1;
  
  private LinkedList handlerStatusStack;
  
  private Yylex lexer = new Yylex((Reader)null);
  
  private Yytoken token = null;
  
  private int status = 0;
  
  private int peekStatus(LinkedList paramLinkedList) {
    if (paramLinkedList.size() == 0)
      return -1; 
    Integer integer = (Integer)paramLinkedList.getFirst();
    return integer.intValue();
  }
  
  public void reset() {
    this.token = null;
    this.status = 0;
    this.handlerStatusStack = null;
  }
  
  public void reset(Reader paramReader) {
    this.lexer.yyreset(paramReader);
    reset();
  }
  
  public int getPosition() { return this.lexer.getPosition(); }
  
  public Object parse(String paramString) throws ParseException { return parse(paramString, (ContainerFactory)null); }
  
  public Object parse(String paramString, ContainerFactory paramContainerFactory) throws ParseException {
    StringReader stringReader = new StringReader(paramString);
    try {
      return parse(stringReader, paramContainerFactory);
    } catch (IOException iOException) {
      throw new ParseException(-1, 2, iOException);
    } 
  }
  
  public Object parse(Reader paramReader) throws IOException, ParseException { return parse(paramReader, (ContainerFactory)null); }
  
  public Object parse(Reader paramReader, ContainerFactory paramContainerFactory) throws IOException, ParseException {
    reset(paramReader);
    LinkedList linkedList1 = new LinkedList();
    LinkedList linkedList2 = new LinkedList();
    try {
      do {
        Map map2;
        List list2;
        Map map1;
        String str;
        List list1;
        nextToken();
        switch (this.status) {
          case 0:
            switch (this.token.type) {
              case 0:
                this.status = 1;
                linkedList1.addFirst(new Integer(this.status));
                linkedList2.addFirst(this.token.value);
                break;
              case 1:
                this.status = 2;
                linkedList1.addFirst(new Integer(this.status));
                linkedList2.addFirst(createObjectContainer(paramContainerFactory));
                break;
              case 3:
                this.status = 3;
                linkedList1.addFirst(new Integer(this.status));
                linkedList2.addFirst(createArrayContainer(paramContainerFactory));
                break;
            } 
            this.status = -1;
            break;
          case 1:
            if (this.token.type == -1)
              return linkedList2.removeFirst(); 
            throw new ParseException(getPosition(), true, this.token);
          case 2:
            switch (this.token.type) {
              case 5:
                break;
              case 0:
                if (this.token.value instanceof String) {
                  String str1 = (String)this.token.value;
                  linkedList2.addFirst(str1);
                  this.status = 4;
                  linkedList1.addFirst(new Integer(this.status));
                  break;
                } 
                this.status = -1;
                break;
              case 2:
                if (linkedList2.size() > 1) {
                  linkedList1.removeFirst();
                  linkedList2.removeFirst();
                  this.status = peekStatus(linkedList1);
                  break;
                } 
                this.status = 1;
                break;
            } 
            this.status = -1;
            break;
          case 4:
            switch (this.token.type) {
              case 6:
                break;
              case 0:
                linkedList1.removeFirst();
                str = (String)linkedList2.removeFirst();
                map1 = (Map)linkedList2.getFirst();
                map1.put(str, this.token.value);
                this.status = peekStatus(linkedList1);
                break;
              case 3:
                linkedList1.removeFirst();
                str = (String)linkedList2.removeFirst();
                map1 = (Map)linkedList2.getFirst();
                list2 = createArrayContainer(paramContainerFactory);
                map1.put(str, list2);
                this.status = 3;
                linkedList1.addFirst(new Integer(this.status));
                linkedList2.addFirst(list2);
                break;
              case 1:
                linkedList1.removeFirst();
                str = (String)linkedList2.removeFirst();
                map1 = (Map)linkedList2.getFirst();
                map2 = createObjectContainer(paramContainerFactory);
                map1.put(str, map2);
                this.status = 2;
                linkedList1.addFirst(new Integer(this.status));
                linkedList2.addFirst(map2);
                break;
            } 
            this.status = -1;
            break;
          case 3:
            switch (this.token.type) {
              case 5:
                break;
              case 0:
                list1 = (List)linkedList2.getFirst();
                list1.add(this.token.value);
                break;
              case 4:
                if (linkedList2.size() > 1) {
                  linkedList1.removeFirst();
                  linkedList2.removeFirst();
                  this.status = peekStatus(linkedList1);
                  break;
                } 
                this.status = 1;
                break;
              case 1:
                list1 = (List)linkedList2.getFirst();
                map1 = createObjectContainer(paramContainerFactory);
                list1.add(map1);
                this.status = 2;
                linkedList1.addFirst(new Integer(this.status));
                linkedList2.addFirst(map1);
                break;
              case 3:
                list1 = (List)linkedList2.getFirst();
                list2 = createArrayContainer(paramContainerFactory);
                list1.add(list2);
                this.status = 3;
                linkedList1.addFirst(new Integer(this.status));
                linkedList2.addFirst(list2);
                break;
            } 
            this.status = -1;
            break;
          case -1:
            throw new ParseException(getPosition(), true, this.token);
        } 
        if (this.status == -1)
          throw new ParseException(getPosition(), true, this.token); 
      } while (this.token.type != -1);
    } catch (IOException iOException) {
      throw iOException;
    } 
    throw new ParseException(getPosition(), true, this.token);
  }
  
  private void nextToken() {
    this.token = this.lexer.yylex();
    if (this.token == null)
      this.token = new Yytoken(-1, null); 
  }
  
  private Map createObjectContainer(ContainerFactory paramContainerFactory) {
    if (paramContainerFactory == null)
      return new JSONObject(); 
    Map map = paramContainerFactory.createObjectContainer();
    return (map == null) ? new JSONObject() : map;
  }
  
  private List createArrayContainer(ContainerFactory paramContainerFactory) {
    if (paramContainerFactory == null)
      return new JSONArray(); 
    List list = paramContainerFactory.creatArrayContainer();
    return (list == null) ? new JSONArray() : list;
  }
  
  public void parse(String paramString, ContentHandler paramContentHandler) throws ParseException { parse(paramString, paramContentHandler, false); }
  
  public void parse(String paramString, ContentHandler paramContentHandler, boolean paramBoolean) throws ParseException {
    StringReader stringReader = new StringReader(paramString);
    try {
      parse(stringReader, paramContentHandler, paramBoolean);
    } catch (IOException iOException) {
      throw new ParseException(-1, 2, iOException);
    } 
  }
  
  public void parse(Reader paramReader, ContentHandler paramContentHandler) throws IOException, ParseException { parse(paramReader, paramContentHandler, false); }
  
  public void parse(Reader paramReader, ContentHandler paramContentHandler, boolean paramBoolean) throws IOException, ParseException {
    if (!paramBoolean) {
      reset(paramReader);
      this.handlerStatusStack = new LinkedList();
    } else if (this.handlerStatusStack == null) {
      paramBoolean = false;
      reset(paramReader);
      this.handlerStatusStack = new LinkedList();
    } 
    LinkedList linkedList = this.handlerStatusStack;
    try {
      do {
        switch (this.status) {
          case 0:
            paramContentHandler.startJSON();
            nextToken();
            switch (this.token.type) {
              case 0:
                this.status = 1;
                linkedList.addFirst(new Integer(this.status));
                if (!paramContentHandler.primitive(this.token.value))
                  return; 
                break;
              case 1:
                this.status = 2;
                linkedList.addFirst(new Integer(this.status));
                if (!paramContentHandler.startObject())
                  return; 
                break;
              case 3:
                this.status = 3;
                linkedList.addFirst(new Integer(this.status));
                if (!paramContentHandler.startArray())
                  return; 
                break;
            } 
            this.status = -1;
            break;
          case 1:
            nextToken();
            if (this.token.type == -1) {
              paramContentHandler.endJSON();
              this.status = 6;
              return;
            } 
            this.status = -1;
            throw new ParseException(getPosition(), true, this.token);
          case 2:
            nextToken();
            switch (this.token.type) {
              case 5:
                break;
              case 0:
                if (this.token.value instanceof String) {
                  String str = (String)this.token.value;
                  this.status = 4;
                  linkedList.addFirst(new Integer(this.status));
                  if (!paramContentHandler.startObjectEntry(str))
                    return; 
                  break;
                } 
                this.status = -1;
                break;
              case 2:
                if (linkedList.size() > 1) {
                  linkedList.removeFirst();
                  this.status = peekStatus(linkedList);
                } else {
                  this.status = 1;
                } 
                if (!paramContentHandler.endObject())
                  return; 
                break;
            } 
            this.status = -1;
            break;
          case 4:
            nextToken();
            switch (this.token.type) {
              case 6:
                break;
              case 0:
                linkedList.removeFirst();
                this.status = peekStatus(linkedList);
                if (!paramContentHandler.primitive(this.token.value))
                  return; 
                if (!paramContentHandler.endObjectEntry())
                  return; 
                break;
              case 3:
                linkedList.removeFirst();
                linkedList.addFirst(new Integer(5));
                this.status = 3;
                linkedList.addFirst(new Integer(this.status));
                if (!paramContentHandler.startArray())
                  return; 
                break;
              case 1:
                linkedList.removeFirst();
                linkedList.addFirst(new Integer(5));
                this.status = 2;
                linkedList.addFirst(new Integer(this.status));
                if (!paramContentHandler.startObject())
                  return; 
                break;
            } 
            this.status = -1;
            break;
          case 5:
            linkedList.removeFirst();
            this.status = peekStatus(linkedList);
            if (!paramContentHandler.endObjectEntry())
              return; 
            break;
          case 3:
            nextToken();
            switch (this.token.type) {
              case 5:
                break;
              case 0:
                if (!paramContentHandler.primitive(this.token.value))
                  return; 
                break;
              case 4:
                if (linkedList.size() > 1) {
                  linkedList.removeFirst();
                  this.status = peekStatus(linkedList);
                } else {
                  this.status = 1;
                } 
                if (!paramContentHandler.endArray())
                  return; 
                break;
              case 1:
                this.status = 2;
                linkedList.addFirst(new Integer(this.status));
                if (!paramContentHandler.startObject())
                  return; 
                break;
              case 3:
                this.status = 3;
                linkedList.addFirst(new Integer(this.status));
                if (!paramContentHandler.startArray())
                  return; 
                break;
            } 
            this.status = -1;
            break;
          case 6:
            return;
          case -1:
            throw new ParseException(getPosition(), true, this.token);
        } 
        if (this.status == -1)
          throw new ParseException(getPosition(), true, this.token); 
      } while (this.token.type != -1);
    } catch (IOException iOException) {
      this.status = -1;
      throw iOException;
    } catch (ParseException parseException) {
      this.status = -1;
      throw parseException;
    } catch (RuntimeException runtimeException) {
      this.status = -1;
      throw runtimeException;
    } catch (Error error) {
      this.status = -1;
      throw error;
    } 
    this.status = -1;
    throw new ParseException(getPosition(), true, this.token);
  }
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\json\simple\parser\JSONParser.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.0.4
 */