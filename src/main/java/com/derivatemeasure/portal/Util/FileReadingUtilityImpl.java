package com.derivatemeasure.portal.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.derivatemeasure.portal.Constant.DerivateMeasureConstant;
import com.derivatemeasure.portal.Model.Derivative;
import com.derivatemeasure.portal.Model.PeerGroup;
import com.derivatemeasure.portal.Model.UnderLying;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Component
public class FileReadingUtilityImpl implements FileReadingUtility{
	
	private static final Logger log = LoggerFactory.getLogger(FileReadingUtilityImpl.class);
	
	@Value("${derivate.measure.file.path}")
	private String derivateMeasureFilePath;

	@Override
	public Map<String, UnderLying> readFileDataFromTradeGateCSV() {
		synchronized (this) {
			log.info("Start reading trade gate csv file data and timestamp : "+LocalDateTime.now());
	        Map<String, UnderLying> map = null;
	        CSVReader csvReader = null;
	        String[] line;
	        try(FileReader fileReader = new FileReader(new File(derivateMeasureFilePath+"\\"+DerivateMeasureConstant.DERIVATE_MEASURE_TRADE_GATE))){
	        	map = new HashMap<>();
	        	CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
	            csvReader = new CSVReaderBuilder(fileReader)
	                    .withCSVParser(csvParser)
	                    .build();
	            while ((line = csvReader.readNext()) != null) {
	                map.put(line[0], new UnderLying(line[0], Double.parseDouble(line[2]), Double.parseDouble(line[4])));
	            } 
	            log.info("Done reading trade gate csv file data and timestamp : "+LocalDateTime.now());
	        }catch(Exception e){
	            log.error("Error getting while reading data from trade gate csv file :",e);
			} finally {
				try {
					if (csvReader != null) {
						csvReader.close();
					}
				} catch (IOException e) {
					log.error("Error getting while close costly resource of trade gate method :",e);
				}
			}
			return map;
		}
    }
	
	@Override
	public Map<String, Derivative> readFileDataFromStammdatenAlleCSV() {
		synchronized (this) {
			log.info("Start reading stammdaten alle csv file data and timestamp : " + LocalDateTime.now());
			String line;
			Map<String, Derivative> map = null;
			try (BufferedReader bufferedReader = new BufferedReader(new FileReader(ResourceUtils.getFile(derivateMeasureFilePath + "\\" + DerivateMeasureConstant.DERIVATE_MEASURE_STAMMDATEN_ALLE)))){
				map = new HashMap<>();
				while ((line = bufferedReader.readLine()) != null) {
					if (line.split(";")[2].length() == 6) {
						if (line.split(";")[2].length() == 6 && line.split(";")[3].length() == 12) {
							Derivative derivative = populateDerivative(line);
							map.put(line.split(";")[2], derivative);
						}
					}
				}
				log.info("Done reading stammdaten alle csv file data and timestamp : " + LocalDateTime.now());
			} catch (Exception e) {
				log.error("Error getting while reading data from stammdaten alle csv file :", e);
			} 
			return map;
		}
	}
	
	@Override
	public Map<String, PeerGroup> readFileDataFromPeerGroupsCSV() {
		synchronized (this) {
			log.info("Start reading peer groups csv file data and timestamp : " + LocalDateTime.now());
			Map<String, PeerGroup> map = null;
			String line;
			try (BufferedReader bufferedReader = new BufferedReader(new FileReader(ResourceUtils.getFile(derivateMeasureFilePath + "\\" + DerivateMeasureConstant.DERIVATE_MEASURE_PEER_GROUPS)))){
				map = new HashMap<>();
				while ((line = bufferedReader.readLine()) != null) {
					map.put(line.split(";")[0], populatePeerGroupObject(line));
				}
				log.info("Done reading peer groups csv file data and timestamp : " + LocalDateTime.now());
			} catch (Exception e) {
				log.error("Error getting while reading data from peer groups csv file :", e);
			} 
			return map;
		}
	}
	
	@Override
	public Map<String, Derivative> readFileDataFromErgebnisCSV() {
		synchronized (this) {
			log.info("Start reading ergebnis csv file data and timestamp : " + LocalDateTime.now());
			CSVReader csvReader = null;
			Map<String, Derivative> map = null;
			String[] line;
			try (FileReader fileReader = new FileReader(new File(derivateMeasureFilePath + "\\" + DerivateMeasureConstant.DERIVATE_MEASURE_ERGEBNIS))){
				map = new HashMap<>();
				CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
				csvReader = new CSVReaderBuilder(fileReader).withCSVParser(csvParser).build();
				while ((line = csvReader.readNext()) != null) {
					map.put(line[0], new Derivative(line[0], Double.parseDouble(line[1]), Double.parseDouble(line[4])));
				}
				log.info("Done reading ergebnis csv file data and timestamp : " + LocalDateTime.now());
			} catch (Exception e) {
				log.error("Error getting while reading data from ergebnis csv file :", e);
			} finally {
				try {
					if (csvReader != null) {
						csvReader.close();
					}
				} catch (IOException e) {
					log.error("Error getting while close costly resource of trade gate method :",e);
				}
			}
			return map;
		}
	}
	
