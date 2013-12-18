import java.awt.Robot;
/**
 * Move Eye. 
 * by Simon Greenwold.
 * 
 * The camera lifts up (controlled by mouseY) while looking at the same point.
 */

void setup() {
  size(displayWidth, displayHeight, P3D);
  fill(204);
}

float x = 0;
float y = 0;

float d = PI/5;
float dy = 0;
void draw() {
  lights();
  background(0);
  
  d  += radians(360*((float)(mouseX-width/2)/((float)width/2)));
  dy += radians(360*((float)(mouseY-height/2)/((float)height/2)));
  
  if (checkKey(KEY_w)) {
    x += cos(d) * 1;
    y += sin(d) * 1;
  }
  if (checkKey(KEY_s)) {
    x += cos(d) * -1;
    y += sin(d) * -1;
  }
  if (checkKey(KEY_d)) {
    x += cos(d+radians(90)) * 1;
    y += sin(d+radians(90)) * 1;
  }
  if (checkKey(KEY_a)) {
    x += cos(d+radians(90)) * -1;
    y += sin(d+radians(90)) * -1;
  }
  
  if (abs(dy) > radians(90)) {
    dy = (dy/abs(dy)) * radians(90);
  }
  
  camera(x, 0, y, x+cos(d), sin(dy), y+sin(d),
         0.0, 1.0, 0.0);
  
  noStroke();
  box(90);
  
  box(90);
  translate(200, 0, 0); 
  box(90);
  stroke(255);
  line(-100, 0, 0, 100, 0, 0);
  line(0, -100, 0, 0, 100, 0);
  line(0, 0, -100, 0, 0, 100);
  
  try {
    Robot robot = new Robot();
    robot.mouseMove(width/2, height/2);
  }
  catch (Exception e) {}
  
}

boolean sketchFullScreen() {
  return true;
  //return false;
}
    
