package application;
import java.util.ArrayList;
import java.util.LinkedList;

public class Graph {
 private LinkedList<Sommet> list;
 public Graph() {
	 list=new LinkedList<Sommet>();
 }
 
 public void addSommet(int Noffre) {
	 Sommet s=new Sommet(Noffre);
	 list.add(s);
 }
 public Sommet get(int index) {
	 return list.get(index);
 }
 public int getIndex(Sommet s) {
	 for(int i=0;i<list.size();i++) {
		 if(list.get(i)==s) 
			 return i;
	 }
	 return -1;
 }
 public int size() {
	 return list.size();
 }
 
 public Sommet getSommetByInt(int offre) {
	 for(Sommet s: list) {
		 if(s.getNoeud()==offre)return s;
	 }
	 return null;
 }
 
 public boolean getEdge(Sommet s1,Sommet s2) {
	 if(s1.ifConflit(s2) || s2.ifConflit(s1)) return true;
	 else 
		 return false;
 }
 public boolean getEdge(int s1,int s2) {
	 if(getSommetByInt(s1).ifConflit(getSommetByInt(s2)) || getSommetByInt(s2).ifConflit(getSommetByInt(s1))) return true;
	 else 
		 return false;
 }
	public void creatConflictGraph(Instance ins) {
		for(int i=0;i<ins.getN();i++)this.addSommet(ins.getOffre(i).getNumOffre());
		for(int j=0;j<ins.getN();j++) {
			for(int k=j+1;k<ins.getN();k++) {
				if(ins.getOffre(j).conflit(ins.getOffre(k).getObjectsCover())) {
				this.get(ins.getOffre(j).getNumOffre()).addVoisin(this.get(ins.getOffre(k).getNumOffre()));
				this.get(ins.getOffre(k).getNumOffre()).addVoisin(this.get(ins.getOffre(j).getNumOffre()));
				}
			}
		}
	}
	
	public boolean noConflict(ArrayList<Offre> lis,Offre o) {
		if(this.offreContain(lis, o)){return false;}
		else {
		boolean Nconfli=true;
		for(int i=0;i<lis.size() & Nconfli;i++) {
			if(getEdge(lis.get(i).getNumOffre(), o.getNumOffre())){Nconfli=false;}
		}
		return Nconfli;}
	}
	public ArrayList<Offre> removeConflict(ArrayList<Offre> lis,Offre o) {
		if(this.offreContain(lis, o)){return lis;}
		else {
		int i;
		for(i=0;i<lis.size();i++) {
			if(getEdge(lis.get(i).getNumOffre(), o.getNumOffre())){lis.remove(i);i--;}
		}
		lis.add(o);
		return lis;}
	}
	
	public boolean offreContain(ArrayList<Offre> lis,Offre o) {
		boolean contain=false;
		int i=0;
		while(i<lis.size() & !contain) {
			if(lis.get(i).equals(o))contain=true;		
			i++;
		}
		return contain;
	}
	public void print() {
		System.out.print("les offres [ ");
		for(int i=0;i<this.size()-1;i++)System.out.print(this.get(i).getNoeud()+", ");
		System.out.println(this.get(this.size()-1).getNoeud()+" ]");
		System.out.print("Edges [ ");
		for(int i=0;i<this.size()-1;i++) {
			this.get(i).printArcs();
		}
		System.out.println(" ]");

	}
}

