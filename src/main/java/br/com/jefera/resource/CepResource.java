package br.com.jefera.resource;

import br.com.jefera.service.ViaCepService;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/endereco/cep")
public class CepResource  {

    @Inject
    @RestClient
    public ViaCepService viaCepService;

    @POST
    @Path("endereco")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response endereco(String cep) {

        String response;
        var jsonRequest = new  JSONObject(cep);
        try {
            String endereco = this.viaCepService.getEnderecoByCep(jsonRequest.getString("cep"));
            response = endereco;
            if (endereco != null) {
                return Response.ok(response).build();
            } else {
                response = "Cep não encontrado na API do ViaCep";
                return Response.status(Status.NOT_FOUND).entity(response).build();
            }
        } catch(Exception e) {
            response = "Erro ao consultar o serviço do ViaCep";
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(response)
                    .build();
        }
    }

}