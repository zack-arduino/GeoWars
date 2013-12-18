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

final boolean PRINT_KEY_CODE = true;

boolean[] keys = new boolean[526];

boolean checkKey(int k)
{
  if (keys.length >= k) {
    return keys[k];  
  }
  return false;
}


void keyPressed()
{ 
  keys[keyCode] = true;
  
  if (PRINT_KEY_CODE) {
    println(keyCode);
  }
}

void keyReleased()
{ 
  keys[keyCode] = false; 
}
