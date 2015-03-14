package pt.substance.app;

import pt.base.impl.Statistics;
import pt.base.inter.IEnquirer;
import pt.base.inter.IResponder;
import pt.base.inter.IStatistics;
import pt.substance.actors.EnquirerMaze;
import pt.substance.actors.ResponderMaze;

public class Orchestrator
{
	public static void main(String[] args)
	{
		IEnquirer enq;
		IResponder resp;
		IStatistics stat;
		
		System.out.println("Enquirer com Mordor...");
		stat = new Statistics();
		resp = new ResponderMaze(stat, "mordor");
		enq = new EnquirerMaze();
		enq.connect(resp);
		enq.discover();
		System.out.println("----------------------------------------------------------------------------------------\n");
	}
}


