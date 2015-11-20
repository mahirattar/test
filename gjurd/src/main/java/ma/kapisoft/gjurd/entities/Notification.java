package ma.kapisoft.gjurd.entities;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class Notification{
	
	
	private String type;
	private String message;
	private String lien;
	private Object[] paremetres;

	public Notification() {
		// TODO Auto-generated constructor stub
	}
	
	

	public Notification(String type, String message, String lien) {
		super();
		this.type = type;
		this.message = message;
		this.lien = lien;
	}



	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLien() {
		return lien;
	}

	public void setLien(String lien) {
		this.lien = lien;
	}

	

	public Object[] getParemetres() {
		return paremetres;
	}



	public void setParemetres(Object[] paremetres) {
		this.paremetres = paremetres;
	}


	public String view(String local)
	{
		Locale loc=new Locale(local);

		String msgnotif= ResourceBundle.getBundle("/Bundle",loc).getString(message);
		if(paremetres!=null)
		{
			int i=1;
			for(Object obj:paremetres)
			{
				msgnotif=msgnotif.replaceAll("##"+i+"##", obj.toString());
			}
		}
		
		return type+":"+msgnotif;
	}
	
	

}
