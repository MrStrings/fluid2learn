package br.com.aoj.game;

import java.io.IOException;
import java.util.ArrayList;

public class InimigoFraco extends Inimigo {

	public InimigoFraco(int x, int y, ArrayList<Tiro> tiros) {
		super(x, y, 2, tiros);
		energia = 1;
		try {
			loadSprites("inimigo1.gif");
		} catch (IOException ex) {
		}
	}
}
