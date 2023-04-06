package defPack;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Vector;

public final class Channel {

   private String e;
   public int a = 0;
   public int b = 0;
   public int volume = 100;
   public boolean d = false;
   private Vector f = new Vector();


   public Channel(String var1) {
      e = var1;
   }

   public Channel(DataInputStream var1, RiffTools[] var2, int var3) throws Exception {
      byte[] var5 = new byte[var1.readInt()];
      var1.readFully(var5);
      e = new String(var5);
      a = var1.readInt();
      b = var1.readInt();
      volume = var1.readInt();
      int var6 = var1.readInt();

      for(int i = 0; i < var6; ++i) {
         j var8 = new j(var1, var2, this);
         f.addElement(var8);
      }

   }

   public final j[] a() {
      j[] var1 = new j[f.size()];
      f.copyInto(var1);
      return var1;
   }

   public final int b() {
      return f.size();
   }

   public final void a(j var1) {
      for(int var2 = 0; var2 < f.size(); ++var2) {
         j var3 = (j)f.elementAt(var2);
         if(var1.a < var3.a) {
            f.insertElementAt(var1, var2);
            return;
         }
      }

      f.addElement(var1);
   }

   public final void b(j var1) {
      f.removeElement(var1);
   }

   public final void a(int var1) {
      if(var1 >= 0 && var1 <= 15) {
         a = var1;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public final void b(int var1) {
      if(var1 >= 0 && var1 <= 127) {
         b = var1;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public final void c(int var1) {
      if(var1 >= 0 && var1 <= 127) {
         volume = var1;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public final int d(int var1) {
      int var2 = Integer.MAX_VALUE;

      for(int var3 = 0; var3 < f.size(); ++var3) {
         j var4;
         if((var4 = (j)f.elementAt(var3)).a <= var1 && var4.a + var4.b.b > var1) {
            return 0;
         }

         if(var4.a >= var1) {
            var2 = var4.a;
            break;
         }
      }

      return var2 - var1;
   }

   public final void save(DataOutputStream var1) throws Exception {
      var1.writeInt(e.length());
      var1.write(e.getBytes());
      var1.writeInt(a);
      var1.writeInt(b);
      var1.writeInt(volume);
      int var2 = f.size();
      var1.writeInt(var2);

      for(int var3 = 0; var3 < var2; ++var3) {
         ((j)f.elementAt(var3)).a(var1);
      }

   }
}
