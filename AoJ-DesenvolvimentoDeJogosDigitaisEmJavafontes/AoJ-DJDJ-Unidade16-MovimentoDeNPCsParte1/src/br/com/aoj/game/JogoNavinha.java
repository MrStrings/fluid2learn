package br.com.aoj.game;

import br.com.aoj.core.AudioManager;
import br.com.aoj.core.Game;
import br.com.aoj.core.InputManager;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class JogoNavinha extends Game {

	// Modelo do jogo.
	Jogador player;
	ArrayList<Nave> inimigos;
	ArrayList<Tiro> tirosPlayer;
	ArrayList<Explosao> explosoes;
	int pontos;
	int vidas;
	int rndInimigos;
	Random rnd;

	public JogoNavinha() {
		rnd = new Random(10);
		inimigos = new ArrayList<Nave>();
		tirosPlayer = new ArrayList<Tiro>();
		explosoes = new ArrayList<Explosao>();
		player = new Jogador(400, 500, tirosPlayer);
		pontos = 0;
		vidas = 3;
		rndInimigos = 300;
	}

	@Override
	public void onLoad() {
		try {
			AudioManager.getInstance().loadAudio("loop.wav").loop();
		} catch (IOException ex) {
		}
	}

	@Override
	public void onUnload() {
		try {
			AudioManager.getInstance().loadAudio("loop.wav").stop();
		} catch (IOException ex) {
		}
	}

	@Override
	public void onUpdate(int currentTick) {
		player.update(currentTick);
		if (currentTick % 600 ==  0) {
			if (rndInimigos > 10) {
				rndInimigos -= 10;
			}
		}
		if (rnd.nextInt(rndInimigos) == 0) {
			if (rnd.nextBoolean()) {
				inimigos.add(new Inimigo1(rnd.nextInt(800), -100));
			} else {
				inimigos.add(new Inimigo2(rnd.nextInt(800), -100));
			}
		}
		for (int i = tirosPlayer.size() - 1; i >= 0; i--) {
			tirosPlayer.get(i).update(currentTick);
			testaColisaoTiroPlayer(tirosPlayer.get(i));
			if (tirosPlayer.get(i).energia == 0) {
				tirosPlayer.remove(tirosPlayer.get(i));
			}
		}
		for (int i = inimigos.size() - 1; i >= 0; i--) {
			inimigos.get(i).update(currentTick);
			if (inimigos.get(i).energia == 0) {
				inimigos.remove(i);
			}
		}
		for (int i = explosoes.size() - 1; i >= 0; i--) {
			explosoes.get(i).update(currentTick);
			if (explosoes.get(i).energia == 0) {
				explosoes.remove(i);
			}
		}
		if (InputManager.getInstance().isPressed(KeyEvent.VK_ESCAPE)) {
			terminate();
		}
	}

	@Override
	public void onRender(Graphics2D g) {
		g.setColor(Color.blue);
		g.fillRect(0, 0, 800, 600);
		for (Nave n : inimigos) {
			n.render(g);
		}
		player.render(g);
		for (Tiro t : tirosPlayer) {
			t.render(g);
		}
		for (Explosao e : explosoes) {
			e.render(g);
		}
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, 800, 20);
		g.setColor(Color.cyan);
		g.drawString("PONTOS: " + pontos, 350, 15);
		g.drawString("VIDAS: " + vidas, 750, 15);
	}

	public void testaColisaoTiroPlayer(Tiro tiro) {
		for (Nave n : inimigos) {
			if (tiro.pos.x > n.pos.x - n.getImage().getWidth() / 2 && tiro.pos.x < n.pos.x + n.getImage().getWidth() / 2 && tiro.pos.y > n.pos.y && tiro.pos.y < n.pos.y + n.getImage().getHeight()) {
				n.energia -= tiro.energia;
				pontos += 5;
				explosoes.add(new Explosao(tiro.pos.x, tiro.pos.y));
				if (n.energia <= 0) {
					n.energia = 0;
					explosoes.add(new Explosao2(n.pos.x, n.pos.y + n.getImage().getHeight() / 2));
					pontos += 15;
				}
				tiro.energia = 0;
			}
		}
	}
}
