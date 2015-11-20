package ma.kapisoft.gjurd.excel;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import ma.kapisoft.gjurd.controller.AbstractController;
import ma.kapisoft.gjurd.dao.AbstractDao;
import ma.kapisoft.gjurd.dao.CompteUtilisateurDao;
import ma.kapisoft.gjurd.entities.CompteUtilisateur;
import ma.kapisoft.gjurd.util.StringUtil;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;















import com.khalid.generateur.Generateur;


public abstract class ManipExcel<T> {
	private static final Log log = LogFactory.getLog(ManipExcel.class);
	
	private static final String LABEL_ATTRIBUTE="#ENTITY#Label_#ATTRIBUTE#";
	private static final String PATTERN_ATTRIBUTE="#ATTRIBUTE#";
	private static final String PATTERN_ENTITY="#ENTITY#";
    private static final String FORMAT_DATE_FR="d/m/yyyy";
    private static final String FORMAT_DATE_EN="m/d/yyyy";
    
	protected static final String CELL_NULL="CELL_NULL";
	protected static final String ERROR_READ_CELL="ERROR_READ_CELL";
	protected static final String ERROR_OBJECT_INEXISTANT="ERROR_OBJECT_INEXISTANT";
	
	
	private Class<T> itemClass;
	private int numFirstLine=0;
	private int numFirstColumn=0;
    
    private XSSFCellStyle cellDateStyle=null;
    private XSSFCellStyle cellNumberStyle=null;
    private XSSFCellStyle cellBooleanStyle=null;
    private XSSFCellStyle cellStringStyle=null;
    private XSSFCellStyle cellBlankStyle=null;

	public ManipExcel(Class<T> itemClass) {
		// TODO Auto-generated constructor stub
		this.itemClass=itemClass;
	}
	
	//getters && setters
	

	public int getNumFirstLine() {
		return numFirstLine;
	}

	public void setNumFirstLine(int numFirstLine) {
		this.numFirstLine = numFirstLine;
	}

	public int getNumFirstColumn() {
		return numFirstColumn;
	}

	public void setNumFirstColumn(int numFirstColumn) {
		this.numFirstColumn = numFirstColumn;
	}
	
	
	 public void importSheet(XSSFWorkbook workbook,String name,Map<String,String> atributesForeignKeys,List<MessageExcel> msgs)
	    {
		 importSheet(workbook, name, atributesForeignKeys, msgs,false);
	    }
	
	 public void importSheet(XSSFWorkbook workbook,String name,Map<String,String> atributesForeignKeys,List<MessageExcel> msgs,boolean addId)
	    {
		 List<String> attributes = getAttributs(addId);
		 importSheet(workbook, name, attributes, atributesForeignKeys, msgs);
	    }
	
    public void importSheet(XSSFWorkbook workbook,String name,List<String> attributes,Map<String,String> atributesForeignKeys,List<MessageExcel> msgs)
    {
    	
    	XSSFSheet sheet = workbook.getSheet(name);
    	XSSFRow row; 
    	XSSFCell cell;

    	Iterator rows = sheet.rowIterator();
    	T t;
    	rows.next();
    	 while (rows.hasNext())
    		{
    		 row=(XSSFRow) rows.next();
    		 	
    		 
    	    	boolean error=false;
    	    	t=rechercher(row);
    	    	if(t==null)
    	    	{
    	    		t=instancier();
    	    		for(int i=0;(i<attributes.size()&&!error);i++)
    	    		{
    	    			error=!readCell(row.getCell(i),attributes.get(i),t,msgs,row.getRowNum());
    	    				
    	    		}
    	    		if(!error)
    	    			add(t,msgs,row.getRowNum());
    	    		
    	    	}else
    	    	{
    	    		for(int i=0;(i<attributes.size()&&!error);i++)
    	    		{
    	    			error=!readCell(row.getCell(i),attributes.get(i),t,msgs,row.getRowNum());
    	    			
    	    		}
    	    		if(!error)
    	    			edit(t,msgs,row.getRowNum());
    	    	}
    	    	
    		}
	
        
    }
	public abstract AbstractDao<T> getDao(); 
     
  protected void add(T t, List<MessageExcel> msgs,int rownum) {
		
		try{
			getDao().add(t);
		}catch(Exception  me)
		{
			log.warn(me.getMessage());
			MessageExcel msg=new MessageExcel(rownum,itemClass.getSimpleName()+"CreateError");
			
			msgs.add(msg);
		}
	}

