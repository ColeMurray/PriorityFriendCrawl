package com.utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import twitter4j.User;

public class WriteFunctions {
	static String mCacheDirectory = "cache";

	/**
	 * Creates a directory in "cache/" + @param path
	 * 
	 * To create multi-level, provide path in format "/firstlevel/secondLevel"
	 * 
	 * @param path
	 */
	public static void makeDirectory(String path) {

		File dir = new File(mCacheDirectory + File.separator + path);
		boolean check = dir.mkdirs();
		if (check) {
			System.out.println("Successful creation of directories");
		}
	}

	/**
	 * Path is the name of the levels. For first level, path should be userName
	 * Second level should be firstLevelUsername + File.seperator +
	 * secondUserName
	 * 
	 * @param tweets
	 * @param path
	 */
	public static void writeTweetsToFile(List<String> tweets, String path) {
		makeDirectory(path);
		String fileName = System.nanoTime() + ".json";
		String tweetPath = mCacheDirectory + File.separator + path
				+ File.separator + fileName;
		try {
			File file = new File(tweetPath);
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			for (String s : tweets) {
				writer.append(s);
				writer.append("\n");
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void userListToCSV(String screenName, List<User> celebAndUserList, String filename) {
		String dirName = "csv";
		makeDirectory(dirName);
		String filePath = getFilePath(dirName) + filename;
		try {
			File file = new File(filePath);
			file.createNewFile();
			FileWriter writer = new FileWriter(file,true);
			writer.append(screenName);
			writer.append(",");
			writer.append(String.valueOf(celebAndUserList.size()));
			writer.append("\n");

			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static String getFilePath(String folderName) {
		return mCacheDirectory + File.separator + folderName + File.separator;
	}

}
