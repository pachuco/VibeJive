/* Patch and patch familly selection menu.
*/

package defPack;
import java.io.IOException;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;

public final class InstrSelector implements CommandListener {

   private static Command comCancel = new Command("Cancel", Command.CANCEL, 1);
   private static Command comBack = new Command("Back", Command.BACK, 1);
   private static Command comPlay = new Command("Audition", Command.SCREEN, 1);
   private SeqArranger localSeqArranger;
   private Display localDisplay;
   private MidiPlayer localMidiPlayer;
   private boolean g;
   private boolean h;
   private boolean i;
   private boolean j;
   private int k;
   private Image imgFoldMus;
   private Image imgDocPulse;
   private List n;
   private List o;


   public InstrSelector(SeqArranger var1, Display var2, MidiPlayer var3, int var4) {
      localSeqArranger = var1;
      localDisplay = var2;
      localMidiPlayer = var3;
      g = true;
      h = true;
      k = var4;
      j = false;

      try {
         imgFoldMus = Image.createImage("/images/folder_music.png");
         imgDocPulse = Image.createImage("/images/document_pulse.png");
      } catch (IOException var6) {
         ;
      }
   }

   public final void a(boolean var1, boolean var2) {
      h = var1;
      g = var2;
   }

   public final void a(boolean var1) {
      j = true;
      i = var1;
   }

   public final void a() {
      String[] var1 = InstrBank.a();
      List var10001;
      InstrSelector var10000;
      if(h) {
         Image[] var2 = new Image[var1.length];

         for(int var3 = 0; var3 < var2.length; ++var3) {
            var2[var3] = imgFoldMus;
         }

         var10000 = this;
         var10001 = new List("Patch Families", 3, var1, var2);
      } else {
         var10000 = this;
         var10001 = new List("Patch Families", 3);
      }

      var10000.n = var10001;
      if(g) {
         n.append("Drum Track", imgFoldMus);
      }

      n.addCommand(comCancel);
      n.setCommandListener(this);
      localDisplay.setCurrent(n);
   }

   public final void commandAction(Command var1, Displayable var2) {
      if(var1 == List.SELECT_COMMAND && var2 == n) {
         int var3;
         if((var3 = n.getSelectedIndex()) == 16) {
            localSeqArranger.a(-1, 9);
         } else {
            String[] var4;
            Image[] var5 = new Image[(var4 = InstrBank.b(var3)).length];

            for(int var6 = 0; var6 < var5.length; ++var6) {
               var5[var6] = imgDocPulse;
            }

            o = new List(InstrBank.a()[var3], 3, var4, var5);
            o.addCommand(comBack);
            if(j) {
               o.addCommand(comPlay);
            }

            o.setCommandListener(this);
            localDisplay.setCurrent(o);
         }
      } else if(var1 == List.SELECT_COMMAND && var2 == o) {
         localSeqArranger.a(n.getSelectedIndex() * 8 + o.getSelectedIndex(), k);
      } else if(var1 == comBack) {
         localDisplay.setCurrent(n);
      } else if(var1 == comCancel) {
         localSeqArranger.a(-2, k);
      } else {
         if(var1 == comPlay) {
            if(i) {
               localMidiPlayer.a(k, n.getSelectedIndex() * 8 + o.getSelectedIndex());
               return;
            }

            localMidiPlayer.b(k, n.getSelectedIndex() * 8 + o.getSelectedIndex());
         }

      }
   }
}
