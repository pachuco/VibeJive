package defPack;
import cust.TouchyMcGrope;
import java.util.Vector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import midlet.SeqMidlet;

public final class SeqArranger extends Canvas implements CommandListener, r {

   private static Command comNewRiff = new Command("New pattern", Command.SCREEN, 31);
   private static Command comEdRiff = new Command("Edit pattern", Command.SCREEN, 32);
   private static Command comLstRiff = new Command("All patterns", Command.SCREEN, 33);
   private static Command comCopy = new Command("Copy", Command.SCREEN, 41);
   private static Command comCut = new Command("Cut", Command.SCREEN, 42);
   private static Command comReptP = new Command("Paste (repeat)", Command.SCREEN, 43);
   private static Command comCloneP = new Command("Paste (create)", Command.SCREEN, 44);
   private static Command comNewTrk = new Command("New track", Command.SCREEN, 51);
   private static Command comEditInstr = new Command("Track instrument", Command.SCREEN, 52);
   private static Command comEditVol = new Command("Track volume", Command.SCREEN, 52);
   private static Command comDelTrk = new Command("Delete track", Command.SCREEN, 53);
   private static Command comLoopStrt = new Command("Loop start", Command.SCREEN, 61);
   private static Command comLoopEnd = new Command("Loop end", Command.SCREEN, 62);
   private Display localDisplay;
   private SeqMidlet localSeqMidlet;
   private SeqRiffEdit localSeqRiffEdit;
   private Song localSong;
   private MidiPlayer localMidiPlayer;
   private int units_per_row = 8;
   private int t = 5;
   private int u = 0;
   private int v = 0;
   private int w = 0;
   private int x = 0;
   private j selected_riff = null;
   private Channel z = null;
   private boolean A;
   private boolean boolTrkVolume;
   private int C;
   private int D;
   private int E;
   private int F;
   private Vector G = new Vector();
   private Vector H = new Vector();
   private int screen_height;
   private int screen_width;
   private int unit_width;
   private int unit_height;
   private int x_grid_offset;
   private int y_grid_offset;
   private int y_grid_end;
   private int volume_x2;
   private int volume_y2;
   private TouchyMcGrope localTouch;
   private static int nothing_touched=-1;
   private static int stuff_touched=0;
   private static int grid_touched=1;
   private static int volume_touched=2;
   private int what_touched;

   public SeqArranger(Display var1, SeqMidlet var2, MidiPlayer var3) {
      localDisplay = var1;
      localSeqMidlet = var2;
      localMidiPlayer = var3;
      var3.a((r)this);
      localTouch= new TouchyMcGrope();
      setCommandListener(this);
      //setFullScreenMode(true);
   }

   public final void a(Song var1) {
      localSong = var1;
      v = 0;
      x = 0;
      u = 0;
      w = 0;
      e();
      menuAppender();
      localSeqMidlet.sideMenuDisplay((Displayable)this);
   }

   private void menuAppender() {
      if(selected_riff == null && z != null) {
         addCommand(comNewRiff);
         addCommand(comLstRiff);
      } else {
         removeCommand(comNewRiff);
         removeCommand(comLstRiff);
      }

      if(selected_riff != null) {
         addCommand(comEdRiff);
      } else {
         removeCommand(comEdRiff);
      }

      if(!G.isEmpty()) {
         addCommand(comCopy);
         addCommand(comCut);
      } else {
         removeCommand(comCopy);
         removeCommand(comCut);
      }

      if(!H.isEmpty()) {
         addCommand(comReptP);
         addCommand(comCloneP);
      } else {
         removeCommand(comReptP);
         removeCommand(comCloneP);
      }

      label42: {
         Command com;
         SeqArranger seqArr;
         if(z != null) {
            if(z.a != 9) {
               addCommand(comEditInstr);
            } else {
               removeCommand(comEditInstr);
            }

            addCommand(comDelTrk);
            if(!boolTrkVolume) {
               addCommand(comEditVol);
               break label42;
            }

            seqArr = this;
            com = comEditVol;
         } else {
            removeCommand(comEditInstr);
            seqArr = this;
            com = comDelTrk;
         }

         seqArr.removeCommand(com);
      }

      addCommand(comNewTrk);
      if(localSong.b() > 0) {
         addCommand(comLoopStrt);
         addCommand(comLoopEnd);
      } else {
         removeCommand(comLoopStrt);
         removeCommand(comLoopEnd);
      }
   }

