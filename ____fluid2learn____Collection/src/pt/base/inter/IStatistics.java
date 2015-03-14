package pt.base.inter;

public interface IStatistics {
	
	public void addInfo(int numeroPergunta, String pergunta, String resposta);
		
	public String toString();
	
	public boolean repetiu();
	
	public int totalPerguntas();
}
