package com.objects;

import java.util.Comparator;
import java.util.PriorityQueue;

import com.utility.PriorityUserException;
import com.utility.WriteFunctions;

public class PriorityQueueTwitter extends PriorityQueue<PriorityTwitUser> {
	public PriorityQueueTwitter(int i, PQTComparator pqtComparator) {
		super (i,pqtComparator);
	}
	
	/**
	 * Pops user off priority queue, gets tweets, and writes to file
	 * 
	 */
	public void getUserTweetsAndWriteToFile(){
		PriorityTwitUserImp user = (PriorityTwitUserImp) this.poll();
		try {
			user.setStatusList(user.retrieve100TweetsFromId());
			String path = user.getPath();
			WriteFunctions.writeTweetsToFile(user.getStatusList().getStatusesInJSON(), path);
			this.add(user);
		} catch (PriorityUserException e) {
			//User has no tweets, no reason to re-add them to file
			e.printStackTrace();
		}
		
	
	}

	private static final long serialVersionUID = 1650582478869757281L;
	
	public static class PQTComparator implements Comparator<PriorityTwitUser>{

		@Override
		public int compare(PriorityTwitUser a, PriorityTwitUser b ) {
			if (a.getLastRetrievedTweetDate().before(b.getLastRetrievedTweetDate()))
				return 1;
			else if (a.getLastRetrievedTweetDate().equals(b.getLastRetrievedTweetDate()))
				return 0;
			else
				return -1;
			
		}
		
	
	}
	
	
}
