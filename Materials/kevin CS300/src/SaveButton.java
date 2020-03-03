
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * This is the class for the Save button. The button saves the current furniture set up in the room,
 * 
 * @author azraf
 */
public class SaveButton extends Button implements DormGUI {
  

    /**
     * This is a constructor for the Save button. It sets to position of the button in the UI and as
     * well as its label.
     * 
     *  @param x  X-position of the button
     * @param y  Y-position of the button
     */
    
    public SaveButton(float x, float y, PApplet processing) {
        position = new float[2];
        super.processing = processing;
        super.label = "Save Room";
        position[0] = x;
        position[1] = y;

        processing.rect(position[0] - super.getWitdh() / 2, position[1] - super.getHeight() / 2,
                        position[0] + super.getWitdh() / 2, position[1] + super.getHeight() / 2);
    }

    public void mouseUp() {

    }

    public void mouseDown(Furniture[] furniture) {

        if (isMouseOver()) { // Change button to darker shade when hovering over it.
            try {
                FileOutputStream fileMaker =
                                new FileOutputStream("." + File.separatorChar + "RoomData.ddd");
                PrintWriter outFS = new PrintWriter(fileMaker);
                for (int i = 0; i < furniture.length; i++) {
                    if (furniture[i] != null && i == 0) {
                        outFS.print(furniture[i].getInformation()); // store information in file
                    } // first line should not start with space
                    else if (furniture[i] != null) {
                        outFS.println();
                        outFS.print(furniture[i].getInformation());
                    } // last line should not end with space
                }
                outFS.close();
            } catch (Exception FileNotFoundException) {
                System.out.println("“WARNING: Could not save room contents to file RoomData.ddd.”");
            }
        }
    }



}
