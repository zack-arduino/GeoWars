int mode_l = 1;
int mode   = 0;
final int MODE_MENU    = 0;
final int MODE_OPTIONS = 1;
final int MODE_GAME    = 2;


Button Play_Game;
Button Options;

Button Back_To_Menu;


Ship player;

void setup() {
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

void draw() {
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

void draw_splash_screen() {
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

boolean sketchFullScreen() {
  //return true;
  return false;
}
