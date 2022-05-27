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

@Path("/livrese/cep")
public class ProdutoResource {

    @Inject
    @RestClient
    public ViaCepService viaCepService;

    @POST
    @Path("produto")
    @Produces(MediaType.APPLICATION_JSON)
    public Response produto() {

        String response;
        try {
            String endereco = this.viaCepService.getProdutos();
            response = endereco;
            if (endereco != null) {
                return Response.ok(response).build();
            } else {
                response = "Produtos não Encontrado";
                return Response.status(Status.NOT_FOUND).entity(response).build();
            }
        } catch(Exception e) {
            response = "Erro ao consultar o serviço de Produtos";
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(response)
                    .build();
        }
    }

}