		protected void edit(T t, List<MessageExcel> msgs, int rownum) {
		// TODO Auto-generated method stub
		try{
			getDao().modify(t);
		}catch(Exception  me)
		{
			log.warn(me.getMessage());
			MessageExcel msg=new MessageExcel(rownum,itemClass.getSimpleName()+"EditError");
			
			msgs.add(msg);
		}
	}


	protected abstract boolean readCell(XSSFCell cell, String string, T t, List<MessageExcel> msgs,int rownum);
	protected boolean readStringNotNull(XSSFCell cell, String attribute, T t,
			List<MessageExcel> msgs, int rownum)
	{
		try{
			if(cell==null||cell.getStringCellValue().trim().equals(""))
			{
				MessageExcel msg=new MessageExcel(rownum,CELL_NULL);
				String[] params={getLabelAttribute(attribute)};
				msg.setParemetres(params);
				msgs.add(msg);
				return false;
			}else
			{
				runSetter(attribute, t, cell.getStringCellValue().trim());
			}
		}catch(Exception e)
		{
			MessageExcel msg=new MessageExcel(rownum,ERROR_READ_CELL);
			String[] params={getLabelAttribute(attribute)};
			msg.setParemetres(params);
			msgs.add(msg);
			return false;
		}
		
		return true;
	}
	
	protected boolean readStringNull(XSSFCell cell, String attribute, T t,
			List<MessageExcel> msgs, int rownum)
	{
		try{
			if(cell==null||cell.getStringCellValue().trim().equals(""))
			{
				runSetter(attribute, t, null);
			}else
			{
				runSetter(attribute, t, cell.getStringCellValue().trim());
			}
		}catch(Exception e)
		{
			MessageExcel msg=new MessageExcel(rownum,ERROR_READ_CELL);
			String[] params={getLabelAttribute(attribute)};
			msg.setParemetres(params);
			msgs.add(msg);
			return false;
		}
		
		return true;
	}
	protected boolean readBooleanNotNull(XSSFCell cell, String attribute, T t,
			List<MessageExcel> msgs, int rownum)
	{
		try{
			if(cell==null)
			{
				MessageExcel msg=new MessageExcel(rownum,CELL_NULL);
				String[] params={getLabelAttribute(attribute)};
				msg.setParemetres(params);
				msgs.add(msg);
				return false;
			}else
			{
				runSetter(attribute, t, cell.getBooleanCellValue());
			}
		}catch(Exception e)
		{
			MessageExcel msg=new MessageExcel(rownum,ERROR_READ_CELL);
			String[] params={getLabelAttribute(attribute)};
			msg.setParemetres(params);
			msgs.add(msg);
			return false;
		}
		
		return true;
	}
	protected boolean readBooleanNull(XSSFCell cell, String attribute, T t,
			List<MessageExcel> msgs, int rownum)
	{
		try{
			if(cell==null)
			{
				runSetter(attribute, t, null);
			}else
			{
				runSetter(attribute, t, cell.getBooleanCellValue());
			}
		}catch(Exception e)
		{
			MessageExcel msg=new MessageExcel(rownum,ERROR_READ_CELL);
			String[] params={getLabelAttribute(attribute)};
			msg.setParemetres(params);
			msgs.add(msg);
			return false;
		}
		
		return true;
	}
	
	protected boolean readNumberNotNull(XSSFCell cell, String attribute, T t,
			List<MessageExcel> msgs, int rownum)
	{
		try{
			if(cell==null)
			{
				MessageExcel msg=new MessageExcel(rownum,CELL_NULL);
				String[] params={getLabelAttribute(attribute)};
				msg.setParemetres(params);
				msgs.add(msg);
				return false;
			}else
			{
				runSetter(attribute, t, cell.getNumericCellValue());
			}
		}catch(Exception e)
		{
			MessageExcel msg=new MessageExcel(rownum,ERROR_READ_CELL);
			String[] params={getLabelAttribute(attribute)};
			msg.setParemetres(params);
			msgs.add(msg);
			return false;
		}
		
		return true;
	}
	protected boolean readNumberNull(XSSFCell cell, String attribute, T t,
			List<MessageExcel> msgs, int rownum)
	{
		try{
			if(cell==null)
			{
				runSetter(attribute, t, null);
			}else
			{
				runSetter(attribute, t, cell.getNumericCellValue());
			}
		}catch(Exception e)
		{
			MessageExcel msg=new MessageExcel(rownum,ERROR_READ_CELL);
			String[] params={getLabelAttribute(attribute)};
			msg.setParemetres(params);
			msgs.add(msg);
			return false;
		}
		
		return true;
	}
    
