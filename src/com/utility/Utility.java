package com.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;

public class Utility{
	public static Twitter getValidTwitterObject(){
		return ConfigBuilder.getTwitter();
	}
	
	public static long getLastIdFromStatusList (List <Status> statuses){
		long maxId = Long.MAX_VALUE;
		for (Status tweet : statuses){
			if (tweet.getId() < maxId){
				maxId = tweet.getId();
				
			}
		}
		return maxId;
	}

	public static Date getLastCreatedDateFromStatusList (List <Status> statuses){
		Date lastCreatedDate = new Date();
		for (Status t: statuses){
			if (t.getCreatedAt().before(lastCreatedDate)){
				lastCreatedDate = t.getCreatedAt();
			}
		}
		
		return lastCreatedDate;
	}
	/**
	 * 
	 * @param statuses
	 * @return param[0] lastId 
	 * 		   param[1] lastCreated Date
	 */
	public static Object[] getLastIdAndLastCreatedDate (List <Status> statuses){
		long lastId = getLastIdFromStatusList(statuses);
		Date lastCreatedDate = getLastCreatedDateFromStatusList(statuses);
		
		return new Object[]{lastId,lastCreatedDate};
	}
	
	

}