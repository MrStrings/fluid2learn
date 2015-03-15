package pt.substance.actors;

import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

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

        scanner.close();

        return true;
    }

    @Override
    public boolean discover() {
        map = new ArrayList<>();
        
        
        System.out.println ("Iniciando caminhada no labirinto...\n");

        
        
        if (reachEnd(new Par(0,0))) //define a origem como ponto de partida
            return true;

        else {
            System.out.println("Nao foi possivel encontrar a saida");
            return false;
        }
    }

    private boolean reachEnd(Par pos) {
        if (responder.finalAnswer(null)) {
            System.out.println("Saida encontrada!! Está a " + pos.i + " posições ao Norte e " + pos.j
                    + " posições a Leste do ponto de partida.\n\n"
                    + "Nota: Posicoes negativas representam saida ao Sul e Oeste,"
                    + " respectivamente.\n");
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
                } else if (responder.ask("leste").equals("passagem") || responder.ask("leste").equals("saida")) {
                    responder.move("leste");
                    if (this.reachEnd(new Par(pos.i, pos.j + 1))) {
                        return true;
                    }
                } else if (responder.ask("oeste").equals("passagem") || responder.ask("oeste").equals("saida")) {
                    responder.move("oeste");
                    if (this.reachEnd(new Par(pos.i, pos.j - 1))) {
                        return true;
                    }
                } else if (responder.ask("sul").equals("passagem") || responder.ask("sul").equals("saida")) {
                    responder.move("sul");
                    if (this.reachEnd(new Par(pos.i - 1, pos.j))) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
