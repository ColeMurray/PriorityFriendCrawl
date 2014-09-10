package com;

import java.util.List;

import twitter4j.Twitter;
import twitter4j.User;

import com.objects.PriorityQueueTwitter;
import com.objects.PriorityQueueTwitter.PQTComparator;
import com.objects.PriorityTwitUserImp;
import com.utility.ConfigBuilder;
import com.utility.Utility;

public class Start {
	public static void main (String[] args){
		PriorityQueueTwitter pq;
		PriorityTwitUserImp freshUser;
		PriorityTwitUserImp oldUser;
		Twitter twitter;
		User freshTwitUser;
		User oldTwitUser;
		
		
		
		
		
		twitter = ConfigBuilder.getTwitter();
		
		pq = new PriorityQueueTwitter(1,new PQTComparator());
		List <User> userList = Utility.getUserListFromFile("userNames.json");
		for (User u : userList){
			PriorityTwitUserImp pUser = new PriorityTwitUserImp(twitter, u);
			pq.add(pUser);
		}
		while (true)
		pq.getUserTweetsAndWriteToFile();
		
	}

}
