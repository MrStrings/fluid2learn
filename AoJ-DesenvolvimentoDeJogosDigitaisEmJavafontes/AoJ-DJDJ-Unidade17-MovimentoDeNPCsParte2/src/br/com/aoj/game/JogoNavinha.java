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
	ArrayList<Tiro> tirosInimigos;
	ArrayList<Explosao> explosoes;
	int pontos;
	int vidas;
	int rndInimigos;
	Random rnd;
	boolean limpaTela;
	boolean gameOver;

	public JogoNavinha() {
		rnd = new Random(10);
		inimigos = new ArrayList<Nave>();
		tirosPlayer = new ArrayList<Tiro>();
		tirosInimigos = new ArrayList<Tiro>();
		explosoes = new ArrayList<Explosao>();
		player = new Jogador(400, 500, tirosPlayer);
		pontos = 0;
		vidas = 3;
		rndInimigos = 300;
		limpaTela = false;
		gameOver = false;
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
		if (!gameOver) {
			if (limpaTela) {
				inimigos.clear();
				tirosInimigos.clear();
				tirosPlayer.clear();
				limpaTela = false;
			}

			player.update(currentTick);
			if (currentTick % 300 == 0) {
				if (rndInimigos > 10) {
					rndInimigos -= 10;
				}
			}
			if (rnd.nextInt(rndInimigos) == 0) {
				int r = rnd.nextInt(4);
				if (r == 0) {
					inimigos.add(new InimigoFraco(rnd.nextInt(800), -100, tirosInimigos));
				} else if (r == 1) {
					inimigos.add(new InimigoForte(rnd.nextInt(800), -100, tirosInimigos));
				} else if (r == 2) {
					inimigos.add(new InimigoQueSegue(rnd.nextInt(800), -100, player, tirosInimigos));
				} else if (r == 3) {
					inimigos.add(new InimigoZigueZague(rnd.nextInt(800), -100, tirosInimigos));
				}
			}
			for (int i = tirosPlayer.size() - 1; i >= 0; i--) {
				tirosPlayer.get(i).update(currentTick);
				testaColisaoTiroPlayer(tirosPlayer.get(i));
				if (tirosPlayer.get(i).energia == 0) {
					tirosPlayer.remove(tirosPlayer.get(i));
				}
			}
			for (int i = tirosInimigos.size() - 1; i >= 0; i--) {
				tirosInimigos.get(i).update(currentTick);
				testaColisaoTiroInimigo(tirosInimigos.get(i));
				if (tirosInimigos.get(i).energia == 0) {
					tirosInimigos.remove(tirosInimigos.get(i));
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
		for (Tiro t : tirosInimigos) {
			t.render(g);
		}
		for (Explosao e : explosoes) {
			e.render(g);
		}
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, 800, 20);
		g.setColor(Color.cyan);
		g.drawString("PONTOS: " + pontos, 350, 15);
		g.drawString("ENERGIA: " + (player.energia * 10) + "%      VIDAS: " + vidas, 630, 15);
		if (gameOver) {
			g.setColor(Color.darkGray);
			g.fillRect(0, 250, 800, 100);
			g.setColor(Color.cyan);
			g.drawString("FIM DO JOGO", 350, 280);
			g.drawString("PONTOS: " + pontos, 350, 310);
			g.drawString("pressione ESC para sair", 350, 340);
		}
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

	public void testaColisaoTiroInimigo(Tiro tiro) {
		Nave n = player;
		if (tiro.pos.x > n.pos.x - n.getImage().getWidth() / 2 && tiro.pos.x < n.pos.x + n.getImage().getWidth() / 2 && tiro.pos.y > n.pos.y && tiro.pos.y < n.pos.y + n.getImage().getHeight()) {
			n.energia -= tiro.energia;
			explosoes.add(new Explosao(tiro.pos.x, tiro.pos.y));
			if (n.energia <= 0) {
				explosoes.add(new Explosao2(n.pos.x, n.pos.y));
				n.energia = 10;
				vidas--;
				if (vidas == 0) {
					gameOver = true;
				}
				player.pos.x = 400;
				player.pos.y = 500;
				limpaTela = true;
			}
			tiro.energia = 0;
		}
	}
}
