package cust;

import java.io.IOException;
import javax.microedition.media.MediaException;

public class ExceptionHandler{

    public static void ex(Exception ex){
        if(ex instanceof MediaException && ex.getMessage().toLowerCase().indexOf("sounds not allowed") > -1){
            defPack.ErrorDisplay.error("Playback failed. Check phone sound settings.");
        }else if(ex instanceof MediaException){
            //defPack.ErrorDisplay.info("Playback failed :(");
            defPack.ErrorDisplay.error((Throwable)ex);
        }else if(ex instanceof IOException){
            defPack.ErrorDisplay.error((Throwable)ex);
        }else if(ex instanceof Exception){
            defPack.ErrorDisplay.error((Throwable)ex);
        }
    }
}
