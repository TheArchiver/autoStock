/**
 * 
 */
package com.autoStock.signal;

import com.autoStock.Co;
import com.autoStock.signal.SignalDefinitions.SignalSource;
import com.autoStock.signal.SignalDefinitions.SignalTypeMetric;
import com.autoStock.tools.MathTools;

/**
 * @author Kevin Kowalewski
 *
 */
public class SignalOfPPC{
	private double percentChange = 0;
	
	public SignalOfPPC(double[] arrayOfPrice, int periodAverage){
		if (arrayOfPrice.length < 3){throw new IllegalArgumentException();}
		if (periodAverage > 0 && arrayOfPrice.length < periodAverage * 2){throw new IllegalArgumentException();}
		
		double firstValue = 0;
		double secondValue = 0;
		
		if (periodAverage > 0){
			for (int i=0; i<periodAverage; i++){
				firstValue += arrayOfPrice[i];
			}
			
			for (int i=arrayOfPrice.length-periodAverage; i<arrayOfPrice.length; i++){
				secondValue += arrayOfPrice[i];
			}
			
			firstValue /= periodAverage;
			secondValue /= periodAverage;
		}else{
			firstValue = arrayOfPrice[0];
			secondValue = arrayOfPrice[arrayOfPrice.length-1]; 
		}
		
		percentChange = secondValue / firstValue;
		//Co.println("First average, second: " +arrayOfPrice.length + "," + MathTools.roundToTwoDecimalPlaces(firstValue) + "," + MathTools.roundToTwoDecimalPlaces(secondValue) + "," + getSignal().strength);
		//Co.println("PPC: " + arrayOfPriceClose.length + ","+ percentChange);
	}
	
	public SignalMetric getSignal(){
		SignalMetric signalMetric = new SignalMetric(0, SignalTypeMetric.metric_ppc);
	
		SignalStrenghts.setSignalStrengthForPPC(signalMetric, percentChange);
		
		return signalMetric;
	}
	
	public double getChange(){
		return percentChange;
	}
}