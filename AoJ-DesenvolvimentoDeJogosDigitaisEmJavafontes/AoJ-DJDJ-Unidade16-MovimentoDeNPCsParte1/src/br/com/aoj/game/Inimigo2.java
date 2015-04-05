package br.com.aoj.game;

import java.io.IOException;

public class Inimigo2 extends Inimigo {

	public Inimigo2(int x, int y) {
		super(x, y, 1);
		energia = 5;
		try {
			loadSprites("inimigo2.gif");
		} catch (IOException ex) {
		}
	}

	@Override
	public void update(int currentTick) {
		pos.y += speed;
		if (pos.y > 600) {
			energia = 0;
		}
	}
}
