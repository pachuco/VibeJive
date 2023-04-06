package defPack;
import cust.TouchyMcGrope;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import midlet.SeqMidlet;

public final class SeqRiffEdit extends Canvas implements CommandListener, r {

   private static Command comListTrk = new Command("Tracks", Command.BACK, 1);
   private static Command comEdNoteLngth = new Command("Note length", Command.SCREEN, 31);
   private static Command comEdNoteVol = new Command("Note volume", Command.SCREEN, 32);
   private static Command comDrumRowVol = null;
   private static Command comNoteUp = new Command("Note up", Command.SCREEN, 27);
   private static Command comNoteDown = new Command("Note down", Command.SCREEN, 28);
   private static Command comOctUp = new Command("Octave up", Command.SCREEN, 29);
   private static Command comOctDown = new Command("Octave down", Command.SCREEN, 30);
   private Display localDisplay;
   private SeqMidlet localSeqMidlet;
   private SeqArranger localSeqArranger;
   private MidiPlayer localMidiPlayer;
   private Song localSong;
   private RiffTools localSeqRiffTransp;
   private int n;
   private int o = 4;
   private int screen_height;
   private int screen_width;
   private int font_height;
   private int font_width;
   private int u;
   private int v;
   private int w;
   private int x;
   private int unit_width;
   private int unit_height;
   private int z;
   private int C = 8;
   private int D;
   private int E;
   private int F;
   private int G;
   private boolean H = false;
   private int I;
   private int J;
   private boolean boolNoteVol;
   private boolean boolDrumRowVol;
   private boolean any_volume;
   private boolean boolDrumEditor;
   private int M;
   private int N;
   private int O;
   private int P;
   private int Q;
   private int R;
   private int S;
   private int T;
   private int U;
   private int V;
   private aj selected_note;
   private boolean boolNoteLength;
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


   public SeqRiffEdit(Display varDisplay, SeqMidlet varSeqMidlet, SeqArranger varSeqArranger, MidiPlayer varMidiPlayer) {
      localDisplay = varDisplay;
      localSeqMidlet = varSeqMidlet;
      localSeqArranger = varSeqArranger;
      localMidiPlayer = varMidiPlayer;
      varMidiPlayer.a((r)this);
      localTouch= new TouchyMcGrope();
      setCommandListener(this);
      //setFullScreenMode(true);
   }

   public final void a(Song varSong, RiffTools varSeqRiffTransp, boolean var3, int var4) {
      localSong = varSong;
      localSeqRiffTransp = varSeqRiffTransp;
      boolDrumEditor = var3;
      n = var4;
      int var10001;
      SeqRiffEdit var10000;
      if(var3) {
         S = InstrBank.b();
         var10000 = this;
         var10001 = S + InstrBank.c().length - 1;
      } else {
         S = 0;
         var10000 = this;
         var10001 = 127;
      }

      var10000.T = var10001;
      if(varSeqRiffTransp.b() > 0) {
         var10000 = this;
         var10001 = varSeqRiffTransp.a(0).c[0];
      } else if(var3) {
         var10000 = this;
         var10001 = S;
      } else {
         var10000 = this;
         var10001 = 60;
      }

      var10000.V = var10001;
      Q = V - 5;
      if(Q < S) {
         Q = S;
      }
      U = 0;
      R = 0;
      boolNoteLength = false;
      boolNoteVol = false;
      boolDrumRowVol = false;
      I = 127;
      J = 1;
      d();
      menuAppender();
      localSeqMidlet.sideMenuDisplay((Displayable)this);
   }

   private void menuAppender() {
      addCommand(comListTrk);
      if(boolNoteLength) {
         removeCommand(comEdNoteLngth);
      } else if(selected_note != null){
         addCommand(comEdNoteLngth);
      }

      if(comDrumRowVol != null) {
         removeCommand(comDrumRowVol);
      }

      if(selected_note != null) {
         addCommand(comEdNoteVol);
         if(boolDrumEditor) {
            comDrumRowVol = new Command(InstrBank.funDrmShortName(V) + " volume", 1, 33);
            addCommand(comDrumRowVol);
         }
      } else {
         removeCommand(comEdNoteVol);
      }
      addCommand(comNoteUp);
      addCommand(comNoteDown);
      addCommand(comOctUp);
      addCommand(comOctDown);
   }

