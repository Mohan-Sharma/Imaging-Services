package com.nzion.hibernate.ext;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import com.nzion.domain.Historical;
import com.nzion.domain.HistoricalModel;
import com.nzion.domain.base.BaseEntity;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilDateTime;
import com.nzion.util.UtilValidator;

// TODO: Auto-generated Javadoc
/**
 * The Class AuditColumnPopulator.
 */
public class AutoColumnsPopulator extends EmptyInterceptor implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
/**
 * Removed star schema functionality
 */
	/*
	 * (non-Javadoc)
	 * @see org.hibernate.EmptyInterceptor#onSave(java.lang.Object, java.io.Serializable, java.lang.Object[],
	 * java.lang.String[], org.hibernate.type.Type[])
	 */
	/**
	 * Sets the createdBy and CreatedTimestamp for every persistent object that
	 * has createdBy and createdByTimestamp properties.
	 *
	 */
	@Override
	public boolean onSave(Object entity, Serializable id, Object[] currentState, String[] propertyNames, Type[] types) {
	if(entity instanceof BaseEntity && UtilValidator.isEmpty(((BaseEntity)entity).getCreatedBy())){
	setValue(currentState, propertyNames, "createdBy", Infrastructure.getUserName());
	setValue(currentState, propertyNames, "createdTxTimestamp", new Timestamp(new Date().getTime()));
	}
	else{
		setValue(currentState, propertyNames, "updatedBy", Infrastructure.getUserName());
		setValue(currentState, propertyNames, "updatedTxTimestamp", new Timestamp(new Date().getTime()));
	}

	if (Historical.class.isAssignableFrom(entity.getClass())) {

		HistoricalModel model = ((Historical)entity).getHistoricalModel();
		if(model==null){
			model = new HistoricalModel();
		}
		if(model.getFromDate()==null)
			model.setFromDate(UtilDateTime.nowDate());
		setValue(currentState, propertyNames, "historicalModel", model);
	}

	return true;
	}

	/**
	 * Sets the value.
	 *
	 * @param currentState
	 *            the current state
	 * @param propertyNames
	 *            the property names
	 * @param propertyToSet
	 *            the property to set
	 * @param value
	 *            the value
	 */
	private void setValue(Object[] currentState, String[] propertyNames, String propertyToSet, Object value) {
	int index = Arrays.asList(propertyNames).indexOf(propertyToSet);
	if (index >= 0) {
		currentState[index] = value;
	}
	}

	/*
	 * (non-Javadoc)
	 * @see org.hibernate.EmptyInterceptor#onFlushDirty(java.lang.Object, java.io.Serializable, java.lang.Object[],
	 * java.lang.Object[], java.lang.String[], org.hibernate.type.Type[])
	 */
	/**
	 *
	 * Sets the values for updatedBy and updatedTxTimestamp for all persistence
	 * classes having those properties.
	 */
	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
	setValue(currentState, propertyNames, "updatedBy", Infrastructure.getUserName());
	setValue(currentState, propertyNames, "updatedTxTimestamp", new Timestamp(new Date().getTime()));
	return true;
	}


	@Override
	public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
	super.onDelete(entity, id, state, propertyNames, types);
	}

	@Override
	public String onPrepareStatement(String sql) {
	// TODO Auto-generated method stub
	return super.onPrepareStatement(sql);
	}

}