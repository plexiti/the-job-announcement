package com.plexiti.activiti.test;

import static org.fest.assertions.Assertions.assertThat;

import org.activiti.engine.repository.DiagramLayout;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;
import org.fest.assertions.Condition;

public class ActivitiFestConditions {
	
	public static Condition<Object> assignedTo(final String userId) {
		return new Condition<Object>() {
			@Override
			public boolean matches(Object task) {
				assertThat(userId).isNotNull();
				// This cast and the Condition<Object> return value should not be necessary anymore with a newer version of fest...
				// TODO Review in more depth http://jira.codehaus.org/browse/FEST-363
				return userId.equals(((Task) task).getAssignee());
			}
		};
	}
	
	public static Condition<Object> atActivity(final String nodeId) {
		return new Condition<Object>() {
			@Override
			public boolean matches(Object execution) {
				assertThat(nodeId).isNotNull();
				assertThat(execution).isNotNull();
				// This cast and the Condition<Object> return value should not be necessary anymore with a newer version of fest...
				// TODO Review in more depth http://jira.codehaus.org/browse/FEST-363
				Execution exec = (Execution) execution;
				String targetActivity = (String) ActivitiRuleHelper.get().getRuntimeService().getVariableLocal(exec.getId(), TestProcessInstance.ActivitiTargetActivity);
				boolean atActivity = ActivitiRuleHelper.get().getRuntimeService().getActiveActivityIds(exec.getId()).contains(nodeId);
				if (nodeId.equals(targetActivity) && atActivity) {
					throw new TestProcessInstance.ActivitiTargetActivityReached();
				}
				return atActivity;
			}
		};
	}

	public static Condition<Object> containingNode(final String nodeId) {
		return new Condition<Object>() {
			@Override
			public boolean matches(Object diagramLayout) {
				assertThat(nodeId).isNotNull();
				// This cast and the Condition<Object> return value should not be necessary anymore with a newer version of fest...
				// TODO Review in more depth http://jira.codehaus.org/browse/FEST-363
				return ((DiagramLayout) diagramLayout).getNode(nodeId) != null;
			}
		};
	}

	public static Condition<Object> finished() {
		return new Condition<Object>() {
			@Override
			public boolean matches(Object execution) {
				// This cast and the Condition<Object> return value should not be necessary anymore with a newer version of fest...
				// TODO Review in more depth http://jira.codehaus.org/browse/FEST-363
				return execution == null || ((Execution) execution).isEnded();
			}
		};
	}
	
	public static Condition<Object> inCandidateGroup(final String groupId) {
		return new Condition<Object>() {
			@Override
			public boolean matches(Object task) {
				assertThat(groupId).isNotNull();
				// This cast and the Condition<Object> return value should not be necessary anymore with a newer version of fest...
				// TODO Review in more depth http://jira.codehaus.org/browse/FEST-363
				return ActivitiRuleHelper.get().getTaskService().createTaskQuery().taskId(((Task) task).getId()).taskCandidateGroup(groupId).singleResult() != null;
			}
		};
	}

	public static Condition<Object> started() {
		return new Condition<Object>() {
			@Override
			public boolean matches(Object execution) {
				// This cast and the Condition<Object> return value should not be necessary anymore with a newer version of fest...
				// TODO Review in more depth http://jira.codehaus.org/browse/FEST-363
				return execution != null && !((Execution) execution).isEnded();
			}
		};
	}

	public static Condition<Object> unassigned() {
		return new Condition<Object>() {
			@Override
			public boolean matches(Object task) {
				// This cast and the Condition<Object> return value should not be necessary anymore with a newer version of fest...
				// TODO Review in more depth http://jira.codehaus.org/browse/FEST-363
				return ((Task) task).getAssignee() == null;
			}
		};
	}
	
}
