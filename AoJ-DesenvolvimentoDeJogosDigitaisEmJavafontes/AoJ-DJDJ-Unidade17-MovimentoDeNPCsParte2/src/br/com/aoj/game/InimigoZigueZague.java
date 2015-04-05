package br.com.aoj.game;

import java.util.ArrayList;

public class InimigoZigueZague extends InimigoFraco {

	public InimigoZigueZague(int x, int y, ArrayList<Tiro> tiros) {
		super(x, y, tiros);
		dir = 1;
	}

	@Override
	public void update(int currentTick) {
		if (pos.x < 0 || pos.x > 800) {
			dir = -dir;
		}
		pos.x += dir * speed;
		super.update(currentTick);
	}
}
