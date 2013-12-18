class Rect {
  public int[] x = {0, 0, 0, 0};
  public int[] y = {0, 0, 0, 0};
  public int[] z = {0, 0, 0, 0};
  
  Rect(int x, int y, int h, int w) {
    this.x[0] = x;
    this.y[0] = y;
    
    this.x[1] = x + w;
    this.y[1] = y;
    
    this.x[2] = x + w;
    this.y[2] = y + h;
    
    this.x[3] = x;
    this.y[3] = y + h;
  }
  
  Rect(int x, int y, int z1, int z2, int h, int w) {
    this.x[0] = x;
    this.y[0] = y;
    this.z[0] = z1;
    
    this.x[1] = x + w;
    this.y[1] = y;
    this.z[0] = z2;
    
    this.x[2] = x + w;
    this.y[2] = y + h;
    this.z[0] = z2;
    
    this.x[3] = x;
    this.y[3] = y + h;
    this.z[0] = z1;
  }
  
  void Draw() {
  }
}


class Rect3d {
  int[] x = {0, 0, 0, 0, 0, 0, 0, 0};
  int[] y = {0, 0, 0, 0, 0, 0, 0, 0};
  int[] z = {0, 0, 0, 0, 0, 0, 0, 0};
}
