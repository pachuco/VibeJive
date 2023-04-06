/* Here, several menus are handled. And it's sideMenuDisplay fucking mess.
 * It's hard to decipher and near impossible to edit properlly.
 * This needs to be rewritten ASAP
 */
package defPack;

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

    public Menu(SeqMidlet varSeqMid, Song var4) {
        localSeqMidlet = varSeqMid;
        localDisplay = Display.getDisplay(localSeqMidlet);
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
        //online functionality
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

    private boolean hasAnySongs() {
        return defPack.RecStoreHandler.totalSongNumGET() > 0;
    }
}
