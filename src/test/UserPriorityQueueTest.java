package test;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.PriorityUser;
import com.UserPriorityQueue;

public class UserPriorityQueueTest {
	

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testUserPriorityQueue() {
		UserPriorityQueue pq = new UserPriorityQueue();
		Date userA = new Date(2014,10,02);
		Date userB = new Date(2013,10,02);
		PriorityUser a = new PriorityUser();
							a.setLastRetrievedTweetDate(userA);
		PriorityUser b = new PriorityUser();
							b.setLastRetrievedTweetDate(userB);
		pq.add(b);
		pq.add(a);
		assertEquals("Expected 2014/10/2",userA, pq.getPQ().peek().getLastRetrievedTweetDate());
		
		pq.add(a);
		pq.add(b);
		assertEquals("Expected 2014/10/2",userA, pq.getPQ().peek().getLastRetrievedTweetDate());
	}


}
