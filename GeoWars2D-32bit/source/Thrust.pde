ArrayList  thrusters = new ArrayList();

void thrusters_remove() {
  for (int i = 0; i < thrusters.size(); i += 0) {
    thrusters.remove(0);
  }
}

void thrusters_add(Thruster t) {
  thrusters.add(t);
}

void thrusters_update() {
  for (int i = 0; i < thrusters.size(); i++) {
    Thruster t = (Thruster)thrusters.get(i);
    t.update();
    if (t.active == false) {
      thrusters.remove(i);
    }
  }
}




class Thruster {
  public float sx;
  public float sy;
  public float x;
  public float y;
  public float dx;
  public float dy;
  public int size;
  public boolean active;
  
  public Thruster(int x, int y, float direction, float speed) {
    this.x = (float)x;
    this.y = (float)y;
    this.sx = (float)x;
    this.sy = (float)y;
    
    float d = radians(direction);
    
    this.dx = cos(d) * speed;
    this.dy = sin(d) * speed;
    
    this.size = 6;
    active = true;
  }
  
  public void update() {
    float distance = sqrt(((this.sx-this.x)*(this.sx-this.x))+((this.sy-this.y)*(this.sy-this.y)));
    if (active == true) {
      
      
      noStroke();
      fill(255-((255/25.0)*distance), 0, 0);
      
      this.x += this.dx;
      this.y += this.dy;
      
      ellipse((int)this.x, (int)this.y, this.size, this.size);
    }
    
    if (distance >= 25) {
      active = false;
    }
  }
}
    
    
