package br.com.aoj.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.font.TextAttribute;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import static java.lang.Math.abs;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

// Esta é classe que representa um jogo genérico na nossa arquitetura.
// Nossos jogos vão sempre extender dela, herdando a funcionaldiade de motor.
// Esta classe implementa a interface WindowListener porque precisaremos
// escutar o evento de fechar a janela, para terminar o jogo quando ele ocorrer.
public class JogoCirculo implements WindowListener {

    // Variáveis necessária para o motor do jogo.
    // mainWindow armazenará uma referência para a janela criada para o jogo.
    private JFrame mainWindow;
    // active indica se o jogo está ativo.
    private boolean active;
    // bufferStrategy nos permite acessar o vídeo de forma mais eficiente.
    private BufferStrategy bufferStrategy;
    // Variáveis necessárias para nosso jogo.
    // Elas armazenam a posição do círculo (x,y) e a velocidade que ela anda.
    int x, x1;
    int y, y1;
    int sx, sx1;
    int sy, sy1;

    public JogoCirculo() {
        // Este é o contructor desta clase, onde criamos a janela principal.
        mainWindow = new JFrame("Abrindo o Jogo - Desenvolvimento de Jogos Digitais em Java");
        // Ajustamos as dimensões da janela.
        mainWindow.setSize(800, 600);
        // Cadastramos esta clase (Game) como ouvinte dos eventos da janela.
        mainWindow.addWindowListener(this);
        // Marcamos o jogo como "não ativo".
        active = false;
    }

    public void terminate() {
        // Este método faz com que o jogo termine, atribuindo falso para
        // a variável active. Poara ver como isso funciona, veja o método
        // run, abaixo.
        active = false;
    }

    public void run() {
        // Este método é o principal do jogo, contendo nosso game loop.
        // É aqui que o programa fica travado, em execução permanente até
        // que a vari[avel active receba o valor falso.
        active = true;
        // Antes de entrar no loop principal do jogo, chamamos o método load.
        load();
        // Em seguida entramos em um loop que fica executando até que 
        // a variável active passe para o valor falso.
        while (active) {
            // Dentro do loop chamamos os métodos update e render.
            update();
            render();
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(JogoCirculo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // Logo após sair do loop, chamamos o método unload.
        unload();
    }

    public void load() {
        // Configruamos para ignorar o evento de desenho do sistema, afinal,
        // vamos utilizar renderização ativa, ou seja, desenhar por nossa conta.
        mainWindow.setIgnoreRepaint(true);
        // Posicionamos a janela a 100 pixels da borda.
        mainWindow.setLocation(100, 100);
        // Mostramos a janela.
        mainWindow.setVisible(true);
        // Criamos a buffer strategy com 2 buffers (double buffer).
        mainWindow.createBufferStrategy(2);
        // Armazenamos a buffer strategy na nossa variável para uso posterior.
        bufferStrategy = mainWindow.getBufferStrategy();
        x = 0;
        x1 = 350;
        y = 0;
        y1 = 400;
        sx = 1;
        sy = 1;
        sx1 = -2;
        sy1 = -4;
    }

    public void unload() {
        // Depois disso, liberamos a buffer strategy.
        bufferStrategy.dispose();
        // liberamos a janela.
        mainWindow.dispose();
    }

    public void update() {
        // Este método é chamado cada vez que a lógica do jogo precisa ser
        // atualizada. Aqui mudamos os valores das variáveis para
        // fazer a bola se mover na tela, rebatendo nas bordas.
        x += sx;
        y += sy;
        
        x1 += sx1;
        y1 += sy1;
        // Toda vez que a posição chega em um limite da tela,
        // a velocidade naquela direção é invertida.
        if (x < 0 || x > getWidth() - 100) {
            sx *= -1;
        }
        if (x1 < 0 || x1 > getWidth() - 100) {
            sx1 *= -1;
        }
        if (y < 0 || y > getHeight() - 100) {
            sy *= -1;
        }
        if (y1 < 0 || y1 > getHeight() - 100) {
            sy1 *= -1;
        }

        if (((x-x1)*(x-x1) + (y-y1)*(y-y1)) <= 10000) {
            int aux;
            if (abs(x-x1) != 0) {
                aux = sx;
                sx = sx1;
                sx1 = aux;
            }
            
             if (abs(y-y1) != 0) {
                aux = sy;
                sy = sy1;
                sy1 = aux;
            }
        }
    }

    public void render() {
        // A cada chamada a render, obtermos um graphics para desenhar.
        // Ele representa a te
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        // Especificamos a cor preta.
        g.setColor(Color.black);
        // E desenhamos um retângulo do tamanho da janela (limpamos a tela).
        g.fillRect(0, 0, mainWindow.getWidth(), mainWindow.getHeight());
        // Este método é chamado cada vez que é preciso atualizar a imagem
        // do jogo na tela. É aqui que desenyhamos abola na posição 
        // armazenada nas variáveis.
        g.setColor(Color.orange);
        g.fillOval(x, y, 100, 100);
        
        g.setColor(Color.green);
        g.fillOval(x1, y1, 100, 100);
       
        Font font = new Font(Font.SERIF, Font.PLAIN, 10);
        HashMap<TextAttribute, Object> map =
            new HashMap<TextAttribute, Object>();
 
        g.setFont(font); 
        map.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
        font.deriveFont(map);
        g.setFont(font);
        g.drawString ("WOW MAN!!!", 45, 150);
        
        
        // Liberamos o objeto graphics.
        g.dispose();
        // Pedimos ao buffer strategy para mostrar o que foi desenhado acima.
        bufferStrategy.show();
    }

    public int getWidth() {
        // Retorna a largura da janela.
        return mainWindow.getWidth();
    }

    public int getHeight() {
        // Retorna a altura da janela.
        return mainWindow.getHeight();
    }

    public void windowClosing(WindowEvent e) {
        // Método chamado no evento de fechar a janela.
        // Nese momento chamamos terminate para terminar o jogo.
        terminate();
    }

    // Método que precisa ser implementado porque implementamos
    // a interface WindowListener, que contem ele.
    // Nesse caso, não precisamos nenhum código dentro dele.
    public void windowOpened(WindowEvent e) {
    }

    // Método que precisa ser implementado porque implementamos
    // a interface WindowListener, que contem ele.
    // Nesse caso, não precisamos nenhum código dentro dele.
    public void windowClosed(WindowEvent e) {
    }

    // Método que precisa ser implementado porque implementamos
    // a interface WindowListener, que contem ele.
    // Nesse caso, não precisamos nenhum código dentro dele.
    public void windowIconified(WindowEvent e) {
    }

    // Método que precisa ser implementado porque implementamos
    // a interface WindowListener, que contem ele.
    // Nesse caso, não precisamos nenhum código dentro dele.
    public void windowDeiconified(WindowEvent e) {
    }

    // Método que precisa ser implementado porque implementamos
    // a interface WindowListener, que contem ele.
    // Nesse caso, não precisamos nenhum código dentro dele.
    public void windowActivated(WindowEvent e) {
    }

    // Método que precisa ser implementado porque implementamos
    // a interface WindowListener, que contem ele.
    // Nesse caso, não precisamos nenhum código dentro dele.
    public void windowDeactivated(WindowEvent e) {
    }
}