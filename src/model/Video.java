package model;

public class Video {
	private String title, uri, thumbnail;

	public Video(String title, String uri, String thumbnail) {
		this.title = title;
		this.uri = uri;
		this.thumbnail = thumbnail;
	}
	
	public String getThumbnail() {
		return thumbnail;
	}

	public String getTitle() {
		return title;
	}

	public String getUri() {
		return uri;
	}
	
}
