package com.beone.shg.net.webservice.rest.model.resp;

import java.util.ArrayList;
import java.util.List;

import com.beone.shg.net.config.DBConst;
import com.beone.shg.net.persistence.support.EnumCache;

public class Attachment {
	private long docId;
	private DocTypeValue docType;
	private String fileName;
	public Attachment(long docId, DocTypeValue docType, String fileName) {
		super();
		this.docId = docId;
		this.docType = docType;
		this.fileName = fileName;
	}
	public long getDocId() {
		return docId;
	}
	public void setDocId(long docId) {
		this.docId = docId;
	}
	public DocTypeValue getDocType() {
		return docType;
	}
	public void setDocType(DocTypeValue docType) {
		this.docType = docType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public static List<Attachment> buildAttachments(String attachmentUrl) {
		List<Attachment> attachments = new ArrayList<Attachment>();
		if(attachmentUrl != null && !attachmentUrl.isEmpty()) {
			String[] docs = attachmentUrl.split(DBConst.ATTACH_EXTERNAL_DILIMITER);
			if(docs != null && docs.length > 0)
			{
				for(String doc : docs) {
					String[] par = doc.split(DBConst.ATTACH_INTER_DILIMITER);
					if(par != null && par.length == 3) {
						attachments.add(new Attachment(Long.valueOf(par[0]), 
								EnumCache.getDocTypeValue(Integer.valueOf(par[1])), 
								par[2]));
					}
				}
			}
		}
		return attachments;
	}
	
	public static String updateAttachmentUrl(String fileUpdate, String attachmentUrl) {
		
		if(fileUpdate != null && !fileUpdate.isEmpty()) {
			String[] filePar = fileUpdate.split(DBConst.ATTACH_INTER_DILIMITER);
			if(filePar != null && filePar.length == 3) {
				long docId = Long.valueOf(filePar[0]);
				
				if(attachmentUrl != null && !attachmentUrl.isEmpty()) {
					String[] docs = attachmentUrl.split(DBConst.ATTACH_EXTERNAL_DILIMITER);
					if(docs != null && docs.length > 0)
					{
						StringBuilder sb = new StringBuilder();
						for(int index = 0; index < docs.length; index++) {
							String[] par = docs[index].split(DBConst.ATTACH_INTER_DILIMITER);
							if(index > 0) {
								sb.append(DBConst.ATTACH_EXTERNAL_DILIMITER);
							}
							if(par != null && par.length == 3 && docId == Long.valueOf(par[0])) {
								sb.append(fileUpdate);
							} else {
								sb.append(docs[index]);
							}
						}
						attachmentUrl = sb.toString();
					}
				}
			}
		}				
		return attachmentUrl;
	}
	
	public static String deleteAttachmentUrl(String docIdStr, String attachmentUrl) {

		if(docIdStr != null && !docIdStr.isEmpty()) {
			long docId = Long.valueOf(docIdStr);

			if(attachmentUrl != null && !attachmentUrl.isEmpty()) {
				String[] docs = attachmentUrl.split(DBConst.ATTACH_EXTERNAL_DILIMITER);
				if(docs != null && docs.length > 0)
				{
					StringBuilder sb = new StringBuilder();
					boolean elementAdded = false;
					for(int index = 0; index < docs.length; index++) {
						String[] par = docs[index].split(DBConst.ATTACH_INTER_DILIMITER);
						if(!(par != null && par.length == 3 && docId == Long.valueOf(par[0]))) {
							if(elementAdded) {
								sb.append(DBConst.ATTACH_EXTERNAL_DILIMITER);
							}
							sb.append(docs[index]);
							elementAdded = true;
						}
					}
					attachmentUrl = sb.toString();
				}
			}
		}				
		return attachmentUrl;
	}
}
