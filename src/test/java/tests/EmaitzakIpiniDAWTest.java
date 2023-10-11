package tests;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.Event;
import domain.Question;
import domain.Quote;
import exceptions.EventNotFinished;
import test.dataAccess.TestDataAccess;

//test oier
public class EmaitzakIpiniDAWTest {
	//sut:system under test
		 static DataAccess sut=new DataAccess();
		 
		 //additional operations needed to execute the test 
		 static TestDataAccess testDA=new TestDataAccess();
		 
		
		 private Quote quo;
		 
		 @Test
		 public void test1() {
		 
			 try {
					
					//define paramaters
					String eventText="event1";
					String queryText="Almeria";
					Float betMinimum=new Float(2);
					
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					Date oneDate=null;;
					try {
						oneDate = sdf.parse("05/10/2024");
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					
					//configure the state of the system (create object in the dabatase)
					testDA.open();
					quo = testDA.addEventWithQuestionQuote(eventText, oneDate,queryText, betMinimum);
					testDA.close();
					
					
					
					//invoke System Under Test (sut)  
					sut.emaitzakIpini(quo);
					
					
					//if the program continues fail
				    fail();
			 }catch(EventNotFinished e)
			 {
				 assertTrue(true);
			 }
			 finally {
				//Remove the created objects in the database (cascade removing)   
					testDA.open();
			         boolean b=testDA.removeEventwithQuote(quo);
			          testDA.close();
			         System.out.println("Finally "+b); 
			 }
		 }
		 
		 @Test
		 public void test2() {
			 String queryText="Athletic";
			 testDA.open();
				quo = testDA.addQuote(queryText);
				testDA.close();
				
				try {
					
					sut.emaitzakIpini(quo);
					assertTrue(true);
				}
				catch(Exception e)
				{
					fail();
				}
				finally
				{
					testDA.open();
			         boolean b=testDA.removeQuote(quo);
			          testDA.close();
			         System.out.println("Finally "+b);
				}
		 }
		 
		 @Test
		 public void test3() {
			 String queryText="Real Sociead";
			
			 
			 testDA.open();
				quo = testDA.addQuoteandApustuAnitz(queryText);
				testDA.close();
				
				try {
					
					sut.emaitzakIpini(quo);
					assertTrue(true);
				}
				catch(Exception e)
				{
					fail();
				}
				finally
				{
					testDA.open();
			         boolean b=testDA.removeQuote(quo);
			          testDA.close();
			         System.out.println("Finally "+b);
				}
		 }
		 
		 @Test
		 public void test4() {
			 String queryText="Real Sociead";
			
			 
			 testDA.open();
				quo = testDA.addQuoteandApustu(queryText);
				testDA.close();
				
				try {
					
					sut.emaitzakIpini(quo);
					assertTrue(true);
				}
				catch(Exception e)
				{
					fail();
				}
				finally
				{
					testDA.open();
			         boolean b=testDA.removeQuote(quo);
			          testDA.close();
			         System.out.println("Finally "+b);
				}
		 }
		 
		 @Test
		 public void test5() {
			 try {
					
					//define paramaters
					String eventText="event1";
					String queryText="Almeria";
					Float betMinimum=new Float(2);
					
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					Date oneDate=null;;
					try {
						oneDate = sdf.parse("05/10/2022");
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					
					//configure the state of the system (create object in the dabatase)
					testDA.open();
					quo = testDA.addEventWithQuestionQuote(eventText, oneDate,queryText, betMinimum);
					testDA.close();
					
					
					
					//invoke System Under Test (sut)  
					sut.emaitzakIpini(quo);
					
					
					//if the program continues fail
					 assertTrue(true);
			 }catch(Exception e)
			 {
				fail();
			 }
			 finally {
				//Remove the created objects in the database (cascade removing)   
					testDA.open();
			         boolean b=testDA.removeEventwithQuote(quo);
			          testDA.close();
			         System.out.println("Finally "+b); 
			 }
		 }
		 
		 @Test
		 public void test6() {
			 try {
					
					//define paramaters
					String eventText="event1";
					String queryText="Almeria";
					Float betMinimum=new Float(2);
					
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					Date oneDate=null;;
					try {
						oneDate = sdf.parse("05/10/2022");
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					testDA.open();
					quo = testDA.addEventWithQuestionQuoteApus(eventText, oneDate,queryText, betMinimum);
					testDA.close();
					
					sut.emaitzakIpini(quo);
					assertTrue(true);
			 }
			 catch(Exception e) {
				 e.printStackTrace();
			 }
			 finally {
					//Remove the created objects in the database (cascade removing)   
						testDA.open();
				         boolean b=testDA.removeEventwithQuote(quo);
				          testDA.close();
				         System.out.println("Finally "+b); 
				 }
		 }
		 
		 @Test
		 public void test7() {
			 try {
					
					//define paramaters
					String eventText="event1";
					String queryText="Almeria";
					Float betMinimum=new Float(2);
					
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					Date oneDate=null;;
					try {
						oneDate = sdf.parse("05/10/2022");
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					testDA.open();
					quo = testDA.addEventWithQuestionQuoteApusBi(eventText, oneDate,queryText, betMinimum);
					testDA.close();
					
					sut.emaitzakIpini(quo);
					assertTrue(true);
			 }
			 catch(Exception e) {
				 e.printStackTrace();
			 }
			 finally {
					//Remove the created objects in the database (cascade removing)   
						testDA.open();
				         boolean b=testDA.removeEventwithQuote(quo);
				          testDA.close();
				         System.out.println("Finally "+b); 
				 }
		 }
}
