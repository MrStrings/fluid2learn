package pt.base.inter;

public interface IEnquirer
{
    public void connect(IResponder responder);
    public boolean discover();
}
