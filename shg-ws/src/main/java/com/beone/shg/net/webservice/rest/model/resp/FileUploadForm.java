package com.beone.shg.net.webservice.rest.model.resp;


import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.web.multipart.MultipartFile;

@JsonSerialize(include = Inclusion.NON_NULL)
public class FileUploadForm {

    private List<MultipartFile> files;

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}
	
 
}
