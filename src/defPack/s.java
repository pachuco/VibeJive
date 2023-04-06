package defPack;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class s implements n {

   public int a;
   public String b;
   public int c;
   public String d;


   public final byte[] e() throws Exception {
      ByteArrayOutputStream var1;
      (var1 = new ByteArrayOutputStream()).write(ArrayHandlerThingie.a((Object)(new Integer(a))));
      var1.write(ArrayHandlerThingie.a((Object)b));
      var1.write(ArrayHandlerThingie.a((Object)(new Integer(c))));
      var1.write(ArrayHandlerThingie.a((Object)d));
      return var1.toByteArray();
   }

   public final void a(DataInputStream var1) throws Exception {
      Object var2 = null;
      var2 = ArrayHandlerThingie.a(var1);
      a = ((Integer)var2).intValue();
      var2 = ArrayHandlerThingie.a(var1);
      b = (String)var2;
      var2 = ArrayHandlerThingie.a(var1);
      c = ((Integer)var2).intValue();
      var2 = ArrayHandlerThingie.a(var1);
      d = (String)var2;
   }
}
