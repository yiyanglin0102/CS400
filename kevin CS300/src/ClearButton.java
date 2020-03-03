/**
 * This is the class for the Clear button. The button clears all the Furnitures in the screen,
 * 
 * @author azraf
 */

public class ClearButton extends Button implements DormGUI {

    /**
     * This is a constructor for the Clear button. It sets to position of the button of the Clear
     * button in the UI and as well as its label.
     * 
     * @param x  X-position of the button
     * @param y  Y-position of the button
     */
    public ClearButton(float x, float y, PApplet processing) {

        position = new float[2];
        super.processing = processing;
        super.label = "Clear Room";
        position[0] = x;
        position[1] = y;

        processing.rect(position[0] - super.getWitdh() / 2, position[1] - super.getHeight() / 2,
                        position[0] + super.getWitdh() / 2, position[1] + super.getHeight() / 2);
    }

    public void mouseDown(Furniture[] furniture) {
        for (int i = 0; i < furniture.length; i++) { // clear the furniture Array when button is
                                                     // pressed
            furniture[i] = null;
        }
    }

    public void mouseUp() {}

}