   private void e() {
      selected_riff = null;
      z = null;
      if(localSong.b() > 0) {
         Channel var1;
         j[] var2 = (var1 = localSong.a(x)).a();

         for(int var3 = 0; var3 < var2.length; ++var3) {
            if(var2[var3].a <= v && var2[var3].a + var2[var3].b.b > v) {
               selected_riff = var2[var3];
               z = var1;
               return;
            }
         }

         z = var1;
      }

   }

   private void volumeBar(int x_offset, int y_offset, int var2, int var3, int var4, boolean volEdit, Graphics graph) {
      volume_x2 = var2 - 2;
      volume_y2 = y_offset + (var3 - 2);
      int var7 = (var2 - 4) * var4 / 127;
      graph.setColor(0x000000); //Black
      graph.drawRect(x_offset + 2, y_offset + 5, var2 - 4, var3 - 10);
      graph.drawLine(x_offset + 2 + var7, y_offset + 2, x_offset + 2 + var7, y_offset + (var3 - 2));
      graph.setColor(0xff0000); //Red
      graph.fillRect(x_offset + 3, y_offset + 6, x_offset + (var7 - 1), var3 - 11);
      if(volEdit) {
         graph.setColor(0xff8080); //Pink
         graph.drawRect(x_offset, y_offset, var2, var3);
      }

   }

