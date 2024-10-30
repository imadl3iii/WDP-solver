package application;
import java.util.Arrays;

public class Offre {
private int[] objectsCover;  //Sj
private double prix;        //Pj
private int num;
public Offre(int[] objectsCover, double prix) {
	this.objectsCover = objectsCover;
	this.prix = prix;
}
public Offre(String data,int numOff) {
	this.transOff(data);
	num=numOff;
}
public int getNumOffre() {
	return num;
}
public int[] getObjectsCover() {
	return objectsCover;
}
public void setObjectsCover(int[] objectsCover) {
	this.objectsCover = objectsCover;
}
public double getPrix() {
	return prix;
}
public void setPrix(double prix) {
	this.prix = prix;
}
public int length() {
	return objectsCover.length;
}
private void transOff(String data) {
	data=data.trim();
	String ch[]=data.split("\\s+");
	this.objectsCover=new int[ch.length-1];
	this.prix=Double.parseDouble(ch[0]);
	for(int i=1;i<ch.length;i++)
		this.objectsCover[i-1]=Integer.parseInt(ch[i]);
	
}

public boolean exist(int objet) {
	boolean exist=false;
	for(int i=0;i<objectsCover.length;i++) {
		if(objet==objectsCover[i])
			exist=true;
			}
	return exist;
}
public boolean conflit(int[] offr2) {
	boolean conflit=false;
	for(int i=0;i<offr2.length;i++) {
		if(this.exist(offr2[i]))conflit=true;		
	}
	return conflit;
}


public void setNumOffre(int num) {
	this.num = num;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Offre other = (Offre) obj;
	if (num != other.num)
		return false;
	if (!Arrays.equals(objectsCover, other.objectsCover))
		return false;
	if (Double.doubleToLongBits(prix) != Double.doubleToLongBits(other.prix))
		return false;
	return true;
}
@Override
public String toString() {
	return "Bid [ <Sj=" + Arrays.toString(objectsCover) + ", Pj=" + prix + ">]\n";
}

}
