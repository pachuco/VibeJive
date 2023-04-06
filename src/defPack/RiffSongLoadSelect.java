package defPack;
import java.util.Vector;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;

public final class RiffSongLoadSelect implements CommandListener {

   private static Command comCancel = new Command("Cancel", Command.CANCEL, 1);
   private RiffLoadSelect localRiffLoadSelect;
   private Vector c;
   private List d;


   public RiffSongLoadSelect(RiffLoadSelect var1) {
      localRiffLoadSelect = var1;
   }

   public final Displayable a(Song var1) {
      try {
         Vector[] var2;
         Vector var3 = (var2 = RecStoreHandler.songListGET())[0];
         c = var2[1];
         if(var1 != null && var1.d == 0) {
            var3.insertElementAt(var1.songName, 0);
            c.insertElementAt(new Integer(0), 0);
         }

         if(var3.size() == 0) {
            return null;
         } else {
            Image var4 = Image.createImage("/images/document_music.png");
            d = new List("Songs", 3);

            for(int var5 = 0; var5 < var3.size(); ++var5) {
               d.append((String)var3.elementAt(var5), var4);
            }

            d.addCommand(comCancel);
            d.setCommandListener(this);
            return d;
         }
      } catch (Throwable var6) {
         var6.printStackTrace();
         return null;
      }
   }

   public final void commandAction(Command var1, Displayable var2) {
      int var10001;
      RiffLoadSelect var10000;
      if(var1 == List.SELECT_COMMAND) {
         var10000 = localRiffLoadSelect;
         var10001 = ((Integer)c.elementAt(d.getSelectedIndex())).intValue();
      } else {
         if(var1 != comCancel) {
            return;
         }

         var10000 = localRiffLoadSelect;
         var10001 = -1;
      }

      var10000.a(var10001);
   }
}
