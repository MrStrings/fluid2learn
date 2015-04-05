package br.com.aoj.game;

import br.com.aoj.core.ImageManager;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Inimigo extends Nave {

	ArrayList<Tiro> tiros;
	int intervaloTiro;

	public Inimigo(int x, int y, int speed, ArrayList<Tiro> tiros) {
		super(x, y, speed);
		this.tiros = tiros;
		intervaloTiro = 120;
	}

	@Override
	public void loadSprites(String filename) throws IOException {
		super.loadSprites(filename);
		sprites[0] = ImageManager.getInstance().flipImage(sprites[0], false,
				true);
		sprites[1] = ImageManager.getInstance().flipImage(sprites[1], false,
				true);
		sprites[2] = ImageManager.getInstance().flipImage(sprites[2], false,
				true);
	}

	@Override
	public void update(int currentTick) {
		if (currentTick % intervaloTiro == 0) {
			try {
				tiros.add(new TiroInimigo(pos.x, pos.y + getImage().
						getHeight()));
			} catch (IOException ex) {
			}
		}
		pos.y += speed;
		if (pos.y > 600) {
			energia = 0;
		}
	}
}
