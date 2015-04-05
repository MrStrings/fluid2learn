package br.com.aoj.game;

import br.com.aoj.core.AudioManager;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;

public class TiroJogador extends Tiro {

	public TiroJogador(int x, int y) throws IOException {
		super(x, y, 10);
		sound = AudioManager.getInstance().loadAudio("gun.wav");
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.blue);
		g.drawLine(pos.x - 1, pos.y, pos.x - 1, pos.y + 10);
		g.drawLine(pos.x + 1, pos.y, pos.x + 1, pos.y + 10);
		g.setColor(Color.yellow);
		g.drawLine(pos.x, pos.y, pos.x, pos.y + 10);
	}
}