   public final void paint(Graphics graph) {
      screen_height = graph.getClipHeight();
      screen_width = graph.getClipWidth();
      int clipX = graph.getClipX();
      int clipY = graph.getClipY();
      graph.setColor(0xffffff); //White
      graph.fillRect(clipX, clipY, screen_width, screen_height);
      Font var6= graph.getFont();
      int var7 = var6.getHeight();
      int var8 = var6.charWidth('H');
      x_grid_offset = var8*2;
      y_grid_offset = 5 + unit_height;
      y_grid_end = screen_height - var7 * 2;
      int var11 = screen_width - (x_grid_offset + 5);
      unit_width = var11 / units_per_row;
      unit_height = var7 - 1;
      int var14 = (C / localSong.b - u * 16) * unit_width / 16;
      t = (screen_height - 5 - 3 * var7) / unit_height;
      int var15 = localSong.e - u;
      int var16 = localSong.f - u;
      if(var15 < units_per_row) {
         if(var15 < 0) {
            var15 = 0;
         }

         if(var16 >= 0) {
            if(var16 >= units_per_row) {
               var16 = units_per_row - 1;
            }

            graph.setColor(0xb0ffb0); //Light Green
            graph.fillRect(x_grid_offset + var15 * unit_width, 5, unit_width * (var16 - var15 + 1), unit_height);
         }
      }

      graph.setColor(0x000000); //Black

      int var17;
      for(var16 = 0; var16 < units_per_row; ++var16) {
         var17 = x_grid_offset + unit_width * var16;
         graph.drawString(String.valueOf(u + var16 + 1), var17 + unit_width / 2 - var8 / 2, 5, 0);
      }

      if(localSong.b() > 0) {
         if((var16 = localSong.b() - w) > t) {
            var16 = t;
         }

         graph.setStrokeStyle(1);

         int var18;
         for(var17 = 0; var17 < var16 + 1; ++var17) {
            var18 = 5 + unit_height * (var17 + 1);
            graph.setColor(0xb0b0b0); //Gray
            graph.drawLine(x_grid_offset, var18, x_grid_offset + unit_width * units_per_row, var18);
            if(var17 < var16) {
               graph.setColor(0x000000); //Black
               graph.drawString(String.valueOf(var17 + w + 1), 1, var18, 0);
            }
         }

         graph.setColor(0xb0b0b0); //Gray

         for(var17 = 0; var17 < units_per_row + 1; ++var17) {
            var18 = x_grid_offset + unit_width * var17;
            graph.drawLine(var18, y_grid_offset, var18, 5 + unit_height * (var16 + 1));
         }

         graph.setStrokeStyle(0);

         int var21;
         int var20;
         int var01;
         Graphics varGraph;
         for(var17 = 0; var17 < var16; ++var17) {
            j[] var19 = localSong.a(var17 + w).a();
            var20 = 5 + unit_height * (var17 + 1);

            for(var21 = 0; var21 < var19.length; ++var21) {
               int var22;
               int var23 = (var22 = var19[var21].a - u) + var19[var21].b.b;
               if(var22 < units_per_row && var23 > 0) {
                  if(var22 < 0) {
                     var22 = 0;
                  }

                  if(var23 > units_per_row) {
                     var23 = units_per_row;
                  }

                  int var24 = x_grid_offset + unit_width * var22;
                  int var25 = unit_width * (var23 - var22);
                  graph.setColor(0x000000); //Black
                  graph.drawRect(var24, var20, var25, unit_height);
                  if(G.contains(var19[var21])) {
                     varGraph = graph;
                     var01 = 0x5050ff; //Blue
                  } else {
                     varGraph = graph;
                     var01 = 0xe0e0e0; //Light gray
                  }

                  varGraph.setColor(var01);
                  graph.fillRect(var24 + 1, var20 + 1, var25 - 1, unit_height - 1);
               }
            }
         }

         if(!boolTrkVolume) {
            graph.setColor(0xff0000); //Red
            var17 = x - w;
            int var10003;
            int var10002;
            if(selected_riff == null) {
               var18 = v - u;
               varGraph = graph;
               var01 = x_grid_offset + unit_width * var18;
               var10002 = 5 + unit_height * (var17 + 1);
               var10003 = unit_width;
            } else {
               int var26 = (var18 = selected_riff.a - u) + selected_riff.b.b;
               if(var18 < 0) {
                  var18 = 0;
               }

               if(var26 > units_per_row) {
                  var26 = units_per_row;
               }

               varGraph = graph;
               var01 = x_grid_offset + unit_width * var18;
               var10002 = 5 + unit_height * (var17 + 1);
               var10003 = unit_width * (var26 - var18);
            }
            //selection box
            varGraph.drawRect(var01, var10002, var10003, unit_height);
         }

         volumeBar(1, y_grid_end, 40, var7, z.volume, boolTrkVolume, graph);
         String var28 = z.a == 9?"Drums":InstrBank.a(z.b);
         graph.setColor(0x000000); //Black
         graph.drawString(var28, 45, screen_height - var7 * 2, 0);
         if(selected_riff != null) {
            String var27 = selected_riff.b.a;
            graph.drawString(var27, 1, screen_height - var7, 0);
         }

         if(localMidiPlayer.isPlayerOn()) {
            var18 = var6.stringWidth("00:00.0") + 4;
            var20 = screen_width - var18 + 2;
            var21 = screen_height - var7;
            graph.setColor(0x0000ff);//Red
            graph.fillRect(var20, var21, var18, var7);
            graph.setColor(0xffffff); //White
            graph.drawString(String.valueOf(D / 10) + D % 10 + ":" + E / 10 + E % 10 + "." + F, var20 + 2, var21, 0);
            if(var14 > 0 && var14 < var11) {
               graph.setColor(0x000000);//Black
               graph.drawLine(var14 + x_grid_offset, 5, var14 + x_grid_offset, 5 + unit_height * (var16 + 1));
            }
         }
      }
      if(localTouch.isTouchDevice(this)){
         if(boolTrkVolume){
            localTouch.calibration(localTouch.sens_finedrag, -1);
         }else{
            localTouch.calibration(unit_width, unit_height);
         }
      }
   }

   public final void keyRepeated(int var1) {
      if(getGameAction(var1) != 8) {
         keyPressed(var1);
      }

   }

