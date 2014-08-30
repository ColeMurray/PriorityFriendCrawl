package com;
import java.util.Comparator;
import java.util.PriorityQueue;


public class UserPriorityQueue {
	PriorityQueue <PriorityUser> pq ;
	
	public UserPriorityQueue(){
		pq = new PriorityQueue<PriorityUser>(1, new UPQComparator());
	}
	
	public class UPQComparator implements Comparator<PriorityUser> {

		@Override
		public int compare(PriorityUser a, PriorityUser b) {
			if (a.getLastRetrievedTweetDate().before(b.getLastRetrievedTweetDate()))
				return 1;
			else if (a.getLastRetrievedTweetDate().equals(b.getLastRetrievedTweetDate()))
				return 0;
			else
				return -1;
		}
		
	}
	
	public void add(PriorityUser pUser){
		pq.add(pUser);
	}
	public void deque(PriorityUser pUser){
		pq.remove(pUser);
	}

}
