import org.jsoup.*;
import org.jsoup.nodes.Document;

/**
 * The ParsingURL class takes in URL given by the user, connect via HTTP using the Jsoup library to fetch the contents of the webpage, and construct a 
 * corpus of text that lives on that webpage. This corpus contains the title of the webpage, and all the text in the body. 
 * 
 * We prune out non-alphanumeric characters because they are uninteresting in terms of getting any relevant information. We also prune
 * words that have 's in the end. This is so that words like Dawson and Dawson’s are treated in the same way. 
 * 
 * @author Priya Dhandev
 *
 */

public class ParsingURL {
	private String body;
	private String title;
	
    public ParsingURL(String url) throws Exception {
    	
        // Note: Using Chrome user agent by default. This is so that we can avoid the 503 type HTTP errors.
        /*
        * http://stackoverflow.com/questions/17031003/jsoup-throws-url-status-503-in-eclipse-but-url-works-fine-in-browser
        * */
        Document doc = Jsoup.connect(url).userAgent("Chrome/*").get();


        this.title = this.preprocessString(doc.title());				// getting the title from HTML
        this.body = this.preprocessString(doc.body()                 // getting the body from HTML
                								.text() );             // getting the text from the body HTML tag

    }
    
    private String preprocessString(String str) {
    	return str
    			.toLowerCase()                               // convert everything to lowercase
    			.replaceAll("[^\\w\\s\\d@#-']", "")          // regex filter to match only desired characters
    			.replaceAll("'s","")                        // regex filter to remove words that have 's. This is so that Priya's and Priya are treated as the same word.
    			.replaceAll("&", "");
    }
    
    public String getBody(){
    	return body;
    }

	public String getTitle() {
		return title;
	}

    
    
}