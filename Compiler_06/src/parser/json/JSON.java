/* Generated By:JavaCC: Do not edit this line. JSON.java */
package parser.json;

/**
 * A JSON parser (JavaCC generated - see JSON,jj).
 * <p/>
 * Parse a JSON input stream and call back to an implementation
 * of {@link Handler} at various parse events.
 * <p/>
 * The parser is unaware of the object graph being created,
 * concerning itself only with the identification of
 * JSON objects, arrays, members, elements and their values.
 * <p/>
 * It is biased towards a collections-based result, supporting
 * generic parameters for the JSON concepts of objects
 * and arrays, however these can be simply marker interfaces
 * if required - their implementation is entirely opaque
 * to the parser. The generic parameters at the class, rather
 * than the method level arises from JavaCC's limitation in
 * that regard.
 * <p/>
 * The parser discriminates values that are numeric, one of
 * the JSON literals true/false/null or strings, so that
 * the Handler implementation can act appropriately. 
 * <p/>
 * To use the parser, instantiate it using one of the
 * standard JavaCC-generated constructors and then
 * call <code>parse(Handler)</code>, <code>parseObject(Handler)</code>
 * or <code>parseArray(Handler)</code>.
 * <p/>
 * A number of deviations from the JSON standard are supported.
 * These are
 * <ol>
 * <li>Strings may be single-quoted instead of double-quoted.
 * An embedded single quote must be escaped but a double
 * quote need not be.</li>
 * <li>Object names need not be quoted at all, however in
 * this case they must be alpha-numeric, like identifiers
 * in Java or C.</li>
 * <li>Comments in either Java single or multi-line style
 * are supported. These are ignored by the parser.</li>
 * </ol>
 *
 * @param <O> The type for JSON objects
 * @param <A> The type for JSON arrays
 */
public class JSON<O, A> implements JSONConstants {
  /**
   * Parse the current input using the specified {@link Handler}.
   * Use this method if you know the JSON root is an object.
   */
  @SuppressWarnings("unchecked")
  public O parseObject(Handler<O, A> h) throws ParseException
  {
    // Unchecked cast
    return (O)start(h);
  }

  /**
   * Parse the current input using the specified {@link Handler}.
   * Use this method if you know the JSON root is an array.
   */
  @SuppressWarnings("unchecked")
  public A parseArray(Handler<O, A> h) throws ParseException
  {
    // Unchecked cast
    return (A)start(h);
  }

  /**
   * Parse the current input using the specified {@link Handler}.
   */
  public Object parse(Handler<O, A> h) throws ParseException
  {
    return start(h);
  }

  private String processStringEscapes(String s)
  {
    int pos, len;
    int cur = 0;

    char escape = '\u005c\u005c';

    if ((len = s.length()) == 0 || (pos = s.indexOf(escape)) < 0)
      return s;

    // Examine escape sequences and substitute for char equivalent
    StringBuffer buf = new StringBuffer(len);

    while (cur < len)
    {
      cur = doEscape(s, buf, cur, pos, len, escape);
      pos = s.indexOf(escape, cur);
    }

    return buf.toString();
  }

  private int doEscape(String       s,
                       StringBuffer buf,
                       int          cur,
                       int          pos,
                       int          len,
                       char         escape)
  {
    // if pos is non-negative it represents the escape character
    // at the start of an escape sequence.  Otherwise there
    // is no sequence left so copy remaining from cur

    int to = (pos < 0) ? len : pos;

    for (int i = cur; i < to; i++)
      buf.append(s.charAt(i));

    if (pos < 0)
      return len;

    // consume escape chars
    // step over escape char
    char echar = s.charAt(++to);

    switch(echar)
    {
      case 'n':
        buf.append(System.getProperties().get("line.separator")); to++;
        break;

      case 'r':  buf.append('\u005cr'); to++; break;
      case 't':  buf.append('\u005ct'); to++; break;
      case 'b':  buf.append('\u005cb'); to++; break;
      case 'f':  buf.append('\u005cf'); to++; break;
      case '\u005c\u005c': buf.append('\u005c\u005c'); to++; break;
      case '"':  buf.append('"');  to++; break;
      case '\u005c'': buf.append('\u005c''); to++; break;
      case '\u005cr': to++; if (to < len && s.charAt(to) == '\u005cn') to++; break;
      case '\u005cn': to++; break;
    }

