package defPack;

public final class PoopyCrypt {

   private static final byte[] key = new byte[]{
       5, 7, 6, 5, 1, 4, 3, 1, 6, 4, 2, 0, 3, 5, 6, 5, 4, 1, 6, 0, 7, 2, 6, 1, 2, 7, 4, 1, 3, 5, 3, 5, 7, 1, 3, 7, 1, 2, 1, 7, 4, 1, 2, 3, 5, 7, 3, 4, 1, 5, 6, 4, 2, 1, 3, 4, 2, 7, 5, 7, 2, 5, 6, 1, 1, 7, 3, 7, 2, 7, 0, 3, 4, 3, 5, 3, 1, 7, 4, 1, 0, 4, 6, 5, 6, 5, 6, 0, 2, 3, 7, 6, 7, 3, 2, 3, 0, 3, 2, 4, 6, 3, 0, 1, 6, 5, 6, 5, 1, 5, 4, 1, 0, 7, 6, 3, 2, 1, 6, 4, 6, 2, 0, 4, 6, 5, 7, 4, 4, 0, 6, 5, 2, 3, 7, 2, 5, 0, 5, 3, 4, 5, 3, 6, 0, 2, 4, 6, 4, 3, 4, 0, 2, 0, 6, 1, 5, 6, 0, 5, 4, 1, 7, 4, 3, 0, 6, 4, 3, 7, 3, 7, 2, 7, 5, 4, 5, 2, 5, 1, 2, 1, 6, 2, 6, 3, 6, 0, 4, 1, 7, 2, 7, 0, 4, 6, 3, 4, 2, 3, 4, 5, 6, 4, 5, 3, 1, 2, 1, 0, 2, 1, 0, 1, 5, 5, 0, 4, 2, 7, 4, 2, 5, 1, 3, 6, 1, 2, 7, 6, 7, 3};


   public static final byte[] encode(byte[] byte_array) {
      for(int i = 0; i < byte_array.length; ++i) {
         byte_array[i] = in(byte_array[i], key[i % key.length]);
      }

      return byte_array;
   }

   public static final byte[] decode(byte[] byte_array) {
      for(int i = 0; i < byte_array.length; ++i) {
         byte_array[i] = out(byte_array[i], key[i % key.length]);
      }

      return byte_array;
   }

   private static byte in(byte byte0, int int1) {
      if(int1 == 0) {
         return byte0;
      } else {
         int int2 = byte0 & 255;
         int int3 = byte0 & 255;
         int2 <<= int1;
         int3 = (int3 << int1 & '\uff00') >> 8;
         return (byte)(int2 & 255 | int3 & 255);
      }
   }

   private static byte out(byte byte0, int int1) {
      return int1 == 0?byte0:in(byte0, 8 - int1);
   }
}
