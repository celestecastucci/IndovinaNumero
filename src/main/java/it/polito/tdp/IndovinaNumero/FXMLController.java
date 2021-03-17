/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.IndovinaNumero;

import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ResourceBundle;

import it.polito.tdp.IndovinaNumero.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML // fx:id="layoutTentativo"
    private HBox layoutTentativo; // Value injected by FXMLLoader

    @FXML // fx:id="btnNuovaPartita"
    private Button btnNuovaPartita; // Value injected by FXMLLoader

    @FXML // fx:id="txtTentativi"
    private TextField txtTentativi; // Value injected by FXMLLoader

    @FXML // fx:id="txtTentativoUtente"
    private TextField txtTentativoUtente; // Value injected by FXMLLoader

    @FXML // fx:id="btnProva"
    private Button btnProva; // Value injected by FXMLLoader

    @FXML // fx:id="txtRisultato"
    private TextArea txtRisultato; // Value injected by FXMLLoader

    @FXML
    void doNuovaPartita(ActionEvent event) {
    	
    	//invoco il metodo in Model per iniziare la partita
    	this.model.nuovaPartita();  
    	
    	/**
    	 * QUI RIMANE LA PARTE GRAFICA LEGATA A SCENE BUILDER
    	 * ma il controller non sa più cosa sia TMAX --> è definito di la
    	 * quindi quando ci serve qualcosa riferito alla logica applicativa devo RICHIAMARE IL MODELLO
    	 * AVENDO DEFINITO QUI LA CLASSE COME Model model;
    	 * CREO NELLA CLASSE MODELLO TUTTI I METODI GETTER DELLE VARIABILI !
    	 */
    	//gestione dell'interfaccia 
    	this.txtRisultato.clear(); //pulisco ogni volta che faccio nuova partita
    	this.txtTentativi.setText(Integer.toString(this.model.getTMAX()));
    	this.layoutTentativo.setDisable(false);
    }

    @FXML
    void doTentativo(ActionEvent event) {
    	//lettura input dell'utente
    	String ts = txtTentativoUtente.getText();
    	
    	int tentativo;
    	try {
    		tentativo = Integer.parseInt(ts);
    	}catch(NumberFormatException e) {
    		txtRisultato.setText("Devi inserire un numero!");
    		this.txtTentativoUtente.setText("");  //pulisco anche se entro nell'eccezione 
        	
    		return;
    	}
    	
   this.txtTentativoUtente.setText("");
    	
    	
    	/**
    	 * DEVO CREARE UN METODO TRY/CATCH per richiamare i vari tipi di controlli
    	 * creo una variabile ris a cui assegno il metodo creato in model
    	 * se entro nel CATCH metto come testo nel txtRisultato il messaggio
    	 *  che ho scritto per quell'eccezione nel model con nomeEccezioneAbbreviato.getMessage()
    	 */
    	
    	int ris;
    	try{
    		ris=this.model.tentativo(tentativo);     //tutto ok 
    		
    	} catch(IllegalStateException se) {         //prima eccezione 
    		this.txtRisultato.setText(se.getMessage());
    		this.layoutTentativo.setDisable(true);  //SE HO PERSO DEVO DISABILITARE IL LAYOUT
    		return;
    	} catch(InvalidParameterException pe) {     //seconda eccezione
    		this.txtRisultato.setText(pe.getMessage());
    		return;
    	}
    	
    	//AGGIORNO IL BOX DEI TENTATIVI
    	this.txtTentativi.setText(Integer.toString(this.model.getTMAX()-this.model.getTentativiFatti()));
    	
    	if(ris==0) {
    		//HO INDOVINATO
    		txtRisultato.setText("HAI VINTO CON " + this.model.getTentativiFatti() + "TENTATIVI");
    		this.layoutTentativo.setDisable(true);
    		return;
    		//NON HO INDOVINATO INFORMO DELLA BONTA' --> ho adattato qui i vari casi possibili
    	} else if(ris<0) {
    		txtRisultato.setText("TENTATIVO TROPPO BASSO");
    	} else {
    		txtRisultato.setText("TENTATIVO TROPPO ALTO");
    	}
    	
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnNuovaPartita != null : "fx:id=\"btnNuovaPartita\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTentativi != null : "fx:id=\"txtTentativi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTentativoUtente != null : "fx:id=\"txtTentativoUtente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnProva != null : "fx:id=\"btnProva\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model=model;
    }
    
}
