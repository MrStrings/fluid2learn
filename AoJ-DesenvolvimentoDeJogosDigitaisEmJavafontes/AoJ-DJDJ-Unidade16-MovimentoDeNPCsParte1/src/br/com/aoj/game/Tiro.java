package br.com.aoj.game;

import br.com.aoj.core.AudioManager;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Tiro extends Entidade {

	AudioClip sound;

	public Tiro(int x, int y, int speed) throws IOException {
		super(x, y, speed);
		energia = 1;
		sound = AudioManager.getInstance().loadAudio("gun.wav");
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
	public void render(Graphics2D g) {
		g.setColor(Color.red);
		g.drawLine(pos.x-1, pos.y, pos.x-1, pos.y + 10);
		g.drawLine(pos.x+1, pos.y, pos.x+1, pos.y + 10);
		g.setColor(Color.white);
		g.drawLine(pos.x, pos.y, pos.x, pos.y + 10);
	}

	@Override
	public BufferedImage getImage() {
		return null;
	}
}
