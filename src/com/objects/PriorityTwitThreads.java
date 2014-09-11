package com.objects;

import twitter4j.TwitterException;

import com.utility.PriorityUserException;

public class PriorityTwitThreads {

	public static class GetAndWriteTweetsThread implements Runnable {
		private String threadName;
		private Thread t;
		private PriorityQueueTwitter pq;

		public GetAndWriteTweetsThread(String name, PriorityQueueTwitter pq) {
			threadName = name;
			this.pq = pq;
		}

		@Override
		public void run() {
			while (true) {
				PriorityTwitUserImp user;
				user = (PriorityTwitUserImp) pq.peek();
				if (user != null) {
					try {
							user.getUserTweetsAndWriteToFile();

					} catch (PriorityUserException e) {
						System.out.println(e.getMessage()  + user.getScreenName());
						if (e.getMessage().equals(PriorityUserException.ALL_TWEETS)){
							//we need the list to resort, but we don't want to lose
							// the user to gather friends from
							pq.remove();
							pq.add(user);
						}
						if(e.getMessage().equals(PriorityUserException.INACCESSIBLE_USER))
							pq.remove();
					}

				}
			}
		}

		public void start() {
			System.out.println("Running" + threadName);
			if (t == null) {
				t = new Thread(this, threadName);
				t.start();
			}
		}

	}

	public static class GetFriendsListThread implements Runnable {
		private PriorityQueueTwitter pq;
		private Thread t;
		private String threadName;

		public GetFriendsListThread(String name, PriorityQueueTwitter pq) {
			threadName = name;
			this.pq = pq;

		}

		@Override
		public void run() {
			while (true) {
				PriorityTwitUserImp user;
				user = (PriorityTwitUserImp) pq.peek();
				if (user != null)
					if (!user.isHasRetrievedFriends()) {
						try {
							pq.addAll(user.getFriendsInPriorityQueue());
						} catch (TwitterException e) {
							e.printStackTrace();
						}
					}
			}

		}

		public void start() {
			System.out.println("Running" + threadName);
			if (t == null) {
				t = new Thread(this, threadName);
				t.start();
			}

		}

	}


}
