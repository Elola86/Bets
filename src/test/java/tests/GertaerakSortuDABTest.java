package tests;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import domain.Event;
import test.dataAccess.TestDataAccess;

public class GertaerakSortuDABTest {
	
	//sut:system under test
	static DataAccess sut=new DataAccess();
				 
	//additional operations needed to execute the test 
	static TestDataAccess testDA=new TestDataAccess();

	private Event ev;
	private String description;
	private Date eventDate;
	private String sport;
		
	@Before
	public void initialize() {
		description = "Real Madrid-FC Barcelona";
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
	//Parámetros correctos y el evento que se quiere añadir ya está en la BD
	@Test
	public void test1() {
		try { 
   	        boolean expected=false;
   	         
   	        testDA.open();
			ev = testDA.addEventWithQuestion(description,eventDate,"", 0);
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
	
	//El deporte recibido no está en la BD
	@Test
	public void test2() {
		try {
	   			
			sport = "Hockey"; // Deporte que no está en la base de datos
	   	    boolean expected=false;
	   	    boolean obtained = sut.gertaerakSortu(description, eventDate, sport);
	   	     
	   	    assertEquals(expected,obtained);  			
	   		  
	   		} catch (Exception e) {
	   		   	fail();
	   		} 
	}
	
	//LEl evento descrito no está en la BD
	@Test
	public void test3() {
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
	
	//La descripción no está escrita correctamente (sin -)
	@Test
	public void test4() {
		try {
   	        description="RealMadridBarcelona";
   	        
   	        sut.gertaerakSortu(description, eventDate, sport);
   	        //Para conseguir el evento que queremos añadir y así poder eliminarlo de la BD al final
   	        List<Event> events = sut.getEvents(eventDate);
   	        for (Event event:events){
   	        	if(event.getDescription().equals(description)) {
   	        		ev=event;
   	        	}
   	        }	        
	     	fail();
   	     	
   		   } catch (Exception e) {
   		   		assertTrue(true);
   		   } 
	}
	
	//La descripción del evento es null
	@Test
	public void test5() {
		try { 
			description=null;
   	     	sut.gertaerakSortu(description, eventDate, sport);
   	     	//Para conseguir el evento que queremos añadir y así poder eliminarlo de la BD al final
   	     	List<Event> events = sut.getEvents(eventDate);
	        for (Event event:events){
	        	if(event.getDescription().equals(description)) {
	        		ev=event;
	        	}
	        }	 
	        fail();
	        
   		   } catch (Exception e) { 			   
   		   		assertTrue(true);
   		   } 
	}
	
	//La fecha es null
	@Test
	public void test6() {
		try { 
			eventDate=null;
   	     	sut.gertaerakSortu(description, eventDate, sport);
   	     	//Para conseguir el evento que queremos añadir y así poder eliminarlo de la BD al final
   	     	List<Event> events = sut.getEvents(eventDate);
	        for (Event event:events){
	        	if(event.getDescription().equals(description)) {
	        		ev=event;
	        	}
	        }	 
	        fail();
	        
   		   } catch (Exception e) { 			   
   		   		assertTrue(true);
   		   } 
	}

	//El deporte es null
	@Test
	public void test7() {
		try { 
			sport=null;
   	     	sut.gertaerakSortu(description, eventDate, sport);
   	     	//Para conseguir el evento que queremos añadir y así poder eliminarlo de la BD al final
   	     	List<Event> events = sut.getEvents(eventDate);
	        for (Event event:events){
	        	if(event.getDescription().equals(description)) {
	        		ev=event;
	        	}
	        }	 
	        fail();
	        
   		   } catch (Exception e) { 			   
   		   		assertTrue(true);
   		   } 
	}
	
	
	
	
}
