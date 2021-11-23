package com.derivatemeasure.portal.Business;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.derivatemeasure.portal.Constant.DerivateMeasureConstant;
import com.derivatemeasure.portal.Model.Derivative;
import com.derivatemeasure.portal.Model.PeerGroup;
import com.derivatemeasure.portal.Model.UnderLying;
import com.derivatemeasure.portal.Util.FileReadingUtility;

@Component
public class DerivateMeasureReadFilesImpl implements DerivateMeasureReadFiles{
	
	private static final Logger log = LoggerFactory.getLogger(DerivateMeasureReadFilesImpl.class);
	
	@Autowired
	private FileReadingUtility fileReadingUtility;
	
	Map<String, UnderLying> tradeGateMap = new ConcurrentHashMap<>();
	Map<String, Derivative> stammdatenAlleMap = new ConcurrentHashMap<>();
	Map<String, PeerGroup> peerGroupsMap = new ConcurrentHashMap<>();
	Map<String, Derivative> ergebnisMap = new ConcurrentHashMap<>();
	List<Derivative> stammdatenAlleDervatelist = Collections.synchronizedList(new ArrayList<>());

	@Override
	public Map<String, UnderLying> getFileDataFromTradeGateCSV() {
		log.info("Start to read file data from trade gate csv file.");
		return fileReadingUtility.readFileDataFromTradeGateCSV();
	}

	@Override
	public List<Derivative> getFileDataFromStammdatenAlleCSV() {
		try {
			synchronized (this){
				log.info("Start to read file data from stammdaten alle csv file.");
				if(stammdatenAlleMap.size() == 0)
					updateMapFromStammdatenAlleCSV();
				if(ergebnisMap.size() == 0)
					updateMapFromErgebnisCSV();
				for (Entry<String, Derivative> derivate : stammdatenAlleMap.entrySet()) {
					Derivative derivative = derivate.getValue();
					derivative.setAsk(ergebnisMap.get(derivate.getKey()) != null ? ergebnisMap.get(derivate.getKey()).getAsk() : Double.valueOf(0));
					derivative.setBid(ergebnisMap.get(derivate.getKey()) != null ? ergebnisMap.get(derivate.getKey()).getBid() : Double.valueOf(0));
					if(derivative != null && StringUtils.isNotBlank(derivative.getU_isin()) && derivative.getValdate() != null) {
						double ask = Double.valueOf(derivative.getAsk() != null ? derivative.getAsk() : 0d);
						double bv = Double.valueOf(derivative.getBv() != null ? derivative.getBv() : 0d);
						double derivativeAsk = ask/bv;
						double cap = derivative.getCap() == null ? 0d : Double.valueOf(derivative.getCap());
						double underlyingAsk = getTradeGateDataByKey(derivative.getU_isin()) != null ? getTradeGateDataByKey(derivative.getU_isin()).getAsk() : 0d;
						long restDays = TimeUnit.DAYS.convert(derivative.getValdate().getTime() - new Date().getTime(), TimeUnit.MILLISECONDS);
						derivative.setSidewaysReturnPa(calculateAnnualRate(calculateRate(Math.min(cap, underlyingAsk), derivativeAsk), restDays));
					}
					stammdatenAlleDervatelist.add(derivative);
				}
			}
		} catch (Exception e) {
			log.error("Error getting while merge stammdaten alle and ergebnis and calculate data in derivative object : ",e);
		}
		return stammdatenAlleDervatelist;
	}

	@Override
	public List<Derivative> getStammdatenAlleDateFromList() {
		if(stammdatenAlleDervatelist.size() == 0)
			getFileDataFromStammdatenAlleCSV();
		return stammdatenAlleDervatelist;
	}

	@Override
	public Map<String, PeerGroup> getFileDataFromPeerGroupsCSV() {
		log.info("Start to read file data from peer groups csv file.");
		return fileReadingUtility.readFileDataFromPeerGroupsCSV();
	}

	@Override
	public Map<String, Derivative> getFileDataFromErgebnisCSV() {
		log.info("Start to read file data from ergebnis csv file.");
		return fileReadingUtility.readFileDataFromErgebnisCSV();
	}

	@Override
	public void updateMapFromTradeGateCSV() {
		log.info("Calling from schedular to read file data from trade gate csv file and update in map.");
		tradeGateMap = fileReadingUtility.readFileDataFromTradeGateCSV();
	}

	@Override
	public void updateMapFromStammdatenAlleCSV() {
		log.info("Calling from schedular to read file data from stammdaten alle csv file and update in map.");
		stammdatenAlleMap = fileReadingUtility.readFileDataFromStammdatenAlleCSV();
	}

	@Override
	public void updateMapFromPeerGroupsCSV() {
		log.info("Calling from schedular to read file data from peer groups csv file and update in map.");
		peerGroupsMap = fileReadingUtility.readFileDataFromPeerGroupsCSV();
	}

	@Override
	public void updateMapFromErgebnisCSV() {
		log.info("Calling from schedular to read file data from ergebnis csv file and update in map.");
		ergebnisMap = fileReadingUtility.readFileDataFromErgebnisCSV();
	}
	
