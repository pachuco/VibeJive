package midlet;

import java.util.Date;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import cust.FS;

public class SeqMidlet extends MIDlet implements Runnable {

   private static Command comPlay = new Command("Play", Command.SCREEN, 0);
   private static Command comStop = new Command("Stop", Command.SCREEN, 0);
   private static Command comMenu = new Command("Main menu", Command.BACK, 2);
   private static Command comSongProp = new Command("Song properties", Command.SCREEN, 91);
   private static Command comMemStat = new Command("Memory usage", Command.SCREEN, 96);
   private static Command comModeDirect = new Command("Direct mode", Command.SCREEN, 97);
   private static Command comModeStream = new Command("Stream mode", Command.SCREEN, 97);
   private static Command comAboutOK = new Command("OK", Command.SCREEN, 1);
   private defPack.Song localSong;
   private defPack.MidiPlayer localMidiPlayer;
   private defPack.SeqArranger localSeqArranger;
   private Display localDisplay = Display.getDisplay(this);
   private Displayable localDisplayable;
   private defPack.OnlineArray2Http localOnlineArray2Http;
   private FS squirrelAway;

   public SeqMidlet() {
      defPack.ErrorDisplay.setDisplay(localDisplay);
   }

   private void songCreate() {
      int varSongAmount = defPack.RecStoreHandler.totalSongNumGET() + 1;
      localSong = new defPack.Song("Song " + varSongAmount);
      defPack.Channel var2;
      (var2 = new defPack.Channel("")).a(9);
      localSong.b(var2);
   }

   private void h() {
      try {
         String var1 = getAppProperty("gatewayHost");
         String var2 = getAppProperty("gatewayPort");
         String var10001;
         SeqMidlet varSeqMidlet;
         int var10002;
         if(var1 != null) {
            varSeqMidlet = this;
            var10001 = var1;
            var10002 = Integer.parseInt(var2);
         } else {
            varSeqMidlet = this;
            var10001 = "127.0.0.1";
            var10002 = 8205;
         }

         varSeqMidlet.localOnlineArray2Http = defPack._OnlineArrayThingie.a(var10001, var10002);
         localMidiPlayer = new defPack.MidiPlayer();
         localMidiPlayer.a(false);
         localSeqArranger = new defPack.SeqArranger(localDisplay, this, localMidiPlayer);
         boolean var3 = false;
         long[] var4;
         if((var4 = defPack.RecStoreHandler.getLoginInfo()) == null) {
            defPack.RecStoreHandler.a(-1, 31536000000L, System.currentTimeMillis());
         } else {
            new Date(var4[1] + var4[2]);
            long var6;
            if((var6 = System.currentTimeMillis()) < var4[2]) {
               var4[2] = var6;
            }

            var4[1] -= var6 - var4[2];
            defPack.RecStoreHandler.a((int)var4[0], var4[1], System.currentTimeMillis());
            //expiry date check
            //if(var4[1] < 0L) {
            //   port = true;
            //}
         }

         mainMenu(false, true);
      } catch (Throwable var8) {
         var8.printStackTrace();
         defPack.ErrorDisplay.error(var8, localSeqArranger);
      }
   }

   private void memStatGET() {
      try {
         int var1 = defPack.RecStoreHandler.a(localSong).length / 1024;
         int var2 = defPack.RecStoreHandler.remainingRecordStorageGET();
         Runtime.getRuntime().gc();
         long var3 = Runtime.getRuntime().freeMemory() / 1024L;
         Alert var5;
         (var5 = new Alert("Memory Status", "Current song size: " + var1 + "k.\n" + "Free phone memory: " + var2 + "k.\n" + "Free heap memory: " + var3 + "k", (Image)null, (AlertType)null)).setTimeout(-2);
         localDisplay.setCurrent(var5, localDisplay.getCurrent());
      } catch (Exception var6) {
         ;
      }
   }

   private void j() {
      localDisplayable = localDisplay.getCurrent();
      new defPack.InfoEditSong(this, localDisplay, localSong);
   }

   public final void startApp() throws MIDletStateChangeException {
      if(localDisplayable != null) {
         sideMenuDisplay(localDisplayable);
         localDisplay.setCurrent(localDisplayable);
         localDisplayable = null;
      } else {
         new defPack.LoadScreen("Initialising", localDisplay);
         (new Thread(this)).start();
      }
   }

   public final void run() {
      h();
   }

   public final void pauseApp() {
      localMidiPlayer.playToggle();
      localDisplayable = localDisplay.getCurrent();
   }

