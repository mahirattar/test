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
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((fin == null) ? 0 : fin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Delimiteur other = (Delimiteur) obj;
		if (fin == null) {
			if (other.fin != null)
				return false;
		} else if (!fin.equals(other.fin))
			return false;
		return true;
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
