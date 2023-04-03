// a singleton representing the scenario

import java.util.ArrayList;
import java.util.HashMap;


class Scenario {

   // -----------------------------------------------------------\\
   // singleton method
   // -----------------------------------------------------------//
    private static Scenario _instance = null;
    
    private Configurations config;
    
    public static Scenario instance(){
        if (_instance == null){
            _instance = new Scenario();
        }
        
        return _instance;
    }
    
   // -----------------------------------------------------------\\
   // defining scenario objects
   // -----------------------------------------------------------//

    // rooms ......................................................

    public static HashMap<String, Room> rooms = new HashMap<String, Room>() {{

        put("Common Space",  new Room("Common Space", "assets/CommonSpace.jpg", 1f, 1f));
        put("Kitchen",       new Room("Kitchen",      "assets/Kitchen.jpg",     2f, 1f));
        put("Bedroom 1",     new Room("Bedroom 1",    "assets/Bedroom1.jpg",    0.5f, 0.0f));
        put("Bedroom 2",     new Room("Bedroom 2",    "assets/Bedroom2.jpg",    1.5f, 0.0f));
        put("Washroom",      new Room("Washroom",     "assets/Washroom.jpg",    3f, 1f));

    }}; 

    // characters .................................................
    
    // Please add all Characters. First character added will be set as Owner.
    public static HashMap<String, Character> characters = new HashMap<String, Character>(){{

        put("Jill Smith", new Character("Jill Smith", "assets/characters/mom.png", 33));
        put("Amy Copper", new Character("Amy Copper", "assets/characters/daughter.png", 19));
        put("Ben",        new Character("Ben",        "assets/characters/bebe.png" , 2));
        put("Buddy",      new Character("Buddy",      "assets/characters/dog.png", 1));
        put("V",          new Character("V",          "assets/characters/bf.png", 0));
        put("Robot",      new Character("Robot",      "assets/characters/robot.png", 0));
        
        

    }};


    // items ......................................................

    public static HashMap<String, Item> items = new HashMap<String, Item>(){{
        put("Credit Card", new Item("Credit Card", "assets/items/credit_card.png", Item.Risk.MEDIUM)); 
        put("Wallet",      new Item("Wallet", "assets/items/wallet.png", Item.Risk.MEDIUM)); 
        put("Knife",       new Item("Knife", "assets/items/knife.png", Item.Risk.HIGH) ); 
        put("Beer",        new Item("Beer", "assets/items/beer.png", Item.Risk.MEDIUM)); 
        put("Diary",       new Item("Diary", "assets/items/diary.png", Item.Risk.SAFE)); 

    }};

    
    
