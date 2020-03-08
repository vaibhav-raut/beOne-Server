package com.beone.shg.net.webservice.rest.support;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.beone.shg.net.webservice.rest.model.gen.LinkRESTRelationEnum;

public class LinkRESTRelationEnumJsonSerializer extends JsonSerializer<LinkRESTRelationEnum> {

	@Override
	public void serialize(LinkRESTRelationEnum value, JsonGenerator gen, SerializerProvider provider) throws IOException,
			JsonProcessingException
	{
		gen.writeString(value.getRestValue());
	}
}