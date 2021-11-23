package com.derivatemeasure.portal.Business;

import com.derivatemeasure.portal.Model.Derivative;

import java.util.List;
import java.util.Map;

public interface DerivateFilterResponse {

    public void findDerivateListByFilterKeyword();

    public List<Derivative> getDerivateListByDerivateParameterKey(Map<String, String> derivativeParameterKeyMap, long from, long limit);

    Map<String, String> getDerivateStringListByDerivateStringField(String filterIsin, String filterWkn, String filterUisin, String filterEmi, String filterCur, String filterQuanto, String filterExec, String filterBv, String filterCap, String filterAsk, String filterBid, String filterSidewaysReturnPa, String filterMatDate, String filterValDate);
}
