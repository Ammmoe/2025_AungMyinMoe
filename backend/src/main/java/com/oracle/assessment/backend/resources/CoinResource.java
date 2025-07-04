package com.oracle.assessment.backend.resources;

import com.oracle.assessment.backend.api.CoinRequest;
import com.oracle.assessment.backend.api.CoinResponse;
import com.oracle.assessment.backend.service.CoinCalculatorService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/coins")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CoinResource {
	
	private final CoinCalculatorService service;
	
	public CoinResource(CoinCalculatorService service) {
		this.service = service;
	}
	
	@POST
	@Path("/")
	public Response getMinCoins(CoinRequest request) {
		try {
			return Response.ok(
				new CoinResponse(service.calculateMinCoins(request.getTargetAmount(), request.getCoinDenominations()))
			).build();
		} catch (IllegalArgumentException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
	
}
