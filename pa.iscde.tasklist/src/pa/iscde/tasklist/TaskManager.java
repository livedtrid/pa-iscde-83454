package pa.iscde.tasklist;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pa.iscde.tasklist.internal.Comment;

public class TaskManager {

	static class Match {
		int start;
		String text;
	}

	static List<Match> commentMatches = new ArrayList<Match>();

	static List<Integer> commentLines = new ArrayList<Integer>();

	static List<Comment> comments = new ArrayList<Comment>();

	/**
	 * Get the document line number of a given string
	 * https://stackoverflow.com/questions/7871007/get-line-number-within-matcher-find-using-matcher-start/26314471
	 * 
	 * @param data
	 * @param start
	 * @return the line number
	 */
	static int getLine(String data, int start) {
		int line = 1;
		Pattern pattern = Pattern.compile("\n");
		Matcher matcher = pattern.matcher(data);
		matcher.region(0, start);
		while (matcher.find()) {
			line++;
		}
		return (line);
	}

	private Set<Task> tasks = new HashSet<Task>();

	/**
	 * Find strings with the given tokens
	 * https://inneka.com/programming/java/how-to-find-a-whole-word-in-a-string-in-java/
	 * 
	 * @param tokens List of tokens
	 * @param text   filtered comment String
	 * @return All text after the token
	 */
	public String extractTokens(List<String> tokens, String text) {

		String value = "";
		String patternString = "\\b(" + String.join("|", tokens) + ")\\b";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(text);

		while (matcher.find()) {
			value = matcher.group(1);
			System.out.println(value);
		}

		return value;
	}

	/**
	 * Filter strings with comments
	 * https://stackoverflow.com/questions/1657066/java-regular-expression-finding-comments-in-code/1740692
	 * 
	 * @param tokens List of tokens
	 * @param file the file where we want to find the comments
	 */	
	public void findComments(List<String> tokens, File file, String s) {

		String text = s;

		comments.clear();
		commentMatches.clear();

		Pattern commentsPattern = Pattern.compile("(//.*?$)|(/\\*.*?\\*/)", Pattern.MULTILINE | Pattern.DOTALL);
		Pattern stringsPattern = Pattern.compile("(\".*?(?<!\\\\)\")");

		Matcher commentsMatcher = commentsPattern.matcher(text);
		while (commentsMatcher.find()) {
			Match match = new Match();
			match.start = commentsMatcher.start();
			match.text = commentsMatcher.group();

			int line = getLine(text, commentsMatcher.start());
			commentLines.add(line);
			commentMatches.add(match);
		}

		List<Match> commentsList = new ArrayList<Match>();

		Matcher stringsMatcher = stringsPattern.matcher(text);
		while (stringsMatcher.find()) {
			for (Match comment : commentMatches) {
				if (comment.start > stringsMatcher.start() && comment.start < stringsMatcher.end()) {
					commentsList.add(comment);
				}
			}
		}

		for (Match commentMatch : commentsList)
			commentMatches.remove(commentMatch);

		for (Match commentMatch : commentMatches) {

			String commentText = commentMatch.text.replaceAll("(//)|(/*)(//*)|(/*)(\\*)|(/*)", "");
			Comment comment = new Comment(commentText, commentMatch.start);
			comments.add(comment);

		}

		for (int i = 0; i < comments.size(); i++) {

			if (extractTokens(tokens, comments.get(i).getText()) != "") {
				UUID id = UUID.randomUUID();
				tasks.add(new Task(id, "", comments.get(i).getText(), file.getName(), file, commentLines.get(i),
						comments.get(i).getOffset() + 2 /* To compensate the 2 first comment characters that was removed before*/));
			}
		}
	}

	/**
	 * Getter for all the tasks found
	 * 
	 * @return Set of Tasks
	 */
	public Set<Task> getTasks() {
		return tasks;
	}

}
