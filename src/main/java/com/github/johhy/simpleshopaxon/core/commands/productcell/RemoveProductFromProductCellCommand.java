package com.github.johhy.simpleshopaxon.core.commands.productcell;

/**
 * @author johhy
 *
 */
public class RemoveProductFromProductCellCommand extends AbstractProductCellCommand {
	
	private final int quantity;
	
	public RemoveProductFromProductCellCommand(String codeOfProduct, int quantity) {
		super(codeOfProduct);
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	@Override
	public String toString() {
		return "RemoveProductFromProductCellCommand [quantity=" + quantity + 
				", codeOfProduct=" + getCodeOfProduct() + "]";
	}

	
}
