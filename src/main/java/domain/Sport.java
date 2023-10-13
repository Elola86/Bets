package domain;

import java.io.Serializable;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Sport implements Serializable{
	@XmlID
	@Id 
	private String izena;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Vector<Event> events=new Vector<Event>();
	
	private Integer apustuKantitatea;
	
	public Sport() {
		super();
	}
	
	public Sport(String izena) {
		this.izena=izena;
		this.apustuKantitatea=0;
	}

	public String getIzena() {
		return izena;
	}

	public void setIzena(String izena) {
		this.izena = izena;
	}

	public Vector<Event> getEvents() {
		return events;
	}

	public void setEvents(Vector<Event> events) {
		this.events = events;
	}

	public Integer getApustuKantitatea() {
		return apustuKantitatea;
	}

	public void setApustuKantitatea(Integer apustuKantitatea) {
		this.apustuKantitatea = apustuKantitatea;
	}
	
	public void eguneratuApustuKantitatea() {
		this.apustuKantitatea=this.apustuKantitatea+1;
	}
	
	public void addEvent(Event ev) {
		this.events.add(ev);
	}
	

	@Override
	public String toString() {
		return this.izena;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apustuKantitatea == null) ? 0 : apustuKantitatea.hashCode());
		result = prime * result + ((events == null) ? 0 : events.hashCode());
		result = prime * result + ((izena == null) ? 0 : izena.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sport other = (Sport) obj;
		if (apustuKantitatea == null) {
			if (other.apustuKantitatea != null)
				return false;
		} else if (!apustuKantitatea.equals(other.apustuKantitatea))
			return false;
		if (events == null) {
			if (other.events != null)
				return false;
		} else if (!events.equals(other.events))
			return false;
		if (izena == null) {
			if (other.izena != null)
				return false;
		} else if (!izena.equals(other.izena))
			return false;
		return true;
	}
	
	
}
