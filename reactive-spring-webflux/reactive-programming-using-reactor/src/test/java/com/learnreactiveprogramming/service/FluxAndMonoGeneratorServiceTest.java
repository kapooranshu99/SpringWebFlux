package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService service = new FluxAndMonoGeneratorService();

    @Test
    void namesFlux() {
        var namesFlux = service.namesFlux();
        StepVerifier.create(namesFlux)
                //.expectNext("Gaurav", "Gautam", "Monika", "Siddhartha")
                .expectNext("Gaurav")
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void namesMono() {
        var namesMono = service.namesMono();
        StepVerifier.create(namesMono)
                .expectNext("Gautam")
                .verifyComplete();
    }

    @Test
    void namesFluxMap() {
        var namesFluxMap = service.namesFluxMap();
        StepVerifier.create(namesFluxMap)
                .expectNext("GAURAV", "GAUTAM", "MONIKA", "SIDDHARTHA")
                .verifyComplete();
    }

    @Test
    void namesFluxFlatmap() {
        var namesFluxFlatmap = service.namesFluxFlatmap();
        StepVerifier.create(namesFluxFlatmap)
                //.expectNext("G","A","U","R","A","V", "G","A","U","T","A","M", "M","O","N","I","K","A", "S","I","D","D","H","A","R","T","H","A")
                .expectNextCount(28)
                .verifyComplete();
    }

    @Test
    void namesFluxFlatmapAsync() {
        var namesFluxFlatmapAsync = service.namesFluxFlatmapAsync();
        StepVerifier.create(namesFluxFlatmapAsync)
                .expectNext("G","A","U","R","A","V", "G","A","U","T","A","M", "M","O","N","I","K","A", "S","I","D","D","H","A","R","T","H","A")
                .verifyComplete();

    }

    @Test
    void namesFluxConcatmap() {{
        var namesFluxConcatmap = service.namesFluxConcatmap();
        StepVerifier.create(namesFluxConcatmap)
                .expectNext("G","A","U","R","A","V", "G","A","U","T","A","M", "M","O","N","I","K","A", "S","I","D","D","H","A","R","T","H","A")
                .verifyComplete();

    }
    }

    @Test
    void namesMonoFlatmap() {
        var namesMonoFlatmap = service.namesMonoFlatmap();
        StepVerifier.create(namesMonoFlatmap)
                .expectNext(List.of("G","A","U","T","A","M"))
                .verifyComplete();
    }

    @Test
    void namesMonoFlatmapmany() {
        var namesMonoFlatmapmany = service.namesMonoFlatmapmany();
        StepVerifier.create(namesMonoFlatmapmany)
                .expectNext("G","A","U","T","A","M")
                .verifyComplete();
    }

    @Test
    void namesFluxTransform() {
        var namesFluxTransform = service.namesFluxTransform();
        StepVerifier.create(namesFluxTransform)
                .expectNext("GAURAV", "GAUTAM", "MONIKA", "SIDDHARTHA")
                .verifyComplete();
    }

    @Test
    void namesFluxTransform_1() {
        var namesFluxTransform = service.namesFluxTransform();
        StepVerifier.create(namesFluxTransform)
                .expectNext("No Value Available")
                .verifyComplete();
    }

    @Test
    void namesFluxSwitchIfEmpty() {
        var namesFluxSwitchIfEmpty = service.namesFluxSwitchIfEmpty();
        StepVerifier.create(namesFluxSwitchIfEmpty)
                .expectNext("Default","Value")
                .verifyComplete();
    }

    @Test
    void explore_concat() {
        var concatFlux = service.explore_concat();
        StepVerifier.create(concatFlux)
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();
    }

    @Test
    void explore_concatWith() {
        var concatFlux = service.explore_concatWith();
        StepVerifier.create(concatFlux)
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();
    }

    @Test
    void explore_merge() {
        var mergeFlux = service.explore_merge();
        StepVerifier.create(mergeFlux)
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    void explore_mergeWith() {
        var mergeFlux = service.explore_mergeWith();
        StepVerifier.create(mergeFlux)
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    void explore_mergesequencial() {
        var mergeFlux = service.explore_mergesequencial();
        StepVerifier.create(mergeFlux)
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();

    }

    @Test
    void explore_zip() {
        var zipFlux = service.explore_zip();
        StepVerifier.create(zipFlux)
                //.expectNext("AD","BE","CF")
                .expectNext("AD14", "BE25", "CF36")
                .verifyComplete();
    }

    @Test
    void explore_zipWith() {
        var zipFlux = service.explore_zipWith();
        StepVerifier.create(zipFlux)
                .expectNext("AD","BE","CF")
                //.expectNext("AD14", "BE25", "CF36")
                .verifyComplete();
    }
}