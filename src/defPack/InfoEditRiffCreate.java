/* New Pattern screen.
*/

package defPack;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.TextField;

public final class InfoEditRiffCreate implements CommandListener {

   private static Command comSubmit = new Command("Submit", Command.OK, 1);
   private static Command comCancel = new Command("Cancel", Command.CANCEL, 1);
   private SeqArranger c;
   private Display d;
   private int e;
   private String f;
   private boolean g;
   private Form h;
   private TextField i;
   private TextField j;


   public InfoEditRiffCreate(SeqArranger var1, Display var2, int var3, String var4) {
      c = var1;
      d = var2;
      e = var3;
      f = var4;
      g = true;
      a();
   }

   private void a() {
      h = new Form("Pattern Info");
      if(g) {
         h.append((Item)(i = new TextField("Bars", "1", 3, 2)));
      }

      h.append((Item)(j = new TextField("Name", f, 20, 0)));
      h.addCommand(comSubmit);
      h.addCommand(comCancel);
      h.setCommandListener(this);
      d.setCurrent(h);
   }

   public final void commandAction(Command var1, Displayable var2) {
      if(var1 == comCancel) {
         c.a();
      } else {
         if(var1 == comSubmit) {
            int var3 = 1;
            if(g) {
               Alert var4;
               if((var3 = Integer.parseInt(i.getString())) > e) {
                  (var4 = new Alert("Pattern Info", "The pattern length is too big to fit.\n\nThere is room for " + e + " bars", (Image)null, AlertType.ERROR)).setTimeout(-2);
                  d.setCurrent(var4);
                  return;
               }

               if(var3 <= 0) {
                  (var4 = new Alert("Pattern Info", "The pattern length must be at least 1 bar", (Image)null, AlertType.ERROR)).setTimeout(-2);
                  d.setCurrent(var4);
                  return;
               }
            }

            c.a(var3, j.getString());
         }

      }
   }
}
