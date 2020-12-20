package warcraftTD;

public abstract class Monster {
  // Position du monstre à l'instant t
  Position p;
  // Vitesse du monstre
  double speed;
  // Position du monstre à l'instant t+1
  Position nextP;
  // Boolean pour savoir si le monstre à atteint le chateau du joueur
  boolean reached;
  // Compteur de déplacement pour savoir si le monstre à atteint le chateau du joueur
  int checkpoint = 0;

  public Monster(Position p) {
    this.p = p;
    this.nextP = new Position(p);
  }

  /**
   * Déplace le monstre en fonction de sa vitesse sur l'axe des x et des y et de sa prochaine position.
   */
  public void move() {
    // Mesure sur quel axe le monstre se dirige.
    double dx = this.nextP.x - this.p.x;
    double dy = this.nextP.y - this.p.y;
    if (dy + dx != 0) {
      // Mesure la distance à laquelle le monstre à pu se déplacer.
      double ratioX = dx / (Math.abs(dx) + Math.abs(dy));
      double ratioY = dy / (Math.abs(dx) + Math.abs(dy));
			this.p.x += ratioX * this.speed;
			this.p.y += ratioY * this.speed;
    }
  }

  public void update() {
		this.move();
		this.draw();
		this.checkpoint++;
  }

  /**
   * Fonction abstraite qui sera instanciée dans les classes filles pour afficher le monstre sur le plateau de jeu.
   */
  public abstract void draw();
}
