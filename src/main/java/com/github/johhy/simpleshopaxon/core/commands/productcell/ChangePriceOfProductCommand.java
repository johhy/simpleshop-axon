package com.github.johhy.simpleshopaxon.core.commands.productcell;

/**
 * @author johhy
 *
 */
public class ChangePriceOfProductCommand extends AbstractProductCellCommand {

	private final double price;
	
	public ChangePriceOfProductCommand(String codeOfProduct, double price) {
		super(codeOfProduct);
		this.price = price;
	}

	public double getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "ChangePriceOfProductCommand [price=" + price + ", codeOfProduct=" + getCodeOfProduct() + "]";
	}


}
