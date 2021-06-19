package tr.com.tolaas.spring.reactive.demo.samplereactivejava.fluxNmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Locale;

public class FluxTransformTest {

    @Test
    public void testFluxTransform() {
        List<String> clubs = List.of("Barcelona","Manchester United","Milan");

        Flux<String> clubStream = Flux.fromIterable(clubs)
                .map(s -> s.toUpperCase() + " FC")
                .log();

        StepVerifier.create(clubStream)
                .expectNext("BARCELONA FC")
                .expectNext("MANCHESTER UNITED FC")
                .expectNext("MILAN FC")
                .verifyComplete();
    }

    @Test
    public void testFluxTransformToLenGth() {
        List<String> clubs = List.of("Barcelona","Manchester United","Milan");

        Flux<Integer> clubStream = Flux.fromIterable(clubs)
                .map(s -> s.length())
                .log();

        StepVerifier.create(clubStream)
                .expectNext("BARCELONA".length())
                .expectNext("MANCHESTER UNITED".length())
                .expectNext("MILAN".length())
                .verifyComplete();
    }
}
