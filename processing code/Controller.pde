import java.util.Queue;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;

class Controller {

    Scenario scene = Scenario.instance();
    
    Character robot;
    Configurations config;

    private long secondDelay;
    
    RequestHistory history;

    Controller() {
        robot = scene.getRobot();
        config = scene.getConfig();
        history = new RequestHistory();
    }

public boolean command(Character requester, Item item, Entity receiver) {
    // Print the list of registered users
    System.out.println(config.getUserList());

    // Update the status with the command request
    updateStatus(String.format("COMMAND: [%s] asks the robot to bring [%s] to [%s]", requester.name, item.name, receiver.name));

    // Get the room where the item is located and determine the final room based on the type of receiver
    Room fetchRoom = getRoomItemIsIn(item);
    Room finalRoom = determineFinalRoom(receiver);

    // Print the command parameters
    println("command called with", requester, item, receiver);

    // Check if the receiver is a room and if there are child restrictions on the item
    boolean isReceiverRoom = receiver instanceof Room;
    boolean childRestricted = config.isChildSafeItemRestriction() && !config.isItemInChildSafeItemList(item);

    // Check for restrictions
    String restrictionMessage = checkRestrictions(requester, item, receiver, isReceiverRoom, childRestricted);

    // Process the request
    boolean success = processRequest(requester, item, isReceiverRoom, childRestricted, restrictionMessage);

    // Log the request
    logRequest(requester, item, receiver);

    // Execute the fetch procedure if the request was successful
    if (success) {
        FetchProcedureAsync(item, fetchRoom, finalRoom, receiver);
    }
   
    return success;
}

// Determine the final room based on the type of receiver
private Room determineFinalRoom(Entity receiver) {
    return receiver instanceof Room ? (Room) receiver : getRoomCharacterIsIn((Character) receiver);
}

// Check for restrictions
private String checkRestrictions(Character requester, Item item, Entity receiver, boolean isReceiverRoom, boolean childRestricted) {
    String restrictionMessage = null;

    if (childRestricted && (config.isCharacterInUnder12List(requester) || config.isCharacterInUnder12List((Character) receiver))) {
        restrictionMessage = "Robot cannot process request due to item child restriction. (%s Underage)";
    } else if (config.isAgeRestriction() && (config.isItemInAgeRestrictedItemList(item) && (config.isCharacterInAgeRestrictedList(requester) || config.isCharacterInAgeRestrictedList((Character) receiver)))) {
        restrictionMessage = "Robot cannot process request due to item age restriction. (%s Age Restricted)";
    } else if (isReceiverRoom && ((Room) receiver).isBlacklistedItem(item)) {
        restrictionMessage = "Unable to process request due blacklisted Item. (Room Blacklist)";
    } else if (!isReceiverRoom && (requester.isBlacklistedItem(item) || ((Character) receiver).isBlacklistedItem(item))) {
        restrictionMessage = "Unable to process request due blacklisted Item. (%s Blacklist)";
    } else if (item.getRisk() == Item.Risk.HIGH && !config.isHighRiskItemManipulation()) {
        restrictionMessage = "Robot cannot process request due to item risk level restriction. (High Risk Item Manipulation Restricted)";
    } else if (item.getRisk() == Item.Risk.MEDIUM && !config.isMediumRiskItemManipulation()) {
        restrictionMessage = "Robot cannot process request due to item risk level restriction. (Medium Risk Level Manipulation Restricted)";
    }

    return restrictionMessage;
}



// Process the request
private boolean processRequest(Character requester, Item item, boolean isReceiverRoom, boolean childRestricted, String restrictionMessage) {
    boolean success = false;

    if (restrictionMessage == null) {
        // All conditions are met, and the request is approved
        success = true;
        updateStatus("Request Approved");
    } else {
        // There are restrictions, and the request is denied
        Room finalRoom = getCurrentRobotRoom();
        String userStatus = config.isCharacterInUnder12List(requester) || requester.isBlacklistedItem(item) ? "Requester" : "Receiver";
        updateStatus(String.format(restrictionMessage, userStatus));

        // Check for admin override
        if (config.isAdminOverride() && config.isCharacterInAdministratorList(requester)) {
          
          System.out.println("Admin Override Detected. Would you like to proceed with the task anyways? (Y/N)");
          Scanner scanner = new Scanner(System.in);
          String userInput = scanner.nextLine();


        if (userInput.equalsIgnoreCase("Y")) {
            // Admin override approved
            success = true;
            updateStatus("Admin Override Approved");
        }
    }
}

    // Check if requester is a registered user, and if unregistered users can make requests
    if (!config.isCharacterInUserList(requester)) {
        if (config.isUnregisteredUserRequest() && config.isItemInGuestItemList(item)) {
            // Request is approved for unregistered users
            success = true;
            updateStatus("Request Approved");
        } else {
            // Request is denied for unregistered users
            success = false;
            updateStatus("Request Denied: Requester is not a registered user.");
        }
    }

  return success;
}

// Log the request
private void logRequest(Character requester, Item item, Entity receiver) {
  String entry = String.format("Current requester: %s sent current target: %s to current receiver: %s with the following message:", requester, item, receiver);
  if (config.isKeepHistory() && history != null) {
    // Add the request to the history
    history.addRequest(entry);
  }
}



public void FetchProcedureAsync(Item item, Room fetchRoom, Room finalRoom, Entity receiver){

        ArrayList<Room> pathToItem = BFS(fetchRoom);
        int timeToGetItem = pathToItem.size();

        travel(pathToItem);
        
        CompletableFuture.delayedExecutor(timeToGetItem, TimeUnit.SECONDS).execute(() -> {
            updateStatus("''[-.-] --{picked up "+item.name+")");
            fetchRoom.removeItem(item);            
        });
        

        // apologies for the nesting ~*
        CompletableFuture.delayedExecutor(timeToGetItem + 1, TimeUnit.SECONDS).execute(() -> {
            ArrayList<Room> pathToReceiver = BFS(finalRoom);
            int timeToReceiver = pathToReceiver.size();
            travel(pathToReceiver);
            
            CompletableFuture.delayedExecutor(timeToReceiver + 1, TimeUnit.SECONDS).execute(() -> {
                updateStatus("![^-^] --{brought item to: "+receiver.name+")");
                finalRoom.addItem(item);
            });

            CompletableFuture.delayedExecutor(timeToReceiver + 3, TimeUnit.SECONDS).execute(() -> {
                if(gui != null) gui.toggleState();
            });
        });


    }

    

