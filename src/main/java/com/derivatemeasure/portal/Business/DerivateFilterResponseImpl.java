package com.derivatemeasure.portal.Business;

import com.derivatemeasure.portal.Constant.DerivateEnum;
import com.derivatemeasure.portal.Model.Derivative;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DerivateFilterResponseImpl implements DerivateFilterResponse{

    private static final Logger log = LoggerFactory.getLogger(DerivateFilterResponseImpl.class);

    @Autowired
    private DerivateMeasureReadFiles derivateMeasureReadFiles;

    private static final DateFormat dateFormat = new SimpleDateFormat("MMM DD, yyyy, HH:MM:SS a");

    Map<String, List<Derivative>> finalMap = new ConcurrentHashMap<>();
    Map<String, List<Derivative>> filterIsinDerivateMap = new HashMap<>();
    Map<String, List<Derivative>> filterWknDerivateMap = new HashMap<>();
    Map<String, List<Derivative>> filterUisinDerivateMap = new HashMap<>();
    Map<String, List<Derivative>> filterEmiDerivateMap = new HashMap<>();
    Map<String, List<Derivative>> filterCurDerivateMap = new HashMap<>();

    @Override
    public void findDerivateListByFilterKeyword() {
        try {
            synchronized (this){
                List<Derivative> derivativeList = derivateMeasureReadFiles.getStammdatenAlleDateFromList();
                getDervativeListByDervativeParopertyIsin(derivativeList);
                getDervativeListByDervativeParopertyWKN(derivativeList);
                getDervativeListByDervativeParopertyUisin(derivativeList);
                getDervativeListByDervativeParopertyEmi(derivativeList);
                getDervativeListByDervativeParopertyCur(derivativeList);
                finalMap.putAll(filterIsinDerivateMap);
                finalMap.putAll(filterWknDerivateMap);
                finalMap.putAll(filterUisinDerivateMap);
                finalMap.putAll(filterEmiDerivateMap);
                finalMap.putAll(filterCurDerivateMap);
            }
        }catch (Exception e){
            log.error("Error getting while filter derivate object by derivate parameter key : ",e);
        }
    }

    private void getDervativeListByDervativeParopertyCur(List<Derivative> derivativeList) {
        try {
            derivativeList.stream().filter(Objects::nonNull).forEach(curDerivate -> {
                if (filterCurDerivateMap.get(curDerivate.getCur()) == null) {
                    List<Derivative> curList = new ArrayList<>();
                    curList.add(curDerivate);
                    filterCurDerivateMap.put(curDerivate.getCur(), curList);
                } else {
                    List<Derivative> curList = filterCurDerivateMap.get(curDerivate.getCur());
                    curList.add(curDerivate);
                    filterCurDerivateMap.put(curDerivate.getCur(), curList);
                }
            });
        }catch (Exception e){
            log.error("Error getting while prepare derivate cur map by derivate parameter key : ",e);
        }
    }

    private void getDervativeListByDervativeParopertyEmi(List<Derivative> derivativeList) {
        try {
            derivativeList.stream().filter(Objects::nonNull).forEach(emiDerivate -> {
                if (filterEmiDerivateMap.get(emiDerivate.getEmi()) == null) {
                    List<Derivative> emiList = new ArrayList<>();
                    emiList.add(emiDerivate);
                    filterEmiDerivateMap.put(emiDerivate.getEmi(), emiList);
                } else {
                    List<Derivative> emiList = filterEmiDerivateMap.get(emiDerivate.getEmi());
                    emiList.add(emiDerivate);
                    filterEmiDerivateMap.put(emiDerivate.getEmi(), emiList);
                }
            });
        }catch (Exception e){
            log.error("Error getting while prepare derivate emi map by derivate parameter key : ",e);
        }
    }

    private void getDervativeListByDervativeParopertyUisin(List<Derivative> derivativeList) {
        try {
            derivativeList.stream().filter(Objects::nonNull).forEach(uisinDerivate -> {
                if (filterUisinDerivateMap.get(uisinDerivate.getU_isin()) == null) {
                    List<Derivative> uisinList = new ArrayList<>();
                    uisinList.add(uisinDerivate);
                    filterUisinDerivateMap.put(uisinDerivate.getU_isin(), uisinList);
                } else {
                    List<Derivative> uisinList = filterUisinDerivateMap.get(uisinDerivate.getU_isin());
                    uisinList.add(uisinDerivate);
                    filterUisinDerivateMap.put(uisinDerivate.getU_isin(), uisinList);
                }
            });
        }catch (Exception e){
            log.error("Error getting while prepare derivate u_isin map by derivate parameter key : ",e);
        }
    }

    private void getDervativeListByDervativeParopertyWKN(List<Derivative> derivativeList) {
        try {
            derivativeList.stream().filter(Objects::nonNull).forEach(wknDerivate -> {
                if (filterWknDerivateMap.get(wknDerivate.getWkn()) == null) {
                    List<Derivative> wknList = new ArrayList<>();
                    wknList.add(wknDerivate);
                    filterWknDerivateMap.put(wknDerivate.getWkn(), wknList);
                } else {
                    List<Derivative> wknList = filterWknDerivateMap.get(wknDerivate.getWkn());
                    wknList.add(wknDerivate);
                    filterWknDerivateMap.put(wknDerivate.getWkn(), wknList);
                }
            });
        }catch (Exception e){
            log.error("Error getting while prepare derivate wkn map by derivate parameter key : ",e);
        }
    }

    private void getDervativeListByDervativeParopertyIsin(List<Derivative> derivativeList) {
        try {
            derivativeList.stream().filter(Objects::nonNull).forEach(isinDerivate -> {
                if (filterIsinDerivateMap.get(isinDerivate.getIsin()) == null) {
                    List<Derivative> isinList = new ArrayList<>();
                    isinList.add(isinDerivate);
                    filterIsinDerivateMap.put(isinDerivate.getIsin(), isinList);
                } else {
                    List<Derivative> isinList = filterIsinDerivateMap.get(isinDerivate.getIsin());
                    isinList.add(isinDerivate);
                    filterIsinDerivateMap.put(isinDerivate.getIsin(), isinList);
                }
            });
        }catch (Exception e){
            log.error("Error getting while prepare derivate isin map by derivate parameter key : ",e);
        }
    }

    @Override
    public List<Derivative> getDerivateListByDerivateParameterKey(Map<String, String> map, long from, long limit) {
        List<Derivative> list = new CopyOnWriteArrayList<>();
        try {
            if (finalMap.size() == 0)
                findDerivateListByFilterKeyword();
            List<Derivative> listByKeyResult = new CopyOnWriteArrayList<>();
            for(String value : map.values()){
                if(StringUtils.isNotBlank(map.get(DerivateEnum.QUANTO.name()))){
                    listByKeyResult = getDerivativeListByDerivateProperty(DerivateEnum.QUANTO.name(), map.get(DerivateEnum.QUANTO.name()).trim());
                }else if(StringUtils.isNotBlank(map.get(DerivateEnum.EXEC.name()))){
                    listByKeyResult = getDerivativeListByDerivateProperty(DerivateEnum.EXEC.name(), map.get(DerivateEnum.EXEC.name()).trim());
                }else if(StringUtils.isNotBlank(map.get(DerivateEnum.BV.name()))) {
                    listByKeyResult = getDerivativeListByDerivateProperty(DerivateEnum.BV.name(), map.get(DerivateEnum.BV.name()).trim());
                }else if(StringUtils.isNotBlank(map.get(DerivateEnum.CAP.name()))) {
                    listByKeyResult = getDerivativeListByDerivateProperty(DerivateEnum.CAP.name(), map.get(DerivateEnum.CAP.name()).trim());
                }else if(StringUtils.isNotBlank(map.get(DerivateEnum.ASK.name()))) {
                    listByKeyResult = getDerivativeListByDerivateProperty(DerivateEnum.ASK.name(), map.get(DerivateEnum.ASK.name()).trim());
                }else if(StringUtils.isNotBlank(map.get(DerivateEnum.BID.name()))) {
                    listByKeyResult = getDerivativeListByDerivateProperty(DerivateEnum.BID.name(), map.get(DerivateEnum.BID.name()).trim());
                }else if(StringUtils.isNotBlank(map.get(DerivateEnum.SIDE_WAYS_RETURN_PA.name()))){
                    listByKeyResult = getDerivativeListByDerivateProperty(DerivateEnum.SIDE_WAYS_RETURN_PA.name(), map.get(DerivateEnum.SIDE_WAYS_RETURN_PA.name()).trim());
                }else if(StringUtils.isNotBlank(map.get(DerivateEnum.MAT_DATE.name()))){
                    listByKeyResult = getDerivativeListByDerivateDateProperty(DerivateEnum.MAT_DATE.name(), map.get(DerivateEnum.MAT_DATE.name()).trim());
                }else if(StringUtils.isNotBlank(map.get(DerivateEnum.VAL_DATE.name()))){
                    listByKeyResult = getDerivativeListByDerivateDateProperty(DerivateEnum.VAL_DATE.name(), map.get(DerivateEnum.VAL_DATE.name()).trim());
                }else if(Stream.of(value).noneMatch(StringUtils::isNumeric)){
                    listByKeyResult = finalMap.get(value);
                }
                break;
            }
            list = getDerivativeListByStringFieldWithANDOperator(map, listByKeyResult);
        }catch (Exception e){
            log.error("Error getting while find derivate list object by derivate parameter key : ",e);
        }
        return list.stream().skip(from).limit(limit).collect(Collectors.toList());
    }

    private List<Derivative> getDerivativeListByDerivateDateProperty(String name, String value) {
        List<Derivative> list = new CopyOnWriteArrayList<>();
        try {
            List<Derivative> derivativeList = derivateMeasureReadFiles.getStammdatenAlleDateFromList();
            if(name.equalsIgnoreCase(DerivateEnum.MAT_DATE.name())){
                if(value.contains(">")){
                    long greaterMillisecondValue = dateFormat.parse(value.substring(value.indexOf(">") + 1)).getTime();
                    list = derivativeList.stream().filter(Objects::nonNull).filter(derivative -> derivative.getMatdate() != null && derivative.getMatdate().getTime() >= greaterMillisecondValue)
                            .collect(Collectors.toList());
                }else if(value.contains("<")){
                    long lessMillisecondValue = dateFormat.parse(value.substring(value.indexOf("<") + 1)).getTime();
                    list = derivativeList.stream().filter(Objects::nonNull).filter(derivative -> derivative.getMatdate() != null && derivative.getMatdate().getTime() <= lessMillisecondValue)
                            .collect(Collectors.toList());
                }
            }else if(name.equalsIgnoreCase(DerivateEnum.VAL_DATE.name())){
                if(value.contains(">")){
                    long greaterMillisecondValue = dateFormat.parse(value.substring(value.indexOf(">") + 1)).getTime();
                    list = derivativeList.stream().filter(Objects::nonNull).filter(derivative -> derivative.getValdate() != null && derivative.getValdate().getTime() >= greaterMillisecondValue)
                            .collect(Collectors.toList());
                }else if(value.contains("<")){
                    long lessMillisecondValue = dateFormat.parse(value.substring(value.indexOf("<") + 1)).getTime();
                    list = derivativeList.stream().filter(Objects::nonNull).filter(derivative -> derivative.getValdate() != null && derivative.getValdate().getTime() <= lessMillisecondValue)
                            .collect(Collectors.toList());
                }
            }
        }catch (Exception e){
            log.error("Error getting while prepare derivate list object by derivate date parameter key : ",e);
        }
        return list;
    }

    private List<Derivative> getDerivativeListByStringFieldWithANDOperator(Map<String, String> map, List<Derivative> listByKeyResult) {
        List<Derivative> list = new CopyOnWriteArrayList<>();
        try {
            for(Derivative derivative : Objects.requireNonNull(listByKeyResult)){
                int value = 0;
                for(Map.Entry<String, String> entry : map.entrySet()){
                    if(DerivateEnum.QUANTO.name().equalsIgnoreCase(entry.getKey())){
                        if(entry.getValue().contains(">") && derivative.getQuanto() >= Integer.valueOf(entry.getValue().substring(entry.getValue().indexOf(">") + 1))){
                            value++;
                        }else if(entry.getValue().contains("<") && derivative.getQuanto() <= Integer.valueOf(entry.getValue().substring(entry.getValue().indexOf("<") + 1))){
                            value++;
                        }
                    }else if(DerivateEnum.EXEC.name().equalsIgnoreCase(entry.getKey())){
                        if(entry.getValue().contains(">") && derivative.getExec() >= Integer.valueOf(entry.getValue().substring(entry.getValue().indexOf(">") + 1))){
                            value++;
                        }else if(entry.getValue().contains("<") && derivative.getExec() <= Integer.valueOf(entry.getValue().substring(entry.getValue().indexOf("<") + 1))){
                            value++;
                        }
                    }else if(DerivateEnum.BV.name().equalsIgnoreCase(entry.getKey())) {
                        if(entry.getValue().contains(">") && derivative.getBv() >= Integer.valueOf(entry.getValue().substring(entry.getValue().indexOf(">") + 1))){
                            value++;
                        }else if(entry.getValue().contains("<") && derivative.getBv() <= Integer.valueOf(entry.getValue().substring(entry.getValue().indexOf("<") + 1))){
                            value++;
                        }
                    }else if(DerivateEnum.CAP.name().equalsIgnoreCase(entry.getKey())) {
                        if(entry.getValue().contains(">") && derivative.getCap() >= Integer.valueOf(entry.getValue().substring(entry.getValue().indexOf(">") + 1))){
                            value++;
                        }else if(entry.getValue().contains("<") && derivative.getCap() <= Integer.valueOf(entry.getValue().substring(entry.getValue().indexOf("<") + 1))){
                            value++;
                        }
                    }else if(DerivateEnum.ASK.name().equalsIgnoreCase(entry.getKey())) {
                        if(entry.getValue().contains(">") && derivative.getAsk() >= Integer.valueOf(entry.getValue().substring(entry.getValue().indexOf(">") + 1))){
                            value++;
                        }else if(entry.getValue().contains("<") && derivative.getAsk() <= Integer.valueOf(entry.getValue().substring(entry.getValue().indexOf("<") + 1))){
                            value++;
                        }
                    }else if(DerivateEnum.BID.name().equalsIgnoreCase(entry.getKey())) {
                        if(entry.getValue().contains(">") && derivative.getBid() >= Integer.valueOf(entry.getValue().substring(entry.getValue().indexOf(">") + 1))){
                            value++;
                        }else if(entry.getValue().contains("<") && derivative.getBid() <= Integer.valueOf(entry.getValue().substring(entry.getValue().indexOf("<") + 1))){
                            value++;
                        }
                    }else if(DerivateEnum.SIDE_WAYS_RETURN_PA.name().equalsIgnoreCase(entry.getKey())){
                        if(entry.getValue().contains(">") && derivative.getSidewaysReturnPa() >= Integer.valueOf(entry.getValue().substring(entry.getValue().indexOf(">") + 1))){
                            value++;
                        }else if(entry.getValue().contains("<") && derivative.getSidewaysReturnPa() <= Integer.valueOf(entry.getValue().substring(entry.getValue().indexOf("<") + 1))){
                            value++;
                        }
                    }else if(DerivateEnum.ISIN.name().equalsIgnoreCase(entry.getKey()) && derivative.getIsin().equalsIgnoreCase(entry.getValue())){
                        value++;
                    }else if(DerivateEnum.WKN.name().equalsIgnoreCase(entry.getKey()) && derivative.getWkn().equalsIgnoreCase(entry.getValue())){
                        value++;
                    }else if(DerivateEnum.U_ISIN.name().equalsIgnoreCase(entry.getKey()) && derivative.getU_isin().equalsIgnoreCase(entry.getValue())){
                        value++;
                    }else if(DerivateEnum.EMI.name().equalsIgnoreCase(entry.getKey()) && derivative.getEmi().equalsIgnoreCase(entry.getValue())){
                        value++;
                    }else if(DerivateEnum.CUR.name().equalsIgnoreCase(entry.getKey()) && derivative.getCur().equalsIgnoreCase(entry.getValue())){
                        value++;
                    }else if(DerivateEnum.MAT_DATE.name().equalsIgnoreCase(entry.getKey())) {
                        if (entry.getValue().contains(">") && derivative.getMatdate() != null){
                            long matGreaterMilli = dateFormat.parse(entry.getValue().substring(entry.getValue().indexOf(">") + 1)).getTime();
                            if(derivative.getMatdate().getTime() >= matGreaterMilli)
                                value++;
                        }else if (entry.getValue().contains("<") && derivative.getMatdate() != null){
                            long matLessMilli = dateFormat.parse(entry.getValue().substring(entry.getValue().indexOf("<") + 1)).getTime();
                            if(derivative.getMatdate().getTime() <= matLessMilli)
                                value++;
                        }
                    }else if(DerivateEnum.VAL_DATE.name().equalsIgnoreCase(entry.getKey())) {
                        if (entry.getValue().contains(">") && derivative.getValdate() != null){
                            long valGreaterMilli = dateFormat.parse(entry.getValue().substring(entry.getValue().indexOf(">") + 1)).getTime();
                            if(derivative.getValdate().getTime() >= valGreaterMilli)
                                value++;
                        }else if (entry.getValue().contains("<") && derivative.getValdate() != null){
                            long valLessMilli = dateFormat.parse(entry.getValue().substring(entry.getValue().indexOf("<") + 1)).getTime();
                            if(derivative.getValdate().getTime() <= valLessMilli)
                                value++;
                        }
                    }
                }
                if(map.size() == value)
                    list.add(derivative);
            }
        }catch (Exception e){
            log.error("Error getting while prepare derivative list by string field with AND operator : ",e);
        }
        return list;
    }

    @Override
    public Map<String, String> getDerivateStringListByDerivateStringField(String filterIsin, String filterWkn, String filterUisin,
                                                                          String filterEmi, String filterCur, String filterQuanto,
                                                                          String filterExec, String filterBv, String filterCap,
                                                                          String filterAsk, String filterBid, String filterSidewaysReturnPa,
                                                                          String filterMatDate, String filterValDate) {
        Map<String, String> map = new HashMap<>();
        try {
            if(StringUtils.isNotBlank(filterIsin)){
                map.put(DerivateEnum.ISIN.name(), filterIsin);
            }
            if(StringUtils.isNotBlank(filterWkn)){
                map.put(DerivateEnum.WKN.name(), filterWkn);
            }
            if(StringUtils.isNotBlank(filterUisin)){
                map.put(DerivateEnum.U_ISIN.name(), filterUisin);
            }
            if(StringUtils.isNotBlank(filterEmi)){
                map.put(DerivateEnum.EMI.name(), filterEmi);
            }
            if(StringUtils.isNotBlank(filterCur)){
                map.put(DerivateEnum.CUR.name(), filterCur);
            }
            if(StringUtils.isNotBlank(filterQuanto)){
                map.put(DerivateEnum.QUANTO.name(), filterQuanto);
            }
            if(StringUtils.isNotBlank(filterExec)){
                map.put(DerivateEnum.EXEC.name(), filterExec);
            }
            if(StringUtils.isNotBlank(filterBv)){
                map.put(DerivateEnum.BV.name(), filterBv);
            }
            if(StringUtils.isNotBlank(filterCap)){
                map.put(DerivateEnum.CAP.name(), filterCap);
            }
            if(StringUtils.isNotBlank(filterAsk)){
                map.put(DerivateEnum.ASK.name(), filterAsk);
            }
            if(StringUtils.isNotBlank(filterBid)){
                map.put(DerivateEnum.BID.name(), filterBid);
            }
            if(StringUtils.isNotBlank(filterSidewaysReturnPa)){
                map.put(DerivateEnum.SIDE_WAYS_RETURN_PA.name(), filterSidewaysReturnPa);
            }
            if(StringUtils.isNotBlank(filterMatDate)){
                map.put(DerivateEnum.MAT_DATE.name(), filterMatDate);
            }
            if(StringUtils.isNotBlank(filterValDate)){
                map.put(DerivateEnum.VAL_DATE.name(), filterValDate);
            }
        }catch (Exception e){
            log.error("Error getting while adding derivative string field in map : ",e);
        }
        return map;
    }

    private List<Derivative> getDerivativeListByDerivateProperty(String name, String value) {
        List<Derivative> list = new CopyOnWriteArrayList<>();
        try {
            List<Derivative> derivativeList = derivateMeasureReadFiles.getStammdatenAlleDateFromList();
            if(name.equalsIgnoreCase(DerivateEnum.QUANTO.name())){
                if(value.contains(">")){
                    list = derivativeList.stream().filter(Objects::nonNull).filter(derivative -> derivative.getQuanto() != null && derivative.getQuanto() >= Integer.valueOf(value.substring(value.indexOf(">") + 1)))
                            .collect(Collectors.toList());
                }else if(value.contains("<")){
                    list = derivativeList.stream().filter(Objects::nonNull).filter(derivative -> derivative.getQuanto() != null && derivative.getQuanto() <= Integer.valueOf(value.substring(value.indexOf("<") + 1)))
                            .collect(Collectors.toList());
                }
            }else if(name.equalsIgnoreCase(DerivateEnum.EXEC.name())){
                if(value.contains(">")){
                    list = derivativeList.stream().filter(Objects::nonNull).filter(derivative -> derivative.getExec() != null && derivative.getExec() >= Integer.valueOf(value.substring(value.indexOf(">") + 1)))
                            .collect(Collectors.toList());
                }else if(value.contains("<")){
                    list = derivativeList.stream().filter(Objects::nonNull).filter(derivative -> derivative.getExec() != null && derivative.getExec() <= Integer.valueOf(value.substring(value.indexOf("<") + 1)))
                            .collect(Collectors.toList());
                }
            }else if(name.equalsIgnoreCase(DerivateEnum.BV.name())){
                if(value.contains(">")){
                    list = derivativeList.stream().filter(Objects::nonNull).filter(derivative -> derivative.getBv() != null && derivative.getBv() >= Integer.valueOf(value.substring(value.indexOf(">") + 1)))
                            .collect(Collectors.toList());
                }else if(value.contains("<")){
                    list = derivativeList.stream().filter(Objects::nonNull).filter(derivative -> derivative.getBv() != null && derivative.getBv() <= Integer.valueOf(value.substring(value.indexOf("<") + 1)))
                            .collect(Collectors.toList());
                }
            }else if(name.equalsIgnoreCase(DerivateEnum.CAP.name())){
                if(value.contains(">")){
                    list = derivativeList.stream().filter(Objects::nonNull).filter(derivative -> derivative.getCap() != null && derivative.getCap() >= Integer.valueOf(value.substring(value.indexOf(">") + 1)))
                            .collect(Collectors.toList());
                }else if(value.contains("<")){
                    list = derivativeList.stream().filter(Objects::nonNull).filter(derivative -> derivative.getCap() != null && derivative.getCap() <= Integer.valueOf(value.substring(value.indexOf("<") + 1)))
                            .collect(Collectors.toList());
                }
            }else if(name.equalsIgnoreCase(DerivateEnum.ASK.name())){
                if(value.contains(">")){
                    list = derivativeList.stream().filter(Objects::nonNull).filter(derivative -> derivative.getAsk() != null && derivative.getAsk() >= Integer.valueOf(value.substring(value.indexOf(">") + 1)))
                            .collect(Collectors.toList());
                }else if(value.contains("<")){
                    list = derivativeList.stream().filter(Objects::nonNull).filter(derivative -> derivative.getAsk() != null && derivative.getAsk() <= Integer.valueOf(value.substring(value.indexOf("<") + 1)))
                            .collect(Collectors.toList());
                }
            }else if(name.equalsIgnoreCase(DerivateEnum.BID.name())){
                if(value.contains(">")){
                    list = derivativeList.stream().filter(Objects::nonNull).filter(derivative -> derivative.getBid() != null && derivative.getBid() >= Integer.valueOf(value.substring(value.indexOf(">") + 1)))
                            .collect(Collectors.toList());
                }else if(value.contains("<")){
                    list = derivativeList.stream().filter(Objects::nonNull).filter(derivative -> derivative.getBid() != null && derivative.getBid() <= Integer.valueOf(value.substring(value.indexOf("<") + 1)))
                            .collect(Collectors.toList());
                }
            }else if(name.equalsIgnoreCase(DerivateEnum.SIDE_WAYS_RETURN_PA.name())){
                if(value.contains(">")){
                    list = derivativeList.stream().filter(Objects::nonNull).filter(derivative -> derivative.getSidewaysReturnPa() != null && derivative.getSidewaysReturnPa() >= Integer.valueOf(value.substring(value.indexOf(">") + 1)))
                            .collect(Collectors.toList());
                }else if(value.contains("<")){
                    list = derivativeList.stream().filter(Objects::nonNull).filter(derivative -> derivative.getSidewaysReturnPa() != null &&  derivative.getSidewaysReturnPa() <= Integer.valueOf(value.substring(value.indexOf("<") + 1)))
                            .collect(Collectors.toList());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("Error getting while prepare derivate numeric field list by derivate parameter key : ",e);
        }
        return list;
    }
}
