import java.util.*;
import java.io.*;

public class Guestbook{
    private List<GuestbookEntry> entries = new ArrayList<GuestbookEntry>();
    private final String filePath = "src/main/resources/entries.txt";
    private File entriesFile = new File(filePath);

    public Guestbook(){
        createInitialList();
    }

    @Deprecated
    public void addEntry(GuestbookEntry entry){
        entries.add(entry);
    }

    public List<GuestbookEntry> getEntries(){
        return entries;
    }

    @Deprecated
    public void displayEntries(){
        for(GuestbookEntry entry : entries){
            System.out.println("Name: " + entry.getName());
            System.out.println("Date: " + entry.getDate());
            System.out.println("Message: " + entry.getMessage());
            System.out.println("");
        }
    }

    @Deprecated
    public void updateList(List<GuestbookEntry> entries){
        this.entries = entries;
        importList(entries);
    }

    public void importList(List<GuestbookEntry> entries){
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(entriesFile));

            for (GuestbookEntry entry : entries) {
                String entryData = entry.getName() + "|" + entry.getDate() + "|" + entry.getMessage()+ "\n";
                writer.write(entryData);
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createInitialList(){

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(entriesFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] rawData = line.split("\\|");
                String name = rawData[0];
                String date = rawData[1];
                String message = rawData[2];
                GuestbookEntry entry = new GuestbookEntry(name, date, message);
                entries.add(entry);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}