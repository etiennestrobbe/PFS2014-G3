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

import model.bdd.GestionnaireStock;
import model.stock.MaterielStock;

public class Test extends JFrame {
	
	private GestionnaireStock gestionStock;
	private LinkedList<MaterielStock> currentStock;
	private GUIManager gui;
	private JComboBox<String> comboName,comboProprieties1,comboProprieties2,comboProprieties3;
	private JList<String> listStock;
	

	public Test(GestionnaireStock gs, GUIManager gui) {
		this.setLayout(new GridLayout(2,4));
		this.gestionStock = gs; 
		this.currentStock = (LinkedList<MaterielStock>) gestionStock.getStock().clone(); 
		this.gui = gui; 
		this.comboName = new JComboBox<String>();
		this.comboProprieties1 = new JComboBox<String>();
		this.comboProprieties2 = new JComboBox<String>();
		this.comboProprieties3 = new JComboBox<String>();
		comboName.addItemListener(new ItemState());
		comboName.addActionListener(new ActionMajStock(this));


		setLocation(500, 250);
//		setVisible(true);
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
	private JComboBox<String> fetchComboPropertie(LinkedList<MaterielStock> stock , JComboBox<String> combo){
		LinkedList<String> listOfProperties = new LinkedList<String>();
		for(MaterielStock ms : stock){
			LinkedList<String> listTemp = ms.getProprietes();
			for(String s : listTemp){
				if(! listOfProperties.contains(s)){
					listOfProperties.add(s);
					combo.addItem(s);
				}
			}
		}
		return combo;
	}
	
	
	
	class ItemState implements ItemListener{
		private LinkedList<MaterielStock> stock;
		private Test frame;
		private JPanel panel;
		private GestionnaireStock gestionStock;
		
		public ItemState(){}
		@Override
		public void itemStateChanged(ItemEvent e) {	
			System.out.println("événement déclenché sur : " + e.getItem());
		}
	}
	class ActionMajStock implements ActionListener{
		private Test frame;
		
		public ActionMajStock(Test f){
			this.frame = f;

		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("#################################");
			System.out.println("Stock courant debut");
			System.out.println("#################################");
			System.out.println(frame.currentStock);
			System.out.println("#################################");
			System.out.println("Stock depart debut");
			System.out.println("#################################");
			System.out.println(frame.gestionStock.getStock());
			frame.currentStock = frame.gestionStock.elementsWhichNameIs((String)comboName.getSelectedItem());
			System.out.println("#################################");
			System.out.println("Stock courant fin");
			System.out.println("#################################");
			System.out.println(frame.currentStock);
			System.out.println("#################################");
			System.out.println("Stock depart fin");
			System.out.println("#################################");
			System.out.println(frame.gestionStock.getStock());
			//listStock.setListData(listData);
			listStock.revalidate();
			frame.comboProprieties1=fetchComboPropertie(currentStock,frame.comboProprieties1);
			
		}
	}
	
	// TODO methode qui met a jour les jcombo en fonction des choix fait avant
	
}
