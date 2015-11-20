package ma.kapisoft.gjurd.util;

import java.util.Date;

public class DateUtil {

	final static long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;

	
	public DateUtil() {
		// TODO Auto-generated constructor stub
	}
	
	/*
	*Le nombre des jours entre deux dates
	*il peut etre negatif si d1 <d2
	*/
	public static int nbrDays(Date d1,Date d2)
	{
		
		int diffInDays = (int) ((d1.getTime() - d2.getTime())/ DAY_IN_MILLIS );
		return diffInDays;
	}

}
