/* This class pesters you with errors. All functions are overloaded
 * under the handle 'fun'.
 * Not like it's actually fun or anything.
*/

package defPack;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Image;

public final class ErrorDisplay {

   private static Display localDisplay;


   public static void setDisplay(Display var0) {
      localDisplay = var0;
   }

   public static void error(Throwable var0) {
      error(var0, (Displayable)null);
   }

   public static void error(Throwable var0, Displayable var1) {
      var0.printStackTrace();
      Alert var2;
      (var2 = new Alert("Error", "" + var0, (Image)null, (AlertType)null)).setTimeout(4000);
      if(var1 == null) {
         localDisplay.setCurrent(var2);
      } else {
         localDisplay.setCurrent(var2, var1);
      }
   }

   public static void info(String var0, Displayable var1) {
      Alert var2;
      (var2 = new Alert("Info", var0, (Image)null, (AlertType)null)).setTimeout(-2);
      if(var1 == null) {
         localDisplay.setCurrent(var2);
      } else {
         localDisplay.setCurrent(var2, var1);
      }
   }

   public static void error(String var0) {
      info(var0, (Displayable)null);
   }
}
