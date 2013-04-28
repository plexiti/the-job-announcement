package com.plexiti.activiti.test;

import org.camunda.bpm.engine.test.ProcessEngineRule;

public class ActivitiRuleHelper {
	
	private static ThreadLocal<ProcessEngineRule> activitiRule = new ThreadLocal<ProcessEngineRule>();
	
	public static void set(ProcessEngineRule activitiRule) {
		ActivitiRuleHelper.activitiRule.set(activitiRule);
	};

	public static ProcessEngineRule get() {
		return ActivitiRuleHelper.activitiRule.get();
	};

}
