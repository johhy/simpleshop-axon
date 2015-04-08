package com.github.johhy.simpleshopaxon.core.commands.productcell;

/**
 * @author johhy
 *
 */
public class AddProductToProductCellCommand extends AbstractProductCellCommand {
	

	private final int quantity;
	
	public AddProductToProductCellCommand(String codeOfProduct, int quantity) {
		super(codeOfProduct);
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	@Override
	public String toString() {
		return "AddProductToProductCellCommand [quantity=" + quantity + ", codeOfProduct=" + getCodeOfProduct()
				+ "]";
	}


	
}