   public final void keyPressed(int key) {
      if(localSong.b() > 0) {
         int var3;
         int var10001;
         SeqArranger var10000;
         switch(getGameAction(key)) {
         case 1:
            if(boolTrkVolume) {
               var3 = z.volume;
               var3 += 5;
               if(var3 > 127) {
                  var3 = 127;
               }

               z.c(var3);
            } else {
               label95: {
                  if(--x < 0) {
                     x = localSong.b() - 1;
                     if(x < t) {
                        break label95;
                     }

                     var10000 = this;
                     var10001 = x - t + 1;
                  } else {
                     if(x >= w) {
                        break label95;
                     }

                     var10000 = this;
                     var10001 = x;
                  }

                  var10000.w = var10001;
               }

               e();
               menuAppender();
            }

            repaint();
            return;
         case 2:
            if(boolTrkVolume) {
               var3 = z.volume;
               var3 -= 5;
               if(var3 < 0) {
                  var3 = 0;
               }

               z.c(var3);
            } else {
               if(selected_riff != null) {
                  var10000 = this;
                  var10001 = selected_riff.a;
               } else {
                  var10000 = this;
                  var10001 = v;
               }

               var10000.v = var10001 - 1;
               if(v < 0) {
                  v = 0;
               }

               if(v < u) {
                  u = v;
               }

               e();
               menuAppender();
            }

            repaint();
            return;
         case 3:
         case 4:
         case 7:
         default:
            break;
         case 5:
            if(boolTrkVolume) {
               var3 = z.volume;
               var3 += 5;
               if(var3 > 127) {
                  var3 = 127;
               }

               z.c(var3);
            } else {
               int var10002;
               if(selected_riff != null) {
                  var10000 = this;
                  var10001 = selected_riff.a;
                  var10002 = selected_riff.b.b;
               } else {
                  var10000 = this;
                  var10001 = v;
                  var10002 = 1;
               }

               var10000.v = var10001 + var10002;
               if(v - u >= units_per_row) {
                  u = v - units_per_row + 1;
               }

               e();
               menuAppender();
            }

            repaint();
            return;
         case 6:
            if(boolTrkVolume) {
               var3 = z.volume;
               var3 -= 5;
               if(var3 < 0) {
                  var3 = 0;
               }

               z.c(var3);
            } else {
               label85: {
                  if(++x == localSong.b()) {
                     x = 0;
                     var10000 = this;
                     var10001 = 0;
                  } else {
                     if(x - w != t) {
                        break label85;
                     }

                     var10000 = this;
                     var10001 = w + 1;
                  }

                  var10000.w = var10001;
               }

               e();
               menuAppender();
            }

            repaint();
            return;
         case 8:
            if(boolTrkVolume) {
               boolTrkVolume = false;
               menuAppender();
               repaint();
               return;
            }

            k();
         }
      }

   }

   private void editRiff() {
      if(selected_riff != null) {
         if(localSeqRiffEdit == null) {
            localSeqRiffEdit = new SeqRiffEdit(localDisplay, localSeqMidlet, this, localMidiPlayer);
         }

         localSeqRiffEdit.a(localSong, selected_riff.b, z.a == 9, selected_riff.a * 16 * localSong.b);
         localDisplay.setCurrent(localSeqRiffEdit);
      }

   }

   private void newRiff() {
      String var1 = "Pat. " + (localSong.number_of_riffs() + 1);
      int var2 = z.d(v);
      new InfoEditRiffCreate(this, localDisplay, var2, var1);
   }

   private void lstRiff() {
      int var1 = z.d(v);
      (new RiffLoadSelect(localDisplay, localMidiPlayer, localSong, this, var1)).a();
   }

   private void paste(boolean var1) {
      int var2 = Integer.MAX_VALUE;
      int var3 = Integer.MAX_VALUE;

      int var4;
      for(var4 = 0; var4 < H.size(); ++var4) {
         j var5;
         if((var5 = (j)H.elementAt(var4)).a < var2) {
            var2 = var5.a;
         }

         int var6;
         if((var6 = localSong.a(var5.c)) < var3) {
            var3 = var6;
         }
      }

      var4 = localSong.a(z);

      int var10;
      j var11;
      for(var10 = 0; var10 < H.size(); ++var10) {
         var11 = (j)H.elementAt(var10);
         boolean var7 = false;
         int var8;
         if((var8 = var4 + (localSong.a(var11.c) - var3)) < localSong.b()) {
            var7 = localSong.a(var8).d(v + (var11.a - var2)) >= var11.b.b;
         }

         if(!var7) {
            Alert var9;
            (var9 = new Alert("Paste patterns", "Sorry, the copied pattern(s) do not fit here", (Image)null, (AlertType)null)).setTimeout(-2);
            localDisplay.setCurrent(var9);
            return;
         }
      }

      for(var10 = 0; var10 < H.size(); ++var10) {
         var11 = (j)H.elementAt(var10);
         Channel var12 = localSong.a(var4 + (localSong.a(var11.c) - var3));
         j var13;
         (var13 = new j(var12)).a(v + (var11.a - var2));
         RiffTools var10001;
         j var10000;
         if(var1) {
            var10000 = var13;
            var10001 = var11.b;
         } else {
            RiffTools var14 = defPack.RiffTools.a(var11.b);
            localSong.a(var14);
            var10000 = var13;
            var10001 = var14;
         }

         var10000.b = var10001;
         var12.a(var13);
      }

      e();
      menuAppender();
      repaint();
   }

