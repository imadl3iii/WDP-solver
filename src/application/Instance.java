package application;
import java.io.BufferedReader;
import java.util.ArrayList;

public class Instance {
private int m;
private int n;
private ArrayList<Offre> listOffres;
private double maxPrice;

public 	Instance(BufferedReader r) {
	listOffres=new ArrayList<Offre>();
	String ligne=null;
	try{	
		ligne=r.readLine();
		ligne=ligne.trim();
		String ch[]=ligne.split("\\s+");
		m=Integer.parseInt(ch[0]);
		n=Integer.parseInt(ch[1]);
		int i=1;
		while((ligne=r.readLine())!=null){
			listOffres.add(new Offre(ligne,i-1));
			i++;
			}
		maxPrice();
		}
	catch(Exception e) {}
		}

public int getM() {
	return m;
}

public void setM(int m) {
	this.m = m;
}

public int getN() {
	return n;
}

public void setN(int n) {
	this.n = n;
}

public ArrayList<Offre> getListOffres() {
	return listOffres;
}

public void setListOffres(ArrayList<Offre> listOffres) {
	this.listOffres = listOffres;
}
public Offre getOffre(int indice) {
	return listOffres.get(indice);
}
public Offre bestPrice() {
	double max=0.0;
	int maxId=0;
	for(int i=0;i<listOffres.size();i++) {
		if(max<listOffres.get(i).getPrix()) {
			max=listOffres.get(i).getPrix();
			maxId=i;
		}
	}
	return listOffres.get(maxId);
}
public Offre increasePrice() {
	double max=0.0;
	int maxId=0;
	Interval intr=new Interval(0, getN());

	while(max<this.maxPrice*70/100){
		maxId=intr.getIntegerRandom();
		max=listOffres.get(maxId).getPrix();
	}

	Offre o=new Offre(listOffres.get(maxId).getObjectsCover(), listOffres.get(maxId).getPrix());
	o.setNumOffre(listOffres.get(maxId).getNumOffre());
	return o;
}
public void maxPrice() {
	double max=0.0;
	for(int i=0;i<listOffres.size();i++) {
		if(max<listOffres.get(i).getPrix()) {
			max=listOffres.get(i).getPrix();
		}
	}
	this.maxPrice=max;
}
@Override
public String toString() {
	return "Instance [m=" + m + ", n=" + n + ", listOffres=" + listOffres + "]";
}

}
