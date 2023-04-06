package defPack;

public final class o {

   public static byte a(int var0, int var1) {
      if(var1 >= 0 && var1 <= 3) {
         return var1 == 3?(byte)(var0 / 16777216):(var1 == 2?(byte)((var0 %= 16777216) / 65536):(var1 == 1?(byte)((var0 %= 65536) / 256):(byte)(var0 % 256)));
      } else {
         throw new IllegalArgumentException();
      }
   }

   public static byte[] a(int var0) {
      byte[] var1 = new byte[4];
      int var2 = 0;
      byte[] var10000 = var1;
      int var10001 = 0;
      int var10002 = var0 & 127;

      while(true) {
         var10000[var10001] = (byte)var10002;
         if((var0 >>= 7) == 0) {
            byte[] var3 = new byte[var2 + 1];

            for(int var4 = 0; var2 != -1; var3[var4++] = var1[var2--]) {
               ;
            }

            return var3;
         }

         ++var2;
         var10000 = var1;
         var10001 = var2;
         var10002 = var0 & 127 | 128;
      }
   }
}