   private void d() {
      int var1 = U * localSong.b;
      selected_note = null;
      aj[] var2 = localSeqRiffTransp.a();

      for(int var3 = 0; var3 < var2.length; ++var3) {
         if(var2[var3].a() && var2[var3].c[0] == V && var2[var3].a <= var1 && var2[var3].a + var2[var3].b > var1) {
            I = var2[var3].c[1];
            J = var2[var3].b;
            selected_note = var2[var3];
            return;
         }
      }

   }

   private void e() {
      if(selected_note != null) {
         localSeqRiffTransp.a(selected_note);
      } else {
         int var1 = U * localSong.b;
         aj var2;
         (var2 = aj.a(V, I, J)).a = var1;
         int var3;
         if((var3 = localSeqRiffTransp.c(var2)) == -1) {
            var3 = localSeqRiffTransp.b * 16 * localSong.b;
         }

         if(var3 < J) {
            var2.b = var3;
            J = var3;
         }

         localSeqRiffTransp.b(var2);
      }
   }

   private void volumeBar(int x_offset, int y_offset, int var2, int var3, int var4, boolean volEdit, Graphics var6) {
      volume_x2 = var2 - 2;
      volume_y2 = y_offset + (var3 - 2);
      int var7 = (var2 - 4) * var4 / 127;
      var6.setColor(0x000000); //Black
      var6.drawRect(x_offset + 2, y_offset + 5, var2 - 4, var3 - 10);
      var6.drawLine(x_offset + 2 + var7, y_offset + 2, x_offset + 2 + var7, y_offset + (var3 - 2));
      var6.setColor(0xff0000); //Red
      var6.fillRect(x_offset + 3, y_offset + 6, x_offset + (var7 - 1), var3 - 11);
      if(volEdit) {
         var6.setColor(0xff8080); //Pink
         var6.drawRect(x_offset, y_offset, var2, var3);
      }

   }

   private static void sidePiano(Graphics var0, int var1, int var2, int var3, int var4, int var5) {
      var0.setStrokeStyle(0);
      int var6;
      int var10004;
      int var10001;
      Graphics graph;
      int var10003;
      int var10002;
      if((var6 = var1 % 12) != 1 && var6 != 3 && var6 != 6 && var6 != 8 && var6 != 10) {
         if(var6 != 0 && var6 != 5) {
            if(var6 != 4 && var6 != 11) {
               return;
            }

            graph = var0;
            var10001 = var2;
            var10002 = var3;
            var10003 = var2 + var4;
            var10004 = var3;
         } else {
            graph = var0;
            var10001 = var2;
            var10002 = var3 + var5;
            var10003 = var2 + var4;
            var10004 = var3 + var5;
         }
      } else {
         var0.fillRect(var2, var3, var4 / 2, var5);
         graph = var0;
         var10001 = var2 + var4 / 2;
         var10002 = var3 + var5 / 2;
         var10003 = var2 + var4;
         var10004 = var3 + var5 / 2;
      }

      graph.drawLine(var10001, var10002, var10003, var10004);
   }

   private void setVars(Graphics graph) {
      screen_height = getHeight();
      screen_width = getWidth();
      Font var2 = graph.getFont();
      font_height = var2.getHeight();
      font_width = var2.charWidth('H');
      x_grid_offset = font_width * 3 + 5;
      u = 5;
      v = screen_width - (x_grid_offset + 5);
      x = o * 4;
      unit_width = v / x;
      z = unit_width * 4;
      G = var2.stringWidth("00:00.0") + 4;
      F = font_height;
      D = screen_width - G + 2;
      E = screen_height - F;
      H = true;
   }

   private void a(boolean var1) {
      int var10001;
      SeqRiffEdit var10000;
      if(var1) {
         var10000 = this;
         var10001 = Math.min(I + 5, 127);
      } else {
         var10000 = this;
         var10001 = Math.max(I - 5, 0);
      }

      var10000.I = var10001;
      if(boolNoteVol) {
         selected_note.c[1] = (byte)I;
      } else {
         aj[] var2 = localSeqRiffTransp.a();

         for(int var3 = 0; var3 < var2.length; ++var3) {
            if(var2[var3].a() && var2[var3].c[0] == V) {
               var2[var3].c[1] = (byte)I;
            }
         }

      }
   }

