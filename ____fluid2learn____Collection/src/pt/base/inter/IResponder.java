package pt.base.inter;


public interface IResponder
{
    public String scenario();
	public String ask(String question);
	public boolean move(String direction);
    public boolean finalAnswer(String answer);
}
