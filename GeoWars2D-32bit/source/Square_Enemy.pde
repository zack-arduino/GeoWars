ArrayList  Square_Enemys = new ArrayList();

void Square_Enemys_remove() {
  for (int i = 0; i < Square_Enemys.size(); i += 0) {
    Square_Enemys.remove(0);
  }
}

void Square_Enemys_add(Square_Enemy e) {
  Square_Enemys.add(e);
}

void Square_Enemys_update() {
  for (int i = 0; i < Square_Enemys.size(); i++) {
    Square_Enemy e = (Square_Enemy)Square_Enemys.get(i);
    e.update();
    if (e.active == false) {
      Square_Enemys.remove(i);
    }
  }
}

class Square_Enemy {
  public float x;
  public float y;
  public float dx;
  public float dy;
  public float speed = 1;
  public color c = color(0, 255, 0);
  
  public float size     = 25;
  public float size_t   = 0;
  public float size_s   = 25;
  public boolean size_d = true;
  
  public boolean active = true;
  
  public int health = 2;
  
  public Square_Enemy (float x, float y) {
    this.x = x;
    this.y = y;
  }
  
  public void check_collion() {
    for (int i = 0; i < bullets.size(); i++) {
      Bullet b = (Bullet)bullets.get(i);
      if (b.type == false) {
        if (b.x >= this.x-size/2 && b.x <= this.x+size/2 &&
            b.y >= this.y-size/2 && b.y <= this.y+size/2) {
          explosion_add((int)b.x, (int)b.y, this.c);
          b.active = false;
          health -= 1;
        }
      
        if (health <= 0) {
          this.active = false;
        }
      
        if (b.active == false) {
          bullets.remove(i);
        }
      }
    }
  }
    
  public void update() {
    check_collion();
    if (active) {
      // Calculate position
      if (size_d) {
        size = (size_t + size_s);
        size_t += 0.25;
        if (size_t > 10) {
          size_d = false;
        }
      }
      else {
        size = (size_t + size_s);
        size_t -= 0.25;
        if (size_t < 0) {
          size_d = true;
        }
      }
      
      
      if (x > player.x) {
        if (x - player.x < speed) {
          dx = -(x - player.x);
        }
        else {
          dx = -speed;
        }
      }
      else if (x < player.x) {
        if (player.x - x < speed) {
          dx = (player.x - x);
        }
        else {
          dx = speed;
        }
      }
      else {
        dx = 0;
      }
      
      if (y > player.y) {
        if (y - player.y < speed) {
          dy = -(y - player.y);
        }
        else {
          dy = -speed;
        }
      }
      else if (y < player.y) {
        if (player.y - y < speed) {
          dy = (player.y - y);
        }
        else {
          dy = speed;
        }
      }
      else {
        dy = 0;
      }
        
      
      x += dx;
      y += dy;
      
      // Draw Enemy
      stroke(c);
      noFill();
      rect(x-size/2 , y-size/2 , size, size);
    }
  }
}
