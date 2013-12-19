import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class GeoWars extends PApplet {

int mode_l = 1;
int mode   = 0;
final int MODE_MENU    = 0;
final int MODE_OPTIONS = 1;
final int MODE_GAME    = 2;


Button Play_Game;
Button Options;

Button Back_To_Menu;


Ship player;

public void setup() {
  // Full screen
  //size(displayWidth, displayHeight, P3D);
  size(700, 600, P2D);
  
  // Main Menu
  Play_Game    = new Button(width/3, height/5,    200, 40, "Play Game");
  Options      = new Button(width/3, height/5+50, 200, 40, "Options");
  
  // Options Menu
  Back_To_Menu = new Button(width/3, height/5,    200, 40, "Main Menu");
}

int respon_rate  = 2000;
int last_u       = 0;

int respon_rate1 = 5000;
int last_u1      = 0;

int respon_rate2 = 6000;
int last_u2      = 0;

public void draw() {
  // Clear Screen
  background(0);
  
  if (mode == MODE_MENU) {
    if (mode != mode_l) {
      // Remove all game objects
      Square_Enemys_remove();
      Snake_Enemys_remove();
      Spawner_Enemys_remove();
      thrusters_remove();
      explosion_remove();
      bullets_remove();
      
      // Update mode_l
      mode_l = mode;
    }
    // Draw Screen
    draw_splash_screen();
    
    // Update buttons
    Play_Game.update();
    Options.update();
    
    if (Play_Game.pressed) {
      mode = MODE_GAME;
    }
    if (Options.pressed) {
      mode = MODE_OPTIONS;
    }
  }
  
  if (mode == MODE_OPTIONS) {
    if (mode != mode_l) {
      // Update mode_l
      mode_l = mode;
    }
    // Draw Screen
    draw_splash_screen();
    
    // Update buttons
    Back_To_Menu.update();
    
    if (Back_To_Menu.pressed) {
      mode = MODE_MENU;
    }
  }
  
  if (mode == MODE_GAME) {
    if (mode != mode_l) {
      // Create player
      player = new Ship();
      Create_Level_1();
      // Update mode_l
      mode_l = mode;
    }
    
    player.control(checkKey(KEY_w), checkKey(KEY_a), checkKey(KEY_s), checkKey(KEY_d), checkKey(KEY_SPACE), checkKey(KEY_z));
    
    // Update all game objects
    
    player.update();
    bullets_update();
    thrusters_update();
    
    Square_Enemys_update();
    Snake_Enemys_update();
    Spawner_Enemys_update();

    explosion_update();
    
    // Update Level Events
    Level_1.update();
    Clock(Level_1.get_time());
    // End game if player dies
    if (player.health <= 0 || (Level_1.done && Snake_Enemys.size() == 0 && Spawner_Enemys.size() == 0 && Square_Enemys.size() == 0)) {
      mode = MODE_MENU;
    }
  }
}

public void draw_splash_screen() {
  stroke(0, 150, 0);
  for (int i = 10; i > 0; i--) {
    arc(width/3*2+i+(15*10), height/3-20, 600, 60, PI/8+PI, -(PI/8)+TWO_PI);
  }
  
  noStroke();
  fill(0, 255, 0);
  textSize(52);
  text("G E O   W A R S", width/3*2-52, height/3-52);
  
  stroke(255);
  noFill();
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
    p.Rotate(45);
    p.Translate(width/3*2, height/3);
  }
    
    
  // Draw Ship
  Point q = new Point(0, 0);
  for (Point p : points) {
    if (q.x != 0 && q.y != 0) {
      Line(p, q);
    }
    q = p;
  }
  
  // Bullets
  Point p1 = new Point(-15, -10);
  Point p2 = new Point( 15, -10);
  p1.Rotate(45);
  p1.Translate(width/3*2, height/3);
  p2.Rotate(45);
  p2.Translate(width/3*2, height/3);
  
  float d = radians(-45);
    
  float dx = cos(d) * 20;
  float dy = sin(d) * 20;
  
  p1.Translate(dx, dy);
  p2.Translate(dx, dy);
  
  for (int i = 0; i < 2; i++) {
    p1.Translate(dx, dy);
    p2.Translate(dx, dy);
    noStroke();
    fill(0, 0, 255);
    ellipse((int)p1.x, (int)p1.y, 6, 6);
    ellipse((int)p2.x, (int)p2.y, 6, 6);
  }
  
}

