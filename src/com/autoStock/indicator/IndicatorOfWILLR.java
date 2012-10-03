/**
 * 
 */
package com.autoStock.indicator;

import com.autoStock.indicator.results.ResultsWILLR;
import com.autoStock.taLib.MInteger;
import com.autoStock.taLib.RetCode;

/**
 * @author Kevin Kowalewski
 *
 */
public class IndicatorOfWILLR extends IndicatorBase{
	public ResultsWILLR results;
	
	public IndicatorOfWILLR(int periodLength, CommonAnlaysisData commonAnlaysisData) {
		super(periodLength, commonAnlaysisData);
	}
	
	public ResultsWILLR analyize(){
		results = new ResultsWILLR(endIndex+1);
		
		results.arrayOfDates = commonAnlaysisData.arrayOfDates;
		results.arrayOfPrice = commonAnlaysisData.arrayOfPriceClose;
		
		RetCode returnCode = getTaLibCore().willR(0, endIndex, arrayOfPriceHigh, arrayOfPriceLow, arrayOfPriceClose, periodLength-1, new MInteger(), new MInteger(), results.arrayOfWILLR);
		handleAnalysisResult(returnCode);
		
		return results;
	}
}
