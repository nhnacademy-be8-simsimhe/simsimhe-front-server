package com.simsimbookstore.frontserver.security.resolver;

import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.server.DefaultServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class CustomAuthorizationRequestResolver extends DefaultServerOAuth2AuthorizationRequestResolver {

    public CustomAuthorizationRequestResolver(ReactiveClientRegistrationRepository clientRegistrationRepository) {
        super(clientRegistrationRepository);
    }

    @Override
    public Mono<OAuth2AuthorizationRequest> resolve(ServerWebExchange exchange, String clientRegistrationId) {
        Mono<OAuth2AuthorizationRequest> authorizationRequestMono = super.resolve(exchange, clientRegistrationId);

        return authorizationRequestMono.map(authorizationRequest ->
                OAuth2AuthorizationRequest.from(authorizationRequest)
                        .additionalParameters(parameters -> {
                            parameters.put("serviceProviderCode", "FRIENDS");
                            parameters.put("userLocale", "ko_KR");
                        })
                        .build()
        );
    }
}
