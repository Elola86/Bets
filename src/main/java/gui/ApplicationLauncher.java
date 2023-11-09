package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.net.URL;
import java.util.Locale;

import javax.swing.UIManager;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import businessLogic.BLFactory;
import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Registered;

public class ApplicationLauncher { 
	
	public static void main(String[] args) {
		int isLocal = 1;
		BLFacade blFacade =	new BLFactory().getBusinessLogicFactory(isLocal);
		/*try {
			blFacade= new BLFacadeImplementation(); 
			Registered user = blFacade.findUser(new Registered("mikel","123",1));
			WindowTable vt = new WindowTable(user);
			vt.setVisible(true);
		} catch (Exception e) {
				e.printStackTrace();
		}*/
	}
}
