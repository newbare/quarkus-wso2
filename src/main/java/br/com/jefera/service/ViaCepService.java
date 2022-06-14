package br.com.jefera.service;

import br.com.jefera.factory.CorpClientHeadersFactory;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("")
@RegisterRestClient(configKey = "wso2-client")
@RegisterClientHeaders(CorpClientHeadersFactory.class)
public interface ViaCepService {
    @GET
    @Path("/cepe/1.0.0/ws/{cep}/json/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    String getEnderecoByCep( @PathParam String cep);
    @GET
    @Path("/api/v1/territory/state/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    String getEnderecoEstados();

    @GET
    @Path("/api/v1/territory/state/{estado}")
    String getEnderecoEstadosId(@PathParam int estado);


    @GET
    @Path("api/v1/opendata/products")
    String getProdutos();

}
