
//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: PO4 Dorm Design 4000
// Files: Main.java
// Course: cs300
//
// Author: Azraf A Rakin
// Email: rakin@wisc.edu
// Lecturer's Name: Gary Dahl
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name: Yu-Tai Chen
// Partner Email: ychen697@wisc.edu
// Lecturer's Name: Gary Dahl
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
// ___ Write-up states that pair programming is allowed for this assignment.
// ___ We have both read and understand the course Pair Programming Policy.
// ___ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates
// strangers, etc do. If you received no outside help from either type of
// source, then please explicitly indicate NONE.
//
// Persons: (identify each person and describe their help in detail)
// Online Sources: (identify each URL and describe their assistance in detail)
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////


import java.util.ArrayList;

/**
 * This is the main method for the Dorm Designer. It starts the graphical interface, and generates
 * the elements such as buttons and the background. Users can load and save their designs and clear
 * it to start over.
 * 
 * @author azraf
 * 
 */
public class Main {
    private PApplet processing;
    private PImage backgroundImage;
    // Max number of furniture that LoadButton will be allowed to load at once.
    private final static int MAX_LOAD_FURNITURE = 100;
    private Furniture[] furniture;
    private ArrayList<DormGUI> guiObjects = new ArrayList<DormGUI>();


    public Main(PApplet processing) {
        this.processing = processing;
        processing.background(100, 150, 250);
        backgroundImage = processing.loadImage("images/background.png");

        processing.image(backgroundImage, processing.width / 2, processing.height / 2);

        // Generate all the Buttons

        guiObjects.add(new CreateFurnitureButton("eruguqrwhfluf ", 50, 24, processing));
        guiObjects.add(new CreateFurnitureButton("Sofa", 150, 24, processing));
        guiObjects.add(new CreateFurnitureButton("Dresser", 250, 24, processing));
        guiObjects.add(new CreateFurnitureButton("Desk", 350, 24, processing));
        guiObjects.add(new CreateFurnitureButton("Sink", 450, 24, processing));
        guiObjects.add(new ClearButton(50, 574, processing));
        guiObjects.add(new SaveButton(650, 24, processing));
        guiObjects.add(new LoadButton(750, 24, processing));

    }

    public static void main(String[] args) {

        Utility.startApplication();

    }

    // THis method allows other classes to find the furniture limit, as it returns the Main Class's
    // private field MAX_LOAD_FURNITURE.
    public static int getFurnitureLimit() {
        return MAX_LOAD_FURNITURE;
    }

    // This method checks if certain keys are pressed and executes an action accordingly
    public void keyPressed() {
        furniture = extractFurnitureFromGUIObjects();

        for (int i = 0; i < furniture.length; i++) {
            if (furniture[i] != null && furniture[i].isMouseOver()) {
                if (processing.key == 'r' || processing.key == 'R') { // Rotate operation

                    furniture[i].rotate();
                    replaceFurnitureInGUIObjects(furniture);
                    break;
                }



                // delete furniture with press of 'D' key
                if (processing.key == 'D' || processing.key == 'd') { // Delete Operation
                    furniture[i] = null;
                    replaceFurnitureInGUIObjects(furniture);
                    break;

                }
            }
        }
    }


    public void update() {
        processing.background(100, 150, 250);
        processing.image(backgroundImage, processing.width / 2, processing.height / 2);
        for (int i = 0; i < guiObjects.size(); i++) {
            guiObjects.get(i).update();
        }

    }

    public void mouseDown() {
        furniture = extractFurnitureFromGUIObjects(); // put all Furniture instances of guiObject
                                                      // ArrayList in furniture Array
        for (int i = 0; i < guiObjects.size(); i++) {
            if (guiObjects.get(i).isMouseOver()) { // Check is mouse is over a particular guiObject

                if (guiObjects.get(i) instanceof LoadButton) { // furnitureArray should reset when
                                                               // load button is clicked
                    furniture = new Furniture[MAX_LOAD_FURNITURE];
                }
                guiObjects.get(i).mouseDown(furniture);
                break;
            }
        }
        replaceFurnitureInGUIObjects(furniture); // Replace existing furniture instances in
                                                 // guiObjects with the instances in the furniture
                                                 // array

    }

    public void mouseUp() {
        for (int i = 0; i < furniture.length; i++) {
            if (furniture[i] != null)
                furniture[i].mouseUp();
        }
    }

    /**
     * This method creates a new Furniture[] for the old mouseDown() methods to make use of. It does
     * so by copying all Furniture references from this.guiObjects into a temporary array of size
     * MAX_LOAD_FURNITURE.
     * 
     * @return that array of Furniture references.
     */
    private Furniture[] extractFurnitureFromGUIObjects() {
        Furniture[] furniture = new Furniture[MAX_LOAD_FURNITURE];
        int nextFreeIndex = 0;
        for (int i = 0; i < guiObjects.size() && nextFreeIndex < furniture.length; i++)
            if (guiObjects.get(i) instanceof Furniture)
                furniture[nextFreeIndex++] = (Furniture) guiObjects.get(i);
        return furniture;
    }

    /**
     * This method first removes all Furniture references from this.guiObjects, and then adds back
     * in all of the non-null references from it's parameter.
     * 
     * @param furniture contains the only furniture that will be left in this.guiObjects after this
     *        method is invoked (null references ignored).
     */
    private void replaceFurnitureInGUIObjects(Furniture[] furniture) {
        for (int i = 0; i < guiObjects.size(); i++)
            if (guiObjects.get(i) instanceof Furniture)
                guiObjects.remove(i--);
        for (int i = 0; i < furniture.length; i++)
            if (furniture[i] != null)
                guiObjects.add(furniture[i]);
    }
}
