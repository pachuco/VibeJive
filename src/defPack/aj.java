package defPack;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public final class aj {

   private static final byte[] d = new byte[0];
   public int a;
   public int b;
   private int e;
   public byte[] c;


   public static aj a(int var0, int var1, int var2) {
      byte[] var3;
      (var3 = new byte[2])[0] = (byte)var0;
      var3[1] = (byte)var1;
      return new aj(144, var3, var2);
   }

   public static aj a(int var0) {
      byte[] var1;
      (var1 = new byte[1])[0] = (byte)var0;
      return new aj(192, var1);
   }

   public static aj a(aj var0) {
      byte[] var1;
      if(var0.c.length == 0) {
         var1 = var0.c;
      } else {
         var1 = new byte[var0.c.length];
         System.arraycopy(var0.c, 0, var1, 0, var0.c.length);
      }

      aj var2;
      (var2 = new aj(var0.e, var1, var0.b)).a = var0.a;
      return var2;
   }

   private aj(int var1, byte[] var2) {
      this(var1, var2, 0);
   }

   private aj(int var1, byte[] var2, int var3) {
      c = d;
      e = var1 & 240;
      a(var2);
      b = var3;
   }

   public final int b(int var1) {
      return e + var1;
   }

   private void a(byte[] var1) {
      byte[] var10001;
      aj var10000;
      if(var1 == null) {
         var10000 = this;
         var10001 = d;
      } else {
         var10000 = this;
         var10001 = var1;
      }

      var10000.c = var10001;
   }

   public final boolean a() {
      return e == 144;
   }

   public final void a(DataOutputStream var1) throws Exception {
      var1.writeInt(a);
      var1.writeInt(e);
      var1.writeInt(b);
      var1.writeInt(c.length);
      var1.write(c);
   }

   public aj(DataInputStream var1) throws Exception {
      c = d;
      a = var1.readInt();
      e = var1.readInt();
      b = var1.readInt();
      int var2 = var1.readInt();
      c = new byte[var2];
      var1.readFully(c);
   }
}
