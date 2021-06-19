package tr.com.tolaas.spring.reactive.demo.samplereactivejava.fluxNmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Supplier;

public class BasicMonoFactoryMethodsTest {

    @Test
    public void testEmptyMono() {
        Mono<String> nullMono = Mono.justOrEmpty(null);

        StepVerifier.create(nullMono.log())
                .verifyComplete();
    }

    @Test
    public void testMonoFromSupplier() {
        Supplier<String> myStringSupplier = () -> "Ömer Önder Tola";

        System.out.println(myStringSupplier.get());

        Mono<String> stringMono = Mono.fromSupplier(myStringSupplier).log();
        StepVerifier.create(stringMono)
                .expectNext("Ömer Önder Tola")
                .verifyComplete();
    }


}
