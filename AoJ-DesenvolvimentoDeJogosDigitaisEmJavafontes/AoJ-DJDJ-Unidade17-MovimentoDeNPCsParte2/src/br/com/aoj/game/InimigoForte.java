package br.com.aoj.game;

import java.io.IOException;
import java.util.ArrayList;

public class InimigoForte extends Inimigo {

	public InimigoForte(int x, int y, ArrayList<Tiro> tiros) {
		super(x, y, 1, tiros);
		energia = 5;
		try {
			loadSprites("inimigo2.gif");
		} catch (IOException ex) {
		}
	}
}
