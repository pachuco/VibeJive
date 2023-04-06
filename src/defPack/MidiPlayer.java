package defPack;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;
import javax.microedition.media.control.MIDIControl;

public final class MidiPlayer implements Runnable, PlayerListener {

   private Player localPlayer;
   private MIDIControl localMIDIControl;
   public int a;
   public boolean b;
   private e e;
   private boolean isPlaying = true;
   private Thread localThread;
   private Song localSong;
   private int i;
   private int j;
   private int k;
   private boolean l;
   private Vector localVector = new Vector();


   public final void a(r var1) {
      localVector.addElement(var1);
   }

   public final int a(boolean var1) {
      try {
         if(localPlayer != null) {
            localPlayer.close();
         }

         localPlayer = Manager.createPlayer("device://midi");
         localPlayer.realize();
         localPlayer.prefetch();
         localMIDIControl = (MIDIControl)localPlayer.getControl("javax.microedition.media.control.MIDIControl");
         if(localMIDIControl != null) {
            b = true;
         }
      } catch (Exception var4) {
         ;
      }

      if(b && var1) {
         if(e == null) {
            e = new e(false);
         }

         a = 2;
      } else {
         try {
            if(localPlayer != null) {
               localPlayer.close();
            }
         } catch (Exception var3) {
            ;
         }

         a = 1;
      }

      return a;
   }

   public final void a(Song var1, int var2, int var3, boolean var4) throws IOException, MediaException {
      if(isPlaying && a != 0) {
         localSong = var1;
         int var10001;
         MidiPlayer var10000;
         if(var2 == -1) {
            var10000 = this;
            var10001 = 0;
         } else {
            var10000 = this;
            var10001 = var2 * var1.f();
         }

         var10000.i = var10001;
         if(var3 == -1) {
            var10000 = this;
            var10001 = Integer.MAX_VALUE;
         } else {
            var10000 = this;
            var10001 = (var3 + 1) * var1.f() - 1;
         }

         var10000.j = var10001;
         l = var4;
         isPlaying = false;
         if(a == 2) {
            a(var1);
            k = i;
            e.a(var1);
            localThread = new Thread(this);
            localThread.setPriority(10);
            localThread.start();
         } else {
            byte[] var6 = (new ToolsSongData(var1)).a(i, j, var4);
            ByteArrayInputStream var7 = new ByteArrayInputStream(var6);
            localPlayer = Manager.createPlayer(var7, "audio/midi");
            localPlayer.addPlayerListener(this);
            localPlayer.realize();
            localPlayer.prefetch();
            if(var4) {
               localPlayer.setLoopCount(-1);
            }

            localPlayer.start();
         }

         for(int var5 = 0; var5 < localVector.size(); ++var5) {
            ((r)localVector.elementAt(var5)).c();
         }

         (new Thread(this)).start();
      }

   }

   public final boolean isPlayerOn() {
      return !isPlaying;
   }

   public final void playToggle() {
      if(!isPlaying) {
         isPlaying = true;
         if(a == 2) {
            e.a();

            try {
               localThread.join();
            } catch (Exception var3) {
               ;
            }
         } else {
            try {
               localPlayer.deallocate();
               localPlayer.close();
            } catch (Exception var2) {
               defPack.ErrorDisplay.error((Throwable)var2);
            }
         }

         for(int var1 = 0; var1 < localVector.size(); ++var1) {
            ((r)localVector.elementAt(var1)).b();
         }
      }

   }

