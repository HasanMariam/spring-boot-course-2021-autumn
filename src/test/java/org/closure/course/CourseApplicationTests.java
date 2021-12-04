package org.closure.course;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.closure.course.dto.JwtRequest;
import org.closure.course.services.TestService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
// @WithMockUser
// @WebMvcTest(EmployeeController.class)
class CourseApplicationTests {

	@Autowired
	MockMvc mockMvc;
	@Autowired
	TestService testService;

	@Test
	void sumTest() throws Exception{
		
		Assertions.assertThrows(Exception.class, () -> {
			testService.sum(5, 1);
		});
	}

	@Test
	void greetingWithNoJwt() throws Exception {
		mockMvc.perform(get("/greeting")).andExpect(status().isForbidden());
	}

	@Test
	void geeetingNotFoundUrl() throws Exception {
		mockMvc.perform(get("/greetings")).andExpect(status().isForbidden());
	}

	@Test
	void greretingWitRegisteredUserAndJwt() throws Exception {

		JwtRequest jwtRequest = new JwtRequest("anas", "pass");
		ObjectMapper objectMapper = new ObjectMapper();
		String jwtJson = objectMapper.writeValueAsString(jwtRequest);
		String jwtResponseJson = mockMvc
				.perform(post("/authenticate").contentType(MediaType.APPLICATION_JSON).content(jwtJson))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		JsonNode jsonNode = objectMapper.readTree(jwtResponseJson);
		String token = jsonNode.get("jwttoken").asText();
		mockMvc.perform(get("/greeting").header("Authorization", String.format("Bearer %s", token))).andExpect(status().isOk());
	}
}
