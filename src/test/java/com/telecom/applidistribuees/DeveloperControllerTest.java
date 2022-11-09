package com.telecom.applidistribuees;


import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;

public class DeveloperControllerTest extends ControllerTest {

	@Test
	public void testGetTasks() throws Exception {
		
		mvc.perform(get("/developers")
				.contentType(MediaType.APPLICATION_JSON))
			    .andExpect(status().isOk())
			    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			    .andExpect(jsonPath("$[0].firstname", is("dev1")))
			    .andExpect(jsonPath("$[0].lastname", is("dev1")))
			    .andExpect(jsonPath("$[0].password", is("dev1")))
			    .andExpect(jsonPath("$[0].email", is("dev1@dev.dev")))
			    ;
	}
}
