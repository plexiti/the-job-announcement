package com.plexiti.activiti.test;
import static org.fest.assertions.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;

public abstract class ActivitiTest {
	
	private Map<String, TestProcessInstance> processes = new HashMap<String, TestProcessInstance>();

	@Rule
	public ProcessEngineRule activitiRule = new ProcessEngineRule();
	
	{
		ActivitiRuleHelper.set(activitiRule);
	}
	
	protected TestProcessInstance start(TestProcessInstance testProcess) {
		if (processes.containsKey(testProcess.processDefinitionKey)) {
			return processes.get(testProcess.processDefinitionKey);
		} else {
			processes.put(testProcess.processDefinitionKey, testProcess);
			testProcess.start();
			return testProcess;			
		}
	}
	
	protected TestProcessInstance process(String processDefinitionKey) {
		return processes.get(processDefinitionKey);
	}

	protected TestProcessInstance process() {
		assertThat(processes).hasSize(1);
		return processes.values().iterator().next();
	}

}
