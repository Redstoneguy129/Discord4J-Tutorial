package me.cameronwhyte.tutorials.discord4j;

import discord4j.core.DiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

public class Main {

    public static void main(String[] args) {
        final DiscordClient client = DiscordClient.create("Nzg2OTkyNjIzOTI2NjQwNjcw.X9Od_w.UVMV2EjPUTPUVLKYoWRtVxf2NPw");
        client.withGateway(gateway -> {
            final Publisher<?> pingPong = gateway.on(MessageCreateEvent.class, event ->
                    Mono.just(event.getMessage())
                            .filter(message -> "!ping".equalsIgnoreCase(message.getContent()))
                            .flatMap(Message::getChannel)
                            .flatMap(channel -> channel.createMessage("Pong!")));
            return Mono.when(pingPong);
        }).block();
        client.login().block();
    }
}
