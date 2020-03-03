
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

  /**
     * This class the main class for buttons. It contains all the the UI and as
     * well as its label.
     * 
     */
public class Button implements DormGUI{
    private static final int WIDTH = 96; //Width of button
    private static final int HEIGHT = 32; //Height of button
    protected PApplet processing;
    protected float[] position;
    protected String label;
     
    public Button() {
        
    }
    public static int getWitdh() {
        return WIDTH;
    }
    
    public static int getHeight() {
        return HEIGHT;
    }
    /**
     * This is a constructor for all buttons. It sets to position of the button in the UI and as
     * well as its label.
     * 
     * @param x X-position of the button
     * @param y Y-position of the button
     */

    public Button(float x, float y, PApplet processing) {
        position = new float[2];
        this.processing = processing;
        label = "Button";
        position[0] = x;
        position[1] = y;
    }
     
    public void update() {
        if (isMouseOver()) { // Change button to darker shade when hovering over it.
            processing.fill(100);
        } else {
            processing.fill(200);
        }
        processing.rect(position[0] - WIDTH / 2, position[1] - HEIGHT / 2, position[0] + WIDTH / 2,
                        position[1] + HEIGHT / 2);

        processing.fill(0);
        processing.text(label, position[0], position[1]);
    }
    
    public void mouseDown(Furniture[] furniture) {
        System.out.println("A button was pressed.");
    }
    public void mouseUp() {}
    
    public boolean isMouseOver() { 
        if (processing.mouseX > (position[0] - WIDTH / 2)
                        && processing.mouseX < (position[0] + WIDTH / 2)
                        && processing.mouseY > (position[1] - HEIGHT / 2)
                        && processing.mouseY < (position[1] + HEIGHT / 2)) {
            return true;
        } else {
            return false;
        }
    }
}
