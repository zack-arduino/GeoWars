ArrayList  bullets = new ArrayList();

void bullets_remove() {
  for (int i = 0; i < bullets.size(); i += 0) {
    bullets.remove(0);
  }
}

void bullets_add(Bullet b) {
  bullets.add(b);
}

void bullets_update() {
  for (int i = 0; i < bullets.size(); i++) {
    Bullet b = (Bullet)bullets.get(i);
    b.update();
    if (b.active == false) {
      bullets.remove(i);
    }
  }
}




class Bullet {
  public float sx;
  public float sy;
  public float x;
  public float y;
  public float dx;
  public float dy;
  public int size;
  public boolean type;
  public boolean active;
  
  public Bullet(int x, int y, float direction, float speed, boolean type) {
    this.type = type;
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
    if (active == true) {
      noStroke();
      if (this.type) {
        fill(255, 0, 0);
      }
      else {
        fill(0, 0, 255);
      }
      
      this.x += this.dx;
      this.y += this.dy;
      
      ellipse((int)this.x, (int)this.y, this.size, this.size);
    }
    
    if (sqrt(((this.sx-this.x)*(this.sx-this.x))+((this.sy-this.y)*(this.sy-this.y))) >= 400) {
      active = false;
    }
  }
}
    
    