   public final void paint(Graphics graph) {
      if(!H) {
         setVars(graph);
      }

      unit_height = boolDrumEditor?font_height - 2:9;
      //unit_height=unit_width;
      y_grid_offset = font_height - 2;
      C = (screen_height - (u + y_grid_offset + font_height)) / unit_height;
      w = u + y_grid_offset + unit_height * C;
      graph.setColor(0xffffff); //White
      graph.fillRect(graph.getClipX(), graph.getClipY(), graph.getClipWidth(), graph.getClipHeight());
      if(graph.getClipWidth() == screen_width && graph.getClipHeight() == screen_height) {
         graph.setColor(0x000000); //Black

         int var2;
         int var3;
         for(var2 = 0; var2 < o; ++var2) {
            var3 = x_grid_offset + z * var2;
            graph.drawString(String.valueOf(R + var2 + 1), var3 + z / 2 - font_width / 2, u, 0);
         }

         for(var2 = 0; var2 < C + 1; ++var2) {
            var3 = w - unit_height * var2;
            graph.setColor(0xb0b0b0); //Gray
            graph.setStrokeStyle(1);
            graph.drawLine(x_grid_offset, var3, x_grid_offset + v, var3);
            if(var2 < C) {
               graph.setColor(0x000000); //Black
               if(boolDrumEditor) {
                  graph.drawString(InstrBank.funDrmShortName(var2 + Q), 1, var3 - unit_height, 0);
               } else {
                  sidePiano(graph, var2 + Q, 1, var3 - unit_height, x_grid_offset - 1, unit_height);
               }
            }
         }

         for(var2 = 0; var2 < x + 1; ++var2) {
            int var10001;
            Graphics varGraph;
            if(var2 % 4 == 0) {
               varGraph = graph;
               var10001 = 0x000000; //Black
            } else {
               varGraph = graph;
               var10001 = 0xb0b0b0; //Gray
            }

            varGraph.setColor(var10001);
            var3 = x_grid_offset + unit_width * var2;
            byte var12;
            if(!boolDrumEditor && var2 == 0) {
               varGraph = graph;
               var12 = 0;
            } else {
               varGraph = graph;
               var12 = 1;
            }

            varGraph.setStrokeStyle(var12);
            graph.drawLine(var3, u + y_grid_offset, var3, w);
         }

         graph.setStrokeStyle(0);
         aj[] var10 = localSeqRiffTransp.a();

         int var4;
         int var5;
         int var6;
         int var7;
         int var8;
         for(var3 = 0; var3 < var10.length; ++var3) {
            if(var10[var3].a()) {
               var5 = (var4 = var10[var3].a / localSong.b - R * 4 * localSong.b) + var10[var3].b / localSong.b;
               if(var4 < x && var5 > 0) {
                  if(var4 < 0) {
                     var4 = 0;
                  }

                  if(var5 > x) {
                     var5 = x;
                  }

                  if((var6 = var10[var3].c[0] - Q) >= 0 && var6 < C) {
                     var7 = w - unit_height * (var6 + 1);
                     var8 = x_grid_offset + unit_width * var4;
                     graph.setColor(0x000000); //Black
                     graph.drawRect(var8, var7, unit_width * (var5 - var4), unit_height);
                     int var9 = Math.min(290 - var10[var3].c[1], 255);
                     graph.setColor(var9, var9, var9);
                     graph.fillRect(var8 + 1, var7 + 1, unit_width * (var5 - var4) - 1, unit_height - 1);
                  }
               }
            }
         }

         var3 = V - Q;
         graph.setColor(0xff0000); //Red
         if(selected_note != null) {
            var5 = (var4 = selected_note.a / localSong.b - R * 4 * localSong.b) + selected_note.b / localSong.b;
            if(var4 < 0) {
               var4 = 0;
            }

            if(var5 > x) {
               var5 = x;
            }

            graph.drawRect(x_grid_offset + unit_width * var4, w - unit_height * (var3 + 1), unit_width * (var5 - var4), unit_height);
            if(boolNoteLength) {
               var6 = x_grid_offset + unit_width * var4 + 2;
               var7 = x_grid_offset + unit_width * var5 - 2;
               var8 = w - unit_height * (var3 + 1) + unit_height / 2;
               graph.drawLine(var6, var8, var7, var8);
               graph.drawLine(var6, var8, var6 + 3, var8 - 3);
               graph.drawLine(var6, var8, var6 + 3, var8 + 3);
               graph.drawLine(var7, var8, var7 - 3, var8 - 3);
               graph.drawLine(var7, var8, var7 - 3, var8 + 3);
            }
         } else {
            var4 = U - R * 4;
            graph.drawRect(x_grid_offset + unit_width * var4, w - unit_height * (var3 + 1), unit_width, unit_height);
         }
         y_grid_end = screen_height - font_height;
         volumeBar(1, y_grid_end, 40, font_height, I, boolNoteVol | boolDrumRowVol, graph);
         graph.setColor(0x000000); //Black
         String var11 = boolDrumEditor?InstrBank.d(V):InstrBank.f(V) + " " + InstrBank.e(V);
         graph.drawString(var11, 41, screen_height - font_height, 0);
         if(localMidiPlayer.isPlayerOn() && M > 0 && M < v) {
            graph.drawLine(M + x_grid_offset, u, M + x_grid_offset, w);
         }
      }else{
          setVars(graph);
          repaint();
      }

      if(localMidiPlayer.isPlayerOn()) {
         graph.setColor(0x0000ff); //Red
         graph.fillRect(D, E, G, F);
         graph.setColor(0xffffff); //Black
         graph.drawString(String.valueOf(N / 10) + N % 10 + ":" + O / 10 + O % 10 + "." + P, D + 2, E, 0);
      }
      if(localTouch.isTouchDevice(this)){
         if(boolNoteVol | boolDrumRowVol){
            localTouch.calibration(localTouch.sens_finedrag, -1);
         }else if(boolNoteLength && selected_note != null){
            localTouch.calibration(unit_width, unit_height*4);
         }else{
            localTouch.calibration(unit_width, unit_height);
         }
      }
   }

