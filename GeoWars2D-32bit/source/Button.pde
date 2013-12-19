class Button {
  public  int     x;
  public  int     y;
  public  int     h;
  public  int     w;
  public  String  s;
  public  Boolean pressed = false;
  private Boolean state   = false;
  private Boolean state_l = false;
  public  color   norm;
  public  color   edge;
  public  color   hover;
  public  color   clicked;
  
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
