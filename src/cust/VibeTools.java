package cust;

import defPack.RecStoreHandler;
import defPack.Song;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextField;
import midlet.SeqMidlet;

public class VibeTools implements CommandListener {
   
   private static Command comOk = new Command("OK", Command.SCREEN, 0);
   private static Command comOpen = new Command("Open", Command.ITEM, 2);
   private static Command comDelete = new Command("Delete", Command.ITEM, 8);
   private static Command comNewFolder = new Command("Create folder", Command.SCREEN, 9);
   private static Command comImpVbm = new Command("Import song", Command.SCREEN, 7);
   private static Command comExpVbm = new Command("Export song", Command.ITEM, 6);
   private static Command comExpMid = new Command("Export midi", Command.ITEM, 5);
   private static Command comExpRms = new Command("Backup RMS", Command.SCREEN, 4);
   private static Command comImpRms = new Command("Restore RMS", Command.SCREEN, 3);
   private static Command comSave = new Command("Save", Command.SCREEN, 0);
   private static Command comBack = new Command("Back", Command.BACK, 1);
   private static Command comCancel = new Command("Cancel", Command.CANCEL, 1);
   private Display localDisplay;
   private FS localFS;
   private Vector recordIDs;
   private Vector songTitleList;
   private Vector songIndexList;
   private List listLocal;
   private List listFiles;
   private Vector fileList;
   private int selInd;
   private int selMenu;
   private SeqMidlet localSeqMidlet;
   private Song localSong;
   private Image imgFolder;
   private Image imgDummy;
   private Image imgVBM;
   private Image imgMID;
   private Image imgRMS;
   private Form saveForm;
   private Form folderForm;
   private Form error_form;
   private TextField saveField;
   
   private static int MIDexport=0;
   private static int VBMexport=1;
   private static int VBMimport=2;
   private static int RMSexport=3;
   private static int RMSimport=4;
  
   public VibeTools(SeqMidlet var1, Song var2) {
      try {
          imgFolder = Image.createImage("/images/folder.png");
          imgDummy = Image.createImage("/images/dummy.png");
          imgVBM = Image.createImage("/images/document_music.png");
          imgMID = Image.createImage("/images/purple_note.png");
          imgRMS = Image.createImage("/images/disk_blue.png");
      } catch (IOException ex) {
          ;
      }
      localSeqMidlet = var1;
      localSong = var2;
      localDisplay = Display.getDisplay(localSeqMidlet);
      if(localSeqMidlet.fetchFS()==null){
          localFS = new FS();
      }else{
          localFS=localSeqMidlet.fetchFS();
      }
      listLocal();
   }
   
   private void listLocal() {
      listLocal = new List("Saved songs", 3);
      listLocal.addCommand(comImpVbm);
      listLocal.addCommand(comImpRms);
      listLocal.addCommand(comBack);
      boolean var2 = false;
      if (hasAnySongs()) {
         songListGET();
         for (int i = 0; i < songTitleList.size(); ++i) {
            listLocal.append((String) songTitleList.elementAt(i), imgVBM);
         }
         listLocal.addCommand(comExpRms);
         listLocal.addCommand(comExpMid);
         listLocal.addCommand(comExpVbm);
         listLocal.addCommand(comOpen);
         listLocal.addCommand(comDelete);
         listLocal.setSelectCommand(comOpen);
      }
      recordIDs = RecStoreHandler.songIdListGET();
      localDisplay.setCurrent(listLocal);
      listLocal.setCommandListener(this);
   }
   
   private void listFiles() {
      listFiles = new List("Browsing...", 3);
      fileList = localFS.fileListGET();
      if(localFS.isNotRoot()) {
         fileList.insertElementAt("..", 0);
      }

      listFiles.addCommand(comCancel);
      if(selMenu==VBMexport||selMenu==MIDexport||selMenu==RMSexport) {
         listFiles.addCommand(comSave);
      }

      if(localFS.isNotRoot()) {
         listFiles.addCommand(comNewFolder);
         //listFiles.addCommand(comDelete);
         listFiles.addCommand(comBack);
      }
      Image icon;
      for(int i = 0; i < fileList.size(); ++i) {
         String fileName=((String)fileList.elementAt(i)).toLowerCase();;
         if(fileName.endsWith("/")) {
             icon=imgFolder;
         }else if(fileName.endsWith(".vbm")) {
             icon=imgVBM;
         }else if(fileName.endsWith(".mid")) {
             icon=imgMID;
         }else if(fileName.endsWith(".rms")) {
             icon=imgRMS;
         }else{
             icon=imgDummy;
         }
         listFiles.append((String)fileList.elementAt(i), icon);
      }
      listFiles.setCommandListener(this);
      localDisplay.setCurrent(listFiles);
   }
   
