package defPack;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.Item;

public final class LoadScreen extends Gauge {

   public LoadScreen(String var1, Display var2, int var3) {
      super("Please wait", false, var3, 0);
      Form var4;
      (var4 = new Form(var1)).append((Item)this);
      var2.setCurrent(var4);
   }
   public LoadScreen(String var1, Display var2) {
      super("Please wait", false, -1, 2);
      Form var3;
      (var3 = new Form(var1)).append((Item)this);
      var2.setCurrent(var3);
   }
}
