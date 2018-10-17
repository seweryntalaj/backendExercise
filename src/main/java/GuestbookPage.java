import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class GuestbookPage implements HttpHandler {
    private final String filePath = "src/main/resources/guestbook.txt";

    Guestbook guestbook = new Guestbook();
    List<GuestbookEntry> entries = guestbook.getEntries();

    FileParser fileParser = new FileParser();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {


        String response = "";
        String method = httpExchange.getRequestMethod();

        // Send a form if it wasn't submitted yet.
        if(method.equals("GET")){
            response = fileParser.getFile(filePath);
            guestbook.importList(entries);
        }

        // If the form was submitted, retrieve it's content.
        if(method.equals("POST")){
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            StringBuilder sb = new StringBuilder();

            System.out.println(formData);

            Map inputs = parseFormData(formData);

            String name = inputs.get("name").toString();
            String date = EntryDate.generateDate();
            String message = inputs.get("message").toString();
            String filteredMsg = EntryCharacterFilter.filterCharacters(message);
            GuestbookEntry newEntry = new GuestbookEntry(name, date, filteredMsg);
            entries.add(newEntry);
            guestbook.importList(entries);
            sb.append(
                    "<html><body>" +
                            "<table border="+"1"+">"+
                            "<tr>"+
                            " <th>Name:</th>"+
                            "<th>Date:</th>"+
                            "<th>Message</th>"+
                            "</tr>");


            for (GuestbookEntry entry : entries){
                sb.append(
                        "<tr>"+
                                "<td>"+ entry.getName() + "</td>"+
                                "<td>"+ entry.getDate() + "</td>"+
                                "<td>"+ entry.getMessage() + "</td>"+
                                "</tr>"
                );
            }
            sb.append("</body></html>");
            response = sb.toString();


        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    /**
     * GuestbookPage data is sent as a urlencoded string. Thus we have to parse this string to get data that we want.
     * See: https://en.wikipedia.org/wiki/POST_(HTTP)
     */
    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<String, String>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }
}
