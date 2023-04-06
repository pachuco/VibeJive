package defPack;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.TextField;

public final class OnlineRegLog implements CommandListener, Runnable {

   private static Command comSubmit = new Command("Submit", Command.OK, 1);
   private static Command comCancel = new Command("Cancel", Command.CANCEL, 1);
   private Menu localMenu;
   private Display localDisplay;
   private boolean regORlog;
   private OnlineArray2Http localArrayThingie;
   private Form localForm;
   private TextField userField;
   private TextField passField;


   public OnlineRegLog(Display var1, boolean var2, Menu var3, OnlineArray2Http var4) {
      localDisplay = var1;
      regORlog = var2;
      localMenu = var3;
      localArrayThingie = var4;
      createForm();
   }

   private void createForm() {
      Form formVar;
      OnlineRegLog ORLvar;
      if(regORlog) {
         ORLvar = this;
         formVar = new Form("Register");
      } else {
         ORLvar = this;
         formVar = new Form("Login");
      }

      ORLvar.localForm = formVar;
      localForm.append((Item)(userField = new TextField("Username", "", 20, 0)));
      localForm.append((Item)(passField = new TextField("Password", "", 20, 0)));
      localForm.addCommand(comSubmit);
      localForm.addCommand(comCancel);
      localForm.setCommandListener(this);
      localDisplay.setCurrent(localForm);
   }

   public final void commandAction(Command var1, Displayable var2) {
      if(var1 == comCancel) {
         localMenu.goMainMenu();
      } else {
         if(var1 == comSubmit) {
            if(userField.getString().length() == 0 || passField.getString().length() == 0) {
               localMenu.a("Username and password must not be blank.");
               return;
            }

            (new Thread(this)).start();
         }

      }
   }

   public final void run() {
      String var1 = regORlog?"Registering":"Logging in";
      new LoadScreen(var1, localDisplay);

      try {
         int var2;
         if((var2 = regORlog?localArrayThingie.register(userField.getString(), passField.getString()):localArrayThingie.login(userField.getString(), passField.getString())) == -1) {
            if(regORlog) {
               defPack.ErrorDisplay.info("The username is already taken. Please choose a different one.", localForm);
            } else {
               defPack.ErrorDisplay.info("Username or password invalid. Please try again.", localForm);
            }
         } else {
            long var3 = localArrayThingie.a(var2);
            RecStoreHandler.a(var2, var3, System.currentTimeMillis());
            if(var3 < 0L) {
               //c.demoAlert();
            }
            RecStoreHandler.a(localArrayThingie.b(var2));
            localMenu.b();
            }
         }catch (Exception var6) {
         defPack.ErrorDisplay.error((Throwable)var6);
         }
      }
   }
