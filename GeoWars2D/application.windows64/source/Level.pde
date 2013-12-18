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
  
  void run() {
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
  
  void pump_time() {
    level_time = millis()-start_time;
  }
  
  void update() {
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
  
  int get_time() {
    pump_time();
    return level_time;
  }
}




Level Level_1;

void Create_Level_1() {
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
