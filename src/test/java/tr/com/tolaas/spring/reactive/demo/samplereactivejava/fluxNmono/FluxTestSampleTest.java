package tr.com.tolaas.spring.reactive.demo.samplereactivejava.fluxNmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxTestSampleTest {

    @Test
    public void testFluxBasicTest() {
        Flux<String> stringFlux = Flux.just("Elma", "Armut", "Kel Mahmut")
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Elma")
                .expectNext("Armut")
                .expectNext("Kel Mahmut")
                .verifyComplete();
    }

    @Test
    public void testFluxBasicTestThatFails() {
        Flux<String> stringFlux = Flux.just("Elma", "Armut", "Kel Mahmut")
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Armut")
                .expectNext("Elma")
                .expectNext("Kel Mahmut")
                .verifyComplete();
    }

    @Test
    public void testFluxBasicTestDoesNothingWithoutVerifyCompleteCall() {
        Flux<String> stringFlux = Flux.just("Elma", "Armut", "Kel Mahmut")
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Elma")
                .expectNext("Armut")
                .expectNext("Kel Mahmut");
    }

    @Test
    public void testFluxWithExceptionTypeCheck() {
        Flux<String> serialFlux = Flux.just("From", "Paris", "with", "Love")
                .concatWith(Flux.error(new RuntimeException("Love, Death and Robots")))
                .log();

        StepVerifier.create(serialFlux)
                .expectNext("From")
                .expectNext("Paris")
                .expectNext("with")
                .expectNext("Love")
                .expectError(RuntimeException.class)
                .verify();
    }


    @Test
    public void testFluxWithExceptionMessageCheck() {
        Flux<String> serialFlux = Flux.just("From", "Paris", "with", "Love")
                .concatWith(Flux.error(new RuntimeException("Love, Death and Robots")))
                .log();

        StepVerifier.create(serialFlux)
                .expectNext("From")
                .expectNext("Paris")
                .expectNext("with")
                .expectNext("Love")
                .expectErrorMessage("Love, Death and Robots")
                .verify();
    }

    @Test
    public void testFluxNoOfEvents() {
        Flux<String> popFlux = Flux.just("We", "Don't", "Need", "No", "Education")
                .concatWith(Flux.error(new RuntimeException("Hey, teacher!")))
                .log();

        StepVerifier.create(popFlux)
                .expectNextCount(5)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    public void testFluxWithSingleLineExpect() {
        Flux<String> wodFlux = Flux.just("Call", "me", "I", "will", "call", "you", "too.")
                .concatWith(Flux.error(new RuntimeException("what")))
                .log();

        StepVerifier.create(wodFlux)
                .expectNext("Call", "me", "I", "will", "call", "you", "too.")
                .expectErrorMessage("what")
                .verify();
    }

}
