package com.utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterObjectFactory;
import twitter4j.User;

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
	
	public static List<User> getUserListFromFile(String filename) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(filename));

			String userToBuild;
			List<User> userList = new ArrayList<User>();

			while ((userToBuild = br.readLine()) != null) {
				User user = TwitterObjectFactory.createUser(userToBuild);
				if (!user.isProtected())
				userList.add(user);

			}

			br.close();
			return userList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	

}