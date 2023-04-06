/* Here, several menus are handled. And it's sideMenuDisplay fucking mess.
 * It's hard to decipher and near impossible to edit properlly.
 * This needs to be rewritten ASAP
 */
package defPack;

import service.Category;
import service.OnlineSong;
import midlet.SeqMidlet;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;
//import javax.microedition.lcdui.List;

public final class Menu implements CommandListener, Runnable {

    //private static Command select = new Command("", Command.ITEM, 0);
    private static Command comBack = new Command("Back", Command.BACK, 0);
    private static Command comOpen = new Command("Open", Command.ITEM, 2);
    private static Command comAdd = new Command("Add", Command.SCREEN, 1);
    private static Command comDelete = new Command("Delete", Command.ITEM, 1);
    private static Command comExit = new Command("Exit", Command.EXIT, 0);
    private static Command comLogin = new Command("Login", Command.SCREEN, 1);
    private static Vector g;
    private Display localDisplay;
    private SeqMidlet localSeqMidlet;
    private OnlineArray2Http localOnlineArray2Http;
    private Song localSong;
    //private List menuList;
    private List menuList;
    private int currentMenu;
    private int p;
    private int q;
    private int r;
    private Vector s;
    private Vector t;
    private Vector songTitleList;
    private Vector songIndexList;
    private String w;
    private int x = 0;
    private Image imgDocMus;
    private Image imgFoldMus;
    private Image imgEarthMus;
    private Image imgUsers;
    private Image imgExit;
    private Image imgPhone;
    private Image imgFloppy;
    private Image imgSpeaker;
    private Image imgAbout;
    private Image imgUpdate;
    private OnlineBuddyForm localOnlineBuddyForm;
    //Sure beats lugging strings around, eh??
    private static int nullhandle = -1;
    //menu items
    private static int hNew = 0;
    private static int hOpen = 1;
    private static int hSave = 2;
    private static int hOnline = 3;
    private static int hUpdate = 4;
    private static int hAbout = 5;
    private static int hExit = 6;
    //menus
    private static int mMain = 0;
    private static int m1 = 1;
    private static int mLoadLocal = 2;
    private static int m3 = 3;
    private static int m4 = 4;
    private static int m5 = 5;
    private static int m6 = 6;
    private static int m7 = 7;
    private static int m8 = 8;
    private static int m9 = 9;
    private static int m10 = 10;
    private static int m11 = 11;

    public Menu(SeqMidlet varSeqMid, Song var4, OnlineArray2Http var3) {
        localSeqMidlet = varSeqMid;
        localDisplay = Display.getDisplay(localSeqMidlet);
        localOnlineArray2Http = var3;
        localSong = var4;

        currentMenu = mMain;

        r = defPack.RecStoreHandler.e();

        try {
            imgDocMus = Image.createImage("/images/document_music.png");
            imgFoldMus = Image.createImage("/images/folder_music.png");
            imgEarthMus = Image.createImage("/images/earth_music.png");
            imgUsers = Image.createImage("/images/users2.png");
            imgExit = Image.createImage("/images/exit.png");
            imgPhone = Image.createImage("/images/mobilephone1.png");
            imgFloppy = Image.createImage("/images/disk_blue.png");
            imgSpeaker = Image.createImage("/images/loudspeaker.png");
            imgAbout = Image.createImage("/images/about.png");
        } catch (IOException ex) {
            ;
        }
    }

    public final void menuListAppend() {
        if (currentMenu == mMain) {
            menuList = new List("File menu", 3);
            menuList.append("New", imgDocMus); //cust.langParser.stringGET(3)
            menuList.append("Songs", imgFoldMus);
            if (localSong != null) {
                menuList.append("Save " + localSong.songName, imgFloppy);
            }
            //menuList.append("Online", imgEarthMus, hOnline);
            menuList.append("About", imgAbout);
            menuList.append("Exit vibe", imgExit);
        }

        if (localSong != null || currentMenu != mMain) {
            menuList.addCommand(comBack);
        }
        //menuList.setSelectCommand(select);
        menuList.setCommandListener(this);
        localDisplay.setCurrent(menuList);
    }

    public final void commandAction(Command com, Displayable uselessVar) {
        int selectedIndex = menuList.getSelectedIndex();
        Image selectedImage = menuList.getImage(selectedIndex);
        if (currentMenu == mMain) {
            if ("".equals(com.getLabel())) {
                if (selectedImage == imgDocMus) { //new
                    localSeqMidlet.songNew();
                }
                if (selectedImage == imgFoldMus) { //Open
                    localSeqMidlet.vibeTools();
                    /*
                     currentMenu = mLoadLocal;
                     getSongList();
                     menuListAppend();
                     return;
                     */
                }
                if (selectedImage == imgFloppy) { //save
                    localSeqMidlet.songSave();
                }
                if (selectedImage == imgAbout) { //about
                    localSeqMidlet.aboutScreen();
                }
                if (selectedImage == imgExit) { //exit
                    localSeqMidlet.appExit();
                }
            } else if (com == comBack) {
                localSeqMidlet.setFocus();
            }
        }

    }

    private void getSongList() {
        Vector[] var1 = defPack.RecStoreHandler.songListGET();
        songTitleList = var1[0];
        songIndexList = var1[1];
    }

