package defPack;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Vector;

public final class ToolsSongData {

   private static byte[] a = new byte[]{(byte)77, (byte)84, (byte)104, (byte)100, (byte)0, (byte)0, (byte)0, (byte)6, (byte)0, (byte)1};
   private static byte[] b = new byte[]{(byte)77, (byte)84, (byte)114, (byte)107};
   private static byte[] c = new byte[]{(byte)0, (byte)-1, (byte)81, (byte)3};
   private static byte[] d = new byte[]{(byte)0, (byte)-1, (byte)47, (byte)0};
   private Song localSong;


   public ToolsSongData(Song var1) {
      localSong = var1;
   }

   public final byte[] a(int int1, int int2, boolean bool3) throws IOException {
      ByteArrayOutputStream byteout_array1;
      (byteout_array1 = new ByteArrayOutputStream(6000)).write(a);
      byteout_array1.write(o.a(localSong.b() + 1, 1));
      byteout_array1.write(o.a(localSong.b() + 1, 0));
      byteout_array1.write(o.a(localSong.b * 96, 1));
      byteout_array1.write(o.a(localSong.b * 96, 0));
      byteout_array1.write(b);
      ByteArrayOutputStream byteout_array2;
      (byteout_array2 = new ByteArrayOutputStream()).write(c);
      byteout_array2.write(o.a(localSong.e(), 2));
      byteout_array2.write(o.a(localSong.e(), 1));
      byteout_array2.write(o.a(localSong.e(), 0));
      byteout_array2.write(d);
      byte[] byte_array1 = byteout_array2.toByteArray();
      byteout_array1.write(o.a(byte_array1.length, 3));
      byteout_array1.write(o.a(byte_array1.length, 2));
      byteout_array1.write(o.a(byte_array1.length, 1));
      byteout_array1.write(o.a(byte_array1.length, 0));
      byteout_array1.write(byte_array1);
      Channel[] var7 = localSong.a();

      for(int var8 = 0; var8 < var7.length; ++var8) {
         Vector var9 = new Vector();
         Channel var10 = var7[var8];
         byteout_array1.write(b);
         ByteArrayOutputStream var11 = new ByteArrayOutputStream(2000);
         if(var10.a != 9) {
            aj var12 = aj.a(var10.b);
            var11.write(0);
            var11.write(var12.b(var10.a));
            var11.write(var12.c);
         }

         var11.write(0);
         var11.write(176 + var10.a);
         var11.write(7);
         var11.write(var10.volume);
         int var28 = 1;
         if(bool3) {
            int var13 = (int2 - int1 + 1) / localSong.f();
            var28 = Math.max(40 / var13, 1);
         }

         byte[] var29 = (byte[])null;

         for(int var14 = 0; var14 < var28; ++var14) {
            if(var29 == null) {
               ByteArrayOutputStream var15 = new ByteArrayOutputStream(500);
               int var16 = int1;
               j[] var17 = var10.a();

               int var18;
               for(var18 = 0; var18 < var17.length; ++var18) {
                  int var19 = var17[var18].a * localSong.f();
                  aj[] var20 = var17[var18].b.a();

                  for(int var21 = 0; var21 < var20.length; ++var21) {
                     int var22;
                     if((var22 = var19 + var20[var21].a) >= int1 && var22 <= int2) {
                        int[] var24;
                        while(0 < var9.size() && (var24 = (int[])var9.elementAt(0))[0] <= var22) {
                           var15.write(o.a((var24[0] - var16) * 24));
                           var16 = var24[0];
                           var15.write(var24[1]);
                           var15.write(var24[2]);
                           var15.write(var24[3]);
                           var9.removeElement(var24);
                        }

                        var15.write(o.a((var22 - var16) * 24));
                        var16 = var22;
                        var15.write(var20[var21].b(var10.a));
                        var15.write(var20[var21].c);
                        if(var20[var21].a()) {
                           int[] var23;
                           (var23 = new int[4])[0] = var22 + var20[var21].b;
                           var23[1] = 128 + var10.a;
                           var23[2] = var20[var21].c[0] & 255;
                           var23[3] = var20[var21].c[1] & 255;
                           boolean var32 = false;

                           for(int var25 = 0; var25 < var9.size() && !var32; ++var25) {
                              int[] var26 = (int[])var9.elementAt(var25);
                              if(var23[0] < var26[0]) {
                                 var9.insertElementAt(var23, var25);
                                 var32 = true;
                              }
                           }

                           if(!var32) {
                              var9.addElement(var23);
                           }
                        }
                     }
                  }
               }

               for(var18 = 0; var18 < var9.size(); ++var18) {
                  int[] var31;
                  if((var31 = (int[])var9.elementAt(var18))[0] > int2 + 1) {
                     var31[0] = int2 + 1;
                  }

                  var15.write(o.a(Math.max(var31[0] - var16, 0) * 24));
                  var16 = var31[0];
                  var15.write(var31[1]);
                  var15.write(var31[2]);
                  var15.write(var31[3]);
               }

               if(var16 < int2 + 1) {
                  var15.write(o.a((int2 + 1 - var16) * 24));
                  var15.write(137);
                  var15.write(0);
                  var15.write(0);
               }

               var29 = var15.toByteArray();
            }

            var11.write(var29);
         }

         var11.write(d);
         byte[] var30 = var11.toByteArray();
         byteout_array1.write(o.a(var30.length, 3));
         byteout_array1.write(o.a(var30.length, 2));
         byteout_array1.write(o.a(var30.length, 1));
         byteout_array1.write(o.a(var30.length, 0));
         byteout_array1.write(var30);
      }

      byte[] var27 = byteout_array1.toByteArray();
      byteout_array1.close();
      return var27;
   }
}
