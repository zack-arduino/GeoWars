void Line(Point p1, Point p2) {
  line( p1.x, p1.y, p2.x, p2.y);
}

class Point {
  public float x;
  public float y;
  
  Point(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  void Rotate(float deg) {
    float x = (this.x)*cos(radians(deg)) - (this.y)*sin(radians(deg));
    float y = (this.x)*sin(radians(deg)) + (this.y)*cos(radians(deg));
    this.x = x;
    this.y = y;
  }
  
  void Translate(float x, float y) {
    this.x += x;
    this.y += y;
  }
}
