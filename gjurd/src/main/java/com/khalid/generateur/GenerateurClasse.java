package com.khalid.generateur;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



public class GenerateurClasse {

	private String classe;
	private String  dossier;
	private String controleur;
	private String  service;
	private String  beanservice;
	private String  beancontroleur;
	private String  converter;
	private String dao;
	private List<String> msgsfr=new ArrayList<String>();
	private List<String> msgsen=new ArrayList<String>();
	

	public GenerateurClasse(String classe, String dossier, String controleur,
			String service, String dao) {
		super();
		this.classe = classe;
		this.dossier = dossier;
		this.controleur = controleur;
		this.service = service;
		this.dao = dao;
		this.converter=classe+"Converter";
		this.beanservice=Generateur.notcapitalise(service);
		this.beancontroleur=Generateur.notcapitalise(controleur);
	}

	public GenerateurClasse(String classe, String dossier, String controleur,
			String service) {
		super();
		this.classe = classe;
		this.dossier = dossier;
		this.controleur = controleur;
		this.service = service;
		this.dao = classe+"Dao";
		this.converter=classe+"Converter";
		this.beanservice=Generateur.notcapitalise(service);
		this.beancontroleur=Generateur.notcapitalise(controleur);

	}

	public GenerateurClasse(String classe, String dossier, String controleur) {
		super();
		this.classe = classe;
		this.dossier = dossier;
		this.controleur = controleur;
		this.service = classe+"Service";
		this.dao = classe+"Dao";
		this.converter=classe+"Converter";
		this.beanservice=Generateur.notcapitalise(service);
		this.beancontroleur=Generateur.notcapitalise(controleur);
	}
	public GenerateurClasse(String classe,String dossier) {
		// TODO Auto-generated constructor stub
		this.classe = classe;
		this.dossier = dossier;
		this.controleur = classe+"Controller";
		this.service = classe+"Service";
		this.dao = classe+"Dao";
		this.converter=classe+"Converter";
		this.beanservice=Generateur.notcapitalise(service);
		this.beancontroleur=Generateur.notcapitalise(controleur);
	}

	public GenerateurClasse(String classe) {
		super();
		this.classe = classe;
		this.dossier = classe.toLowerCase();
		this.controleur = classe+"Controller";
		this.service = classe+"Service";
		this.dao = classe+"Dao";
		this.converter=classe+"Converter";
		this.beanservice=Generateur.notcapitalise(service);
		this.beancontroleur=Generateur.notcapitalise(controleur);
	}
	
