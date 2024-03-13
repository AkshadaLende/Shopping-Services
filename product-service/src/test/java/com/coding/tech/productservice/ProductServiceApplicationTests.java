package com.coding.tech.productservice;

import com.coding.tech.productservice.dto.ProductRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.google.common.net.MediaType;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.shaded.com.google.common.net.MediaType.*;

@SpringBootTest
public/*@Testcontainers
@AutoConfigureMockMvc*/
class ProductServiceApplicationTests {
	@Test
    public void contextLoads() {
	}

/*@Container
static	MongoDBContainer mongoDBContainer= new MongoDBContainer("mongo:4:4:2");
@Autowired
private MockMvc mockMvc;
@Autowired
private ObjectMapper objectMapper;

static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
	dynamicPropertyRegistry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);
}
	@Test
	void shouldCreateProduct() throws Exception{
		ProductRequest productRequest= getProductRequest();
String productRequestString= objectMapper.writeValueAsString(productRequest);
				mockMvc.perform(MockMvcRequestBuilders.post("api/product")
						.contentType(org.springframework.http.MediaType.APPLICATION_JSON)
						.content(productRequestString))
						.andExpect(status().isCreated());

	}

	private ProductRequest getProductRequest() {
	return ProductRequest.builder()
			.name("Iphone 13")
			.description("Iphone 13")
			.price(BigDecimal.valueOf(12000))
			.build();
	}*/

}
