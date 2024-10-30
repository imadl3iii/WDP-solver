package application;
import java.util.ArrayList;

public class Chromosome {
	private ArrayList<Offre> listGene;
    @Override
	public String toString() {
		return "Chromosome [listGene=" + listGene + "]";
	}
    public String stringOfOffre() {
		return "The accepted bids: [\n" + listGene + "]";
	}
	private int numChrom;
	public Chromosome(ArrayList<Offre> listGene, int numChrom) {
		super();
		this.listGene = listGene;
		this.numChrom = numChrom;
	}
	public ArrayList<Offre> getListGene() {
		return listGene;
	}
	public void setListGene(ArrayList<Offre> listGene) {
		this.listGene = listGene;
	}
	public int getNumChrom() {
		return numChrom;
	}
	public void setNumChrom(int numChrom) {
		this.numChrom = numChrom;
	}
    public void setGene(Offre o) {
    	listGene.add(o);
    }
    public void setGene(int i,Offre o) {
    	listGene.add(i, o);
    }
    public Offre getGene(int indice) {
    	return listGene.get(indice);
    }
    public int length() {
    	return listGene.size();
    }
}
