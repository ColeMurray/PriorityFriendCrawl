package com.objects;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityQueueTwitter extends PriorityQueue<PriorityTwitUser> {
	public PriorityQueueTwitter(int i, PQTComparator pqtComparator) {
		super (i,pqtComparator);
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