	private Derivative populateDerivative(String line) {
		Derivative derivative = null;
		boolean dateEightPosition = false;
		boolean dateNinePosition = false;
		List<String> fields = null;
		try {
			if(StringUtils.isNotBlank(line)) {
				fields = Arrays.asList(line.split(";+"));
				if(fields.size() <= 8) {
					derivative = new Derivative();
					derivative.setType(Integer.valueOf(fields.get(0)));
					derivative.setIsin(StringUtils.isNotBlank(fields.get(1)) ? fields.get(1) : "");
					derivative.setWkn(StringUtils.isNotBlank(fields.get(2)) ? fields.get(2) : "");
					derivative.setU_isin(StringUtils.isNotBlank(fields.get(3)) ? fields.get(3) : "");
					derivative.setEmi(StringUtils.isNotBlank(fields.get(4)) ? fields.get(4) : "");
					derivative.setCur(StringUtils.isNotBlank(fields.get(5)) ? fields.get(5) : "");
					if(!fields.get(6).contains("-")) {
						derivative.setQuanto(StringUtils.isNotBlank(fields.get(6)) ? Integer.valueOf(fields.get(6)) : Integer.MIN_VALUE);
					}
					if(!fields.get(7).contains("-")) {
						derivative.setExec(StringUtils.isNotBlank(fields.get(7)) ? Integer.valueOf(fields.get(7)) : Integer.MIN_VALUE);
					}
				} else if(fields.size() == 9) {
					derivative = new Derivative();
					derivative.setType(Integer.valueOf(fields.get(0)));
					derivative.setIsin(StringUtils.isNotBlank(fields.get(1)) ? fields.get(1) : "");
					derivative.setWkn(StringUtils.isNotBlank(fields.get(2)) ? fields.get(2) : "");
					derivative.setU_isin(StringUtils.isNotBlank(fields.get(3)) ? fields.get(3) : "");
					derivative.setEmi(StringUtils.isNotBlank(fields.get(4)) ? fields.get(4) : "");
					derivative.setCur(StringUtils.isNotBlank(fields.get(5)) ? fields.get(5) : "");
					if(!fields.get(6).contains("-")) {
						derivative.setQuanto(StringUtils.isNotBlank(fields.get(6)) ? Integer.valueOf(fields.get(6)) : Integer.MIN_VALUE);
					}
					if(!fields.get(7).contains("-")) {
						derivative.setExec(StringUtils.isNotBlank(fields.get(7)) ? Integer.valueOf(fields.get(7)) : Integer.MIN_VALUE);
					}
					if(StringUtils.isNotBlank(fields.get(8)) && !fields.get(8).contains("-")) {
						derivative.setBv(StringUtils.isNotBlank(fields.get(8)) ?  Double.valueOf(fields.get(8)) : Double.NaN);
					}
				}else if(fields.size() > 9) {
					derivative = new Derivative();
					derivative.setType(Integer.valueOf(fields.get(0)));
					derivative.setIsin(StringUtils.isNotBlank(fields.get(1)) ? fields.get(1) : "");
					derivative.setWkn(StringUtils.isNotBlank(fields.get(2)) ? fields.get(2) : "");
					derivative.setU_isin(StringUtils.isNotBlank(fields.get(3)) ? fields.get(3) : "");
					derivative.setEmi(StringUtils.isNotBlank(fields.get(4)) ? fields.get(4) : "");
					derivative.setCur(StringUtils.isNotBlank(fields.get(5)) ? fields.get(5) : "");
					if(!fields.get(6).contains("-")) {
						derivative.setQuanto(StringUtils.isNotBlank(fields.get(6)) ? Integer.valueOf(fields.get(6)) : Integer.MIN_VALUE);
					}	
					if(!fields.get(7).contains("-")) {
						derivative.setExec(StringUtils.isNotBlank(fields.get(7)) ? Integer.valueOf(Double.valueOf(fields.get(7)).intValue()) : Integer.MIN_VALUE);
					}	
					if(StringUtils.isNotBlank(fields.get(8)) && !fields.get(8).contains("-")) {
						derivative.setBv(StringUtils.isNotBlank(fields.get(8)) ?  Double.valueOf(fields.get(8)) : Double.NaN);
					}if(StringUtils.isNotBlank(fields.get(8)) && fields.get(8).contains("-")){
						derivative.setMatdate(new SimpleDateFormat("yyyy-MM-dd").parse(fields.get(8)));
						dateEightPosition =true;
					}
					if(dateEightPosition) {
						derivative.setValdate(new SimpleDateFormat("yyyy-MM-dd").parse(fields.get(9)));
					}else if(StringUtils.isNotBlank(fields.get(9)) && !fields.get(9).contains("-")) {
						derivative.setCap(StringUtils.isNotBlank(fields.get(9)) ?  Double.valueOf(fields.get(9)) : Double.NaN);
					}else if(StringUtils.isNotBlank(fields.get(9)) && fields.get(9).contains("-")){
						derivative.setMatdate(new SimpleDateFormat("yyyy-MM-dd").parse(fields.get(9)));
						dateNinePosition =true;
					}
					if(dateNinePosition) {
						derivative.setValdate(new SimpleDateFormat("yyyy-MM-dd").parse(fields.get(10)));
					}else if(!dateEightPosition && !dateNinePosition)  {
						if(fields.size() > 10 && StringUtils.isNotBlank(fields.get(10)) && fields.get(10).contains("-")) {
							derivative.setMatdate(new SimpleDateFormat("yyyy-MM-dd").parse(fields.get(10)));
						}	
						if(fields.size() > 11 && StringUtils.isNotBlank(fields.get(11)) && fields.get(11).contains("-")) {
							derivative.setValdate(new SimpleDateFormat("yyyy-MM-dd").parse(fields.get(11)));
						}	
					}
				}
			}
		} catch (Exception e) {
			log.error("Error getting while populate derivative object :", e);
		}
		return derivative;
	}
	
	private PeerGroup populatePeerGroupObject(String line) {
		return new PeerGroup(line.split(";")[0], Arrays.asList(line.split(";+")));
	}
	
}
