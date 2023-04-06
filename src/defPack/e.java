package defPack;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Vector;

public final class e implements Runnable {

   private Object b = new Object();
   private boolean c = false;
   private boolean d;
   private int e;
   private int f;
   private Song g;
   public ByteArrayOutputStream a = new ByteArrayOutputStream();
   private Vector h = new Vector();
   private boolean i = false;
   private int[] j;
   private int[] k;


   public e(boolean var1) {
      d = var1;
   }

   public final void a(Song var1) {
      g = var1;
      h.removeAllElements();
      j = new int[var1.b()];
      k = new int[var1.b()];
      f = -1;
      if(d) {
         c = false;
         (new Thread(this)).start();
      }

   }

   public final void a() {
      if(d) {
         Object var1 = b;
         synchronized(b) {
            c = true;
            b.notify();
         }
      }
   }

   public final void a(int var1) {
      e = var1;
      if(d) {
         Object var2 = b;
         synchronized(b) {
            b.notify();
         }
      } else {
         c();
      }
   }

   private void c() {
      try {
         a.reset();
         d();
         Channel[] var1 = g.a();

         for(int var2 = 0; var2 < var1.length; ++var2) {
            if(!var1[var2].d && var1[var2].b() > 0) {
               j[] var3 = var1[var2].a();
               boolean var4 = false;
               int var5 = 0;
               if(j[var2] < var3.length) {
                  var4 = (var5 = var3[j[var2]].a * g.f()) <= e && var5 + var3[j[var2]].b.b * g.f() > e;
               }

               if(!var4) {
                  k[var2] = 0;

                  for(int var6 = 0; var6 < var3.length && !var4; ++var6) {
                     if((var5 = var3[var6].a * g.f()) <= e && var5 + var3[var6].b.b * g.f() > e) {
                        var4 = true;
                        j[var2] = var6;
                     }
                  }
               } else if(f > e) {
                  k[var2] = 0;
               }

               if(var4) {
                  aj[] var10 = var3[j[var2]].b.a();
                  boolean var7 = false;

                  for(int var8 = k[var2]; var8 < var10.length && !var7; ++var8) {
                     if(var5 + var10[var8].a == e) {
                        a.write(var10[var8].b(var1[var2].a));
                        a.write(var10[var8].c);
                        if(var10[var8].a()) {
                           a(var10[var8], var1[var2].a);
                        }

                        ++k[var2];
                     } else if(var5 + var10[var8].a > e) {
                        var7 = true;
                     }
                  }
               }
            }
         }

         f = e;
      } catch (IOException var9) {
         ;
      }
   }

   public final void run() {
      try {
         Object var1 = b;
         synchronized(b) {
            while(!c) {
               i = false;
               b.wait();
               i = true;
               c();
            }

         }
      } catch (InterruptedException var3) {
         ;
      }
   }

   public final byte[] b() {
      return i?null:a.toByteArray();
   }

   private void a(aj var1, int var2) {
      int[] var3;
      (var3 = new int[4])[0] = 128 + var2;
      var3[1] = var1.c[0] & 255;
      var3[2] = var1.c[1] & 255;
      var3[3] = var1.b;
      h.addElement(var3);
   }

   private void d() {
      int var1 = h.size();

      for(int var2 = 0; var2 < var1; ++var2) {
         int[] var3;
         if(--(var3 = (int[])h.elementAt(var2))[3] == 0) {
            a.write(var3[0]);
            a.write(var3[1]);
            a.write(var3[2]);
            h.removeElementAt(var2);
            --var2;
            --var1;
         }
      }

   }
}
