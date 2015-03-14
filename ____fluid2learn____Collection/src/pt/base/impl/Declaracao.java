package pt.base.impl;

import pt.base.inter.IDeclaracao;

public class Declaracao implements IDeclaracao
{
    private String propriedade;
    private String valor;
    
    public Declaracao(String propriedade, String valor)
    {
    	this.propriedade = propriedade;
    	this.valor = valor;
    }
    
    public String getPropriedade()
    {
    	return propriedade;
    }
    
    public String getValor()
    {
    	return valor;
    }
    
    public String toString()
    {
    	return "(propriedade: " + propriedade + ", valor: " + valor + ")";
    }
}
