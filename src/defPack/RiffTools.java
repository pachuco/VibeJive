package defPack;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Vector;

public final class RiffTools {

   private static long d = System.currentTimeMillis();
   public String a;
   public int b = 1;
   public long c;
   private Vector e = new Vector();


   public static RiffTools a(RiffTools var0) {
      String var1;
      if((var1 = "Copy of " + var0.a).length() > 20) {
         var1 = var1.substring(0, 21);
      }

      RiffTools var2;
      (var2 = new RiffTools(var1)).b = var0.b;

      for(int var3 = 0; var3 < var0.e.size(); ++var3) {
         aj var4 = (aj)var0.e.elementAt(var3);
         var2.e.addElement(aj.a(var4));
      }

      return var2;
   }

   public RiffTools(String var1) {
      a = var1;
      c = (long)(d++);
   }

   public RiffTools(DataInputStream var1) throws Exception {
      byte[] var3 = new byte[var1.readInt()];
      var1.readFully(var3);
      a = new String(var3);
      b = var1.readInt();
      c = var1.readLong();
      int var4 = var1.readInt();

      for(int var5 = 0; var5 < var4; ++var5) {
         aj var6 = new aj(var1);
         e.addElement(var6);
      }

   }

   private boolean funTransp(int var1) {
      boolean var10000 = false;

      while(true) {
         boolean var2 = var10000;

         for(int var3 = 0; var3 < e.size(); ++var3) {
            aj var4;
            if((var4 = (aj)e.elementAt(var3)).a()) {
               int var5;
               if((var5 = (var4.c[0] & 255) + var1) < 0 || var5 > 127) {
                  return false;
               }

               if(var2) {
                  var4.c[0] = (byte)var5;
               }
            }
         }

         if(var2) {
            return true;
         }

         var10000 = true;
      }
   }

   public final aj[] a() {
      aj[] var1 = new aj[e.size()];
      e.copyInto(var1);
      return var1;
   }

   public final aj a(int var1) {
      return (aj)e.elementAt(var1);
   }

   public final void a(aj var1) {
      e.removeElement(var1);
   }

   public final void b(aj var1) {
      for(int var2 = 0; var2 < e.size(); ++var2) {
         if(((aj)e.elementAt(var2)).a >= var1.a) {
            e.insertElementAt(var1, var2);
            return;
         }
      }

      e.addElement(var1);
   }

   public final void b(int var1) {
      if(var1 < 1) {
         throw new IllegalArgumentException();
      } else {
         b = var1;
      }
   }

   public final int b() {
      return e.size();
   }

   public final int c(aj var1) {
      int var2 = var1.a;
      byte var3 = var1.c[0];

      for(int var4 = 0; var4 < e.size(); ++var4) {
         aj var5;
         if((var5 = (aj)e.elementAt(var4)).a > var2 && var5.a() && var5.c[0] == var3) {
            return var5.a - var2;
         }
      }

      return -1;
   }

   public final void a(DataOutputStream var1) throws Exception {
      var1.writeInt(a.length());
      var1.write(a.getBytes());
      var1.writeInt(b);
      var1.writeLong(c);
      int var2 = e.size();
      var1.writeInt(var2);

      for(int var3 = 0; var3 < var2; ++var3) {
         ((aj)e.elementAt(var3)).a(var1);
      }

   }

   public final boolean funTranspAccess(int inTr) {
      return funTransp(inTr);
   }
}
