
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is the class for the Load button. The button load the furniture set up saved in RoomData.ddd
 * file and displays it in the room, as soon as it's clicked.
 * 
 */

public class LoadButton extends Button implements DormGUI {
   

    private String type;

    /**
     * This is a constructor for the Load button. It sets to position of the button in the UI and as
     * well as its label.
     * 
     * @param x X-position of the button
     * @param y Y-position of the button
     */

    public LoadButton(float x, float y, PApplet processing) {
        position = new float[2];
        super.processing = processing;
        super.label = "Load Room";
        position[0] = x;
        position[1] = y;

        processing.rect(position[0] - super.getWitdh() / 2, position[1] - super.getHeight() / 2,
                        position[0] + super.getWitdh() / 2, position[1] + super.getHeight() / 2);

    }



    public void mouseDown(Furniture[] furniture) {

        if (isMouseOver()) {

            try {
                FileInputStream fileReader =
                                new FileInputStream("." + File.separatorChar + "RoomData.ddd");
                Scanner scnr = new Scanner(fileReader);
                String line;
                ArrayList<String> validLines = new ArrayList<String>();

                while (scnr.hasNextLine()) {
                    boolean validLine = true;
                    int colonCount = 0;
                    int commaCount = 0;
                    line = scnr.nextLine().trim();


                    if (line.length() != 0) {
                        for (int i = 0; i < line.length(); i++) { // iterate line, and search for
                                                                  // commas and colons.
                            if (line.charAt(i) == ':') {
                                colonCount++;

                            }
                            if (line.charAt(i) == ',') {
                                commaCount++;
                            }
                            if (colonCount == 0 && commaCount > 0) { // if comma appears before
                                                                     // colon line is declared as in
                                                                     // valid
                                validLine = false;
                            }
                            if (colonCount > 1) { // if more than 1 comma exists line is not valid
                                validLine = false;
                            }
                        }
                        if (commaCount < 2) { // line has to have at least two commas
                            validLine = false;
                        }
                        if (validLine) {
                            validLines.add(line);
                        } else {
                            System.out.println(
                                            "“WARNING: Found incorrectly formatted line in file: "
                                                            + line + "”");
                        }
                        if (validLines.size() > Main.getFurnitureLimit()) {
                            System.out.println("“WARNING: Unable to load more furniture.”");
                        }
                    }

                }

                for (int i = 0; i < furniture.length && i < validLines.size(); i++) {
                    String[] data = new String[4];
                    data = validLines.get(i).split("[:,]"); // Split line with colons and commas

                    try { // attempt to convert strings to appropriate data types
                        this.type = data[0].trim().toLowerCase();
                        float xCord = Float.parseFloat(data[1].trim());
                        float yCord = Float.parseFloat(data[2].trim());
                        int rotations = Integer.parseInt(data[3].trim());

                        try { // object has to be a bed
                              // or sofa
                            furniture[i] = new Furniture(type, xCord, yCord, processing, rotations);

                        } catch (NullPointerException e) {
                            System.out.println("“WARNING: Could not find an image for a furniture"
                                            + " object of type: " + type + "”");

                        }
                    } catch (Exception e) { // Show warnings if data types are not valid
                        System.out.println("“WARNING: Found incorrectly formatted line in file: "
                                        + validLines.get(i) + "”");
                    }
                }



                scnr.close();


            } catch (FileNotFoundException e) { // Throw warning when file doesn't exist.
                System.out.println(
                                "“WARNING: Could not load room contents from file RoomData.ddd.”");
            }

        }
    }

    public void mouseUp() {

    }



}
