package com.beone.shg.common.web.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class BufferedRequestWrapper extends HttpServletRequestWrapper {

	ByteArrayInputStream bais;
	ByteArrayOutputStream baos;
	BufferedServletInputStream bsis;
	byte [] buffer; 

	public BufferedRequestWrapper(HttpServletRequest req) throws IOException {
		super(req);
		// Read InputStream and store its content in a buffer.
		InputStream is = req.getInputStream();
		baos = new ByteArrayOutputStream();
		byte buf[] = new byte[1024];
		int letti;
		while ((letti=is.read(buf))>0) baos.write(buf,0,letti);
		buffer = baos.toByteArray();
	}

	@SuppressWarnings("finally")
	public ServletInputStream getInputStream() {
		try {
			// Generate a new InputStream by stored buffer
			bais = new ByteArrayInputStream(buffer);
			// Istantiate a subclass of ServletInputStream
			// (Only ServletInputStream or subclasses of it are accepted by the servlet engine!)
			bsis = new BufferedServletInputStream(bais); 
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		} 
		finally {
			return bsis;
		}
	}

	@Override
	public Map getParameterMap() {
		System.out.println("getParameterMap()---->");
		 HttpServletRequest request = (HttpServletRequest)getRequest(); 
		 return request.getParameterMap();
	}

}