    private Scenario(){
        
         // Initialize robot configurations
         config = new Configurations();

        // -----------------------------------------------------------\\
        // creating map        
        // -----------------------------------------------------------//
        
        // connecting rooms ............................................
        rooms.get("Common Space").addConnection(rooms.get("Kitchen"));
        rooms.get("Common Space").addConnection(rooms.get("Bedroom 1"));
        rooms.get("Common Space").addConnection(rooms.get("Bedroom 2"));
        rooms.get("Kitchen")     .addConnection(rooms.get("Washroom"));        
        
        
        // placing characters ..........................................
        rooms.get("Common Space").addCharacter(characters.get("Jill Smith"));
        rooms.get("Bedroom 1")   .addCharacter(characters.get("Amy Copper"));
        rooms.get("Bedroom 2")   .addCharacter(characters.get("Ben"));
        rooms.get("Kitchen")     .addCharacter(characters.get("Buddy"));
        rooms.get("Bedroom 1")   .addCharacter(characters.get("V"));
        rooms.get("Common Space").addCharacter(characters.get("Robot"));
        
        // placing items .............................................
        rooms.get("Kitchen")     .addItem(items.get("Knife"));
        rooms.get("Bedroom 2")   .addItem(items.get("Diary"));
        rooms.get("Common Space").addItem(items.get("Credit Card"));
        rooms.get("Bedroom 1")   .addItem(items.get("Wallet"));
        rooms.get("Washroom")    .addItem(items.get("Beer"));      
        
        String owner_name = "Jill Smith"; // User sets owner
        
        // Adds first user as Owner and admin, all other characters registered as Users.
        for (HashMap.Entry<String, Character> characterEntry : characters.entrySet()) {
          
          if(characterEntry.getKey() == owner_name){
            config.setOwner(characterEntry.getValue());
            config.addAdministrator(characterEntry.getValue());
          } 
          
          Character character = characterEntry.getValue();
          config.addUser(character);
          
       }
       System.out.println("Owner");
       System.out.println(config.getOwner().name);
       System.out.println("Admins");
       System.out.println(config.getAdministrators());

       
       // Add Rooms.
        for (HashMap.Entry<String, Room> roomEntry : rooms.entrySet()) {
          Room room = roomEntry.getValue();
          config.addLocation(room);
       }
       
       System.out.println(config.getLocationList());
      
    
        // Please enter the names of all administrators (comma-separated)
        String[] adminNames = {}; // read input from user
        ArrayList<Character> administrators = new ArrayList<Character>();
        for (String adminName : adminNames) {
            Character admin = Scenario.characters.get(adminName);
            if(admin!=null) config.addAdministrator(admin);
        }
        

        // Please enter the names of all under 12 users (comma-separated)
        String[] under12UserNames = {"Ben"}; // read input from user
        ArrayList<Character> under12Users = new ArrayList<Character>();
        for (String userName : under12UserNames) {
            Character user = Scenario.characters.get(userName);
            if (user != null) under12Users.add(user);
        }
        config.setUnder12Users(under12Users);
        
        // Please enter the names of all age-restricted users (comma-separated)
        String[] ageRestrictedUserNames = {}; // read input from user
        ArrayList<Character> ageRestrictedUsers = new ArrayList<Character>();
        for (String userName : ageRestrictedUserNames) {
            Character user = Scenario.characters.get(userName);
            if(user != null) ageRestrictedUsers.add(user);
        }
        config.setAgeRestrictedUsers(ageRestrictedUsers);
        
        
        // Please enter the names of all locations restricted for under 12 users (comma-separated)
        String[] under12LocationNames = {}; // read input from user
        ArrayList<Room> under12Locations = new ArrayList<Room>();
        for (String locationName : under12LocationNames) {
            Room location = Scenario.rooms.get(locationName);
            if(location != null) under12Locations.add(location);
        }
        config.setUnder12LocationList(under12Locations);
        
        // Please enter the names of all age-restricted locations (comma-separated)
        String[] ageRestrictedLocationNames = {}; // read input from user
        ArrayList<Room> ageRestrictedLocations = new ArrayList<Room>();
        for (String locationName : ageRestrictedLocationNames) {
            Room location = Scenario.rooms.get(locationName);
            if(location != null) ageRestrictedLocations.add(location);
        }
        config.setAgeRestrictedLocationList(ageRestrictedLocations);
        
        // Please enter the names of all child-safe items (comma-separated)
        String[] childSafeItemNames = {"Diary"}; // read input from user
        ArrayList<Item> childSafeItems = new ArrayList<Item>();
        for (String itemName : childSafeItemNames) {
            Item item = Scenario.items.get(itemName);
            if(item != null) childSafeItems.add(item);
        }
        config.setChildSafeItems(childSafeItems);
        
        // Please enter the names of all guest items (comma-separated)
        String[] guestItemNames = {}; // read input from user
        ArrayList<Item> guestItems = new ArrayList<Item>();
        for (String itemName : guestItemNames) {
            Item item = Scenario.items.get(itemName);
            if(item != null) guestItems.add(item);
        }
        config.setGuestItemList(guestItems);
        
        // Please enter the names of all age-restricted items (comma-separated)
        String[] ageRestrictedItemNames = {}; // read input from user
        ArrayList<Item> ageRestrictedItems = new ArrayList<Item>();
        for (String itemName : ageRestrictedItemNames) {
            Item item = Scenario.items.get(itemName);
            if(item != null) ageRestrictedItems.add(item);
        }
        config.setAgeRestrictedItems(ageRestrictedItems);

        
        

         
    }

    public Character getRobot(){
        return characters.get("Robot");
    }
    
    public Configurations getConfig(){
      return config;
    }
}
