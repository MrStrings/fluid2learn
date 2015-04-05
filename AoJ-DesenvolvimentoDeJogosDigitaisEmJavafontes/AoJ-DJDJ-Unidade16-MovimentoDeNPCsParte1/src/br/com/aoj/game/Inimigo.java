package br.com.aoj.game;

import br.com.aoj.core.ImageManager;
import java.io.IOException;

public abstract class Inimigo extends Nave {

	public Inimigo(int x, int y, int speed) {
		super(x, y, speed);
	}

	@Override
	public void loadSprites(String filename) throws IOException {
		super.loadSprites(filename);
		sprites[0] = ImageManager.getInstance().flipImage(sprites[0], false, true);
		sprites[1] = ImageManager.getInstance().flipImage(sprites[1], false, true);
		sprites[2] = ImageManager.getInstance().flipImage(sprites[2], false, true);
	}
}