   public final void keyRepeated(int varKey) {
      if(getGameAction(varKey) != 8) {
         keyPressed(varKey);
      }

   }

   public final void keyPressed(int varKey) {
      int var10001;
      SeqRiffEdit var10000;
      switch(getGameAction(varKey)) {
      case 1:
         if(!boolNoteVol && !boolDrumRowVol) {
            label89: {
               if(++V > T) {
                  V = S;
                  var10000 = this;
                  var10001 = S;
               } else {
                  if(V - Q != C) {
                     break label89;
                  }

                  var10000 = this;
                  var10001 = Q + 1;
               }

               var10000.Q = var10001;
            }

            d();
            menuAppender();
         } else {
            a(true);
         }

         var10000 = this;
         break;
      case 2:
         if(!boolNoteVol && !boolDrumRowVol) {
            label157: {
               if(boolNoteLength && selected_note != null) {
                  if(selected_note.b <= localSong.b) {
                     break label157;
                  }

                  selected_note.b -= localSong.b;
                  var10000 = this;
                  var10001 = (selected_note.a + (selected_note.b - 1)) / localSong.b;
               } else if(selected_note != null) {
                  var10000 = this;
                  var10001 = (selected_note.a - localSong.b) / localSong.b;
               } else {
                  var10000 = this;
                  var10001 = U - 1;
               }

               var10000.U = var10001;
            }

            if(U < 0) {
               U = localSeqRiffTransp.b * 16 - 1;
               R = localSeqRiffTransp.b * 4 - 4;
            }

            if(U < R * 4) {
               R = U / 4;
            }

            d();
            menuAppender();
         } else {
            a(false);
         }

         var10000 = this;
         break;
      case 3:
      case 4:
      case 7:
      default:
         return;
      case 5:
         if(!boolNoteVol && !boolDrumRowVol) {
            label153: {
               if(boolNoteLength && selected_note != null) {
                  int var3;
                  if((var3 = localSeqRiffTransp.c(selected_note)) == -1) {
                     var3 = localSeqRiffTransp.b * 16 * localSong.b;
                  }

                  if(selected_note.b >= var3) {
                     break label153;
                  }

                  selected_note.b += localSong.b;
                  var10000 = this;
                  var10001 = (selected_note.a + (selected_note.b - 1)) / localSong.b;
               } else if(selected_note != null) {
                  var10000 = this;
                  var10001 = (selected_note.a + selected_note.b) / localSong.b;
               } else {
                  var10000 = this;
                  var10001 = U + 1;
               }

               var10000.U = var10001;
            }

            label111: {
               if(U >= localSeqRiffTransp.b * 16) {
                  U = 0;
                  var10000 = this;
                  var10001 = 0;
               } else {
                  if(U / 4 - R < o) {
                     break label111;
                  }

                  var10000 = this;
                  var10001 = U / 4 - o + 1;
               }

               var10000.R = var10001;
            }

            d();
            menuAppender();
         } else {
            a(true);
         }

         var10000 = this;
         break;
      case 6:
         if(!boolNoteVol && !boolDrumRowVol) {
            label100: {
               if(--V < S) {
                  V = T;
                  var10000 = this;
                  var10001 = T - C + 1;
               } else {
                  if(V >= Q) {
                     break label100;
                  }

                  var10000 = this;
                  var10001 = V;
               }

               var10000.Q = var10001;
            }

            d();
            menuAppender();
         } else {
            a(false);
         }

         var10000 = this;
         break;
      case 8:
         if(boolNoteVol | boolDrumRowVol) {
            boolNoteVol = false;
            boolDrumRowVol = false;
         } else {
            if(!boolNoteLength || selected_note == null) {
               e();
            }

            boolNoteLength = false;
            d();
         }

         menuAppender();
         var10000 = this;
      }

      var10000.repaint();
   }

