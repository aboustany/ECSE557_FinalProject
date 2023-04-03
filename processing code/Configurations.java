import java.util.ArrayList;

public class Configurations {
  
  private boolean registeredUserRequest;
  private boolean unregisteredUserRequest;
  
  private boolean highRiskItemManipulation;
  private boolean mediumRiskItemManipulation;
  
  private boolean childSafeItemRestriction;
  private boolean ageRestriction;
  //private boolean unregisteredGuestRestriction;
  private boolean adminOverride;
  private boolean keepHistory;
  
  private Character owner;
  private ArrayList<Character> administrators;
  private ArrayList<Character> userList;
  private ArrayList<Character> under12Users;
  private ArrayList<Character> ageRestrictedUsers;
  
  private ArrayList<Room> locationList;
  private ArrayList<Room> under12LocationList;
  private ArrayList<Room> ageRestrictedLocationList;
  
  private ArrayList<Item> childSafeItems;
  private ArrayList<Item> guestItemList;
  private ArrayList<Item> ageRestrictedItems;
  
  
  // Can be manipulated by owner
  public Configurations(){
    
    this.registeredUserRequest = true;
    this.unregisteredUserRequest = false;
    this.highRiskItemManipulation = false;
    this.mediumRiskItemManipulation = false;
    this.childSafeItemRestriction = true;
    this.ageRestriction = true;
    this.adminOverride = false;
    this.keepHistory = true;
    
    this.userList = new ArrayList<Character>();
    this.administrators = new ArrayList<Character>();
    this.under12Users = new ArrayList<Character>();
    this.ageRestrictedUsers = new ArrayList<Character>();
    
    this.locationList = new ArrayList<Room>();
    this.under12LocationList = new ArrayList<Room>();
    this.ageRestrictedLocationList = new ArrayList<Room>();
    
    this.childSafeItems = new ArrayList<Item>();
    this.guestItemList = new ArrayList<Item>();
    this.ageRestrictedItems = new ArrayList<Item>();
  
  }
  
  // Getter and setter for registeredUserRequest
  public boolean isRegisteredUserRequest() {
    return registeredUserRequest;
  }
  public void setRegisteredUserRequest(boolean registeredUserRequest) {
    this.registeredUserRequest = registeredUserRequest;
  }
  
  // Getter and setter for unregisteredUserRequest
  public boolean isUnregisteredUserRequest() {
    return unregisteredUserRequest;
  }
  public void setUnregisteredUserRequest(boolean unregisteredUserRequest) {
    this.unregisteredUserRequest = unregisteredUserRequest;
  }
  
  // Getter and setter for highRiskItemManipulation
  public boolean isHighRiskItemManipulation() {
    return highRiskItemManipulation;
  }
  public void setHighRiskItemManipulation(boolean highRiskItemManipulation) {
    this.highRiskItemManipulation = highRiskItemManipulation;
  }
  
  // Getter and setter for mediumRiskItemManipulation
  public boolean isMediumRiskItemManipulation() {
    return mediumRiskItemManipulation;
  }
  public void setMediumRiskItemManipulation(boolean mediumRiskItemManipulation) {
    this.mediumRiskItemManipulation = mediumRiskItemManipulation;
  }
  
  // Getter and setter for childSafeItemRestriction
  public boolean isChildSafeItemRestriction() {
    return childSafeItemRestriction;
  }
  public void setChildSafeItemRestriction(boolean childSafeItemRestriction) {
    this.childSafeItemRestriction = childSafeItemRestriction;
  }
  
  // Getter and setter for ageRestriction
  public boolean isAgeRestriction() {
    return ageRestriction;
  }
  public void setAgeRestriction(boolean ageRestriction) {
    this.ageRestriction = ageRestriction;
  }
  
  //// Getter and setter for unregisteredGuestRestriction
  //public boolean isUnregisteredGuestRestriction() {
  //  return unregisteredGuestRestriction;
  //}
  //public void setUnregisteredGuestRestriction(boolean unregisteredGuestRestriction) {
  //  this.unregisteredGuestRestriction = unregisteredGuestRestriction;
  //}
  
  // Getter and setter for adminOverride
  public boolean isAdminOverride() {
    return adminOverride;
  }
  public void setAdminOverride(boolean adminOverride) {
    this.adminOverride = adminOverride;
  }
  
  // Getter and setter for keepHistory
  public boolean isKeepHistory() {
    return keepHistory;
  }
  public void setKeepHistory(boolean keepHistory) {
    this.keepHistory = keepHistory;
  }
   
  // Getter and setter for owner
  public Character getOwner() {
    return owner;
  }
  
  public void setOwner(Character owner) {
    this.owner = owner;
  }

  // Getter and setter for administrators
  public ArrayList<Character> getAdministrators() {
    return administrators;
  }
  public void setAdministrators(ArrayList<Character> administrators) {
    this.administrators = administrators;
  }
  
  // Getter and setter for userList
  public ArrayList<Character> getUserList() {
    return userList;
  }
  public void setUserList(ArrayList<Character> userList) {
    this.userList = userList;
  }
  
  // Getter and setter for under12Users
  public ArrayList<Character> getUnder12Users() {
    return under12Users;
  }
  public void setUnder12Users(ArrayList<Character> under12Users) {
    this.under12Users = under12Users;
  }
  
