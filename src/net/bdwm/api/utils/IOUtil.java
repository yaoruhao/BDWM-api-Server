package net.bdwm.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * This class is used for reading url.
 * @author Ruhao Yao: yaoruhao@gmail.com
 *
 */
public class IOUtil {
	
	private static Log logger = LogFactory.getLog(IOUtil.class);
	
	public static String readUrl(String url){
		String result = null;
		InputStream in = null;
		try {
			in = new URL(url).openStream();
			result = IOUtils.toString(in);
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(in);
		}
		if (result == null) {
			logger.warn("IOUtil read url failed:" + url);
			return null;
		}		
		return result;
	}

}
