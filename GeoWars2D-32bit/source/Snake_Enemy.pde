ArrayList  Snake_Enemys = new ArrayList();

void Snake_Enemys_remove() {
  for (int i = 0; i < Snake_Enemys.size(); i+=0) {
    Snake_Enemys.remove(i);
  }
}

void Snake_Enemys_add(Snake_Enemy e) {
  Snake_Enemys.add(e);
}

void Snake_Enemys_update() {
  for (int i = 0; i < Snake_Enemys.size(); i++) {
    Snake_Enemy e = (Snake_Enemy)Snake_Enemys.get(i);
    e.update();
    if (e.active == false) {
      Snake_Enemys.remove(i);
    }
  }
}

class Snake_Enemy {
  public float x;
  public float y;
  public float dx;
  public float dy;
  public float speed = 2;
  
  public color c = color(255, 132, 0);
  
  public float size = 20;
  public float size_h = 40;
  public float size_b = 20;
  
  public float life = 0;
  
  public float turn = 0;
  
  public boolean active = true;
  
  public int health = 20;
  
  public ArrayList links = new ArrayList();
  
  //public Point[] point_history = new Point(x,y)[12];
  
  public Snake_Enemy (float x, float y) {
    this.x = x;
    this.y = y;
    
    for (int i = 0; i < 100; i++) {
      links.add(new Snake_Link(x, y, 0));
    }
  }
  
  public void check_collion() {
    for (int i = 0; i < bullets.size(); i++) {
      Bullet b = (Bullet)bullets.get(i);
      if (b.type == false) {
         Snake_Link s1 = (Snake_Link)links.get(links.size() - 1);
         
        if (b.x >= s1.x - 225 && b.x <= s1.x + 225 &&
            b.y >= s1.y - 225 && b.y <= s1.y + 225) {
          
          for (int j = 0; j < links.size(); j+=(size/2-5)) {
        
            float a = sqrt((size*size)-((size/2)*(size/2)))-size/2;
        
            Point[] points = {new Point( 0,-((int)a)),
                              new Point(-(int)size/2, (int)size/2),
                              new Point( (int)size/2, (int)size/2),
                              new Point( 0,-((int)a))};
                            
            Snake_Link link = (Snake_Link)links.get(j);
        
            for (Point p : points) {
              p.Rotate(link.d * 180 / PI + 90);
              p.Translate(link.x, link.y);
            }
          
            Triangle t = new Triangle(points[0].x,points[0].y,points[1].x,points[1].y,points[2].x,points[2].y);
            if (checkTriCollision(b.x, b.y, t)) {
              explosion_add((int)b.x, (int)b.y, this.c);
              b.active = false;
              health -= 1;
            }
          }
        }
        if (health <= 0 && this.active == true) {
          for (int j = 0; j < links.size(); j+=(size/2-5)) {
            Snake_Link link = (Snake_Link)links.get(j);
            explosion_add((int)link.x, (int)link.y, this.c);
          }
          
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
      turn = ((turn+1)%360);
      
      
      if (x > player.x) {
        dx-=0.1;
        
        if (dx < -speed) {
          dx = -speed;
        }
      }
      else if (x < player.x) {
        dx+=0.1;
        
        if (dx > speed) {
          dx = speed;
        }
      }
      else {
        dx = 0;
      }
      
      if (y > player.y) {
        dy-=0.1;
        
        if (dy < -speed) {
          dy = -speed;
        }
      }
      else if (y < player.y) {
        dy+=0.1;
        
        if (dy > speed) {
          dy = speed;
        }
      }
      else {
        dy = 0;
      }
      
      x += dx;
      y += dy;
      
      links.add(new Snake_Link(x, y, atan2(dy, dx)));
      links.remove(0);
      
      stroke(c);
      noFill();
      
      for (int i = 0; i < links.size(); i+=(size/2-5)) {
        float b = sqrt((size*size)-((size/2)*(size/2)))-size/2;
        
        Point[] points = {new Point( 0,-((int)b)),
                          new Point(-(int)size/2, (int)size/2),
                          new Point( (int)size/2, (int)size/2),
                          new Point( 0,-((int)b))};
                          
        Snake_Link link = (Snake_Link)links.get(i);
        
        for (Point p : points) {
          p.Rotate(link.d * 180 / PI + 90);
          p.Translate(link.x, link.y);
        }
        
        Point q = new Point(0, 0);
        for (Point p : points) {
          if (q.x != 0 && q.y != 0) {
            Line(p, q);
          }
          q = p;
        }
      }
    }
  }
}

class Snake_Link {
  public float x;
  public float y;
  public float d;
  
  public Snake_Link(float x, float y, float d) {
    this.x = x;
    this.y = y;
    this.d = d;
  }
}
