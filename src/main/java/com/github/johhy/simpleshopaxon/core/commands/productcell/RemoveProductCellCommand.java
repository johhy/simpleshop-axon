package com.github.johhy.simpleshopaxon.core.commands.productcell;

/**
 * 
 * @author johhy
 *
 */
public class RemoveProductCellCommand extends AbstractProductCellCommand {

	public RemoveProductCellCommand(String codeOfProduct) {
		super(codeOfProduct);
	}

	@Override
	public String toString() {
		return "RemoveProductCellCommand [codeOfProduct=" + getCodeOfProduct() + "]";
	}


}
