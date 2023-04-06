package cust;

import java.io.DataInputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import midlet.SeqMidlet;
import defPack.LoadScreen;


public final class UpdateCheck implements CommandListener {

   private static Command comYes = new Command("Yes", Command.OK, 0);
   private static Command comBack = new Command("Back", Command.BACK, 0);
   private static Command comNo = new Command("No", Command.CANCEL, 0);
   private SeqMidlet localSeqMidlet;
   private Display localDisplay;
   private Form localForm;
   private String message;
   private String updateVersion;
   
   private static int updateAvailable    =1;
   private static int latestVersion      =0;
   private static int somethingBroke     =-1;
   private static int noConnection       =-2;
   private static int invalidJad         =-3;
   private static int unreachableJad     =-4;


   public UpdateCheck(SeqMidlet var1) {
      localSeqMidlet = var1;
      localDisplay = Display.getDisplay(localSeqMidlet);
      new LoadScreen("Update check", localDisplay);
      localForm = new Form("Vibe update");
      int updateStatus=unfunCheckUpdate(localSeqMidlet);
      
      if (updateStatus==updateAvailable) {
          message="There is a newer version of Vibe available. It is strongly recommended you get this now.\nWould you like get the new version?";
          localForm.addCommand(comYes);
          localForm.addCommand(comNo);
          
      }else if (updateStatus==latestVersion) {
          message="You have the latest version.";
          localForm.addCommand(comBack);
          
      }else if (updateStatus==somethingBroke) {
          message="Unable to check for updates because something is broken.";
          localForm.addCommand(comBack);
          
      }else if (updateStatus==noConnection) {
          message="Unable to connect.";
          localForm.addCommand(comBack);
          
      }else if (updateStatus==invalidJad) {
          message="Update file found but is invalid.";
          localForm.addCommand(comBack);
          
      }else if (updateStatus==unreachableJad) {
          message="Update file not found.";
          localForm.addCommand(comBack);
      }
      localForm.append(message);
      localForm.setCommandListener(this);
      localDisplay.setCurrent(localForm);
   }

   public final void commandAction(Command varCom, Displayable uselessVar) {
      if(varCom == comYes) {
          updateInstall(localSeqMidlet);
      }
      if(varCom == comBack||varCom == comNo){
         localSeqMidlet.mainMenu(false, false);
      }
   }
   
   private int unfunCheckUpdate(SeqMidlet paramMIDlet){
       HttpConnection localHttpConnection = null;
       DataInputStream localDataInputStream = null;
       try{
           String updateURL = updateUrlGET(paramMIDlet);
           if (updateURL == null){
               return somethingBroke;
           }else if ((localHttpConnection = (HttpConnection)Connector.open(updateURL)).getResponseCode() == 200){
               String property = "MIDlet-Version";
               String version = paramMIDlet.getAppProperty(property);
               localDataInputStream = localHttpConnection.openDataInputStream();
               long l;
               byte[] arrayOfByte = new byte[(int)(l = localHttpConnection.getLength())];
               localDataInputStream.readFully(arrayOfByte);
               String str4;
               int i;
               int j;
               if (((i = (str4 = new String(arrayOfByte)).indexOf(property)) != -1) && ((j = str4.indexOf("\n", i)) != -1)){
                   updateVersion = str4.substring(i + property.length() + 1, j).trim();
                   if (!version.equals(updateVersion)){
                       return updateAvailable;
                   }else{
                       return latestVersion;
                   }
               }else{
                   return invalidJad;
               }
           }else{
               return unreachableJad;
           }
       }catch (Throwable localThrowable5){
           ;
       }finally{
           try{
               if (localDataInputStream != null){
                   localDataInputStream.close();
               }
           }catch (Throwable localThrowable6){
               ;
           }
       }
       return noConnection;
   }

   private void updateInstall(SeqMidlet var0) {
      try {
         String var1;
         if((var1 = updateUrlGET(var0)) != null) {
            var0.platformRequest(var1);
         }
      } catch (Exception var2) {
         ;
      }
   }
   
   private static String updateUrlGET(SeqMidlet varMID) {
      String varString;
      return (varString = varMID.getAppProperty("UpdateURL"));
   }
}
