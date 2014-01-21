package gui;

import java.awt.Color;
import java.util.LinkedList;

import javax.swing.JFrame;

import model.bdd.GestionnaireStock;

public class GUIManager {

	public final static Color THEME_COLOR = new Color(0xFF8000); 
	private Login login; 
	private Account account; 
	private Test test;
	private GestionChoix menuGestionStock;
	public static LinkedList<String> listTexte;
	public GUIManager(GestionnaireStock gdd) {

		login = new Login(gdd, this); 
		account = new Account(gdd, this);
		listTexte = new LinkedList<String>();
		listTexte.add("Gestion du stock");
		listTexte.add("Ajouter du materiel au stock :");
		listTexte.add("Supprimer du materiel du stock :");
		listTexte.add("Ajouter");
		listTexte.add("Supprimer");
<<<<<<< HEAD
		menuGestionStock = new GestionChoix(gdd, this,listTexte);
		test = new Test(gdd,this);
=======
		menuGestionStock = new GestionChoix(gdd, this,listTexte); 
		menuGestionStock.setVisible(true);
>>>>>>> ce19026f2ab3e75dc6aa69b5a48be09662d2cbdb
		

	}
	
	public void switchView(JFrame ori,JFrame des){
		ori.setVisible(false);
		des.setVisible(true);
	}

}
