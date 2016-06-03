package com.nzion.view.component;

import org.apache.commons.lang.math.NumberUtils;
import org.zkoss.zhtml.Caption;
import org.zkoss.zhtml.Fieldset;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.impl.InputElement;
import org.zkoss.zul.impl.NumberInputElement;
import com.nzion.domain.base.IdSequence;
import com.nzion.util.Constants;

public final class IdSequenceHBox extends Hbox {

	private static final long serialVersionUID = 1L;
	private Label lblPrefix;
	private Textbox prefix;
	private Label lblStartIndex;
	private Longbox startIndex;
	private Label lblSuffix;
	private Textbox suffix;
	private Label lblIncrementBy;
	private Longbox incrementBy;
	private String entityName;

	private IdSequenceHBox() {
	lblPrefix = new Label();
	prefix = new Textbox();
	prefix.setCols(5);
	lblStartIndex = new Label();
	startIndex = new Longbox();
	startIndex.setCols(2);
	lblSuffix = new Label();
	suffix = new Textbox();
	suffix.setCols(5);
	lblIncrementBy = new Label();
	incrementBy = new Longbox();
	incrementBy.setCols(2);
	}
	
	private IdSequenceHBox(Builder builder) {
	this();
	this.appendChild(makeCaption(builder.caption));
	buildAndSetComponentsIntoHbox(builder.id, builder.prefixVal, builder.startIndex, builder.suffix, builder.incrementBy);
	this.setParent(new Fieldset()); 
//	Legend legend = new Legend()
//	legend.setv
	this.entityName = builder.entityName;
	}

	private Caption makeCaption(String caption) {
	return new Caption(caption);
	}

	private void buildAndSetComponentsIntoHbox(String id, String prefixVal, String startIndexVal, String suffixVal,String incrementByVal) {
	this.appendChild(buildAndSetVbox(lblPrefix, prefix, "prefix", id, "Prefix", prefixVal));
	this.appendChild(buildAndSetVbox(lblStartIndex, startIndex, "startindex", id, "Start", startIndexVal));
	this.appendChild(buildAndSetVbox(lblSuffix, suffix, "suffix", id, "Suffix", suffixVal));
	this.appendChild(buildAndSetVbox(lblIncrementBy, incrementBy, "incrementby", id, "Increment", incrementByVal));
	}

	private Vbox buildAndSetVbox(Label lbl, InputElement txt, String compPrefix, String Id, String lblValue,String txtValue) {
	setLabel(lbl, compPrefix, Id, lblValue);
	setTextBox(txt, compPrefix, Id, txtValue);
	return buildVbox(lbl,txt);
	}

	private void setLabel(Label lbl, String compPrefix, String id, String lblValue) {
	lbl.setId(Constants.LABEL_PREFIX + compPrefix + id);
	lbl.setValue(lblValue);
	}

	private void setTextBox(InputElement txt, String compPrefix, String id, String txtValue) {
	txt.setId(Constants.PREFERENCE_COMPONENT_PREFIX + compPrefix + id);
	if (org.zkoss.zul.impl.NumberInputElement.class.isAssignableFrom(txt.getClass())) {
		((NumberInputElement) txt).setRawValue(NumberUtils.createNumber(txtValue).longValue());
	} else
		if (Textbox.class.isAssignableFrom(txt.getClass())) {
			((Textbox) txt).setValue(txtValue);
		}
	}

	private Vbox buildVbox(Label lbl, InputElement txt) {
	Vbox vbox = new Vbox();
	vbox.appendChild(lbl);
	vbox.appendChild(txt);
	return vbox;
	}


	public String getPrefixVal() {
	if (prefix != null) {
		return prefix.getValue();
	}
	return null;
	}

	public Long getStartIndexVal() {
	if (startIndex != null) {
		return startIndex.getValue();
	}
	return null;
	}

	public String getSuffixVal() {
	if (suffix != null) {
		return suffix.getValue();
	}
	return null;
	}

	public Long getIncrementByVal() {
	if (incrementBy != null) {
		return incrementBy.longValue();
	}
	return null;
	}

	public String getEntityName() {
	return entityName;
	}

	public void setEntityName(String entityName) {
	this.entityName = entityName;
	}
	public IdSequence getIdsequence() {
	return getPopulatedIdsequence(new IdSequence());
	}
	
	public IdSequence getPopulatedIdsequence(IdSequence idSequence) {
	idSequence.setEntityName(this.getEntityName());
	idSequence.setIncrement(this.getIncrementByVal() == null ? null : this.getIncrementByVal());
	idSequence.setPrefix(this.getPrefixVal());
	idSequence.setStartIndex(this.getStartIndexVal() == null ? null : this.getStartIndexVal());
	idSequence.setSuffix(this.getSuffixVal());
	return idSequence;
	}
	
	public static class Builder{
		private String id;
		private String prefixVal;
		private String startIndex;
		private String suffix;
		private String incrementBy;
		private String entityName;
		private String caption;
		
		public Builder(String id, String a_prefixVal, String a_startIndex, String a_suffix, String a_incrementBy) {
		this.id = id;
		this.prefixVal = a_prefixVal;
		this.startIndex =  a_startIndex;
		this.suffix = a_suffix;
		this.incrementBy = a_incrementBy;
		}
	
		public Builder setEntityName(String entityName) {
		this.entityName = entityName;
		return this;
		}
		
		public Builder setCaption(String caption) {
		this.caption= caption;
		return this;
		}
		
		public IdSequenceHBox build() {
		return new IdSequenceHBox(this);
		}
	
	}
}
