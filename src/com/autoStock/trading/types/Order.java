package com.autoStock.trading.types;

import java.util.concurrent.atomic.AtomicInteger;

import com.autoStock.Co;
import com.autoStock.exchange.request.RequestMarketOrder;
import com.autoStock.exchange.request.base.RequestHolder;
import com.autoStock.exchange.request.listener.RequestMarketOrderListener;
import com.autoStock.exchange.results.ExResultMarketOrder.ExResultRowMarketOrder;
import com.autoStock.exchange.results.ExResultMarketOrder.ExResultSetMarketOrder;
import com.autoStock.finance.Account;
import com.autoStock.order.OrderDefinitions.IbOrderStatus;
import com.autoStock.order.OrderDefinitions.OrderMode;
import com.autoStock.order.OrderDefinitions.OrderStatus;
import com.autoStock.order.OrderDefinitions.OrderType;
import com.autoStock.order.OrderSimulator;
import com.autoStock.order.OrderStatusListener;
import com.autoStock.order.OrderTools;
import com.autoStock.order.OrderValue;
import com.autoStock.position.PositionManager;
import com.autoStock.types.Exchange;
import com.autoStock.types.Symbol;

/**
 * @author Kevin Kowalewski
 *
 */
public class Order {
	public Symbol symbol;
	public Exchange exchange;
	public Position position;
	private final int unitsRequested;
	private final double priceRequested;
	private OrderStatus orderStatus = OrderStatus.none;
	public OrderType orderType = OrderType.none;
	private RequestMarketOrder requestMarketOrder;
	private OrderStatusListener orderStatusListener;
	private AtomicInteger atomicIntForUnitsFilled = new AtomicInteger();
	
	public Order(Symbol symbol, Exchange exchange, Position position, OrderType orderType, int unitsRequested, double priceRequested, OrderStatusListener orderStatusListener){
		this.symbol = symbol;
		this.exchange = exchange;
		this.position = position;
		this.unitsRequested = unitsRequested;
		this.priceRequested = priceRequested;
		this.orderType = orderType;
		this.orderStatusListener = orderStatusListener;
	}
	
	public void executeOrder(){
		Co.println("--> Executing order with mode: " + PositionManager.getInstance().orderMode.name() + ", " + position.positionType.name() + ", " + orderType.name());
		
		if (orderStatus == OrderStatus.none){
			if (PositionManager.getInstance().orderMode == OrderMode.mode_exchange){
				requestMarketOrder = new RequestMarketOrder(new RequestHolder(new RequestMarketOrderListener(){
					@Override
					public void failed(RequestHolder requestHolder) {
						Co.println("--> Order failed");
					}
	
					@Override
					public void receivedChange(RequestHolder requestHolder, ExResultRowMarketOrder exResultRowMarketOrder) {
						Co.println("--> Received order: " + exResultRowMarketOrder.status.name());
					}
	
					@Override
					public void completed(RequestHolder requestHolder, ExResultSetMarketOrder exResultSetMarketOrder) {
						Co.println("--> Order completed");
						Co.println("--> Order status: " + new OrderTools().getOrderStatus(exResultSetMarketOrder).name());
					}
	
					@Override
					public void failed(IbOrderStatus ibOrderStatus) {
						if (ibOrderStatus == IbOrderStatus.status_cancelled){
							orderStatus = OrderStatus.status_cancelled;
						}
						Co.println("--> Failed with ibOrderStatus: " + ibOrderStatus.name());
					}
				}), this, exchange);
			}else{
				new OrderSimulator(this).simulateOrderFill();
			}
		}else{
			throw new IllegalStateException();
		}
	}
	
	public void cancelOrder(){
		orderStatus = OrderStatus.status_cancelled;
		if (requestMarketOrder == null){
			throw new IllegalStateException();
		}
		requestMarketOrder.cancel();
	}
	
