package com.utility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class ConfigBuilder {

	public static class ConfigKeys {
		public String consumerKey;
		public String consumerSecret;
		public String accessToken;
		public String accessTokenSecret;

		public ConfigKeys(String consumerKey, String consumerSecret,
				String accessToken, String accessSecret) {
			this.consumerKey = consumerKey;
			this.consumerSecret = consumerSecret;
			this.accessToken = accessToken;
			this.accessTokenSecret = accessSecret;

		};

	}

	public static ConfigurationBuilder getConfigBuilder(ConfigKeys key) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setOAuthConsumerKey(key.consumerKey);
		cb.setOAuthConsumerSecret(key.consumerSecret);
		cb.setOAuthAccessToken(key.accessToken);
		cb.setOAuthAccessTokenSecret(key.accessTokenSecret);
		cb.setJSONStoreEnabled(true);

		// cb.setApplicationOnlyAuthEnabled(true);
		return cb;
	}

	public static Twitter getTwitter() {
		ConfigKeys ck = ConfigBuilder.getConsumerKeys("keys.json");

		ConfigurationBuilder cb = ConfigBuilder.getConfigBuilder(ck);

		Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		return twitter;
	}

	/**
	 * Look up filename and parse json to get consumer keys
	 * 
	 * Format {“Consumer Key”:”x”, “Consumer Secret”:”x”, “accessToken”:”x”,
	 * “accessSecret”:”x”}
	 * 
	 * @param fileName
	 * @return
	 */
	public static ConfigKeys getConsumerKeys(String fileName) {
		BufferedReader br;
		JSONParser parser = new JSONParser();
		try {
			br = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Error reading Consumer keys. Check file");
			br = null;
			e.printStackTrace();
		}
		String currLine = null;
		if (br != null) {
			try {
				while ((currLine = br.readLine()) != null) {

					JSONObject obj;
					try {
						obj = (JSONObject) parser.parse(currLine);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						System.out.print("Error parsing, object now null");
						obj = null;
						e.printStackTrace();
					}
					if (obj != null) {

						String consumerKey = (String) obj.get("consumerKey");
						String consumerSecret = (String) obj
								.get("consumerSecret");
						String accessToken = (String) obj.get("accessToken");
						String accessSecret = (String) obj.get("accessSecret");

						ConfigKeys keys = new ConfigKeys(consumerKey,
								consumerSecret, accessToken, accessSecret);
						return keys;

					} else {
						return null;
					}

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
