package ma.kapisoft.gjurd.entities;

import javax.persistence.Basic;
import javax.persistence.Column;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "etape")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Etape {
	
	private static final long serialVersionUID = 1L;
	
	 @Id
	@GeneratedValue(strategy = IDENTITY)
	   private Integer id; 
	
	 
	 @Basic(optional = false)
	 @Column(name = "x")
	 private int x;
	
	 @Basic(optional = false)
	 @Column(name = "y")
	 private int y;
	 
	 @ManyToOne(fetch = FetchType.EAGER)
	 @JoinColumn(name = "workflow", nullable = false)
	 Workflow workflow;

	public Etape() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}
	
	public abstract boolean valide();
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + x;
		result = prime * result + y;
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
		Etape other = (Etape) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	

}