   public void commandAction(Command com, Displayable disp) {
      if(disp == listLocal) {
         selInd = listLocal.getSelectedIndex();
         if(hasAnySongs()){
            int jay = ((Integer) songIndexList.elementAt(selInd)).intValue();
            if(com == comOpen){
               localSong = defPack.RecStoreHandler.songGET(jay);
               localSeqMidlet.songLoad(localSong);
            }
            if(com == comDelete){
               defPack.RecStoreHandler.songDelete(jay);
               listLocal();
            }
            if(com == comExpVbm){
               selMenu = VBMexport;
               listFiles();
            }
            if(com == comExpMid){
               selMenu = MIDexport;
               listFiles();
            }
            if(com == comExpRms){
               selMenu = RMSexport;
               listFiles();
            }
         }
         if(com == comImpVbm) {
            selMenu = VBMimport;
            listFiles();
         }
         if(com == comImpRms) {
            selMenu = RMSimport;
            listFiles();
         }
         
         if(com == comBack) {
            localSeqMidlet.keepFS(localFS);
            localSeqMidlet.mainMenu(false, false);

         }
         
      } else {
         if(disp == listFiles) {
            int selInd2=listFiles.getSelectedIndex();
            if(com == comSave) {
               promptFileSave();
            }
            if(com==comNewFolder){
               promptFolderCreate();
            }
            if(com==comDelete && listFiles.getSelectedIndex()!=0){
                localFS.delete((String)fileList.elementAt(selInd2));
                listFiles();
            }
            if("".equals(com.getLabel())) {
               if(localFS.isNotRoot() && listFiles.getSelectedIndex() == 0) {
                  localFS.up_one_folder();
                  listFiles();
                  return;
               }
               if(((String)fileList.elementAt(selInd2)).endsWith("/")) {
                  localFS.open_folder((String)fileList.elementAt(selInd2));
                  listFiles();
                  return;
               }
               if(selMenu == VBMimport && ((String)fileList.elementAt(selInd2)).endsWith(".vbm")) {
                  byte[] var4 = ImpSEx.VBMload(localFS.currentPath() + (String)fileList.elementAt(selInd2));
                  RecStoreHandler.array2rms(var4);
                  listLocal();
                  return;
               }
               if(selMenu == RMSimport && ((String)fileList.elementAt(selInd2)).endsWith(".rms")) {
                  ImpSEx.RMSload(localFS.currentPath() + (String)fileList.elementAt(selInd2));
                  localSeqMidlet.emptySong();
                  listLocal();
                  return;
               }
            }
            if(com == comBack) {
               if(localFS.isNotRoot()){
                  localFS.up_one_folder();
                  listFiles();
               }else{
                  listLocal();
               }
               return;
            }
         }
         if(disp == saveForm) {
             if(com == comSave) {
                 commit_file_save();
             }
         }
         if(disp == folderForm) {
             if(com == comSave) {
                 commit_folder_create();
             }
         }
         if(disp == error_form) {
             if(com == comOk) {
                 listFiles();
             }
         }
         if(com == comCancel) {
            listLocal();
         }

      }
   }
   
   private boolean hasAnySongs(){
      return defPack.RecStoreHandler.totalSongNumGET() > 0;
   }
   
   private void songListGET() {
        Vector[] var1 = defPack.RecStoreHandler.songListGET();
        songTitleList = var1[0];
        songIndexList = var1[1];
   }
   
   private void promptFileSave(){
      if(selMenu==MIDexport){
         saveForm = new Form("Save Midi");
         //String songName=songNameGET((byte[])((byte[])recordIDs.elementAt(selInd)));
         String songName=(String) songTitleList.elementAt(selInd);
         saveField = new TextField("Filename:", songName, 255, 0);
      }else if(selMenu==VBMexport){
         saveForm = new Form("Save VBM");
         //String songName=songNameGET((byte[])((byte[])recordIDs.elementAt(selInd)));
         String songName=(String) songTitleList.elementAt(selInd);
         saveField = new TextField("Filename:", songName, 255, 0);
      }else if(selMenu == RMSexport){
         saveForm = new Form("Backup RMS");
         saveField = new TextField("Filename:", "Vibe", 255, 0);
      }
      saveForm.append((Item)saveField);
      saveForm.addCommand(comSave);
      saveForm.addCommand(comCancel);
      saveForm.setCommandListener(this);
      localDisplay.setCurrent(saveForm);
   }
   
   private void promptFolderCreate(){
      folderForm = new Form("Create folder");
      saveField = new TextField("", "", 255, 0);
      folderForm.append((Item)saveField);
      folderForm.addCommand(comSave);
      folderForm.addCommand(comCancel);
      folderForm.setCommandListener(this);
      localDisplay.setCurrent(folderForm);
   }
   
   public void commit_file_save(){
      String fileName=saveField.getString();
      if(selMenu==MIDexport||selMenu==VBMexport){
         byte[] songOut=(byte[])((byte[])recordIDs.elementAt(selInd));
         if(selMenu == MIDexport) {
            try {
               ImpSEx.toFile(ImpSEx.RenderMidi(songOut), localFS.currentPath() + fileName + ".mid");
            } catch (Exception ex) {
               ;
            }
            listLocal();
         }
         if(selMenu == VBMexport) {
            ImpSEx.toFile(songOut, localFS.currentPath() + fileName + ".vbm");
            listLocal();
         }
      }else if(selMenu == RMSexport) {
         ImpSEx.RMSsave(localFS.currentPath() + fileName + ".rms");
         listLocal();
      }
   }
   
   private void commit_folder_create(){
      String folder_name=saveField.getString();
      if(folder_name.length()==0){
         error_form = new Form("Error");
         error_form.append("Name must not be blank!");
         error_form.addCommand(comOk);
         error_form.setCommandListener(this);
         localDisplay.setCurrent(error_form);
      }else if(!localFS.make_dir(folder_name)){
         error_form = new Form("Error");
         error_form.append("Folder already exists!");
         error_form.addCommand(comOk);
         error_form.setCommandListener(this);
         localDisplay.setCurrent(error_form);
      }else{
         listFiles();
      }
   }
   
}
