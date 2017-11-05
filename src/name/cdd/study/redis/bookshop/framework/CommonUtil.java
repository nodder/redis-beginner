package name.cdd.study.redis.bookshop.framework;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil
{
    private CommonUtil() {}
    
    public static long toTimeLong(String dateStr)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            return sdf.parse(dateStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date(0L).getTime();
        }
    }
    
    public static String toDateStr(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
        return sdf.format(date);
    }
}
