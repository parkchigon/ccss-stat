package com.lgu.ccss.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javassist.NotFoundException;

@Component
public class HttpReqUtil {

	private static final Logger logger = LoggerFactory.getLogger(HttpReqUtil.class);
	
	public static JSONObject httpRequest(String url, ArrayList<Pair<String, String>> headerList, String reqContent) throws ParseException, IOException, NotFoundException {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		StringEntity reqEntity = null;
		HttpResponse response = null;
		HttpEntity resEntity = null;
		
		String responseJson = null;
		JSONObject resultJson = null;
		logger.debug("=========== httpRequest Info ===========");
		logger.debug("httpRequest url        : "+url);
		logger.debug("httpRequest reqContent : "+reqContent);
		logger.debug("=========== HeaderInfo ===========");
		for (Pair<String, String> pair : headerList) {
			logger.debug(pair.getElement0()+ " : " +pair.getElement1());
			post.setHeader((String)pair.getElement0(),(String)pair.getElement1());
		}
		logger.debug("==================================");
		try {
			reqEntity = new StringEntity(reqContent,"UTF-8");
			
			post.setEntity(reqEntity);
			logger.debug("HttpRequest!");
			response = client.execute(post);			
			if( response.getStatusLine().getStatusCode() == 200) {
				logger.debug("HttpRequest OK!");
				logger.debug("Response Code : "+response.getStatusLine().getStatusCode()  );
				resEntity = response.getEntity();
				responseJson = EntityUtils.toString(resEntity, "UTF-8");
				JSONParser parser = new JSONParser();				
				resultJson = (JSONObject) parser.parse(responseJson);
				logger.debug("JSON MSG      : "+resultJson);
			}else {
				logger.debug("HttpRequest Not OK!");
				logger.debug("Response Code : "+response.getStatusLine().getStatusCode()  );
				logger.error("throw new IOException");
				throw new NotFoundException("ResponseCode : "+response.getStatusLine().getStatusCode());
			}			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error("UnsupportedEncodingException",e);
			throw e;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			logger.error("ClientProtocolException",e);
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("IOException",e);
			throw e;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error("ParseException",e);
			throw e;
		}
		
		return resultJson;
	}
}
