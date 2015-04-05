package br.com.aoj.game;

import java.io.IOException;
import java.util.ArrayList;

public class InimigoQueSegue extends Inimigo {

	Jogador jogador;

	public InimigoQueSegue(int x, int y, Jogador j, ArrayList<Tiro> tiros) {
		super(x, y, 5, tiros);
		intervaloTiro = 60;
		energia = 1;
		jogador = j;
		try {
			loadSprites("inimigo3.gif");
		} catch (IOException ex) {
		}
	}

	@Override
	public void update(int currentTick) {
		if (pos.x < jogador.pos.x) {
			dir = 1;
		} else if (pos.x > jogador.pos.x) {
			dir = -1;
		}
		pos.x += speed * dir;
		super.update(currentTick);
	}
}
