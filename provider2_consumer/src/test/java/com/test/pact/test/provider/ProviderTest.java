package com.test.pact.test.provider;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import com.pact.myapi.consumer.dto.Address;
import com.pact.myapi.consumer.dto.Student;
import com.test.consumer.MyConsumer;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;

public class ProviderTest {
	
private int PORT = 8092;
	
	@Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("studentservice", "localhost", PORT, this);
	
	@Pact(consumer = "consumer2",provider="studentservice")
    public RequestResponsePact  createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<String, String>();
        return builder
                .uponReceiving("a student request from consumer2")
                .path("/myapi/student/15")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body("{\"address\":{\"addressLine1\":\"1026YV\",\"addressLine2\":\"Arjenstraat\"}}")
                .toPact();
    }
	
	@Test
	@PactVerification(value="studentservice")
	public void testData() {
		
		Student student = MyConsumer.getStudent(PORT, 15);
		Address actualExpected = new Address("1026YV","Arjenstraat");
		Assert.assertTrue(student.getAddress().equals(actualExpected));
		
		
	}

}
