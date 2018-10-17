import java.text.SimpleDateFormat;
import java.util.Date;

public class EntryDate{
    String date;

    public EntryDate(){
        date = generateDate();
    }

    private static String datePattern = "yyyy-MM-dd HH:mm:ss";

    public static String generateDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        String date = simpleDateFormat.format(new Date());
        return date;
    }

}