package net.bdwm.api.model;

import java.util.ArrayList;
import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author Ruhao Yao: yaoruhao@gmail.com
 *
 */
public class HotTopicsModel {

	// Top 10 topics
	private ArrayList<Topic> allTopTopics;

	// Each divison top topics
	private HashMap<String, ArrayList<Topic>> divisionTopTopics;

	// School hot topics
	private ArrayList<Topic> schoolHotTopics;

	// Academic hot topics
	private ArrayList<Topic> academicHotTopics;
	
	private JSONArray topTenJsonArray;
	
	private JSONObject divisionHotJsonObject;
	
	private JSONArray schoolHotJsonArray;
	
	private JSONArray academicHotJsonArray;

	

	public JSONArray getTopTenJsonArray() {
		return topTenJsonArray;
	}

	public void setTopTenJsonArray(JSONArray topTenJsonArray) {
		this.topTenJsonArray = topTenJsonArray;
	}

	public JSONObject getDivisionHotJsonObject() {
		return divisionHotJsonObject;
	}

	public void setDivisionHotJsonObject(JSONObject divisionHotJsonObject) {
		this.divisionHotJsonObject = divisionHotJsonObject;
	}

	public JSONArray getSchoolHotJsonArray() {
		return schoolHotJsonArray;
	}

	public void setSchoolHotJsonArray(JSONArray schoolHotJsonArray) {
		this.schoolHotJsonArray = schoolHotJsonArray;
	}

	public JSONArray getAcademicHotJsonArray() {
		return academicHotJsonArray;
	}

	public void setAcademicHotJsonArray(JSONArray academicHotJsonArray) {
		this.academicHotJsonArray = academicHotJsonArray;
	}

	public ArrayList<Topic> getAcademicHotTopics() {
		return academicHotTopics;
	}

	public void setAcademicHotTopics(ArrayList<Topic> academicHotTopics) {
		this.academicHotTopics = academicHotTopics;
	}

	public ArrayList<Topic> getSchoolHotTopics() {
		return schoolHotTopics;
	}

	public void setSchoolHotTopics(ArrayList<Topic> schoolHotTopics) {
		this.schoolHotTopics = schoolHotTopics;
	}

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

	public HotTopicsModel() {
		allTopTopics = new ArrayList<Topic>();
		divisionTopTopics = new HashMap<String, ArrayList<Topic>>();
		schoolHotTopics = new ArrayList<Topic>();
		academicHotTopics = new ArrayList<Topic>();
	}

}
