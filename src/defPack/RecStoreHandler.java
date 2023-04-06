package defPack;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Vector;
import javax.microedition.rms.RecordComparator;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordFilter;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

public final class RecStoreHandler {
   static String songDB="seqdb1.rms";

   public static byte[] a(Song var0) throws Exception {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      DataOutputStream var2 = new DataOutputStream(var1);
      var0.a(var2);
      var2.close();
      return var1.toByteArray();
   }

   public static int remainingRecordStorageGET() {
      try {
         RecordStore recStore;
         int size = (recStore = RecordStore.openRecordStore(songDB, true)).getSizeAvailable() / 1024;
         recStore.closeRecordStore();
         return size;
      } catch (Throwable var2) {
         ErrorDisplay.error(var2);
         return 0;
      }
   }

   public static int totalSongNumGET() {
      try {
         RecordStore var0;
         int var1 = (var0 = RecordStore.openRecordStore(songDB, true)).getNumRecords();
         var0.closeRecordStore();
         return var1;
      } catch (Throwable var2) {
         ErrorDisplay.error(var2);
         return 0;
      }
   }

   public static Song songGET(int var0) {
      try {
         RecordStore rStore = RecordStore.openRecordStore(songDB, true);
         ByteArrayInputStream BA_IS = new ByteArrayInputStream(rStore.getRecord(var0));
         DataInputStream D_IS = new DataInputStream(BA_IS);
         Song varSong;
         (varSong = new Song(D_IS)).d = var0;
         D_IS.close();
         rStore.closeRecordStore();
         return varSong;
      } catch (Throwable var5) {
         ErrorDisplay.error(var5);
         return null;
      }
   }

   public static boolean songSave(Song varSong) {
      try {
         byte[] var1 = a(varSong);
         RecordStore recStore = RecordStore.openRecordStore(songDB, true);
         if(var1.length > recStore.getSizeAvailable()) {
            return false;
         }

         if(varSong.d == 0) {
            int var3 = recStore.addRecord(var1, 0, var1.length);
            varSong.d = var3;
         } else {
            recStore.setRecord(varSong.d, var1, 0, var1.length);
         }

         recStore.closeRecordStore();
      } catch (Throwable var4) {
         ErrorDisplay.error(var4);
      }

      return true;
   }

   public static void songDelete(int var0) {
      try {
         RecordStore var1;
         (var1 = RecordStore.openRecordStore("seqdb1.rms", true)).deleteRecord(var0);
         var1.closeRecordStore();
      } catch (Throwable var2) {
         ErrorDisplay.error(var2);
      }
   }

   public static Vector[] songListGET() {
      Vector var0 = new Vector();
      Vector var1 = new Vector();

      try {
         RecordStore recStore;
         RecordEnumeration recEnum = (recStore = RecordStore.openRecordStore("seqdb1.rms", true)).enumerateRecords((RecordFilter)null, (RecordComparator)null, false);

         while(recEnum.hasNextElement()) {
            int var4 = recEnum.nextRecordId();
            var1.addElement(new Integer(var4));
            if(recEnum.hasPreviousElement()) {
               recEnum.previousRecordId();
            } else {
               recEnum.reset();
            }

            ByteArrayInputStream BA_IS = new ByteArrayInputStream(recEnum.nextRecord());
            DataInputStream D_IS = new DataInputStream(BA_IS);
            var0.addElement(Song.a(D_IS));
            D_IS.close();
         }
        recStore.closeRecordStore();
      } catch (Throwable var7) {
         var7.printStackTrace();
      }

      Vector[] songList;
      (songList = new Vector[2])[0] = var0;
      songList[1] = var1;
      return songList;
   }

   public static long[] getLoginInfo() {
      long[] var0 = (long[])null;

      try {
         RecordStore var1;
         RecordEnumeration recEnum;
         if((recEnum = (var1 = RecordStore.openRecordStore("seqdb2.rms", true)).enumerateRecords((RecordFilter)null, (RecordComparator)null, false)).numRecords() > 0) {
            var0 = new long[3];
            ByteArrayInputStream var3 = new ByteArrayInputStream(recEnum.nextRecord());
            DataInputStream var4 = new DataInputStream(var3);
            var0[0] = var4.readLong();
            var0[1] = var4.readLong();
            var0[2] = var4.readLong();
            var4.close();
         }

         var1.closeRecordStore();
      } catch (Throwable var5) {
         ErrorDisplay.error(var5);
      }

      return var0;
   }

