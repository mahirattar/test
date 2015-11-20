package ma.kapisoft.gjurd.excel;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageExcel {
	
	public final static int WARN=1;
	public final static int INFO=0;
	public final static int ERROR=2;
			
	
	int type=2;
	int rownum;
	String msg;
	private Object[] paremetres;
	
	
	
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Object[] getParemetres() {
		return paremetres;
	}
	public void setParemetres(Object[] paremetres) {
		this.paremetres = paremetres;
	}
	
	public MessageExcel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MessageExcel(int rownum, String msg) {
		super();
		this.rownum = rownum;
		this.msg = msg;
	}
	


}