   public final void destroyApp(boolean var1) throws MIDletStateChangeException {
      if(localMidiPlayer != null) {
         localMidiPlayer.playToggle();
      }

      notifyDestroyed();
   }

   public final void sideMenuDisplay(Displayable var1) {
      label21: {
         Command var10001;
         Displayable var10000;
         label20: {
            if(!localMidiPlayer.isPlayerOn()) {
               var1.addCommand(comPlay);
               var1.removeCommand(comStop);
               if(!localMidiPlayer.b) {
                  break label21;
               }

               if(localMidiPlayer.a == 2) {
                  var1.addCommand(comModeStream);
                  var10000 = var1;
                  var10001 = comModeDirect;
                  break label20;
               }

               var1.addCommand(comModeDirect);
            } else {
               var1.addCommand(comStop);
               var1.removeCommand(comPlay);
               var1.removeCommand(comModeDirect);
            }

            var10000 = var1;
            var10001 = comModeStream;
         }

         var10000.removeCommand(var10001);
      }

      var1.addCommand(comMenu);
      var1.addCommand(comSongProp);
      var1.addCommand(comMemStat);
   }

   public final void sideMenuCommand(Command c, Displayable disp) {
      if(c == comPlay) {
         try {
            localMidiPlayer.a(localSong, localSong.e, localSong.f, true);
         } catch (Exception ex) {
            cust.ExceptionHandler.ex(ex);
         }
      } else if(c == comStop) {
         localMidiPlayer.playToggle();
      } else if(c == comSongProp) {
         j();
      } else if(c == comMenu) {
         mainMenu(false, true);
      } else if(c == comMemStat) {
         memStatGET();
      } else {
         boolean var10001;
         defPack.MidiPlayer var10000;
         if(c == comModeDirect) {
            var10000 = localMidiPlayer;
            var10001 = true;
         } else {
            if(c != comModeStream) {
               return;
            }

            var10000 = localMidiPlayer;
            var10001 = false;
         }

         var10000.a(var10001);
         sideMenuDisplay(disp);
      }
   }

   public final void setFocusKillPlayback() {
      localMidiPlayer.playToggle();
      localDisplay.setCurrent(localDisplayable);
      //localDisplay.setCurrent(localSeqArranger);
   }

   public final void setFocus() {
      localDisplay.setCurrent(localDisplayable);
      //localDisplay.setCurrent(localSeqArranger);
   }

   public final void songLoad(defPack.Song var1) {
      localSong = var1;
      localSeqArranger.a(localSong);
      localDisplay.setCurrent(localSeqArranger);
   }

   public final void songSave() {
       if(defPack.RecStoreHandler.totalSongNumGET()<1){
           songCreate();
           songLoad(localSong);
       }
       if(defPack.RecStoreHandler.songSave(localSong)) {
           k();
       }else{
           defPack.ErrorDisplay.error("There is not enough free space to save this song");
       }
   }
   
   public final void songNew() {
      songCreate();
      localSeqArranger.a(localSong);
      localDisplay.setCurrent(localSeqArranger);
   }

   public final void a(String address) {
      String host = getAppProperty("gatewayHost");
      String port = getAppProperty("gatewayPort");
      if(host == null) {
         host = "localhost";
         port = "8205";
      }

      try {
         platformRequest("http://" + host + ":" + port + "/" + address);
         k();
      } catch (Exception ex) {
         defPack.ErrorDisplay.error((Throwable)ex, localDisplayable);
      }
   }

   private void k() {
      if(localDisplayable != null) {
         localDisplay.setCurrent(localDisplayable);
      } else {
         mainMenu(false, true);
      }
   }

   public final void appExit() {
      try {
         destroyApp(true);
      } catch (Throwable var2) {
         var2.printStackTrace();
      }
   }
   
   public void emptySong(){
       localSong=null;
   }
   public void mainMenu(boolean notListsMenu, boolean getsDisplay) {
      if(getsDisplay){
          localDisplayable = localDisplay.getCurrent();
      }
      defPack.Menu menu = new defPack.Menu(this, localSong, localOnlineArray2Http);
      if(!notListsMenu) {
         menu.menuListAppend();
      }

   }
   public void aboutScreen(){
       new cust.AboutScreen(this);
   }
   public void updateCheck(){
       new cust.UpdateCheck(this);
   }
   public void vibeTools(){
       new cust.VibeTools(this, localSong);
   }
   public void keepFS(FS var1){
       squirrelAway=var1;
   }
   public FS fetchFS(){
       return squirrelAway;
   }
}
