package pa.iscde.tasklist;

import java.io.File;
import java.util.UUID;

public class Task {

	private UUID id;
	private String token;
	private String description;
	private String project;
	private File file;
	private int line;
	private int offset;

	/**
	 * Task Object
	 * 
	 * @param token
	 * @param description
	 * @param project
	 * @param file
	 * @param line
	 */
	public Task(UUID id, String token, String description, String project, File file, int line, int offset) {

		this.id = id;
		this.token = token;
		this.description = description;
		this.project = project;
		this.file = file;
		this.line = line;
		this.offset = offset;
	}

	/**
	 * Returns the task description
	 * 
	 * @return String description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * the java file where the task token were found
	 * 
	 * @return String file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * the line where the task token were found
	 * 
	 * @return Integer line
	 */
	public int getLine() {
		return line;
	}

	/**
	 * it was supposed to return the project name
	 * 
	 * @return String project
	 */
	public String getProject() {
		return project;
	}

	/**
	 * Get the token used to describe a task
	 * 
	 * @return String token
	 */
	public String getToken() {
		return token;
	}

	public int getOffset() {
		return offset;
	}

	public UUID getId() {
		return id;
	}

}