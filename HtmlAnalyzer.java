import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Deque;

public class HtmlAnalyzer {

    // Regular expressions to match open and close tags
    private static final String OPEN_TAG_REGEX = "<[a-zA-Z]+>";
    private static final String CLOSE_TAG_REGEX = "</[a-zA-Z]+>";

    public static void main(String[] args) throws Exception {
        
        // Check if the user has provided the URL
        if(args.length != 1) {
            System.out.println("Usage: java HtmlAnalyzer <URL>");
            return;
        }

        String urlStr = args[0];

        try {
            String result = extractDeepestText(urlStr);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    /*
     * This method will return the deepest text in the HTML document
     * 
     * @param urlString The URL of the HTML document.
     * @return The deepest text in the HTML document or "malformed HTML" if there are unclosed tags in the document.
     * @throws Exception If an error occurs while reading the document.
     */
    public static String extractDeepestText(String urlString) throws Exception {

        URL url = new URL(urlString);

        // Create a BufferedReader to read the document and a Stack to control the open tags
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        Deque<String> tagStack = new ArrayDeque<>();        
        
        String line;
        int maxDepth = -1; // Variable to store the maximum depth, used to return the deepest text
        String deepestText = ""; // Variable to store the deepest text of the document

        while((line = reader.readLine()) != null) {
            line = line.trim(); // Remove leading and trailing whitespaces
            if(line.isEmpty()) continue;

            if (line.matches(OPEN_TAG_REGEX)){
                String tag = extractTagName(line);
                tagStack.push(tag);
            } else if(line.matches(CLOSE_TAG_REGEX)) {
                String tag = extractTagName(line);
                if(tagStack.isEmpty() || !tagStack.peek().equals(tag)) {
                    reader.close();
                    return "malformed HTML";
                }
                tagStack.pop();
            } else {
                int depth = tagStack.size();
                if(depth > maxDepth) {
                    maxDepth = depth;
                    deepestText = line;
                }
            }
        }
        reader.close();

        // If the tagStack is not empty, the HTML is malformed because there are unclosed tags
        if(!tagStack.isEmpty()) return "malformed HTML";

        return deepestText;
    }

    /*
     * This method will extract the tag name from a tag, removing the '<', '</' and '>' characters to return just the name.
     * 
     * @param tag The tag to extract the name.
     * @return The name of the tag.
     */
    private static String extractTagName(String tag){
        return tag.replaceAll("[</>]", "");
    }
}
