class Ship {
  public float x = width/2;
  public float y = height/2;
  public float dx;
  public float dy;
  
  public float direction = 0;
  public float speed;
  
  public boolean thruster_on = false;
  public float thruster_strength = 0.001;
  
  public int fire_rate  = 150;
  private int last_shot = 0;
  
  public int ring_rate  = 5000;
  private int last_ring = 0;
  
  public float health = 100;
  
  public void fire() {
    if (millis() >= fire_rate + last_shot) {
      last_shot = millis();
      //bullets_add(new Bullet((int)x, (int)y, direction-90, 4, false));
      
      // Calculate turret points
      Point p1 = new Point(-15, -10);
      Point p2 = new Point( 15, -10);
      p1.Rotate(direction);
      p1.Translate(x, y);
      p2.Rotate(direction);
      p2.Translate(x, y);
      
      // Fire bullets
      bullets_add(new Bullet((int)p1.x, (int)p1.y, direction-90, 7, false));
      bullets_add(new Bullet((int)p2.x, (int)p2.y, direction-90, 7, false));
    }
  }
  
  public void ring() {
    if (millis() >= ring_rate + last_ring) {
      last_ring = millis();
      for (int i = 0; i < 360; i+=1) {
        bullets_add(new Bullet((int)x, (int)y, i, 7, false));
      }
      for (int i = 0; i < 360; i+=1) {
        bullets_add(new Bullet((int)x, (int)y, i, 7, false));
      }
    }
  }
  
  public void control(boolean up, boolean left, boolean down, boolean right, boolean fire, boolean ring) {
    // Move ship
    
    
    if (up) {
      this.thruster_on = true;
      this.speed += thruster_strength;
      
      // Calculate thruster points
      Point p1 = new Point(-20,  15);
      Point p2 = new Point( 20,  15);
      p1.Rotate(direction);
      p1.Translate(x, y);
      p2.Rotate(direction);
      p2.Translate(x, y);
      
      // Create thrust
      for (int i = 0; i < 10; i++) {
        thrusters_add(new Thruster((int)p1.x, (int)p1.y, (direction+90+random(-10,10)), 3));
        thrusters_add(new Thruster((int)p2.x, (int)p2.y, (direction+90+random(-10,10)), 3));
      }
    }
    
    if (down) {
      this.thruster_on = true;
      this.speed -= thruster_strength;
      
      // Calculate thruster points
      Point p1 = new Point(-15, -10);
      Point p2 = new Point( 15, -10);
      p1.Rotate(direction);
      p1.Translate(x, y);
      p2.Rotate(direction);
      p2.Translate(x, y);
      
      // Create thrust
      for (int i = 0; i < 10; i++) {
        thrusters_add(new Thruster((int)p1.x, (int)p1.y, (direction-90+random(-7,7)), 3));
        thrusters_add(new Thruster((int)p2.x, (int)p2.y, (direction-90+random(-7,7)), 3));
      }
    }
    
    if (right) {
      this.direction += 3;
    }
    
    if (left) {
      this.direction -= 3;
    }
    
    // Shoot weapon
    if (fire) {
      this.fire();
    }
    
    if (ring) {
      this.ring();
    }
  }
  
  public void update() {
    // Calculate Ship position
    if (thruster_on) {
      dx += cos(radians(direction-90))*speed;
      dy += sin(radians(direction-90))*speed;
    }
    else {
      speed = 0;
    }
    
    thruster_on = false;
    
    // Bounce off walls
    if (x >= width || x <= 0) {
      if (x <= 0 && dx < 0) {
        dx *= -0.15;
      }
      else if (x > 0 && dx > 0) {
        dx *= -0.15;
      }
    }
    if (y >= height || y <= 0) {
      if (y <= 0 && dy < 0) {
        dy *= -0.15;
      }
      else if (y > 0 && dy > 0) {
        dy *= -0.15;
      }
    }
    
    // Change Position
    x += dx;
    y += dy;
    
    
    // Set Line color
    stroke(255);
    
    // Calculate line points in ship
    Point[] points = {new Point(-20, 15),
                      new Point(-15,-10),
                      new Point( -2,  0),
                      new Point(-10,-15),
                      new Point(  0,-30),
                      new Point( 10,-15),
                      new Point(  2,  0),
                      new Point( 15,-10),
                      new Point( 20, 15),
                      new Point(  0,  5),
                      new Point(-20, 15)};
                      
    for (Point p : points) {
      p.Rotate(direction);
      p.Translate(x, y);
    }
    
    
    // Draw Ship
    Point q = new Point(0, 0);
    for (Point p : points) {
      if (q.x != 0 && q.y != 0) {
        Line(p, q);
      }
      q = p;
    }
    
    
    // Check for damage
    Triangle t = new Triangle(points[0].x,points[0].y,points[4].x,points[4].y,points[8].x,points[8].y);
    
    for (int i = 0; i < Square_Enemys.size(); i++) {
      Square_Enemy e = (Square_Enemy)Square_Enemys.get(i);
      if (checkTriCollision(e.x, e.y, t)) {
        health-=0.1;
      }
    }
    for (int i = 0; i < Spawner_Enemys.size(); i++) {
      Spawner_Enemy e = (Spawner_Enemy)Spawner_Enemys.get(i);
      if (checkTriCollision(e.x, e.y, t)) {
        health-=0.1;
      }
    }
    for (int i = 0; i < Snake_Enemys.size(); i++) {
      Snake_Enemy e = (Snake_Enemy)Snake_Enemys.get(i);
      for (int j = 0; j < e.links.size(); j+=(e.size/2-5)) {
        
        Snake_Link link = (Snake_Link)e.links.get(j);
        
        if (checkTriCollision(link.x, link.y, t)) {
          health-=0.1;
        }
      }
    }
    
    fill(0,255,0);
    rect(0, height-10, width/100*health, 10);
  }
}
