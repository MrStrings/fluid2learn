package br.com.aoj.game;

import br.com.aoj.core.AudioManager;
import br.com.aoj.core.ImageManager;
import br.com.aoj.core.SpriteAnimation;
import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Explosao extends Entidade {

	SpriteAnimation animation;
	AudioClip sound;

	public Explosao(int x, int y) {
		super(x, y, 0);
		try {
			animation = ImageManager.getInstance().loadSpriteAnimation("explosao.gif", 7);
			animation.setLoop(false);
			animation.setSpeed(2);
			sound = AudioManager.getInstance().loadAudio("explosao.wav");
		} catch (IOException ex) {
		}
	}

	@Override
	public void update(int currentTick) {
		if (sound != null) {
			sound.play();
			sound = null;
		}
		animation.update(currentTick);
		if (animation.finished()) {
			energia = 0;
		}
	}

	@Override
	public BufferedImage getImage() {
		return animation.getImage();
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(getImage(), pos.x - getImage().getWidth() / 2, pos.y - getImage().getHeight() / 2, null);
	}
}
