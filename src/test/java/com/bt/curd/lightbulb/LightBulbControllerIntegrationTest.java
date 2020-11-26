package com.bt.curd.lightbulb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.bt.curd.lightbulb.model.LightBulb;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.bt.curd.lightbulb.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LightBulbControllerIntegrationTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {

	}

	@Test
	public void testGetAllLightBulbs() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/lightBulbs",
				HttpMethod.GET, entity, String.class);
		
		assertNotNull(response.getBody());
	}

	@Test
	public void testGetLightBulbById() {
		LightBulb lightBulb = restTemplate.getForObject(getRootUrl() + "/lightBulbs/1", LightBulb.class);
		System.out.println(lightBulb.getColor());
		assertNotNull(lightBulb);
	}

	@Test
	public void testCreateLightBulb() {
		LightBulb lightBulb = new LightBulb();
		lightBulb.setState("Light");
		lightBulb.setColor("BLUE");

		ResponseEntity<LightBulb> postResponse = restTemplate.postForEntity(getRootUrl() + "/lightBulbs", lightBulb, LightBulb.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdateLightBulb() {
		int id = 1;
		LightBulb lightBulb = restTemplate.getForObject(getRootUrl() + "/lightBulbs/" + id, LightBulb.class);
		lightBulb.setState("Dark");
		lightBulb.setColor("BLUE");

		restTemplate.put(getRootUrl() + "/lightBulbs/" + id, lightBulb);

		LightBulb updatedLightBulb = restTemplate.getForObject(getRootUrl() + "/lightBulbs/" + id, LightBulb.class);
		assertNotNull(updatedLightBulb);
	}

	@Test
	public void testDeleteLightBulb() {
		int id = 2;
		LightBulb lightBulb = restTemplate.getForObject(getRootUrl() + "/lightBulbs/" + id, LightBulb.class);
		assertNotNull(lightBulb);

		restTemplate.delete(getRootUrl() + "/lightBulbs/" + id);

		try {
			lightBulb = restTemplate.getForObject(getRootUrl() + "/lightBulbs/" + id, LightBulb.class);
		} catch (final HttpClientErrorException e) {
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}
}
