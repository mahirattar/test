package ma.kapisoft.gjurd.service;

import java.io.Serializable;

import ma.kapisoft.gjurd.entities.Etape;

public class EtapeElement implements Serializable {
    
    private String name;
    private boolean haveimage;
    private String image;
    private Etape etape;

    public EtapeElement() {
    }

    public EtapeElement(String name) {
        this.name = name;
        this.haveimage=false;
    }
    
    public EtapeElement(String name, String image) {
        this.name = name;
        this.image = image;
        this.haveimage=true;
    }
    
    

   

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    

 

	public boolean isHaveimage() {
		return haveimage;
	}

	public void setHaveimage(boolean haveimage) {
		this.haveimage = haveimage;
	}
	
	
	

	public Etape getEtape() {
		return etape;
	}

	public void setEtape(Etape etape) {
		this.etape = etape;
	}

	@Override
    public String toString() {
        return name;
    }
     
}