package com.zenith.els;

import java.io.FileReader;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.zenith.els.publish.ELSDataPublisher;

public class ELSMain 
{
	private static final Logger logger = Logger.getLogger(ELSMain.class);

	public static void main(String[] args) 
	{
		try
		{
			Gson gson = new Gson();
			
			FileReader reader=new FileReader("./src/test/resources/application.properties");  
			Properties p=new Properties();  
			p.load(reader);
			
			String elasticSearchHost = p.getProperty("elasticsearch.host");
			String elasticSearchPort = p.getProperty("elasticsearch.port");
			
			ELSDataPublisher publisher = new ELSDataPublisher(elasticSearchHost, elasticSearchPort);
			
			String lorem ="Lorem ipsum dolor sit amet consectetur adipisicing elit. Eligendi ab aliquid nesciunt magni quasi excepturi atque, "
					+ "illo repellat hic numquam maiores tempore possimus! Cum ducimus nobis dolorem rem autem animi?";
			
			String time = (new Date()).toString();
			
			List<String> responseList = publisher.publish("zenith-test", "01", gson.toJson(new DataVO(time, lorem)));
			
			logger.info(responseList);
		}
		catch(Exception e)
		{
			logger.error(e, e);
		}
	}
}