public boolean sketchFullScreen() {
  //return true;
  return false;
}
ArrayList  bullets = new ArrayList();

public void bullets_remove() {
  for (int i = 0; i < bullets.size(); i += 0) {
    bullets.remove(0);
  }
}

public void bullets_add(Bullet b) {
  bullets.add(b);
}

public void bullets_update() {
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
    
    
class Button {
  public  int     x;
  public  int     y;
  public  int     h;
  public  int     w;
  public  String  s;
  public  Boolean pressed = false;
  private Boolean state   = false;
  private Boolean state_l = false;
  public  int   norm;
  public  int   edge;
  public  int   hover;
  public  int   clicked;
  
  public Button() {
    this.x       = 0;
    this.y       = 0;
    this.h       = 50;
    this.w       = 100;
    this.s       = "Test";
    this.norm    = color(255);
    this.edge    = color(155);
    this.hover   = color(0, 153, 204);
    this.clicked = color(55);
  }
  
  public Button(int x, int y, int w, int h, String s) {
    this.x       = x;
    this.y       = y;
    this.h       = h;
    this.w       = w;
    this.s       = s;
    this.norm    = color(255);
    this.edge    = color(155);
    this.hover   = color(184, 207, 229);
    this.clicked = color(55);
  }
  
  public void update() {
    stroke(0);
    
    if (mouseX >= this.x && mouseX <= this.x+this.w && 
        mouseY >= this.y && mouseY <= this.y+this.h){
      boolean c = mousePressed;
      
      this.state = Mouse_Pressed;
      Mouse_Pressed = false;
      
      if (c) {
        stroke(0);
        fill(edge);
        rect(this.x, this.y, this.w, this.h, 3);
        
        noFill();
        for (int i = 0; i < 5; i++) {
          float r = red(edge)-((red(edge)-red(clicked))/i);
          float g = green(edge)-((green(edge)-green(clicked))/i);
          float b = blue(edge)-((blue(edge)-blue(clicked))/i);
          stroke(r, g, b);
          rect(this.x+i, this.y+i, this.w-i*2, this.h-i*2, 3);
        }
      }
      else {
        stroke(0);
        fill(norm);
        rect(this.x, this.y, this.w, this.h, 3);
        
        noFill();
        for (int i = 0; i < 6; i++) {
          float r = red(norm)-((red(norm)-red(hover))/i);
          float g = green(norm)-((green(norm)-green(hover))/i);
          float b = blue(norm)-((blue(norm)-blue(hover))/i);
          stroke(r, g, b);
          rect(this.x+i, this.y+i, this.w-i*2, this.h-i*2, 3);
        }
      }
    }
    else {
      stroke(0);
      fill(norm);
      rect(this.x, this.y, this.w, this.h, 3);
      
      noFill();
      for (int i = 0; i < 5; i++) {
        float r = red(norm)-((red(norm)-red(edge))/i);
        float g = green(norm)-((green(norm)-green(edge))/i);
        float b = blue(norm)-((blue(norm)-blue(edge))/i);
        stroke(r, g, b);
        rect(this.x+i, this.y+i, this.w-i*2, this.h-i*2, 3);
      }
    }
    textSize((this.h/3)*2);
    fill(0);
    text(this.s, this.x+7, this.y+this.h-(this.h/4));
    
    if (this.state && this.state != this.state_l) {
      this.pressed = true;
    }
    else {
      this.pressed = false;
    }
    this.state_l = this.state;
  }
}
public void Clock (int time) {
  textSize(32);
  fill(155);
  text( ((time/1000) + ":" + ((time/10)%100)), 5, 35);
}
ArrayList  explosions = new ArrayList();

public void explosion_remove() {
  for (int i = 0; i < explosions.size(); i += 0) {
    explosions.remove(0);
  }
}

public void explosion_add(int x, int y, int c) {
  for (int i = 0; i < 360; i+=5) {
    if ((int)random(4) == 1) {
      explosions.add(new Explosion(x, y, i, 2, c));
    }
  }
}

public void explosion_update() {
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
  public int c;
  
  public Explosion(int x, int y, float direction, float speed, int c) {
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
final int KEY_w =     87;
final int KEY_a =     65;
final int KEY_s =     83;
final int KEY_d =     68;
final int KEY_q =     81;
final int KEY_e =     69;
final int KEY_z =     90;
final int KEY_x =     88;
final int KEY_SPACE = 32;
final int KEY_UP =    38;
final int KEY_LEFT =  37;
final int KEY_DOWN =  40;
final int KEY_RIGHT = 39;
final int KEY_1 =     49;
final int KEY_2 =     50;
final int KEY_3 =     51;
final int KEY_4 =     52;

final boolean PRINT_KEY_CODE = false;

boolean[] keys = new boolean[526];

public boolean checkKey(int k)
{
  if (keys.length >= k) {
    return keys[k];  
  }
  return false;
}


public void keyPressed()
{ 
  keys[keyCode] = true;
  
  if (PRINT_KEY_CODE) {
    println(keyCode);
  }
}

public void keyReleased()
{ 
  keys[keyCode] = false; 
}
class Event {
  public boolean completed = false;
  public int time;
  
  public int type;
  public Snake_Enemy es   = null;
  public Spawner_Enemy ep = null;
  public Square_Enemy eq  = null;
  
  Event(Snake_Enemy e, int time) {
    this.es   = e;
    this.time = time;
    this.type = 0;
  }
  
  Event(Spawner_Enemy e, int time) {
    this.ep   = e;
    this.time = time;
    this.type = 1;
  }
  
  Event(Square_Enemy e, int time) {
    this.eq   = e;
    this.time = time;
    this.type = 2;
  }
  
  public void run() {
    if (type == 0) {
      Snake_Enemys_add(es);
    }
    else if (type == 1) {
      Spawner_Enemys_add(ep);
    }
    else if (type == 2) {
      Square_Enemys_add(eq);
    }
    completed = true;
  }
      
}

class Level {
  public Event[] events;
  public int start_time;
  public int level_time;
  public boolean done = false;
  
  Level(Event[] events) {
    this.events = events;
    start_time = millis();
    level_time = 0;
  }
  
  public void pump_time() {
    level_time = millis()-start_time;
  }
  
  public void update() {
    pump_time();
    boolean done = true;
    for (Event event : events) {
      if (event.time <= level_time && event.completed == false) {
        event.run();
      }
      if (event.completed == false) {
        done = false;
      }
    }
    this.done = done;
  }
  
  public int get_time() {
    pump_time();
    return level_time;
  }
}




Level Level_1;

public void Create_Level_1() {
  Event[] events = {new Event(new Square_Enemy(width/4, height/4),             100),
                    new Event(new Square_Enemy(3*(width/4), 3*(height/4)),     100),
                    
                    new Event(new Square_Enemy(width/4, height/4),             6000),
                    new Event(new Square_Enemy(3*(width/4), 3*(height/4)),     6000),
                    new Event(new Square_Enemy(width/4, height/4),             6000),
                    new Event(new Square_Enemy(3*(width/4), 3*(height/4)),     6000),
                    
                    new Event(new Square_Enemy(width/4, height/4),             7000),
                    new Event(new Square_Enemy(3*(width/4), 3*(height/4)),     7000),
                    
                    new Event(new Square_Enemy(width/4, height/4),             8000),
                    new Event(new Square_Enemy(3*(width/4), 3*(height/4)),     8000),
                    
                    new Event(new Square_Enemy(width/4, height/4),             9000),
                    new Event(new Square_Enemy(3*(width/4), 3*(height/4)),     9000),
                    
                    new Event(new Square_Enemy(width/4, height/4),             10000),
                    new Event(new Square_Enemy(3*(width/4), 3*(height/4)),     10000),
                    
                    new Event(new Spawner_Enemy(width/4, height/4, 5),         11000),
                    new Event(new Spawner_Enemy(3*(width/4), 3*(height/4), 5), 11000),
                    
                    new Event(new Square_Enemy(width/4, height/4),             12000),
                    new Event(new Square_Enemy(3*(width/4), 3*(height/4)),     12000),
                    
                    new Event(new Square_Enemy(width/4, height/4),             13000),
                    new Event(new Square_Enemy(3*(width/4), 3*(height/4)),     13000),
                    
                    new Event(new Spawner_Enemy(width/4, height/4, 5),         14000),
                    new Event(new Spawner_Enemy(3*(width/4), 3*(height/4), 5), 14000),
                    new Event(new Square_Enemy(width/4, height/4),             14000),
                    new Event(new Square_Enemy(3*(width/4), 3*(height/4)),     14000),
                    
                    new Event(new Spawner_Enemy(width/4, height/4, 5),         20000),
                    new Event(new Spawner_Enemy(3*(width/4), 3*(height/4), 5), 20000),
                    new Event(new Square_Enemy(width/4, height/4),             20500),
                    new Event(new Square_Enemy(3*(width/4), 3*(height/4)),     20500),
                    
                    new Event(new Snake_Enemy(width/4, 3*(height/4)),          25000),
                    new Event(new Snake_Enemy(3*(width/4), height/4),          25000),
                    new Event(new Square_Enemy(width/4, height/4),             23000),
                    new Event(new Square_Enemy(3*(width/4), 3*(height/4)),     23000),
                    new Event(new Spawner_Enemy(width/4, height/4, 5),         27000),
                    new Event(new Spawner_Enemy(3*(width/4), 3*(height/4), 5), 27000),
                    
                    new Event(new Snake_Enemy(width/4, 3*(height/4)),          44000),
                    new Event(new Snake_Enemy(3*(width/4), height/4),          48000),
                    new Event(new Square_Enemy(width/4, height/4),             49000),
                    new Event(new Square_Enemy(3*(width/4), 3*(height/4)),     49000),
                    new Event(new Snake_Enemy(width/4, 3*(height/4)),          52000),
                    new Event(new Square_Enemy(width/4, height/4),             53000),
                    new Event(new Square_Enemy(3*(width/4), 3*(height/4)),     53000),
                    new Event(new Snake_Enemy(3*(width/4), height/4),          56000),
                    new Event(new Snake_Enemy(width/4, 3*(height/4)),          60000),
                    new Event(new Square_Enemy(width/4, height/4),             61000),
                    new Event(new Square_Enemy(3*(width/4), 3*(height/4)),     61000),
                    new Event(new Snake_Enemy(3*(width/4), height/4),          64000),
                    new Event(new Square_Enemy(width/4, height/4),             65000),
                    new Event(new Square_Enemy(3*(width/4), 3*(height/4)),     65000),
                    new Event(new Snake_Enemy(width/4, 3*(height/4)),          68000),
                    new Event(new Snake_Enemy(3*(width/4), height/4),          72000),
                    
                    new Event(new Spawner_Enemy(width/4, height/4, 5),         75000),
                    new Event(new Spawner_Enemy(3*(width/4), 3*(height/4), 5), 75000),
                    new Event(new Square_Enemy(width/4, height/4),             75500),
                    new Event(new Square_Enemy(3*(width/4), 3*(height/4)),     75500),
                    
                    new Event(new Snake_Enemy(width/4, 3*(height/4)),          80000),
                    new Event(new Snake_Enemy(3*(width/4), height/4),          82000),
                    new Event(new Snake_Enemy(width/4, 3*(height/4)),          86000),
                    new Event(new Snake_Enemy(3*(width/4), height/4),          88000),
                    
                    new Event(new Spawner_Enemy(width/4, 3*(height/4), 5),     90000),
                    new Event(new Spawner_Enemy(3*(width/4), (height/4), 5),   90000),
                    new Event(new Square_Enemy(width/4, height/4),             91000),
                    new Event(new Square_Enemy(3*(width/4), 3*(height/4)),     91000),
                    
                    new Event(new Spawner_Enemy(width/4, 3*(height/4), 5),     92000),
                    new Event(new Spawner_Enemy(3*(width/4), (height/4), 5),   92000),
                    new Event(new Square_Enemy(width/4, height/4),             93000),
                    new Event(new Square_Enemy(3*(width/4), 3*(height/4)),     93000),
                    
                    new Event(new Spawner_Enemy(width/4, 3*(height/4), 5),     94000),
                    new Event(new Spawner_Enemy(3*(width/4), (height/4), 5),   94000),
                    new Event(new Square_Enemy(width/4, height/4),             95000),
                    new Event(new Square_Enemy(3*(width/4), 3*(height/4)),     95000),
                    
                    new Event(new Spawner_Enemy(width/4, 3*(height/4), 5),     96000),
                    new Event(new Spawner_Enemy(3*(width/4), (height/4), 5),   96000),
                    new Event(new Square_Enemy(width/4, height/4),             97000),
                    new Event(new Square_Enemy(3*(width/4), 3*(height/4)),     97000),
                    
                    new Event(new Spawner_Enemy(width/4, 3*(height/4), 5),     98000),
                    new Event(new Spawner_Enemy(3*(width/4), (height/4), 5),   98000),
                    new Event(new Square_Enemy(width/4, height/4),             99000),
                    new Event(new Square_Enemy(3*(width/4), 3*(height/4)),     99000),
                  
                    new Event(new Spawner_Enemy(width/4, 3*(height/4), 5),     100000),
                    new Event(new Spawner_Enemy(3*(width/4), (height/4), 5),   100000),
                    new Event(new Square_Enemy(width/4, height/4),             100000),
                    new Event(new Square_Enemy(3*(width/4), 3*(height/4)),     100000),};
  
  Level_1 = new Level(events);
}
boolean Mouse_Pressed = false;

public void mousePressed() {
  Mouse_Pressed = true;
}

public void mouseClicked() {
}

public void mouseReleased() {
  Mouse_Pressed = false;
}
public void Line(Point p1, Point p2) {
  line( p1.x, p1.y, p2.x, p2.y);
}

class Point {
  public float x;
  public float y;
  
  Point(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public void Rotate(float deg) {
    float x = (this.x)*cos(radians(deg)) - (this.y)*sin(radians(deg));
    float y = (this.x)*sin(radians(deg)) + (this.y)*cos(radians(deg));
    this.x = x;
    this.y = y;
  }
  
  public void Translate(float x, float y) {
    this.x += x;
    this.y += y;
  }
}
class Ship {
  public float x = width/2;
  public float y = height/2;
  public float dx;
  public float dy;
  
  public float direction = 0;
  public float speed;
  
  public boolean thruster_on = false;
  public float thruster_strength = 0.001f;
  
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
        dx *= -0.15f;
      }
      else if (x > 0 && dx > 0) {
        dx *= -0.15f;
      }
    }
    if (y >= height || y <= 0) {
      if (y <= 0 && dy < 0) {
        dy *= -0.15f;
      }
      else if (y > 0 && dy > 0) {
        dy *= -0.15f;
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
        health-=0.1f;
      }
    }
    for (int i = 0; i < Spawner_Enemys.size(); i++) {
      Spawner_Enemy e = (Spawner_Enemy)Spawner_Enemys.get(i);
      if (checkTriCollision(e.x, e.y, t)) {
        health-=0.1f;
      }
    }
    for (int i = 0; i < Snake_Enemys.size(); i++) {
      Snake_Enemy e = (Snake_Enemy)Snake_Enemys.get(i);
      for (int j = 0; j < e.links.size(); j+=(e.size/2-5)) {
        
        Snake_Link link = (Snake_Link)e.links.get(j);
        
        if (checkTriCollision(link.x, link.y, t)) {
          health-=0.1f;
        }
      }
    }
    
    fill(0,255,0);
    rect(0, height-10, width/100*health, 10);
  }
}
ArrayList  Snake_Enemys = new ArrayList();

public void Snake_Enemys_remove() {
  for (int i = 0; i < Snake_Enemys.size(); i+=0) {
    Snake_Enemys.remove(i);
  }
}

public void Snake_Enemys_add(Snake_Enemy e) {
  Snake_Enemys.add(e);
}

public void Snake_Enemys_update() {
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
  
  public int c = color(255, 132, 0);
  
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
        dx-=0.1f;
        
        if (dx < -speed) {
          dx = -speed;
        }
      }
      else if (x < player.x) {
        dx+=0.1f;
        
        if (dx > speed) {
          dx = speed;
        }
      }
      else {
        dx = 0;
      }
      
      if (y > player.y) {
        dy-=0.1f;
        
        if (dy < -speed) {
          dy = -speed;
        }
      }
      else if (y < player.y) {
        dy+=0.1f;
        
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
ArrayList Spawner_Enemys = new ArrayList();

public void Spawner_Enemys_remove() {
  for (int i = 0; i < Spawner_Enemys.size(); i += 0) {
    Spawner_Enemys.remove(0);
  }
}

public void Spawner_Enemys_add(Spawner_Enemy e) {
  Spawner_Enemys.add(e);
}

public void Spawner_Enemys_update() {
  for (int i = 0; i < Spawner_Enemys.size(); i++) {
    Spawner_Enemy e = (Spawner_Enemy)Spawner_Enemys.get(i);
    e.update();
    if (e.active == false) {
      Spawner_Enemys.remove(i);
    }
  }
}

class Spawner_Enemy {
  public float x;
  public float y;
  public float dx;
  public float dy;
  public float speed = 1;
  public int c = color(255, 0, 0);
  
  public float size     = 25;
  public float size_t   = 0;
  public float size_s   = 25;
  public boolean size_d = true;
  
  public boolean active = true;
  
  public int health = 5;
  
  public int carnation = 0;
  
  public Spawner_Enemy (float x, float y, int carnation) {
    this.carnation = carnation;
    health = carnation;
    size_s = 10 * carnation;
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
        
        if (health <= 0 && active == true) {
          if (carnation-1 != 0) {
            Spawner_Enemys_add(new Spawner_Enemy(this.x, this.y, carnation-1));
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
      if (size_d) {
        size = (size_t + size_s);
        size_t += 0.25f;
        if (size_t > 10) {
          size_d = false;
        }
      }
      else {
        size = (size_t + size_s);
        size_t -= 0.25f;
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
ArrayList  Square_Enemys = new ArrayList();

public void Square_Enemys_remove() {
  for (int i = 0; i < Square_Enemys.size(); i += 0) {
    Square_Enemys.remove(0);
  }
}

public void Square_Enemys_add(Square_Enemy e) {
  Square_Enemys.add(e);
}

public void Square_Enemys_update() {
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
  public int c = color(0, 255, 0);
  
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
        size_t += 0.25f;
        if (size_t > 10) {
          size_d = false;
        }
      }
      else {
        size = (size_t + size_s);
        size_t -= 0.25f;
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
ArrayList  thrusters = new ArrayList();

public void thrusters_remove() {
  for (int i = 0; i < thrusters.size(); i += 0) {
    thrusters.remove(0);
  }
}

public void thrusters_add(Thruster t) {
  thrusters.add(t);
}

public void thrusters_update() {
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
      fill(255-((255/25.0f)*distance), 0, 0);
      
      this.x += this.dx;
      this.y += this.dy;
      
      ellipse((int)this.x, (int)this.y, this.size, this.size);
    }
    
    if (distance >= 25) {
      active = false;
    }
  }
}
    
    
class Triangle {
  float point1x;
  float point1y;
  float point2x;
  float point2y;
  float point3x;
  float point3y;
  
  Triangle(float point1x,float point1y,float point2x,float point2y,float point3x,float point3y){
  this.point1x = point1x;
  this.point1y = point1y;
  this.point2x = point2x;
  this.point2y = point2y;
  this.point3x = point3x;
  this.point3y = point3y;        
  }
  
  public void drawTriangle() {
    triangle(point1x, point1y, point2x, point2y, point3x, point3y);
  }
}


public boolean checkTriCollision(float x, float y, Triangle t) {
  float tArea,t1Area,t2Area,t3Area;
  tArea  = triangleArea(t.point1x, t.point1y, t.point3x, t.point3y, t.point2x, t.point2y);
  t1Area = triangleArea(x,y, t.point2x, t.point2y, t.point3x, t.point3y);
  t2Area = triangleArea(x,y, t.point3x, t.point3y, t.point1x, t.point1y);
  t3Area = triangleArea(x,y, t.point2x, t.point2y, t.point1x, t.point1y);
  
  float totalArea = t1Area+t2Area+t3Area;
  return ((int)totalArea == (int)tArea);
}

public float triangleArea(float p1, float p2, float p3, float p4, float p5, float p6) {
  float a,b,c,d;
  a = p1 - p5;
  b = p2 - p6;
  c = p3 - p5;
  d = p4 - p6;
  return (0.5f* abs((a*d)-(b*c)));
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "GeoWars" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
