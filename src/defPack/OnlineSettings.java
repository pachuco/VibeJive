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
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;

public final class OnlineSettings implements CommandListener {

   private static Command comSubmit = new Command("Submit", Command.OK, 1);
   private static Command comCancel = new Command("Cancel", Command.CANCEL, 1);
   private Menu c;
   private Display d;
   private int e;
   private Form localForm;
   private TextField g;


   public OnlineSettings(Menu var1, Display var2, int var3) {
      c = var1;
      d = var2;
      e = var3;
      a();
   }

   private void a() {
      localForm = new Form("Online settings");
      localForm.append((Item)(g = new TextField("Buffer size", String.valueOf(e), 4, 2)));
      localForm.append((Item)(new StringItem("Info", "Increase buffer size for faster song up/download. Decrease if up/downloads fail.", 0)));
      localForm.addCommand(comSubmit);
      localForm.addCommand(comCancel);
      localForm.setCommandListener(this);
      d.setCurrent(localForm);
   }

   public final void commandAction(Command var1, Displayable var2) {
      if(var1 == comCancel) {
         c.f();
      } else {
         if(var1 == comSubmit) {
            int var3;
            if((var3 = Integer.parseInt(g.getString())) < 500) {
               Alert var4;
               (var4 = new Alert("Buffer size", "The buffer size must be at least 500", (Image)null, AlertType.ERROR)).setTimeout(-2);
               d.setCurrent(var4);
               return;
            }

            c.a(var3);
         }

      }
   }
}