   public final void commandAction(Command varCommand, Displayable varDisplayable) {
      if(varCommand == comListTrk) {
         localDisplay.setCurrent(localSeqArranger);
      } else if(varCommand == comEdNoteLngth) {
         boolNoteLength = true;
         menuAppender();
         repaint();
      } else if(varCommand == comEdNoteVol) {
         boolNoteVol = true;
         menuAppender();
         repaint();
      } else if(varCommand == comDrumRowVol) {
         boolDrumRowVol = true;
         menuAppender();
         repaint();
         ;
      } else if(varCommand == comNoteUp) {
         //if(l.cc()) {
         if(localSeqRiffTransp.funTranspAccess(1)) {
            menuAppender();
            repaint();
         } else {
            defPack.ErrorDisplay.error("Could not transpose as some notes would go above highest note available");
         }
      } else if(varCommand == comNoteDown) {
         if(localSeqRiffTransp.funTranspAccess(-1)) {
         //if(l.dd()) {
            menuAppender();
            repaint();
         } else {
            defPack.ErrorDisplay.error("Could not transpose as some notes would go below lowest note available");
         }
      } else if(varCommand == comOctUp) {
         if(localSeqRiffTransp.funTranspAccess(12)) {
         //if(l.c()) {
            menuAppender();
            repaint();
         } else {
            defPack.ErrorDisplay.error("Could not transpose as some notes would go above highest note available");
         }
      } else if(varCommand == comOctDown) {
         if(localSeqRiffTransp.funTranspAccess(-12)) {
         //if(l.d()) {
            menuAppender();
            repaint();
         } else {
            defPack.ErrorDisplay.error("Could not transpose as some notes would go below lowest note available");
         }
      } else {
         localSeqMidlet.sideMenuCommand(varCommand, varDisplayable);
      }
   }

   public final void a(int var1, int var2, int var3, int var4) {
      M = ((var1 - n) / localSong.b - R * 4) * unit_width;
      N = var2;
      O = var3;
      P = var4;
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

   protected void pointerDragged(int x, int y) {
      int xk=localTouch.dragX(x);
      int yk=localTouch.dragY(y);
      if(what_touched==grid_touched||any_volume){
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
      any_volume=boolNoteVol||boolDrumRowVol;
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
         }else if(k==TouchyMcGrope.LONG && selected_note!=null){
            commandAction(comEdNoteLngth, (Displayable)null);
         }
      }else if(what_touched==volume_touched && k==TouchyMcGrope.SHORT && selected_note!=null && !any_volume){
          commandAction(comEdNoteVol,(Displayable)null);
      }else if(what_touched==volume_touched && k==TouchyMcGrope.LONG && boolDrumEditor && selected_note != null && !any_volume){
          commandAction(comDrumRowVol,(Displayable)null);
      }else if(what_touched==volume_touched && any_volume){
          keyPressed(getKeyCode(FIRE));
      }
      what_touched=nothing_touched;
   }
   
}
