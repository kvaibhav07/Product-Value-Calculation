package com.derivatemeasure.portal.Business;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.derivatemeasure.portal.Model.Currency;
import com.derivatemeasure.portal.Model.Derivative;
import com.derivatemeasure.portal.Model.PeerGroup;
import com.derivatemeasure.portal.Model.UnderLying;

public interface DerivateMeasureReadFiles {

	public Map<String, UnderLying> getFileDataFromTradeGateCSV();
	
	public Set<Derivative> getFileDataFromStammdatenAlleCSV();

	public Set<Derivative> getStammdatenAlleDateFromList();
	
	public Map<String, PeerGroup> getFileDataFromPeerGroupsCSV();
	
	public Map<String, Derivative> getFileDataFromErgebnisCSV();

	public Map<String, Currency> getFileDataFromErgebnisFxvwdCSV();

	public void updateMapFromTradeGateCSV();
	
	public void updateMapFromStammdatenAlleCSV();
	
	public void updateMapFromPeerGroupsCSV();
	
	public void updateMapFromErgebnisCSV();
	
	public Derivative getDerivateDataByKey(String derivateKey);

	public PeerGroup getPeerGroupDataByKey(String peerKey);

	public List<Derivative> getDerivativeListDataByKey(String peerKey, String order);

	public Derivative getErgebnisDataByKey(String ergebnisKey);

	public UnderLying getTradeGateDataByKey(String tradeKey);
	
	public Derivative getSidewayReturn(String derivateKey);

	public void updateMapFromErgebnisFxvwdCSV();
}
