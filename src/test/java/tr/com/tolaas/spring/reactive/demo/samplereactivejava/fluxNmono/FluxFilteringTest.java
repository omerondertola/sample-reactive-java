package tr.com.tolaas.spring.reactive.demo.samplereactivejava.fluxNmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

public class FluxFilteringTest {

    @Test
    public void testFluxFilter() {
        List<String> family = List.of("Ömer","Defne","İrem","Ayşegül");
        Flux<String> stringFlux = Flux.fromIterable(family)
            .filter(s -> s.contains("r"))
            .log();

        StepVerifier.create(stringFlux)
                .expectNext("Ömer")
                .expectNext("İrem")
                .verifyComplete();
    }

    @Test
    public void testFluxFilterByLength() {
        List<String> family = List.of("Ömer","Defne","İrem","Ayşegül");
        Flux<String> stringFlux = Flux.fromIterable(family)
            .filter(s -> s.length() > 4)
            .log();

        StepVerifier.create(stringFlux)
                .expectNext("Defne")
                .expectNext("Ayşegül")
                .verifyComplete();
    }
}
