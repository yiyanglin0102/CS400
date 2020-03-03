
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

import java.io.FileNotFoundException;

/**
 * This is for all Furniture objects. It stores the type, position, and the rotation of each
 * Furniture
 * 
 * @author azraf
 */
public class Furniture implements DormGUI {
    private PApplet processing;
    private PImage image;
    private float[] position;
    private boolean isDragging;
    private int rotations;
    private String type;

    // initializes the fields of a new bed object positioned in the center of the room.
    public Furniture(String type, PApplet processing) {
        this.processing = processing;
        position = new float[2];
        position[0] = processing.width / 2;
        position[1] = processing.height / 2;
        this.type = type;
        image = processing.loadImage("images/" + type + ".png");

    }

    public String getInformation() { // record information of furniture in correct format
        String details = type + ": " + position[0] + ", " + position[1] + ", " + rotations;
        return details;
    }


    public Furniture(String type, float x, float y, PApplet processing, int rotations)
                    throws Exception {
        this.processing = processing;
        position = new float[2];
        position[0] = x;
        position[1] = y;
        this.type = type;


        image = processing.loadImage("images/" + type + ".png");
        this.rotations = rotations;

    }

    public void position(float x, float y) {
        this.position[0] = x;
        this.position[1] = y;
    }

    public boolean isMouseOver() {
        if (processing.mouseX > (position[0] - image.width / 2)
                        && processing.mouseX < (position[0] + image.width / 2)
                        && processing.mouseY > (position[1] - image.height / 2)
                        && processing.mouseY < (position[1] + image.height / 2)) {
            return true;
        } else {
            return false;
        }

    }
    // used to start dragging the bed, when the mouse is over this bed when it is
    // pressed

    public void mouseDown(Furniture[] furniture) {

        this.isDragging = true;
    }

    // used to indicate that the bed is no longer being dragged
    public void mouseUp() {



        this.isDragging = false;

    }

    // helper method to determine whether the mouse is currently over this bed
    public void update() {
        for (int i = 0; i < position.length; i++) {

            if (position != null) {
                if (image != null) {
                    image = processing.loadImage("images/" + type + ".png");
                    processing.image(image, position[0], position[1], rotations * PApplet.PI / 2);
                    if (isDragging) { // change position as soon as the mouse is moved, and drag
                                      // along
                                      // with the mouse
                        position[0] = processing.mouseX;
                        position[1] = processing.mouseY;
                    }

                }
            }

        }
    }

    public void rotate() {
        this.rotations++;
    }
}
