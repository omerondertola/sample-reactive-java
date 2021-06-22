package tr.com.tolaas.spring.reactive.demo.samplereactivejava.fluxNmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.List;

public class CombineFluxTest {

    @Test
    public void testCombineFluxConcat() {
        Flux<String> flux1 = Flux.fromIterable(List.of("1","2","3"));
        Flux<String> flux2 = Flux.fromIterable(List.of("A","B","C"));

        Flux<String> mergedFlux = Flux.concat(flux1, flux2);

        StepVerifier.create(mergedFlux.log())
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    public void testCombineFluxConcatUnordered() {
        Flux<String> flux1 = Flux.fromIterable(List.of("1","2","3")).delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.fromIterable(List.of("a","b","c")).delayElements(Duration.ofMillis(500));

        Flux<String> mergedFlux = Flux.concat(flux1, flux2);

        StepVerifier.create(mergedFlux.log())
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    public void testCombineFluxMerge() {
        Flux<String> flux1 = Flux.fromIterable(List.of("1","2","3"));
        Flux<String> flux2 = Flux.fromIterable(List.of("A","B","C"));

        Flux<String> mergedFlux = Flux.merge(flux1, flux2);

        StepVerifier.create(mergedFlux.log())
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    public void testCombineFluxMergeUnordered() {
        Flux<String> flux1 = Flux.fromIterable(List.of("1","2","3")).delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.fromIterable(List.of("a","b","c")).delayElements(Duration.ofMillis(500));

        Flux<String> mergedFlux = Flux.merge(flux1, flux2);

        StepVerifier.create(mergedFlux.log())
                .expectNextCount(6)
                .verifyComplete();
    }
}
