package de.nilspreusker.test.mock;

import java.beans.FeatureDescriptor;
import java.util.Iterator;

import org.activiti.engine.impl.javax.el.ELContext;
import org.activiti.engine.impl.javax.el.ELResolver;

/*
 * @author nils.preusker@camunda.com
 */
public class MockElResolver extends ELResolver {

	@Override
	public Class<?> getCommonPropertyType(ELContext context, Object base) {
		return Object.class;
	}

	@Override
	public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
		return null;
	}

	@Override
	public Class<?> getType(ELContext context, Object base, Object property) {
		// TODO
		return null;
	}

	@Override
	public Object getValue(ELContext context, Object base, Object property) {
		Object bean = Mocks.get(property);
		if(bean != null) {
			context.setPropertyResolved(true);
		}
		return bean;
	}

	@Override
	public boolean isReadOnly(ELContext context, Object base, Object property) {
		// TODO
		return false;
	}

	@Override
	public void setValue(ELContext context, Object base, Object property, Object value) {
		// TODO
	}

}
