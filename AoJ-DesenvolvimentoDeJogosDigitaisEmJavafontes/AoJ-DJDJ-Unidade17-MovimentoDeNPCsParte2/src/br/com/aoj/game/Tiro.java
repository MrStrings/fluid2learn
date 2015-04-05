package br.com.aoj.game;

import java.applet.AudioClip;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Tiro extends Entidade {

	AudioClip sound;

	public Tiro(int x, int y, int speed) throws IOException {
		super(x, y, speed);
		energia = 1;
	}

	public void update(int currentTick) {
		if (sound != null) {
			sound.play();
			sound = null;
		}
		pos.y -= speed;
		if (pos.y < 0) {
			energia = 0;
		}
	}

	@Override
	public BufferedImage getImage() {
		return null;
	}
}
