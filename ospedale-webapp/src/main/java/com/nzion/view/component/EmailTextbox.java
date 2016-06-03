/**
 * 
 */
package com.nzion.view.component;

import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Textbox;

import com.nzion.util.UtilValidator;

/**
 * @author Nafis
 *
 */
public class EmailTextbox extends Textbox{
	
	public EmailTextbox(){
	addEventListener("onBlur", new EventListener() {
		@Override
		public void onEvent(Event arg0) throws Exception {
		if(UtilValidator.isNotEmpty(((Textbox)arg0.getTarget()).getValue()) && !UtilValidator.validateEmail(((Textbox)arg0.getTarget()).getValue()))
				throw new WrongValueException(EmailTextbox.this, "Invalid Email format");
		}});
	}
	private static final long serialVersionUID = 1L;

}
