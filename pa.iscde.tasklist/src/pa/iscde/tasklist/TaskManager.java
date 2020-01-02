package pa.iscde.tasklist;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import org.eclipse.jface.text.MultiStringMatcher.Match;

public class TaskManager {

	static class Match {
		int start;
		String text;
	}

	private Set<Task> tasks = new HashSet<Task>();

	static List<Match> commentMatches = new ArrayList<Match>();

	static List<String> comments = new ArrayList<String>();

	// Metodo para encontrar os comentários e devolver as tasks
	public void findComments(String s) {
		String text = s;

		Pattern commentsPattern = Pattern.compile("(//.*?$)|(/\\*.*?\\*/)", Pattern.MULTILINE | Pattern.DOTALL);
		Pattern stringsPattern = Pattern.compile("(\".*?(?<!\\\\)\")");

		Matcher commentsMatcher = commentsPattern.matcher(text);
		while (commentsMatcher.find()) {
			Match match = new Match();
			match.start = commentsMatcher.start();
			match.text = commentsMatcher.group();
			commentMatches.add(match);
		}

		List<Match> commentsList = new ArrayList<Match>();

		Matcher stringsMatcher = stringsPattern.matcher(text);
		while (stringsMatcher.find()) {
			for (Match comment : commentMatches) {
				if (comment.start > stringsMatcher.start() && comment.start < stringsMatcher.end())
					commentsList.add(comment);
			}
		}
		for (Match comment : commentsList)
			commentMatches.remove(comment);

		for (Match comment : commentMatches) {
			comments.add(comment.text);
			// text = text.replace(comment.text, " ");

		}

		for (String comment : comments) {

			System.out.println(comment);
			/*
			List<String> tokenList = extractTokens(comment, "TODO")
			
			
			for (String token : tokenList) {

				System.out.println(token);
			}
			*/
		}
		
		extractTokens();
		


	}

	public void extractTokens() {

		String text = "I TODO come and meet you at JOSEFININHA and all the woods";
			List<String> tokens = new ArrayList<String>();
		tokens.add("TODO");
		tokens.add("JOSEFININHA");

		String patternString = "\\b(" + String.join("|", tokens) + ")\\b";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(text);

		while (matcher.find()) {
		    System.out.println(matcher.group(1));
		}
	}


}
