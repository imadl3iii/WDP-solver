package application;
import java.util.ArrayList;

public class Individu {
private ArrayList<Integer> chromosome;
private double fitness;
private int similary;
public int getSimilary() {
	return similary;
}
public void setSimilary(int similary) {
	this.similary = similary;
}
public Individu(ArrayList<Integer> c)
{
   chromosome   = c;
   fitness = 0.0;
}
public Individu(ArrayList<Integer> chrom, double fitness) {
	super();
	this.chromosome = chrom;
	this.fitness = fitness;
	
}


public double getFitness() {
	return fitness;
}
public void setFitness(double fitness) {
	this.fitness = fitness;
}

public int length() {
	return chromosome.size();
}
public ArrayList<Integer> getChromosome() {
	return chromosome;
}
public void setChromosome(ArrayList<Integer> chromosome) {
	this.chromosome = chromosome;
}

public void setGene(int offreN) {
	chromosome.add(offreN);
}

public void setGene(int index,int offreN) {
	chromosome.add(index, offreN);;
}

public int getGene(int index) {
	return chromosome.get(index);
}


@Override
public String toString() {
	return "Individu [chromosome=" + chromosome + "]";
}
public void evaluatefitness(Chromosome c) {
	double fit=0;
	for(int i=0;i<c.length();i++) {
		fit=fit+c.getGene(i).getPrix();
	}
	this.setFitness(fit);
}
public int similarityOf2Indv(Individu ind) {
	int sim=0;
	for(int i=0;i<ind.length();i++) {
		if(this.getChromosome().contains(ind.getGene(i)))sim++;	
	}
	return sim;
}
public void evaluateSimilarity(Individu[] tabInd) {
	int max,i=0;
	max=this.similarityOf2Indv(tabInd[i]);
	for(i=1;i<tabInd.length;i++){
		if(max<this.similarityOf2Indv(tabInd[i]))max=this.similarityOf2Indv(tabInd[i]);
	}
	this.setSimilary(max);
}
public void evaluateSimilarity(ArrayList<Individu> list) {
	int max,i=0;
	max=this.similarityOf2Indv(list.get(i));
	for(i=1;i<list.size();i++){
		if(max<this.similarityOf2Indv(list.get(i)))max=this.similarityOf2Indv(list.get(i));
	}
	this.setSimilary(max);
}
public boolean egal(Individu ind) {
	boolean bol=true;
	if(this.getChromosome().size()!=ind.getChromosome().size())return false;
	else {
		for(int i=0;i<this.length() & bol;i++) {
			if(this.getGene(i)!= ind.getGene(i))bol=false;
		}
		return bol;
	}
}
}