	protected boolean readDateNotNull(XSSFCell cell, String attribute, T t,
			List<MessageExcel> msgs, int rownum)
	{
		try{
			if(cell==null)
			{
				MessageExcel msg=new MessageExcel(rownum,CELL_NULL);
				String[] params={getLabelAttribute(attribute)};
				msg.setParemetres(params);
				msgs.add(msg);
				return false;
			}else
			{
				runSetter(attribute, t, cell.getDateCellValue());
			}
		}catch(Exception e)
		{
			MessageExcel msg=new MessageExcel(rownum,ERROR_READ_CELL);
			String[] params={getLabelAttribute(attribute)};
			msg.setParemetres(params);
			msgs.add(msg);
			return false;
		}
		
		return true;
	}
	protected boolean readDateNull(XSSFCell cell, String attribute, T t,
			List<MessageExcel> msgs, int rownum)
	{
		try{
			if(cell==null)
			{
				runSetter(attribute, t, null);
			}else
			{
				runSetter(attribute, t, cell.getDateCellValue());
			}
		}catch(Exception e)
		{
			MessageExcel msg=new MessageExcel(rownum,ERROR_READ_CELL);
			String[] params={getLabelAttribute(attribute)};
			msg.setParemetres(params);
			msgs.add(msg);
			return false;
		}
		
		return true;
	}
	protected abstract T rechercher(XSSFRow row);
    protected  T instancier()
    {
    	try {
			return itemClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
   
	public XSSFWorkbook getWorkbook(String name,List<T> list,Map<String,String> atributesForeignKeys,boolean addId)
	{
		
		List<String> attributes = getAttributs(addId);
		return getWorkbook(name, list, attributes, atributesForeignKeys);
	}
	
	public XSSFWorkbook getWorkbook(String name,List<T> list,Map<String,String> atributesForeignKeys)
	{
		
		return getWorkbook(name, list, atributesForeignKeys,false);
	}
	
	public XSSFWorkbook getWorkbook(String name,List<T> list,List<String> attributes,Map<String,String> atributesForeignKeys)
	{
		XSSFWorkbook workbook = new XSSFWorkbook(); 
		creatSheet(workbook, name, list, attributes,atributesForeignKeys);
		return workbook;
	}
	public void creatSheet(XSSFWorkbook workbook,String name,List<T> list,Map<String,String> atributesForeignKeys,boolean addId)
	{
		List<String> attributes = getAttributs(addId);
		creatSheet(workbook,name,list,attributes,atributesForeignKeys);
	}
	public void creatSheet(XSSFWorkbook workbook,String name,List<T> list,Map<String,String> atributesForeignKeys)
	{
			creatSheet(workbook,name,list,atributesForeignKeys,false);
	}
	public void creatSheet(XSSFWorkbook workbook,String name,List<T> list,List<String> attributes,Map<String,String> atributesForeignKeys)
	{
		XSSFSheet sheet = workbook.createSheet(name);
		Row row = sheet.createRow(numFirstLine);
		XSSFCellStyle styleHeader=getStyleHeader(workbook);
		int column=numFirstColumn;
		for(String att:attributes)
		{
			Cell cell = row.createCell(column);
		    cell.setCellStyle(styleHeader);
		    cell.setCellValue(getLabelAttribute(att));
		    column++;
		    
		}
		
	     int line =numFirstLine+1;
	    
	     
	     XSSFCellStyle style=getStyleCell(workbook);
	     
		    
		    for (T t:list)
		    {
		    	row = sheet.createRow(line++);
		        column=numFirstColumn;
				for(String att:attributes)
				{
					Cell cell = row.createCell(column);
				   // cell.setCellStyle(style);
				    assignValueToCell(workbook,cell, att, t,atributesForeignKeys);
				    column++;
				    
				}
		        
		      
		    }
		autoSizeColumn(sheet, attributes.size());
	}
	
	protected XSSFCellStyle getStyleHeader(XSSFWorkbook workbook)
	{
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(255,242,0)));
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		return cellStyle;
	}
	
