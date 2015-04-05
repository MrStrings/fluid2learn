package br.com.aoj.game;

import java.io.IOException;

public class Inimigo1 extends Inimigo {

	public Inimigo1(int x, int y) {
		super(x, y, 2);
		energia = 1;
		try {
			loadSprites("inimigo1.gif");
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
