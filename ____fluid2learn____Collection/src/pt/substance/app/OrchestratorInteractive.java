package pt.substance.app;

import java.util.Scanner;

import pt.base.impl.BaseConhecimento;
import pt.base.impl.Statistics;
import pt.base.inter.IBaseConhecimento;
import pt.base.inter.IEnquirer;
import pt.base.inter.IResponder;
import pt.base.inter.IStatistics;
import pt.substance.actors.EnquirerAnimals;
import pt.substance.actors.EnquirerMaze;
import pt.substance.actors.ResponderAnimals;
import pt.substance.actors.ResponderMaze;

public class OrchestratorInteractive {

    public static void main(String[] args) {
        IEnquirer enq = null;
        IResponder resp = null;
        IStatistics stat;
        String gameName;

        Scanner scanner = new Scanner(System.in);
        IBaseConhecimento base = new BaseConhecimento();
        stat = new Statistics();

        boolean continua = true;
        while (continua) {
            System.out.print("Escolha seu jogo:\n Animals(A) or Maze(M): ");
 
            gameName = scanner.nextLine();
            switch (gameName.toUpperCase()) {
                case "A":
                case "ANIMALS":
                    base.setScenario("animals");
                    resp = new ResponderAnimals(stat); // Jah pergunta qual animal sera
                    enq = new EnquirerAnimals();
                    if (enq != null) {
                        enq.connect(resp);
                        enq.discover();
                    }
                    break;
                case "M":
                case "MAZE":
                    base.setScenario("maze");

                    System.out.print("\nEncolha seu labirinto dentre as opções: \n");
                    for (String s : base.listaNomes()) {
                        System.out.println(s);
                    }
                    System.out.print("  --> Sua escolha: ");
                    String gameArena = scanner.nextLine();

                    resp = new ResponderMaze(stat, gameArena.toLowerCase());
                    enq = new EnquirerMaze();
                    if (enq != null) {
                        enq.connect(resp);
                        enq.discover();
                    }
                    break;
                default:
                    System.out.println("\nJogo nao encontrado.");
                    break;
            }
            System.out.print("Digite CONTINUE para "
                    + "selecionar um jogo ou STOP para "
                    + "encerrar o aplicativo: ");
            gameName = scanner.nextLine();
            switch (gameName.toUpperCase()) {
                case "C":
                case "CONTINUE":
                    System.out.println("Você decidiu continuar.\n");
                    break;
                case "S":
                case "STOP":
                    continua = false;
                    break;
            }
        }

        System.out.println("Encerrando o aplicativo");
        System.out.println("----------------------------------------------------------------------------------------\n");

    }
}
