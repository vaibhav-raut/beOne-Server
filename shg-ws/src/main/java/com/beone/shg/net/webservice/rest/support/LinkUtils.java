package com.beone.shg.net.webservice.rest.support;

import java.util.LinkedList;
import java.util.List;

import com.beone.shg.net.webservice.rest.model.gen.LinkREST;
import com.beone.shg.net.webservice.rest.model.gen.LinkRESTRelationEnum;

public class LinkUtils {

	public static List<LinkREST> createList(LinkRESTRelationEnum rel, String href) {
		return createList(rel, href, null);
	}

	public static List<LinkREST> createList(LinkRESTRelationEnum rel, String href, String type) {
		LinkREST link = new LinkREST(rel,type,href);
		LinkedList<LinkREST> list = new LinkedList<LinkREST>();
		list.add(link);
		return list;
	}
}
