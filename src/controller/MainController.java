package controller;

import java.io.File;
import java.util.ArrayList;

import model.Video;

public class MainController {
	
	public ArrayList<Video> getVideoList() {
		ArrayList<Video> videoList = new ArrayList<Video>();
		
		videoList.add(new Video("NCS - Superhero", getResource("ncs-superhero.mp4"), getResource("ncs-superhero-image.png")));
		videoList.add(new Video("NCS - On & On", getResource("ncs-on&on.mp4"), getResource("ncs-on&on-image.png")));
		videoList.add(new Video("Millenium Disco Club", getResource("millenium-dc.mp4"), getResource("millenium-dc-image.png")));
		videoList.add(new Video("DJ Pipi Mimi", getResource("dj-pipi-mimi.mp4"), getResource("dj-pipi-mimi-image.png")));
		videoList.add(new Video("DJ Goyang Dayung", getResource("dj-goyang-dayung.mp4"), getResource("dj-goyang-dayung-image.png")));
		
		return videoList;
	}
	
	private String getResource(String fileName) {
		return System.getProperty("user.dir") + File.separator + "src" + File.separator + "resources" + File.separator + fileName;
	}
}
