package ma.kapisoft.gjurd.test.excel;

import java.io.InputStream;
import java.util.List;

import ma.kapisoft.gjurd.excel.MessageExcel;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface IExcelService {
	
	public XSSFWorkbook getExcelUsers();
	public List<MessageExcel> importerExcel(InputStream input);
	

}
