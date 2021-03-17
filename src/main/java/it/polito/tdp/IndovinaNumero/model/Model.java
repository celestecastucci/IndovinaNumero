package it.polito.tdp.IndovinaNumero.model;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;

public class Model {

	//sono stati tolti dal controller perchè fanno parte della gestione 
	private final int NMAX = 100;
	private final int TMAX = 8;
	private int segreto;
	private int tentativiFatti;
	private boolean inGioco = false;
	
	//definisco un SET per memorizzare i tentativi fatti --> cosi verifico se ho già inserito il numero
	Set<Integer>tentativiSet;
	
	/**
	 * CREO METODO NUOVA PARTITA --> GESTIONE
	 * E' UN VOID PERCHE' NON DEVE RESTITUIRE NULLA
	 */ 
	public void nuovaPartita() {
		//gestione inizio nuova partita
    	this.segreto = (int) (Math.random() * NMAX) +1;
    	this.tentativiFatti = 0;
    	this.inGioco = true;
    	tentativiSet= new HashSet<>();
	}
	
	/**
	 * CREO METODO TENTATIVO 
	 * DEVE RITORNARE QUALCOSA --> non puo essere void 
	 * posso prevedere un ritorno intero con una convenzione (0 corretto, 1 alto, -1 basso)
	 */

	public int tentativo(int tentativo) {
		//controlla se la partita è in corso --> in caso contrario ECCEZIONE STATO ILLEGALE
		if(!inGioco) {
			throw new IllegalStateException("hai perso, il segreto era "+this.segreto);
		}
		
		//controllo dell'input --> l'avevo già fatto nel Controller 
		//però qui faccio controlli sul modello e lì lascio quelli relativi a interfaccia
		//richiamo il metodo booleano
		if(!tentativoValido(tentativo)) {
			throw new InvalidParameterException("devi inserire un numero tra 1 e "+NMAX+"e non puoi inserire più volte lo stesso numero");
			}
		
		//se sono qui il tentativo è valido e aggiungo al SET QUEL TENTATIVO
		this.tentativiFatti++;
		this.tentativiSet.add(tentativo);  
		
		if(this.tentativiFatti==(TMAX-1)) {  //TMAX-1 altrimenti anche a 0 tentativi posso ancora giocare
			this.inGioco=false;   //cosi la volta successiva si bloccherà dicendo che non è in gioco generando ILLEGAL e che ho perso
		
		}
		
		//cosa il metodo ritorna nei vari casi
		if(tentativo==this.segreto) {
			this.inGioco=false;
			return 0;
		} else if( tentativo<this.segreto) {
			return -1;
		} else {
			return 1;
		}
	}
		/**
		 * CREO UN METODO BOOLEAN PER IL CONTROLLO SUL TENTATIVO --> è piu generico 
		 * nell'if del public richiamo solo questo metodo
		 */
		public boolean tentativoValido(int tentativo) {
		   if(tentativo<1 || tentativo>NMAX) 
			return false;
			
		   if(tentativiSet.contains(tentativo))  //accedo direttamente al set con il contains
		   return false;
			
		return true;
		
	}
	
	/**
	 * CREO METODI GETTER PER RICHIAMARE LE VARIABILI PRESENTI QUI NELLA CLASSE CONTROLLER
	 */
	
	public int getNMAX() {
		return NMAX;
	}

	public int getTMAX() {
		return TMAX;
	}

	public int getSegreto() {
		return segreto;
	}

	public int getTentativiFatti() {
		return tentativiFatti;
	}
	
	
}
