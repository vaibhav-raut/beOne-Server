package com.beone.shg.net.persistence.spo.util;

import com.beone.shg.net.persistence.util.DataUtil;

public class AlgoSPOUtil {

	public static float computeCreditRating(float allInstPaid, float allInstMissed, float allInstLate) {
		int cRating = DataUtil.ZERO_INTEGER;
		if(allInstPaid > DataUtil.ZERO_FLOAT) {
			cRating = (int)(((allInstPaid - allInstMissed - (allInstLate/2.0F)) / allInstPaid) * 100.0F);
		}
		return cRating;
	}

}
