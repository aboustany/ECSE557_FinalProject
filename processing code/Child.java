import processing.core.PApplet;
import processing.core.PImage;
import java.util.HashSet;

class Child extends Character {
  
  private HashSet<Item> childSafeItems;

  Child(String name, String filepath, int age){
    super(name,filepath, age);
    this.childSafeItems = new HashSet<Item>();
    for (Item item : Scenario.instance().items.values()){
      this.addBlacklistedItem(item);
    }
  }
  
  public void addChildSafeItem(Item item) {
    this.childSafeItems.add(item);
    this.deleteBlacklistedItem(item);
  }
  
  public void deleteChildSafeItem(Item item) {
    this.childSafeItems.remove(item);
    this.addBlacklistedItem(item);
  }

  public boolean isChildSafeItem(Item item) {
    return this.childSafeItems.contains(item);
  }
  
}
