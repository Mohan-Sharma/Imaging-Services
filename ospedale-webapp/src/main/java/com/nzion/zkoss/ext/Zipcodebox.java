/**
 * 
 */
package com.nzion.zkoss.ext;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Textbox;

import com.nzion.util.UtilDisplay;

/**
 * @author Nafis
 *
 */
public class Zipcodebox  extends Textbox{
	
	private static final long serialVersionUID = 1L;

	public Zipcodebox(){
	    setMaxlength(6);
	}

}
