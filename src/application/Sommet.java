package application;
import java.util.LinkedList;

public class Sommet {
	private int offre;
	private boolean marque=false;
	private LinkedList<Sommet> voisins;
    
	public Sommet(int offre) {
		this.offre=offre;
		voisins=new LinkedList<Sommet>();
	}
	public int getNoeud() {
		return offre;
	}
	public void setNoeud(int offre) {
		this.offre = offre;
	}
	public boolean isMarque() {
		return marque;
	}
	public void setMarque(boolean marque) {
		this.marque = marque;
	}
	public LinkedList<Sommet> getVoisins() {
		return voisins;
	}
	public void setVoisins(LinkedList<Sommet> voisins) {
		this.voisins = voisins;
	}
	public void addVoisin(Sommet voisin) {
		voisins.add(voisin);
	}
	
	public boolean ifConflit(Sommet s) {
		boolean conflit=false;
		for(int i=0;i<this.getVoisins().size() & !conflit;i++) {
			if(this.getVoisins().get(i).getNoeud() == s.getNoeud())conflit=true;
		}
		return conflit;
		//return voisins.contains(s);
	}
	
	public void printArcs() {
		for(int i=0;i<this.voisins.size();i++){
			System.out.print(" {"+offre+"-"+getVoisins().get(i).getNoeud() +"} ");
		}
	}
}
