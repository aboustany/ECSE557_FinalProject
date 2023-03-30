class Item extends Entity {
  
  enum Risk {HIGH, MEDIUM, SAFE};
  
  private Risk risk;
  private int ageRestriction;
  
  Item(String name, String filepath, Risk risk, int ageRestriction){
    super(name, filepath);
    this.risk = risk;
    this.ageRestriction =  ageRestriction;
  }
  
  public Risk getRisk(){
    return this.risk;
  }
  
  public int getAgeRestriction(){
    return this.ageRestriction;
  }
  
  public void setRisk(Risk risk){
    this.risk = risk;
  }
  
  public void setAgeRestriction(int age){
    this.ageRestriction = age;
  }
    
}
