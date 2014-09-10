package com;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import com.objects.PriorityQueueTwitter;
import com.objects.PriorityQueueTwitter.PQTComparator;
import com.objects.PriorityTwitUserImp;
import com.utility.ConfigBuilder;
import com.utility.PriorityUserException;
import com.utility.WriteFunctions;

public class Start {
	public static void main (String[] args){
		PriorityQueueTwitter pq;
		PriorityTwitUserImp freshUser;
		PriorityTwitUserImp oldUser;
		Twitter twitter;
		User freshTwitUser;
		User oldTwitUser;
		
		
		
		
		
		
		
		pq = new PriorityQueueTwitter(1,new PQTComparator());
		freshUser = new PriorityTwitUserImp();
		oldUser = new PriorityTwitUserImp();
		twitter = ConfigBuilder.getTwitter();
		try {
			freshTwitUser = twitter.showUser("SpiritAang");
			oldTwitUser = twitter.showUser("reginald");
			
			freshUser.setTwitterAndUser(twitter, freshTwitUser);
			oldUser.setTwitterAndUser(twitter,oldTwitUser);
			pq.add(freshUser);
			pq.add(oldUser);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while (true){
					pq.getUserTweetsAndWriteToFile();
		

		}
		
		
	}

}
