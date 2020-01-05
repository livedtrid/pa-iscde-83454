package pa.iscde.tasklist;

public class Task {

	private String token;
	private String description;
	private String project;
	private String file;
	private int line;

	public Task(String token, String description, String project, String file, int line) {

		this.token = token;
		this.description = description;
		this.project = project;
		this.file = file;
		this.line = line;
	}

	public String getToken() {
		return token;
	}

	public String getDescription() {
		return description;
	}

	public String getProject() {
		return project;
	}

	public String getFile() {
		return file;
	}

	public int getLine() {
		return line;
	}

}