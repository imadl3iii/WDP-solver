package application;
import java.util.ArrayList;
import java.util.Arrays;

public class Population {
	 // PRIVATE MEMORY
	  private int      popsize;  // The number of individuals
	  private Individu popI[];  // The vector of individuals
	  private Chromosome popC[];   // The vector of chromosoms

	  // STATISTICS--------------------------------------------------------------
	  // * 1 For Fitness
	  private int     bestp; // The position of the best  individual: [0..popsize-1]
	  private int     worstp;// The position of the worst individual: [0..popsize-1]
	  private double  bestf; // The best fitness of the present population
	  private double  avgf;  // The average fitness of the present population
	  private double  worstf;// The worst fitness of the present population
	  private double  BESTF; // The best fitness ever found during the search
	  // * 2 For Similarity or Diversity
	  private int     bestSp; // The position of the best  individual: [0..popsize-1]
	  private int     worstSp;// The position of the worst individual: [0..popsize-1]
	  private int  bestS; // The best similarity (worst diversity) of the present population
	  private int  avgS;  // The average similarity (diversity) of the present population
	  private int  worstS;// The worst similarity (best diversity) of the present population
	  public Population(int ps, Graph conflictGraph,Instance I)
	  {
	    popsize      = ps;
	    popI=new Individu[popsize];
	    popC=new Chromosome[popsize]; 
	    //Générer la population initiale P par l'encodage de clef aléatoire;
        this.RandomKey(I, conflictGraph);
	    // Initialize statistics
	    bestp = 0;     worstp = 0;
	    bestf = 0.0;   avgf   = 0.0;   worstf = 9999999999.0;    BESTF = 0.0;
	    bestSp = 0;    worstSp = 0;
	    bestS=0;       avgS=0;         worstS = 999999999;
	  }
	  
	  /*
	  public void RandomKey(Instance inst,Graph g) {
		  ArrayList<Double> keys=this.keysArray(inst.getN());
		  double max=0.0;
		  int maxId=0,k=0;
		  Chromosome chrom;
		  Individu indv;
		  ArrayList<Offre> offres=new ArrayList<Offre>();
		  ArrayList<Integer> ints=new ArrayList<Integer>();
		  int it=0;
		  System.out.println("while popsize");
		  while(it<popsize) {
				 offres=new ArrayList<Offre>();
				 ints=new ArrayList<Integer>();
				  System.out.println("while keys");
				  k=0;
			 while(k<inst.getN()) {
				 maxId=0;max=0;
		        for(int j=0;j<keys.size();j++) {
		  		  System.out.println("keys(["+keys.get(j)+"]");
			      if(max<keys.get(j)) {
				     max=keys.get(j);
				     maxId=j;
					  System.out.println("yes max "+max+"id "+maxId);
			       }
		         }
			 if(g.noConflict(offres, inst.getOffre(maxId+k))) {
				 offres.add(inst.getOffre(maxId+k));
				 ints.add(inst.getOffre(maxId+k).getNumOffre());
				  System.out.println("no conflict");
			 }
			 keys.remove(maxId);
			 k++; 
			 }
		  chrom=new Chromosome(offres, it);
		  indv=new Individu(ints);
		  popC[it]=chrom;
		  popI[it]=indv;
		  keys=this.keysArray(inst.getN());
		  it++;
		  }
	 }*/
	  
