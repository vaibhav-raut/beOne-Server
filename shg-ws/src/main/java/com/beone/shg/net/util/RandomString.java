package com.beone.shg.net.util;

import java.util.Random;

public class RandomString {

	private final char[] symbols;

	private final Random random = new Random();

	private final char[] buf;

	public RandomString(int length, boolean charAllowed, boolean numAllowed) {
		if (length < 1) {
			throw new IllegalArgumentException("length < 1: " + length);
		}
		if (!charAllowed && !numAllowed) {
			throw new IllegalArgumentException("Both Charters & Numbers can't be OFF!");
		}
		
		buf = new char[length];

		{
			StringBuilder tmp = new StringBuilder();
			if(numAllowed) {
				for (char ch = '0'; ch <= '9'; ++ch)
					tmp.append(ch);
			}
			if(charAllowed) {
				for (char ch = 'a'; ch <= 'z'; ++ch)
					tmp.append(ch);
			}
			symbols = tmp.toString().toCharArray();
		}	
	}

	public String nextString() {
		for (int idx = 0; idx < buf.length; ++idx) {
			buf[idx] = symbols[random.nextInt(symbols.length)];
		}
		return new String(buf);
	}
}