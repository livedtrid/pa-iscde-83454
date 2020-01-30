package pa.iscde.tasklist.internal;

public class Comment {

	private String text;
	private int offset;

	public Comment(String text, int offset) {

		this.text = text;
		this.offset = offset;
	}

	public String getText() {
		return text;
	}

	public int getOffset() {
		return offset;
	}

}