   private void funCut() {
      H.removeAllElements();

      for(int var1 = 0; var1 < G.size(); ++var1) {
         j var2;
         (var2 = (j)G.elementAt(var1)).c.b(var2);
         H.addElement(var2);
      }

      G.removeAllElements();
      e();
      menuAppender();
      repaint();
   }

   private void funDelTrk() {
      j[] var1 = z.a();

      for(int var2 = 0; var2 < var1.length; ++var2) {
         G.removeElement(var1[var2]);
         H.removeElement(var1[var2]);
      }

      localSong.c(z);
      if(localSong.b() == 0) {
         x = 0;
         v = 0;
      } else if(x >= localSong.b()) {
         x = localSong.b() - 1;
      }

      e();
      menuAppender();
      repaint();
   }

   private void b(boolean var1) {
      int var2;
      int var3;
      boolean var10001;
      SeqArranger var10000;
      if(var1) {
         var2 = localSong.a(false);
         var3 = localSong.a(true);
         if(var2 == -1) {
            Alert var4;
            if(var3 == -1) {
               (var4 = new Alert("New Track", "All midi channels are allocated", (Image)null, (AlertType)null)).setTimeout(-2);
               localDisplay.setCurrent(var4, this);
               return;
            }

            (var4 = new Alert("New Track", "Only the Drum track is available", (Image)null, (AlertType)null)).setTimeout(-2);
            localDisplay.setCurrent(var4, this);
         }

         var10000 = this;
         var10001 = false;
      } else {
         var2 = z.a;
         var3 = -1;
         var10000 = this;
         var10001 = true;
      }

      var10000.A = var10001;
      InstrSelector var6 = new InstrSelector(this, localDisplay, localMidiPlayer, var2);
      if(var2 != -1) {
         label37: {
            InstrSelector var5;
            if(localMidiPlayer.a == 2) {
               var5 = var6;
               var10001 = localMidiPlayer.isPlayerOn();
            } else {
               if(localMidiPlayer.isPlayerOn()) {
                  break label37;
               }

               var5 = var6;
               var10001 = false;
            }

            var5.a(var10001);
         }
      }

      var6.a(var2 != -1, var3 != -1);
      var6.a();
   }

   private void b(int var1, int var2) {
      boolean var3 = var1 == -1;
      Channel var4;
      (var4 = new Channel("Chan " + (var2 + 1))).a(var2);
      if(!var3) {
         var4.b(var1);
      }

      localSong.b(var4);
      x = localSong.b() - 1;
      if(x - w >= t) {
         ++w;
      }

      e();
      menuAppender();
      localDisplay.setCurrent(this);
   }

   private void k() {
      if(selected_riff != null) {
         if(G.contains(selected_riff)) {
            G.removeElement(selected_riff);
         } else {
            G.addElement(selected_riff);
         }
      } else {
         G.removeAllElements();
      }

      menuAppender();
      repaint();
   }

   private void funCopy() {
      H.removeAllElements();

      for(int var1 = 0; var1 < G.size(); ++var1) {
         H.addElement(G.elementAt(var1));
      }

      G.removeAllElements();
      menuAppender();
      repaint();
   }

   private void funSetLoopStart() {
      int var10001;
      Song var10000;
      if(selected_riff != null) {
         var10000 = localSong;
         var10001 = selected_riff.a;
      } else {
         var10000 = localSong;
         var10001 = v;
      }

      var10000.f(var10001);
      repaint();
   }

   private void funSetLoopEnd() {
      int var10001;
      Song var10000;
      if(selected_riff != null) {
         var10000 = localSong;
         var10001 = selected_riff.a + selected_riff.b.b - 1;
      } else {
         var10000 = localSong;
         var10001 = v;
      }

      var10000.e(var10001);
      repaint();
   }

