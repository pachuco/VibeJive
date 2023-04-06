/* This class should fix the asshattery that is 'List'
 * Now it's possible to access menu items by a 'handle'
 * instead of clumsy List.getSelectedIndex().
 * Unfortunatelly, the WTK emulator doesn't exactly like
 * this and breaks attached commands.
 */
package cust;

import java.util.Vector;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;

public final class ListV2 extends List {
    private static Integer nullhandle = new Integer(-1);

    private Vector handleList = new Vector();

    public ListV2(String title, int listType) {
        super(title, listType);
    }
    public ListV2(String title, int listType, String[] stringElements, Image[] imageElements) {
        super(title, listType, stringElements, imageElements);
        //TODO: Make array importer function
    }
    public ListV2(String title, int listType, String[] stringElements, Image[] imageElements, int[] handleElements) {
        super(title, listType, stringElements, imageElements);
        //TODO: Make array importer function
    }
    public int append(String stringPart, Image imagePart, int handlePart) {
        handleList.addElement(new Integer(handlePart));
        return super.append(stringPart, imagePart);
    }
    public int append(String stringPart, Image imagePart) {
        handleList.addElement(nullhandle);
        return super.append(stringPart, imagePart);
    }
    public void delete(int elementNum) {
        handleList.removeElementAt(elementNum);
        super.delete(elementNum);
    }
    public void deleteAll() {
        handleList.removeAllElements();
        super.deleteAll();
    }
    public int getHandle(int elementNum) {
        if(handleList.size()>0){
            Integer intg = (Integer)handleList.elementAt(elementNum);
            return intg.intValue();
        }else{
            return nullhandle.intValue();
        }
    }
    public int getSelectedHandle() {
        if(handleList.size()>0){
            Integer intg = (Integer)handleList.elementAt(super.getSelectedIndex());
            return intg.intValue(); 
        }else{
            return nullhandle.intValue();
        }
    }
    public void insert(int elementNum, String stringPart, Image imagePart) {
        handleList.insertElementAt(nullhandle, elementNum);
        super.insert(elementNum, stringPart, imagePart);
    }

    public void insert(int elementNum, String stringPart, Image imagePart, int handlePart) {
        handleList.insertElementAt(new Integer(handlePart), elementNum);
        super.insert(elementNum, stringPart, imagePart);
    }
    public void set(int elementNum, String stringPart, Image imagePart) {
        handleList.setElementAt(nullhandle, elementNum);
        super.set(elementNum, stringPart, imagePart);
    }
    public void set(int elementNum, String stringPart, Image imagePart, int handlePart) {
        handleList.setElementAt(new Integer(handlePart), elementNum);
        super.set(elementNum, stringPart, imagePart);
    }
}
