package com.beone.shg.net.persistence.mpo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beone.shg.net.persistence.util.DataUtil;

public class SMSSendingUtil {

	private static final Logger log = LoggerFactory.getLogger(SMSSendingUtil.class);

	// Replace with your username
	public final static String USER = "beone.tech";

	// Replace with your API KEY (We have sent API KEY on activation email, also available on panel)
	public final static String API_KEY = "rQaoDpiPLkRNV9ECR0Vn";

	// Replace if you have your own Sender ID, else donot change
	public final static String SENDER_ID = "SHGONE";

	// For Plain Text, use "txt" ; for Unicode symbols or regional Languages like hindi/tamil/kannada use "uni"
	public final static String TYPE = "txt";

	public final static String MAIN_URL = "http://smshorizon.co.in/api/sendsms.php?";

	@SuppressWarnings("deprecation")
	public static List<Integer> sendTxSMS(List<String> mobileNos, String message) {

		List<Integer> messageIds = new ArrayList<Integer>(mobileNos.size());

		for(String mobileNo : mobileNos) {
			int messageId = sendTxSMS(mobileNo, message);
			if(messageId > 0) {
				messageIds.add(messageId);
			}
		}

		return messageIds;
	}

	@SuppressWarnings("deprecation")
	public static int sendTxSMS(String mobileNo, String message) {

		log.error(mobileNo + " - " + message);
		
		int messageId = -1;
		
//		//encoding message 
//		String encoded_message = URLEncoder.encode(message);
//
//		//Prepare parameter string 
//		StringBuilder sbPostData= new StringBuilder(MAIN_URL);
//		sbPostData.append("user=" + USER); 
//		sbPostData.append("&apikey=" + API_KEY);
//		sbPostData.append("&message=" + encoded_message);
//		sbPostData.append("&mobile=" + mobileNo);
//		sbPostData.append("&senderid=" + SENDER_ID);
//		sbPostData.append("&type=" + TYPE);
//
//		//final string
//		String mainUrl = sbPostData.toString();
//		try
//		{
//			//prepare connection
//			URL myURL = new URL(mainUrl);
//			URLConnection myURLConnection = myURL.openConnection();
//			myURLConnection.connect();
//			
//			BufferedReader reader= new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
//			
//			//reading response 
//			String response;
//			while ((response = reader.readLine()) != null) {
//				messageId = DataUtil.toInteger(response.trim());
//			}
//			//finally close connection
//			reader.close();
//		} 
//		catch (IOException e) 
//		{ 
//			e.printStackTrace();
//		} 

		return messageId;
	}
}
