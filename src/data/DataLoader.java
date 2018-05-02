package data;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataLoader {
	
	
	public static ArrayList<Entity> load(String fileName) throws IOException {	
		
		FileInputStream fstream = new FileInputStream(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		ArrayList<Entity> entities = new ArrayList<>();
		
		String line;
		br.readLine();
		
		while ((line = br.readLine()) != null)   {
			Entity entity = parseLine(line);
			entities.add(entity);
		}
		
		br.close();
		
		return entities;
	}
	
	public static Entity parseLine(String line) {
		
		String matchIdR = "^(?<matchId>'[\\w\\d:]*')";
	    String d1="\\|";	
	    String marketIdR="(?<marketId>[^\\|]*)";
	    String d2="\\|";
	    String outputIdR="(?<outputId>[^\\|]*)";
	    String d3="\\|";
	    String specR="(?<spec>.*)$";

	    Pattern p = Pattern.compile(matchIdR + d1 + marketIdR + d2 + outputIdR + d3 + specR,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	    Matcher m = p.matcher(line);

	    if (m.find(0))
	    {	        
	        String matchId = m.group("matchId");
	        int marketId = Integer.valueOf(m.group("marketId"));
	        String outputId = m.group("outputId");
	        String spec = m.group("spec");
	        
	        Entity entity = new Entity(matchId, marketId, outputId, spec);
	        
	        return entity;
	    }

	    return null;
	}
}
