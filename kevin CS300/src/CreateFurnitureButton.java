
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
     * This is the class for the button to create all the Furniture objects, to set up the room.
     *  
     *  @author azraf
     */

public class CreateFurnitureButton extends Button implements DormGUI {

   
    private String type;

    /**
     * This is a constructor for the CreateFurnitureButton. It sets to position of the button in the
     * UI and as well as its label. Users also specify the type of furniture to create
     * 
     * @param type  The type of furniture that will be placed in the room
     * @param x     X-position of the button
     * @param y     Y-positionn of the button
     *  
     */

    public CreateFurnitureButton(String type, float x, float y, PApplet processing) {
        this.type = type;
        position = new float[2];
        super.processing = processing;
        super.label = "Create " + type;
        this.type = this.type.toLowerCase(); // change the format to lower case, because image files
                                             // of type are in lower case
        position[0] = x;
        position[1] = y;

        processing.rect(position[0] - super.getWitdh() / 2, position[1] - super.getHeight() / 2,
                        position[0] + super.getWitdh() / 2, position[1] + super.getHeight() / 2);
    }

    public void mouseUp() {

    }

    public void mouseDown(Furniture[] furniture) { // create bed with initial position at middle of
                                                   // screen.
        for (int i = 0; i < furniture.length; i++) {
            if (furniture[i] == null) {
                furniture[i] = new Furniture(type, processing);
                break;
            }
        }
    }



}
