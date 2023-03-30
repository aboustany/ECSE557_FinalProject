import processing.core.PApplet;
import processing.core.PImage;

class Entity extends PApplet {
  String name;
  protected PImage img; 
  protected String imagePath; 
  
  
  Entity(String name, String imagePath){
    this.name = name;
    this.imagePath = imagePath;
  }

  public void init(PApplet papplet){
    this.img = papplet.loadImage(this.imagePath);
  }

  public void getImage(PApplet papplet, float x, float y, int width, int height){
    papplet.image(this.img, x, y, width, height);
  }

  public void update(PApplet papplet){};
  
   public boolean isBlacklistedItem(Item item) {
     boolean result = false; 
     if(this instanceof Room){
       Room room = (Room) this;
       result = room.blacklistedItems.contains(item);
     }
     return result;   
  }
  
  public int getAge(){
    int age = 0;
    if(this instanceof Character){
      Character chara = (Character)this;
      age = chara.getAge();
    }
    return age;
  }
}
