package carrie.util;

import java.sql.Timestamp;
import java.util.Date;

public class DateUtil {
	
	public static Timestamp d2t(Date d) {
		return new Timestamp(d.getTime());
	}
	
	public static Date t2d(Timestamp t) {
		return new Date(t.getTime());
	}
}
