package com.derivatemeasure.portal.Controller;

import com.derivatemeasure.portal.Business.DerivateFilterResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.derivatemeasure.portal.Business.DerivateMeasureReadFiles;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/derivatives")
public class DerivateMeasureController {
	
	private static final Logger log = LoggerFactory.getLogger(DerivateMeasureController.class);

	@Autowired
	private DerivateMeasureReadFiles deeDerivateMeasureReadFiles;

	@Autowired
	private DerivateFilterResponse derivateFilterResponse;
	
	@GetMapping("/alltradegate")
    public String getAllTradegate() {
        log.info("Getting trade gate data and convert into json.");
        return new GsonBuilder().serializeNulls().create()
		.toJsonTree(deeDerivateMeasureReadFiles.getFileDataFromTradeGateCSV()).toString();
    }
	
	@GetMapping("/allderivatives")
    public String getAllStammdatenAlle(@RequestParam(required = false, defaultValue = "1") long from, @RequestParam(required = false, defaultValue = "100") long limit) {
        if(from > 0 && limit > 0) {
        	log.info("Getting stammdaten alle data and other calculation with convert into json by using limit. form value is : "+from+" and limit is : "+limit);
            return new GsonBuilder().serializeNulls().create()
    		.toJsonTree(deeDerivateMeasureReadFiles.getStammdatenAlleDateFromList().stream().skip(from).limit(limit).collect(Collectors.toList())).toString();
        }else {
			return "Data is not correct. please verify it.";
		}
    }
	
	@GetMapping("/allpeergroups")
    public String getAllPeerGroups() {
        log.info("Getting peer groups data and convert into json.");
        return new GsonBuilder().serializeNulls().create()
		.toJsonTree(deeDerivateMeasureReadFiles.getFileDataFromPeerGroupsCSV()).toString();
    }
	
	@GetMapping("/allergebnis")
    public String getAllErgebnis() {
        log.info("Getting ergebnis data and convert into json.");
        return new GsonBuilder().serializeNulls().create()
		.toJsonTree(deeDerivateMeasureReadFiles.getFileDataFromErgebnisCSV()).toString();
    }
	
	@GetMapping("/derivativebyderivatekey")
	public String getDerivative(@RequestParam(required = false, defaultValue = "") String derivativeKey) {
		if(StringUtils.isNotBlank(derivativeKey)) {
			log.info("Getting derivate data from map by key : " + derivativeKey + " and convert into json.");
			return new GsonBuilder().serializeNulls().create()
					.toJsonTree(deeDerivateMeasureReadFiles.getDerivateDataByKey(derivativeKey)).toString();
		}else {
			return "Derivative key is blank : "+derivativeKey;
		}
	}
	
	@GetMapping("/peergroupbypeerkey")
	public String getPeerGroup(@RequestParam(required = false, defaultValue = "") String peerKey) {
		if(StringUtils.isNotBlank(peerKey)) {
			log.info("Getting peer group data from map by key : " + peerKey + " and convert into json.");
			return new GsonBuilder().serializeNulls().create()
					.toJsonTree(deeDerivateMeasureReadFiles.getPeerGroupDataByKey(peerKey)).toString();
		}else {
			return "Peer Group key is blank : "+peerKey;
		}
	}
	
	@GetMapping("/derivativelistbypeerkey")
	public String getDervativeListByPeerKey(@RequestParam(required = false, defaultValue = "") String peerKey, @RequestParam(required = false, defaultValue = "") String order) {
		if(StringUtils.isNotBlank(peerKey)) {
			log.info("Getting derivative list data from map by key : " + peerKey + " and order is : "+order+" and convert into json.");
			return new GsonBuilder().serializeNulls().create()
					.toJsonTree(deeDerivateMeasureReadFiles.getDerivativeListDataByKey(peerKey, order)).toString();
		}else {
			return "Peer key is blank : "+peerKey;
		}
	}
	
	@GetMapping("/ergebnisbyergebniskey")
	public String getErgebnis(@RequestParam(required = false, defaultValue = "") String ergebnisKey) {
		if(StringUtils.isNotBlank(ergebnisKey)) {
			log.info("Getting ergebnis data from map by key : " + ergebnisKey + " and convert into json.");
			return new GsonBuilder().serializeNulls().create()
					.toJsonTree(deeDerivateMeasureReadFiles.getErgebnisDataByKey(ergebnisKey)).toString();
		}else {
			return "Ergebnis key is blank : "+ergebnisKey;
		}
	}
	
	@GetMapping("/tradegatebytradeKey")
	public String getTradeGate(@RequestParam(required = false, defaultValue = "") String tradeKey) {
		if(StringUtils.isNotBlank(tradeKey)) {
			log.info("Getting trade gate data from map by key : " + tradeKey + " and convert into json.");
			return new GsonBuilder().serializeNulls().create()
					.toJsonTree(deeDerivateMeasureReadFiles.getTradeGateDataByKey(tradeKey)).toString();
		}else {
			return "Trade key is blank : "+tradeKey;
		}
	}
	
	@GetMapping("/calculatederivateratebyderivatekey")
	public String calculateDerivateRate(@RequestParam(required = false, defaultValue = "") String derivativeKey) {
		if(StringUtils.isNotBlank(derivativeKey)) {
			log.info("Getting calculation of derivate rate by key : " + derivativeKey);
			return new GsonBuilder().serializeNulls().create()
					.toJsonTree(deeDerivateMeasureReadFiles.getSidewayReturn(derivativeKey)).toString();
		}else {
			return "Derivate key is blank while doing calculation of derivate rate by key : " + derivativeKey;
		}
	}

	@GetMapping("/derivateratelistbyderivateparameterkey")
	public String getDerivateListByDerivateParameterKey(@RequestParam(required = false, defaultValue = "") String filterIsin,
														@RequestParam(required = false, defaultValue = "") String filterWkn,
														@RequestParam(required = false, defaultValue = "") String filterUisin,
														@RequestParam(required = false, defaultValue = "") String filterEmi,
														@RequestParam(required = false, defaultValue = "") String filterCur,
														@RequestParam(required = false, defaultValue = "") String filterQuanto,
														@RequestParam(required = false, defaultValue = "") String filterExec,
														@RequestParam(required = false, defaultValue = "") String filterBv,
														@RequestParam(required = false, defaultValue = "") String filterCap,
														@RequestParam(required = false, defaultValue = "") String filterAsk,
														@RequestParam(required = false, defaultValue = "") String filterBid,
														@RequestParam(required = false, defaultValue = "") String filterSidewaysReturnPa,
														@RequestParam(required = false, defaultValue = "") String filterMatDate,
														@RequestParam(required = false, defaultValue = "") String filterValDate,
														@RequestParam(required = false, defaultValue = "0") long from,
														@RequestParam(required = false, defaultValue = "50") long limit) {
		Map<String, String> map = new HashMap<>();
		map = derivateFilterResponse.getDerivateStringListByDerivateStringField(filterIsin, filterWkn, filterUisin, filterEmi, filterCur, filterQuanto, filterExec, filterBv, filterCap, filterAsk, filterBid, filterSidewaysReturnPa, filterMatDate, filterValDate);
		if(!map.isEmpty()) {
			log.info("Getting derivate list of derivate parameter by key list : " + map+" form value is : "+from+" and limit is : "+limit);
			return new GsonBuilder().serializeNulls().create()
					.toJsonTree(derivateFilterResponse.getDerivateListByDerivateParameterKey(map, from, limit)).toString();
		}else {
			return "Derivate key is blank while doing calculation of derivate rate by key list : " + map;
		}
	}
}
