package com.objects;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;
import twitter4j.TwitterObjectFactory;

public class PriorityUserStatusList extends ArrayList<Status>{
	List <String> statusesInJSON;
	private static final long serialVersionUID = -3230766945065066029L;

	public PriorityUserStatusList (List <Status> statuses){
		this.addAll(statuses);
	}
	public List <String> getStatusesInJSON(){
		return statusesInJSON;
	}
	public List <String> toJSON(){
		List <String> JSON = new ArrayList<String>();
		for ( Status s : this ){
			String tweetToJSON = TwitterObjectFactory.getRawJSON(s);
			JSON.add(tweetToJSON);
			
		}
		return JSON;
	}
	
}