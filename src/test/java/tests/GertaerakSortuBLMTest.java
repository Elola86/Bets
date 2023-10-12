package tests;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Event;
import domain.Sport;
import exceptions.EventFinished;

@RunWith(MockitoJUnitRunner.class)
public class GertaerakSortuBLMTest {

	DataAccess dataAccess=Mockito.mock(DataAccess.class);
	Event mockedEvent=Mockito.mock(Event.class);
	
	@InjectMocks
	BLFacade sut=new BLFacadeImplementation(dataAccess);
	
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
			eventDate = sdf.parse("10/12/2023");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
	    Mockito.doReturn(description).when(mockedEvent).getDescription();
		Mockito.doReturn(eventDate).when(mockedEvent).getEventDate();
		Mockito.doReturn(new Sport(sport)).when(mockedEvent).getSport();
	}
	
	//Parámetros correctos y el evento que se quiere añadir ya está en la BD
	@Test
	public void test1() {
		try {
			
			//configure Mock
			Mockito.doReturn(false).when(dataAccess).gertaerakSortu(Mockito.eq(description), Mockito.eq(eventDate),Mockito.eq(sport));			
			
			//invoke System Under Test (sut) 
			boolean obtained=sut.gertaerakSortu(description, eventDate, sport);
			
			//verify the results				
			Mockito.verify(dataAccess,Mockito.times(1)).gertaerakSortu(Mockito.eq(description), Mockito.eq(eventDate),Mockito.eq(sport));

			assertFalse(obtained);
			
		}catch(EventFinished e) {
			fail();
		}
			
	}
	
	//El deporte recibido no está en la BD
	@Test
	public void test2() {
		try {
			sport="Hockey";
			//configure Mock
			Mockito.doReturn(false).when(dataAccess).gertaerakSortu(Mockito.any(String.class), Mockito.any(Date.class),Mockito.eq(sport));			
			
			//invoke System Under Test (sut) 
			boolean obtained=sut.gertaerakSortu(description, eventDate, sport);
			
			//verify the results				
			Mockito.verify(dataAccess,Mockito.times(1)).gertaerakSortu(Mockito.any(String.class), Mockito.any(Date.class),Mockito.eq(sport));

			assertFalse(obtained);
			
		}catch(EventFinished e) {
			fail();
		}
	}

	//El evento descrito no está en la BD
	@Test
	public void test3() {
		try {
			//configure Mock
			Mockito.doReturn(true).when(dataAccess).gertaerakSortu(Mockito.eq(description), Mockito.eq(eventDate),Mockito.eq(sport));			
			
			//invoke System Under Test (sut) 
			boolean obtained=sut.gertaerakSortu(description, eventDate, sport);
			
			List<Event> events = sut.getEvents(eventDate);
	        for (Event event:events){
	        	if(event.getDescription().equals(description)) {
	        		ev=event;
	        	}
	        }	 
			//verify the results				
			ArgumentCaptor<String> descriptionCaptor = ArgumentCaptor.forClass(String.class);
			ArgumentCaptor<Date> eventDateCaptor = ArgumentCaptor.forClass(Date.class);
			ArgumentCaptor<String> sportCaptor = ArgumentCaptor.forClass(String.class);
			
			Mockito.verify(dataAccess,Mockito.times(1)).gertaerakSortu(descriptionCaptor.capture(),eventDateCaptor.capture(), sportCaptor.capture());
			
			assertTrue(obtained);
			assertEquals(descriptionCaptor.getValue(),mockedEvent.getDescription());
			assertEquals(eventDateCaptor.getValue(),mockedEvent.getEventDate());
			assertEquals(sportCaptor.getValue(),mockedEvent.getSport().getIzena());
			
		}catch(EventFinished e) {
			fail();
		} finally {
			sut.gertaeraEzabatu(ev);
		}
	}
	
	//La descripción no está escrita correctamente (sin -)
	@Test
	public void test4() {
		try {
			 description="Real MadridFC Barcelona";
			 Mockito.doThrow(new Exception("La descripción está escrita de forma incorrecta")).when(dataAccess).gertaerakSortu(description, eventDate, sport);
			 
			 sut.gertaerakSortu(description, eventDate, sport);
			 fail();
		} catch(Exception e) {
			assertTrue(true);
		}
	}
	
	//La descripción del evento es null
	@Test
	public void test5() {
		try {
			 description=null;
			 Mockito.doThrow(new Exception("La descripción es null")).when(dataAccess).gertaerakSortu(description, eventDate, sport);
			 
			 sut.gertaerakSortu(description, eventDate, sport);
			 fail();
		} catch(Exception e) {
			assertTrue(true);
		}
	}
	
	//La fecha es null
	@Test
	public void test6() {
		try {
			 eventDate=null;
			 Mockito.doThrow(new Exception("La fecha es null")).when(dataAccess).gertaerakSortu(description, eventDate, sport);
			 
			 boolean obtained=sut.gertaerakSortu(description, eventDate, sport);
			 fail();
		} catch(Exception e) {
			assertTrue(true);
		}
	}
	
	//El deporte es null
	@Test
	public void test7() {
		try {
			 sport=null;
			 Mockito.doThrow(new Exception("El deporte es null")).when(dataAccess).gertaerakSortu(description, eventDate, sport);
			 
			 sut.gertaerakSortu(description, eventDate, sport);
			 fail();
		} catch(Exception e) {
			assertTrue(true);
		}
	}
}
