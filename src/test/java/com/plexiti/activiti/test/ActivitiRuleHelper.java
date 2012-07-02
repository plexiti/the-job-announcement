package com.plexiti.activiti.test;

import org.activiti.engine.test.ActivitiRule;

public class ActivitiRuleHelper {
	
	private static ThreadLocal<ActivitiRule> activitiRule = new ThreadLocal<ActivitiRule>();
	
	public static void set(ActivitiRule activitiRule) {
		ActivitiRuleHelper.activitiRule.set(activitiRule);
	};

	public static ActivitiRule get() {
		return ActivitiRuleHelper.activitiRule.get();
	};

}
