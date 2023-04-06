package defPack;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public final class j {

   public int a;
   public RiffTools b;
   public Channel c;


   public j(Channel var1) {
      c = var1;
   }

   public j(DataInputStream var1, RiffTools[] var2, Channel var3) throws Exception {
      this(var3);
      a = var1.readInt();
      long var4 = var1.readLong();

      for(int var6 = 0; var6 < var2.length; ++var6) {
         if(var2[var6].c == var4) {
            b = var2[var6];
            return;
         }
      }

   }

   public final void a(int var1) {
      if(var1 < 0) {
         throw new IllegalArgumentException();
      } else {
         a = var1;
      }
   }

   public final void a(DataOutputStream var1) throws Exception {
      var1.writeInt(a);
      var1.writeLong(b.c);
   }
}
