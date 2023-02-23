package com.zenith.els.publish;

import java.util.Arrays;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


public class ELSDataPublisher 
{
	private static final Logger logger = Logger.getLogger(ELSDataPublisher.class);
	
	private String host;
	
	private String port;
	
	public ELSDataPublisher(String host, String port) 
	{
		super();
		this.host = host;
		this.port = port;
	}

	public List<String> publish(String index, String id, String data)
	{
		String createIndexResp = createIndex(index);
		
		String createOrUpdateIndexDocDataResp = createOrUpdateIndexDocData(index, id, data);
		
		return Arrays.asList(createIndexResp, createOrUpdateIndexDocDataResp);
	}
	
	private String createOrUpdateIndexDocData(String index, String id, String data)
	{
		String result = "";

        try
        {
        	HttpPost post = new HttpPost(getURL(host, port, index, "_doc", id));
        	post.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        	post.setEntity(new StringEntity(data));
        	
        	CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(post);
            result = EntityUtils.toString(response.getEntity());
        }
        catch (Exception e) 
        {
        	logger.error(e, e);
		}

        return result;
	}

	private String createIndex(String index)
	{
		String result = "";
        
        try
        {
        	CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(new HttpPut(getURL(host, port, index)));
            result = EntityUtils.toString(response.getEntity());
        }
        catch (Exception e) 
        {
        	logger.error(e, e);
		}

        return result;
	}

	private String getURL(String host, String port, String ... vars)
	{
		StringBuilder url = new StringBuilder("http://");

		url.append(host).append(":").append(port).append("/");
		
		if(vars != null)
		{
			for (String str : vars) 
			{
				url.append(str).append("/");
			}
		}
		
		return url.toString();
	}
}
