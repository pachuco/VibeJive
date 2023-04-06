package defPack;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Vector;

public final class Song {

   public String songName;
   public int b = 1;
   public int tempo = 120;
   public int d = 0;
   public int e = 0;
   public int f = 3;
   private Vector g = new Vector();
   private Vector h = new Vector();


   public Song(String var1) {
      songName = var1;
   }

   public Song(DataInputStream var1) throws Exception {
      int var2 = var1.readInt();
      byte[] var4 = new byte[var1.readInt()];
      var1.readFully(var4);
      songName = new String(var4);
      b = var1.readInt();
      tempo = var1.readInt();
      e = var1.readInt();
      f = var1.readInt();
      int var5 = var1.readInt();

      for(int var6 = 0; var6 < var5; ++var6) {
         RiffTools var7 = new RiffTools(var1);
         h.addElement(var7);
      }

      RiffTools[] var10 = c();
      int var11 = var1.readInt();

      for(int var8 = 0; var8 < var11; ++var8) {
         Channel var9 = new Channel(var1, var10, var2);
         g.addElement(var9);
      }

   }

   public final Channel[] a() {
      Channel[] var1 = new Channel[g.size()];
      g.copyInto(var1);
      return var1;
   }

   public final Channel a(int var1) {
      return (Channel)g.elementAt(var1);
   }

   public final int b() {
      return g.size();
   }

   public final int a(Channel var1) {
      return g.indexOf(var1);
   }

   public final void b(Channel var1) {
      g.addElement(var1);
   }

   public final void c(Channel var1) {
      g.removeElement(var1);
   }

   public final void a(RiffTools var1) {
      h.addElement(var1);
   }

   public final RiffTools[] c() {
      RiffTools[] var1 = new RiffTools[h.size()];
      h.copyInto(var1);
      return var1;
   }

   public final int number_of_riffs() {
      return h.size();
   }

   public final int e() {
      return 60000000 / tempo;
   }

   public final int b(int var1) {
      return var1 * ('\uea60' / tempo) / (b * 4);
   }

   public final int c(int var1) {
      return var1 * 4 / ('\uea60' / tempo / b);
   }

   public final int f() {
      return b * 16;
   }

   public final void setTempo(int var1) {
      if(var1 >= 1 && var1 <= 500) {
         tempo = var1;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public final void a(DataOutputStream var1) throws Exception {
      var1.writeInt(1);
      var1.writeInt(songName.length());
      var1.write(songName.getBytes());
      var1.writeInt(b);
      var1.writeInt(tempo);
      var1.writeInt(e);
      var1.writeInt(f);
      int var2 = h.size();
      var1.writeInt(var2);

      int var3;
      for(var3 = 0; var3 < var2; ++var3) {
         ((RiffTools)h.elementAt(var3)).a(var1);
      }

      var3 = g.size();
      var1.writeInt(var3);

      for(int var4 = 0; var4 < var3; ++var4) {
         ((Channel)g.elementAt(var4)).save(var1);
      }

   }

   public static String a(DataInputStream var0) throws Exception {
      var0.readInt();
      byte[] var3 = new byte[var0.readInt()];
      var0.readFully(var3);
      return new String(var3);
   }

   public final void e(int var1) {
      f = var1;
      if(e > var1) {
         f = e;
      }

   }

   public final void f(int var1) {
      e = var1;
      if(var1 > f) {
         f = var1;
      }

   }

   public final int a(boolean var1) {
      boolean[] var2 = new boolean[16];

      int var3;
      for(var3 = 0; var3 < g.size(); ++var3) {
         Channel var4 = (Channel)g.elementAt(var3);
         var2[var4.a] = true;
      }

      if(var1) {
         return var2[9]?-1:9;
      } else {
         for(var3 = 0; var3 < 16; ++var3) {
            if(!var2[var3] && var3 != 9) {
               return var3;
            }
         }

         return -1;
      }
   }

   public final int g() {
      int var1 = 0;
      int var2 = f();

      for(int var3 = 0; var3 < g.size(); ++var3) {
         j[] var5 = ((Channel)g.elementAt(var3)).a();

         for(int var6 = 0; var6 < var5.length; ++var6) {
            int var7;
            if((var7 = (var5[var6].a + var5[var6].b.b) * var2) > var1) {
               var1 = var7;
            }
         }
      }

      return var1;
   }
}
