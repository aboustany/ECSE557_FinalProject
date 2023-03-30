import processing.core.PApplet;
import processing.core.PImage;
import java.util.HashSet;

class Character extends Entity {
  
  private HashSet<Item> blacklistedItems;
  private int age;

  Character(String name, String filepath, int age){
    super(name, filepath);
    this.blacklistedItems = new HashSet<Item>();
    this.age = age;
  }
 
  public int getAge(){
    return this.age;
  }

  public void addBlacklistedItem(Item item) {
    this.blacklistedItems.add(item);
  }
  
   public void deleteBlacklistedItem(Item item) {
    this.blacklistedItems.remove(item);
  }

  public boolean isBlacklistedItem(Item item) {
    return this.blacklistedItems.contains(item);
  }
  
} 
