package com.khalid.generateur;

public class Generateur {

	public Generateur() {
		// TODO Auto-generated constructor stub
		
		GenerateurClasse g=new GenerateurClasse("Cabinet");
		g.generer();
		
g=new GenerateurClasse("Departement");
		g.generer();
		g=new GenerateurClasse("Workflow");
		g.generer();
		g=new GenerateurClasse("Etape");
		g.generer();
		
		g=new GenerateurClasse("Action");
		g.generer();
		
		g=new GenerateurClasse("Condition");
		g.generer();
		
		g=new GenerateurClasse("Delimiteur");
		g.generer();
		
	}
	public static void main(String[] args) {
		new Generateur();
	}
	public static String capitalise(String ch)
	{
	return	ch.substring(0, 1).toUpperCase() + ch.substring(1);
	}
	public static String notcapitalise(String ch)
	{
	return	ch.substring(0, 1).toLowerCase() + ch.substring(1);
	}
	
	
	public static final String PACKAGE_ENTITES="ma.kapisoft.gjurd.entities";
	public static final String PACKAGE_DAO="ma.kapisoft.gjurd.dao";
	public static final String PACKAGE_SERVICES="ma.kapisoft.gjurd.service";
	public static final String PACKAGE_CONVERTER="ma.kapisoft.gjurd.converters";
	public static final String PACKAGE_CONTROLLER="ma.kapisoft.gjurd.controller";
	public static final String PACKAGE_EXCEL="ma.kapisoft.gjurd.excel";
	public static final String PACKAGE_UTIL="ma.kapisoft.gjurd.util";
	

}