	protected XSSFCellStyle getStyleCell(XSSFWorkbook workbook)
	{
		return null;
	}
    protected XSSFCellStyle getDateStyleCell(XSSFWorkbook workbook)
	{
        if(cellDateStyle==null)
        {
            cellDateStyle = workbook.createCellStyle();
           cellDateStyle.setFillForegroundColor(new XSSFColor(new Color(255,255,255)));
            cellDateStyle.setFillPattern(cellDateStyle.SOLID_FOREGROUND);
            CreationHelper createHelper = workbook.getCreationHelper();
            cellDateStyle.setDataFormat(createHelper.createDataFormat().getFormat(getFormatDate()));
            
		}
        return cellDateStyle;
	}
    private void autoSizeColumn(XSSFSheet sheet,int nbrcolumn)
    {
    	for (int i=0; i<nbrcolumn; i++){
    		sheet.autoSizeColumn(i);
    		}
    }
    
      protected XSSFCellStyle getNumberStyleCell(XSSFWorkbook workbook)
	{
        if(cellNumberStyle==null)
        {
            cellNumberStyle = workbook.createCellStyle();
            cellNumberStyle.setFillForegroundColor(new XSSFColor(new Color(255,255,255)));
               cellNumberStyle.setFillPattern(cellNumberStyle.SOLID_FOREGROUND);
		}
        return cellNumberStyle;
	}
    protected XSSFCellStyle getBooleanStyleCell(XSSFWorkbook workbook)
	{
        if(cellBooleanStyle==null)
        {
            cellBooleanStyle = workbook.createCellStyle();
            cellBooleanStyle.setFillForegroundColor(new XSSFColor(new Color(255,255,255)));
                 cellBooleanStyle.setFillPattern(cellBooleanStyle.SOLID_FOREGROUND);
		}
        return cellBooleanStyle;
	}
    
    protected XSSFCellStyle getStringStyleCell(XSSFWorkbook workbook)
	{
        if(cellStringStyle==null)
        {
            cellStringStyle = workbook.createCellStyle();
            cellStringStyle.setFillForegroundColor(new XSSFColor(new Color(255,255,255)));
               cellStringStyle.setFillPattern(cellStringStyle.SOLID_FOREGROUND);
		}
        return cellStringStyle;
	}

    protected XSSFCellStyle getBlankStyleCell(XSSFWorkbook workbook)
	{
        if(cellBlankStyle==null)
        {
            cellBlankStyle = workbook.createCellStyle();
            cellBlankStyle.setFillForegroundColor(new XSSFColor(new Color(255,255,255)));
              cellBlankStyle.setFillPattern(cellBlankStyle.SOLID_FOREGROUND);
		}
        return cellBlankStyle;
	}        
  
    protected String getFormatDate()
    {
        return FORMAT_DATE_FR;
    }
    
	protected String getLabelAttribute(String attr)
	{
		return ResourceBundle.getBundle("/Bundle").getString(LABEL_ATTRIBUTE.replaceAll(PATTERN_ENTITY,itemClass.getSimpleName()).replaceAll(PATTERN_ATTRIBUTE, attr));
	}
	
	private Object getValueAttribute(String attr,T t)
	{
		return runGetter(attr, t);
	}
	
	protected void assignValueToCell(XSSFWorkbook workbook,Cell cell,String attr,T t,Map<String,String> atributesForeignKeys)
	{
		
		try{
			Object obj= getValueAttribute(attr,t);
			if(obj==null)
			{
				assignNullToCell(workbook,cell);
			}
			else if(obj instanceof String)
			{
				assignStringToCell(workbook,cell, (String)obj);
			}else if(obj instanceof Boolean)
			{
				assignBooleanToCell(workbook,cell, (Boolean)obj);
			}else if(obj instanceof Double)
			{
				assignDoubleToCell(workbook,cell, (Double)obj);
			}else if(obj instanceof Float)
			{
				assignFloatToCell(workbook,cell, (Float)obj);
			}else if(obj instanceof Integer)
			{
				assignIntegerToCell(workbook,cell, (Integer)obj);
			}else if(obj instanceof Date)
			{
				assignDateToCell(workbook,cell, (Date)obj);
			}else 
			{
				assignObjectToCell(workbook,cell,obj,atributesForeignKeys);
			}
			
			
		}catch(Exception e)
		{
			log.error("assignValueToCell :", e);
		}
	}
	
	
	protected void assignNullToCell(XSSFWorkbook workbook,Cell cell) {
		// TODO Auto-generated method stub
		cell.setCellType(XSSFCell.CELL_TYPE_BLANK);
		cell.setCellStyle(getBlankStyleCell(workbook));
	}

