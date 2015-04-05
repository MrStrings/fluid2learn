package br.com.aoj.game;

import br.com.aoj.core.ImageManager;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Nave extends Entidade {

	int dir;
	BufferedImage[] sprites;

	public Nave(int x, int y, int speed) {
		super(x, y, speed);
		sprites = new BufferedImage[3];
		dir = 0;
	}

	public void update(int currentTick) {
	}

	public void loadSprites(String filename) throws IOException {
		sprites[0] = ImageManager.getInstance().loadImage(filename, 58, 0,
				44, 99);
		sprites[1] = ImageManager.getInstance().loadImage(filename, 0, 0,
				58, 99);
		sprites[2] = ImageManager.getInstance().flipImage(sprites[0], true,
				false);
	}

	public BufferedImage getImage() {
		// Retorna a imagem conforme o movimento da nave.
		// Se dir = -1 (esquerda), retona o sprite 0;
		// Se dir =  0 (cantro), retona o sprite 1;
		// Se dir =  1 (direita), retona o sprite 2;
		return sprites[dir + 1];
	}
}