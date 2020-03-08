package com.beone.shg.common.web.security;

import java.io.ByteArrayInputStream;

import javax.servlet.ServletInputStream;

public class BufferedServletInputStream extends ServletInputStream {

	ByteArrayInputStream bais;

	public BufferedServletInputStream(ByteArrayInputStream bais) {
		this.bais = bais;
	}

	public int available() {
		return bais.available();
	}

	public int read() {
		return bais.read();
	}

	public int read(byte[] buf,int off,int len) {
		return bais.read(buf,off,len);
	}

}