	@Override
	public Derivative getDerivateDataByKey(String derivateKey) {
		Derivative derivative = null;
		try {
			log.info("Find the derivative data from map by derivative key : "+derivateKey);
			if(stammdatenAlleMap.size() == 0) 
				updateMapFromStammdatenAlleCSV();
			
			derivative = stammdatenAlleMap.get(derivateKey);
			if(derivative != null) {
				if(ergebnisMap.size() == 0) 
					updateMapFromErgebnisCSV();
				derivative.setAsk(ergebnisMap.get(derivateKey) != null ? ergebnisMap.get(derivateKey).getAsk() : Double.valueOf(0));
				derivative.setBid(ergebnisMap.get(derivateKey) != null ? ergebnisMap.get(derivateKey).getBid() : Double.valueOf(0));
			}
		} catch (Exception e) {
			log.error("Error getting while merge stammdaten alle and ergebnis data in derivative object : ",e);
		}
		return derivative;
	}

	@Override
	public PeerGroup getPeerGroupDataByKey(String peerKey) {
		log.info("Find the peer group data from map by peer key : "+peerKey);
		if(peerGroupsMap.size() == 0)
			updateMapFromPeerGroupsCSV();
		return peerGroupsMap.get(peerKey);
	}

	@Override
	public List<Derivative> getDerivativeListDataByKey(String peerKey, String order) {
		List<Derivative> list = null;
		try {
			log.info("Find the peers group list data from map by peer key : "+peerKey);
			if(peerGroupsMap.size() == 0)
				updateMapFromPeerGroupsCSV();
			List<Derivative> appendDerivateList = peerGroupsMap.get(peerKey).getPeers().stream().filter(Objects::nonNull).map(peers -> {
				Derivative derivative = getDerivateDataByKey(peers);
				derivative.setSidewaysReturnPa(getSidewayReturn(derivative.getWkn()).getSidewaysReturnPa());
				return derivative;
			}).collect(Collectors.toList());
			
			if(StringUtils.isNotBlank(order) && order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_ASCENDING)) {
				list = appendDerivateList.stream().sorted(Comparator.comparingDouble(Derivative::getSidewaysReturnPa)).collect(Collectors.toList());
			}else if(StringUtils.isNotBlank(order) && order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_DESCENDING)) {
				list = appendDerivateList.stream().sorted(Comparator.comparingDouble(Derivative::getSidewaysReturnPa).reversed()).collect(Collectors.toList());
			}else {
				list = appendDerivateList;
			}
		} catch (Exception e) {
			log.error("Error getting while merge peers list to derivative list : ",e);
		}
		return list;
	}

	@Override
	public Derivative getErgebnisDataByKey(String ergebnisKey) {
		log.info("Find the ergebnis data from map by ergebnis key : "+ergebnisKey);
		if(ergebnisMap.size() == 0)
			updateMapFromErgebnisCSV();
		return ergebnisMap.get(ergebnisKey);
	}

	@Override
	public UnderLying getTradeGateDataByKey(String tradeKey) {
		log.info("Find the trade gate data from map by trade key : "+tradeKey);
		if(tradeGateMap.size() == 0)
			updateMapFromTradeGateCSV();
		return tradeGateMap.get(tradeKey);
	}

	@Override
	public Derivative getSidewayReturn(String derivateKey) {
		Derivative derivative = null;
		try {
			synchronized (this) {
				if(StringUtils.isNotBlank(derivateKey)) {
					derivative = getDerivateDataByKey(derivateKey);
					if(derivative != null && StringUtils.isNotBlank(derivative.getU_isin()) && derivative.getValdate() != null) {
						double derivativeAsk = derivative.getAsk()/derivative.getBv();
						double cap = derivative.getCap();
						double underlyingAsk = getTradeGateDataByKey(derivative.getU_isin()).getAsk();
						long restDays = TimeUnit.DAYS.convert(derivative.getValdate().getTime() - new Date().getTime(), TimeUnit.MILLISECONDS);
						derivative.setSidewaysReturnPa(calculateAnnualRate(calculateRate(Math.min(cap, underlyingAsk), derivativeAsk), restDays));
						log.info("Calculate derivate by key : "+derivateKey+" and derivate ask value : "+derivativeAsk+ " and cap value : "+cap
								+" underlying ask value : "+underlyingAsk+" and rest day : "+restDays+" and final calculation result : "+derivative.getSidewaysReturnPa());
					}
				}
			}
		} catch (Exception e) {
			log.error("Error getting while calculate derivate and underlying value : ",e);
		}
		return derivative;
	}

	private synchronized double calculateAnnualRate(double calculateRate, long restDays) {
		double result = Math.pow(1 + calculateRate, 365 * 1.0 / restDays) - 1;
		return Double.isInfinite(result) ? 0d : Double.isNaN(result) ? 0d : result;
	}

	private synchronized double calculateRate(double endPrice, double price) {
		double result = endPrice / price - 1;
		return Double.isInfinite(result) ? 0d : Double.isNaN(result) ? 0d : result;
	}
}
