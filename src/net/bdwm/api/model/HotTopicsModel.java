package net.bdwm.api.model;

import java.util.ArrayList;
import java.util.HashMap;

public class HotTopicsModel {
	
	//Top 10 topics
	private ArrayList<Topic> allTopTopics;
	
	//Each divison top topics
	private HashMap<String, ArrayList<Topic>> divisionTopTopics;

	public ArrayList<Topic> getAllTopTopics() {
		return allTopTopics;
	}

	public void setAllTopTopics(ArrayList<Topic> allTopTopics) {
		this.allTopTopics = allTopTopics;
	}

	public HashMap<String, ArrayList<Topic>> getDivisionTopTopics() {
		return divisionTopTopics;
	}

	public void setDivisionTopTopics(
			HashMap<String, ArrayList<Topic>> divisionTopTopics) {
		this.divisionTopTopics = divisionTopTopics;
	}
	
	public HotTopicsModel(){
		allTopTopics = new ArrayList<Topic>();
		divisionTopTopics = new HashMap<String, ArrayList<Topic>>();
	}

}
