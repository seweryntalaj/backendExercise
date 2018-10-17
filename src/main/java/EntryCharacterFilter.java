import java.lang.String;

public class EntryCharacterFilter{

    public static String filterCharacters(String entryMessage){
        String modifiedMsg = entryMessage.replaceAll("\"/[^(\\d)(\\w)., `%/()]\"", "_"); //
        return modifiedMsg;
    }

    public static boolean isMessageTooLong(String modifiedMsg){
        if(modifiedMsg.length() > 40){return true;}
        else{return false;}
    }


    public static boolean checkForWhitespace(String modifiedMsg){
        boolean containsWhitespace = false;
        int whitespaceIndex;
        whitespaceIndex = modifiedMsg.indexOf("\\s");
        if(whitespaceIndex != -1){
            containsWhitespace = true;
        }
        return containsWhitespace;
    }


    public static String breakMessage(String modifiedMsg){
        if(isMessageTooLong(modifiedMsg) && (!checkForWhitespace(modifiedMsg))){
            StringBuilder brokenMessageBuilder = new StringBuilder(modifiedMsg);
            System.out.println(brokenMessageBuilder.toString());
            brokenMessageBuilder.insert(30, "<br>");
            System.out.println(brokenMessageBuilder.toString());
            String brokenMessage = brokenMessageBuilder.toString();
            return brokenMessage;
        }else return modifiedMsg;
    }



}