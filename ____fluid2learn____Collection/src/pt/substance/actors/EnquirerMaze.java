package pt.substance.actors;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pt.base.inter.IEnquirer;
import pt.base.inter.IResponder;

public class EnquirerMaze implements IEnquirer {

    IResponder responder;
    List map;

    public class Par {

        private final long i;
        private final long j;

        public Par(long i, long j) {
            this.i = i;
            this.j = j;
        }

        public long geti() {
            return this.i;
        }

        public long getj() {
            return this.j;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Par)) {
                return false;
            }
            Par par = (Par) obj;

            return i == par.i && j == par.j;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 71 * hash + (int) (this.i ^ (this.i >>> 32));
            hash = 71 * hash + (int) (this.j ^ (this.j >>> 32));
            return hash;
        }
    }

    @Override
    public void connect(IResponder responder) {
        this.responder = responder;
    }

    @Override
    public boolean discover() {
        Scanner scanner = new Scanner(System.in);
        String gameMode;
        boolean continua = true, continua2;
        
        System.out.println("Iniciando o jogo...\n\n"
                + "-------*-------*-------*-------*-------*-------*-------*-------*-------*-------*");

        while (continua) {
            System.out.println("\nDeseja desvendar os segredos do labirinto manualmente(M)...\n"
                    + "Ou será que prefere ser guiado(G)?");

            System.out.print("  --> Sua escolha: ");
            gameMode = scanner.nextLine();
            while (gameMode.length() <= 0) // Evita possiveis erros
                gameMode = scanner.nextLine();
            continua2 = true;
            while (continua2) {
                switch (gameMode.toLowerCase()) {
                    case "manualmente":
                    case "manual":
                    case "m":
                        discover_manual();
                        break;
                    case "automatico":
                    case "auto":
                    case "guiado":
                    case "g":
                        discover_auto();
                        break;
                    case "f":
                    case "fim":
                        continua = false;
                        continua2 = false;
                        break;

                    default:
                        System.out.println("\nAVISO: Resposta incompreendida. Para escolher"
                                + " outro jogo, digite F. Ou tente novamente..."
                                + " se for corajoso o suficiente.");
                        break;
                }
                if (continua) {
                    System.out.print("Deseja jogar de novo?: ");
                }
                gameMode = scanner.nextLine();
                while (gameMode.length() == 0) // Evita possiveis erros
                    gameMode = scanner.nextLine();

                switch (gameMode.toLowerCase()) {

                    case "nao":
                    case "n":
                    case "f":
                    case "fim":
                        System.out.println ("-------*-------*-------*-------*-------*-------*-------*-------*-------*-------*");
                        continua = false;
                    case "sim":
                    case "s":
                        continua2 = false;
                        
                }
                        
                }
            }
return true;
    }

    public boolean discover_manual() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("(P)ergunta, (M)ovimento ou (F)im? ");
        String tipo = scanner.nextLine();
        while (!tipo.equalsIgnoreCase("F")) {
            System.out.print("  --> ");
            String pc = scanner.nextLine();
            switch (tipo.toUpperCase()) {
                case "P":
                    String resposta = responder.ask(pc);
                    System.out.println("  Resposta: " + resposta);
                    break;
                case "M":
                    boolean moveu = responder.move(pc);
                    System.out.println((moveu) ? "  Movimento executado!" : "Não é possível mover");
                    break;
            }
            System.out.print("(P)ergunta, (M)ovimento ou (F)im? ");
            tipo = scanner.nextLine();
        }

        if (responder.finalAnswer("cheguei")) {
            System.out.println("Você encontrou a saida!");
        } else {
            System.out.println("Fuém fuém fuém!");
        }


        return true;
    }

    public boolean discover_auto() {
        map = new ArrayList<>();

        System.out.println("\nIniciando caminhada no labirinto...");

        if (reachEnd(new Par(0, 0))) //define a origem como ponto de partida
        {
            return true;
        } else {
            System.out.println("Nao foi possivel encontrar a saida");
            return false;
        }
    }

    private boolean reachEnd(Par pos) {
        if (responder.finalAnswer(null)) {
            System.out.printf("\nSaida encontrada!! Está a %d posições ao %s e %d"
                    + " posições a %s do ponto de partida.\n",
                    pos.i > 0 ? pos.i : -pos.i,
                    pos.i > 0 ? "Norte" : "Sul",
                    pos.j > 0 ? pos.j : -pos.j,
                    pos.j > 0 ? "Leste" : "Oeste");
            return true;
        } else {

            if (map.contains(pos)) {
                return false;
            } else {
                map.add(pos);

                if (responder.ask("norte").equals("passagem") || responder.ask("norte").equals("saida")) {
                    responder.move("norte");
                    if (this.reachEnd(new Par(pos.i + 1, pos.j))) {
                        return true;
                    }
                    responder.move("sul");
                }

                if (responder.ask("leste").equals("passagem") || responder.ask("leste").equals("saida")) {
                    responder.move("leste");
                    if (this.reachEnd(new Par(pos.i, pos.j + 1))) {
                        return true;
                    }
                    responder.move("oeste");
                }

                if (responder.ask("oeste").equals("passagem") || responder.ask("oeste").equals("saida")) {
                    responder.move("oeste");
                    if (this.reachEnd(new Par(pos.i, pos.j - 1))) {
                        return true;
                    }
                    responder.move("leste");
                }

                if (responder.ask("sul").equals("passagem") || responder.ask("sul").equals("saida")) {
                    responder.move("sul");
                    if (this.reachEnd(new Par(pos.i - 1, pos.j))) {
                        return true;
                    }
                    responder.move("norte");
                }
            }
        }

        return false;
    }
}