	public void orderUnitFilled(double priceAverageFill, int units){
		atomicIntForUnitsFilled.addAndGet(units);
		Co.println("--> Order units filled: " + units + " of " + unitsRequested);
		
		if (atomicIntForUnitsFilled.get() == unitsRequested){
			orderStatus = OrderStatus.status_filled;
			if (orderType == OrderType.order_long_entry){orderType = OrderType.order_long;}
			else if (orderType == OrderType.order_short_entry){orderType = OrderType.order_short;}
			else if (orderType == OrderType.order_long_exit){orderType = OrderType.order_long_exited;}
			else if (orderType == OrderType.order_short_exit){orderType = OrderType.order_short_exited;}
			else {throw new IllegalStateException();}
			
			orderStatusListener.orderStatusChanged(this, orderStatus);
		}else{
			orderStatus = OrderStatus.status_filled_partially;
		}
	}
	
	public OrderValue getOrderValue(){
		//units requested, units filled, price requested, price filled
		return new OrderValue(
				getRequestedValue(false), getFilledValue(false), getIntrinsicValue(false), 
				getRequestedValue(true), getFilledValue(true), getIntrinsicValue(true), 
				getRequestedPrice(true), getFilledPrice(true), getIntrinsicPrice(true),
				getUnitPriceRequested(), getUnitPriceFilled(), getUnitPriceIntrinsic(),
				Account.getInstance().getTransactionCost(getUnitsIntrinsic(), priceRequested)
				);
	}
	
	//TODO: fix this
	private double getFilledPrice(boolean includeTransactionFees){
		double transactionFees = Account.getInstance().getTransactionCost(getUnitsFilled(), priceRequested); 
		double positionValue = getUnitsFilled() * priceRequested;
		double total = positionValue + (includeTransactionFees ? transactionFees : 0);
		
		return total;
	}
	
	private double getFilledValue(boolean includeTransactionFees){
		double transactionFees = Account.getInstance().getTransactionCost(getUnitsFilled(), priceRequested); 
		double positionValue = getUnitsFilled() * priceRequested;
		double total = positionValue - (includeTransactionFees ? transactionFees : 0);
		
		return total;
	}
	
	private double getRequestedPrice(boolean includeTransactionFees){
		double transactionFees = Account.getInstance().getTransactionCost(getUnitsFilled(), priceRequested); 
		double positionValue = getUnitsFilled() * priceRequested;
		double total = positionValue + (includeTransactionFees ? transactionFees : 0);
		
		return total;
	}
	
	private double getRequestedValue(boolean includeTransactionFees){
		double transactionFees = Account.getInstance().getTransactionCost(getUnitsFilled(), priceRequested); 
		double positionValue = getUnitsFilled() * priceRequested;
		double total = positionValue - (includeTransactionFees ? transactionFees : 0);
		
		return total;
	}
	
	private double getIntrinsicPrice(boolean includeTransactionFees){
		if (orderStatus == OrderStatus.status_filled){
			return getFilledPrice(includeTransactionFees);
		}else if (orderStatus == OrderStatus.status_filled_partially){
			return getRequestedPrice(includeTransactionFees);
		}else {
			return getRequestedPrice(includeTransactionFees);
		}
	}
	
	private double getIntrinsicValue(boolean includeTransactionFees){
		if (orderStatus == OrderStatus.status_filled){
			return getFilledValue(includeTransactionFees);
		}else if (orderStatus == OrderStatus.status_filled_partially){
			return getRequestedValue(includeTransactionFees);
		}else {
			return getRequestedValue(includeTransactionFees);
		}
	}
	
	public int getUnitsRequested(){
		return unitsRequested;
	}
	
	public int getUnitsFilled(){
		return atomicIntForUnitsFilled.get();
	}
	
	public int getUnitsIntrinsic(){
		return unitsRequested;
	}
	
	public double getUnitPriceRequested(){
		return priceRequested;
	}
	
	public double getUnitPriceFilled(){
		return priceRequested;
	}
	
	private double getUnitPriceIntrinsic(){
		return priceRequested;
	}
	
	public double getTransactionFees(){
		return Account.getInstance().getTransactionCost(unitsRequested, priceRequested);
	}
}