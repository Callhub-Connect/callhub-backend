package callhub.connect.drivers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Value("${azure}")
    private String azureEndpoint;

    /**
     * Registers STOMP endpoints for WebSocket communication.
     *
     * This method adds a STOMP endpoint at '/callhub' and configures it to allow connections
     * from specified origins - locally from '<a href="http://localhost:3000/">...</a>' and from an Azure endpoint.
     *
     * @param registry The registry where the endpoint will be added.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry
                                               registry) {
        registry.addEndpoint("/callhub")
                .setAllowedOrigins("http://localhost:3000/", azureEndpoint);
    }

    /**
     * Configures the message broker for handling STOMP messages.
     *
     * This method sets up the message broker with a simple broker available at '/topic'.
     * It also defines '/app' as the prefix for application destination prefixes used in message mapping.
     *
     * @param config The MessageBrokerRegistry to configure.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }
}