  // Getter and setter for ageRestrictedUsers
  public ArrayList<Character> getAgeRestrictedUsers() {
    return ageRestrictedUsers;
  }
  public void setAgeRestrictedUsers(ArrayList<Character> ageRestrictedUsers) {
    this.ageRestrictedUsers = ageRestrictedUsers;
  }
  
  // Getter and setter for locationList
  public ArrayList<Room> getLocationList() {
    return locationList;
  }
  public void setLocationList(ArrayList<Room> locationList) {
    this.locationList = locationList;
  }
  
  // Getter and setter for under12LocationList
  public ArrayList<Room> getUnder12LocationList() {
    return under12LocationList;
  }
  public void setUnder12LocationList(ArrayList<Room> under12LocationList) {
    this.under12LocationList = under12LocationList;
  }
  
  // Getter and setter for ageRestrictedLocationList
  public ArrayList<Room> getAgeRestrictedLocationList() {
    return ageRestrictedLocationList;
  }
  public void setAgeRestrictedLocationList(ArrayList<Room> ageRestrictedLocationList) {
    this.ageRestrictedLocationList = ageRestrictedLocationList;
  }
  
  // Getter and setter for childSafeItems
  public ArrayList<Item> getChildSafeItems() {
    return childSafeItems;
  }
  public void setChildSafeItems(ArrayList<Item> childSafeItems) {
    this.childSafeItems = childSafeItems;
  }
  
  // Getter and setter for guestItemList
  public ArrayList<Item> getGuestItemList() {
    return guestItemList;
  }
  public void setGuestItemList(ArrayList<Item> guestItemList) {
    this.guestItemList = guestItemList;
  }
  
  // Getter and setter for ageRestrictedItems
  public ArrayList<Item> getAgeRestrictedItems() {
    return ageRestrictedItems;
  }
  public void setAgeRestrictedItems(ArrayList<Item> ageRestrictedItems) {
    this.ageRestrictedItems = ageRestrictedItems;
  }
  
  // Add and remove methods for administrators
  public void addAdministrator(Character administrator) {
    administrators.add(administrator);
  }
  public void removeAdministrator(Character administrator) {
    administrators.remove(administrator);
  }
  
  // Add and remove methods for userList
  public void addUser(Character user) {
    userList.add(user);
  }
  public void removeUser(Character user) {
    userList.remove(user);
  }
  
  // Add and remove methods for under12Users
  public void addUnder12User(Character under12User) {
    under12Users.add(under12User);
  }
  public void removeUnder12User(Character under12User) {
    under12Users.remove(under12User);
  }
  
  // Add and remove methods for ageRestrictedUsers
  public void addAgeRestrictedUser(Character ageRestrictedUser) {
    ageRestrictedUsers.add(ageRestrictedUser);
  }
  public void removeAgeRestrictedUser(Character ageRestrictedUser) {
    ageRestrictedUsers.remove(ageRestrictedUser);
  }
  
  // Add and remove methods for locationList
  public void addLocation(Room location) {
    locationList.add(location);
  }
  public void removeLocation(Room location) {
    locationList.remove(location);
  }
  
  // Add and remove methods for under12LocationList
  public void addUnder12Location(Room under12Location) {
    under12LocationList.add(under12Location);
  }
  public void removeUnder12Location(Room under12Location) {
    under12LocationList.remove(under12Location);
  }
  
  // Add and remove methods for ageRestrictedLocationList
  public void addAgeRestrictedLocation(Room ageRestrictedLocation) {
    ageRestrictedLocationList.add(ageRestrictedLocation);
  }
  public void removeAgeRestrictedLocation(Room ageRestrictedLocation) {
    ageRestrictedLocationList.remove(ageRestrictedLocation);
  }
  
  // Add and remove methods for childSafeItems
  public void addChildSafeItem(Item childSafeItem) {
    childSafeItems.add(childSafeItem);
  }
  public void removeChildSafeItem(Item childSafeItem) {
    childSafeItems.remove(childSafeItem);
  }
  
  // Add and remove methods for guestItemList
  public void addGuestItem(Item guestItem) {
    guestItemList.add(guestItem);
  }
  public void removeGuestItem(Item guestItem) {
    guestItemList.remove(guestItem);
  }
  
  // Add and remove methods for ageRestrictedItems
  public void addAgeRestrictedItem(Item ageRestrictedItem) {
    ageRestrictedItems.add(ageRestrictedItem);
  }
  public void removeAgeRestrictedItem(Item ageRestrictedItem){
    ageRestrictedItems.remove(ageRestrictedItem);
}
  
  
  
  public boolean isCharacterInAdministratorList(Character character) {
      return administrators.contains(character);
  }
  
  public boolean isCharacterInUnder12List(Character character) {
      return under12Users.contains(character);
  }
  
  public boolean isCharacterInAgeRestrictedList(Character character) {
      return ageRestrictedUsers.contains(character);
  }
  
  
  public boolean isRoomInUnder12LocationList(Room room) {
      return under12LocationList.contains(room);
  }
  
  public boolean isRoomInAgeRestrictedLocationList(Room room) {
      return ageRestrictedLocationList.contains(room);
  }
  
  public boolean isItemInChildSafeItemList(Item item) {
      return childSafeItems.contains(item);
  }
  
  public boolean isItemInGuestItemList(Item item) {
      return guestItemList.contains(item);
  }
  
  public boolean isItemInAgeRestrictedItemList(Item item) {
      return ageRestrictedItems.contains(item);
  }
  
  public boolean isCharacterInUserList(Character character) {
    return userList.contains(character);
  }



}
