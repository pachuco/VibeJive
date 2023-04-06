package defPack;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public final class ArrayHandlerThingie {

   public static final byte[] a(Object var0) throws Exception {
      if(var0 == null) {
         return new byte[]{(byte)76, (byte)1, (byte)1};
      } else {
         Class var1 = var0.getClass();
         ByteArrayOutputStream var2 = new ByteArrayOutputStream();
         DataOutputStream var3 = new DataOutputStream(var2);
         if(var1.isArray()) {
            System.out.println("Array " + var1);
            a(var0, var3);
         } else if(var0 instanceof Byte) {
            var3.write(new byte[]{(byte)76, (byte)98});
            var3.writeByte(0);
            var3.writeByte(((Byte)var0).byteValue());
         } else if(var0 instanceof Character) {
            var3.write(new byte[]{(byte)76, (byte)99});
            var3.writeByte(0);
            var3.writeChar(((Character)var0).charValue());
         } else if(var0 instanceof Short) {
            var3.write(new byte[]{(byte)76, (byte)115});
            var3.writeByte(0);
            var3.writeShort(((Short)var0).shortValue());
         } else if(var0 instanceof Integer) {
            var3.write(new byte[]{(byte)76, (byte)105});
            var3.writeByte(0);
            var3.writeInt(((Integer)var0).intValue());
         } else if(var0 instanceof Long) {
            var3.write(new byte[]{(byte)76, (byte)108});
            var3.writeByte(0);
            var3.writeLong(((Long)var0).longValue());
         } else if(var0 instanceof Boolean) {
            var3.write(new byte[]{(byte)76, (byte)122});
            var3.writeByte(0);
            var3.writeBoolean(((Boolean)var0).booleanValue());
         } else {
            String var4;
            if(var0 instanceof String) {
               var3.write(new byte[]{(byte)88, (byte)88});
               var3.writeByte(0);
               var4 = (String)var0;
               var3.writeChar(var4.length());
               var3.write(var4.getBytes());
            } else if(var0 instanceof Date) {
               var3.write(new byte[]{(byte)88, (byte)89});
               var3.writeByte(0);
               var3.writeLong(((Date)var0).getTime());
            } else if(var0 instanceof Calendar) {
               var3.write(new byte[]{(byte)88, (byte)84});
               var3.writeByte(0);
               var3.writeLong(((Calendar)var0).getTime().getTime());
            } else {
               int var5;
               if(var0 instanceof Vector) {
                  var3.write(new byte[]{(byte)88, (byte)65});
                  var3.writeByte(0);
                  Vector var9;
                  var5 = (var9 = (Vector)var0).size();
                  var3.writeChar(var5);

                  for(int var6 = 0; var6 < var5; ++var6) {
                     Object var7;
                     if((var7 = var9.elementAt(var6)) == null) {
                        var7 = "";
                     }

                     byte[] var8 = a(var7);
                     var3.write(var8);
                  }
               } else {
                  if(!(var0 instanceof n)) {
                     throw new Exception("Unsupported type " + var1);
                  }

                  var3.write(new byte[]{(byte)85, (byte)85, (byte)0});
                  var5 = (var4 = var0.getClass().getName()).length();
                  var3.writeChar(var5);
                  var3.write(var4.getBytes());
                  var3.write(((n)var0).e());
               }
            }
         }

         var3.flush();
         var3.close();
         return var2.toByteArray();
      }
   }

   private static void a(Object var0, DataOutputStream var1) throws Exception {
      int var2;
      if((var2 = b(var0)) > 2) {
         throw new Exception("Array has too many dimensions");
      } else {
         String var3 = b(var0.getClass());
         var1.write(91);
         if(var3.length() == 1) {
            var1.write(79);
            var1.write(0);
            var1.write((byte)var3.charAt(0));
            var1.writeInt(var2);
            int var5;
            int var6;
            switch(var3.charAt(0)) {
            case 98:
               if(var2 == 1) {
                  byte[] var17 = (byte[])((byte[])var0);
                  var1.writeChar(var17.length);

                  for(var5 = 0; var5 < var17.length; ++var5) {
                     var1.writeByte(var17[var5]);
                  }

                  return;
               }

               byte[][] var15 = (byte[][])((byte[][])var0);
               var1.writeChar(var15.length);
               var1.writeChar(var15[0].length);

               for(var5 = 0; var5 < var15.length; ++var5) {
                  for(var6 = 0; var6 < var15[0].length; ++var6) {
                     var1.write(var15[var5][var6]);
                  }
               }

               return;
            case 99:
               if(var2 == 1) {
                  char[] var16 = (char[])((char[])var0);
                  var1.writeChar(var16.length);

                  for(var5 = 0; var5 < var16.length; ++var5) {
                     var1.writeChar(var16[var5]);
                  }

                  return;
               }

               char[][] var13 = (char[][])((char[][])var0);
               var1.writeChar(var13.length);
               var1.writeChar(var13[0].length);

               for(var5 = 0; var5 < var13.length; ++var5) {
                  for(var6 = 0; var6 < var13[0].length; ++var6) {
                     var1.writeChar(var13[var5][var6]);
                  }
               }

               return;
            case 105:
               if(var2 == 1) {
                  int[] var14 = (int[])((int[])var0);
                  var1.writeChar(var14.length);

                  for(var5 = 0; var5 < var14.length; ++var5) {
                     var1.writeInt(var14[var5]);
                  }

                  return;
               }

               int[][] var11 = (int[][])((int[][])var0);
               var1.writeChar(var11.length);
               var1.writeChar(var11[0].length);

               for(var5 = 0; var5 < var11.length; ++var5) {
                  for(var6 = 0; var6 < var11[0].length; ++var6) {
                     var1.writeInt(var11[var5][var6]);
                  }
               }

               return;
            case 108:
               if(var2 == 1) {
                  long[] var12 = (long[])((long[])var0);
                  var1.writeChar(var12.length);

                  for(var5 = 0; var5 < var12.length; ++var5) {
                     var1.writeLong(var12[var5]);
                  }

                  return;
               }

               long[][] var9 = (long[][])((long[][])var0);
               var1.writeChar(var9.length);
               var1.writeChar(var9[0].length);

               for(var5 = 0; var5 < var9.length; ++var5) {
                  for(var6 = 0; var6 < var9[0].length; ++var6) {
                     var1.writeLong(var9[var5][var6]);
                  }
               }

               return;
            case 115:
               if(var2 == 1) {
                  short[] var10 = (short[])((short[])var0);
                  var1.writeChar(var10.length);

                  for(var5 = 0; var5 < var10.length; ++var5) {
                     var1.writeShort(var10[var5]);
                  }

                  return;
               }

               short[][] var8 = (short[][])((short[][])var0);
               var1.writeChar(var8.length);
               var1.writeChar(var8[0].length);

               for(var5 = 0; var5 < var8.length; ++var5) {
                  for(var6 = 0; var6 < var8[0].length; ++var6) {
                     var1.writeShort(var8[var5][var6]);
                  }
               }

               return;
            case 122:
               if(var2 == 1) {
                  boolean[] var7 = (boolean[])((boolean[])var0);
                  var1.writeChar(var7.length);

                  for(var5 = 0; var5 < var7.length; ++var5) {
                     var1.writeBoolean(var7[var5]);
                  }

                  return;
               }

               boolean[][] var4 = (boolean[][])((boolean[][])var0);
               var1.writeChar(var4.length);
               var1.writeChar(var4[0].length);

               for(var5 = 0; var5 < var4.length; ++var5) {
                  for(var6 = 0; var6 < var4[0].length; ++var6) {
                     var1.writeBoolean(var4[var5][var6]);
                  }
               }
            }
         }

      }
   }

   private static int a(Class var0) {
      String var1 = var0.getName();
      int var2 = 0;
      var1.length();
      char[] var3 = var1.toCharArray();

      for(int var4 = 0; var4 < var3.length && var3[var4] == 91; ++var4) {
         ++var2;
      }

      return var2;
   }

   private static int b(Object var0) {
      return a(var0.getClass());
   }

   private static String b(Class var0) throws Exception {
      String var1;
      (var1 = var0.getName()).length();
      char[] var3 = var1.toCharArray();

      for(int var4 = 0; var4 < var3.length; ++var4) {
         if(var3[var4] != 91) {
            switch(var3[var4]) {
            case 76:
               throw new Exception("Only primitive arrays supported");
            default:
               return ("" + var3[var4]).toLowerCase();
            }
         }
      }

      throw new Exception("Internal parser exception");
   }

   private static Object a(int var0, DataInputStream var1) throws Exception {
      switch(var0) {
      case 65:
         Vector var5 = new Vector();
         char var6 = var1.readChar();

         for(int var7 = 0; var7 < var6; ++var7) {
            var5.addElement(a(var1));
         }

         return var5;
      case 84:
         Calendar var4;
         (var4 = Calendar.getInstance()).setTime(new Date(var1.readLong()));
         return var4;
      case 85:
         return c(var1);
      case 88:
         byte[] var3 = new byte[var1.readChar()];
         var1.readFully(var3);
         return new String(var3);
      case 89:
         return new Date(var1.readLong());
      default:
         throw new Exception("Unknown type: " + var0);
      }
   }

   public static final Object a(DataInputStream var0) throws Exception {
      return b(var0);
   }

   private static Object b(DataInputStream var0) throws Exception {
      byte var1 = var0.readByte();
      byte var2 = var0.readByte();
      if(var1 == 79) {
         return b(var2, var0);
      } else if(var1 == 76) {
         return var0.readByte() == 1?null:b(var2, var0);
      } else if(var1 != 88 && var1 != 85) {
         if(var1 == 91) {
            return var0.readByte() == 1?null:(var2 == 79?c(var0.readByte(), var0):null);
         } else {
            throw new Exception("Invalid data stream");
         }
      } else {
         return var0.readByte() == 1?null:a(var2, var0);
      }
   }

   private static Object b(int var0, DataInputStream var1) throws Exception {
      switch(var0) {
      case 98:
         return new Byte(var1.readByte());
      case 99:
         return new Character(var1.readChar());
      case 105:
         return new Integer(var1.readInt());
      case 108:
         return new Long(var1.readLong());
      case 115:
         return new Short(var1.readShort());
      case 122:
         return new Boolean(var1.readBoolean());
      default:
         return null;
      }
   }

   private static Object c(int var0, DataInputStream var1) throws Exception {
      int var3 = var1.readInt();
      char var4 = var1.readChar();
      char var5;
      int var7;
      int var8;
      int var12;
      switch(var0) {
      case 98:
         if(var3 == 1) {
            byte[] var17 = new byte[var4];

            for(var12 = 0; var12 < var4; ++var12) {
               var17[var12] = var1.readByte();
            }

            return var17;
         } else {
            var5 = var1.readChar();
            byte[][] var20 = new byte[var4][var5];

            for(var7 = 0; var7 < var4; ++var7) {
               for(var8 = 0; var8 < var5; ++var8) {
                  var20[var7][var8] = var1.readByte();
               }
            }

            return var20;
         }
      case 99:
         if(var3 == 1) {
            char[] var16 = new char[var4];

            for(var12 = 0; var12 < var4; ++var12) {
               var16[var12] = var1.readChar();
            }

            return var16;
         }

         var5 = var1.readChar();
         char[][] var19 = new char[var4][var5];

         for(var7 = 0; var7 < var4; ++var7) {
            for(var8 = 0; var8 < var5; ++var8) {
               var19[var7][var8] = var1.readChar();
            }
         }

         return var19;
      case 105:
         if(var3 == 1) {
            int[] var13 = new int[var4];

            for(var12 = 0; var12 < var4; ++var12) {
               var13[var12] = var1.readInt();
            }

            return var13;
         }

         var5 = var1.readChar();
         int[][] var18 = new int[var4][var5];

         for(var7 = 0; var7 < var4; ++var7) {
            for(var8 = 0; var8 < var5; ++var8) {
               var18[var7][var8] = var1.readInt();
            }
         }

         return var18;
      case 108:
         if(var3 == 1) {
            long[] var11 = new long[var4];

            for(var12 = 0; var12 < var4; ++var12) {
               var11[var12] = var1.readLong();
            }

            return var11;
         }

         var5 = var1.readChar();
         long[][] var15 = new long[var4][var5];

         for(var7 = 0; var7 < var4; ++var7) {
            for(var8 = 0; var8 < var5; ++var8) {
               var15[var7][var8] = var1.readLong();
            }
         }

         return var15;
      case 115:
         if(var3 == 1) {
            short[] var10 = new short[var4];

            for(var12 = 0; var12 < var4; ++var12) {
               var10[var12] = var1.readShort();
            }

            return var10;
         }

         var5 = var1.readChar();
         short[][] var14 = new short[var4][var5];

         for(var7 = 0; var7 < var4; ++var7) {
            for(var8 = 0; var8 < var5; ++var8) {
               var14[var7][var8] = var1.readShort();
            }
         }

         return var14;
      case 122:
         if(var3 == 1) {
            boolean[] var9 = new boolean[var4];

            for(var12 = 0; var12 < var4; ++var12) {
               var9[var12] = var1.readBoolean();
            }

            return var9;
         }

         var5 = var1.readChar();
         boolean[][] var6 = new boolean[var4][var5];

         for(var7 = 0; var7 < var4; ++var7) {
            for(var8 = 0; var8 < var5; ++var8) {
               var6[var7][var8] = var1.readBoolean();
            }
         }

         return var6;
      default:
         throw new Exception("Unknow component type");
      }
   }

   private static Object c(DataInputStream var0) throws Exception {
      byte[] var2 = new byte[var0.readChar()];
      var0.readFully(var2);
      n var5;
      (var5 = (n)Class.forName(new String(var2)).newInstance()).a(var0);
      return var5;
   }
}
