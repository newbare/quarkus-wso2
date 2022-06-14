package br.com.jefera.resource;

import br.com.jefera.service.ViaCepService;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/estados/estados")
public class EstadosResource {

    @Inject
    @RestClient
    public ViaCepService viaCepService;

    @GET
    @Path("/endereco/estados")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response estados() {

        String response;

        try {
            String endereco = this.viaCepService.getEnderecoEstados();
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

    @GET
    @Path("endereco/estados/{estado}")
    public String estado(@PathParam("estado") @Parameter(required = true, example = "12") @NotBlank(message = "Identificação do Estado é obrigatório.")  int estado) {

        String response;

        try {
            String endereco = this.viaCepService.getEnderecoEstadosId(estado);
            response = endereco;
            if (endereco != null) {
                return response;
            } else {
                response = "Cep não encontrado na API do ViaCep";
                return Response.status(Status.NOT_FOUND).entity(response).build().toString();
            }
        } catch(Exception e) {
            response = "Erro ao consultar o serviço do ViaCep";
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(response)
                    .build().toString();
        }
    }
    @Path("{name}/{age:\\d+}")
    @GET
    public String personalisedHello( @PathParam("name")  String name, @PathParam("age")  String age) {
        return "Hello " + name + " is your age really " + age + "?";
    }

}