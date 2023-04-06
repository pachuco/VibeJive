package defPack;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;
import javax.microedition.media.MediaException;

public final class RiffLoadSelect implements CommandListener {

   private static Command comBack = new Command("Back", Command.BACK, 1);
   private static Command comPlay = new Command("Audition", Command.SCREEN, 2);
   private RiffSongLoadSelect localRiffSongLoadSelect;
   private Display localDisplay;
   private MidiPlayer localMidiPlayer;
   private Song localSong;
   private SeqArranger localSeqArranger;
   private int h;
   private Song localSong2;
   private RiffTools[] arrSeqRiffTransp;


   public RiffLoadSelect(Display var1, MidiPlayer var2, Song var3, SeqArranger var4, int var5) {
      localDisplay = var1;
      localMidiPlayer = var2;
      localSong = var3;
      localSeqArranger = var4;
      h = var5;
   }

   private void b(int var1) {
      localMidiPlayer.playToggle();
      RiffTools var2 = arrSeqRiffTransp[var1];
      j var3 = null;
      Channel[] var4 = localSong2.a();

      for(int var5 = 0; var5 < var4.length && var3 == null; ++var5) {
         j[] var6 = var4[var5].a();

         for(int var7 = 0; var7 < var6.length && var3 == null; ++var7) {
            if(var6[var7].b == var2) {
               var3 = var6[var7];
            }
         }
      }

      Song var9;
      (var9 = new Song("")).setTempo(localSong2.tempo);
      Channel var10 = new Channel("");
      if(var3 != null) {
         var10.a(var3.c.a);
         var10.b(var3.c.b);
      }

      (var3 = new j(var10)).b = var2;
      var3.a(0);
      var10.a(var3);
      var9.b(var10);

      try {
         localMidiPlayer.a(var9, 0, var2.b, false);
      } catch (Exception ex) {
         cust.ExceptionHandler.ex(ex);
      }
   }

   public final void a() {
      localRiffSongLoadSelect = new RiffSongLoadSelect(this);
      Displayable var1 = localRiffSongLoadSelect.a(localSong);
      localDisplay.setCurrent(var1);
   }

   public final void a(int var1) {
      if(var1 == -1) {
         localSeqArranger.a((Song)null, (RiffTools)null);
      } else {
         List var2;
         (var2 = new List("Patterns", 3)).addCommand(comBack);
         var2.addCommand(comPlay);
         var2.setCommandListener(this);
         Song var10001;
         RiffLoadSelect var10000;
         if(localSong != null && localSong.d != var1) {
            var10000 = this;
            var10001 = RecStoreHandler.songGET(var1);
         } else {
            var10000 = this;
            var10001 = localSong;
         }

         var10000.localSong2 = var10001;
         arrSeqRiffTransp = localSong2.c();

         for(int var3 = 0; var3 < arrSeqRiffTransp.length; ++var3) {
            var2.append(arrSeqRiffTransp[var3].a, (Image)null);
         }

         localDisplay.setCurrent(var2);
      }
   }

   public final void commandAction(Command var1, Displayable var2) {
      if(var1 == List.SELECT_COMMAND) {
         RiffTools var4;
         if((var4 = arrSeqRiffTransp[((List)var2).getSelectedIndex()]).b > h) {
            defPack.ErrorDisplay.error("That pattern is too long to fit at the cursor position");
         } else {
            localSeqArranger.a(localSong2, var4);
         }
      } else if(var1 == comPlay) {
         b(((List)var2).getSelectedIndex());
      } else {
         if(var1 == comBack) {
            Displayable var3 = localRiffSongLoadSelect.a(localSong);
            localDisplay.setCurrent(var3);
         }

      }
   }
}
