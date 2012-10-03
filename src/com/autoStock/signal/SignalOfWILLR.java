/**
 * 
 */
package com.autoStock.signal;

import com.autoStock.signal.SignalDefinitions.SignalMetricType;

/**
 * @author Kevin Kowalewski
 *
 */
public class SignalOfWILLR{
	private double willrValue = 0;
	private SignalMetricType signalMetricType = SignalMetricType.metric_willr;
	
	public SignalOfWILLR(double[] arrayOfWILLR, int periodAverage){
		if (arrayOfWILLR.length < 1){throw new IllegalArgumentException();}
		if (periodAverage > 0 && arrayOfWILLR.length < periodAverage){throw new IllegalArgumentException();}
		
		if (periodAverage > 0){
			for (int i=arrayOfWILLR.length-periodAverage; i<arrayOfWILLR.length; i++){
				willrValue += arrayOfWILLR[i];
			}
			
			willrValue /= periodAverage;
			
		}else{
			willrValue = arrayOfWILLR[arrayOfWILLR.length-1];
		}
	}
	
	public SignalMetric getSignal(){
		return new SignalMetric(signalMetricType.getSignalStrength(willrValue), signalMetricType);
	}
	
	public double getValue(){
		return willrValue;
	}
}
