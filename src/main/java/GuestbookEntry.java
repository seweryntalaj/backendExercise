import java.text.SimpleDateFormat;

public class GuestbookEntry{

    private String name;
    private String date;
    private String message;

    public GuestbookEntry(String name, String date, String message){
        this.name = name;
        this.date = date;
        this.message = message;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = EntryCharacterFilter.breakMessage(message);
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = EntryDate.generateDate();
    }
}