    public final void run() {

        try {
            int var1 = (int) defPack.RecStoreHandler.getLoginInfo()[0];
            int var12;
            if (currentMenu == m6) {
                new LoadScreen("Fetching categories", localDisplay);
                var12 = -1;
                if (s.size() > 0) {
                    var12 = ((Integer) s.lastElement()).intValue();
                }

                if (p == 0) {
                    g = localOnlineArray2Http.b(var12, var1);
                } else {
                    t = localOnlineArray2Http.a(var12, q, var1);
                    if (t.size() == 0) {
                        defPack.ErrorDisplay.info("Sorry, there is nothing available for download.", menuList);
                        currentMenu = m5;
                        return;
                    }
                }

                menuListAppend();
                return;
            }

            int var5;
            int var7;
            byte[] var8;
            if (currentMenu == m7) {
                if (p != 0) {
                    new LoadScreen("Fetching Songs", localDisplay);
                    Integer var18 = (Integer) s.lastElement();
                    songTitleList = localOnlineArray2Http.a(var18.intValue(), q, var1, x, 10);
                    menuListAppend();
                    return;
                }

                byte[] var16 = defPack.RecStoreHandler.a(localSong);
                LoadScreen var15 = new LoadScreen("Uploading song", localDisplay, var16.length / r + 1);
                Integer var17 = (Integer) s.lastElement();
                var5 = var16.length;
                int var19 = 0;

                while (var5 > 0) {
                    var8 = new byte[var7 = Math.min(r, var5)];
                    System.arraycopy(var16, var16.length - var5, var8, 0, var7);
                    localOnlineArray2Http.a(var17.intValue(), q, localSong.songName, var8, var19 == 0, var1);
                    var5 -= var7;
                    ++var19;
                    var15.setValue(var19);
                }

                localSeqMidlet.songSave();
                return;
            }

            OnlineSong var13;
            if (currentMenu == m8) {
                var12 = menuList.getSelectedIndex();
                if (x > 0) {
                    --var12;
                }

                var13 = (OnlineSong) songTitleList.elementAt(var12);
                LoadScreen var14 = new LoadScreen("Downloading song", localDisplay, var13.c() / r + 1);
                byte[] var6 = new byte[var5 = var13.c()];
                var7 = 0;

                while (var5 > 0) {
                    System.arraycopy(var8 = localOnlineArray2Http.a(var13.a(), r, var7++, var1), 0, var6, var13.c() - var5, var8.length);
                    var5 -= var8.length;
                    var14.setValue(var7);
                }

                ByteArrayInputStream var20 = new ByteArrayInputStream(var6);
                DataInputStream var9 = new DataInputStream(var20);
                Song var10 = new Song(var9);
                var9.close();
                localSeqMidlet.songLoad(var10);
                return;
            }

            if (currentMenu == m10) {
                var12 = menuList.getSelectedIndex();
                if (x > 0) {
                    --var12;
                }

                var13 = (OnlineSong) songTitleList.elementAt(var12);
                new LoadScreen("Exporting to phone", localDisplay);
                String var4 = localOnlineArray2Http.a(var13.a(), var1);
                localSeqMidlet.a(var4);
                return;
            }

            if (currentMenu == m11) {
                new LoadScreen("Fetching chart", localDisplay);
                songTitleList = localOnlineArray2Http.c(var1);
                menuListAppend();
                return;
            }

            if (currentMenu == m9) {
                new LoadScreen("Updating buddies", localDisplay);
                String var2;
                Vector var3;
                if (p == 3) {
                    if ((var2 = localOnlineArray2Http.a(w, var1)) != null) {
                        (var3 = defPack.RecStoreHandler.BuddyListGET()).addElement(var2);
                        defPack.RecStoreHandler.a(var3);
                        menuListAppend();
                        return;
                    }

                    defPack.ErrorDisplay.info("Buddy name not recognised. Please try again.", localOnlineBuddyForm.a);
                    return;
                }

                if (p == 2) {
                    var2 = localOnlineArray2Http.b(w, var1);
                    (var3 = defPack.RecStoreHandler.BuddyListGET()).removeElement(var2);
                    defPack.RecStoreHandler.a(var3);
                    menuListAppend();
                    return;
                }
            }
        } catch (Throwable var11) {
            //user expired
            ;
        }
    }

    public final void b() {
        //TODO: This is trouble
        ;
        try {
            byte var10001;
            Menu var10000;

            var10000 = this;
            var10001 = 4;


            var10000.currentMenu = var10001;
            menuListAppend();
        } catch (Throwable var2) {
            defPack.ErrorDisplay.error(var2);
        }
    }

    public final void goMainMenu() {
        currentMenu = mMain;
        menuListAppend();
    }

    public final void a(String var1) {
        w = var1;
        p = 3;
        (new Thread(this)).start();
    }

    public final void e() {
        menuListAppend();
    }

    public final void a(int var1) {
        r = var1;
        defPack.RecStoreHandler.c(var1);
        menuListAppend();
    }

    public final void f() {
        menuListAppend();
    }

    public final void setOnlineMenu() {
        if (currentMenu == m1) {
            currentMenu = m4;
            menuListAppend();
        } else {
            (new Thread(this)).start();
        }
    }

    private boolean hasAnySongs() {
        return defPack.RecStoreHandler.totalSongNumGET() > 0;
    }
}
