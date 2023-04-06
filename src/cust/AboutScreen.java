package cust;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;
import midlet.SeqMidlet;


public final class AboutScreen implements CommandListener {
    
    private static Command comOK = new Command("OK", Command.OK, 1);
    private static Command comUpdate = new Command("Update check", Command.SCREEN, 1);
    private Display localDisplay;
    private Form localForm;
    private Image imgAbout;
    private SeqMidlet localSeqMidlet;
    
    public AboutScreen(SeqMidlet var1){
        localSeqMidlet = var1;
        localDisplay = Display.getDisplay(localSeqMidlet);
        
        String[] aboutArr={
            "\nVibe (C) Digitime Computer Systems\n",
            "Mod version: "+localSeqMidlet.getAppProperty("MIDlet-Version")+"\n\n",
            "Please visit vibejive.net for songs and stuff.\n\n",
            "Based on mods by DIM@N and Medvedev O.V.t. Severodvinsk\n"
        };
        String about="";
        for (int i=0;i<aboutArr.length;i++){
            about=about+aboutArr[i];
        }
        
        localForm = new Form("About");
        try {
            imgAbout = Image.createImage("/images/about.png");
            ImageItem header= new ImageItem("", imgAbout, ImageItem.LAYOUT_CENTER, "About");
            localForm.append(header);
            
        } catch (Exception ex) {
            ;
        }
        localForm.append(about);
        localForm.addCommand(comOK);
        localForm.addCommand(comUpdate);
        localForm.setCommandListener(this);
        localDisplay.setCurrent(localForm);
    }

     public final void commandAction(Command c, Displayable d) {
        if(c == comOK) {
            localSeqMidlet.mainMenu(false, false);
        }
        if(c == comUpdate){
            localSeqMidlet.updateCheck();
        }
     }
}