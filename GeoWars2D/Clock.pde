void Clock (int time) {
  textSize(32);
  fill(155);
  text( ((time/1000) + ":" + ((time/10)%100)), 5, 35);
}
