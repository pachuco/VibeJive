package defPack;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class _OnlineCatThingie implements n {

   public boolean a;
   public int b;
   public String c;
   public int d;


   public final byte[] e() throws Exception {
      ByteArrayOutputStream var1;
      (var1 = new ByteArrayOutputStream()).write(ArrayHandlerThingie.a((Object)(new Boolean(a))));
      var1.write(ArrayHandlerThingie.a((Object)(new Integer(b))));
      var1.write(ArrayHandlerThingie.a((Object)c));
      var1.write(ArrayHandlerThingie.a((Object)(new Integer(d))));
      return var1.toByteArray();
   }

   public final void a(DataInputStream var1) throws Exception {
      Object var2 = null;
      var2 = ArrayHandlerThingie.a(var1);
      a = ((Boolean)var2).booleanValue();
      var2 = ArrayHandlerThingie.a(var1);
      b = ((Integer)var2).intValue();
      var2 = ArrayHandlerThingie.a(var1);
      c = (String)var2;
      var2 = ArrayHandlerThingie.a(var1);
      d = ((Integer)var2).intValue();
   }
}
