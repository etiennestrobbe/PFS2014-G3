package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
/**
 * @author Etienne Strobbe
 */

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import listener.ActionMajStock;
import model.bdd.GestionnaireStock;
import model.stock.MaterielStock;

public class Test extends JFrame {
	
	private GestionnaireStock gestionStock;
	private LinkedList<MaterielStock> currentStock,stockAfterSelectedName,stockAfterSelectProperty1;
	private GUIManager gui;
	private JComboBox<String> comboName,comboProprieties1,comboProprieties2,comboProprieties3;
	private JList<String> listStock;
	

	public Test(GestionnaireStock gs, GUIManager gui) {
		this.setLayout(new GridLayout(8,1));
		this.gestionStock = gs; 
		this.currentStock = (LinkedList<MaterielStock>) gestionStock.getStock().clone(); 
		this.gui = gui; 
		this.comboName = new JComboBox<String>();
		this.comboProprieties1 = new JComboBox<String>();
		this.comboProprieties2 = new JComboBox<String>();
		this.comboProprieties3 = new JComboBox<String>();
		comboName.addActionListener(new ActionMajStock(this));
		comboProprieties1.addActionListener(new ActionMajStock(this));
		comboProprieties2.addActionListener(new ActionMajStock(this));


		setLocation(500, 250);
		setVisible(true);
		fetchComboName(gestionStock);
		Container mainFrame = getContentPane();
		mainFrame.add(comboName);
		mainFrame.add(comboProprieties1);
		mainFrame.add(comboProprieties2);
		mainFrame.add(comboProprieties3);
		listStock = inStockList();
		mainFrame.add(listStock);
		pack(); 
		 
	}
	
	public LinkedList<MaterielStock> getStockAfterSelectedName() {
		return stockAfterSelectedName;
	}


	public void setStockAfterSelectedName(
			LinkedList<MaterielStock> stockAfterSelectedName) {
		this.stockAfterSelectedName = stockAfterSelectedName;
	}


	public LinkedList<MaterielStock> getStockAfterSelectProperty1() {
		return stockAfterSelectProperty1;
	}


	public void setStockAfterSelectProperty1(
			LinkedList<MaterielStock> stockAfterSelectProperty1) {
		this.stockAfterSelectProperty1 = stockAfterSelectProperty1;
	}


	public GestionnaireStock getGestionStock() {
		return gestionStock;
	}

	public void setGestionStock(GestionnaireStock gestionStock) {
		this.gestionStock = gestionStock;
	}

	public LinkedList<MaterielStock> getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(LinkedList<MaterielStock> currentStock) {
		this.currentStock = currentStock;
	}

	public GUIManager getGui() {
		return gui;
	}

	public void setGui(GUIManager gui) {
		this.gui = gui;
	}

	public JComboBox<String> getComboName() {
		return comboName;
	}

	public void setComboName(JComboBox<String> comboName) {
		this.comboName = comboName;
	}

	public JComboBox<String> getComboProprieties1() {
		return comboProprieties1;
	}

	public void setComboProprieties1(JComboBox<String> comboProprieties1) {
		this.comboProprieties1 = comboProprieties1;
	}

	public JComboBox<String> getComboProprieties2() {
		return comboProprieties2;
	}

	public void setComboProprieties2(JComboBox<String> comboProprieties2) {
		this.comboProprieties2 = comboProprieties2;
	}

	public JComboBox<String> getComboProprieties3() {
		return comboProprieties3;
	}

	public void setComboProprieties3(JComboBox<String> comboProprieties3) {
		this.comboProprieties3 = comboProprieties3;
	}

	public JList<String> getListStock() {
		return listStock;
	}

	public void setListStock(JList<String> listStock) {
		this.listStock = listStock;
	}
	public JList<String> inStockList(){
		
		DefaultListModel<String> model = new DefaultListModel<String>();
		
		for(MaterielStock ms : this.currentStock){
			model.addElement(ms.getName() + " " + ms.getProprietes()); 
		}
		
		JList<String> items = new JList<String>(model);
		items.setBackground(GUIManager.THEME_COLOR); 
		items.setForeground(Color.BLACK); 
		
		return items; 
	}
	
	/**
	 * Methode qui initialise l'attribut comboName d'apres le stock
	 */
	private void fetchComboName(GestionnaireStock gs){
		LinkedList<MaterielStock> stock = gs.getStock();
		LinkedList<String> namesOfMateriel = new LinkedList<String>();
 		for(MaterielStock ms : stock){
 			if(! namesOfMateriel.contains(ms.getName())){
 				namesOfMateriel.add(ms.getName());
 				this.comboName.addItem(ms.getName());
 			}
		}
	}
	
	/**
	 * Methode qui renvoi un object JComboBox contenant les proprietes restantes pour le stock donne en parametre	
	 * @param gs le stock 
	 * @param combo l'objet renvoye
	 */
	public void fetchComboPropertie(LinkedList<MaterielStock> stock , JComboBox<String> combo){
		LinkedList<String> listOfProperties = new LinkedList<String>();
		combo.removeAllItems();
		for(MaterielStock ms : stock){
			LinkedList<String> listTemp = ms.getProprietes();
			for(String s : listTemp){
				if(! listOfProperties.contains(s)){
					listOfProperties.add(s);
					combo.addItem(s);
				}
			}
		}
	}
	
}
