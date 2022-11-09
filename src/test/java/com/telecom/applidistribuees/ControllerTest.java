package com.telecom.applidistribuees;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("deprecation")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ControllerTest {
	@Autowired
    protected MockMvc mvc;
	
	@LocalServerPort
	private int port;

	protected String accessToken;
	
	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void greetingShouldReturnDefaultMessage() throws Exception {
		assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/",String.class));
	}
	
}