    return to;
  }

  // Helper class to allow a boolean to pass up
  // through the grammar methods.
  static private class BoolRef
  {
    boolean value;
  }

  final public Object start(Handler<O,A> h) throws ParseException {
  Object result = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LBRACE:
      result = object(h, null, null, null, new BoolRef());
      break;
    case LBRACKET:
      result = array(h, null, null, null, new BoolRef());
      break;
    default:
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return result;}
    throw new Error("Missing return statement in function");
  }

  final public O object(Handler<O,A> h, String name, O parentObject, A parentArray, BoolRef bool) throws ParseException {
  O thisObject = null;
    jj_consume_token(LBRACE);
    thisObject = h.startObject(name, parentObject, parentArray);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case DBL_QUOTE_STRING:
    case SGL_QUOTE_STRING:
    case IDENTIFIER:
      thisObject = member(h, thisObject, bool);
      label_1:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COMMA:
          ;
          break;
        default:
          break label_1;
        }
        jj_consume_token(COMMA);
        thisObject = member(h, thisObject, bool);
      }
      break;
    default:
      ;
    }
    jj_consume_token(RBRACE);
    thisObject = h.endObject(name, thisObject, parentObject, parentArray);
    {if (true) return thisObject;}
    throw new Error("Missing return statement in function");
  }

  final public O member(Handler<O,A> h, O thisObject, BoolRef bool) throws ParseException {
  String  name    = null;
  String  value   = null;
  boolean curBool = bool.value;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case DBL_QUOTE_STRING:
      jj_consume_token(DBL_QUOTE_STRING);
                          name = processStringEscapes(token.image.substring(1, token.image.length()-1));
      break;
    case SGL_QUOTE_STRING:
      jj_consume_token(SGL_QUOTE_STRING);
                          name = processStringEscapes(token.image.substring(1, token.image.length()-1));
      break;
    case IDENTIFIER:
      jj_consume_token(IDENTIFIER);
                   name = token.image;
      break;
    default:
      jj_consume_token(-1);
      throw new ParseException();
    }
    name = h.name(name, thisObject);
    jj_consume_token(COLON);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TRUE:
    case FALSE:
    case NULL:
    case INTEGER:
    case FLOATING_POINT:
    case DBL_QUOTE_STRING:
    case SGL_QUOTE_STRING:
      value = value(bool);
        thisObject = h.valueToObject(name, value, thisObject, bool.value);
        bool.value = curBool;
        {if (true) return thisObject;}
      break;
    case LBRACE:
      object(h, name, thisObject, null, bool);
        bool.value = curBool;
        {if (true) return thisObject;}
      break;
    case LBRACKET:
      array(h, name, thisObject, null, bool);
        bool.value = curBool;
        {if (true) return thisObject;}
      break;
    default:
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public A array(Handler<O,A> h, String name, O parentObject, A parentArray, BoolRef bool) throws ParseException {
  A   thisArray = null;
  int count     = 0;
    jj_consume_token(LBRACKET);
    thisArray = h.startArray(name, parentObject, parentArray);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LBRACE:
    case LBRACKET:
    case TRUE:
    case FALSE:
    case NULL:
    case INTEGER:
    case FLOATING_POINT:
    case DBL_QUOTE_STRING:
    case SGL_QUOTE_STRING:
      thisArray = element(h, thisArray, count++, bool);
      label_2:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COMMA:
          ;
          break;
        default:
          break label_2;
        }
        jj_consume_token(COMMA);
        thisArray = element(h, thisArray, count++, bool);
      }
      break;
    default:
      ;
    }
    jj_consume_token(RBRACKET);
    thisArray = h.endArray(name, thisArray, parentObject, parentArray);
    {if (true) return thisArray;}
    throw new Error("Missing return statement in function");
  }

  final public A element(Handler<O,A> h, A thisArray, int count, BoolRef bool) throws ParseException {
  String  value    = null;
  boolean curBool  = bool.value;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TRUE:
    case FALSE:
    case NULL:
    case INTEGER:
    case FLOATING_POINT:
    case DBL_QUOTE_STRING:
    case SGL_QUOTE_STRING:
      value = value(bool);
    thisArray = h.valueToArray(value, thisArray, count, bool.value);
    bool.value = curBool;
    {if (true) return thisArray;}
      break;
    case LBRACE:
      object(h, null, null, thisArray, bool);
    bool.value = curBool;
    {if (true) return thisArray;}
      break;
    case LBRACKET:
      array(h, null, null, thisArray, bool);
    bool.value = curBool;
    {if (true) return thisArray;}
      break;
    default:
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public String value(BoolRef bool) throws ParseException {
  String  value     = null;
  boolean isNumeric = false;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INTEGER:
    case FLOATING_POINT:
    case DBL_QUOTE_STRING:
    case SGL_QUOTE_STRING:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case DBL_QUOTE_STRING:
        jj_consume_token(DBL_QUOTE_STRING);
                             value = processStringEscapes(token.image.substring(1, token.image.length()-1));
        break;
      case SGL_QUOTE_STRING:
        jj_consume_token(SGL_QUOTE_STRING);
                             value = processStringEscapes(token.image.substring(1, token.image.length()-1));
        break;
      case INTEGER:
        jj_consume_token(INTEGER);
                             value = token.image; isNumeric = true;
        break;
      case FLOATING_POINT:
        jj_consume_token(FLOATING_POINT);
                             value = token.image; isNumeric = true;
        break;
      default:
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    case TRUE:
    case FALSE:
    case NULL:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TRUE:
        jj_consume_token(TRUE);
                  value = Handler.JSON_TRUE;
        break;
      case FALSE:
        jj_consume_token(FALSE);
                  value = Handler.JSON_FALSE;
        break;
      case NULL:
        jj_consume_token(NULL);
                  value = Handler.JSON_NULL;
        break;
      default:
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    default:
      jj_consume_token(-1);
      throw new ParseException();
    }
    bool.value = isNumeric;
    {if (true) return value;}
    throw new Error("Missing return statement in function");
  }

  /** Generated Token Manager. */
  public JSONTokenManager token_source;
  JavaCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;

  /** Constructor with InputStream. */
  public JSON(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public JSON(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new JavaCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new JSONTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
  }

  /** Constructor. */
  public JSON(java.io.Reader stream) {
    jj_input_stream = new JavaCharStream(stream, 1, 1);
    token_source = new JSONTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
  }

  /** Constructor with generated Token Manager. */
  public JSON(JSONTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
  }

  /** Reinitialise. */
  public void ReInit(JSONTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      return token;
    }
    token = oldToken;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  /** Generate ParseException. */
  public ParseException generateParseException() {
    Token errortok = token.next;
    int line = errortok.beginLine, column = errortok.beginColumn;
    String mess = (errortok.kind == 0) ? tokenImage[0] : errortok.image;
    return new ParseException("Parse error at line " + line + ", column " + column + ".  Encountered: " + mess);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
