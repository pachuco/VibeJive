package defPack;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.io.StreamConnection;

public final class OnlineConnection implements Runnable {

   private int a;
   private int b;
   private byte[] byte_array;
   private boolean isHTTP200;
   private Exception e;
   private String address;
   private String port;
   private static boolean h;
   private static boolean i;
   private static boolean isSocket;


   private OnlineConnection(int var1, int var2, byte[] var3, String var4, String var5) {
      address = var4;
      port = var5;
      a = var1;
      b = var2;
      byte_array = var3;
   }

   public final void run() {
      InputStream input_stream = null;
      DataOutputStream output_stream = null;
      StreamConnection stream_con = null;

      try {
         ByteArrayOutputStream byte_stream = new ByteArrayOutputStream();
         DataOutputStream data_stream;
         (data_stream = new DataOutputStream(byte_stream)).writeInt(a);
         data_stream.writeChar(b);
         data_stream.writeInt(byte_array.length);
         data_stream.write(byte_array);
         data_stream.close();
         String location = "http://" + address + ":" + port;
         byte[] content = PoopyCrypt.encode(byte_stream.toByteArray());
         if(!isSocket) {
            HttpConnection http_con;
            (http_con = (HttpConnection)(stream_con = (StreamConnection)Connector.open(location))).setRequestMethod("POST");
            http_con.setRequestProperty("content-type", "application/octet-Stream ");
            output_stream = stream_con.openDataOutputStream();
         } else {
            location = "socket://" + address + ":" + port;
            int content_length = content.length;
            stream_con = (StreamConnection)Connector.open(location);
            isHTTP200 = true;
            StringBuffer var9;
            (var9 = new StringBuffer()).append("post:nc\r\n");
            var9.append("content-length: " + content_length + "\r\n\r\n");
            (output_stream = stream_con.openDataOutputStream()).write(var9.toString().getBytes());
         }

         output_stream.writeInt(content.length);
         output_stream.write(content);
         output_stream.flush();
         if(!isSocket) {
            isHTTP200 = ((HttpConnection)stream_con).getResponseCode() == 200;
         }

         input_stream = stream_con.openInputStream();
         byte_stream = new ByteArrayOutputStream();
         byte[] var23 = new byte[200];

         int var25;
         while((var25 = input_stream.read(var23)) != -1) {
            byte_stream.write(var23, 0, var25);
         }

         byte[] var10 = byte_stream.toByteArray();
         String POSTbody;
         if((POSTbody = (new String(var10)).trim()).startsWith("HTTP/")) {
            isHTTP200 = POSTbody.startsWith("HTTP/1.0 200");
            int linend_position;
            if((linend_position = POSTbody.indexOf("\r\n\r\n")) == -1) {
               throw new Exception("Internal error: HTTP headers detected in stream");
            }

            byte[] var13 = new byte[var10.length - (linend_position + 4)];
            System.arraycopy(var10, linend_position + 4, var13, 0, var13.length);
            var10 = var13;
         }

         byte_array = PoopyCrypt.decode(var10);
         byte_stream.close();
         if(isSocket && byte_array[0] == 0) {
            ByteArrayOutputStream var26;
            (var26 = new ByteArrayOutputStream()).write(byte_array, 2, byte_array.length);
            byte_array = var26.toByteArray();
            var26.close();
            isHTTP200 = false;
         }

         return;
      } catch (Exception var21) {
         e = var21;
         isHTTP200 = false;
      } finally {
         try {
            if(output_stream != null) {
               output_stream.close();
            }

            if(input_stream != null) {
               input_stream.close();
            }

            if(stream_con != null) {
               stream_con.close();
            }
         } catch (Exception var20) {
            ;
         }

      }

   }

   private Object a() throws Exception {
      if(!isHTTP200 && e != null) {
         throw e;
      } else {
         ByteArrayInputStream var1 = new ByteArrayInputStream(byte_array);
         DataInputStream var2;
         Object var3 = ArrayHandlerThingie.a(var2 = new DataInputStream(var1));
         var2.close();
         if(!isHTTP200) {
            throw new Exception(var3.toString());
         } else {
            return var3;
         }
      }
   }

   public static final Object doStuff(int int0, int int1, byte[] byte_array) throws Exception {
      String address = ToolsAddressPort.getAddress();
      String port = ToolsAddressPort.getPort();
      if(address == null) {
         throw new Exception("GatewayHost not initialized");
      } else if(h && !i) {
         throw new Exception("Device has not been authenticated");
      } else {
         OnlineConnection var5 = new OnlineConnection(int0, int1, byte_array, address, port);
         Thread var6;
         (var6 = new Thread(var5)).start();
         var6.join();
         return var5.a();
      }
   }
}
