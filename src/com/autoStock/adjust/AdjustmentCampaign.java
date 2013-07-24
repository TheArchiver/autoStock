package com.autoStock.adjust;

import java.util.ArrayList;

import com.autoStock.Co;

/**
 * @author Kevin Kowalewski
 *
 */
public abstract class AdjustmentCampaign {
	protected ArrayList<IterableBase> listOfIterableBase = new ArrayList<IterableBase>();
	protected ArrayList<AdjustmentBase> listOfAdjustmentBase = new ArrayList<AdjustmentBase>();
	private Permutation permutation = new Permutation(listOfIterableBase);
	
	public static enum AdjustmentType {
		signal_metric_long_entry,
		signal_metric_long_exit,
		signal_metric_short_entry,
		signal_metric_short_exit,
	}
	
	protected abstract void initializeAdjustmentCampaign();
	
//	public void initializeAdjustmentCampaign(){
////		listOfAdjustmentBase.add(new AdjustmentOfSignalMetric(SignalMetricType.metric_adx, AdjustmentType.signal_metric_long_entry, new IterableOfInteger(0, 50, 2)));
////		listOfAdjustmentBase.add(new AdjustmentOfSignalMetric(SignalMetricType.metric_adx, AdjustmentType.signal_metric_long_exit, new IterableOfInteger(-50, 0, 2)));
//		listOfAdjustmentBase.add(new AdjustmentOfSignalMetric(SignalMetricType.metric_cci, AdjustmentType.signal_metric_long_entry, new IterableOfInteger(-50, 0, 2)));
//		listOfAdjustmentBase.add(new AdjustmentOfSignalMetric(SignalMetricType.metric_cci, AdjustmentType.signal_metric_long_exit, new IterableOfInteger(0, 50, 2)));
//		listOfAdjustmentBase.add(new AdjustmentOfSignalMetric(SignalMetricType.metric_cci, AdjustmentType.signal_metric_short_entry, new IterableOfInteger(0, 50, 2)));
//		listOfAdjustmentBase.add(new AdjustmentOfSignalMetric(SignalMetricType.metric_cci, AdjustmentType.signal_metric_short_exit, new IterableOfInteger(-50, 0, 2)));
////		listOfAdjustmentBase.add(new AdjustmentOfSignalMetric(SignalMetricType.metric_rsi, AdjustmentType.signal_metric_long_entry, new IterableOfInteger(0, 50, 2)));
////		listOfAdjustmentBase.add(new AdjustmentOfSignalMetric(SignalMetricType.metric_rsi, AdjustmentType.signal_metric_long_exit, new IterableOfInteger(-50, 0, 2)));
////		listOfAdjustmentBase.add(new AdjustmentOfSignalMetric(SignalMetricType.metric_di, AdjustmentType.signal_metric_long_entry, new IterableOfInteger(-50, 50, 2)));
////		listOfAdjustmentBase.add(new AdjustmentOfSignalMetric(SignalMetricType.metric_di, AdjustmentType.signal_metric_long_exit, new IterableOfInteger(-50, 50, 2)));
////		listOfAdjustmentBase.add(new AdjustmentOfSignalMetric(SignalMetricType.metric_trix, AdjustmentType.signal_metric_long_entry, new IterableOfInteger(-4, 40, 2)));
////		listOfAdjustmentBase.add(new AdjustmentOfSignalMetric(SignalMetricType.metric_trix, AdjustmentType.signal_metric_long_exit, new IterableOfInteger(-40, 4, 2)));
////		listOfAdjustmentBase.add(new AdjustmentOfSignalMetric(SignalMetricType.metric_macd, AdjustmentType.signal_metric_long_entry, new IterableOfInteger(-4, 40, 2)));
////		listOfAdjustmentBase.add(new AdjustmentOfSignalMetric(SignalMetricType.metric_macd, AdjustmentType.signal_metric_long_exit, new IterableOfInteger(-40, 4, 2)));
////		listOfAdjustmentBase.add(new AdjustmentOfSignalMetric(SignalMetricType.metric_roc, AdjustmentType.signal_metric_long_entry, new IterableOfInteger(-4, 40, 1)));
////		listOfAdjustmentBase.add(new AdjustmentOfSignalMetric(SignalMetricType.metric_roc, AdjustmentType.signal_metric_long_exit, new IterableOfInteger(-40, 4, 1)));
////		
////		listOfAdjustmentBase.add(new AdjustmentOfBasicInteger("MACD EMA", IndicatorOfMACD.immutableIntegerForEma, new IterableOfInteger(3, 45, 1)));
////		listOfAdjustmentBase.add(new AdjustmentOfBasicInteger("MACD long", IndicatorOfMACD.immutableIntegerForLong, new IterableOfInteger(3, 45, 1)));
////		listOfAdjustmentBase.add(new AdjustmentOfBasicInteger("MACD short", IndicatorOfMACD.immutableIntegerForShort, new IterableOfInteger(3, 45, 1)));
////		
//////	listOfAdjustmentBase.add(new AdjustmentOfBasicInteger(StrategyOptionManager.getInstance().getDefaultStrategyOptions().maxStopLossValue, new IterableOfInteger(-50, 0, 5)));
//////	listOfAdjustmentBase.add(new AdjustmentOfBasicInteger(StrategyOptionManager.getInstance().getDefaultStrategyOptions().intervalForReentryMins, new IterableOfInteger(1, 10, 1)));
//////	listOfAdjustmentBase.add(new AdjustmentOfBasicInteger(StrategyOptionManager.getInstance().getDefaultStrategyOptions().maxReenterTimes, new IterableOfInteger(1, 3, 1)));
//////	listOfAdjustmentBase.add(new AdjustmentOfBasicDouble(StrategyOptionManager.getInstance().getDefaultStrategyOptions().minReentryPercentGain, new IterableOfDouble(0.1, 0.5, 0.1)));
//////	
////		listOfAdjustmentBase.add(new AdjustmentOfBasicInteger(SignalControl.periodLengthStart, new IterableOfInteger(10, 30, 2)));
////		listOfAdjustmentBase.add(new AdjustmentOfBasicInteger("SignalControl.periodLengthMiddle", SignalControl.periodLengthMiddle, new IterableOfInteger(10, 40, 2)));
//		
////		listOfAdjustmentBase.add(new AdjustmentOfBasicInteger("CCI Period", SignalMetricType.metric_cci.periodLength, new IterableOfInteger(10, 46, 2)));
////		listOfAdjustmentBase.add(new AdjustmentOfBasicInteger("CCI Average", SignalMetricType.metric_cci.maxSignalAverage, new IterableOfInteger(1, 10, 1)));
//		
////		listOfAdjustmentBase.add(new AdjustmentOfBasicInteger("ADX Period", IndicatorGroup.immutableIntegerForADX, new IterableOfInteger(10, 60, 2)));
////		listOfAdjustmentBase.add(new AdjustmentOfBasicInteger("DI Period", IndicatorGroup.immutableIntegerForDI, new IterableOfInteger(10, 60, 2)));
//		
////		listOfAdjustmentBase.add(new AdjustmentOfEnum("CCI Guage Long Entry", new IterableOfEnum<SignalGuageType>(SignalGuageType.values()[0]), SignalDefinitions.SignalMetricType.metric_cci.arrayOfSignalGuageForLongEntry[0].immutableEnumForSignalGuageType));
////		listOfAdjustmentBase.add(new AdjustmentOfEnum("CCI Guage Long Exit", new IterableOfEnum<SignalGuageType>(SignalGuageType.values()[0]), SignalDefinitions.SignalMetricType.metric_cci.arrayOfSignalGuageForLongExit[0].immutableEnumForSignalGuageType));
//////		
////		listOfAdjustmentBase.add(new AdjustmentOfEnum("CCI Guage Short Entry", new IterableOfEnum<SignalGuageType>(SignalGuageType.values()[0]), SignalDefinitions.SignalMetricType.metric_cci.arrayOfSignalGuageForShortEntry[0].immutableEnumForSignalGuageType));
////		listOfAdjustmentBase.add(new AdjustmentOfEnum("CCI Guage Short Exit", new IterableOfEnum<SignalGuageType>(SignalGuageType.values()[0]), SignalDefinitions.SignalMetricType.metric_cci.arrayOfSignalGuageForShortExit[0].immutableEnumForSignalGuageType));
////		
////		listOfAdjustmentBase.add(new AdjustmentOfEnum("ADX Guage Entry", new IterableOfEnum<SignalGuageType>(SignalGuageType.values()[0]), SignalDefinitions.SignalMetricType.metric_adx.arrayOfSignalGuageForLongEntry[0].immutableEnumForSignalGuageType));
////		listOfAdjustmentBase.add(new AdjustmentOfEnum("ADX Guage Exit", new IterableOfEnum<SignalGuageType>(SignalGuageType.values()[0]), SignalDefinitions.SignalMetricType.metric_adx.arrayOfSignalGuageForLongExit[0].immutableEnumForSignalGuageType));
//	}
	
