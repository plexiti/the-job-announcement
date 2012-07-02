package com.plexiti.activiti.test.mocking;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

import com.plexiti.activiti.test.ActivitiTest;

public class ActivitiMockitoTest extends ActivitiTest {

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
		Mockitos.register(this); 
	}

}
