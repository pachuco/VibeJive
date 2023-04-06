package cust;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.Alert;
import javax.microedition.rms.RecordStore;

public final class ImpSEx {
    
   private static String songDB="seqdb1.rms";
   
   private static String songNameGET(byte[] var0) {
      try {
         ByteArrayInputStream BA_IS = new ByteArrayInputStream(var0);
         DataInputStream D_IS;
         (D_IS = new DataInputStream(BA_IS)).skip(4L);
         byte[] var2 = new byte[D_IS.readInt()];
         D_IS.readFully(var2);
         D_IS.close();
         BA_IS.close();
         return new String(var2);
      } catch (IOException var3) {
         return "unidentified";
      }
   }
   
   public static byte[] RenderMidi(byte[] var0) throws Exception {
      ByteArrayInputStream BA_IS = new ByteArrayInputStream(var0);
      DataInputStream D_IS = new DataInputStream(BA_IS);
      defPack.Song songVar = new defPack.Song(D_IS);

      try {
         D_IS.close();
         BA_IS.close();
      } catch (IOException ex) {
         ;
      }

      int var5 = songVar.e * songVar.f();
      int var6 = (songVar.f + 1) * songVar.f() - 1;
      return (new defPack.ToolsSongData(songVar)).a(var5, var6, false);
   }

   public static byte[] VBMload(String path) {
      try {
         FileConnection FileCon;
         if(!(FileCon = (FileConnection)Connector.open("file:///" + path, 1)).exists()) {
            return null;
         } else {
            DataInputStream D_IS = FileCon.openDataInputStream();
            byte[] byteArray = new byte[(int)FileCon.fileSize()];
            D_IS.read(byteArray);
            return byteArray;
         }
      } catch (IOException ex) {
         return null;
      }
   }

   public static void toFile(byte[] byteArr, String path) {
      try {
         FileConnection fileCon;
         if((fileCon = (FileConnection)Connector.open("file:///" + path)).exists()) {
            fileCon.truncate(0L);
         } else {
            fileCon.create();
         }

         DataOutputStream D_OS;
         (D_OS = fileCon.openDataOutputStream()).write(byteArr);
         D_OS.close();
      } catch (IOException ex) {
         ;
      }
   }
   
   //public static void toRMS(byte[]byteArr)
   
   public static void RMSsave(String path) {
    try {
         FileConnection fileCon = (FileConnection)Connector.open("file:///" + path, 3);
         if(fileCon.exists()) {
            fileCon.delete();
         }

         fileCon.create();
         DataOutputStream D_OS = fileCon.openDataOutputStream();
         D_OS.writeInt(1380799232);
         if(RecordStore.listRecordStores() != null) {
            //D_OS.writeInt(storeList.length);
            D_OS.writeInt(1);
            //for(int i = 0; i < storeList.length; ++i) {
               RecordStore var5 = RecordStore.openRecordStore(songDB, false);
               D_OS.writeUTF(var5.getName());
               int var6 = var5.getNextRecordID();
               D_OS.writeInt(var6);

               for(int var7 = 1; var7 < var6; ++var7) {
                  try {
                     byte[] var8 = new byte[var5.getRecordSize(var7)];
                     var5.getRecord(var7, var8, 0);
                     D_OS.writeInt(var7);
                     D_OS.writeChar(43);
                     D_OS.writeInt(var8.length);
                     D_OS.write(var8);
                     Object var12 = null;
                  } catch (Exception var9) {
                     D_OS.writeInt(var7);
                     D_OS.writeChar(45);
                  }
               }

               var5.closeRecordStore();
            //}
         } else {
            D_OS.writeInt(0);
         }

         D_OS.close();
         fileCon.close();
      } catch (Exception var10) {
         Alert var2 = new Alert("Exception:");
         var2.setTimeout(-2);
         var2.setString(var10.getMessage());
      }

   }

   public static void RMSload(String path) {
      try {
         FileConnection fileCon = (FileConnection)Connector.open("file:///" + path, 1);
         DataInputStream D_IS = fileCon.openDataInputStream();
         if(D_IS.readInt() == 1380799232) {
            int RecordStoreNumber = D_IS.readInt();
            for(int i = 0; i < RecordStoreNumber; ++i) {
               String recordName = D_IS.readUTF();
               if(recordName.equals(songDB)){
                  try {
                     RecordStore.deleteRecordStore(recordName);
                  } catch (Exception ex) {
                     ;
                  }
               }
               RecordStore recStore = RecordStore.openRecordStore(recordName, true);
               int recordNumber = D_IS.readInt();

               for(int ii = 1; ii < recordNumber; ++ii) {
                  int var9 = D_IS.readInt();
                  char var10 = D_IS.readChar();
                  int var11=0;
                  if(recordName.equals(songDB)){
                     var11 = recStore.addRecord((byte[])null, 0, 0);
                  }
                  if(var10 == 43) {
                     int var12 = D_IS.readInt();
                     byte[] var13 = new byte[var12];
                     D_IS.read(var13);
                     if(recordName.equals(songDB)){
                        recStore.setRecord(var11, var13, 0, var13.length);
                     }
                  } else if(recordName.equals(songDB)){
                     recStore.deleteRecord(var11);
                  }
               }
               recStore.closeRecordStore();
            }
         }

         D_IS.close();
         fileCon.close();
      } catch (Exception var15) {
         Alert var2 = new Alert("Exception:");
         var2.setTimeout(-2);
         var2.setString(var15.getMessage());
      }

   }
}
