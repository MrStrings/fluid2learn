/*
    Arquivo editado por: (file edited by:)
    Pedro Henrique Ferreira Stringhini
    RA: 156983 (Academic ID)
    
    Enquirer: descobre qual ser que o responder aponta de acordo com sua
              database de perguntas.
*/


package pt.substance.actors;

import pt.base.impl.BaseConhecimento;
import pt.base.inter.IBaseConhecimento;
import pt.base.inter.IDeclaracao;
import pt.base.inter.IEnquirer;
import pt.base.inter.IObjetoConhecimento;
import pt.base.inter.IResponder;

import java.util.*;
import java.util.Collections;

public class EnquirerAnimals implements IEnquirer {
    IResponder responder;
    private IObjetoConhecimento obj;
    private final Map<String, TreeMap <String,Boolean>> database;
    private final IBaseConhecimento bc;
    private final ArrayList<String> animais;
    List<QuestionInfo> perguntas;
    
    /* 
        Definicao de Database:
        Mapa que utiliza como chave o animal requisitado,
        e leva para um mapa que, a partir da pergunta utilizada,
        encontra a resposta de acordo com o codigo:
    Sim: True
    Nao: False
    Nao Sei: null
    */
    
    private class QuestionInfo implements Comparable <QuestionInfo>{
        //Guarda a pergunta e o grau de equilibrio dessa:
        String question;
        private long grauDeEquilibrio;
        
        
        /*Def grau de equilibrio:
              Quanto maior o grau de equilibrio,
              menor a diferenca de quantidades de "Sims","Naos" e "NaoSeis"
              referentes a se perguntada a questao referente para todos
              os animais conhecidos.
        */
        
        public QuestionInfo (String pergunta){
            this.question = pergunta;
        }
        
        public long getEquilibrio () {
            return this.grauDeEquilibrio;
        }
        
        
        private void novoEquilibrio () {
                long sims = 0, naos = 0, naoSeis = 0;
            
            for (String animal : animais) {
                Boolean resp = database.get(animal).get(this.question);
                
                if (resp == null)
                    naoSeis++;
                else if (resp == false)
                    naos++;
                else
                    sims++;
            }
            
            this.grauDeEquilibrio = (sims+1)*(naos+1)*(naoSeis+1);
        }
        
        
         @Override
        public int compareTo(QuestionInfo a) { //Special thanks to gbs
            
            if (this.grauDeEquilibrio > a.grauDeEquilibrio)
                return -1;
            
            else if (this.grauDeEquilibrio < a.grauDeEquilibrio)
                return 1;
            else
                return 0;
        }
        
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (!(obj instanceof QuestionInfo))
                return false;
            QuestionInfo questionInfo = (QuestionInfo) obj;
            
            return question.equals(questionInfo.question);
        }

        
        // A pedidos do IDE
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 19 * hash + (int) (this.grauDeEquilibrio ^ (this.grauDeEquilibrio >>> 32));
            return hash;
        }

        
       
    }
    
    
    // Cria Database, lista de QuestionInfos e de animais;
    public EnquirerAnimals() {
        database = new TreeMap<String, TreeMap <String,Boolean>>();
        bc = new BaseConhecimento();
        bc.setScenario("animals");
        String[] animais_aux = bc.listaNomes();
        
        perguntas = new ArrayList<QuestionInfo>();
        animais = new ArrayList<String>();
        
        animais.addAll(Arrays.asList(animais_aux));
        
        
        
        //Monta database com perguntas, animais e respectivas respostas
        for (String animal : animais) {
            obj = bc.recuperaObjeto(animal);
            database.put(animal, new TreeMap<String,Boolean>());
            
            for (IDeclaracao decl = obj.primeira(); decl != null; decl = obj.proxima()){
           
                String perguntaAtual = decl.getPropriedade(); // pega pergunta
                String resp = decl.getValor(); // Pega resposta da pergunta atual
                
                QuestionInfo atual = new QuestionInfo(perguntaAtual);
                if (!perguntas.contains(atual))
                    perguntas.add(atual); //Cria QuestionInfo da pergutnta atual;
                
                if (resp.equalsIgnoreCase("sim"))
                    database.get(animal).put(perguntaAtual, true);
                else //Respondeu nao
                    database.get(animal).put(perguntaAtual, false);
            }
        }
        
        
        for (QuestionInfo pergunta : perguntas)
            pergunta.novoEquilibrio();
    }
    
    // Inicializa responder
    public void connect(IResponder responder) {
        this.responder = responder;
    }
    
    
    @Override
    public boolean discover() {
        

        
        boolean animalEsperado = false;
        

        // Passa por cada pergunta, ateh encontrar o certo:
        while (perguntas.size() > 0) {
            
            Collections.sort(perguntas);
           
            /*System.out.println ("Ordem de perguntas:");
            for (QuestionInfo x : perguntas)
                System.out.println(x.question + " Equilibrio: " + x.getEquilibrio());
            
            System.out.println("\n\n");
            */
            QuestionInfo pergunta = perguntas.get(0);
            perguntas.remove(pergunta);
            
            
            String resposta = responder.ask(pergunta.question); //pergunta para o responder
            
            // Converte resposta em Boolean
            Boolean esperado;
            if (resposta.equalsIgnoreCase("sim"))
                esperado = true;
            else if (resposta.equalsIgnoreCase("nao"))
                esperado = false;
            else
                esperado = null;
            
            Iterator<String> iterator = animais.iterator(); //necessario pois alteramos animais no loop
            
            // Passa por todos os animais:
            while (iterator.hasNext()) {
                String animal = iterator.next();
                Boolean respAtual = database.get(animal).get(pergunta.question);
                
                if (respAtual != esperado) {
                    iterator.remove(); //remove animais que nao batem com esperado
                    database.remove(animal);
                }   
            }
            
            
            //Reequilibra perguntas
            for (QuestionInfo perguntasDesequilibradas : perguntas)
                perguntasDesequilibradas.novoEquilibrio(); 
            
            
            if (animais.size() == 1)
                break; //Sobrou so um animal, esse eh o desejado 
        } 
            
        /*
            Pega o primeiro dos animais restantes
            nota: com database sem animais equivalentes, animais.size() eh 1);
        */
        boolean acertei = responder.finalAnswer(animais.get(0));
        
        if (acertei) {
            System.out.println("Oba! Acertei!");
        } else {
            System.out.println("fuem! fuem! fuem!");
        }
        
        return acertei;
    }
}
   
