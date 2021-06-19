package tr.com.tolaas.spring.reactive.demo.samplereactivejava.fluxNmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.crypto.spec.PSource;
import javax.xml.transform.Source;

public class BasicMonoTest {

    @Test
    public void testMono() {
        Mono<String> monoStream = Mono.just("Ömer Önder Tola");
        monoStream.subscribe(System.out::println);
    }

    @Test
    public void testMonoVerifier() {
        Mono<String> monoStream = Mono.just("Ömer Önder Tola");
        StepVerifier.create(monoStream.log())
                .expectNext("Ömer Önder Tola")
                .verifyComplete();
    }

    @Test
    public void testMonoException() {
        Mono<Object> monoWithError = Mono.error(new RuntimeException("An error occured during mono processing.."));
        monoWithError = monoWithError.log();
        monoWithError.subscribe(
                System.out::println,
                error -> System.out.println("Logging the error: "+ error)
        );
    }

    @Test
    public void testMonoErrorVerification() {
        Mono<Object> errorMonoWithLog = Mono.error(new RuntimeException("Error")).log();
        StepVerifier.create(errorMonoWithLog)
                .expectErrorMessage("Error")
                .verify();
    }

    @Test
    public void testMonoErrorTypeVerification() {
        Mono<Object> error_to_be_verified = Mono.error(new RuntimeException("Error to be verified")).log();
        StepVerifier.create(error_to_be_verified)
                .expectError(RuntimeException.class)
                .verify();
    }
}
