/**
 * 
 */
package com.nzion.zkoss.ext;

import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Textbox;

import com.nzion.util.UtilValidator;

/**
 * @author Nafis
 *
 */
public class SSNBox extends Textbox {

	public SSNBox() {
	setWidgetListener("onKeyUp", "formatSSN(event,'"+this.getUuid()+"');");
	setMaxlength(11);
	addEventListener("onChange", new EventListener() {
	@Override
	public void onEvent(Event arg0) throws Exception {
		if(UtilValidator.isNotEmpty(getValue())){
			if(getValue().trim().length() != 11)
				throw new WrongValueException(SSNBox.this,"Must contain nine digits only");
			//UtilDisplay.validateNineDigits(SSNBox.this);
		}
	}});
	}

	private static final long serialVersionUID = 1L;

}
