package com.derivatemeasure.portal.Util;

import com.derivatemeasure.portal.Model.Currency;
import com.derivatemeasure.portal.Model.Derivative;
import com.derivatemeasure.portal.Model.PeerGroup;
import com.derivatemeasure.portal.Model.UnderLying;

import java.util.List;
import java.util.Map;

public interface FileReadingUtility {

	public Map<String, UnderLying> readFileDataFromTradeGateCSV();
	
	public Map<String, Derivative> readFileDataFromStammdatenAlleCSV();
	
	public Map<String, PeerGroup> readFileDataFromPeerGroupsCSV();
	
	public Map<String, Derivative> readFileDataFromErgebnisCSV();

	public Map<String, Currency> readFileDataFromErgebnisFxvwdCSV();
}
