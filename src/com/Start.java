package com;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import com.objects.PriorityTwitUserImp;
import com.objects.PriorityUserStatusList;
import com.utility.ConfigBuilder;
import com.utility.PriorityUserException;
import com.utility.WriteFunctions;

public class Start {
	public static void main (String[] args){
		Twitter twitter = ConfigBuilder.getTwitter();
		User user = null;
		try {
			user = twitter.showUser("SpiritAang");
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PriorityTwitUserImp pUser = new PriorityTwitUserImp();
		pUser.setTwitter(twitter);
		pUser.setUser(user);
		String path = pUser.getUser().getScreenName();
		while (true){
			try {
				pUser.setStatusList(pUser.retrieve100TweetsFromId());
				WriteFunctions.writeTweetsToFile(pUser.getStatusList().getStatusesInJSON(), path);
			} catch (PriorityUserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

}