	private String uniquesAttribute(String cls)
	{
		//System.out.println(classe);
		Class cl;
		try {
			cl = Class.forName(Generateur.PACKAGE_ENTITES+"."+cls);
			Object ob=cl.newInstance();
			//Class cl=	Class.forName(classe);
			Class cl2=ob.getClass();
			java.lang.annotation.Annotation ann= cl2.getAnnotation(javax.persistence.Table.class);
			if(ann==null)
				return null;
			//for(java.lang.annotation.Annotation ann:cl2.getAnnotations())
			//{
		 //  System.out.println(ann.annotationType());
		 //  System.out.println(ann);
		   
		   String ch=ann.toString().substring(ann.toString().indexOf("columnNames=[")+13);   
		 //  System.out.println(ch);
		  try{
		   ch=ch.substring(0,ch.indexOf("])]"));
		  }catch(Exception e)
		  {
			  return null;
		  }
		 //  System.out.println(ch);
		   return ch; 
			//}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		}
		
		return null;
		
	}
	
	
	
	private Field[]  getAttributs()
	{
		try {
		Class cl=	Class.forName(Generateur.PACKAGE_ENTITES+"."+classe);
		Class cl2=null;
		try{
		Object ob=cl.newInstance();
		cl2=ob.getClass();
		}catch(Exception e)
		{
		cl2=cl;	
		}
		//Class cl=	Class.forName(classe);
		
		System.out.println(Generateur.PACKAGE_ENTITES+"."+classe);
		
		return cl2.getDeclaredFields();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		}
		
		return null;
		
	}
	
	public void genererDAO()
	{
		System.out.println("genererDAO");
		String contenu=lireTemplate("Dao.java");
		contenu=contenu.replaceAll("##ENTITY_PACKAGE##", Generateur.PACKAGE_ENTITES+"."+classe)
				        .replaceAll("##PACKAGE##", Generateur.PACKAGE_DAO)
				        .replaceAll("##ENTITY##",classe)
				        .replaceAll("##DAO##", dao);
		
		
		String unique=uniquesAttribute(classe);;
		String findbyattribute="";
		if(unique!=null)
		{
	String	findbyattributeUnique=lireTemplate("findByAttributeUnique.java");
		findbyattribute=findbyattributeUnique.replaceAll("##ENTITY##",classe)
										.replaceAll("##ATTRIBUTE##",unique)
										.replaceAll("##CAPITALATTRIBUTE##",Generateur.capitalise(unique));
		}
		
		
		String template=lireTemplate("findByAttribute.java");
		
	
		System.out.println(classe);
		for(Field f:getAttributs())
		{
			String attribut=f.getName();
		    if( !attribut.equals("serialVersionUID") && !attribut.equals("id") &&!attribut.equals(unique))
		    {
		    	findbyattribute+="\n\n"+template.replaceAll("##ENTITY##",classe)
									    .replaceAll("##ATTRIBUTE##",attribut)
									    .replaceAll("##CAPITALATTRIBUTE##",Generateur.capitalise(attribut));
		    }
		    }
		
		
		//System.out.println(findbyattribute);
		contenu=contenu.replaceAll("##FINDBYATTRIBUTE##", findbyattribute);
		try {
			FileWriter fw = new FileWriter(System.getProperty("user.dir")+"/src/main/java/"+Generateur.PACKAGE_DAO.replace(".", "/")+"/"+this.dao+".java");
				fw.write(contenu);
			//On ferme le flux
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void genererService()
	{
		System.out.println("genererSERVICE");
		String contenu=lireTemplate("Service.java");
		contenu=contenu.replaceAll("##ENTITY_PACKAGE##", Generateur.PACKAGE_ENTITES+"."+classe)
				        .replaceAll("##DAO_PACKAGE##", Generateur.PACKAGE_DAO+"."+dao)
				        .replaceAll("##PACKAGE##", Generateur.PACKAGE_SERVICES)
				        .replaceAll("##ENTITY##",classe)
				        .replaceAll("##SERVICE##",service)
				        .replaceAll("##DAO##", dao);
		
		try {
			FileWriter fw = new FileWriter(System.getProperty("user.dir")+"/src/main/java/"+Generateur.PACKAGE_SERVICES.replace(".", "/")+"/"+this.service+".java");
				fw.write(contenu);
			//On ferme le flux
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void genererIService()
	{
		System.out.println("genererISERVICE");
		String contenu=lireTemplate("IService.java");
		contenu=contenu.replaceAll("##ENTITY_PACKAGE##", Generateur.PACKAGE_ENTITES+"."+classe)
				        .replaceAll("##DAO_PACKAGE##", Generateur.PACKAGE_DAO+"."+dao)
				        .replaceAll("##PACKAGE##", Generateur.PACKAGE_SERVICES)
				        .replaceAll("##ENTITY##",classe)
				        .replaceAll("##SERVICE##",service)
				        .replaceAll("##DAO##", dao);
		
		try {
			FileWriter fw = new FileWriter(System.getProperty("user.dir")+"/src/main/java/"+Generateur.PACKAGE_SERVICES.replace(".", "/")+"/I"+this.service+".java");
				fw.write(contenu);
			//On ferme le flux
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	public void genererConverter()
	{
		System.out.println("genererConverter");
		String contenu=lireTemplate("Converter.java");
		contenu=contenu.replaceAll("##ENTITY_PACKAGE##", Generateur.PACKAGE_ENTITES+"."+classe)
				        .replaceAll("##DAO_PACKAGE##", Generateur.PACKAGE_DAO+"."+dao)
				        .replaceAll("##SERVICE_PACKAGE##", Generateur.PACKAGE_SERVICES+"."+service)
				        .replaceAll("##ISERVICE_PACKAGE##", Generateur.PACKAGE_SERVICES+".I"+service)
				       
				        .replaceAll("##UTILPACKAGE##", Generateur.PACKAGE_UTIL)
				        .replaceAll("##PACKAGE##", Generateur.PACKAGE_CONVERTER)
				        .replaceAll("##ENTITY##",classe)
				        .replaceAll("##SERVICE##",service)
				        .replaceAll("##CONVERTER##",converter)
				        .replaceAll("##BEANSERVICE##",beanservice)
				        .replaceAll("##DAO##", dao);
		
		
		try {
			FileWriter fw = new FileWriter(System.getProperty("user.dir")+"/src/main/java/"+Generateur.PACKAGE_CONVERTER.replace(".", "/")+"/"+this.converter+".java");
				fw.write(contenu);
			//On ferme le flux
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void genererController()
	{
		System.out.println("genererController");
		String contenu=lireTemplate("Controller.java");
		contenu=contenu.replaceAll("##ENTITY_PACKAGE##", Generateur.PACKAGE_ENTITES+"."+classe)
				        .replaceAll("##DAO_PACKAGE##", Generateur.PACKAGE_DAO+"."+dao)
				        .replaceAll("##SERVICE_PACKAGE##", Generateur.PACKAGE_SERVICES+"."+service)
				        .replaceAll("##ISERVICE_PACKAGE##", Generateur.PACKAGE_SERVICES+".I"+service)
				       
				        .replaceAll("##PACKAGE##", Generateur.PACKAGE_CONTROLLER)
				        .replaceAll("##ENTITY##",classe)
				        .replaceAll("##SERVICE##",service)
				        .replaceAll("##CONTROLLER##",controleur)
				        .replaceAll("##BEANSERVICE##",beanservice)
				        .replaceAll("##DAO##", dao);
		
		try {
			FileWriter fw = new FileWriter(System.getProperty("user.dir")+"/src/main/java/"+Generateur.PACKAGE_CONTROLLER.replace(".", "/")+"/"+this.controleur+".java");
				fw.write(contenu);
			//On ferme le flux
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void genererManipExcel()
	{
		System.out.println("genererManipExcel");
		String contenu=lireTemplate("Excel.java");
		contenu=contenu.replaceAll("##ENTITY_PACKAGE##", Generateur.PACKAGE_ENTITES+"."+classe)
				        .replaceAll("##DAO_PACKAGE##", Generateur.PACKAGE_DAO+"."+dao)
				        .replaceAll("##SERVICE_PACKAGE##", Generateur.PACKAGE_SERVICES+"."+service)
				        .replaceAll("##ISERVICE_PACKAGE##", Generateur.PACKAGE_SERVICES+".I"+service)
				       
				        .replaceAll("##PACKAGE##", Generateur.PACKAGE_CONTROLLER)
				        .replaceAll("##ENTITY##",classe)
				        .replaceAll("##SERVICE##",service)
				        .replaceAll("##CONTROLLER##",controleur)
				        .replaceAll("##BEANSERVICE##",beanservice)
				        .replaceAll("##DAO##", dao);
		
		String uniqueattribute=uniquesAttribute(classe);
		try{
			contenu=contenu.replaceAll("##ATTRIBUTE_UNIQUE##",Generateur.capitalise(uniqueattribute));
		}catch(NullPointerException e)
		{
			contenu=contenu.replaceAll("##ATTRIBUTE_UNIQUE##","");
		}
		
		String attributes_constantes_name="\n";
		int i=0;
		String attr_unique_num="0";
		String if_attribute="\n";
		String read_attributes="\n";
		String GETTERS_DAO="\n";
		String ATTRIBUTES_DAO="\n";
		String IMPORT_DAO="\n";
		String IMPORT_ENTITE="\n";
		for(Field f:getAttributs())
		{
			String attribut=f.getName();
		    Class typeaaa=	f.getType();
		    String type=typeaaa.getSimpleName();
		    boolean obligatoire=false;
		   // Annotation[] as=f.getAnnotations();
		    java.lang.annotation.Annotation[] as=f.getAnnotations();
		    for(java.lang.annotation.Annotation an:as)
		    {
		    	System.out.println(an+" "+an.annotationType().getSimpleName() );
		    	if((an.annotationType().getSimpleName().equals("Id"))||(an.annotationType().getSimpleName().equals("Basic") && an.toString().indexOf("optional=false")!=-1))
		    	{
		    		obligatoire=true;
		    	}
		    }
		    if( !attribut.equals("serialVersionUID"))
		    {
		    System.out.println(attribut+" "+type+" "+obligatoire);
		    attributes_constantes_name+="private static final String ATTRIBUTE_"+attribut.toUpperCase()+"=\""+attribut+"\";\n";
		    if(attribut.equals(uniqueattribute))
		    {
		    	attr_unique_num=""+i;
		    }
		    if(!attribut.equals("id"))
		    {
		    	i++;
		     if(type.equals("String")||type.equals("Integer") || type.equals("Long") || type.equals("Short") || type.equals("Byte") ||type.equals("Float") || type.equals("Double")  || type.equals("double") || type.equals("int") || type.equals("float") || type.equals("int")  || type.equals("boolean")  || type.equals("long")  || type.equals("short")  || type.equals("byte")     )
			{
		    	   if_attribute+="\t\telse if(attribute.equals(ATTRIBUTE_"+attribut.toUpperCase()+"))\n\t\t{\n\t\t\t";
		   		
		    	if(obligatoire)
		    	{
		    		  if_attribute+="error=readNumberNotNull(cell, attribute, t, msgs, rownum);\n\t\t}\n\n";
		    	}
		    	else
		    	{
		    		if_attribute+="error=readNumberNull(cell, attribute, t, msgs, rownum);\n\t\t}\n\n";
		    	}
			}
			else if(type.equals("Boolean"))
			{
				   if_attribute+="\t\telse if(attribute.equals(ATTRIBUTE_"+attribut.toUpperCase()+"))\n\t\t{\n\t\t\t";
					
				if(obligatoire)
		    	{
		    		  if_attribute+="error=readBooleanNotNull(cell, attribute, t, msgs, rownum);\n\t\t}\n\n";
		    	}
		    	else
		    	{
		    		if_attribute+="error=readBooleanNull(cell, attribute, t, msgs, rownum);\n\t\t}\n\n";
		    	}		
			}
			else if(type.equals("Date"))
			{
				   if_attribute+="\t\telse if(attribute.equals(ATTRIBUTE_"+attribut.toUpperCase()+"))\n\t\t{\n\t\t\t";
					
				if(obligatoire)
		    	{
		    		  if_attribute+="error=readDateNotNull(cell, attribute, t, msgs, rownum);\n\t\t}\n\n";
		    	}
		    	else
		    	{
		    		if_attribute+="error=readDateNull(cell, attribute, t, msgs, rownum);\n\t\t}\n\n";
		    	}
			}
			else if(!type.equals("List"))
			{
				System.out.println("                  "+type);
				String uniquetype=uniquesAttribute(type);	
				IMPORT_DAO+="\nimport "+Generateur.PACKAGE_DAO+"."+type+"Dao;";
				IMPORT_ENTITE+="\nimport "+Generateur.PACKAGE_ENTITES+"."+type+";";
			//	System.out.println("IMPORT_ENTITE debut "+classe);
			//	System.out.println(IMPORT_ENTITE);
			//	System.out.println("IMPORT_ENTITE fin ");
				
				ATTRIBUTES_DAO+="\n@Autowired\nprivate "+type+"Dao dao"+type+";\n";
				
				GETTERS_DAO+="\n\n\tpublic "+type+"Dao getDao"+type+"() {\n\t\n\t\treturn dao"+type+";\n\t}\n\n\tpublic void setDao"+type+"("+type+"Dao dao"+type+") {\n\t\tthis.dao"+type+" = dao"+type+";\n\t}";
				if(!type.equals("List"))
				{
				read_attributes+="\n\n"+generateReadAttributeExcel(obligatoire, type, attribut);
				if_attribute+="\t\telse if(attribute.equals(ATTRIBUTE_"+attribut.toUpperCase()+"))\n\t\t{\n\t\t\t";
				if_attribute+="error=read"+type+"Cell(cell, attribute, t, msgs, rownum);\n\t\t}\n\n";
				}

			}
		    }
		    
		    } 
		    
		    
		}
		contenu=contenu.replaceAll("##ATTRIBUTE_UNIQUE_NUM##",attr_unique_num);
		contenu=contenu.replaceAll("##ATTRIBUTES_CONSTANTES_NAME##",attributes_constantes_name);
		contenu=contenu.replaceAll("##IF_ATTRIBUTES##",if_attribute);
		contenu=contenu.replaceAll("##ATTRIBUTE_UNIQUE_NUM##",attr_unique_num);
		
		
		contenu=contenu.replaceAll("##READ_ATTRIBURTE##",read_attributes);
		contenu=contenu.replaceAll("##GETTERS_DAO##",GETTERS_DAO);
		contenu=contenu.replaceAll("##ATTRIBUTES_DAO##",ATTRIBUTES_DAO);
		contenu=contenu.replaceAll("##IMPORT_DAO##",IMPORT_DAO);
		contenu=contenu.replaceAll("##IMPORT_ENTITIES##",IMPORT_ENTITE);
		
		
		
		
		
		
		try {
			System.out.println(System.getProperty("user.dir")+"/src/main/java/"+Generateur.PACKAGE_EXCEL.replace(".", "/")+"/"+this.classe+"Excel.java");
			FileWriter fw = new FileWriter(System.getProperty("user.dir")+"/src/main/java/"+Generateur.PACKAGE_EXCEL.replace(".", "/")+"/"+this.classe+"Excel.java");
				fw.write(contenu);
			//On ferme le flux
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String generateReadAttributeExcel(boolean obligatoire,String type,String attribut)
	{
		
	String unique=	uniquesAttribute(type);
		String ch="";
		if(!obligatoire)
		{
			ch+="	protected boolean read"+Generateur.capitalise(attribut)+"Cell(XSSFCell cell, String attribute, "+classe+" t,List<MessageExcel> msgs, int rownum)";
			ch+="\n	{		";
			ch+="\n		try{";
			ch+="\n			if(cell==null||cell.getStringCellValue().trim().equals(\"\"))";
			ch+="\n			{";
			ch+="\n				t.set"+Generateur.capitalise(attribut)+"(null);";
			ch+="\n			}else";
			ch+="\n			{";
			ch+="\n				"+type+" p=null;";
			try{
			ch+="\n				p=dao"+type+".findBy"+Generateur.capitalise(unique)+"(cell.getStringCellValue());";
			}catch(NullPointerException e)
			{
				
			}
			ch+="\n				if(p==null)";
			ch+="\n				{";
			ch+="\n					MessageExcel msg=new MessageExcel(rownum,ERROR_OBJECT_INEXISTANT);";
			ch+="\n					String[] params={StringUtil.lastStringAfterSeparator("+type+".class.getCanonicalName(), \".\"),getLabelAttribute(attribute)};";
			ch+="\n					msg.setParemetres(params);";
			ch+="\n					msgs.add(msg);";
			ch+="\n					return false;";
			ch+="\n				}";
			ch+="\n				t.set"+Generateur.capitalise(attribut)+"(p);";
			ch+="\n			}";
			ch+="\n		}catch(Exception e)";
			ch+="\n		{";
			ch+="\n			MessageExcel msg=new MessageExcel(rownum,ERROR_READ_CELL);";
			ch+="\n			String[] params={getLabelAttribute(attribute)};";
			ch+="\n			msg.setParemetres(params);";
			ch+="\n			msgs.add(msg);";
			ch+="\n			return false;";
			ch+="\n		}";
			ch+="\n		";
			ch+="\n		return true;";
			ch+="\n	}";
		}
		else
		{
			ch+="	protected boolean read"+Generateur.capitalise(attribut)+"Cell(XSSFCell cell, String attribute, "+classe+" t,List<MessageExcel> msgs, int rownum)";
			ch+="\n	{		";
			ch+="\n		try{";
			ch+="\n			if(cell==null||cell.getStringCellValue().trim().equals(\"\"))";
			ch+="\n			{";
			ch+="\n			MessageExcel msg=new MessageExcel(rownum,CELL_NULL);";
			ch+="\n			String[] params={getLabelAttribute(attribute)};";
			ch+="\n			msg.setParemetres(params);";
			ch+="\n			msgs.add(msg);";
			ch+="\n			return false;";
			
			ch+="\n			}else";
			ch+="\n			{";
			ch+="\n				"+type+" p=null;";
			ch+="\n				p=dao"+type+".findBy"+Generateur.capitalise(unique)+"(cell.getStringCellValue());";
			ch+="\n				if(p==null)";
			ch+="\n				{";
			ch+="\n					MessageExcel msg=new MessageExcel(rownum,ERROR_OBJECT_INEXISTANT);";
			ch+="\n					String[] params={StringUtil.lastStringAfterSeparator("+type+".class.getCanonicalName(), \".\"),getLabelAttribute(attribute)};";
			ch+="\n					msg.setParemetres(params);";
			ch+="\n					msgs.add(msg);";
			ch+="\n					return false;";
			ch+="\n				}";
			ch+="\n				t.set"+Generateur.capitalise(attribut)+"(p);";
			ch+="\n			}";
			ch+="\n		}catch(Exception e)";
			ch+="\n		{";
			ch+="\n			MessageExcel msg=new MessageExcel(rownum,ERROR_READ_CELL);";
			ch+="\n			String[] params={getLabelAttribute(attribute)};";
			ch+="\n			msg.setParemetres(params);";
			ch+="\n			msgs.add(msg);";
			ch+="\n			return false;";
			ch+="\n		}";
			ch+="\n		";
			ch+="\n		return true;";
			ch+="\n	}";

		}
		return ch;
	}
	
	
	public void genererBundleFR()
	{
		System.out.println("genererBundleFR");
		String contenu="";//lireBundleFR();
		
		contenu+="\n\n";
        for(String ch:msgsfr)
        {
        	contenu+=(ch+"\n");
        }
		
		try {
			//FileWriter fw = new FileWriter(cheminpackagegenerateur()+"/BundleFR"+classe+".properties");
			FileOutputStream fww=new FileOutputStream(cheminpackagegenerateur()+"/properties/fr/BundleFR"+classe+".properties");
			OutputStreamWriter out=new OutputStreamWriter(fww,"UTF8");
			//FileWriter fw = new FileWriter(System.getProperty("user.dir")+"/src/main/resources/Bundle_fr.properties");
				out.write(contenu);
				
			//On ferme le flux
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void genererBundleEN()
	{
		System.out.println("genererBundleEN");
		String contenu="";//lireBundleEN();
		
		contenu+="\n\n";
        for(String ch:msgsen)
        {
        	contenu+=(ch+"\n");
        }
		
		try {
			FileWriter fw = new FileWriter(cheminpackagegenerateur()+"/properties/en/BundleEN"+classe+".properties");
			
		//	FileWriter fw = new FileWriter(System.getProperty("user.dir")+"/src/main/resources/Bundle_en.properties");
				fw.write(contenu);
			//On ferme le flux
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void genererCreate()
	{
		creatfolder();
		System.out.println("genererCreate");
		String contenu=lireTemplate("Create.xhtml");
		contenu=contenu.replaceAll("##ENTITY_PACKAGE##", Generateur.PACKAGE_ENTITES+"."+classe)
				        .replaceAll("##DAO_PACKAGE##", Generateur.PACKAGE_DAO+"."+dao)
				        .replaceAll("##SERVICE_PACKAGE##", Generateur.PACKAGE_SERVICES+"."+service)
				        .replaceAll("##PACKAGE##", Generateur.PACKAGE_CONTROLLER)
				        .replaceAll("##ENTITY##",classe)
				        .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
    	    			.replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
    	    			
				        .replaceAll("##SERVICE##",service)
				        .replaceAll("##CONTROLLER##",beancontroleur)
				        .replaceAll("##SERVICE##",beanservice)
				        .replaceAll("##DAO##", dao);
		
		
	//	System.out.println(contenu);
		String attributs="";
		for(Field f:getAttributs())
		{
			String attribut=f.getName();
		    Class type=	f.getType();
		    String typea=type.getSimpleName();
		    boolean obligatoire=false;
		   // Annotation[] as=f.getAnnotations();
		    java.lang.annotation.Annotation[] as=f.getAnnotations();
		    for(java.lang.annotation.Annotation an:as)
		    {
		    	//System.out.println(an+" "+an.annotationType().getSimpleName() );
		    	if(an.annotationType().getSimpleName().equals("Basic") && an.toString().indexOf("optional=false")!=-1)
		    	{
		    		obligatoire=true;
		    	}
		    }
		    if(!attribut.equals("id") && !attribut.equals("serialVersionUID"))
		    {
		    System.out.println(attribut+" "+typea+" "+obligatoire);
		    attributs+=generateInput(attribut,typea,obligatoire);
		    } 
		}
		contenu=contenu.replaceAll("##ATTRIBUTES##", attributs);
		try {
			FileWriter fw = new FileWriter(chemindossier()+"/Create.xhtml");
				fw.write(contenu);
			//On ferme le flux
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void genererCreateMenu()
	{
		System.out.println("genererMenu");
		String contenu=lireTemplate("Menu.xhtml");
		contenu=contenu.replaceAll("##ENTITY_PACKAGE##", Generateur.PACKAGE_ENTITES+"."+classe)
				        .replaceAll("##DAO_PACKAGE##", Generateur.PACKAGE_DAO+"."+dao)
				        .replaceAll("##SERVICE_PACKAGE##", Generateur.PACKAGE_SERVICES+"."+service)
				        .replaceAll("##PACKAGE##", Generateur.PACKAGE_CONTROLLER)
				        .replaceAll("##ENTITY##",classe)
				        .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
    	    			.replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
    	    			.replaceAll("##DOSSIER##",dossier)
				        .replaceAll("##SERVICE##",service)
				        .replaceAll("##CONTROLLER##",beancontroleur)
				        .replaceAll("##SERVICE##",beanservice)
				        .replaceAll("##DAO##", dao);
		
		
		try {
			FileWriter fw = new FileWriter(cheminpackagegenerateur()+"/menus/Menu"+classe+".xhtml");
				fw.write(contenu);
			//On ferme le flux
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	public void genererList()
	{
		creatfolder();
		System.out.println("genererList");
		String contenu=lireTemplate("List.xhtml");
		contenu=contenu.replaceAll("##ENTITY_PACKAGE##", Generateur.PACKAGE_ENTITES+"."+classe)
				        .replaceAll("##DAO_PACKAGE##", Generateur.PACKAGE_DAO+"."+dao)
				        .replaceAll("##SERVICE_PACKAGE##", Generateur.PACKAGE_SERVICES+"."+service)
				        .replaceAll("##PACKAGE##", Generateur.PACKAGE_CONTROLLER)
				        .replaceAll("##ENTITY##",classe)
				        .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
    	    			.replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
    	    			
				        .replaceAll("##SERVICE##",service)
				        .replaceAll("##CONTROLLER##",beancontroleur)
				        .replaceAll("##SERVICE##",beanservice)
				        .replaceAll("##DAO##", dao);
		
		
	//	System.out.println(contenu);
		String attributs="";
		for(Field f:getAttributs())
		{
			String attribut=f.getName();
		    Class type=	f.getType();
		    String typea=type.getSimpleName();
		    if(!attribut.equals("id") && !attribut.equals("serialVersionUID"))
		    {
		      attributs+=generateColumn(attribut,typea);
		    } 
		}
		contenu=contenu.replaceAll("##ATTRIBUTES##", attributs);
		try {
			FileWriter fw = new FileWriter(chemindossier()+"/List.xhtml");
				fw.write(contenu);
			//On ferme le flux
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void genererEdit()
	{
		creatfolder();
		System.out.println("genererEdit");
		String contenu=lireTemplate("Edit.xhtml");
		contenu=contenu.replaceAll("##ENTITY_PACKAGE##", Generateur.PACKAGE_ENTITES+"."+classe)
				        .replaceAll("##DAO_PACKAGE##", Generateur.PACKAGE_DAO+"."+dao)
				        .replaceAll("##SERVICE_PACKAGE##", Generateur.PACKAGE_SERVICES+"."+service)
				        .replaceAll("##PACKAGE##", Generateur.PACKAGE_CONTROLLER)
				        .replaceAll("##ENTITY##",classe)
				        .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
    	    			.replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
    	    			
				        .replaceAll("##SERVICE##",service)
				        .replaceAll("##CONTROLLER##",beancontroleur)
				        .replaceAll("##SERVICE##",beanservice)
				        .replaceAll("##DAO##", dao);
		
		
	//	System.out.println(contenu);
		String attributs="";
		for(Field f:getAttributs())
		{
			String attribut=f.getName();
		    Class type=	f.getType();
		    String typea=type.getSimpleName();
		    boolean obligatoire=false;
		   // Annotation[] as=f.getAnnotations();
		    java.lang.annotation.Annotation[] as=f.getAnnotations();
		    for(java.lang.annotation.Annotation an:as)
		    {
		    	//System.out.println(an+" "+an.annotationType().getSimpleName() );
		    	if(an.annotationType().getSimpleName().equals("Basic") && an.toString().indexOf("optional=false")!=-1)
		    	{
		    		obligatoire=true;
		    	}
		    }
		    if(!attribut.equals("id") && !attribut.equals("serialVersionUID"))
		    {
		    	attributs+=generateInput(attribut,typea,obligatoire);
		    }
		      
		}
		contenu=contenu.replaceAll("##ATTRIBUTES##", attributs);
		try {
			FileWriter fw = new FileWriter(chemindossier()+"/Edit.xhtml");
				fw.write(contenu);
			//On ferme le flux
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
   
	
	public void genererView()
	{
		creatfolder();
		System.out.println("genererView");
		String contenu=lireTemplate("View.xhtml");
		contenu=contenu.replaceAll("##ENTITY_PACKAGE##", Generateur.PACKAGE_ENTITES+"."+classe)
				        .replaceAll("##DAO_PACKAGE##", Generateur.PACKAGE_DAO+"."+dao)
				        .replaceAll("##SERVICE_PACKAGE##", Generateur.PACKAGE_SERVICES+"."+service)
				        .replaceAll("##PACKAGE##", Generateur.PACKAGE_CONTROLLER)
				        .replaceAll("##ENTITY##",classe)
				        .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
    	    			.replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
    	    			
				        .replaceAll("##SERVICE##",service)
				        .replaceAll("##CONTROLLER##",beancontroleur)
				        .replaceAll("##SERVICE##",beanservice)
				        .replaceAll("##DAO##", dao);
		
		
	//	System.out.println(contenu);
		String attributs="";
		for(Field f:getAttributs())
		{
			String attribut=f.getName();
		    Class type=	f.getType();
		    String typea=type.getSimpleName();
		    if(!attribut.equals("id") && !attribut.equals("serialVersionUID"))
		    {
		    attributs+=generateAtributeView(attribut,typea);
		    }
		      
		}
		contenu=contenu.replaceAll("##ATTRIBUTES##", attributs);
		try {
			FileWriter fw = new FileWriter(chemindossier()+"/View.xhtml");
				fw.write(contenu);
			//On ferme le flux
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private String generateAtributeView(String attribut, String type) {
		String ch="\n";
		
		if(type.equals("String")||type.equals("Integer") || type.equals("Long") || type.equals("Short") || type.equals("Byte") ||type.equals("Float") || type.equals("Double")  || type.equals("double") || type.equals("int") || type.equals("float") || type.equals("int")  || type.equals("boolean")  || type.equals("long")  || type.equals("short")  || type.equals("byte")     )
		{
			 ch+="\t<p:outputLabel value='#{bundle."+classe+"Label_"+attribut+"}:' />\n";
			 ch+="\t<h:outputText value='#{"+beancontroleur+".selected."+attribut+"}' title='#{bundle."+classe+"Label_"+attribut+"}'/>";
					
		}
		else if(type.equals("Boolean"))
		{
			 ch+="\t<p:outputLabel value='#{bundle."+classe+"Label_"+attribut+"}:' />\n";
				 ch+="\n\t\t\t<h:panelGroup>\n";
				ch+="\n\t\t\t\t<h:outputText value='#{bundle.Yes}' rendered='#{"+beancontroleur+".selected."+attribut+"}'/>\n";
				ch+="\n\t\t\t\t<h:outputText value='#{bundle.No}' rendered='#{"+beancontroleur+".selected."+attribut+" == false}'/>\n";
			ch+="\n\t\t\t</h:panelGroup>\n";	
		
		}
		else if(type.equals("Date"))
		{
			 ch+="\t<p:outputLabel value='#{bundle."+classe+"Label_"+attribut+"}:' />\n";
			 ch+="\t<h:outputText value='#{"+beancontroleur+".selected."+attribut+"}' title='#{bundle."+classe+"Label_"+attribut+"}'><f:convertDateTime locale='#{changeLocale.locale}' pattern='dd/MM/yyyy'/></h:outputText>\n";
		
		}
		else
		{
			String unique="";
			try{
			unique="."+uniquesAttribute(type);
			}
			catch(Exception e)
			{
				
			}
			 ch+="\t<p:outputLabel value='#{bundle."+classe+"Label_"+attribut+"}:' />\n";
			 ch+="\t<h:outputText value='#{"+beancontroleur+".selected."+attribut+""+unique+"}' title='#{bundle."+classe+"Label_"+attribut+"}'/>";
			

		}
		ch+="\n";
		return ch;

	}

	private String generateColumn(String attribut, String type) {
		String ch="";
		
		if(type.equals("String")||type.equals("Integer") || type.equals("Long") || type.equals("Short") || type.equals("Byte") ||type.equals("Float") || type.equals("Double")  || type.equals("double") || type.equals("int") || type.equals("float") || type.equals("int")  || type.equals("boolean")  || type.equals("long")  || type.equals("short")  || type.equals("byte")     )
		{
			ch+="\t<p:column sortBy='#{item."+attribut+"}' filterBy='#{item."+attribut+"}'>\n";
			ch+="\t\t<f:facet name='header'>\n";
			ch+="\t\t\t<h:outputText value='#{bundle."+classe+"Label_"+attribut+"}:' />\n";
			ch+="\t\t</f:facet>\n";
			ch+="\t\t\t<h:outputText value='#{item."+attribut+"}'/>\n";
			ch+="\t</p:column>";
			
						
		}
		else if(type.equals("Boolean"))
		{
			
			ch+="\t<p:column sortBy='#{item."+attribut+"}' >\n";
			ch+="\t\t<f:facet name='header'>\n";
			ch+="\t\t\t<h:outputText value='#{bundle."+classe+"Label_"+attribut+"}:' />\n";
			ch+="\t\t</f:facet>\n";
			ch+="\t\t\t<h:outputText value='#{bundle.Yes}' rendered='#{item."+attribut+"}'/>\n";
			ch+="\t\t\t<h:outputText value='#{bundle.No}' rendered='#{item."+attribut+" == false}'/>\n";
			ch+="\t</p:column>";		
		}
		else if(type.equals("Date"))
		{
			ch+="\t<p:column sortBy='#{item."+attribut+"}' filterBy='#{item."+attribut+"}'>\n";
			ch+="\t\t<f:facet name='header'>\n";
			ch+="\t\t\t<h:outputText value='#{bundle."+classe+"Label_"+attribut+"}:' />\n";
			ch+="\t\t</f:facet>\n";
			ch+="\t\t\t<h:outputText value='#{item."+attribut+"}'> <f:convertDateTime locale='#{changeLocale.locale}' pattern='dd/MM/yyyy'/></h:outputText>\n";
			ch+="\t</p:column>";
		}
		else
		{
			
		String unique="";
			try{
			unique="."+uniquesAttribute(type);
			}
			catch(Exception e)
			{
				
			}
			ch+="\t<p:column sortBy='#{item."+attribut+""+unique+"}' filterBy='#{item."+attribut+""+unique+"}'>\n";
			ch+="\t\t<f:facet name='header'>\n";
			ch+="\t\t\t<h:outputText value='#{bundle."+classe+"Label_"+attribut+"}:' />\n";
			ch+="\t\t</f:facet>\n";
			ch+="\t\t\t<h:outputText value='#{item."+attribut+""+unique+"}'/>\n";
			ch+="\t</p:column>";
					

		}
		return ch;

	}

	
	public void genererIndex()
	{
		creatfolder();
		System.out.println("genererIndex");
		String contenu=lireTemplate("Index.xhtml");
		contenu=contenu.replaceAll("##ENTITY_PACKAGE##", Generateur.PACKAGE_ENTITES+"."+classe)
				        .replaceAll("##DAO_PACKAGE##", Generateur.PACKAGE_DAO+"."+dao)
				        .replaceAll("##SERVICE_PACKAGE##", Generateur.PACKAGE_SERVICES+"."+service)
				        .replaceAll("##PACKAGE##", Generateur.PACKAGE_CONTROLLER)
				        .replaceAll("##ENTITY##",classe)
				        .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
    	    			.replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
    	    			
				        .replaceAll("##SERVICE##",service)
				        .replaceAll("##CONTROLLER##",beancontroleur)
				        .replaceAll("##SERVICE##",beanservice)
				        .replaceAll("##DAO##", dao);
		
		
		try {
			FileWriter fw = new FileWriter(chemindossier()+"/index.xhtml");
				fw.write(contenu);
			//On ferme le flux
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private String generateInput(String attribut, String type,
			boolean obligatoire) {
		String ch="\n";
		
		if(attribut.equals("description") || attribut.equals("observations"))
		{
			ch+="\t<p:outputLabel value='#{bundle."+classe+"Label_"+attribut+"}' for='"+attribut+"' />\n";
			ch+="\t<p:inputTextarea rows='2'  id='"+attribut+"' value='#{"+beancontroleur+".selected."+attribut+"}' title='#{bundle."+classe+"Label_"+attribut+"}'";
			 if(obligatoire)
			 {
				 ch+=" required='true' requiredMessage='#{bundle."+classe+"RequiredMessage_"+attribut+"}'/>\n"; 
					 
			 }
			 else
			 {
				 ch+=" />\n";
			 }
	
		}
		else if(type.equals("String")||type.equals("Integer") || type.equals("Long") || type.equals("Short") || type.equals("Byte") ||type.equals("Float") || type.equals("Double")  || type.equals("double") || type.equals("int") || type.equals("float") || type.equals("int")  || type.equals("boolean")  || type.equals("long")  || type.equals("short")  || type.equals("byte")     )
		{
			 ch+="\t<p:outputLabel value='#{bundle."+classe+"Label_"+attribut+"}' for='"+attribut+"' />\n";
			 ch+="\t<p:inputText id='"+attribut+"' value='#{"+beancontroleur+".selected."+attribut+"}' title='#{bundle."+classe+"Label_"+attribut+"}'"; 
			 if(obligatoire)
			 {
				 ch+=" required='true' requiredMessage='#{bundle."+classe+"RequiredMessage_"+attribut+"}'/>\n"; 
					 
			 }
			 else
			 {
				 ch+=" />\n";
			 }
		}
		else if(type.equals("Boolean"))
		{
			ch+="\t<p:outputLabel value='#{bundle."+classe+"Label_"+attribut+"}' for='"+attribut+"' />\n";
			
			ch+="\t<p:selectBooleanCheckbox id='"+attribut+"' value='#{"+beancontroleur+".selected."+attribut+"}'  title='#{bundle."+classe+"Label_"+attribut+"}'";
			if(obligatoire)
			 {
				 ch+=" required='true' requiredMessage='#{bundle."+classe+"RequiredMessage_"+attribut+"}'/>\n"; 
					 
			 }
			 else
			 {
				 ch+=" />\n";
			 }
		}
		else if(type.equals("Date"))
		{
			ch+="\t<p:outputLabel value='#{bundle."+classe+"Label_"+attribut+"}' for='"+attribut+"' />\n";
			ch+="\t<p:calendar locale='#{changeLocale.locale}' id='"+attribut+"' value='#{"+beancontroleur+".selected."+attribut+"}' pattern='dd/MM/yyyy'   title='#{bundle."+classe+"Label_"+attribut+"}' ";
			if(obligatoire)
			 {
				 ch+=" required='true' requiredMessage='#{bundle."+classe+"RequiredMessage_"+attribut+"}'/>\n"; 
					 
			 }
			 else
			 {
				 ch+=" />\n";
			 }
		}
		else
		{
			
			String unique="";
			try{
			unique="."+uniquesAttribute(type);
			}
			catch(Exception e)
			{
				
			}
			 ch+="\t<p:outputLabel value='#{bundle."+classe+"Label_"+attribut+"}' for='"+attribut+"' />\n";
			ch+="\t<p:selectOneMenu id='"+attribut+"' value='#{"+beancontroleur+".selected."+attribut+"}' converter='#{"+Generateur.notcapitalise(type)+"Converter}'";
			 if(obligatoire)
			 {
				 ch+=" required='true' requiredMessage='#{bundle."+classe+"RequiredMessage_"+attribut+"}'>\n"; 
					 
			 }
			 else
			 {
				 ch+=" >\n";
			 }
			 ch+="\t\t<f:selectItem noSelectionOption='true' itemLabel='#{bundle.SelectOneMessage}' />\n";
			 ch+="\t\t<f:selectItems value='#{"+Generateur.notcapitalise(type)+"Controller.items}' var='item' itemValue='#{item}' itemLabel='#{item"+unique+"}' />\n";
			 ch+="\t</p:selectOneMenu>\n";

		}
		ch+="\n";
		return ch;
	}

	public String lireTemplate(String template){
			FileReader fr;
		
		try {
			fr = new FileReader(getCheminTemplate(template));
			String str = "";
			int i = 0;
			//Lecture des donn�es
			while((i = fr.read()) != -1)
				str += (char)i;
			return str;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	public String lireBundleFR(){
		FileReader fr;
	
	try {
		fr = new FileReader(getCheminBundleFR());
		String str = "";
		int i = 0;
		//Lecture des donn�es
		while((i = fr.read()) != -1)
			str += (char)i;
		return str;
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;

}
	
	public String lireBundleEN(){
		FileReader fr;
	
	try {
		fr = new FileReader(getCheminBundleEN());
		String str = "";
		int i = 0;
		//Lecture des donn�es
		while((i = fr.read()) != -1)
			str += (char)i;
		return str;
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;

}

	
	private String getCheminTemplate(String template)
	{
		return System.getProperty("user.dir")+"/src/main/java/com/khalid/generateur/templates/"+template+".template";
	}
	private String getCheminProerties(String template)
	{
		return System.getProperty("user.dir")+"/src/main/java/com/khalid/generateur/templates/"+template+".properties";
	}
	
	private String getCheminBundleFR()
	{
		return System.getProperty("user.dir")+"/src/main/resources/Bundle_fr.properties";
	}
	private String getCheminBundleEN()
	{
		return System.getProperty("user.dir")+"/src/main/resources/Bundle_en.properties";
	}
	
	public void generer()
	{
		
		genererDAO();
	  genererIService();
		genererService();
		genererConverter();
		genererController();
		creatfolder();
		genererCreate();
		genererEdit();
		genererIndex();
		genererList();
		genererView();
		remplirBundleFR();
		genererBundleFR();
		remplirBundleEN();
		genererBundleEN();//*/
		genererSqlPrivilege();
		genererCreateMenu();
		genererManipExcel();
	}
	
	private void creatfolder()
	{
		File theDir = new File(chemindossier());

		// if the directory does not exist, create it
		if (!theDir.exists()) {
		    System.out.println("creating directory: " + chemindossier());
		    boolean result = false;

		    try{
		        theDir.mkdirs();
		        result = true;
		    } 
		    catch(SecurityException se){
		        //handle it
		    }        
		    if(result) {    
		        System.out.println("DIR created");  
		    }
		}
	}


	private String chemindossier()
	{
		return System.getProperty("user.dir")+"/src/main/webapp/"+dossier;
	}
	
	private String cheminpackagegenerateur()
	{
		return System.getProperty("user.dir")+"/src/main/java/com/khalid/generateur/";
	}
	
	public void remplirBundleFR()
	{
	  try {
		Properties p=load("fr");
		System.out.println();System.out.println();System.out.println();System.out.println();
		System.out.println(p.getProperty("CREATE_SUCCESS"));
		this.msgsfr.add(p.getProperty("CREATE_SUCCESS").replaceAll("##ENTITE##", classe)
				  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
				  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
				  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
				  );
		
		this.msgsfr.add(p.getProperty("CREATE_ERROR").replaceAll("##ENTITE##", classe)
				  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
				  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
				  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
				  );
		this.msgsfr.add(p.getProperty("Edit_SUCCESS").replaceAll("##ENTITE##", classe)
				  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
				  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
				  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
				  );
		this.msgsfr.add(p.getProperty("Edit_ERROR").replaceAll("##ENTITE##", classe)
				  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
				  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
				  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
				  );
		this.msgsfr.add(p.getProperty("Delete_SUCCESS").replaceAll("##ENTITE##", classe)
				  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
				  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
				  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
				  );
		this.msgsfr.add(p.getProperty("Delete_ERROR").replaceAll("##ENTITE##", classe)
				  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
				  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
				  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
				  );
		
		this.msgsfr.add(p.getProperty("Delete_CONFIRM").replaceAll("##ENTITE##", classe)
				  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
				  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
				  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
				  );
		
		this.msgsfr.add(p.getProperty("TITLE").replaceAll("##ENTITE##", classe)
				  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
				  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
				  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
				  );
		this.msgsfr.add(p.getProperty("LIST_TITLE").replaceAll("##ENTITE##", classe)
				  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
				  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
				  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
				  );
		this.msgsfr.add(p.getProperty("LIST_EMPTY").replaceAll("##ENTITE##", classe)
				  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
				  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
				  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
				  );
		this.msgsfr.add(p.getProperty("CREATE_TITLE").replaceAll("##ENTITE##", classe)
				  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
				  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
				  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
				  );
		this.msgsfr.add(p.getProperty("EDIT_TITLE").replaceAll("##ENTITE##", classe)
				  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
				  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
				  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
				  );
		this.msgsfr.add(p.getProperty("VIEW_TITLE").replaceAll("##ENTITE##", classe)
				  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
				  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
				  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
				  );
		
		for(Field f:getAttributs())
		{
			String attribut=f.getName();
		    Class type=	f.getType();
		    String typea=type.getSimpleName();
		    if(!attribut.equals("serialVersionUID"))
		    {
		    			this.msgsfr.add(p.getProperty("ATTRIBUTE_LABEL").replaceAll("##ENTITE##", classe)
		    			.replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
		    			.replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
		    			.replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
		    			.replaceAll("##ATTRIBUT##", attribut)
		    			.replaceAll("##ATTRIBUT_MINISCULE##", attribut.toLowerCase())
		    			.replaceAll("##ATTRIBUT_MAJISCULE##", attribut.toLowerCase())
		    			.replaceAll("##ATTRIBUT_CAPITAL##", Generateur.capitalise(attribut))
		    			);
		    			
		    			
		    			this.msgsfr.add(p.getProperty("ATTRIBUTE_REQUIRED").replaceAll("##ENTITE##", classe)
		    	    			.replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
		    	    			.replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
		    	    			.replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
		    	    			.replaceAll("##ATTRIBUT##", attribut)
		    	    			.replaceAll("##ATTRIBUT_MINISCULE##", attribut.toLowerCase())
		    	    			.replaceAll("##ATTRIBUT_MAJISCULE##", attribut.toLowerCase())
		    	    			.replaceAll("##ATTRIBUT_CAPITAL##", Generateur.capitalise(attribut))
		    	    			);

		    } 
		}

		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	public void remplirBundleEN()
	{
	  try {
		Properties p=load("en");
		this.msgsen.add(p.getProperty("CREATE_SUCCESS").replaceAll("##ENTITE##", classe)
				  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
				  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
				  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
				  );
		this.msgsen.add(p.getProperty("CREATE_ERROR").replaceAll("##ENTITE##", classe)
				  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
				  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
				  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
				  );
		this.msgsen.add(p.getProperty("Edit_SUCCESS").replaceAll("##ENTITE##", classe)
				  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
				  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
				  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
				  );
		this.msgsen.add(p.getProperty("Edit_ERROR").replaceAll("##ENTITE##", classe)
				  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
				  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
				  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
				  );
		this.msgsen.add(p.getProperty("Delete_SUCCESS").replaceAll("##ENTITE##", classe)
				  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
				  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
				  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
				  );
		this.msgsen.add(p.getProperty("Delete_ERROR").replaceAll("##ENTITE##", classe)
				  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
				  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
				  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
				  );
		this.msgsen.add(p.getProperty("Delete_CONFIRM").replaceAll("##ENTITE##", classe)
				  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
				  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
				  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
				  );
		
		this.msgsen.add(p.getProperty("TITLE").replaceAll("##ENTITE##", classe)
				  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
				  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
				  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
				  );
		this.msgsen.add(p.getProperty("LIST_TITLE").replaceAll("##ENTITE##", classe)
				  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
				  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
				  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
				  );
		this.msgsen.add(p.getProperty("LIST_EMPTY").replaceAll("##ENTITE##", classe)
				  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
				  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
				  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
				  );
		this.msgsen.add(p.getProperty("CREATE_TITLE").replaceAll("##ENTITE##", classe)
				  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
				  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
				  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
				  );
		this.msgsen.add(p.getProperty("EDIT_TITLE").replaceAll("##ENTITE##", classe)
				  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
				  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
				  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
				  );
		this.msgsen.add(p.getProperty("VIEW_TITLE").replaceAll("##ENTITE##", classe)
				  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
				  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
				  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
				  );
		
		for(Field f:getAttributs())
		{
			String attribut=f.getName();
		    Class type=	f.getType();
		    String typea=type.getSimpleName();
		    if(!attribut.equals("serialVersionUID"))
		    {
		    			this.msgsen.add(p.getProperty("ATTRIBUTE_LABEL").replaceAll("##ENTITE##", classe)
		    			.replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
		    			.replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
		    			.replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
		    			.replaceAll("##ATTRIBUT##", attribut)
		    			.replaceAll("##ATTRIBUT_MINISCULE##", attribut.toLowerCase())
		    			.replaceAll("##ATTRIBUT_MAJISCULE##", attribut.toLowerCase())
		    			.replaceAll("##ATTRIBUT_CAPITAL##", Generateur.capitalise(attribut))
		    			);
		    			
		    			
		    			this.msgsen.add(p.getProperty("ATTRIBUTE_REQUIRED").replaceAll("##ENTITE##", classe)
		    	    			.replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
		    	    			.replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
		    	    			.replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
		    	    			.replaceAll("##ATTRIBUT##", attribut)
		    	    			.replaceAll("##ATTRIBUT_MINISCULE##", attribut.toLowerCase())
		    	    			.replaceAll("##ATTRIBUT_MAJISCULE##", attribut.toLowerCase())
		    	    			.replaceAll("##ATTRIBUT_CAPITAL##", Generateur.capitalise(attribut))
		    	    			);

		    } 
		}

		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

	
	
	public void remplirBundle()
	{
		this.msgsfr.add(MSG_FR_CREATE_SUCCESS.replaceAll("##ENTITE##", classe)
											  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
											  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
											  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
											  );
		
		this.msgsfr.add(MSG_FR_CREATE_ERROR.replaceAll("##ENTITE##", classe)
				  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
				  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
				  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
				  );
		
		this.msgsfr.add(MSG_FR_Edit_SUCCESS.replaceAll("##ENTITE##", classe)
				  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
				  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
				  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
				  );

this.msgsfr.add(MSG_FR_Edit_ERROR.replaceAll("##ENTITE##", classe)
.replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
.replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
.replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
);

this.msgsfr.add(MSG_FR_Delete_SUCCESS.replaceAll("##ENTITE##", classe)
		  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
		  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
		  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
		  );

this.msgsfr.add(MSG_FR_Delete_ERROR.replaceAll("##ENTITE##", classe)
		  .replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
		  .replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
		  .replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
		  );

this.msgsfr.add(MSG_FR_VIEW_TITLE.replaceAll("##ENTITE##", classe)
.replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
.replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
.replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
);


this.msgsfr.add(MSG_FR_EDIT_TITLE.replaceAll("##ENTITE##", classe)
.replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
.replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
.replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
);



this.msgsfr.add(MSG_FR_CREATE_TITLE.replaceAll("##ENTITE##", classe)
.replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
.replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
.replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
);


this.msgsfr.add(MSG_FR_LIST_EMPTY.replaceAll("##ENTITE##", classe)
.replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
.replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
.replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
);



this.msgsfr.add(MSG_FR_LIST_TITLE.replaceAll("##ENTITE##", classe)
.replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
.replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
.replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
);



this.msgsfr.add(MSG_FR_TITLE.replaceAll("##ENTITE##", classe)
.replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
.replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
.replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
);
	

for(Field f:getAttributs())
{
	String attribut=f.getName();
    Class type=	f.getType();
    String typea=type.getSimpleName();
    if(!attribut.equals("serialVersionUID"))
    {
    			this.msgsfr.add(MSG_FR_ATTRIBUTE_LABEL.replaceAll("##ENTITE##", classe)
    			.replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
    			.replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
    			.replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
    			.replaceAll("##ATTRIBUT##", attribut)
    			.replaceAll("##ATTRIBUT_MINISCULE##", attribut.toLowerCase())
    			.replaceAll("##ATTRIBUT_MAJISCULE##", attribut.toLowerCase())
    			.replaceAll("##ATTRIBUT_CAPITAL##", Generateur.capitalise(attribut))
    			);
    			
    			
    			this.msgsfr.add(MSG_FR_ATTRIBUTE_REQUIRED.replaceAll("##ENTITE##", classe)
    	    			.replaceAll("##ENTITE_MINISCULE##", classe.toLowerCase())
    	    			.replaceAll("##ENTITE_MAJISCULE##", classe.toUpperCase())
    	    			.replaceAll("##ENTITE_CAPITAL##", Generateur.capitalise(classe))
    	    			.replaceAll("##ATTRIBUT##", attribut)
    	    			.replaceAll("##ATTRIBUT_MINISCULE##", attribut.toLowerCase())
    	    			.replaceAll("##ATTRIBUT_MAJISCULE##", attribut.toLowerCase())
    	    			.replaceAll("##ATTRIBUT_CAPITAL##", Generateur.capitalise(attribut))
    	    			);

    } 
}
		
	}
	public  Properties load(String filename) throws IOException, FileNotFoundException{
	      Properties properties = new Properties();

	    //  FileInputStream input = new FileInputStream(getCheminProerties(filename)); 
	      try{

	       //  properties.load(input);
	         
	         Properties props = new Properties();
	    //     URL resource = getClass().getClassLoader().getResource(filename+".properties");         
	      //   props.load(new InputStreamReader(input, "UTF8"));
	         props.load(new StringReader(lireTemplate(filename+".properties")));
	         
	     //    properties.l
	         return props;
	      //   return properties;

	      }

	              finally{

	 //     input.close();

	      }

	   }
	public String genererSqlPrivilege()
	{
		List<String> liste=new ArrayList<String>();
		String contenu="";
		contenu+="\n"+insertprivilege.replaceAll("##ENTITY_PRIVILEGE##", classe.toUpperCase()+"_WRITE").replaceAll("##DESCRIPTION##", "DELETE and EDIT and ADD");
		contenu+="\n"+insertprivilege.replaceAll("##ENTITY_PRIVILEGE##", classe.toUpperCase()+"_READ").replaceAll("##DESCRIPTION##", "");
		contenu+="\n"+insertprivilege.replaceAll("##ENTITY_PRIVILEGE##", classe.toUpperCase()+"_DELETE").replaceAll("##DESCRIPTION##", "");
		contenu+="\n"+insertprivilege.replaceAll("##ENTITY_PRIVILEGE##", classe.toUpperCase()+"_EDIT").replaceAll("##DESCRIPTION##", "");
		contenu+="\n"+insertprivilege.replaceAll("##ENTITY_PRIVILEGE##", classe.toUpperCase()+"_ADD").replaceAll("##DESCRIPTION##", "");
       
		try {
			FileWriter fw = new FileWriter(cheminpackagegenerateur()+"/prvileges/Previlege"+classe+".sql");
				fw.write(contenu);
			//On ferme le flux
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	public String genererMenu()
	{
		List<String> liste=new ArrayList<String>();
		
		System.out.println(insertprivilege.replaceAll("##ENTITY_PRIVILEGE##", classe.toUpperCase()+"_WRITE").replaceAll("##DESCRIPTION##", "DELETE and EDIT and ADD"));
		System.out.println(insertprivilege.replaceAll("##ENTITY_PRIVILEGE##", classe.toUpperCase()+"_READ").replaceAll("##DESCRIPTION##", ""));
		System.out.println(insertprivilege.replaceAll("##ENTITY_PRIVILEGE##", classe.toUpperCase()+"_DELETE").replaceAll("##DESCRIPTION##", ""));
		System.out.println(insertprivilege.replaceAll("##ENTITY_PRIVILEGE##", classe.toUpperCase()+"_EDIT").replaceAll("##DESCRIPTION##", ""));
		System.out.println(insertprivilege.replaceAll("##ENTITY_PRIVILEGE##", classe.toUpperCase()+"_ADD").replaceAll("##DESCRIPTION##", ""));
       return null;
	}
	
    private final static String insertprivilege="INSERT INTO previlege(nom,description) VALUES('##ENTITY_PRIVILEGE##','##DESCRIPTION##' );";	

   
    
	private static String MSG_FR_CREATE_SUCCESS="##ENTITE##CreateSucess=##ENTITE## ajouté avec succès.";
	private static String MSG_FR_CREATE_ERROR="##ENTITE##CreateError=Erreur lors de l'ajout";
	
	private static String MSG_FR_Edit_SUCCESS="##ENTITE##EditSucess=##ENTITE## mdifié avec succès.";
	private static String MSG_FR_Edit_ERROR="##ENTITE##EditError=Erreur lors de la modification";
	
	private static String MSG_FR_Delete_SUCCESS="##ENTITE##DeleteSucess=##ENTITE## supprimé avec succès.";
	private static String MSG_FR_Delete_ERROR="##ENTITE##DeleteError=Erreur lors de la suppression";
	
	private static String MSG_FR_TITLE="##ENTITE##Title=Gestion des ##ENTITE_MINISCULE##s";
	
	private static String MSG_FR_LIST_TITLE="List##ENTITE##Title=Liste des ##ENTITE_MINISCULE##s";
	private static String MSG_FR_LIST_EMPTY="List##ENTITE##Empty=(Aucun élément trouvé dans la base de données)";
	
	private static String MSG_FR_CREATE_TITLE="Create##ENTITE##Title=Ajouter un ##ENTITE_MINISCULE##";
	
	private static String MSG_FR_EDIT_TITLE="Edit##ENTITE##Title=Modifier un ##ENTITE_MINISCULE##";
	
	private static String MSG_FR_VIEW_TITLE="View##ENTITE##Title=Modifier un ##ENTITE_MINISCULE##";
	
	private static String MSG_FR_ATTRIBUTE_LABEL="##ENTITE##Label_##ATTRIBUT##=##ATTRIBUT_CAPITAL##";
	
	private static String MSG_FR_ATTRIBUTE_REQUIRED="##ENTITE##RequiredMessage_##ATTRIBUT##=* Champ obligatoire: ##ATTRIBUT_CAPITAL##.";
	

}
