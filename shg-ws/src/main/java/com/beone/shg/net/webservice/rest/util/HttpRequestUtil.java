package com.beone.shg.net.webservice.rest.util;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class HttpRequestUtil {

	// Assuming only one file uploaded
    public static FileItem parseRequestToGetFileItem(HttpServletRequest request)
    		throws FileUploadException {

    	// Create a factory for disk-based file items
    	DiskFileItemFactory factory = new DiskFileItemFactory();

    	// Configure a repository (to ensure a secure temp location is used)
    	ServletContext servletContext = request.getServletContext();
    	File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
    	factory.setRepository(repository);

    	// Create a new file upload handler
    	ServletFileUpload upload = new ServletFileUpload(factory);

    	// Parse the request
    	@SuppressWarnings("unchecked")
		List<FileItem> items = upload.parseRequest(request);
    	// return the uploaded file. Assuming, only one file uploaded.
    	for (FileItem item : items) {
    		if (!item.isFormField()) {
    			
    			// For testing only. Save the file to see the content.
//    			File file = new File("c://" + item.getName());
//    			try {
//    				FileUtils.writeByteArrayToFile(file, item.get());
//    			} catch (IOException e) {
//    				e.printStackTrace();
//    			}

    			return item;
    		}
    	}
    	
    	return null;
    }
 }
