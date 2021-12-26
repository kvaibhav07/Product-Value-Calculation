package com.derivatemeasure.portal.Listener;

import com.derivatemeasure.portal.Business.DerivateMeasureReadFiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Component
public class DerivateMeasureListener {

    private static final Logger log = LoggerFactory.getLogger(DerivateMeasureListener.class);

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);

    @Autowired
    DerivateMeasureReadFiles derivateMeasureReadFiles;

    @EventListener(ApplicationReadyEvent.class)
    public void listenerStammdatenAlle() {
        executor.submit(() -> {
            log.info("Listener call derivate measure stammdaten alle call for update map :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
            derivateMeasureReadFiles.updateMapFromStammdatenAlleCSV();
        });
    }

    @EventListener(ApplicationReadyEvent.class)
    public void listenerPeerGroups() {
        executor.submit(() -> {
            log.info("Listener call derivate measure peer groups call for update map :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
            derivateMeasureReadFiles.updateMapFromPeerGroupsCSV();
        });
    }

    /*@EventListener(ApplicationReadyEvent.class)
    public void listenerErgebnis() {
        executor.submit(() -> {
            log.info("Listener call derivate measure ergebnis call for update map :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
            derivateMeasureReadFiles.updateMapFromErgebnisCSV();
        });
    }*/

    @EventListener(ApplicationReadyEvent.class)
    public void listenerTradeGate() {
        executor.submit(() -> {
            log.info("Listener call derivate measure trade gate call for update map :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
            derivateMeasureReadFiles.updateMapFromTradeGateCSV();
        });
    }
}
