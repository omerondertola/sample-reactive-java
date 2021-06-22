package tr.com.tolaas.spring.reactive.demo.samplereactivejava.fluxNmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static reactor.core.scheduler.Schedulers.parallel;

public class FluxFlapMapTest {

    @Test
    public void testFluxFlatMap() {
        List<List<Integer>> doubleList = IntStream.rangeClosed(0, 4)
                .mapToObj(i -> createList(i))
                .collect(toList());

        Flux<Integer> flatFlux = Flux.fromIterable(doubleList)
                .flatMap(s -> Flux.fromIterable(s));

        StepVerifier.create(flatFlux.log())
                .expectNextCount(5*5)
                .verifyComplete();
    }

    private List<Integer> createList(int i) {
        return IntStream.rangeClosed(i * 5 + 1, i * 5 + 5)
                .boxed()
                .collect(Collectors.toList());
    }

    @Test
    public void testDatabaseQueryFlux() {
        List<String> stringList = List.of("Ömer","Defne","İrem","Ayşegül","Önder","Engin");

        Flux<String> queryResultFlux = Flux.fromIterable(stringList)
                .flatMap(s -> Flux.fromIterable(simulateDBQuery(s)))
                .log();

        StepVerifier.create(queryResultFlux)
                .expectNextCount(12)
                .verifyComplete();
    }

    private List<String> simulateDBQuery(String queryParam) {
        List<String> queryResult = List.of("Query result for "+queryParam,queryParam);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return queryResult;
    }

    @Test
    public void testParallelQueryExecutionFlux() {
        List<String> stringList = List.of("Ömer","Defne","İrem","Ayşegül","Önder","Engin");

        Flux<String> parallelQueryExecutionFlux = Flux.fromIterable(stringList)
                .window(2)
                .flatMap(stringFlux -> stringFlux.map(this::simulateDBQuery).subscribeOn(parallel()))
                .flatMap(s -> Flux.fromIterable(s))
                .log();

        StepVerifier.create(parallelQueryExecutionFlux)
                .expectNextCount(12)
                .verifyComplete();
    }

    @Test
    public void testOrderedParallelQueryExecutionFlux() {
        List<String> stringList = List.of("Ömer","Defne","İrem","Ayşegül","Önder","Engin");

        Flux<String> parallelQueryExecutionFlux = Flux.fromIterable(stringList)
                .window(2)
                .flatMapSequential(stringFlux -> stringFlux.map(this::simulateDBQuery).subscribeOn(parallel()))
                .flatMap(s -> Flux.fromIterable(s))
                .log();

        StepVerifier.create(parallelQueryExecutionFlux)
                .expectNextCount(12)
                .verifyComplete();
    }
}
