package businessLogic;

import java.util.*;

import domain.Event;

public class ExtendedIteratorEvents implements ExtendedIterator<Event>{

	List<Event> eventos; 
	int posicion = 0;
	
	public ExtendedIteratorEvents (List<Event> eventos)
	{
		this.eventos = eventos;
		System.out.println(eventos.size() + "skndblashbvdkjasvgdkhagsfvdjhagsvdjhasg");
	}
	
	public boolean hasNext() {
		
		return posicion < eventos.size();
	}

	public Event next() {
		
		Event evento = eventos.get(posicion);
		posicion = posicion + 1;
		return evento;
	}

	public Event previous() {
		Event terminado = null;
		if(hasPrevious())
		{
			terminado = eventos.get(posicion);
			posicion = posicion-1;
		}
		return terminado;
	}

	public boolean hasPrevious() {
		if(posicion>0) {
			return true;
		}
		return false;
	}

	public void goFirst() {
		
		if(eventos.size()>0)
			posicion=0;
	}

	public void goLast() {
		if(eventos.size()>0)
			posicion=eventos.size()-1;
		
	}

}
