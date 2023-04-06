package defPack;

public final class ToolsAddressPort {

   private static String address;
   private static String port = "8201";


   public static final void setAddressPort(String var0, String var1) {
      address = var0;
      port = var1;
   }

   public static final String getAddress() {
      return address;
   }

   public static final String getPort() {
      return port;
   }

}