   public final void commandAction(Command var1, Displayable var2) {
      if(var1 == comNewTrk) {
         b(true);
      } else if(var1 == comEditInstr) {
         b(false);
      } else if(var1 == comEditVol) {
         boolTrkVolume = true;
         menuAppender();
         repaint();
      } else if(var1 == comNewRiff) {
         newRiff();
      } else if(var1 == comLstRiff) {
         lstRiff();
      } else if(var1 == comCopy) {
         funCopy();
      } else if(var1 == comCloneP) {
         paste(false);
      } else if(var1 == comReptP) {
         paste(true);
      } else if(var1 == comCut) {
         funCut();
      } else if(var1 == comEdRiff) {
         editRiff();
      } else if(var1 == comDelTrk) {
         funDelTrk();
      } else if(var1 == comLoopStrt) {
         funSetLoopStart();
         repaint();
      } else if(var1 == comLoopEnd) {
         funSetLoopEnd();
         repaint();
      } else {
         localSeqMidlet.sideMenuCommand(var1, var2);
      }
   }

   public final void a(int var1, int var2) {
      if(A) {
         if(var1 != -2) {
            z.b(var1);
         }

         localMidiPlayer.a(z);
      } else if(var1 != -2) {
         b(var1, var2);
         localMidiPlayer.a(var2, var1);
         return;
      }

      localDisplay.setCurrent(this);
   }

   public final void a(int var1, String var2) {
      RiffTools var3;
      (var3 = new RiffTools(var2)).b(var1);
      localSong.a(var3);
      j var4;
      (var4 = new j(z)).b = var3;
      var4.a(v);
      z.a(var4);
      selected_riff = var4;
      menuAppender();
      editRiff();
   }

   public final void a() {
      localDisplay.setCurrent(this);
   }

   public final void a(int var1, int var2, int var3, int var4) {
      C = var1;
      D = var2;
      E = var3;
      F = var4;
      if(isShown()) {
         repaint();
         serviceRepaints();
      }

   }

   public final void b() {
      localSeqMidlet.sideMenuDisplay((Displayable)this);
      menuAppender();
      repaint();
   }

   public final void c() {
      localSeqMidlet.sideMenuDisplay((Displayable)this);
      menuAppender();
   }

   public final void a(Song varSong, RiffTools varRiffTransp) {
      if(varSong != null) {
         if(varSong.d != localSong.d) {
            localSong.a(varRiffTransp);
         }

         j var3;
         (var3 = new j(z)).b = varRiffTransp;
         var3.a(v);
         z.a(var3);
         selected_riff = var3;
         menuAppender();
      }

      localDisplay.setCurrent(this);
   }

   protected void pointerDragged(int x, int y) {
      int xk=localTouch.dragX(x);
      int yk=localTouch.dragY(y);
      if(what_touched==grid_touched||boolTrkVolume){
         if(xk==TouchyMcGrope.DRAG_LEFT){
            keyPressed(getKeyCode(LEFT));
         }else if(xk==TouchyMcGrope.DRAG_RIGHT){
            keyPressed(getKeyCode(RIGHT));
         }
      
         if(yk==TouchyMcGrope.DRAG_UP){
            keyPressed(getKeyCode(UP));
         }else if(yk==TouchyMcGrope.DRAG_DOWN){
            keyPressed(getKeyCode(DOWN));
         }
      }

   }

   protected void pointerPressed(int x, int y) {
      repaint();
      if(x>x_grid_offset && x<screen_width && y>y_grid_offset && y<y_grid_end){
          what_touched=grid_touched;
      }else if(x>1 && x<volume_x2 && y>y_grid_end && y<volume_y2){
          what_touched=volume_touched;
      }else{
          what_touched=stuff_touched;
      }
      localTouch.press(x, y);
   }

   protected void pointerReleased(int x, int y) {
      repaint();
      int k=localTouch.release(x, y);
      if(what_touched==grid_touched){
         if(k==TouchyMcGrope.SHORT){
            keyPressed(getKeyCode(FIRE));
         }else if(k==TouchyMcGrope.LONG && selected_riff!=null){
            commandAction(comEdRiff, (Displayable)null);
         }
      }else if(what_touched==volume_touched && !boolTrkVolume){
          commandAction(comEditVol,(Displayable)null);
      }else if(what_touched==volume_touched && boolTrkVolume){
          keyPressed(getKeyCode(FIRE));
      }
      what_touched=nothing_touched;
   }
   
}
