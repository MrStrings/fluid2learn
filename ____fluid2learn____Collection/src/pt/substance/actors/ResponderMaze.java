package pt.substance.actors;

import pt.base.impl.BaseConhecimento;
import pt.base.inter.IBaseConhecimento;
import pt.base.inter.IDeclaracao;
import pt.base.inter.IObjetoConhecimento;
import pt.base.inter.IResponder;
import pt.base.inter.IStatistics;

public class ResponderMaze implements IResponder {

    private final IObjetoConhecimento obj;

    private char mazeMatrix[];
    private int nLinhas = 0, nColunas = 0;
    private int linhaAtual = 0, colunaAtual = 0;

    public ResponderMaze(IStatistics estatisticas, String maze) {
        IBaseConhecimento bc = new BaseConhecimento();
        bc.setScenario("maze");

        this.obj = bc.recuperaObjeto(maze);

        IDeclaracao decl = obj.primeira();
        if (decl != null) {
            String mazeFlat = decl.getValor();
            nColunas = mazeFlat.length();
            nLinhas = 1;
            decl = obj.proxima();
            while (decl != null) {
                mazeFlat += decl.getValor();
                nLinhas++;
                decl = obj.proxima();
            }

            int pos = mazeFlat.indexOf("E");

            linhaAtual = pos / nColunas;
            colunaAtual = pos - (linhaAtual * nColunas);

            mazeMatrix = mazeFlat.toCharArray();
        }
    }

    @Override
    public String scenario() {
        return "maze";
    }

    @Override
    public String ask(String question) {
        String resposta = null;

        int novaLinha = linhaAtual,
                novaColuna = colunaAtual;
        switch (question) {
            case "norte":
                novaLinha--;
                break;
            case "sul":
                novaLinha++;
                break;
            case "leste":
                novaColuna++;
                break;
            case "oeste":
                novaColuna--;
                break;
            case "aqui":
                break;
            default:
                resposta = "nao sei";
        }

        if (resposta == null) {
            if (novaLinha < 0 || novaLinha >= nLinhas || novaColuna < 0 || novaColuna >= nColunas) {
                resposta = "mundo";
            } else {
                char celula = mazeMatrix[novaLinha * nColunas + novaColuna];
                switch (celula) {
                    case '#':
                        resposta = "parede";
                        break;
                    case ' ':
                        resposta = "passagem";
                        break;
                    case 'E':
                        resposta = "entrada";
                        break;
                    case 'S':
                        resposta = "saida";
                        break;
                }
            }
        }

        return resposta;
    }

    @Override
    public boolean move(String direction) {
        boolean movimento = true;
        int novaLinha = linhaAtual,
                novaColuna = colunaAtual;
        switch (direction) {
            case "norte":
                novaLinha--;
                break;
            case "sul":
                novaLinha++;
                break;
            case "leste":
                novaColuna++;
                break;
            case "oeste":
                novaColuna--;
                break;
            default:
                return false;
        }
        if (novaLinha < 0 || novaLinha >= nLinhas
                || novaColuna < 0 || novaColuna >= nColunas
                || mazeMatrix[novaLinha * nColunas + novaColuna] == '#') {
            movimento = false;
        } else {
            linhaAtual = novaLinha;
            colunaAtual = novaColuna;
        }
        return movimento;
    }

    @Override
    public boolean finalAnswer(String answer) {
        return (mazeMatrix[linhaAtual * nColunas + colunaAtual] == 'S');
    }

}
