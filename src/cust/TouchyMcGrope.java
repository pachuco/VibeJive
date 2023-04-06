package cust;

import javax.microedition.lcdui.Canvas;
import java.util.Timer;

public class TouchyMcGrope {
    private static int tap_width = 6;
    private static int tap_duration = 300;
    private static int kuk=-16;
    public static int NONE=0;
    public static int SHORT=1;
    public static int SHORT2X=2;
    public static int LONG=3;
    public static int DRAG_LEFT=4;
    public static int DRAG_RIGHT=5;
    public static int DRAG_UP=6;
    public static int DRAG_DOWN=7;
    public int sens_finedrag = 6;
    public int d1=12;
    public int d2=20;
    private int sens_X;
    private int sens_Y;
    private int X=-1;
    private int Y=-1;
    private int Xf=-1;
    private int Yf=-1;
    private long press_time;
    
    public TouchyMcGrope(){       
        //localCanvas=can;
        //just in case it's actually possible to activate pointerDragged() before pointerPressed()
        sens_X=-1;
        sens_Y=-1;
        X=Xf=Y=Yf=kuk;
    }
    public TouchyMcGrope(int sens_x, int sens_y){
        sens_X=sens_x;
        sens_Y=sens_y;
        X=Xf=Y=Yf=kuk;
    }
    public int dragX(int x){
        if(sens_X>=0){
            if(x>X+sens_X*d1/d2){
                X=x;
                Xf=Yf=kuk;
                press_time=0;
                return DRAG_RIGHT;
            }else if(x<X-sens_X*d1/d2){
                X=x;
                Xf=Yf=kuk;
                press_time=0;
                return DRAG_LEFT;
            }else{
                return NONE;
            }
        }else{
            return NONE;
        }
    }
    
    public int dragY(int y){
        if(sens_Y>=0){
            if(y>Y+sens_Y*d1/d2){
                Y=y;
                Xf=Yf=kuk;
                press_time=0;
                return DRAG_DOWN;
            }else if(y<Y-sens_Y*d1/d2){
                Y=y;
                Xf=Yf=kuk;
                press_time=0;
                return DRAG_UP;
            }else{
                return NONE;
            }
        }else{
            return NONE;
        }
    }
    
    public void press(int x, int y){
        press_time=System.currentTimeMillis();
        X=Xf=x;
        Y=Yf=y;
    }
    
    public int release(int x, int y){
        long release_time=System.currentTimeMillis();
        boolean is_within=x<Xf+tap_width && x>Xf-tap_width && y<Yf+tap_width && y>Yf-tap_width;
        boolean is_intime=release_time-press_time<tap_duration;
        if(is_within&&is_intime){
            return SHORT;
        }else if(is_within&&!is_intime){
            return LONG;
        }else{
            return NONE;
        }
    }
    
    public void calibration(int sens_x, int sens_y){
        sens_X=sens_x;
        sens_Y=sens_y;
    }

    public boolean isTouchDevice(Canvas iCan) {
        return iCan.hasPointerEvents() && iCan.hasPointerMotionEvents();
    }

}
