package br.com.aoj.game;

import br.com.aoj.core.AudioManager;
import br.com.aoj.core.ImageManager;
import java.io.IOException;

public class Explosao2 extends Explosao {

	public Explosao2(int x, int y) {
		super(x, y);
		try {
			animation = ImageManager.getInstance().loadSpriteAnimation("explosao2.gif", 6);
			animation.setLoop(false);
			animation.setSpeed(10);
			sound = AudioManager.getInstance().loadAudio("explosao2.wav");
		} catch (IOException ex) {
		}
	}
}
