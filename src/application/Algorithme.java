package application;
import java.util.ArrayList;


public class Algorithme {
private Instance instance;
private int maxgen;
private int maxiter;
private int popSize;
private int c1Size;
private int c2Size;
private double wp;
private Graph graph;
private Population pop;
private Collection C;
public Algorithme(Instance inst,int maxgen,int maxiter,int popsize,int c1,int c2,double wp) {
	instance=inst;
	graph=new Graph();
	this.maxgen=maxgen;
	this.maxiter=maxiter;
	this.popSize=popsize;
	this.c1Size=c1;
	this.c2Size=c2;
	this.wp=wp;
	// the conflict graph creation ********* la creation du graphe de conflit
	graph.creatConflictGraph(inst);
	// Generate randomly an initial population P according to the RK encoding 
	pop=new Population(popSize, graph, instance);
	pop.evaluate();  //evaluate fitness off each individual
	pop.similarits();
	pop.compute_stats();
	// Select a list of candidate individuals C from P using the new selection strategy 
	C=new Collection(this.c1Size,this.c2Size, this.pop);
	C.evaluate();
	C.similarits(pop);
	C.compute_stats();
}
public Collection getC() {
	return C;
}
public void setC(Collection c) {
	C = c;
}
public Instance getInstance() {
	return instance;
}
public void setInstance(Instance instance) {
	this.instance = instance;
}
public int getMaxgen() {
	return maxgen;
}
public void setMaxgen(int maxgen) {
	this.maxgen = maxgen;
}
public int getMaxiter() {
	return maxiter;
}
public void setMaxiter(int maxiter) {
	this.maxiter = maxiter;
}
public int getPopSize() {
	return popSize;
}
public void setPopSize(int popSize) {
	this.popSize = popSize;
}

public double getWp() {
	return wp;
}
public void setWp(double wp) {
	this.wp = wp;
}
public Graph getGraph() {
	return graph;
}
public void setGraph(Graph graph) {
	this.graph = graph;
}
public Population getPop() {
	return pop;
}
public void setPop(Population pop) {
	this.pop = pop;
}

public Chromosome Croisement() {
	Chromosome parent1C,parent2C;
	ArrayList<Offre> genes=new ArrayList<Offre>();
	
	Chromosome enfantC;
	Interval inter1=new Interval(0,C.getC1I().size()),inter2=new Interval(0,C.getC2I().size());
	double rand=Math.random();
	int index1=-1,index2=-1;
    if(rand>=0.65) {
    	index1=inter1.getIntegerRandom();
    	parent1C=new Chromosome(C.getC1C().get(index1).getListGene(), 0);    	
    }
    else {
    	index2=inter2.getIntegerRandom();
    	parent1C=new Chromosome(C.getC2C().get(index2).getListGene(), 0);
    }
    rand=Math.random();
	int index3=0,index4=0;
    if(rand>=0.65) {
    	do
    	index3=inter1.getIntegerRandom();
    	while(index3==index1);
    	parent2C=new Chromosome(C.getC1C().get(index3).getListGene(), -1);
    }
    else {
    	do
    	index4=inter2.getIntegerRandom();
    	while(index4==index2);
    	parent2C=new Chromosome(C.getC2C().get(index4).getListGene(), -1);
    }
    for(int i=0;i<parent1C.length();i++) {
    	genes.add(parent1C.getGene(i));
    }
    for(int j=0;j<parent2C.length();j++) {
    	if(graph.noConflict(genes, parent2C.getGene(j)) == true) {
        	genes.add(parent2C.getGene(j));
    	}
    }
    enfantC=new Chromosome(genes, -1);
  
	return enfantC;

}

public void SLS(Chromosome fils) {
	Individu filsI;
	ArrayList<Integer> chromoso=new ArrayList<Integer>();
	for(int i=0;i<fils.length();i++)chromoso.add(fils.getGene(i).getNumOffre());
	filsI=new Individu(chromoso);
	double rand;
	Interval inter=new Interval(0, instance.getN());
	Offre myOffr;
	for(int j=0;j<this.getMaxiter();j++) {
		rand=Math.random();
		if(rand<this.wp) {
			myOffr=instance.getOffre(inter.getIntegerRandom());
		}
		else {
			myOffr=instance.increasePrice();
		}
		if(graph.noConflict(fils.getListGene(), myOffr)){
			fils.getListGene().add(myOffr);
			filsI.getChromosome().add(myOffr.getNumOffre());
		}
	}
	filsI.evaluatefitness(fils);
	filsI.evaluateSimilarity(this.getPop().getPopI());
	C.compute_stats();
	if(filsI.getFitness()>=C.get_avgf()) {
		if(!C.getC1I().contains(filsI)) {
		C.addC1I(filsI);
		C.addC1C(fils);
		C.removeC1I(C.get_worstp());
		C.removeC1C(C.get_worstp());    }
	}
	else {
		if(filsI.getSimilary()<=C.get_avgS()) {
			if(!C.getC2I().contains(filsI)) {
			C.addC2I(filsI);
			C.addC2C(fils);
			C.removeC2I(C.get_bestSp());
			C.removeC2C(C.get_bestSp());    }
		}
	}
	C.compute_stats();
}

public void SLS2(Chromosome fils) {
	Individu filsI;
	ArrayList<Integer> chromoso=new ArrayList<Integer>();
	for(int i=0;i<fils.length();i++)chromoso.add(fils.getGene(i).getNumOffre());
	filsI=new Individu(chromoso);
	double rand;
	Interval inter=new Interval(0, instance.getN());
	Offre myOffr;
	Chromosome bestC=new Chromosome(fils.getListGene(), fils.getNumChrom());
	Individu bestI=new Individu(chromoso);
	bestI.evaluatefitness(bestC);
	bestI.evaluateSimilarity(this.getPop().getPopI());
	for(int j=0;j<this.getMaxiter();j++) {
		rand=Math.random();
		if(rand<this.wp) {
			myOffr=instance.getOffre(inter.getIntegerRandom());
		}
		else {
			myOffr=instance.increasePrice();
		}
			fils.setListGene(graph.removeConflict(fils.getListGene(), myOffr));
			chromoso=new ArrayList<Integer>();
			for(int i=0;i<fils.length();i++)chromoso.add(fils.getGene(i).getNumOffre());
			filsI=new Individu(chromoso);
			filsI.evaluatefitness(fils);
			filsI.evaluateSimilarity(this.getPop().getPopI());
			if(bestI.getFitness()<filsI.getFitness()) {
				bestC.setListGene(fils.getListGene());
				bestI.setChromosome(filsI.getChromosome());
				bestI.setFitness(filsI.getFitness());
				bestI.setSimilary(filsI.getSimilary());
			}
	}
	
	C.compute_stats();
	if(bestI.getFitness()>=C.get_avgf()) {
		if(!C.existInC1(bestI)) {
		C.addC1I(bestI);
		C.addC1C(bestC);
		C.removeC1I(C.get_worstp());
		C.removeC1C(C.get_worstp());    }
	}
	else {
		if(bestI.getSimilary()<=C.get_avgS()) {
			if(!C.existInC2(bestI)) {
			C.addC2I(bestI);
			C.addC2C(bestC);
			C.removeC2I(C.get_bestSp());
			C.removeC2C(C.get_bestSp());    }
		}
	}
	C.compute_stats();
}
public int    get_worstp() { return pop.get_worstp(); }
public int    get_bestp()  { return pop.get_bestp();  }
public double get_worstf() { return pop.get_worstf(); }
public double get_avgf()   { return pop.get_avgf();   }
public double get_bestf()  { return pop.get_bestf();  }
public double get_BESTF()  { return pop.get_BESTF();  }

public int    get_worstpC() { return C.get_worstp(); }
public int    get_bestpC()  { return C.get_bestp();  }
public double get_worstfC() { return C.get_worstf(); }
public double get_avgfC()   { return C.get_avgf();   }
public double get_bestfC()  { return C.get_bestf();  }
public double get_BESTFC()  { return C.get_BESTF();  }

}
