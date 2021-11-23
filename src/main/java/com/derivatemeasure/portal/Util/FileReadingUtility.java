package com.derivatemeasure.portal.Util;

import java.util.Map;

import com.derivatemeasure.portal.Model.Derivative;
import com.derivatemeasure.portal.Model.PeerGroup;
import com.derivatemeasure.portal.Model.UnderLying;

public interface FileReadingUtility {

	public Map<String, UnderLying> readFileDataFromTradeGateCSV();
	
	public Map<String, Derivative> readFileDataFromStammdatenAlleCSV();
	
	public Map<String, PeerGroup> readFileDataFromPeerGroupsCSV();
	
	public Map<String, Derivative> readFileDataFromErgebnisCSV();
}
