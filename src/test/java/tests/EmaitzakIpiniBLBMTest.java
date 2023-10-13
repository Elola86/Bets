package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.ApustuAnitza;
import domain.Apustua;
import domain.Event;
import domain.Question;
import domain.Quote;
import domain.Registered;
import exceptions.EventNotFinished;

@RunWith(MockitoJUnitRunner.class)
public class EmaitzakIpiniBLBMTest {

	DataAccess dataAccess=Mockito.mock(DataAccess.class);
	@InjectMocks
	BLFacade sut=new BLFacadeImplementation(dataAccess);
	Quote mockedQuote= Mockito.mock(Quote.class);
	Question mockedQuestion = Mockito.mock(Question.class);
	Event mockedEvent = Mockito.mock(Event.class);
	
	String eventText;
	String queryText;
	Float betMinimum;
	SimpleDateFormat sdf;
	Date oneDate;
	
	@Before
	public void initialize() {
		eventText="event1";
		queryText="Almeria";
		betMinimum=new Float(2);
		
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		oneDate=null;;
		try {
			oneDate = sdf.parse("05/10/2022");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	 @Test
	 public void Test1() throws EventNotFinished
	 {
		 try {
		 Event  ev=new Event(eventText,this.oneDate,null, null);
		 Question pregunta= ev.addQuestion(this.queryText, this.betMinimum);
		 Quote finale = pregunta.addQuote((double) 2, this.queryText, pregunta);
		 Quote otraQuote = pregunta.addQuote((double) 3, "a", pregunta);
		 Quote noExiste = pregunta.addQuote(null, this.queryText, pregunta);
		 Apustua finales = new Apustua(null, noExiste);
		 ApustuAnitza ultimo = new ApustuAnitza();
		 finales.setApustuAnitza(ultimo);   
		 otraQuote.addApustua(finales);
		 
		Mockito.doNothing().when(dataAccess).emaitzakIpini(Mockito.eq(finale));
		 
		 
			sut.emaitzakIpini(finale);
			assertTrue(true);
		} catch (EventNotFinished e) {
			fail();
		}
		 
	 }
	 
	 @Test
	 public void Test2()
	 {
		 try {
		 	Event ev=new Event(eventText,oneDate,null, null);
		    Question pregunta= ev.addQuestion(queryText, betMinimum);
		    Quote finale = pregunta.addQuote((double) 2, queryText, pregunta);
		    Quote otraQuote = pregunta.addQuote((double) 3, "a", pregunta);
		    Apustua finales = new Apustua(null, otraQuote);
		    Apustua otra = new Apustua(null, otraQuote);  
		     
		    otraQuote.addApustua(finales);
		    otraQuote.addApustua(otra);
		    
		    Mockito.doNothing().when(dataAccess).emaitzakIpini(Mockito.eq(finale));

		    
				sut.emaitzakIpini(finale);
				assertTrue(true);
			} catch (EventNotFinished e) {
				// TODO Auto-generated catch block
				 fail();
			}
	 }
	 
	 @Test
	 public void Test3()
	 {	
		 try {
		 Event ev=new Event(eventText,oneDate,null, null);
		    ApustuAnitza guztiak = new ApustuAnitza();
		    
		    Question pregunta= ev.addQuestion(queryText, betMinimum);
		    Quote finale = pregunta.addQuote((double) 3, "a", pregunta);
		    Apustua finales = new Apustua(guztiak, finale);
		    finales.setEgoera("irabazita");
		    guztiak.setEgoera("jokoan");
		    guztiak.addApustua(finales);
		    finale.addApustua(finales);
		    guztiak.setBalioa((double)2);
		    Registered registrado = new Registered("asdfadsxkfhjbflsadkjhxgcjks", "a", 1);
		    guztiak.setUser(registrado);
		    
		    Mockito.doNothing().when(dataAccess).emaitzakIpini(Mockito.eq(finale));
		    sut.emaitzakIpini(finale);
		    assertTrue(true);
		 }
		 catch(Exception e)
		 {
			 fail();
		 }
	 }
	 
	 @Test
	 public void Test4()
	 {
		 try {
		 
			Event ev=new Event(eventText,oneDate,null, null);
			Question pregunta= ev.addQuestion(queryText, betMinimum);
			Quote finale = pregunta.addQuote(null, queryText, pregunta);
			
			 Mockito.doNothing().when(dataAccess).emaitzakIpini(Mockito.eq(finale));
			 sut.emaitzakIpini(finale);
			 assertTrue(true);
		}
		 catch(Exception e)
		 {
			 fail();
		 }
		 
	 }
	 
	 @Test
	 public void Test5()
	 {
		
			 try {
					oneDate = sdf.parse("05/10/2024");
			} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}	
			
			 
					
			Event ev=new Event(eventText,oneDate,null, null);
			Question pregunta= ev.addQuestion(queryText, betMinimum);
			Quote finale = pregunta.addQuote(null, queryText, pregunta);
			
			
			try { 
			
			Mockito.doThrow(new EventNotFinished()).when(dataAccess).emaitzakIpini(finale);
				
				
			sut.emaitzakIpini(finale);
				fail();
		}catch(EventNotFinished e)
		{
			
			assertTrue(true);
		}
	 }
	 
	 @Test
	 public void Test6()
	 {
		 try {
			 
				//define paramaters
				Quote quo = new Quote();
	
				Mockito.doThrow(new Exception()).when(dataAccess).emaitzakIpini(quo);
				
				//invoke System Under Test (sut)  
				sut.emaitzakIpini(quo);
				
				
				//if the program continues fail
			    fail();
		 }catch(Exception e)
		 {
			 assertTrue(true);
		 }
	 }
	 
	 @Test
	 public void Test7()
	 {
		 try {
				
			 	Mockito.doThrow(new Exception("quote es null")).when(dataAccess).emaitzakIpini(null);
			 	Quote b = null;
				//invoke System Under Test (sut)  
				sut.emaitzakIpini(b);
				
				
				//if the program continues fail
			    fail();
		 }catch(Exception e)
		 {
			 assertTrue(true);
		 }
	 }
	 
	 @Test
	 public void Test8()
	 {
		 try {
	 
		 Quote finale = new Quote(null, queryText, null);
		 
		 Mockito.doThrow(new Exception("no hay question")).when(dataAccess).emaitzakIpini(Mockito.eq(finale));
		 
		 sut.emaitzakIpini(finale);
		 
		 fail();
		 }
		 catch(Exception e)
		 {
			 assertTrue(true);
		 }
	 }
	 
	 
}
