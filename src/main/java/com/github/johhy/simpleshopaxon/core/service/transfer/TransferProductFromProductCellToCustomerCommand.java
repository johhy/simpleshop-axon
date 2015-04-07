package com.github.johhy.simpleshopaxon.core.service.transfer;

/**
 * Command for ProductCellCustomerTransferService.
 * <p>
 * Parameters obtain from query model
 * 
 * @author johhy
 *
 */
public class TransferProductFromProductCellToCustomerCommand {
	
	private final String codeOfProduct;
	
	private final int amount;
	
	private final double price;
	
	private final String customerId;
	
	private final String orderId;

	public TransferProductFromProductCellToCustomerCommand(
			String codeOfProduct, int amount, double price, String customerId,
			String orderId) {
		super();
		this.codeOfProduct = codeOfProduct;
		this.amount = amount;
		this.price = price;
		this.customerId = customerId;
		this.orderId = orderId;
	}

	public String getCodeOfProduct() {
		return codeOfProduct;
	}

	public int getAmount() {
		return amount;
	}

	public double getPrice() {
		return price;
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getOrderId() {
		return orderId;
	}

	@Override
	public String toString() {
		return "TransferProductFromProductCellToCustomerCommand [codeOfProduct="
				+ codeOfProduct
				+ ", amount="
				+ amount
				+ ", price="
				+ price
				+ ", customerId=" + customerId + ", orderId=" + orderId + "]";
	}

	
}
