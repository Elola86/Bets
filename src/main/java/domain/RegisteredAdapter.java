package domain;

import javax.swing.table.AbstractTableModel;

//UserAdapter
public class RegisteredAdapter extends AbstractTableModel{

	protected Registered user;
	protected String[] columnNames = new String [] {"Event","Question","Date","Bet(€)"};
	public RegisteredAdapter(Registered user) {
		this.user=user;
	}
	
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}
	
	public int getRowCount() {
		return user.getApustuAnitzak().size();
	}
	
	public int getColumnCount() {
		return 4;
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		Apustua bet = getBetInTable(rowIndex);
		if(columnIndex==0) {
			return bet.getKuota().getQuestion().getEvent().getDescription();
		}else if(columnIndex==1) {
			return bet.getKuota().getQuestion().getQuestion();
		}else if(columnIndex==2) {
			return bet.getApustuAnitza().getData();
			
		}else if(columnIndex==3) {
			return bet.getKuota().getQuote();
		}else {
			return null;
		}	

	}
	
	public Apustua getBetInTable(int rowIndex) {
		int totalBets = 0;
	    
	    for (ApustuAnitza apustuAnitza : user.getApustuAnitzak()) {
	        for (Apustua apustua : apustuAnitza.getApustuak()) {
	            if (totalBets == rowIndex) {
	                return apustua;
	            }
	            totalBets++;
	        }
	    }

	    return null;
	}

	
}