   public static void a(int var0, long var1, long var3) {
      try {
         RecordStore.deleteRecordStore("seqdb2.rms");
         RecordStore var5 = RecordStore.openRecordStore("seqdb2.rms", true);
         ByteArrayOutputStream var6 = new ByteArrayOutputStream();
         DataOutputStream var7;
         (var7 = new DataOutputStream(var6)).writeLong((long)var0);
         var7.writeLong(var1);
         var7.writeLong(var3);
         var7.close();
         byte[] var8 = var6.toByteArray();
         var5.addRecord(var8, 0, var8.length);
         var5.closeRecordStore();
      } catch (Throwable var9) {
         ErrorDisplay.error(var9);
      }
   }

   public static int e() {
      int var0 = 1000;

      try {
         RecordStore var1;
         RecordEnumeration recEnum;
         if((recEnum = (var1 = RecordStore.openRecordStore("seqdb4.rms", true)).enumerateRecords((RecordFilter)null, (RecordComparator)null, false)).numRecords() > 0) {
            ByteArrayInputStream var3 = new ByteArrayInputStream(recEnum.nextRecord());
            DataInputStream var4;
            var0 = (var4 = new DataInputStream(var3)).readInt();
            var4.close();
         }

         var1.closeRecordStore();
      } catch (Throwable var5) {
         ErrorDisplay.error(var5);
      }

      return var0;
   }

   public static void c(int var0) {
      try {
         RecordStore.deleteRecordStore("seqdb4.rms");
         RecordStore var1 = RecordStore.openRecordStore("seqdb4.rms", true);
         ByteArrayOutputStream var2 = new ByteArrayOutputStream();
         DataOutputStream var3;
         (var3 = new DataOutputStream(var2)).writeInt(var0);
         var3.close();
         byte[] var4 = var2.toByteArray();
         var1.addRecord(var4, 0, var4.length);
         var1.closeRecordStore();
      } catch (Throwable var5) {
         ErrorDisplay.error(var5);
      }
   }

   public static Vector BuddyListGET() {
      Vector var0 = new Vector();

      try {
         RecordStore var1;
         RecordEnumeration recEnum = (var1 = RecordStore.openRecordStore("seqdb3.rms", true)).enumerateRecords((RecordFilter)null, (RecordComparator)null, false);

         while(recEnum.hasNextElement()) {
            var0.addElement(new String(recEnum.nextRecord()));
         }

         var1.closeRecordStore();
      } catch (Throwable var3) {
         ErrorDisplay.error(var3);
      }

      return var0;
   }

   public static void a(Vector var0) {
      try {
         if(BuddyListGET().size() > 0) {
            RecordStore.deleteRecordStore("seqdb3.rms");
         }

         RecordStore var1 = RecordStore.openRecordStore("seqdb3.rms", true);

         for(int var2 = 0; var2 < var0.size(); ++var2) {
            byte[] var3 = ((String)var0.elementAt(var2)).getBytes();
            var1.addRecord(var3, 0, var3.length);
         }

         var1.closeRecordStore();
      } catch (Throwable var4) {
         ErrorDisplay.error(var4);
      }
   }
   
   public static Vector songIdListGET(){
      Vector recordIDs = new Vector();
      try {
         RecordStore rStore;
         int var7;
         if((var7 = (rStore = RecordStore.openRecordStore(songDB, false)).getNumRecords()) > 0) {
            RecordEnumeration recEnum = rStore.enumerateRecords((RecordFilter)null, (RecordComparator)null, false);

            for(int i = 0; i < var7; ++i) {
               byte[] var3 = recEnum.nextRecord();
               recordIDs.addElement(var3);
            }
         }
         rStore.closeRecordStore();
      } catch (RecordStoreException ex) {
         ;
      }
      return recordIDs;
   }
   
   public static void array2rms(byte[] byteArr){
      try {
         RecordStore rStore;
         (rStore = RecordStore.openRecordStore(songDB, true)).addRecord(byteArr, 0, byteArr.length);
         rStore.closeRecordStore();
      } catch (RecordStoreException var3) {
         ;
      }
   }
}
