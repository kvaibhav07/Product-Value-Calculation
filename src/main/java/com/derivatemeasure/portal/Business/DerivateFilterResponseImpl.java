package com.derivatemeasure.portal.Business;

import com.derivatemeasure.portal.Constant.DerivateEnum;
import com.derivatemeasure.portal.Constant.DerivateMeasureConstant;
import com.derivatemeasure.portal.Model.Derivative;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DerivateFilterResponseImpl implements DerivateFilterResponse{

    private static final Logger log = LoggerFactory.getLogger(DerivateFilterResponseImpl.class);

    @Autowired
    private DerivateMeasureReadFiles derivateMeasureReadFiles;

    private static final DateFormat dateFormat = new SimpleDateFormat("MMM DD, yyyy, HH:MM:SS a");

    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(18);

    Map<String, Set<Derivative>> finalMap = new ConcurrentHashMap<>();
    Map<String, Set<Derivative>> filterIsinDerivateMap = new ConcurrentHashMap<>();
    Map<String, Set<Derivative>> filterWknDerivateMap = new ConcurrentHashMap<>();
    Map<String, Set<Derivative>> filterUisinDerivateMap = new ConcurrentHashMap<>();
    Map<String, Set<Derivative>> filterEmiDerivateMap = new ConcurrentHashMap<>();
    Map<String, Set<Derivative>> filterCurDerivateMap = new ConcurrentHashMap<>();
    Map<Integer, Set<Derivative>> filterQuantoDerivateMap = new ConcurrentHashMap<>();
    Map<Integer, Set<Derivative>> filterExecDerivateMap = new ConcurrentHashMap<>();
    Map<Double, Set<Derivative>> filterBvDerivateMap = new ConcurrentHashMap<>();
    Map<Double, Set<Derivative>> filterCapDerivateMap = new ConcurrentHashMap<>();
    Map<Double, Set<Derivative>> filterAskDerivateMap = new ConcurrentHashMap<>();
    Map<Double, Set<Derivative>> filterBidDerivateMap = new ConcurrentHashMap<>();
    Map<Double, Set<Derivative>> filterSidewaysReturnPaDerivateMap = new ConcurrentHashMap<>();
    Map<Long, Set<Derivative>> filterMatdateDerivateMap = new ConcurrentHashMap<>();
    Map<Long, Set<Derivative>> filterValdateDerivateMap = new ConcurrentHashMap<>();
    Map<String, Set<Derivative>> filterTypeDerivateMap = new ConcurrentHashMap<>();

    @Override
    public void findDerivateListByFilterKeyword() {
        try {
            synchronized (this){
                Set<Derivative> derivativeSet = derivateMeasureReadFiles.getStammdatenAlleDateFromList();
                executor.submit(() -> getDervativeSetByDervativeParopertyIsin(derivativeSet));
                executor.submit(() -> getDervativeSetByDervativeParopertyWKN(derivativeSet));
                executor.submit(() -> getDervativeSetByDervativeParopertyUisin(derivativeSet));
                executor.submit(() -> getDervativeSetByDervativeParopertyEmi(derivativeSet));
                executor.submit(() -> getDervativeSetByDervativeParopertyCur(derivativeSet));
                executor.submit(() -> getDervativeSetByDervativeParopertyType(derivativeSet));
            }
        }catch (Exception e){
            log.error("Error getting while filter derivate object by derivate parameter key : ",e);
        }
    }

    private void getDervativeSetByDervativeParopertyType(Set<Derivative> derivativeSet) {
        try {
            derivativeSet.stream().filter(Objects::nonNull).forEach(typeDerivate -> {
                synchronized (typeDerivate){
                    if (filterTypeDerivateMap.get(String.valueOf(typeDerivate.getType())) == null) {
                        Set<Derivative> typeSet = new HashSet<>();
                        typeSet.add(typeDerivate);
                        filterTypeDerivateMap.put(String.valueOf(typeDerivate.getType()), typeSet);
                    } else {
                        Set<Derivative> typeSet = filterTypeDerivateMap.get(String.valueOf(typeDerivate.getType()));
                        typeSet.add(typeDerivate);
                        filterTypeDerivateMap.put(String.valueOf(typeDerivate.getType()), typeSet);
                    }
                }
            });
        }catch (Exception e){
            log.error("Error getting while prepare derivate type map by derivate parameter key : ",e);
        }
    }

    private void getDervativeSetByDervativeParopertyCur(Set<Derivative> derivativeSet) {
        try {
            derivativeSet.stream().filter(Objects::nonNull).forEach(curDerivate -> {
               synchronized (curDerivate){
                   if (filterCurDerivateMap.get(curDerivate.getCur()) == null) {
                       Set<Derivative> curSet = new HashSet<>();
                       curSet.add(curDerivate);
                       filterCurDerivateMap.put(curDerivate.getCur(), curSet);
                   } else {
                       Set<Derivative> curSet = filterCurDerivateMap.get(curDerivate.getCur());
                       curSet.add(curDerivate);
                       filterCurDerivateMap.put(curDerivate.getCur(), curSet);
                   }
               }
            });
        }catch (Exception e){
            log.error("Error getting while prepare derivate cur map by derivate parameter key : ",e);
        }
    }

    private void getDervativeSetByDervativeParopertyEmi(Set<Derivative> derivativeSet) {
        try {
            derivativeSet.stream().filter(Objects::nonNull).forEach(emiDerivate -> {
                if (filterEmiDerivateMap.get(emiDerivate.getEmi()) == null) {
                    Set<Derivative> emiSet = new HashSet<>();
                    emiSet.add(emiDerivate);
                    filterEmiDerivateMap.put(emiDerivate.getEmi(), emiSet);
                } else {
                    Set<Derivative> emiSet = filterEmiDerivateMap.get(emiDerivate.getEmi());
                    emiSet.add(emiDerivate);
                    filterEmiDerivateMap.put(emiDerivate.getEmi(), emiSet);
                }
            });
        }catch (Exception e){
            log.error("Error getting while prepare derivate emi map by derivate parameter key : ",e);
        }
    }

    private void getDervativeSetByDervativeParopertyUisin(Set<Derivative> derivativeSet) {
        try {
            derivativeSet.stream().filter(Objects::nonNull).forEach(uisinDerivate -> {
                if (filterUisinDerivateMap.get(uisinDerivate.getU_isin()) == null) {
                    Set<Derivative> uisinSet = new HashSet<>();
                    uisinSet.add(uisinDerivate);
                    filterUisinDerivateMap.put(uisinDerivate.getU_isin(), uisinSet);
                } else {
                    Set<Derivative> uisinSet = filterUisinDerivateMap.get(uisinDerivate.getU_isin());
                    uisinSet.add(uisinDerivate);
                    filterUisinDerivateMap.put(uisinDerivate.getU_isin(), uisinSet);
                }
            });
        }catch (Exception e){
            log.error("Error getting while prepare derivate u_isin map by derivate parameter key : ",e);
        }
    }

    private void getDervativeSetByDervativeParopertyWKN(Set<Derivative> derivativeSet) {
        try {
            derivativeSet.stream().filter(Objects::nonNull).forEach(wknDerivate -> {
                if (filterWknDerivateMap.get(wknDerivate.getWkn()) == null) {
                    Set<Derivative> wknSet = new HashSet<>();
                    wknSet.add(wknDerivate);
                    filterWknDerivateMap.put(wknDerivate.getWkn(), wknSet);
                } else {
                    Set<Derivative> wknSet = filterWknDerivateMap.get(wknDerivate.getWkn());
                    wknSet.add(wknDerivate);
                    filterWknDerivateMap.put(wknDerivate.getWkn(), wknSet);
                }
            });
        }catch (Exception e){
            log.error("Error getting while prepare derivate wkn map by derivate parameter key : ",e);
        }
    }

    private void getDervativeSetByDervativeParopertyIsin(Set<Derivative> derivativeSet) {
        try {
            derivativeSet.stream().filter(Objects::nonNull).forEach(isinDerivate -> {
                if (filterIsinDerivateMap.get(isinDerivate.getIsin()) == null) {
                    Set<Derivative> isinSet = new HashSet<>();
                    isinSet.add(isinDerivate);
                    filterIsinDerivateMap.put(isinDerivate.getIsin(), isinSet);
                } else {
                    Set<Derivative> isinSet = filterIsinDerivateMap.get(isinDerivate.getIsin());
                    isinSet.add(isinDerivate);
                    filterIsinDerivateMap.put(isinDerivate.getIsin(), isinSet);
                }
            });
        }catch (Exception e){
            log.error("Error getting while prepare derivate isin map by derivate parameter key : ",e);
        }
    }

    @Override
    public List<Derivative> getDerivateListByDerivateParameterKey(Map<String, String> map, String sortByField, String order, long from, long limit) {
        List<Derivative> finalSetResponse = new CopyOnWriteArrayList<>();
        Set<Derivative> set = new HashSet<>();
        try {
            if (finalMap.size() == 0)
                prepareDerivateFinalMapByStringFilterKeyword();
            Set<Derivative> setByKeyResult = new HashSet<>();
            for(String value : map.values()){
                if(StringUtils.isNotBlank(map.get(DerivateEnum.QUANTO.name()))){
                    setByKeyResult = getDerivativeSetByDerivateProperty(DerivateEnum.QUANTO.name(), map.get(DerivateEnum.QUANTO.name()).trim());
                }else if(StringUtils.isNotBlank(map.get(DerivateEnum.EXEC.name()))){
                    setByKeyResult = getDerivativeSetByDerivateProperty(DerivateEnum.EXEC.name(), map.get(DerivateEnum.EXEC.name()).trim());
                }else if(StringUtils.isNotBlank(map.get(DerivateEnum.BV.name()))) {
                    setByKeyResult = getDerivativeSetByDerivateProperty(DerivateEnum.BV.name(), map.get(DerivateEnum.BV.name()).trim());
                }else if(StringUtils.isNotBlank(map.get(DerivateEnum.CAP.name()))) {
                    setByKeyResult = getDerivativeSetByDerivateProperty(DerivateEnum.CAP.name(), map.get(DerivateEnum.CAP.name()).trim());
                }else if(StringUtils.isNotBlank(map.get(DerivateEnum.ASK.name()))) {
                    setByKeyResult = getDerivativeSetByDerivateProperty(DerivateEnum.ASK.name(), map.get(DerivateEnum.ASK.name()).trim());
                }else if(StringUtils.isNotBlank(map.get(DerivateEnum.BID.name()))) {
                    setByKeyResult = getDerivativeSetByDerivateProperty(DerivateEnum.BID.name(), map.get(DerivateEnum.BID.name()).trim());
                }else if(StringUtils.isNotBlank(map.get(DerivateEnum.SIDE_WAYS_RETURN_PA.name()))){
                    setByKeyResult = getDerivativeSetByDerivateProperty(DerivateEnum.SIDE_WAYS_RETURN_PA.name(), map.get(DerivateEnum.SIDE_WAYS_RETURN_PA.name()).trim());
                }else if(StringUtils.isNotBlank(map.get(DerivateEnum.MAT_DATE.name()))){
                    setByKeyResult = getDerivativeSetByDerivateDateProperty(DerivateEnum.MAT_DATE.name(), map.get(DerivateEnum.MAT_DATE.name()).trim());
                }else if(StringUtils.isNotBlank(map.get(DerivateEnum.VAL_DATE.name()))){
                    setByKeyResult = getDerivativeSetByDerivateDateProperty(DerivateEnum.VAL_DATE.name(), map.get(DerivateEnum.VAL_DATE.name()).trim());
                }else if(StringUtils.isNotBlank(map.get(DerivateEnum.TYPE.name()))){
                    setByKeyResult = finalMap.get(value);
                }else if(Stream.of(value).noneMatch(StringUtils::isNumeric)){
                    setByKeyResult = finalMap.get(value);
                }
                break;
            }
            set = getDerivativeListByStringFieldWithANDOperator(map, setByKeyResult);
        }catch (Exception e){
            log.error("Error getting while find derivate list object by derivate parameter key : ",e);
        }
        finalSetResponse = sortDerivateDataByField(set.stream().skip(from).limit(limit).collect(Collectors.toList()), sortByField, order);
        return finalSetResponse;
    }

    private List<Derivative> sortDerivateDataByField(List<Derivative> finalListResponse, String sortByField, String order) {
        List<Derivative> list = new CopyOnWriteArrayList<>();
        try {
            if(StringUtils.isNotBlank(sortByField) && StringUtils.isNotBlank(order)){
                if(sortByField.equalsIgnoreCase(DerivateEnum.ISIN.name())){
                    if(order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_ASCENDING)) {
                        Collections.sort(finalListResponse, new Comparator<Derivative>() {
                            @Override
                            public int compare(Derivative o1, Derivative o2) {
                                return o1.getIsin().compareToIgnoreCase(o2.getIsin());
                            }
                        });
                        list = finalListResponse;
                    }else if(order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_DESCENDING)) {
                        Collections.sort(finalListResponse, new Comparator<Derivative>() {
                            @Override
                            public int compare(Derivative o1, Derivative o2) {
                                return o2.getIsin().compareToIgnoreCase(o1.getIsin());
                            }
                        });
                        list = finalListResponse;
                    }
                }else if(sortByField.equalsIgnoreCase(DerivateEnum.TYPE.name())){
                    if(order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_ASCENDING)) {
                        Collections.sort(finalListResponse, new Comparator<Derivative>() {
                            @Override
                            public int compare(Derivative o1, Derivative o2) {
                                return String.valueOf(o1.getType()).compareToIgnoreCase(String.valueOf(o2.getType()));
                            }
                        });
                        list = finalListResponse;
                    }else if(order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_DESCENDING)) {
                        Collections.sort(finalListResponse, new Comparator<Derivative>() {
                            @Override
                            public int compare(Derivative o1, Derivative o2) {
                                return String.valueOf(o2.getType()).compareToIgnoreCase(String.valueOf(o1.getType()));
                            }
                        });
                        list = finalListResponse;
                    }
                }else if(sortByField.equalsIgnoreCase(DerivateEnum.WKN.name())){
                    if(order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_ASCENDING)) {
                        Collections.sort(finalListResponse, new Comparator<Derivative>() {
                            @Override
                            public int compare(Derivative o1, Derivative o2) {
                                return o1.getWkn().compareToIgnoreCase(o2.getWkn());
                            }
                        });
                        list = finalListResponse;
                    }else if(order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_DESCENDING)) {
                        Collections.sort(finalListResponse, new Comparator<Derivative>() {
                            @Override
                            public int compare(Derivative o1, Derivative o2) {
                                return o2.getWkn().compareToIgnoreCase(o1.getWkn());
                            }
                        });
                        list = finalListResponse;
                    }
                }else if(sortByField.equalsIgnoreCase(DerivateEnum.U_ISIN.name())){
                    if(order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_ASCENDING)) {
                        Collections.sort(finalListResponse, new Comparator<Derivative>() {
                            @Override
                            public int compare(Derivative o1, Derivative o2) {
                                return o1.getU_isin().compareToIgnoreCase(o2.getU_isin());
                            }
                        });
                        list = finalListResponse;
                    }else if(order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_DESCENDING)) {
                        Collections.sort(finalListResponse, new Comparator<Derivative>() {
                            @Override
                            public int compare(Derivative o1, Derivative o2) {
                                return o2.getU_isin().compareToIgnoreCase(o1.getU_isin());
                            }
                        });
                        list = finalListResponse;
                    }
                }else if(sortByField.equalsIgnoreCase(DerivateEnum.EMI.name())){
                    if(order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_ASCENDING)) {
                        Collections.sort(finalListResponse, new Comparator<Derivative>() {
                            @Override
                            public int compare(Derivative o1, Derivative o2) {
                                return o1.getEmi().compareToIgnoreCase(o2.getEmi());
                            }
                        });
                        list = finalListResponse;
                    }else if(order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_DESCENDING)) {
                        Collections.sort(finalListResponse, new Comparator<Derivative>() {
                            @Override
                            public int compare(Derivative o1, Derivative o2) {
                                return o2.getEmi().compareToIgnoreCase(o1.getEmi());
                            }
                        });
                        list = finalListResponse;
                    }
                }else if(sortByField.equalsIgnoreCase(DerivateEnum.CUR.name())){
                    if(order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_ASCENDING)) {
                        Collections.sort(finalListResponse, new Comparator<Derivative>() {
                            @Override
                            public int compare(Derivative o1, Derivative o2) {
                                return o1.getCur().compareToIgnoreCase(o2.getCur());
                            }
                        });
                        list = finalListResponse;
                    }else if(order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_DESCENDING)) {
                        Collections.sort(finalListResponse, new Comparator<Derivative>() {
                            @Override
                            public int compare(Derivative o1, Derivative o2) {
                                return o2.getCur().compareToIgnoreCase(o1.getCur());
                            }
                        });
                        list = finalListResponse;
                    }
                }else if(sortByField.equalsIgnoreCase(DerivateEnum.QUANTO.name())){
                    if(order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_ASCENDING)) {
                        Collections.sort(finalListResponse, new Comparator<Derivative>() {
                            @Override
                            public int compare(Derivative o1, Derivative o2) {
                                return o1.getQuanto() < o2.getQuanto() ? -1 : o1.getQuanto() == o2.getQuanto() ? 0 : 1;
                            }
                        });
                        list = finalListResponse;
                    }else if(order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_DESCENDING)) {
                        Collections.sort(finalListResponse, new Comparator<Derivative>() {
                            @Override
                            public int compare(Derivative o1, Derivative o2) {
                                return o1.getQuanto() < o2.getQuanto() ? 1 : o1.getQuanto() == o2.getQuanto() ? 0 : -1;
                            }
                        });
                        list = finalListResponse;
                    }
                }else if(sortByField.equalsIgnoreCase(DerivateEnum.EXEC.name())){
                    if(order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_ASCENDING)) {
                        Collections.sort(finalListResponse, new Comparator<Derivative>() {
                            @Override
                            public int compare(Derivative o1, Derivative o2) {
                                return o1.getExec() < o2.getExec() ? -1 : o1.getExec() == o2.getExec() ? 0 : 1;
                            }
                        });
                        list = finalListResponse;
                    }else if(order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_DESCENDING)) {
                        Collections.sort(finalListResponse, new Comparator<Derivative>() {
                            @Override
                            public int compare(Derivative o1, Derivative o2) {
                                return o1.getExec() < o2.getExec() ? 1 : o1.getExec() == o2.getExec() ? 0 : -1;
                            }
                        });
                        list = finalListResponse;
                    }
                }else if(sortByField.equalsIgnoreCase(DerivateEnum.BV.name())){
                    if(order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_ASCENDING)) {
                        Collections.sort(finalListResponse, new Comparator<Derivative>() {
                            @Override
                            public int compare(Derivative o1, Derivative o2) {
                                return o1.getBv() < o2.getBv() ? -1 : o1.getBv() == o2.getBv() ? 0 : 1;
                            }
                        });
                        list = finalListResponse;
                    }else if(order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_DESCENDING)) {
                        Collections.sort(finalListResponse, new Comparator<Derivative>() {
                            @Override
                            public int compare(Derivative o1, Derivative o2) {
                                return o1.getBv() < o2.getBv() ? 1 : o1.getBv() == o2.getBv() ? 0 : -1;
                            }
                        });
                        list = finalListResponse;
                    }
                }else if(sortByField.equalsIgnoreCase(DerivateEnum.CAP.name())){
                    if(order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_ASCENDING)) {
                        Collections.sort(finalListResponse, new Comparator<Derivative>() {
                            @Override
                            public int compare(Derivative o1, Derivative o2) {
                                return o1.getCap() < o2.getCap() ? -1 : o1.getCap() == o2.getCap() ? 0 : 1;
                            }
                        });
                        list = finalListResponse;
                    }else if(order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_DESCENDING)) {
                        Collections.sort(finalListResponse, new Comparator<Derivative>() {
                            @Override
                            public int compare(Derivative o1, Derivative o2) {
                                return o1.getCap() < o2.getCap() ? 1 : o1.getCap() == o2.getCap() ? 0 : -1;
                            }
                        });
                        list = finalListResponse;
                    }
                }else if(sortByField.equalsIgnoreCase(DerivateEnum.ASK.name())){
                    if(order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_ASCENDING)) {
                        Collections.sort(finalListResponse, new Comparator<Derivative>() {
                            @Override
                            public int compare(Derivative o1, Derivative o2) {
                                return o1.getAsk() < o2.getAsk() ? -1 : o1.getAsk() == o2.getAsk() ? 0 : 1;
                            }
                        });
                        list = finalListResponse;
                    }else if(order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_DESCENDING)) {
                        Collections.sort(finalListResponse, new Comparator<Derivative>() {
                            @Override
                            public int compare(Derivative o1, Derivative o2) {
                                return o1.getAsk() < o2.getAsk() ? 1 : o1.getAsk() == o2.getAsk() ? 0 : -1;
                            }
                        });
                        list = finalListResponse;
                    }
                }else if(sortByField.equalsIgnoreCase(DerivateEnum.BID.name())){
                    if(order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_ASCENDING)) {
                        Collections.sort(finalListResponse, new Comparator<Derivative>() {
                            @Override
                            public int compare(Derivative o1, Derivative o2) {
                                return o1.getBid() < o2.getBid() ? -1 : o1.getBid() == o2.getBid() ? 0 : 1;
                            }
                        });
                        list = finalListResponse;
                    }else if(order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_DESCENDING)) {
                        Collections.sort(finalListResponse, new Comparator<Derivative>() {
                            @Override
                            public int compare(Derivative o1, Derivative o2) {
                                return o1.getBid() < o2.getBid() ? 1 : o1.getBid() == o2.getBid() ? 0 : -1;
                            }
                        });
                        list = finalListResponse;
                    }
                }else if(sortByField.equalsIgnoreCase(DerivateEnum.SIDE_WAYS_RETURN_PA.name())){
                    if(order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_ASCENDING)) {
                        Collections.sort(finalListResponse, new Comparator<Derivative>() {
                            @Override
                            public int compare(Derivative o1, Derivative o2) {
                                return o1.getSidewaysReturnPa() < o2.getSidewaysReturnPa() ? -1 : o1.getSidewaysReturnPa() == o2.getSidewaysReturnPa() ? 0 : 1;
                            }
                        });
                        list = finalListResponse;
                    }else if(order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_DESCENDING)) {
                        Collections.sort(finalListResponse, new Comparator<Derivative>() {
                            @Override
                            public int compare(Derivative o1, Derivative o2) {
                                return o1.getSidewaysReturnPa() < o2.getSidewaysReturnPa() ? 1 : o1.getSidewaysReturnPa() == o2.getSidewaysReturnPa() ? 0 : -1;
                            }
                        });
                        list = finalListResponse;
                    }
                }else if(sortByField.equalsIgnoreCase(DerivateEnum.MAT_DATE.name())){
                    if(order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_ASCENDING)) {
                        Collections.sort(finalListResponse, new Comparator<Derivative>() {
                            @Override
                            public int compare(Derivative o1, Derivative o2) {
                                if(o1.getMatdate() != null && o2.getMatdate() != null){
                                    return o1.getMatdate().getTime() < o2.getMatdate().getTime() ? -1 : o1.getMatdate().getTime() == o2.getMatdate().getTime() ? 0 : 1;
                                }
                                return 0;
                            }
                        });
                        list = finalListResponse;
                    }else if(order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_DESCENDING)) {
                        Collections.sort(finalListResponse, new Comparator<Derivative>() {
                            @Override
                            public int compare(Derivative o1, Derivative o2) {
                                if(o1.getMatdate() != null && o2.getMatdate() != null){
                                    return o1.getMatdate().getTime() < o2.getMatdate().getTime() ? 1 : o1.getMatdate().getTime() == o2.getMatdate().getTime() ? 0 : -1;
                                }
                                return 0;
                            }
                        });
                        list = finalListResponse;
                    }
                }else if(sortByField.equalsIgnoreCase(DerivateEnum.VAL_DATE.name())){
                    if(order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_ASCENDING)) {
                        Collections.sort(finalListResponse, new Comparator<Derivative>() {
                            @Override
                            public int compare(Derivative o1, Derivative o2) {
                                if(o1.getValdate() != null && o2.getValdate() != null){
                                    return o1.getValdate().getTime() < o2.getValdate().getTime() ? -1 : o1.getValdate().getTime() == o2.getValdate().getTime() ? 0 : 1;
                                }
                                return 0;
                            }
                        });
                        list = finalListResponse;
                    }else if(order.toLowerCase().equalsIgnoreCase(DerivateMeasureConstant.DERIVATE_MEASURE_DESCENDING)) {
                        Collections.sort(finalListResponse, new Comparator<Derivative>() {
                            @Override
                            public int compare(Derivative o1, Derivative o2) {
                                if(o1.getValdate() != null && o2.getValdate() != null){
                                    return o1.getValdate().getTime() < o2.getValdate().getTime() ? 1 : o1.getValdate().getTime() == o2.getValdate().getTime() ? 0 : -1;
                                }
                                return 0;
                            }
                        });
                        list = finalListResponse;
                    }
                }
            }else{
                return finalListResponse;
            }
        }catch (Exception e){
            log.error("Error getting while sort derivative data by derivative field : ",e);
        }
        return list;
    }

    private void prepareDerivateFinalMapByStringFilterKeyword() {
        try {
            if(filterIsinDerivateMap.size() == 0 && filterWknDerivateMap.size() == 0 && filterUisinDerivateMap.size() == 0
                    && filterEmiDerivateMap.size() == 0 && filterCurDerivateMap.size() == 0 && filterTypeDerivateMap.size() == 0)
                findDerivateListByFilterKeyword();
            finalMap.putAll(filterIsinDerivateMap);
            finalMap.putAll(filterWknDerivateMap);
            finalMap.putAll(filterUisinDerivateMap);
            finalMap.putAll(filterEmiDerivateMap);
            finalMap.putAll(filterCurDerivateMap);
            finalMap.putAll(filterTypeDerivateMap);
        } catch (Exception e) {
            log.error("Error getting while prepare derivate map object by string filter keyword : ",e);
        }
    }

    private Set<Derivative> getDerivativeSetByDerivateDateProperty(String name, String value) {
        Set<Derivative> set = new HashSet<>();
        try {
            Set<Derivative> derivativeSet = derivateMeasureReadFiles.getStammdatenAlleDateFromList();
           if(name.equalsIgnoreCase(DerivateEnum.MAT_DATE.name())){
               if (filterMatdateDerivateMap.size() == 0)
                   findDerivateListByFilterIntegerKeyword();
                if(value.contains(">")){
                    long greaterMillisecondValue = dateFormat.parse(value.substring(value.indexOf(">") + 1)).getTime();
                    for (Long matDateLong :filterMatdateDerivateMap.keySet()) {
                        if(matDateLong >= greaterMillisecondValue){
                            set.addAll(filterMatdateDerivateMap.get(matDateLong));
                        }
                    }
                }else if(value.contains("<")){
                    long lessMillisecondValue = dateFormat.parse(value.substring(value.indexOf("<") + 1)).getTime();
                    for (Long matDateLong :filterMatdateDerivateMap.keySet()) {
                        if(matDateLong <= lessMillisecondValue){
                            set.addAll(filterMatdateDerivateMap.get(matDateLong));
                        }
                    }
                }
            }else if(name.equalsIgnoreCase(DerivateEnum.VAL_DATE.name())){
               if (filterValdateDerivateMap.size() == 0)
                   findDerivateListByFilterIntegerKeyword();
                if(value.contains(">")){
                    long greaterMillisecondValue = dateFormat.parse(value.substring(value.indexOf(">") + 1)).getTime();
                    for (Long valDateLong :filterValdateDerivateMap.keySet()) {
                        if(valDateLong >= greaterMillisecondValue){
                            set.addAll(filterValdateDerivateMap.get(valDateLong));
                        }
                    }
                }else if(value.contains("<")){
                    long lessMillisecondValue = dateFormat.parse(value.substring(value.indexOf("<") + 1)).getTime();
                    for (Long valDateLong :filterValdateDerivateMap.keySet()) {
                        if(valDateLong <= lessMillisecondValue){
                            set.addAll(filterValdateDerivateMap.get(valDateLong));
                        }
                    }
                }
            }
        }catch (Exception e){
            log.error("Error getting while prepare derivate set object by derivate date parameter key : ",e);
        }
        return set;
    }

    private Set<Derivative> getDerivativeListByStringFieldWithANDOperator(Map<String, String> map, Set<Derivative> setByKeyResult) {
        Set<Derivative> set = new HashSet<>();
        try {
            for(Derivative derivative : Objects.requireNonNull(setByKeyResult)){
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
                    }else if(DerivateEnum.SIDE_WAYS_RETURN_PA.name().equalsIgnoreCase(entry.getKey()) && Objects.nonNull(derivative.getSidewaysReturnPa())){
                        if(entry.getValue().contains(">") && derivative.getSidewaysReturnPa() >= Integer.valueOf(entry.getValue().substring(entry.getValue().indexOf(">") + 1))){
                            value++;
                        }else if(entry.getValue().contains("<") && derivative.getSidewaysReturnPa() <= Integer.valueOf(entry.getValue().substring(entry.getValue().indexOf("<") + 1))){
                            value++;
                        }
                    }else if(DerivateEnum.TYPE.name().equalsIgnoreCase(entry.getKey()) && String.valueOf(derivative.getType()).equalsIgnoreCase(entry.getValue())){
                        value++;
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
                    set.add(derivative);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("Error getting while prepare derivative list by string field with AND operator : ",e);
        }
        return set;
    }

    @Override
    public Map<String, String> getDerivateStringListByDerivateStringField(String type, String filterIsin, String filterWkn, String filterUisin,
                                                                          String filterEmi, String filterCur, String filterQuanto,
                                                                          String filterExec, String filterBv, String filterCap,
                                                                          String filterAsk, String filterBid, String filterSidewaysReturnPa,
                                                                          String filterMatDate, String filterValDate) {
        Map<String, String> map = new HashMap<>();
        try {
            if(StringUtils.isNotBlank(type)){
                map.put(DerivateEnum.TYPE.name(), type);
            }
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

    private Set<Derivative> getDerivativeSetByDerivateProperty(String name, String value) {
        Set<Derivative> set =  Collections.synchronizedSet(new HashSet<>());
        try {
            if(name.equalsIgnoreCase(DerivateEnum.QUANTO.name())){
                if (filterQuantoDerivateMap.size() == 0)
                    findDerivateListByFilterIntegerKeyword();
                if(value.contains(">")){
                    for (Integer quantoInteger :filterQuantoDerivateMap.keySet()) {
                        if(quantoInteger >= Integer.valueOf(value.substring(value.indexOf(">") + 1))){
                            set.addAll(filterQuantoDerivateMap.get(quantoInteger));
                        }
                    }
                }else if(value.contains("<")){
                    for (Integer quantoInteger :filterQuantoDerivateMap.keySet()) {
                        if(quantoInteger <= Integer.valueOf(value.substring(value.indexOf("<") + 1))){
                            set.addAll(filterQuantoDerivateMap.get(quantoInteger));
                        }
                    }
                }
            }else if(name.equalsIgnoreCase(DerivateEnum.EXEC.name())){
                if (filterExecDerivateMap.size() == 0)
                    findDerivateListByFilterIntegerKeyword();
                if(value.contains(">")){
                    for (Integer execInteger :filterExecDerivateMap.keySet()) {
                        if(execInteger >= Integer.valueOf(value.substring(value.indexOf(">") + 1))){
                            set.addAll(filterExecDerivateMap.get(execInteger));
                        }
                    }
                }else if(value.contains("<")){
                    for (Integer execInteger :filterExecDerivateMap.keySet()) {
                        if(execInteger <= Integer.valueOf(value.substring(value.indexOf("<") + 1))){
                            set.addAll(filterExecDerivateMap.get(execInteger));
                        }
                    }
                }
            }else if(name.equalsIgnoreCase(DerivateEnum.BV.name())){
                if (filterBvDerivateMap.size() == 0)
                    findDerivateListByFilterIntegerKeyword();
                if(value.contains(">")){
                    for (Double bvDouble :filterBvDerivateMap.keySet()) {
                        if(bvDouble >= Double.valueOf(value.substring(value.indexOf(">") + 1))){
                            set.addAll(filterBvDerivateMap.get(bvDouble));
                        }
                    }
                }else if(value.contains("<")){
                    for (Double bvDouble :filterBvDerivateMap.keySet()) {
                        if(bvDouble <= Double.valueOf(value.substring(value.indexOf("<") + 1))){
                            set.addAll(filterBvDerivateMap.get(bvDouble));
                        }
                    }
                }
            }else if(name.equalsIgnoreCase(DerivateEnum.CAP.name())){
                if (filterCapDerivateMap.size() == 0)
                    findDerivateListByFilterIntegerKeyword();
                if(value.contains(">")){
                    for (Double capDouble :filterCapDerivateMap.keySet()) {
                        if(capDouble >= Double.valueOf(value.substring(value.indexOf(">") + 1))){
                            set.addAll(filterCapDerivateMap.get(capDouble));
                        }
                    }
                }else if(value.contains("<")){
                    for (Double capDouble :filterCapDerivateMap.keySet()) {
                        if(capDouble <= Double.valueOf(value.substring(value.indexOf("<") + 1))){
                            set.addAll(filterCapDerivateMap.get(capDouble));
                        }
                    }
                }
            }else if(name.equalsIgnoreCase(DerivateEnum.ASK.name())){
                if (filterAskDerivateMap.size() == 0)
                    findDerivateListByFilterIntegerKeyword();
                if(value.contains(">")){
                    for (Double askDouble :filterAskDerivateMap.keySet()) {
                        if(askDouble >= Double.valueOf(value.substring(value.indexOf(">") + 1))){
                            set.addAll(filterAskDerivateMap.get(askDouble));
                        }
                    }
                }else if(value.contains("<")){
                    for (Double askDouble :filterAskDerivateMap.keySet()) {
                        if(askDouble <= Double.valueOf(value.substring(value.indexOf("<") + 1))){
                            set.addAll(filterAskDerivateMap.get(askDouble));
                        }
                    }
                }
            }else if(name.equalsIgnoreCase(DerivateEnum.BID.name())){
                if (filterBidDerivateMap.size() == 0)
                    findDerivateListByFilterIntegerKeyword();
                if(value.contains(">")){
                    for (Double bidDouble :filterBidDerivateMap.keySet()) {
                        if(bidDouble >= Double.valueOf(value.substring(value.indexOf(">") + 1))){
                            set.addAll(filterBidDerivateMap.get(bidDouble));
                        }
                    }
                }else if(value.contains("<")){
                    for (Double bidDouble :filterBidDerivateMap.keySet()) {
                        if(bidDouble <= Double.valueOf(value.substring(value.indexOf("<") + 1))){
                            set.addAll(filterBidDerivateMap.get(bidDouble));
                        }
                    }
                }
            }else if(name.equalsIgnoreCase(DerivateEnum.SIDE_WAYS_RETURN_PA.name())){
                if (filterSidewaysReturnPaDerivateMap.size() == 0)
                    findDerivateListByFilterIntegerKeyword();
                if(value.contains(">")){
                    for (Double sidewaysReturnPaDouble :filterSidewaysReturnPaDerivateMap.keySet()) {
                        if(sidewaysReturnPaDouble >= Double.valueOf(value.substring(value.indexOf(">") + 1))){
                            set.addAll(filterSidewaysReturnPaDerivateMap.get(sidewaysReturnPaDouble));
                        }
                    }
                }else if(value.contains("<")){
                    for (Double sidewaysReturnPaDouble :filterSidewaysReturnPaDerivateMap.keySet()) {
                        if(sidewaysReturnPaDouble <= Double.valueOf(value.substring(value.indexOf("<") + 1))){
                            set.addAll(filterSidewaysReturnPaDerivateMap.get(sidewaysReturnPaDouble));
                        }
                    }
                }
            }
        }catch (Exception e){
            log.error("Error getting while prepare derivate numeric field list by derivate parameter key : ",e);
        }
        return set;
    }

    @Override
    public void findDerivateListByFilterIntegerKeyword() {
        try {
            synchronized (this){
                Set<Derivative> derivativeSet = derivateMeasureReadFiles.getStammdatenAlleDateFromList();
                executor.submit(() -> getDervativeSetByDervativeParopertyQuanto(derivativeSet));
                executor.submit(() -> getDervativeSetByDervativeParopertyExec(derivativeSet));
                executor.submit(() -> getDervativeSetByDervativeParopertyBv(derivativeSet));
                executor.submit(() -> getDervativeSetByDervativeParopertyCap(derivativeSet));
                executor.submit(() -> getDervativeSetByDervativeParopertyAsk(derivativeSet));
                executor.submit(() -> getDervativeSetByDervativeParopertyBid(derivativeSet));
                executor.submit(() -> getDervativeSetByDervativeParopertySidewaysReturnPa(derivativeSet));
                executor.submit(() -> getDervativeSetByDervativeParopertyMatDate(derivativeSet));
                executor.submit(() -> getDervativeSetByDervativeParopertyValDate(derivativeSet));
            }
        }catch (Exception e){
            log.error("Error getting while filter derivate object by derivate integer parameter key : ",e);
        }
    }

    private void getDervativeSetByDervativeParopertyValDate(Set<Derivative> derivativeSet) {
        try {
            for (Derivative valdateDerivate : derivativeSet) {
                if (valdateDerivate != null) {
                    if (Objects.nonNull(valdateDerivate.getValdate())){
                        long valDateMillisecond = dateFormat.parse(dateFormat.format(valdateDerivate.getValdate())).getTime();
                        if (filterValdateDerivateMap.get(valDateMillisecond) == null) {
                            Set<Derivative> valdateSet = new HashSet<>();
                            valdateSet.add(valdateDerivate);
                            filterValdateDerivateMap.put(valDateMillisecond, valdateSet);
                        } else {
                            Set<Derivative> valdateSet = filterValdateDerivateMap.get(valDateMillisecond);
                            valdateSet.add(valdateDerivate);
                            filterValdateDerivateMap.put(valDateMillisecond, valdateSet);
                        }
                    }
                }
            }
        }catch (Exception e){
            log.error("Error getting while prepare derivate valdate map by derivate parameter key : ",e);
        }
    }

    private void getDervativeSetByDervativeParopertyMatDate(Set<Derivative> derivativeSet) {
        try {
            for (Derivative matdateDerivate : derivativeSet) {
                if (matdateDerivate != null) {
                    if (Objects.nonNull(matdateDerivate.getMatdate())){
                        long matDateMillisecond = dateFormat.parse(dateFormat.format(matdateDerivate.getMatdate())).getTime();
                        if (filterMatdateDerivateMap.get(matDateMillisecond) == null) {
                            Set<Derivative> matdateSet = new HashSet<>();
                            matdateSet.add(matdateDerivate);
                            filterMatdateDerivateMap.put(matDateMillisecond, matdateSet);
                        } else {
                            Set<Derivative> matdateSet = filterMatdateDerivateMap.get(matDateMillisecond);
                            matdateSet.add(matdateDerivate);
                            filterMatdateDerivateMap.put(matDateMillisecond, matdateSet);
                        }
                    }
                }
            }
        }catch (Exception e){
            log.error("Error getting while prepare derivate matdate map by derivate parameter key : ",e);
        }
    }

    private void getDervativeSetByDervativeParopertySidewaysReturnPa(Set<Derivative> derivativeSet) {
        try {
            derivativeSet.stream().filter(Objects::nonNull).forEach(sidewaysReturnPaDerivate -> {
                if(Objects.nonNull(sidewaysReturnPaDerivate.getSidewaysReturnPa()))
                    if (filterSidewaysReturnPaDerivateMap.get(sidewaysReturnPaDerivate.getSidewaysReturnPa()) == null) {
                        Set<Derivative> sidewaysReturnPaSet = new HashSet<>();
                        sidewaysReturnPaSet.add(sidewaysReturnPaDerivate);
                        filterSidewaysReturnPaDerivateMap.put(sidewaysReturnPaDerivate.getSidewaysReturnPa(), sidewaysReturnPaSet);
                    } else {
                        Set<Derivative> sidewaysReturnPaSet = filterSidewaysReturnPaDerivateMap.get(sidewaysReturnPaDerivate.getSidewaysReturnPa());
                        sidewaysReturnPaSet.add(sidewaysReturnPaDerivate);
                        filterSidewaysReturnPaDerivateMap.put(sidewaysReturnPaDerivate.getSidewaysReturnPa(), sidewaysReturnPaSet);
                    }
            });
        }catch (Exception e){
            log.error("Error getting while prepare derivate sidewaysReturnPa map by derivate parameter key : ",e);
        }
    }

    private void getDervativeSetByDervativeParopertyBid(Set<Derivative> derivativeSet) {
        try {
            derivativeSet.stream().filter(Objects::nonNull).forEach(bidDerivate -> {
                if(Objects.nonNull(bidDerivate.getBid()))
                    if (filterBidDerivateMap.get(bidDerivate.getBid()) == null) {
                        Set<Derivative> bidSet = new HashSet<>();
                        bidSet.add(bidDerivate);
                        filterBidDerivateMap.put(bidDerivate.getBid(), bidSet);
                    } else {
                        Set<Derivative> bidSet = filterBidDerivateMap.get(bidDerivate.getBid());
                        bidSet.add(bidDerivate);
                        filterBidDerivateMap.put(bidDerivate.getBid(), bidSet);
                    }
            });
        }catch (Exception e){
            log.error("Error getting while prepare derivate bid map by derivate parameter key : ",e);
        }
    }

    private void getDervativeSetByDervativeParopertyAsk(Set<Derivative> derivativeSet) {
        try {
            derivativeSet.stream().filter(Objects::nonNull).forEach(askDerivate -> {
                if(Objects.nonNull(askDerivate.getAsk()))
                    if (filterAskDerivateMap.get(askDerivate.getAsk()) == null) {
                        Set<Derivative> askSet = new HashSet<>();
                        askSet.add(askDerivate);
                        filterAskDerivateMap.put(askDerivate.getAsk(), askSet);
                    } else {
                        Set<Derivative> askSet = filterAskDerivateMap.get(askDerivate.getAsk());
                        askSet.add(askDerivate);
                        filterAskDerivateMap.put(askDerivate.getAsk(), askSet);
                    }
            });
        }catch (Exception e){
            log.error("Error getting while prepare derivate ask map by derivate parameter key : ",e);
        }
    }

    private void getDervativeSetByDervativeParopertyCap(Set<Derivative> derivativeSet) {
        try {
            derivativeSet.stream().filter(Objects::nonNull).forEach(capDerivate -> {
                if(Objects.nonNull(capDerivate.getCap()))
                    if (filterCapDerivateMap.get(capDerivate.getCap()) == null) {
                        Set<Derivative> capSet = new HashSet<>();
                        capSet.add(capDerivate);
                        filterCapDerivateMap.put(capDerivate.getCap(), capSet);
                    } else {
                        Set<Derivative> capSet = filterCapDerivateMap.get(capDerivate.getCap());
                        capSet.add(capDerivate);
                        filterCapDerivateMap.put(capDerivate.getCap(), capSet);
                    }
            });
        }catch (Exception e){
            log.error("Error getting while prepare derivate cap map by derivate parameter key : ",e);
        }
    }

    private void getDervativeSetByDervativeParopertyBv(Set<Derivative> derivativeSet) {
        try {
            derivativeSet.stream().filter(Objects::nonNull).forEach(bvDerivate -> {
                if(Objects.nonNull(bvDerivate.getBv()))
                    if (filterBvDerivateMap.get(bvDerivate.getBv()) == null) {
                        Set<Derivative> bvSet = new HashSet<>();
                        bvSet.add(bvDerivate);
                        filterBvDerivateMap.put(bvDerivate.getBv(), bvSet);
                    } else {
                        Set<Derivative> bvSet = filterBvDerivateMap.get(bvDerivate.getBv());
                        bvSet.add(bvDerivate);
                        filterBvDerivateMap.put(bvDerivate.getBv(), bvSet);
                    }
            });
        }catch (Exception e){
            log.error("Error getting while prepare derivate bv map by derivate parameter key : ",e);
        }
    }

    private void getDervativeSetByDervativeParopertyExec(Set<Derivative> derivativeSet) {
        try {
            derivativeSet.stream().filter(Objects::nonNull).forEach(execDerivate -> {
                if(Objects.nonNull(execDerivate.getExec()))
                    if (filterExecDerivateMap.get(execDerivate.getExec()) == null) {
                        Set<Derivative> execSet = new HashSet<>();
                        execSet.add(execDerivate);
                        filterExecDerivateMap.put(execDerivate.getExec(), execSet);
                    } else {
                        Set<Derivative> execSet = filterExecDerivateMap.get(execDerivate.getExec());
                        execSet.add(execDerivate);
                        filterExecDerivateMap.put(execDerivate.getExec(), execSet);
                    }
            });
        }catch (Exception e){
            log.error("Error getting while prepare derivate exec map by derivate parameter key : ",e);
        }
    }

    private void getDervativeSetByDervativeParopertyQuanto(Set<Derivative> derivativeSet) {
        try {
            derivativeSet.stream().filter(Objects::nonNull).forEach(quantoDerivate -> {
                if(Objects.nonNull(quantoDerivate.getQuanto()))
                    if (filterQuantoDerivateMap.get(quantoDerivate.getQuanto()) == null) {
                        Set<Derivative> quantoSet = new HashSet<>();
                        quantoSet.add(quantoDerivate);
                        filterQuantoDerivateMap.put(quantoDerivate.getQuanto(), quantoSet);
                    } else {
                        Set<Derivative> quantoSet = filterQuantoDerivateMap.get(quantoDerivate.getQuanto());
                        quantoSet.add(quantoDerivate);
                        filterQuantoDerivateMap.put(quantoDerivate.getQuanto(), quantoSet);
                    }
            });
        }catch (Exception e){
            log.error("Error getting while prepare derivate quanto map by derivate parameter key : ",e);
        }
    }

}