    private void travel(ArrayList<Room> path){
        if (path.get(0) == getCurrentRobotRoom()){
            updateStatus("  ['_'] ...");
            return;
        }
        this.secondDelay = 1;
        path.forEach(room -> {
            CompletableFuture.delayedExecutor(this.secondDelay, TimeUnit.SECONDS).execute(() -> {
                moveRobot(getCurrentRobotRoom(), room);        
            });
            this.secondDelay += 1;
        });
    }

    private void moveRobot(Room from, Room to){
        from.removeCharacter(robot);
        updateStatus("*~ [o_o] --{beep boop moving to room: "+to.name+")");
        to.addCharacter(robot);            
    }

    private Room getRoomItemIsIn(Item item){
        // finds the room an item is in and returns it

        for(Room room : scene.rooms.values()){
            if (room.items.contains(item)){
                updateStatus("Found item "+item.name+" in room: "+room.name);
                return room;
            }
        }
        updateStatus("Cannot find item! Is it not placed in a room?");
        return new Room("ITEM_NOT_FOUND", "", 0,0);
    }

    private Room getRoomCharacterIsIn(Character chara){
        // finds the room an item is in and returns it

        for(Room room : scene.rooms.values()) {
            if(room.characters.contains(chara)){
                updateStatus("Found "+chara.name+" in room: "+room.name);
                return room;
            }
        }
        updateStatus("Cannot find character! Do they exist or are they not in the apartment?");        
        return new Room("CHARACTER_NOT_FOUND","",0,0);
    }

    class Edge{
        Room from;
        Room to;
        int cost;
        Edge(Room from, Room to, int cost){
            this.from = from;
            this.to = to;
            this.cost = cost;
        }
    }

