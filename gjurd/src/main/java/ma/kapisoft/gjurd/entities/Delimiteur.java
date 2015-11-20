package ma.kapisoft.gjurd.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;



@Entity
@Table(name = "delimiteur")
@PrimaryKeyJoinColumn(name = "id")
public class Delimiteur extends Etape {

	 @Basic(optional = false)
	 @Column(name = "fin")
	 private Boolean fin;
	 
	 @JoinColumn(name = "etape", referencedColumnName = "id")
	  @ManyToOne
	 private Etape etape;
	
	public Delimiteur() {
		// TODO Auto-generated constructor stub
	}

	public Boolean getFin() {
		return fin;
	}

	public void setFin(Boolean fin) {
		this.fin = fin;
	}
	
	

	public Etape getEtape() {
		return etape;
	}

	public void setEtape(Etape etape) {
		this.etape = etape;
	}


	
	
	@Override
	public boolean valide() {
		// TODO Auto-generated method stub
		if(!fin && (etape==null  ||this.equals(etape)))
			return false;
		else
			return true;
	}

}