	public void initialize(){
		initializeAdjustmentCampaign();
		for (AdjustmentBase adjustmentBase : listOfAdjustmentBase){
			listOfIterableBase.add(adjustmentBase.getIterableBase());
		}
	}
	
	public boolean runAdjustment(){
		if (permutation.allDone()){
			return false;
		}else{
			permutation.masterIterate();
			permutation.printIterableSet();
			applyValues();
			return true;
		}
	}
	
	public boolean hasMore(){
		return !permutation.allDone();
	}
	
	public double getPercentComplete(){
		return 0;
	}
	
	public void applyValues(){
		for (AdjustmentBase adjustmentBase : listOfAdjustmentBase){
//			Co.println("--> Applied: " + adjustmentBase.getClass().getSimpleName() + ", " + adjustmentBase.description);
//			Co.println("--> Check: " + ((AdjustmentOfBasicInteger)adjustmentBase).getValue());
			adjustmentBase.applyValue();
		}
	}
	
	public ArrayList<AdjustmentBase> getListOfAdjustmentBase(){
		return listOfAdjustmentBase;
	}

	public ArrayList<AdjustmentOfPortable> getListOfPortableAdjustment(){
		ArrayList<AdjustmentOfPortable> listOfPortable = new ArrayList<AdjustmentOfPortable>();
		
		for (AdjustmentBase adjustmentBase : listOfAdjustmentBase){
			AdjustmentOfPortable adjustmentOfPortable = new AdjustmentOfPortable(adjustmentBase.iterableBase.currentIndex);
			listOfPortable.add(adjustmentOfPortable);
		}
		
		return listOfPortable;
	}

	public void setAdjustmentValuesFromIterationList(ArrayList<AdjustmentOfPortable> listOfAdjustmentOfPortable) {
		if (listOfAdjustmentOfPortable.size() != listOfAdjustmentBase.size()){
			throw new IllegalStateException("Size doesn't match: " + listOfAdjustmentOfPortable.size() + ", " + listOfAdjustmentBase.size());
		}
		
		Co.println("--> Size is: " + listOfAdjustmentOfPortable.size());
		
		for (int i=0; i < listOfAdjustmentBase.size(); i++){
			AdjustmentBase adjustmentBaseLocal = listOfAdjustmentBase.get(i);
			AdjustmentOfPortable adjustmentOfPortable = listOfAdjustmentOfPortable.get(i);
			
			adjustmentBaseLocal.iterableBase.overrideAndSetCurrentIndex(adjustmentOfPortable.iterationIndex);
			adjustmentBaseLocal.applyValue();
		}
	}
}
