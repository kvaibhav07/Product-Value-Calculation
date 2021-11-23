package com.derivatemeasure.portal.Schedular;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.derivatemeasure.portal.Business.DerivateFilterResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.derivatemeasure.portal.Business.DerivateMeasureReadFiles;

@Component
@ConditionalOnProperty(name = "derivate.measure.schedule.status", havingValue = "ON", matchIfMissing = true)
public class DerivateMeasureSchedular {

	private static final Logger log = LoggerFactory.getLogger(DerivateMeasureSchedular.class);
	
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	@Autowired
	private DerivateMeasureReadFiles derivateMeasureReadFiles;

	@Autowired
	private DerivateFilterResponse derivateFilterResponse;
	
	@Scheduled(fixedRate = 1000*30, initialDelay = 1)
	public void scheduledTradeGate() {
		log.info("Execute derivate measure trade gate call for update map :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));  
		derivateMeasureReadFiles.updateMapFromTradeGateCSV();
	}
	
	@Scheduled(fixedRate = 1000*60*60, initialDelay = 1)
	public void scheduledStammdatenAlle() {
		log.info("Execute derivate measure stammdaten alle call for update map :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));  
		derivateMeasureReadFiles.updateMapFromStammdatenAlleCSV();
	}
	
	@Scheduled(fixedRate = 1000*60*60, initialDelay = 1)
	public void scheduledPeerGroups() {
		log.info("Execute derivate measure peer groups call for update map :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));  
		derivateMeasureReadFiles.updateMapFromPeerGroupsCSV();
	}
	
	@Scheduled(fixedRate = 1000*30, initialDelay = 1)
	public void scheduledErgebnis() {
		log.info("Execute derivate measure ergebnis call for update map :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));  
		derivateMeasureReadFiles.updateMapFromErgebnisCSV();
	}

	@Scheduled(fixedRate = 1000*60*60, initialDelay = 10000)
	public void scheduledDerivateFilterByKey() {
		log.info("Execute derivate measure filter by key call for update map :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
		derivateFilterResponse.findDerivateListByFilterKeyword();
	}

	@Scheduled(fixedRate = 1000*60*60, initialDelay = 10000)
	public void scheduledDerivateStammdatenAlleList() {
		log.info("Execute derivate measure stammdaten alle prepeare list :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
		derivateMeasureReadFiles.getFileDataFromStammdatenAlleCSV();
	}
}
