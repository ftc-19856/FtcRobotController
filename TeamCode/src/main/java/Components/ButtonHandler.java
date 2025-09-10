package Components;

// Gamepad toggle class
public class ButtonHandler {

    private boolean currentValue;
    private boolean previousState;

    // Initializes the variables
    public ButtonHandler(boolean initialValue) {
        this.currentValue = initialValue;
        this.previousState = false;
    }

    // Assigns a button to be toggled and adds the logic
    public boolean update(boolean currentButtonState) {
        if(currentButtonState && !previousState){
            currentValue = !currentValue;
        }

        previousState = currentButtonState;
        return currentValue;
    }

    // Gets the current state of the button for telemetry
    public boolean getValue(){
        return currentValue;
    }

}
