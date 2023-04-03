class Item extends Entity {
  
  enum Risk {HIGH, MEDIUM, SAFE};
  
  private Risk risk;
  
  Item(String name, String filepath, Risk risk){
    super(name, filepath);
    this.risk = risk;
  }
  
  public Risk getRisk(){
    return this.risk;
  }
  
  public void setRisk(Risk risk){
    this.risk = risk;
  }
  
    
}
