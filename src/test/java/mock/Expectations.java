package mock;

import org.mockserver.integration.ClientAndServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class Expectations {

	public static void createDefaultExpectations(ClientAndServer mockServer) {

		// GET
		getProduct(mockServer);

	}



	private static void getProduct(ClientAndServer mockServer) {

		mockServer.when(request().withMethod("GET")
				.withHeader("Accept", "application/json").withPath("/billing/summary"))
				.respond(response().withStatusCode(200).withBody(String.valueOf(new ResponseEntity(HttpStatus.OK))));

	}


}