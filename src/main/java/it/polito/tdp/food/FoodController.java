/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.food.model.Model;
import it.polito.tdp.food.model.PorzioneAdiacente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco cammino peso massimo...");
    	
    	String tipo = this.boxPorzioni.getValue();
    	
    	if(tipo == null) {
    		this.txtResult.appendText("Devi selezionare un tipo! \n");
    		return;
    	}
    	
    	int n = 0;
    	
    	try {
    		n = Integer.parseInt(this.txtPassi.getText());    		
    	}catch (NumberFormatException e) {
    		this.txtResult.appendText("Il valore "+txtPassi.getText()+" non e' un numero intero!\n");
    		return;
    	}
    	
    	this.model.cercaCammino(tipo, n);
    	if(model.getCamminoMax()==null) {
    		this.txtResult.appendText("Non ho trovato un cammino di lunghezza N\n");
    	}else {
    		txtResult.appendText("Trovato un cammino di peso "+model.getPesoMax()+"\n");
    		for(String vertice : model.getCamminoMax()) {
    			txtResult.appendText(vertice+"\n");
    		}
    	}
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco porzioni correlate...\n");
    
    	String tipo = this.boxPorzioni.getValue();
    	
    	if(tipo == null) {
    		this.txtResult.appendText("Devi selezionare un tipo! \n");
    		return;
    	}
    	
    	List<PorzioneAdiacente> adiacenti = this.model.getAdiacenti(tipo);
    	for(PorzioneAdiacente p : adiacenti) {
    		txtResult.appendText(String.format("%s %f\n", p.getPorzione(), p.getPeso()));
    	}
    	
    	/*FATTO IO QUANDO NON AVEVO CREATO LA CLASSE DELLE PORZIONI ADIACENTI
    	 * for(String s : this.model.getVicini(tipo)) {
    		this.txtResult.appendText(s);
    	} */       	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	String num = this.txtCalorie.getText();
    	int n = 0;
    	
    	try {
    		n = Integer.parseInt(num);    		
    	}catch (NumberFormatException e) {
    		this.txtResult.appendText("Devi inserire un numero !\n");
    		return;
    	}
    	if(num == null) {
    		this.txtResult.appendText("Devi completare il campo del numero delle calorie!\n");
    		return;
    	}
    	
    	this.model.creaGrafo(n);
    	this.txtResult.appendText("n VERTICI: "+this.model.nVertici()+"\n");
    	this.txtResult.appendText("n ARCHI: "+this.model.nArchi()+"\n");
    
    	this.boxPorzioni.getItems().clear();
    	this.boxPorzioni.getItems().addAll(this.model.getVertici());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;    	
    }
}
