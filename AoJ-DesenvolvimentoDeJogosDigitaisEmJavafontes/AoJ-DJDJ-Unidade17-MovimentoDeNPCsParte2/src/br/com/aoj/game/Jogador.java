package br.com.aoj.game;

import br.com.aoj.core.InputManager;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

public class Jogador extends Nave {

	ArrayList<Tiro> tiros;
	int esperaTiro;

	public Jogador(int x, int y, ArrayList<Tiro> tiros) {
		super(x, y, 5);
		energia = 10;
		esperaTiro = 0;
		this.tiros = tiros;
		try {
			loadSprites("player.gif");
		} catch (IOException ex) {
		}
	}

	@Override
	public void update(int currentTick) {
		dir = 0;
		if (InputManager.getInstance().isPressed(KeyEvent.VK_LEFT)) {
			dir = -1;
		} else if (InputManager.getInstance().isPressed(KeyEvent.VK_RIGHT)) {
			dir = 1;
		}
		pos.x += speed * dir;
		if (InputManager.getInstance().isPressed(KeyEvent.VK_UP)) {
			pos.y -= speed;
		} else if (InputManager.getInstance().isPressed(KeyEvent.VK_DOWN)) {
			pos.y += speed;
		}

		esperaTiro++;
		if (InputManager.getInstance().isPressed(KeyEvent.VK_SPACE) && esperaTiro > 10) {
			try {
				tiros.add(new TiroJogador(pos.x, pos.y));
				esperaTiro = 0;
			} catch (IOException ex) {
			}
		}
	}
}