	protected void assignStringToCell(XSSFWorkbook workbook,Cell cell,String value)
	{
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
        cell.setCellStyle(getStringStyleCell(workbook));
		cell.setCellValue(value);
	}
	protected void assignBooleanToCell(XSSFWorkbook workbook,Cell cell,Boolean value)
	{
		cell.setCellType(XSSFCell.CELL_TYPE_BOOLEAN);
        cell.setCellStyle(getBooleanStyleCell(workbook));
		cell.setCellValue(value);
	}
	protected void assignDoubleToCell(XSSFWorkbook workbook,Cell cell,Double value)
	{
        cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
        cell.setCellStyle(getNumberStyleCell(workbook));
		cell.setCellValue(value);
	}
	protected void assignFloatToCell(XSSFWorkbook workbook,Cell cell,Float value)
	{
        cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
        cell.setCellStyle(getNumberStyleCell(workbook));
		cell.setCellValue(value);
	}
	protected void assignIntegerToCell(XSSFWorkbook workbook,Cell cell,Integer value)
	{
		cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
        cell.setCellStyle(getNumberStyleCell(workbook));
		cell.setCellValue(value);
	}
	
	protected void assignDateToCell(XSSFWorkbook workbook,Cell cell,Date value)
	{
		cell.setCellStyle(getDateStyleCell(workbook));
		cell.setCellValue(value);
	}
	
	protected void assignObjectToCell(XSSFWorkbook workbook,Cell cell,Object value,Map<String,String> atributesForeignKeys)
	{
		if(atributesForeignKeys!=null)
		{
			try{
			  String className=	value.getClass().getCanonicalName();
			  Class classForeignKey=value.getClass();
			  String classForeignKeyName=StringUtil.lastStringAfterSeparator(className, ".");
			  String attribute=atributesForeignKeys.get(classForeignKeyName);
			  Object obj=runGetter(classForeignKey, attribute, value);
				if(obj==null)
				{
					assignNullToCell(workbook,cell);
				}
				else if(obj instanceof String)
				{
					assignStringToCell(workbook,cell, (String)obj);
				}else if(obj instanceof Boolean)
				{
					assignBooleanToCell(workbook,cell, (Boolean)obj);
				}else if(obj instanceof Double)
				{
					assignDoubleToCell(workbook,cell, (Double)obj);
				}else if(obj instanceof Float)
				{
					assignFloatToCell(workbook,cell, (Float)obj);
				}else if(obj instanceof Integer)
				{
					assignIntegerToCell(workbook,cell, (Integer)obj);
				}else if(obj instanceof Date)
				{
					assignDateToCell(workbook,cell, (Date)obj);
				}
				else
				{
					assignNullToCell(workbook,cell);
				}
			}catch(Exception e)
			{
				log.error("assignObjectToCell", e);
			}
				
		}
		else
		{
			cell.setCellValue("");
		}
	}
	
	
	protected  Object runGetter(Class cl,String field, Object o)
	{
		try{
			Method method=	cl.getMethod("get"+StringUtil.capitalise(field), null);
			try
            {
                return method.invoke(o);
            }
            catch (IllegalAccessException e)
            {
                log.fatal("Could not determine method: " + method.getName());
            }
            catch (InvocationTargetException e)
            {
                log.fatal("Could not determine method: " + method.getName());
            }
		}catch(Exception e)
		{
			log.error("runGetter", e);
		}
		
		


	    return null;

	}
	protected  void runSetter(String field, T o,Object value)
	{
		try {
			BeanUtils.setProperty(o,field,value);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//runSetter(itemClass, field, o, value);
	}
	protected  void runSetter(Class cl,String field, Object o,Object value)
	{
		try{
			Method method=	cl.getMethod("set"+StringUtil.capitalise(field), null);
			try
            {
                 method.invoke(o,value);
            }
            catch (IllegalAccessException e)
            {
                log.fatal("Could not determine method: " + method.getName());
            }
            catch (InvocationTargetException e)
            {
                log.fatal("Could not determine method: " + method.getName());
            }
		}catch(Exception e)
		{
			log.error("runGetter", e);
		}

	}
	
	
	protected  Object runGetter(String field, T o)
	{
	  return runGetter(itemClass, field, o);
    }
	
	
	private List<String>  getAttributs(boolean addId)
	{
	 List<String> list=new ArrayList<String>();
		
		for(Field f:itemClass.getDeclaredFields())
		{
			String attribute=f.getName();
			if(!attribute.equals("serialVersionUID"))
		    {
				if(!attribute.equals("id") || addId)
				{
					list.add(attribute);
				}
				
		    }
		}
			
		
	 return list;
	}
	


}
