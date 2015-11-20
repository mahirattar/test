package ma.kapisoft.gjurd.util;

public class StringUtil {

	public StringUtil() {
		// TODO Auto-generated constructor stub
	}

	public static String capitalise(String ch)
	{
		return	ch.substring(0, 1).toUpperCase() + ch.substring(1);
	}
	
	public static String notcapitalise(String ch)
	{
	return	ch.substring(0, 1).toLowerCase() + ch.substring(1);
	}
	
	public static String lastStringAfterSeparator(String ch,String separator)
	{
		int lastseparator= ch.lastIndexOf(separator);
		if(lastseparator!=-1)
			return ch.substring(ch.lastIndexOf(separator)+1);
		return "";
	}
	 public static String getFileExtension(String NomFichier) {

		    int posPoint = NomFichier.lastIndexOf('.');
		    if (0 < posPoint && posPoint <= NomFichier.length() - 2 ) {
		        return NomFichier.substring(posPoint + 1);
		    }
		    return "";
		    }
	
	
}
