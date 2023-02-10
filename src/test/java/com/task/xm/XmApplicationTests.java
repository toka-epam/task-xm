package com.task.xm;

import com.task.xm.repository.CryptoDataRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@RequiredArgsConstructor
class XmApplicationTests {

	@Container
	private static final MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest")
			.withDatabaseName("xm_db");
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private CryptoDataRepository cryptoDataRepository;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.datasource.url",mySQLContainer::getJdbcUrl);
		dynamicPropertyRegistry.add("spring.datasource.username",mySQLContainer::getUsername);
		dynamicPropertyRegistry.add("spring.datasource.password",mySQLContainer::getPassword);
	}


	@Test
	void testCryptoNormalizedRangeList() throws Exception {
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/crypto-stats/normalized-range"))
				.andExpect(status().isOk());
		String response = resultActions.andReturn().getResponse().getContentAsString();
		JSONArray jsonArray = new JSONArray(response);

		assertEquals(cryptoDataRepository.findDistinctName().size(),jsonArray.length());
	}


	@Test
	void testGetCryptoStats() throws Exception {
		List<String> cryptoNames = cryptoDataRepository.findDistinctName();
		String urlTemplate = "/crypto-stats/eth";
		String incorrectCryptoName = "etttthhhh";
		String incorrectArgUrl = "/crypto-stats/" + incorrectCryptoName;
		mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate))
				.andExpect(status().isOk());

		mockMvc.perform(MockMvcRequestBuilders.get(incorrectArgUrl))
				.andExpect(status().isBadRequest());
	}
}
