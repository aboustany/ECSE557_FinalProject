import uibooster.*;
import uibooster.components.*;
import uibooster.model.*;
import uibooster.model.formelements.*;
import uibooster.model.options.*;
import uibooster.utils.*;

import java.util.ArrayDeque;

class GUI_Controller {
    private static final int CONSOLE_HEIGHT = 200;
    private static final int BUTTON_HEIGHT = 100;
    private static final int BUTTON_WIDTH = 200;
    private static final int TEXT_SIZE = 30;
    private static final int MARGIN_LEFT = 10;
    private final int TEXTLINE_YPOS = height - (CONSOLE_HEIGHT/2) - (TEXT_SIZE/2);
    private final int CONSOLE_YBASE = height - 10;
    private final int BUTTONLINE_YPOS = height - (CONSOLE_HEIGHT/2) - (BUTTON_HEIGHT/2) - (TEXT_SIZE/2);
    private static final int MAX_CONSOLE_LINES = 6;

    private String currentRequester = "Amy Copper";
    private String currentTarget = "Credit Card";
    private String currentReceiver = "Jill Smith";

    private ArrayList<String> requesterOptions;
    private ArrayList<String> targetOptions;
    private ArrayList<String> receiverOptions;

    private ArrayDeque<String> consoleLines;

    private UIState uiState; 

    GUI_Controller(){

        this.requesterOptions = new ArrayList<String>(Scenario.instance().characters.keySet());
        this.targetOptions = new ArrayList<String>(Scenario.instance().items.keySet());
        this.receiverOptions = new ArrayList<String>();
        this.consoleLines = new ArrayDeque<String>(6);

        this.uiState = UIState.INPUT;



        for (String characterName : Scenario.instance().characters.keySet()) {
            this.receiverOptions.add(characterName);
        }
        for (String roomName : Scenario.instance().rooms.keySet()) {
            this.receiverOptions.add(roomName);
        }
    }

    void update(Controller controller){
        fill(c_dark);
        rect(0, height-CONSOLE_HEIGHT, width, CONSOLE_HEIGHT);

        switch (uiState) {
            case INPUT:
                renderInputMenu();
                break;
            default:
                renderTerminalFeedback();
                break;
        }
    }

    private void renderInputMenu(){
            if(Button(currentRequester, MARGIN_LEFT, BUTTONLINE_YPOS, BUTTON_WIDTH, BUTTON_HEIGHT)){
                String selection = new UiBooster().showSelectionDialog(
                                                                        "Select requester:",
                                                                        "Requester Character",
                                                                        this.requesterOptions
                                                                        );
                if(selection != null) currentRequester = selection;                
            };

            text("requests: bring", 
                MARGIN_LEFT + 330, 
                TEXTLINE_YPOS);

            if(Button(currentTarget, MARGIN_LEFT + 460, BUTTONLINE_YPOS, BUTTON_WIDTH, BUTTON_HEIGHT)){
                String selection = new UiBooster().showSelectionDialog(
                                                                        "Select item:",
                                                                        "Item to Bring",
                                                                        this.targetOptions
                                                                        );
                if(selection != null) currentTarget = selection;
            };

            text("to: ", 
                MARGIN_LEFT + 710, 
                TEXTLINE_YPOS);

            if(Button(currentReceiver, 
                    MARGIN_LEFT + 740, 
                    BUTTONLINE_YPOS, 
                    BUTTON_WIDTH, 
                    BUTTON_HEIGHT)){
                        String selection = new UiBooster().showSelectionDialog(
                                                                        "Select receiver:",
                                                                        "Destination",
                                                                        this.receiverOptions
                                                                        );
                if(selection != null) currentReceiver = selection;
            };

            if(Button("> Execute", 
                    width - BUTTON_WIDTH - MARGIN_LEFT, 
                    BUTTONLINE_YPOS, 
                    BUTTON_WIDTH, 
                    BUTTON_HEIGHT)){

                println("current requester: "+this.currentRequester);
                println("current target: "+this.currentTarget);
                println("current rec: "+this.currentReceiver);

                println("current receiver:");

                Entity destination = Scenario.instance().characters.get(this.currentReceiver);
                if(destination == null){
                    destination = Scenario.instance().rooms.get(this.currentReceiver);
                }

                controller.command(Scenario.instance().characters.get(this.currentRequester), 
                                Scenario.instance().items.get(this.currentTarget), 
                                destination);
                toggleState();
            };
    }

    public void renderTerminalFeedback(){
        fill(255,255,255);
        textAlign(LEFT);

        int i = 0;
        for (String msg : this.consoleLines) {
            if(msg != null){
                text(msg, MARGIN_LEFT, CONSOLE_YBASE - (i * TEXT_SIZE));
            }
            i++;
        }
    }

    public void updateTerminal(String msg){
        if(this.consoleLines.size() >= 6){
            this.consoleLines.removeLast();
        }
        this.consoleLines.push(msg);
    }

    public void toggleState(){
        if (this.uiState == UIState.INPUT) this.uiState = UIState.FEEDBACK;
        else this.uiState = UIState.INPUT;
    }
}
