ArrayList  explosions = new ArrayList();

void explosion_remove() {
  for (int i = 0; i < explosions.size(); i += 0) {
    explosions.remove(0);
  }
}

void explosion_add(int x, int y, color c) {
  for (int i = 0; i < 360; i+=5) {
    if ((int)random(4) == 1) {
      explosions.add(new Explosion(x, y, i, 2, c));
    }
  }
}

void explosion_update() {
  for (int i = 0; i < explosions.size(); i++) {
    Explosion x = (Explosion)explosions.get(i);
    x.update();
    if (x.active == false) {
      explosions.remove(i);
    }
  }
}




class Explosion {
  public float sx;
  public float sy;
  public float x;
  public float y;
  public float dx;
  public float dy;
  public int size;
  public boolean active;
  public color c;
  
  public Explosion(int x, int y, float direction, float speed, color c) {
    this.c = c;
    this.x = (float)x;
    this.y = (float)y;
    this.sx = (float)x;
    this.sy = (float)y;
    
    float d = radians(direction);
    
    this.dx = cos(d) * speed;
    this.dy = sin(d) * speed;
    
    this.size = 2;
    active = true;
  }
  
  public void update() {
    if (active == true) {
      noStroke();
      fill(c);
      
      this.x += this.dx;
      this.y += this.dy;
      
      ellipse((int)this.x, (int)this.y, this.size, this.size);
    }
    
    if (sqrt(((this.sx-this.x)*(this.sx-this.x))+((this.sy-this.y)*(this.sy-this.y))) >= 50) {
      active = false;
    }
  }
}
