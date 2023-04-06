package defPack;
import java.io.ByteArrayOutputStream;
import java.util.Vector;

public final class OnlineArray2Http {

   private int a = -1853867513;


   public final String a(String var1, int var2) throws Exception {
      ByteArrayOutputStream var4;
      (var4 = new ByteArrayOutputStream()).write(ArrayHandlerThingie.a((Object)var1));
      var4.write(ArrayHandlerThingie.a((Object)(new Integer(var2))));
      return (String)OnlineConnection.doStuff(a, 0, var4.toByteArray());
   }

   public final String a(int var1, int var2) throws Exception {
      ByteArrayOutputStream var4;
      (var4 = new ByteArrayOutputStream()).write(ArrayHandlerThingie.a((Object)(new Integer(var1))));
      var4.write(ArrayHandlerThingie.a((Object)(new Integer(var2))));
      return (String)OnlineConnection.doStuff(a, 1, var4.toByteArray());
   }

   public final long a(int var1) throws Exception {
      ByteArrayOutputStream var3;
      (var3 = new ByteArrayOutputStream()).write(ArrayHandlerThingie.a((Object)(new Integer(var1))));
      return ((Long)OnlineConnection.doStuff(a, 2, var3.toByteArray())).longValue();
   }

   public final byte[] a(int var1, int var2, int var3, int var4) throws Exception {
      ByteArrayOutputStream var6;
      (var6 = new ByteArrayOutputStream()).write(ArrayHandlerThingie.a((Object)(new Integer(var1))));
      var6.write(ArrayHandlerThingie.a((Object)(new Integer(var2))));
      var6.write(ArrayHandlerThingie.a((Object)(new Integer(var3))));
      var6.write(ArrayHandlerThingie.a((Object)(new Integer(var4))));
      return (byte[])((byte[])OnlineConnection.doStuff(a, 3, var6.toByteArray()));
   }

   public final Vector b(int var1) throws Exception {
      ByteArrayOutputStream var3;
      (var3 = new ByteArrayOutputStream()).write(ArrayHandlerThingie.a((Object)(new Integer(var1))));
      return (Vector)OnlineConnection.doStuff(a, 4, var3.toByteArray());
   }

   public final Vector b(int var1, int var2) throws Exception {
      ByteArrayOutputStream var4;
      (var4 = new ByteArrayOutputStream()).write(ArrayHandlerThingie.a((Object)(new Integer(var1))));
      var4.write(ArrayHandlerThingie.a((Object)(new Integer(var2))));
      return (Vector)OnlineConnection.doStuff(a, 5, var4.toByteArray());
   }

   public final Vector a(int var1, int var2, int var3) throws Exception {
      ByteArrayOutputStream var5;
      (var5 = new ByteArrayOutputStream()).write(ArrayHandlerThingie.a((Object)(new Integer(var1))));
      var5.write(ArrayHandlerThingie.a((Object)(new Integer(var2))));
      var5.write(ArrayHandlerThingie.a((Object)(new Integer(var3))));
      return (Vector)OnlineConnection.doStuff(a, 6, var5.toByteArray());
   }

   public final Vector a(int var1, int var2, int var3, int var4, int var5) throws Exception {
      ByteArrayOutputStream var7;
      (var7 = new ByteArrayOutputStream()).write(ArrayHandlerThingie.a((Object)(new Integer(var1))));
      var7.write(ArrayHandlerThingie.a((Object)(new Integer(var2))));
      var7.write(ArrayHandlerThingie.a((Object)(new Integer(var3))));
      var7.write(ArrayHandlerThingie.a((Object)(new Integer(var4))));
      var7.write(ArrayHandlerThingie.a((Object)(new Integer(var5))));
      return (Vector)OnlineConnection.doStuff(a, 7, var7.toByteArray());
   }

   public final int login(String varUser, String varPass) throws Exception {
      ByteArrayOutputStream var4;
      (var4 = new ByteArrayOutputStream()).write(ArrayHandlerThingie.a((Object)varUser));
      var4.write(ArrayHandlerThingie.a((Object)varPass));
      return ((Integer)OnlineConnection.doStuff(a, 8, var4.toByteArray())).intValue();
   }

   public final int register(String varUser, String varPass) throws Exception {
      ByteArrayOutputStream var4;
      (var4 = new ByteArrayOutputStream()).write(ArrayHandlerThingie.a((Object)varUser));
      var4.write(ArrayHandlerThingie.a((Object)varPass));
      return ((Integer)OnlineConnection.doStuff(a, 9, var4.toByteArray())).intValue();
   }

   public final String b(String var1, int var2) throws Exception {
      ByteArrayOutputStream var4;
      (var4 = new ByteArrayOutputStream()).write(ArrayHandlerThingie.a((Object)var1));
      var4.write(ArrayHandlerThingie.a((Object)(new Integer(var2))));
      return (String)OnlineConnection.doStuff(a, 10, var4.toByteArray());
   }

   public final void a(int var1, int var2, String var3, byte[] var4, boolean var5, int var6) throws Exception {
      ByteArrayOutputStream var8;
      (var8 = new ByteArrayOutputStream()).write(ArrayHandlerThingie.a((Object)(new Integer(var1))));
      var8.write(ArrayHandlerThingie.a((Object)(new Integer(var2))));
      var8.write(ArrayHandlerThingie.a((Object)var3));
      var8.write(ArrayHandlerThingie.a((Object)var4));
      var8.write(ArrayHandlerThingie.a((Object)(new Boolean(var5))));
      var8.write(ArrayHandlerThingie.a((Object)(new Integer(var6))));
      OnlineConnection.doStuff(a, 11, var8.toByteArray());
   }

   public final Vector c(int var1) throws Exception {
      ByteArrayOutputStream var3;
      (var3 = new ByteArrayOutputStream()).write(ArrayHandlerThingie.a((Object)(new Integer(var1))));
      return (Vector)OnlineConnection.doStuff(a, 13, var3.toByteArray());
   }
}
