package com.telecom.applidistribuees;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
public class ActuatorTest extends ControllerTest {

    /**
     * Test for home page.
     *
     * @throws Exception On failure.
     */
    @Test
    public void actuator()
            throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/actuator")).andExpect(MockMvcResultMatchers.status().isOk());
        this.mvc.perform(MockMvcRequestBuilders.get("/actuator/info")).andExpect(MockMvcResultMatchers.status().isOk());
    }
}