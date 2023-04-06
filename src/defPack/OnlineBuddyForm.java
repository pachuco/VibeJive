package defPack;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.TextField;

public final class OnlineBuddyForm implements CommandListener {

   private static Command comSubmit = new Command("Submit", Command.OK, 1);
   private static Command comCancel = new Command("Cancel", Command.CANCEL, 1);
   private Menu d;
   private Display e;
   public Form a;
   private TextField f;


   public OnlineBuddyForm(Menu var1, Display var2) {
      d = var1;
      e = var2;
      a();
   }

   private void a() {
      a = new Form("Add Buddy");
      a.append((Item)(f = new TextField("Name", "", 20, 0)));
      a.addCommand(comSubmit);
      a.addCommand(comCancel);
      a.setCommandListener(this);
      e.setCurrent(a);
   }

   public final void commandAction(Command var1, Displayable var2) {
      if(var1 == comCancel) {
         d.e();
      } else {
         if(var1 == comSubmit) {
            d.a(f.getString());
         }

      }
   }
}
