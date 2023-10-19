package test.dataAccess;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import configuration.ConfigXML;
import domain.ApustuAnitza;
import domain.Apustua;
import domain.Event;
import domain.Question;
import domain.Quote;
import domain.Registered;

public class TestDataAccess {
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;

	ConfigXML  c=ConfigXML.getInstance();


	public TestDataAccess()  {
		
		System.out.println("Creating TestDataAccess instance");

		open();
		
	}
 
	
	public void open(){
		
		System.out.println("Opening TestDataAccess instance ");

		String fileName=c.getDbFilename();
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		
	}
	public void close(){
		db.close();
		System.out.println("DataBase closed");
	}

	public boolean removeEvent(Event ev) {
		System.out.println(">> DataAccessTest: removeEvent");
		Event e = db.find(Event.class, ev.getEventNumber());
		if (e!=null) {
			db.getTransaction().begin();
			db.remove(e);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
		
		public Event addEventWithQuestion(String desc, Date d, String question, float qty) {
			System.out.println(">> DataAccessTest: addEvent");
			Event ev=null;
				db.getTransaction().begin();
				try {
				    ev=new Event(desc,d,null, null);
				    ev.addQuestion(question, qty);
					db.persist(ev);
					db.getTransaction().commit();
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return ev;
	    }
		public boolean existQuestion(Event ev,Question q) {
			System.out.println(">> DataAccessTest: existQuestion");
			Event e = db.find(Event.class, ev.getEventNumber());
			if (e!=null) {
				return e.DoesQuestionExists(q.getQuestion());
			} else 
			return false;
			
		}
		
		//para el test emaitzak ipini
		public 	Quote addEventWithQuestionQuote(String desc, Date d, String question, float qty) {
			System.out.println(">> DataAccessTest: addEvent");
			Event ev=null;
			Quote finale = null;
				db.getTransaction().begin();
				try {
				    ev=new Event(desc,d,null, null);
				    Question pregunta= ev.addQuestion(question, qty);
				    finale = pregunta.addQuote(null, question, pregunta);
					db.persist(ev);
					db.getTransaction().commit();
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return finale;
	    }


		public boolean removeEventwithQuote(Quote quo) {
			Question a = quo.getQuestion();
			Event b = a.getEvent();
			Event e = db.find(Event.class, b.getEventNumber());
			if (e!=null) {
				db.getTransaction().begin();
				db.remove(e);
				db.getTransaction().commit();
				return true;
			} else 
			return false;
			
		}
	    
		public Quote addQuote( String question) {
			System.out.println(">> DataAccessTest: addEvent");
			
			Quote finale = null;
				db.getTransaction().begin();
				try {
				    finale = new Quote(null, question, null);
					db.persist(finale);
					db.getTransaction().commit();
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return finale;
	    }


		public boolean removeQuote(Quote quo) {
			Quote e = db.find(Quote.class, quo.getQuoteNumber());
			if (e!=null) {
				db.getTransaction().begin();
				db.remove(e);
				db.getTransaction().commit();
				return true;
			} else 
			return false;
		}


		public Quote addQuoteandApustuAnitz(String queryText) {
			System.out.println(">> DataAccessTest: addEvent");
			
				Quote finale = null;
				
				
				db.getTransaction().begin();
				try {
				    finale = new Quote(null, queryText, null);
				    ApustuAnitza apusaniz = new ApustuAnitza(null, (double) 2);
					
				    Apustua berria = new Apustua(apusaniz, finale);
					
					finale.addApustua(berria);
					db.persist(apusaniz);
					db.persist(finale);
					db.getTransaction().commit();
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return finale;
		}


		public Quote addQuoteandApustu(String queryText) {
			System.out.println(">> DataAccessTest: addEvent");
			
			Quote finale = null;
			
			
			db.getTransaction().begin();
			try {
			    finale = new Quote(null, queryText, null);
			    
				
			    Apustua berria = new Apustua(null, finale);
				
				finale.addApustua(berria);
				
				db.persist(finale);
				db.getTransaction().commit();
			}
			catch (Exception e){
				e.printStackTrace();
			}
			return finale;
		}
		
		//para el test emaitzak ipini
				public 	Quote addEventWithQuestionQuoteApus(String desc, Date d, String question, float qty) {
					System.out.println(">> DataAccessTest: addEvent");
					Event ev=null;
					Quote finale = null;
						db.getTransaction().begin();
						try {
						    ev=new Event(desc,d,null, null);
						    Question pregunta= ev.addQuestion(question, qty);
						    finale = pregunta.addQuote((double) 2, question, pregunta);
						    Quote otraQuote = pregunta.addQuote((double) 3, "a", pregunta);
						    otraQuote.addApustua(new Apustua(null, otraQuote));
						    
						    
							db.persist(ev);
							db.getTransaction().commit();
						}
						catch (Exception e){
							e.printStackTrace();
						}
						return finale;
			    }
				
				public 	Quote addEventWithQuestionQuoteApusBi(String desc, Date d, String question, float qty) {
					System.out.println(">> DataAccessTest: addEvent");
					Event ev=null;
					Quote finale = null;
						db.getTransaction().begin();
						try {
						    ev=new Event(desc,d,null, null);
						    Question pregunta= ev.addQuestion(question, qty);
						    finale = pregunta.addQuote((double) 2, question, pregunta);
						    Quote otraQuote = pregunta.addQuote((double) 3, "a", pregunta);
						    Quote noExiste = pregunta.addQuote(null, question, pregunta);
						    Apustua finales = new Apustua(null, noExiste);
						    ApustuAnitza ultimo = new ApustuAnitza();
						    finales.setApustuAnitza(ultimo);   
						    otraQuote.addApustua(finales);
						    
						    db.persist(ultimo);
							db.persist(ev);
							db.getTransaction().commit();
						}
						catch (Exception e){
							e.printStackTrace();
						}
						return finale;
			    }
				
				public 	Quote ganarMasDeUna(String desc, Date d, String question, float qty) {
					System.out.println(">> DataAccessTest: addEvent");
					Event ev=null;
					Quote finale = null;
						db.getTransaction().begin();
						try {
						    ev=new Event(desc,d,null, null);
						    Question pregunta= ev.addQuestion(question, qty);
						    finale = pregunta.addQuote((double) 2, question, pregunta);
						    Quote otraQuote = pregunta.addQuote((double) 3, "a", pregunta);
						    Apustua finales = new Apustua(null, otraQuote);
						    Apustua otra = new Apustua(null, otraQuote);  
						     
						    otraQuote.addApustua(finales);
						    otraQuote.addApustua(otra);
						    
						   
							db.persist(ev);
							db.getTransaction().commit();
						}
						catch (Exception e){
							e.printStackTrace();
						}
						return finale;
			    }
				
				public Quote ganarApustuAnitzak(String desc, Date d, String question, float qty)
				{
					Event ev=null;
					Quote finale = null;
						db.getTransaction().begin();
						try {
						    ev=new Event(desc,d,null, null);
						    ApustuAnitza guztiak = new ApustuAnitza();
						    
						    Question pregunta= ev.addQuestion(question, qty);
						    finale = pregunta.addQuote((double) 3, "a", pregunta);
						    Apustua finales = new Apustua(guztiak, finale);
						    finales.setEgoera("irabazita");
						    guztiak.setEgoera("jokoan");
						    guztiak.addApustua(finales);
						    finale.addApustua(finales);
						    guztiak.setBalioa((double)2);
						    Registered registrado = new Registered("asdfadsxkfhjbflsadkjhxgcjks", "a", 1);
						    guztiak.setUser(registrado);
						     
						    //db.persist(registrado);
						    db.persist(ev);
						    db.persist(guztiak);
							db.getTransaction().commit();
						    
						}catch (Exception e){
							e.printStackTrace();
						}
					return finale;
				}
}