    public ArrayList<Room> BFS(Room targetRoom){
        // conducts a breadth-first-search and returns a path to a targetRoom
        // targetRoom := a Room that an object of interest is in
        // returns: an ArrayList of rooms for the robot to travel through
        // if for whatever reason the algorithm cannot find the room
        // (i.e. it was not connected to other rooms), the the method
        // will just return the targetRoom and the robot will appear
        // to teleport.
        

        Room startingRoom = getCurrentRobotRoom(); // room robot is currently in
        ArrayList<Room> visited = new ArrayList<Room>(); // saves list of visited rooms (graph is bidirectional, prevents loops)
        Queue<Room> queue = new LinkedList<Room>(); // bfs uses queue
        ArrayList<Edge> distanceTable = new ArrayList<Edge>(); // creates a table of edges between rooms (nodes) and their 
                                                               // unweighted distance from the startingRoom

        println("\nsearching for path from "+startingRoom.name+" ~> "+targetRoom.name);
        
        if(startingRoom == targetRoom) return new ArrayList<Room>(Arrays.asList(targetRoom));
        
        int cost = 0; // distance from starting room

        queue.add( startingRoom ); // start of BFS algorithm
        distanceTable.add( new Edge(startingRoom, startingRoom, cost) );
        while(queue.size() != 0){
            
            cost++;
            Room currentRoom = queue.poll();
            ArrayList<Room> adjacentRooms = currentRoom.connections;

            for (Room room : adjacentRooms) {
                if(!visited.contains(room)){
                    visited.add(room);                    
                    distanceTable.add(new Edge(currentRoom, room, cost));
                    if(room == targetRoom){
                        // we get the path here by recursively iterating through parent nodes
                        ArrayList<Room> navPath = ReconstructPath(distanceTable, room);

                        return navPath;
                    }
                    queue.add(room);
                }
            }
        }
    print("WARNING: cannot find target room! Initiating teleportation sequence...");
    return(new ArrayList<Room>(Arrays.asList(targetRoom)));
    }

    private ArrayList<Room> ReconstructPath(ArrayList<Edge> edges, Room target){
        // reconstructs a path to a target room from a matrix of edges
        ArrayList<Room> path = new ArrayList<Room>();
        for (Edge edge : edges) {
            if(edge.to == target){
                ArrayList<Room> reconstructedPath = ReconstructPathUtil(path, edge, edges);
                Collections.reverse(reconstructedPath);
                // println("reconstructed path:");
                // reconstructedPath.forEach(e -> print(e.name+" "));
                // print("\n");
                return (reconstructedPath);
            }
        }
        println("WARNING: TARGET ROOM CANNOT BE FOUND! Returning null path");  
        return new ArrayList<Room>();
    }


    private ArrayList<Room> ReconstructPathUtil(ArrayList<Room> path, Edge edge, ArrayList<Edge> edges){      
        // recursive utility function for ReconstructPath
        // builds an ArrayList (path) of prev rooms                  
        if(edge.to == edge.from){
            return(path);
        }
        else{            
            for (Edge e : edges) {
                if(e.to == edge.from){
                    path.add(edge.to);
                    return ReconstructPathUtil(path, e, edges );
                }
            }
        }
        print("WARNING: cannot find parent node! Returning truncated path");
        return path;
    }

    private Room getCurrentRobotRoom(){
        for(Room room : scene.rooms.values()){
            if(room.characters.contains(robot)){
                return room;
            }
        }        
        println("ERROR: CANNOT FIND ROBOT! Has it not been placed in a room, or does Scenario.java not have a reference to the Room?");
        return new Room("ROBOT_NOT_FOUND","",0,0); // a bit hacky lol
    }


    private void printEdgeInfo(Edge edge){
        // for debugging
        println("edge---");
        println("from: "+edge.from.name+"\n"+
                "to: "  +edge.to.name+"\n"+
                "cost: "+edge.cost);   
    }

    private void updateStatus(String msg){
        if(gui != null) {
          gui.updateTerminal(msg);
        }
        println(msg);
    }

}