   public final void run() {
      int var6;
      int var12;
      if(Thread.currentThread().getPriority() != 10) {
         while(!isPlaying) {
            int var2;
            int var10;
            if(a == 2) {
               var10 = k;
               var2 = localSong.b(var10);
            } else {
               var2 = (int)(localPlayer.getMediaTime() / 1000L) % localSong.b(j - i + 1) + localSong.b(i);
               var10 = localSong.c(var2);
            }

            int var11 = var2 / '\uea60';
            int var4 = var2 / 1000 % 60;
            var12 = var2 % 100 / 10;

            for(var6 = 0; var6 < localVector.size(); ++var6) {
               ((r)localVector.elementAt(var6)).a(var10, var11, var4, var12);
            }

            try {
               Thread.sleep(100L);
            } catch (InterruptedException var8) {
               ;
            }
         }

      } else {
         long var1 = (long)('\uea60' / (localSong.tempo * 4) / localSong.b) - 5L;

         try {
            e.a(k);
            long var3 = System.currentTimeMillis();

            while(!isPlaying) {
               byte[] var5;
               if((var5 = e.b()) == null) {
                  playToggle();
               } else {
                  var6 = 0;

                  while(var6 < var5.length) {
                     localMIDIControl.shortMidiEvent(var5[var6++] & 255, var5[var6++] & 255, var5[var6++] & 255);
                  }

                  ++k;
                  if(k > j) {
                     k = i;
                     if(!l) {
                        isPlaying = true;
                     }
                  }

                  e.a(k);
               }

               long var13;
               if((var13 = (var3 += var1) - System.currentTimeMillis()) > 0L) {
                  Thread.sleep(var13);
               }
            }

            c();

            for(var12 = 0; var12 < localVector.size(); ++var12) {
               ((r)localVector.elementAt(var12)).b();
            }

         } catch (Exception var9) {
            ;
         }
      }
   }

   private void a(Song var1) {
      Channel[] var2 = var1.a();

      for(int var3 = 0; var3 < var2.length; ++var3) {
         a(var2[var3]);
      }

   }

   public final void a(Channel var1) {
      a(var1.a, var1.b);
   }

   public final void a(int var1, int var2) {
      if(a == 2 && var1 != 9) {
         localMIDIControl.shortMidiEvent(192 + var1, var2, 0);
      }

   }

   public final void b(int var1, int var2) {
      if(a == 2) {
         a(var1, var2);
         localMIDIControl.shortMidiEvent(144 + var1, 60, 127);

         try {
            Thread.sleep(500L);
         } catch (Exception var11) {
            ;
         }

         localMIDIControl.shortMidiEvent(128 + var1, 60, 127);
      } else {
         if(isPlaying) {
            isPlaying = false;
            Song var3 = new Song("");
            Channel var4;
            (var4 = new Channel("")).a(var1);
            var4.b(var2);
            var3.b(var4);
            RiffTools var5;
            (var5 = new RiffTools("")).b(1);
            aj var6;
            (var6 = aj.a(60, 127, 3)).a = 1;
            var5.b(var6);
            j var7;
            (var7 = new j(var4)).b = var5;
            var4.a(var7);

            try {
               byte[] var9 = (new ToolsSongData(var3)).a(0, 4, false);
               ByteArrayInputStream var10 = new ByteArrayInputStream(var9);
               localPlayer = Manager.createPlayer(var10, "audio/midi");
               localPlayer.addPlayerListener(this);
               localPlayer.realize();
               localPlayer.prefetch();
               localPlayer.start();
               return;
            } catch (Exception var12) {
               defPack.ErrorDisplay.error((Throwable)var12);
               isPlaying = false;
            }
         }

      }
   }

   private void c() {
      for(int var1 = 0; var1 < 16; ++var1) {
         localMIDIControl.shortMidiEvent(176 + var1, 123, 0);
      }

   }

   public final void playerUpdate(Player var1, String var2, Object var3) {
      if(var2 == "endOfMedia" && !l) {
         isPlaying = true;

         try {
            var1.deallocate();
            var1.close();
         } catch (Exception var5) {
            defPack.ErrorDisplay.error((Throwable)var5);
         }

         for(int var4 = 0; var4 < localVector.size(); ++var4) {
            ((r)localVector.elementAt(var4)).b();
         }
      }

   }
}
