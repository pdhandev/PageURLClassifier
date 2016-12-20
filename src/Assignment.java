import java.util.HashMap;
import java.util.Iterator;

public class Assignment {

	public static void main(String[] args) {
		if(args.length==0){
			System.out.println("No URL provided!! Please provide a URL.");
			return;
		}
		
		try{
			ParsingURL data = new ParsingURL(args[0]);

			WordCount bodycount = new WordCount(data.getBody());
			WordCount titlecount = new WordCount(data.getTitle());

			HashMap<String, Integer> bodymap = bodycount.wordCount();
			HashMap<String, Integer> titlemap = titlecount.wordCount();
			KeywordAnalysis keyanalyse = new KeywordAnalysis(bodymap, titlemap, data.getBody());

			Iterator<String> keyiter = keyanalyse.keywords.iterator();
			System.out.println("List of Keywords - ");
			while (keyiter.hasNext()) {
				System.out.println(keyiter.next());
			}

		}
		catch(Exception e ){
			System.out.println("Invalid URL or Connection not Found !!");
			e.printStackTrace();
		}


	}

}
