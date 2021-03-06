package pt.base.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import pt.base.inter.IBaseConhecimento;
import pt.base.inter.IDeclaracao;
import pt.base.inter.IObjetoConhecimento;

public class BaseConhecimento implements IBaseConhecimento {

    public static final String DIRETORIO_RELATIVO = "../../substance/bd/",
            EXTENSAO = ".txt";
    private final String diretorio = BaseConhecimento.class.getResource(DIRETORIO_RELATIVO).getPath();

    private String scenario;

    @Override
    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    @Override
    public String[] listaNomes() {
        File diretorioRaiz = new File(diretorio + "/" + scenario);

        String lista[] = diretorioRaiz.list();

        ArrayList<String> listaFiltrada = new ArrayList<>();
        for (String lista1 : lista) {
            if (lista1.endsWith(".txt")) {
                listaFiltrada.add(lista1.substring(0, lista1.length() - 4));
            }
        }

        return (String[]) listaFiltrada.toArray(new String[0]);
    }

    @Override
    public IObjetoConhecimento recuperaObjeto(String nome) {
        ArrayList<IDeclaracao> vdecl = new ArrayList<>();

        try {
            FileReader arquivo = new FileReader(diretorio + "/" + scenario + "/" + nome + EXTENSAO);
            BufferedReader formatado = new BufferedReader(arquivo);

            String linha = formatado.readLine();
            while (linha != null) {
                IDeclaracao decl = montaDeclaracao(linha);

                if (decl != null) {
                    vdecl.add(decl);
                }

                linha = formatado.readLine();
            }

            arquivo.close();
        } catch (IOException erro) {
            erro.printStackTrace();
        }

        IObjetoConhecimento obj = new ObjetoConhecimento(vdecl.toArray(new IDeclaracao[0]));

        return obj;
    }

    private IDeclaracao montaDeclaracao(String linha) {
        String propriedade = null,
                valor = null;

        if (linha != null) {
            if (scenario.equalsIgnoreCase("maze")) {
                propriedade = "maze line";
                valor = linha;
            } else {
                char clinha[] = linha.toCharArray();
                int p = 0;

                if (clinha[p] == '\"') {
                    propriedade = "";
                    p++;
                    while (p < clinha.length && clinha[p] != '\"') {
                        propriedade += clinha[p];
                        p++;
                    }
                    p++;

                    if (p < clinha.length) {
                        while (p < clinha.length && clinha[p] != '\"') {
                            p++;
                        }
                        if (p < clinha.length) {
                            valor = "";
                            p++;
                            while (p < clinha.length && clinha[p] != '\"') {
                                valor += clinha[p];
                                p++;
                            }
                        }
                    }
                }
            }
        }

        IDeclaracao decl = null;
        if (propriedade != null && valor != null) {
            decl = new Declaracao(propriedade, valor);
        }

        return decl;
    }
    
    
    public boolean isListed (String elemento) {
        
        for (String i : this.listaNomes())
            if (i.equals(elemento))
                return true;
        
        return false;
        
    }
}
