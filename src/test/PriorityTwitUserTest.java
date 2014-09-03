package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.objects.PriorityTwitUser;
import com.objects.PriorityTwitUserImp;

public class PriorityTwitUserTest {

	// TODO finish Setup and testTweetRetrieval
	PriorityTwitUserImp ptUser;
	@Before
	public void setUp() throws Exception {
		ptUser = new PriorityTwitUserImp();
	
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	public void testTweetRetrieval(){
		List <String> returned = ptUser.retrieveTweetsInString(0);
	}

}
