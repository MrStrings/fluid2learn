package br.com.aoj.game;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

public abstract class Entidade {

	Point pos;
	int speed;
	int energia;

	public Entidade(int x, int y, int speed) {
		pos = new Point(x, y);
		this.speed = speed;
		this.energia = 1;
	}

	public abstract void update(int currentTick);

	public void render(Graphics2D g) {
		g.drawImage(getImage(), pos.x - getImage().getWidth() / 2, pos.y, null);
	}

	public abstract BufferedImage getImage();
}
