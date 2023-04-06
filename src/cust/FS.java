package cust;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.io.file.FileSystemRegistry;

public class FS {

   public String illegal_chars="\\/:*?\"<>|";
   private Vector path_vector = new Vector();

   public final String currentPath() {
      if(path_vector == null) {
         return "";
      } else {
         StringBuffer str_buf = new StringBuffer();

         for(int i = 0; i < path_vector.size(); ++i) {
            str_buf.append((String)path_vector.elementAt(i));
         }
         return str_buf.toString();
      }
   }

   public final void up_one_folder() {
      if(path_vector != null) {
         if(path_vector.size() > 0) {
            path_vector.setSize(path_vector.size() - 1);
         }

      }
   }

   public final void open_folder(String dir_name) {
      path_vector.addElement(dir_name);
   }

   public final boolean isNotRoot() {
      return path_vector.size() > 0;
   }

   public final Vector fileListGET() {
      Vector file_list;
      Enumeration file_enum;
      if(this.path_vector.size() == 0) {
         file_list = new Vector();
         file_enum = FileSystemRegistry.listRoots();

         while(file_enum.hasMoreElements()) {
            file_list.addElement(file_enum.nextElement());
         }

         return file_list;
      } else {
         file_list = new Vector();

         try {
            FileConnection file_con =(FileConnection)Connector.open("file:///" + currentPath(), 1);
            file_enum = file_con.list();

            while(file_enum.hasMoreElements()) {
               file_list.addElement(file_enum.nextElement());
            }
            file_con.close();
            return file_list;
         } catch (IOException ex) {
            System.out.println("list() Error: " + ex);
            return null;
         }
      }
   }
   
   public boolean make_dir(String dir_name) {
        boolean flag = false;
        try {
           FileConnection file_con = (FileConnection)Connector.open("file:///"+currentPath()+dir_name);
           if(!file_con.exists()) {
               file_con.mkdir(); 
               flag=true;
           }
           file_con.close();
        } catch(IOException ex) {
            ;
        }
        return flag;
     }
   
   public void delete(String filedir_name){
       String base_path=currentPath();
       try {
          Enumeration folder_list;
          Vector des_vec = new Vector();
          FileConnection file_con = (FileConnection)Connector.open("file:///"+base_path+filedir_name);
          if(file_con.isDirectory()){
              des_vec.addElement(filedir_name);
          }else{
              file_con.delete();
          }
          while(!des_vec.isEmpty()){
              String destruction_path="";
              for(int i=0; i<des_vec.size(); i++){
                  destruction_path+=(String)des_vec.elementAt(i);
              }
              file_con.setFileConnection("file:///"+base_path+destruction_path);
              folder_list=file_con.list();
              if(!folder_list.hasMoreElements()){
                  des_vec.setSize(des_vec.size() - 1);
                  file_con.delete();
              }
              while(folder_list.hasMoreElements()){
                  filedir_name = (String)folder_list.nextElement();
                  file_con.setFileConnection("file:///"+base_path+destruction_path+filedir_name);
                  if(file_con.isDirectory()){
                      des_vec.addElement(filedir_name);
                      break;
                  }else{
                      file_con.delete();
                  }
              }
          }
       file_con.close();
       }catch (IOException ex){
          ;
       }
   }
   
}
