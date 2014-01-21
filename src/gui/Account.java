package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.ListModel;

import model.bdd.GestionnaireStock;
import model.stock.MaterielStock;

public class Account extends JFrame {
	
	private GestionnaireStock gdd;
	private GUIManager gui;
	

	public Account(GestionnaireStock gdd, GUIManager gui) {
		this.gdd = gdd; 
		this.gui = gui; 
		setLocation(500, 250);
		
		//setVisible(true);
		
		Container mainFrame = getContentPane();

		mainFrame.add(inStockList(), BorderLayout.CENTER);
		
		pack(); 
		 
	}

	
	public JList inStockList(){
		
		LinkedList<MaterielStock> stock = gdd.getStock();
		
		DefaultListModel<String> model = new DefaultListModel<String>();
		
		for(MaterielStock ms : stock){
			model.addElement(ms.getName() + " " + ms.getProprietes()); 
		}
		
		JList<String> items = new JList<String>(model);
		items.setBackground(GUIManager.THEME_COLOR); 
		items.setForeground(Color.BLACK); 
		
		return items; 
	}
	
}
