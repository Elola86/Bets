package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import domain.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

public class WindowTable extends JFrame {

	private Registered user;
	private JTable	tabla;
	
		
	public WindowTable(Registered	user){
		super("Apuestas	realizadas	por	"+	user.getUsername()+":");
		this.setBounds(100,	100,	700,	200);
		this.user =	user;
		
		RegisteredAdapter	adapt	=	new RegisteredAdapter(user);
		tabla =	new JTable(adapt);
		
		tabla.setPreferredScrollableViewportSize(new Dimension(500,	70));
		//Creamos un JscrollPane	y	le agregamos la JTable
		JScrollPane	scrollPane =	new JScrollPane(tabla);
		//Agregamos el	JScrollPane	al contenedor
		getContentPane().add(scrollPane,	BorderLayout.CENTER);
	}
		

}
