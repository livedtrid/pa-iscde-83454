package pa.iscde.tasklist;

public class Task {

	private String token;
	private String description;
	private String project;
	private String file;
	private int line;

	/**
	 *  Task Object
	 *  
	 * @param token
	 * @param description
	 * @param project
	 * @param file
	 * @param line
	 */
	public Task(String token, String description, String project, String file, int line) {

		this.token = token;
		this.description = description;
		this.project = project;
		this.file = file;
		this.line = line;
	}

	/**
	 * Returns the task description
	 * @return String description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * the java file where the task token were found
	 * @return String file
	 */
	public String getFile() {
		return file;
	}

	/**
	 * the line where the task token were found
	 * @return Integer line
	 */
	public int getLine() {
		return line;
	}

	/**
	 * it was supposed to return the project name
	 * @return String project
	 */
	public String getProject() {
		return project;
	}

	/**
	 * Get the token used to describe a task
	 * @return String token
	 */
	public String getToken() {
		return token;
	}

}