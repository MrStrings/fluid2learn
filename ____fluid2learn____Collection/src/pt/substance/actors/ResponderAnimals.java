package pt.substance.actors;

import java.util.Scanner;

import pt.base.impl.BaseConhecimento;
import pt.base.impl.Statistics;
import pt.base.inter.IBaseConhecimento;
import pt.base.inter.IDeclaracao;
import pt.base.inter.IObjetoConhecimento;
import pt.base.inter.IResponder;
import pt.base.inter.IStatistics;

public class ResponderAnimals implements IResponder
{
	private final String animal;
	private final IObjetoConhecimento obj;
	private final IStatistics estatisticas;
	private int questionCounter;
	private boolean jaPerguntou = false;
	
	
	public ResponderAnimals(String animal)
	{
		IBaseConhecimento bc = new BaseConhecimento();
		
		this.animal = animal;
		this.obj = bc.recuperaObjeto(animal);
		this.estatisticas = new Statistics();
		this.questionCounter = 0;
	}
	
	
	
	/**
	 * Construtor da classe Responder.
	 * Realiza a instanciação da base de conhecimento do animal escolhido.
	 * Carrega o objeto de conhecimento referente ao animal escolhido.
	 * Instancia a estrutura de dados que irá armazenar as estatísticas do Enquirer.
	 * 
         * @param estatisticas  armazena estatisticas das perguntas realizadas
	 * @param 	animal	nome do animal escolhido pelo Responder	
	 */
	public ResponderAnimals(IStatistics estatisticas, String animal)
	{
		IBaseConhecimento bc = new BaseConhecimento();
		bc.setScenario("animals");
		
		this.animal = animal;
		this.obj = bc.recuperaObjeto(animal);
		this.estatisticas = estatisticas;
		this.questionCounter = 0;
	}
	
	/**
	 * Construtor da classe Responder
	 * Lê da entrada padrão o nome do animal escolhido pelo Responder
	 * Realiza a instanciação da base de conhecimento do animal escolhido.
	 * Carrega o objeto de conhecimento referente ao animal escolhido.
	 * Instancia a estrutura de dados que irá armazenar as estatísticas do Enquirer.
         * @param estatisticas armazena estatisticas das perguntas realizadas
	 */
	public ResponderAnimals(IStatistics estatisticas){

		IBaseConhecimento bc = new BaseConhecimento();
		Scanner scanner = new Scanner(System.in);
				
		System.out.println("Escolha um animal da lista a seguir:");
                
                bc.setScenario("animals");
		for (String s : bc.listaNomes())
			System.out.println(s);
		
		System.out.print("  --> Sua escolha: ");
		this.animal = scanner.nextLine();
		this.obj = bc.recuperaObjeto(animal.toLowerCase());
		this.estatisticas = estatisticas;
		this.questionCounter = 0;
		
		scanner.close();
	}	

        @Override
	public String scenario() {
		return "animals";
	}
	
	/**
	 * Responde ao Enquirer alguma pergunta de acordo com o animal escolhido.
	 * @param	question	string com propriedade do animal
	 * @return	string	um de três valores: "sim", "nao" ou "nao sei"
	 */
        @Override
	public String ask(String question)
	{
		String resp = "nao sei";
		
		IDeclaracao decl = obj.primeira();
		while (decl != null && !decl.getPropriedade().equalsIgnoreCase(question))
			decl = obj.proxima();
		
		if (decl != null)
			resp = decl.getValor();
		
		estatisticas.addInfo(questionCounter, question, resp);
		
		questionCounter++;
		
		return resp;
	}

        @Override
	public boolean move(String direction) {
		return false;
	}
	
	/**
	 * Permite ao Enquirer verificar se o animal escolhido é o mesmo do adivinhado.
	 * @param 	answer	nome do animal adivinhado pelo Enquirer
	 * @return	boolean	retorna confirmando ou negando o acerto
	 */
        @Override
	public boolean finalAnswer(String answer)
	{
		boolean acertou = false;
		
		if(jaPerguntou) {
			System.err.println("chamada repetida");
			System.err.println("Abortando...");
			System.exit(1);
		}

		jaPerguntou = true;
		System.out.print("Animal respondido: " + answer);

		if (answer.equalsIgnoreCase(animal)) {
			System.out.println("  --> CORRETO");
			acertou = true;
		} else {
			System.out.println("  --> ERRADO");			
		}
		
		System.out.println("Historico de perguntas:");
		System.out.println(estatisticas.toString());
		
		System.out.print("Perguntas repetidas:");
		if (estatisticas.repetiu()) {
			System.out.println("  --> SIM");
			acertou = true;
		} else {
			System.out.println("  --> NAO");			
		}
		
		return acertou;
	}

}
