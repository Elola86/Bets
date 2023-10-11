package tests;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import domain.Event;
import domain.Team;
import test.dataAccess.TestDataAccess;

public class GertaerakSortuDAWTest {

	//sut:system under test
	static DataAccess sut=new DataAccess();
			 
	//additional operations needed to execute the test 
	static TestDataAccess testDA=new TestDataAccess();

	private Event ev;
	private Event ev2;
	private Event ev3;
	private Event ev4;
	
	private String description;
	private String description2;
	private String description3;
	private String description4;
	private Date eventDate;
	private String sport;
	@Before
	public void initialize() {
		description = "Real Madrid-FC Barcelona";
		description2 = "Las Palmas-Sevilla";
		description3= "Real Sociedad-Levante";
		description4= "Eibar-Barcelona";
	    eventDate = null; 
	    sport = "Futbol"; 
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        
	    try {
			eventDate = sdf.parse("10/10/2023");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
	}

	//El deporte recibido no está en la BD
	@Test
	public void test1() {
		try {
   			description = "";
   	        sport = "Hockey"; // Deporte que no está en la base de datos
   	        boolean expected=false;
   	        boolean obtained = sut.gertaerakSortu(description, eventDate, sport);
   	     
   	     	assertEquals(expected,obtained);  			
   		  
   		   } catch (Exception e) {
   		   		fail();
   		   } 
	}
	
	//No hay eventos en la BD
	@Test
	public void test2() {
		try {
   	        boolean expected=true;  
   	        
   	        
   	        boolean obtained = sut.gertaerakSortu(description, eventDate, sport);
   	        //Para conseguir el evento que queremos añadir y así poder eliminarlo de la BD al final
   	        List<Event> events = sut.getEvents(eventDate);
   	        for (Event event:events){
   	        	if(event.getDescription().equals(description)) {
   	        		ev=event;
   	        	}
   	        }	        
	     	assertEquals(expected,obtained); 
   	     	
   		   } catch (Exception e) {
   		   		fail();
   		   } finally {
   			   sut.gertaeraEzabatu(ev);
   		   }

	}
	
	//El evento que se quiere añadir ya está en la base de datos
	@Test
	public void test3() {
		try { 
   	        boolean expected=false;
   	         
   	        testDA.open();
			ev2 = testDA.addEventWithQuestion(description,eventDate,"", 0);
			testDA.close();	
			
   	     	boolean obtained = sut.gertaerakSortu(description, eventDate, sport);
   	     	//Para conseguir el evento que queremos añadir y así poder eliminarlo de la BD al final
   	     	List<Event> events = sut.getEvents(eventDate);
	        for (Event event:events){
	        	if(event.getDescription().equals(description)) {
	        		ev=event;
	        	}
	        }	
	     	assertEquals(expected,obtained); 
	        
   		   } catch (Exception e) {
   		   		fail();
   		   } finally {
				  //Remove the created objects in the database (cascade removing) 
   			   	sut.gertaeraEzabatu(ev);
				testDA.open();
		        boolean b=testDA.removeEvent(ev);
		        testDA.close();
		        System.out.println("Finally "+b);          
		   }
		   
	}

	//El evento no está en la BD y hay 2 eventos en la BD
	@Test
	public void test4() {
		try {
   			
   	        boolean expected=true;
   	           
   	        testDA.open();
			ev2 = testDA.addEventWithQuestion(description2,eventDate,"", 0);
			ev3 = testDA.addEventWithQuestion(description3,eventDate,"", 0);
			testDA.close();	
			
   	     	boolean obtained = sut.gertaerakSortu(description, eventDate, sport);
   	     	List<Event> events = sut.getEvents(eventDate);
   	     	for (Event event:events){
   	     		if(event.getDescription().equals(description)) {
   	     			ev=event;
   	     		}
   	     	}	
	     	assertEquals(expected,obtained); 
	        
   		   } catch (Exception e) {
   		   		fail();
   		   } finally {
				  //Remove the created objects in the database (cascade removing) 
   			   sut.gertaeraEzabatu(ev);
				testDA.open();
		        testDA.removeEvent(ev2);
		        testDA.removeEvent(ev3);
		        testDA.close();          
		   }
	}
	
	//El evento no está en la BD y hay más de 2 eventos en la BD
	@Test
	public void test5() {
		try {
			boolean expected=true;
   	        testDA.open();
			ev2 = testDA.addEventWithQuestion(description2,eventDate,"", 0);
			ev3 = testDA.addEventWithQuestion(description3,eventDate,"", 0);
			ev4 = testDA.addEventWithQuestion(description4,eventDate,"", 0);
			testDA.close();	
			
   	     	boolean obtained = sut.gertaerakSortu(description, eventDate, sport);
   	     	List<Event> events = sut.getEvents(eventDate);
	     	for (Event event:events){
	     		if(event.getDescription().equals(description)) {
	     			ev=event;
	     		}
	     	}
	     	assertEquals(expected,obtained); 
	        
   		   } catch (Exception e) {
   		   		fail();
   		   } finally {
				  //Remove the created objects in the database (cascade removing)   
   			   sut.gertaeraEzabatu(ev);
   			   testDA.open();
		       testDA.removeEvent(ev2);
		       testDA.removeEvent(ev3);
		       testDA.removeEvent(ev4);
		        testDA.close();         
		   }
	}
}

	


