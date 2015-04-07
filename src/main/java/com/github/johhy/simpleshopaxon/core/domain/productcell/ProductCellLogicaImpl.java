package com.github.johhy.simpleshopaxon.core.domain.productcell;

import java.util.HashMap;
import java.util.Map;

import com.jcabi.aspects.Loggable;


/**
 * The Class ProductCellLogicaImpl.
 * 
 * Product cell logica impl.
 * 
 * @author johhy
 */
public class ProductCellLogicaImpl implements ProductCellLogica {
	
	/** The quantity. */
	int quantity = 0;
	
	/** The price. */
	double price = 0;
	
	 /** The reserved products. */
 	Map<String, Integer> reservedProducts = 
				new HashMap<String, Integer>();
	 
		/* (non-Javadoc)
		 * @see com.github.johhy.simpleshopaxon.core.domain.productcell.ProductCellLogica#getQuantity()
		 */
		public int getQuantity() {
		return quantity;
	}
	
	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.domain.productcell.ProductCellLogica#setQuantity(int)
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.domain.productcell.ProductCellLogica#getPrice()
	 */
	public double getPrice() {
		return price;
	}
	
	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.domain.productcell.ProductCellLogica#setPrice(double)
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	
	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.domain.productcell.ProductCellLogica#getReservedProducts()
	 */
	public Map<String, Integer> getReservedProducts() {
		return reservedProducts;
	}
	
	/* (non-Javadoc)
	 * @see com.github.johhy.simpleshopaxon.core.domain.productcell.ProductCellLogica#setReservedProducts(java.util.Map)
	 */
	public void setReservedProducts(Map<String, Integer> reservedProducts) {
		this.reservedProducts = reservedProducts;
	}
	
		/* (non-Javadoc)
		 * @see com.github.johhy.simpleshopaxon.core.domain.productcell.ProductCellLogica#isProductMoreThanNeed(int)
		 */
		@Loggable(Loggable.DEBUG)
		public boolean isProductMoreThanNeed(int amountReqired) {
			return quantity>=amountReqired;
		}
		
		/* (non-Javadoc)
		 * @see com.github.johhy.simpleshopaxon.core.domain.productcell.ProductCellLogica#removeAmountProductFromProductCell(int)
		 */
		@Loggable(Loggable.DEBUG)
		public int removeAmountProductFromProductCell(int removedAmount) {
			return quantity - removedAmount;
		}
		
		/* (non-Javadoc)
		 * @see com.github.johhy.simpleshopaxon.core.domain.productcell.ProductCellLogica#addAmountProductToProductCell(int)
		 */
		@Loggable(Loggable.DEBUG)
		public int addAmountProductToProductCell(int addedAmount) {
			return quantity + addedAmount;
		}
		
		/* (non-Javadoc)
		 * @see com.github.johhy.simpleshopaxon.core.domain.productcell.ProductCellLogica#changePrice(double)
		 */
		@Loggable(Loggable.DEBUG)
		public double changePrice(double newPrice) {
			return newPrice;
		}
		
		/* (non-Javadoc)
		 * @see com.github.johhy.simpleshopaxon.core.domain.productcell.ProductCellLogica#isProductReservedForOrder(java.lang.String)
		 */
		@Loggable(Loggable.DEBUG)
		public boolean isProductReservedForOrder(String orderId) {
			return reservedProducts.containsKey(orderId);
		}
		
		/* (non-Javadoc)
		 * @see com.github.johhy.simpleshopaxon.core.domain.productcell.ProductCellLogica#addReservedProductForOrder(java.lang.String, int)
		 */
		@Loggable(Loggable.DEBUG)
		public int addReservedProductForOrder(String orderId, int amountToAdd) {
			int newAmount = 0;
			if(isProductReservedForOrder(orderId)) {
				newAmount = reservedProducts.get(orderId) + amountToAdd;
			} else {
				newAmount = amountToAdd;
			}
			return newAmount;
		}
		
		/* (non-Javadoc)
		 * @see com.github.johhy.simpleshopaxon.core.domain.productcell.ProductCellLogica#isAmountReservedProductMoreThanNeedToRemove(java.lang.String, int)
		 */
		@Loggable(Loggable.DEBUG)
		public boolean isAmountReservedProductMoreThanNeedToRemove(String orderId, int amountReqired) {
			return reservedProducts.get(orderId)>=amountReqired;
		}
		
		/* (non-Javadoc)
		 * @see com.github.johhy.simpleshopaxon.core.domain.productcell.ProductCellLogica#removeAmountReservedProductForOrder(java.lang.String, int)
		 */
		@Loggable(Loggable.DEBUG)
		public int removeAmountReservedProductForOrder(String orderId, int amountToRemove) {
			return reservedProducts.get(orderId) - amountToRemove;
		}
		
		/* (non-Javadoc)
		 * @see com.github.johhy.simpleshopaxon.core.domain.productcell.ProductCellLogica#reserveProductForOrder(java.lang.String, int)
		 */
		@Loggable(Loggable.DEBUG)
		public void reserveProductForOrder(String orderId, int amountToReserve) {
			if(amountToReserve==0) {
				reservedProducts.remove(orderId);
			} else reservedProducts.put(orderId, amountToReserve);
		}
		
		/* (non-Javadoc)
		 * @see com.github.johhy.simpleshopaxon.core.domain.productcell.ProductCellLogica#isAnyReservedProductInProductCell()
		 */
		@Loggable(Loggable.DEBUG)
		public boolean isAnyReservedProductInProductCell() {
			return !reservedProducts.isEmpty();
		}
}
