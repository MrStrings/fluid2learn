package pt.substance.app;

import pt.base.impl.BaseConhecimento;
import pt.base.impl.Statistics;
import pt.base.inter.IBaseConhecimento;
import pt.base.inter.IEnquirer;
import pt.base.inter.IResponder;
import pt.base.inter.IStatistics;
import pt.substance.actors.EnquirerAnimals;
import pt.substance.actors.ResponderAnimals;

public class Orchestrator
{
	public static void main(String[] args)
	{
		IEnquirer enq;
		IResponder resp;
		IStatistics stat;
		
		IBaseConhecimento base = new BaseConhecimento();

		base.setScenario("animals");
		String listaAnimais[] = base.listaNomes();
        for (int animal = 0; animal < listaAnimais.length; animal++) {
			System.out.println("Enquirer com " + listaAnimais[animal] + "...");
			stat = new Statistics();
			resp = new ResponderAnimals(stat, listaAnimais[animal]);
			enq = new EnquirerAnimals();
			enq.connect(resp);
			enq.discover();
			System.out.println("----------------------------------------------------------------------------------------\n");
        }		
	}
}
