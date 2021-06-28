/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;
	private List<Integer> anni;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaAffini"
    private Button btnCercaAffini; // Value injected by FXMLLoader

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxRegista"
    private ComboBox<Director> boxRegista; // Value injected by FXMLLoader

    @FXML // fx:id="txtAttoriCondivisi"
    private TextField txtAttoriCondivisi; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	int anno = boxAnno.getValue();
    	if(anni.contains(anno)) {
    		this.model.creaGrafo(anno);
    		txtResult.appendText("\nGrafo Creato\n");
    		txtResult.appendText("#vertici "+this.model.getNvertici());
    		txtResult.appendText("\n#archi "+this.model.getNArchi());
    		boxRegista.getItems().clear();
    		boxRegista.getItems().addAll(this.model.getVertex());
    		
    	} else {
    		txtResult.appendText("\nErrore, dei inserire prima l'anno\n");
    	}
    }

    @FXML
    void doRegistiAdiacenti(ActionEvent event) {
    	
    	Director d = boxRegista.getValue();
    	
    	if(d!=null) {
    		txtResult.appendText("\n"
    				+ " I registi adiacenti a "+d);
    		txtResult.appendText("\n" +model.getRegistiAdiacenti(d).toString());
    	} else {
    		txtResult.appendText("\n Errore, devi prima selezionare un Regista \n");
    	}

    }

    @FXML
    void doRicorsione(ActionEvent event) {
    	
    	String id_string = txtAttoriCondivisi.getText();
    	
    	try{
    		int i =Integer.parseInt(id_string);
    		Director d = boxRegista.getValue();
        	
        	if(d!=null) {
        		txtResult.appendText(this.model.cerca(d, i));
      
        	} else {
        		txtResult.appendText("\n Errore, devi prima selezionare un Regista \n");
        	}
    		
    	}
    	catch(NumberFormatException e) {
    		
    	}
    	

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaAffini != null : "fx:id=\"btnCercaAffini\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxRegista != null : "fx:id=\"boxRegista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAttoriCondivisi != null : "fx:id=\"txtAttoriCondivisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
   public void setModel(Model model) {
    	
    	this.model = model;
    	 anni = new ArrayList<>();
    	anni.add(2004);
    	anni.add(2005);
    	anni.add(2006);
    	
    	boxAnno.getItems().addAll(anni);
    	
    }
    
}
