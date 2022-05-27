package br.com.jefera.factory;

import io.quarkus.oidc.client.OidcClient;
import io.quarkus.oidc.client.Tokens;
import io.vertx.core.http.HttpHeaders;
import org.apache.http.client.methods.HttpHead;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.Arrays;
import java.util.Optional;

import static br.com.jefera.utils.PropertyUtil.getProperty;

/**
 * Classe que adiciona a requisições de um client o header com a API key que da
 * acesso ao recurso no WSO2. O projeto que utilizar esta classe deve possuir
 * uma propriedade com nome "acesso.microsservico-corporativo.api-key".
 */
@RequestScoped
public class CorpClientHeadersFactory implements ClientHeadersFactory {

	private static final Logger LOGGER = Logger.getLogger(CorpClientHeadersFactory.class);

	@ConfigProperty(name = "propagacao")
	String propagate;

	@Inject
	OidcClient client;

	volatile Tokens currentTokens;

	@PostConstruct
	public Tokens init() {
		return client.getTokens().await().indefinitely();
	}

	@GET
	public String getToken() {

		Tokens tokens = currentTokens;
		tokens = client.getTokens().await().indefinitely();
		return "Bearer " + tokens.getAccessToken();// Use tokens.getAccessToken() to configure MP RestClient
													// Authorization header/etc
	}

	@Override
	public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders,
			MultivaluedMap<String, String> clientOutgoingHeaders) {

		MultivaluedMap<String, String> propagatedHeaders = new MultivaluedHashMap<>();
		Optional<String> propagateHeaderString = getProperty(propagate);
		propagateHeaderString.ifPresent(s -> Arrays.stream(s.split(",")).forEach(header -> {
			if (incomingHeaders.containsKey(header)) {
				propagatedHeaders.put(header, incomingHeaders.get(header));
			}
		}));

		propagatedHeaders.putAll(clientOutgoingHeaders);

		LOGGER.info("Utilizando autenticação OAuth");
		propagatedHeaders.putSingle(HttpHeaders.AUTHORIZATION.toString(), getToken());

		return propagatedHeaders;
	}

}

