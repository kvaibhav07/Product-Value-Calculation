package com.derivatemeasure.portal.Schedular;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

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

	ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
	
	@Autowired
	DerivateMeasureReadFiles derivateMeasureReadFiles;

	@Autowired
	DerivateFilterResponse derivateFilterResponse;

	@Scheduled(fixedRate = 1000*60*30, initialDelay = 15000)
	public void scheduledStammdatenAlleFromList() {
		executor.submit(() -> {
			log.info("Execute derivate measure stammdaten alle form list call for update map :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
			derivateMeasureReadFiles.getStammdatenAlleDateFromList();
		});
	}

	@Scheduled(fixedRate = 1000*60*60, initialDelay = 30000)
	public void scheduledDerivateFilterByKey() {
		executor.submit(() -> {
			log.info("Execute derivate measure filter by key call for update map :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
			derivateFilterResponse.findDerivateListByFilterKeyword();
		});
	}

	@Scheduled(fixedRate = 1000*60*60, initialDelay = 30000)
	public void scheduledDerivateFilterByIntegerKey() {
		executor.submit(() -> {
			log.info("Execute derivate measure filter by integer key call for update map :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
			derivateFilterResponse.findDerivateListByFilterIntegerKeyword();
		});
	}

	@Scheduled(fixedRate = 1000*30, initialDelay = 1000)
	public void scheduledErgebnisFxvwd() {
		executor.submit(() -> {
			log.info("Execute derivate measure Ergebnis Fxvwd to update map :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
			derivateMeasureReadFiles.updateMapFromErgebnisFxvwdCSV();
		});
	}
}
