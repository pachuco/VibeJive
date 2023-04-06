/* Song info edit screen. Name, tempoField and whatnot.
*/

package defPack;
import midlet.SeqMidlet;
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

public final class InfoEditSong implements CommandListener {

   private static Command comSubmit = new Command("Submit", Command.OK, 1);
   private static Command comCancel = new Command("Cancel", Command.CANCEL, 1);
   private SeqMidlet localSeqMidlet;
   private Display localDisplay;
   private Song localSong;
   private Form localForm;
   private TextField nameField;
   private TextField tempoField;


   public InfoEditSong(SeqMidlet var1, Display var2, Song var3) {
      localSeqMidlet = var1;
      localDisplay = var2;
      localSong = var3;
      a();
   }

   private void a() {
      localForm = new Form("Song Info");
      localForm.append((Item)(nameField = new TextField("Name", localSong.songName, 99, 0)));
      localForm.append((Item)(tempoField = new TextField("BPM", "" + localSong.tempo, 3, 2)));
      localForm.addCommand(comSubmit);
      localForm.addCommand(comCancel);
      localForm.setCommandListener(this);
      localDisplay.setCurrent(localForm);
   }

   public final void commandAction(Command c, Displayable disp) {
      if(c == comCancel) {
         localSeqMidlet.setFocus();
      } else {
         if(c == comSubmit) {
            int tempo;
            if((tempo = Integer.parseInt(tempoField.getString())) > 500 || tempo < 0) {
               Alert var4;
               (var4 = new Alert("Song Info", "Tempo must be between 1 and 500 bpm", (Image)null, (AlertType)null)).setTimeout(-2);
               localDisplay.setCurrent(var4);
               return;
            }

            localSong.setTempo(tempo);
            localSong.songName = nameField.getString();
            localSeqMidlet.setFocusKillPlayback();
         }

      }
   }
}
