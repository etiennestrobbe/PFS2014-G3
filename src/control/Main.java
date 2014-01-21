package control;
/**
 * author Strobbe Etienne
 */
import gui.GUIManager;

import java.util.Calendar;
import java.util.LinkedList;

import model.stock.*;
import model.temps.DateAbsolue;

import java.util.Scanner;

import model.bdd.*;
import model.emprunt.*;
import personne.*;

public class Main {

	private static GestionnaireStock bdd = new GestionnaireStock("./BDD/");
	private static Scanner scan;
	private static String commande;
	private static Personne personne;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String chemin = "./BDD/";
		GestionBDD.setBDD(chemin);
		GestionnaireStock gestionStock = new GestionnaireStock(chemin);
		GUIManager gui = new GUIManager(gestionStock);
	}

}