	  public void RandomKey(Instance inst,Graph g) {
		  double[] keys=this.keystab(inst.getN());
		  double max=0.0;
		  int maxId=0,k=0;
		  Chromosome chrom;
		  Individu indv;
		  ArrayList<Offre> offres=new ArrayList<Offre>();
		  ArrayList<Integer> ints=new ArrayList<Integer>();
		  int it=0;
		  while(it<popsize) {
				 offres=new ArrayList<Offre>();
				 ints=new ArrayList<Integer>();
				  k=0;
			 while(k<inst.getN()) {
				 maxId=0;max=0;
		        for(int j=0;j<inst.getN();j++) {
			      if(max<keys[j]) {
				     max=keys[j];
				     maxId=j;
			       }
		         }
			 if(g.noConflict(offres, inst.getOffre(maxId))) {
				 offres.add(inst.getOffre(maxId));
				 ints.add(inst.getOffre(maxId).getNumOffre());
			 }
			 keys[maxId]=-1;
			 k++; 
			 }
		  chrom=new Chromosome(offres, it);
		  indv=new Individu(ints);
		  popC[it]=chrom;
		  popI[it]=indv;
		  keys=this.keystab(inst.getN());
		  it++;
		  }
	 }
	public double[] keystab(int size) {
		double[] keys=new double[size]; 
		keys[0]=Math.random();
		for(int i=1;i<size;i++) {
			do
			keys[i]=Math.random();
			while(keys[i] == keys[i-1]);
		}
		return keys;
	}
	public ArrayList<Double> keysArray(int size) {
		ArrayList<Double> keys=new ArrayList<Double>(); 
		double rand=Math.random();
		keys.add(rand);
		for(int i=1;i<size;i++) {
			do
				rand=Math.random();
			while(rand == keys.get(i-1));
			keys.add(rand);
		}
		return keys;
	}
	  @Override
	public String toString() {
		return "Population [popsize=" + popsize + ", popI=" + Arrays.toString(popI) + ", popC=" + Arrays.toString(popC)
				+ ", bestf=" + bestf + ", avgf=" + avgf + ", worstf=" + worstf + ", BESTF=" + BESTF + ", bestS=" + bestS
				+ ", avgS=" + avgS + ", worstS=" + worstS + "]";
	}

	public void set_fitness( int index, double fitness ) throws Exception
	  {
	    popI[index].setFitness(fitness);
	  }
	  
	  public void compute_stats()
	  {
	    double f, total= 0.0;
        int s,totals=0;
	    // Initialize values (always needed!!!)
	    worstf = popI[0].getFitness();     worstp = 0;
	    bestf  = popI[0].getFitness();     bestp  = 0;
	    worstS = popI[0].getSimilary();     worstSp = 0;
	    bestS  = popI[0].getSimilary();     bestSp  = 0;
	    for(int i=0;i<popsize;i++)
	    {   f = popI[i].getFitness();
	        s = popI[i].getSimilary();
	    if(f<=worstf) {worstf = f; worstp = i;}
	    if(f>=bestf)  {bestf  = f; bestp  = i;}
	    if(f>=BESTF)  {BESTF  = f;            }
	    if(s<=worstS) {worstS = s; worstSp = i;}
	    if(s>=bestS)  {bestS  = s; bestSp  = i;}
	      total += f;
	      totals += s;
	    }

	    avgf = total/(double)popsize;
	    avgS = totals/popsize;
	  }

public int    get_worstp() { return worstp; }
public int    get_bestp()  { return bestp;  }
public double get_worstf() { return worstf; }
public double get_avgf()   { return avgf;   }
public double get_bestf()  { return bestf;  }
public double get_BESTF()  { return BESTF;  }


public int    get_worstSp() { return worstSp; }
public int    get_bestSp()  { return bestSp;  }
public int get_worstS() { return worstS; }
public int get_avgS()   { return avgS;   }
public int get_bestS()  { return bestS;  }

public int getPopsize() {
	return popsize;
}

public void setPopsize(int popsize) {
	this.popsize = popsize;
}

public Individu[] getPopI() {
	return popI;
}

public void setPopI(Individu[] popI) {
	this.popI = popI;
}

public Chromosome[] getPopC() {
	return popC;
}

public void setPopC(Chromosome[] popC) {
	this.popC = popC;
}

public void evaluate() {
	for(int i=0;i<this.popsize;i++) {
		this.popI[i].evaluatefitness(this.popC[i]);
	}
}

public void similarits() {
	for(int i=0;i<popsize;i++){
		popI[i].evaluateSimilarity(popI);
	}
}
public void print() {
	
}

public void print_stats()
{
  System.out.print(BESTF);  System.out.print("   ");
  System.out.print(bestf);  System.out.print("   ");
  System.out.print(avgf);   System.out.print("   ");
  System.out.print(worstf); System.out.print("   ");
  System.out.print(bestp);  System.out.print("   ");
  System.out.println(worstp);
}
}
