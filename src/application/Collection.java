package application;
import java.util.ArrayList;

public class Collection {
	  private int      C1size;  // The number of individuals C
	  private int      C2size;  // The number of individuals C
	  private ArrayList<Individu> C1I;  // The vector of individuals C1
	  private ArrayList<Chromosome> C1C;   // The vector of chromosoms C1
	  private ArrayList<Individu> C2I;  // The vector of individuals C2 
	  private ArrayList<Chromosome> C2C;   // The vector of chromosoms C2
	  
	  
	  // STATISTICS--------------------------------------------------------------
	  // * 1 For Fitness
	  private int     bestp; // The position of the best  individual: [0..popsize-1]
	  private int     worstp;// The position of the worst individual: [0..popsize-1]
	  private double  bestf; // The best fitness of the present population
	  private double  avgf;  // The average fitness of the present population
	  private double  worstf;// The worst fitness of the present population
	  private double  BESTF; // The best fitness ever found during the search
	  // * 2 For Similarity or Diversity
	  private int     bestSp; // The position of the worst  individual: [0..popsize-1]
	  private int     worstSp;// The position of the best individual: [0..popsize-1]
	  private int  bestS; // The best similarity (worst diversity) of the present population
	  private int  avgS;  // The average similarity (diversity) of the present population
	  private int  worstS;// The worst similarity (best diversity) of the present population
	  
	  
public Collection(int c1,int c2,Population pop) {
	C1size=c1;
	C2size=c2;
	C1I=new ArrayList<Individu>();
	C2I=new ArrayList<Individu>();
	C1C=new ArrayList<Chromosome>();
	C2C=new ArrayList<Chromosome>();
    selectionStrategy(pop);
    
    // Initialize statistics
    bestp = 0;     worstp = 0;
    bestf = 0.0;   avgf   = 0.0;   worstf = 9999999999.0;    BESTF = 0.0;
    bestSp = 0;    worstSp = 0;
    bestS=0;       avgS=0;         worstS = 999999999;
}

public ArrayList<Individu> getC1I() {
	return C1I;
}
public void setC1I(ArrayList<Individu> c1i) {
	C1I = c1i;
}
public ArrayList<Chromosome> getC1C() {
	return C1C;
}
public void setC1C(ArrayList<Chromosome> c1c) {
	C1C = c1c;
}
public ArrayList<Individu> getC2I() {
	return C2I;
}
public void setC2I(ArrayList<Individu> c2i) {
	C2I = c2i;
}
public ArrayList<Chromosome> getC2C() {
	return C2C;
}
public void setC2C(ArrayList<Chromosome> c2c) {
	C2C = c2c;
}
public void addC1I(Individu indv) {
	this.getC1I().add(indv);
}
public void addC1C(Chromosome cr) {
	this.getC1C().add(cr);
}
public void addC2I(Individu indv) {
	this.getC2I().add(indv);
}
public void addC2C(Chromosome cr) {
	this.getC2C().add(cr);
}
public void removeC1I(int index) {
	C1I.remove(index);
}
@Override
public String toString() {
	return "Collection [C1size=" + C1size + ", C2size=" + C2size + ", C1I=" + C1I + ", C1C=" + C1C + ", C2I=" + C2I
			+ ", C2C=" + C2C + ", bestp=" + bestp + ", worstp=" + worstp + ", bestf=" + bestf + ", avgf=" + avgf
			+ ", worstf=" + worstf + ", BESTF=" + BESTF + ", bestSp=" + bestSp + ", worstSp=" + worstSp + ", bestS="
			+ bestS + ", avgS=" + avgS + ", worstS=" + worstS + "]";
}

public void removeC1C(int index) {
	C1C.remove(index);
}
public void removeC2I(int index) {
	C2I.remove(index);
}
public void removeC2C(int index) {
	C2C.remove(index);
}
public void selectionStrategy(Population pop) {
	ArrayList<Individu>	 popcloneI=new ArrayList<Individu>();
	ArrayList<Chromosome>	 popcloneC=new ArrayList<Chromosome>();
	for(int k=0;k<pop.getPopsize();k++) {
		popcloneC.add(pop.getPopC()[k]);
		popcloneI.add(pop.getPopI()[k]);
	}
	C1I.add(popcloneI.get(pop.get_bestp()));
	C1C.add(popcloneC.get(pop.get_bestp()));
	popcloneI.remove(pop.get_bestp());
	popcloneC.remove(pop.get_bestp());

	int maxId,k=0,minSimi;
	double maxfit;
	for(int i=0;i<C1size;i++) {
		maxId=0;k=0;
		maxfit=popcloneI.get(maxId).getFitness();
		while(k<popcloneI.size()) {
			if(maxfit<popcloneI.get(k).getFitness()) {
				maxfit=popcloneI.get(k).getFitness();
			    maxId=k;}
			k++;
		}
		C1I.add(popcloneI.get(maxId));
		C1C.add(popcloneC.get(maxId));
		popcloneI.remove(maxId);
		popcloneC.remove(maxId);
	}
	for(int j=0;j<C2size;j++) {
		maxId=0;k=0;
		minSimi=popcloneI.get(maxId).getSimilary();
		while(k<popcloneI.size()) {
			if(minSimi>popcloneI.get(k).getSimilary()) {
				minSimi=popcloneI.get(maxId).getSimilary();
			    maxId=k;}
			k++;
		}
		C2I.add(popcloneI.get(maxId));
		C2C.add(popcloneC.get(maxId));
		popcloneI.remove(maxId);
		popcloneC.remove(maxId);
	}

	}
public void evaluate() {
	for(int i=0;i<this.getC1I().size();i++) {
		this.getC1I().get(i).evaluatefitness(this.getC1C().get(i));
	}
	for(int j=0;j<this.getC2I().size();j++) {
		this.getC2I().get(j).evaluatefitness(this.getC2C().get(j));
	}
}

public void similarits(Population pop) {
	for(int i=0;i<this.getC2I().size();i++){
		this.getC2I().get(i).evaluateSimilarity(pop.getPopI());
	}
	for(int i=0;i<this.getC1I().size();i++){
		this.getC1I().get(i).evaluateSimilarity(pop.getPopI());
	}
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

public void compute_stats()
{
  double f, total= 0.0;
  int s,totals=0;
  // Initialize values (always needed!!!)
  worstf = C1I.get(0).getFitness();     worstp = 0;
  bestf  = C1I.get(0).getFitness();     bestp  = 0;
  worstS = C2I.get(0).getSimilary();     worstSp = 0;
  bestS  = C2I.get(0).getSimilary();     bestSp  = 0;
  for(int i=0;i<C1C.size();i++)
  {   f = C1I.get(i).getFitness();
    
  if(f<=worstf) {worstf = f; worstp = i;}
  if(f>=bestf)  {bestf  = f; bestp  = i;}
  if(f>=BESTF)  {BESTF  = f;            }
    total += f;
  }
  for(int j=0;j<C2C.size();j++)
  {    s = C2I.get(j).getSimilary();
  if(s<=worstS) {worstS = s; worstSp = j;}
  if(s>=bestS)  {bestS  = s; bestSp  = j;}
    totals += s;
  }
  avgf = total/(double)C1I.size();
  avgS = totals/C2I.size();
}
public void printSolution() {
	System.out.print("the Solution of WDP is : [");
	for(int i=0;i<this.getC1I().get(bestp).length()-1;i++) {
		System.out.print(getC1I().get(bestp).getGene(i)+", ");
	}
	System.out.println(getC1I().get(bestp).getGene(this.getC1I().get(bestp).length()-1)+" ]");
	System.out.println("Fitness : "+getC1I().get(bestp).getFitness());
}

public String SolutionToString() {
	String solution= "the Solution of WDP is : [";
	for(int i=0;i<this.getC1I().get(bestp).length()-1;i++) {
		solution=solution+String.valueOf(getC1I().get(bestp).getGene(i))+", ";
	}
	solution=solution+String.valueOf(getC1I().get(bestp).getGene(this.getC1I().get(bestp).length()-1))+" ]";
	return solution;
}

public String getFitnessString() {
	return String.valueOf(getC1I().get(bestp).getFitness());
}

public String bestChromos() {
	return getC1C().get(bestp).stringOfOffre();
}

public boolean existInC1(Individu ind) {
	boolean exist=false;
	for(int i=0;i<C1size & !exist;i++) {
		if(getC1I().get(i).egal(ind))exist=true;
	}
	return exist;
}
public boolean existInC2(Individu ind) {
	boolean exist=false;
	for(int i=0;i<C2size & !exist;i++) {
		if(getC2I().get(i).egal(ind))exist=true;
	}
	return exist;
}
}
