package com.beone.shg.net.persistence.bo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;

import com.beone.shg.net.config.DBConst;
import com.beone.shg.net.config.RESTConst;
import com.beone.shg.net.persistence.model.Doc;
import com.beone.shg.net.persistence.model.DocType;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.util.RandomString;
import com.beone.shg.net.webservice.rest.model.resp.FileLink;
import com.beone.shg.net.webservice.rest.support.BadRequestException;

@Component("attachmentBO")
public class AttachmentBO extends BaseBO {

//	private final static String TEMP_FILE_FOLDER = File.separator + "WEB-INF" + File.separator + "classes" + File.separator + "Temp_file_folder";
	private final static String TEMP_FILE_FOLDER = File.separator + "Temp_file_folder";
    private RandomString randomString = new RandomString(12, true, true);

    public String saveFile(long groupAcNo, String fileName, int docTypeId, byte[] fileByte) throws BadRequestException, IOException {

    	if(fileByte == null) {
    		throw new BadRequestException("Invalid File!");
    	}
    	DocType docType = daoFactory.getDocTypeDAO().findById(docTypeId);
    	if(docType == null) {
    		throw new BadRequestException("Invalid Doc Type Id: '" + docTypeId + "'!");
    	}
    	if(fileName == null || fileName.isEmpty()) {
    		throw new BadRequestException("Invalid File Name!");
    	}

    	Doc doc = new Doc();
    	doc.setDocType(docType);
    	doc.setGroupAcNo((int)groupAcNo);
    	doc.setFile(fileByte);
    	doc.setUpdateTs(DateUtil.getCurrentTimeDate());
        
    	daoFactory.getDocDAO().persist(doc);

    	return doc.getDocId() + DBConst.ATTACH_INTER_DILIMITER + doc.getDocType().getDocTypeId() + DBConst.ATTACH_INTER_DILIMITER + fileName;
    }

    public String updateFile(long groupAcNo, long docId, String fileName, int docTypeId, byte[] fileByte) throws BadRequestException, IOException {

    	if(docId <= 0) {
    		throw new BadRequestException("Invalid Doc!");
    	}
    	Doc doc = daoFactory.getDocDAO().findById(docId);
    	if(doc == null) {
    		throw new BadRequestException("Invalid Doc!");
    	}
    	if(doc.getGroupAcNo() != groupAcNo) {
    		throw new BadRequestException("Doc don't belong to given Group!");
    	}
    	if(fileName == null || fileName.isEmpty()) {
    		throw new BadRequestException("Invalid File Name!");
    	}
    	if(fileByte == null) {
    		throw new BadRequestException("Invalid File!");
    	}
    	DocType docType = daoFactory.getDocTypeDAO().findById(docTypeId);
    	if(docType == null) {
    		throw new BadRequestException("Invalid Doc Type Id: '" + docTypeId + "'!");
    	}

    	doc.setDocType(docType);
    	doc.setFile(fileByte);
    	doc.setUpdateTs(DateUtil.getCurrentTimeDate());

    	daoFactory.getDocDAO().merge(doc);

    	return doc.getDocId() + DBConst.ATTACH_INTER_DILIMITER + doc.getDocType().getDocTypeId() + DBConst.ATTACH_INTER_DILIMITER + fileName;
    }

    public void deleteFile(long groupAcNo, long docId) throws BadRequestException, IOException {

    	if(docId <= 0) {
    		throw new BadRequestException("Invalid Doc!");
    	}
    	Doc doc = daoFactory.getDocDAO().findById(docId);
    	if(doc == null) {
    		throw new BadRequestException("Invalid Doc!");
    	}
    	if(doc.getGroupAcNo() != groupAcNo) {
    		throw new BadRequestException("Doc don't belong to given Group!");
    	}

    	daoFactory.getDocDAO().remove(doc);
    }

    public byte[] getFile(long docId) throws BadRequestException, IOException {

    	if(docId <= 0) {
    		throw new BadRequestException("Invalid Doc Id!");
    	}
    	Doc doc = daoFactory.getDocDAO().findById(docId);
    	if(doc == null) {
    		throw new BadRequestException("Invalid Doc Id!");
    	}

    	return doc.getFile();
    }

    public FileLink getFileLink(ServletContext sc, long groupAcNo, long docId, String fileName) throws BadRequestException, IOException {

    	if(docId <= 0) {
    		throw new BadRequestException("Invalid Doc Id!");
    	}
    	Doc doc = daoFactory.getDocDAO().findById(docId);
    	if(doc == null) {
    		throw new BadRequestException("Invalid Doc Id!");
    	}
    	if(doc.getGroupAcNo() != groupAcNo) {
    		throw new BadRequestException("Doc don't belong to given Group!");
    	}
    	if(fileName == null || fileName.isEmpty()) {
    		throw new BadRequestException("Invalid File Name!");
    	}
    	
    	FileLink link = new FileLink();
    	
    	String filepath = TEMP_FILE_FOLDER + File.separator + randomString.nextString().toLowerCase();
    	String realFilepath = sc.getRealPath(filepath);
    	File dir = new File(realFilepath);
    	if(!dir.exists()) {
    		dir.mkdirs();
    	}
    	filepath = filepath + File.separator + fileName;
    	realFilepath = realFilepath + File.separator + fileName;
    	
    	File file = new File(realFilepath);
    	FileOutputStream fileOuputStream = null;
    	try { 
//    		FileUtils.writeByteArrayToFile(file, doc.getFile());
    		fileOuputStream = new FileOutputStream(file); 
    		fileOuputStream.write(doc.getFile());
    	} finally {
    		if(fileOuputStream != null) {
    			fileOuputStream.close();
    		}
    	}
    	
    	link.setSuccess(RESTConst.SUCCESS[0]);
    	link.setLink(filepath);
    	
    	return link;
    }
}
