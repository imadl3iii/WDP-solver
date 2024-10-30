package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class SampleController implements Initializable{
	  @FXML
	    private Button file;

	    @FXML
	    private Button run;

	    @FXML
	    private TextField path;

	    @FXML
	    private TextField maxgen;

	    @FXML
	    private TextField maxiter;

	    @FXML
	    private TextField popsize;

	    @FXML
	    private TextField c1;

	    @FXML
	    private TextField c2;

	    @FXML
	    private TextArea result;

	    @FXML
	    private TextField fitness;

	    @FXML
	    private ImageView progress;

	    @FXML
	    void executAM(ActionEvent event) {
	    	   if( !(path.getText().equals("")||maxgen.getText().equals("")||
	    	             maxiter.getText().equals("")||popsize.getText().equals("")||
	    	             c1.getText().equals("")||c2.getText().equals("")) )
	    	        {
	    		   new Thread(){
	    	            public void run() {	
	    			progress.setVisible(true);
	    			run.setDisable(true);
	    			file.setDisable(true);
	    			result.setText("Start processing.\n Loading ...");
	    		    String mypath=path.getText();
	    			Instance instance=null;
	    			 Reader r;
	    		     try
	    			 {
	    			 r = new BufferedReader(new FileReader(mypath));
	    			 instance=new Instance((BufferedReader)r);
	    			 }
	    			 catch(Exception e) {
	    			    Alert alert = new Alert(AlertType.WARNING);
		    	        alert.setTitle("WARNING");
		    	        alert.setHeaderText("Dataset not exist");
		    	        alert.setContentText("please load a valable dataset !");
		    	        alert.showAndWait();
		    			progress.setVisible(false);
		    			e.printStackTrace();
	    			 }
	    		     int psize = Integer.parseInt(popsize.getText());                     
	    		     int mgen = Integer.parseInt(maxgen.getText());  
	    		     int miter = Integer.parseInt(maxiter.getText());  
	    		     int C1 = Integer.parseInt(c1.getText());  
	    		     int C2 = Integer.parseInt(c2.getText()); 
	    		     int it=0;
	    		     Algorithme AM=new Algorithme(instance, mgen, miter, psize, C1, C2, 0.3);
	    		     Chromosome fils;
	    			 double debut=System.currentTimeMillis();
	    		     while(it<AM.getMaxgen()) {
	    		    	 fils=AM.Croisement();
	    		    	 AM.SLS2(fils);
	    		    	 it++;
	    		     }
	    		     double time=System.currentTimeMillis()-debut;
	    			 progress.setVisible(false);
	    		     result.setText(AM.getC().SolutionToString()+"\n"+AM.getC().bestChromos()+"\n"+"time :"+Double.toString(time));
	    		     //this.result.setText(AM.getC().bestChromos());
	    		     //this.result.setText("time :"+Double.toString(time));
	    		    fitness.setText(AM.getC().getFitnessString());
	    		 	run.setDisable(false);
	    			file.setDisable(false);
	    			try{
	    		        Thread.sleep(1000);
	    		        }catch(InterruptedException ex){
	    		        Thread.currentThread().interrupt();
	    		        }
	    	        }
	    	   
	    	   }.start(); }
	    	   else {
	    		   Alert alert = new Alert(AlertType.WARNING);
	    	        alert.setTitle("WARNING");
	    	        alert.setHeaderText("Running failed");
	    	        alert.setContentText("please fill in all fields or load a good instance of the problem !");
	    	        alert.showAndWait();
	    	   }
	    	   
	    	   
	    	   

	    }

	    @FXML
	    void setPath(ActionEvent event) {
	        FileChooser fileChooser = new FileChooser();
	         //FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
	         //fileChooser.getExtensionFilters().add(extFilter);
	        File file = fileChooser.showOpenDialog(path.getScene().getWindow());
	        if (file != null)path.setText(file.getPath());
	    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		progress.setVisible(false);
	       maxgen.textProperty().addListener(new ChangeListener<String>() {

	             @Override
	             public void changed(ObservableValue<? extends String> ov, String t, String t1) {
	                if(!t1.matches("\\d*"))
	                {
	                    maxgen.setText(t);
	                }
	             }
	         });
	       maxiter.textProperty().addListener(new ChangeListener<String>() {

	             @Override
	             public void changed(ObservableValue<? extends String> ov, String t, String t1) {
	                if(!t1.matches("\\d*"))
	                {
	                	maxiter.setText(t);
	                }
	             }
	         });
	       popsize.textProperty().addListener(new ChangeListener<String>() {

	             @Override
	             public void changed(ObservableValue<? extends String> ov, String t, String t1) {
	                if(!t1.matches("\\d*"))
	                {
	                	popsize.setText(t);
	                }
	             }
	         });
	       
	       c1.textProperty().addListener(new ChangeListener<String>() {

	             @Override
	             public void changed(ObservableValue<? extends String> ov, String t, String t1) {
	                if(!t1.matches("\\d*"))
	                {
	                	c1.setText(t);
	                }
	             }
	         });
	       c2.textProperty().addListener(new ChangeListener<String>() {

	             @Override
	             public void changed(ObservableValue<? extends String> ov, String t, String t1) {
	                if(!t1.matches("\\d*"))
	                {
	                	c2.setText(t);
	                }
	             }
	         });
	}
	
}
