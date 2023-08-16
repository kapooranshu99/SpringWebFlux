package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class FluxAndMonoGeneratorService {

    public Flux<String> namesFlux(){
        return Flux.fromIterable(List.of("Gaurav", "Gautam", "Monika", "Siddhartha")).log();
    }

    public Flux<String> namesFluxMap(){
        return Flux.fromIterable(List.of("Gaurav", "Gautam", "Monika", "Siddhartha"))
                .map(String::toUpperCase)
                .log();
    }

    public Flux<String> namesFluxFlatmap(){
        return Flux.fromIterable(List.of("Gaurav", "Gautam", "Monika", "Siddhartha"))
                .map(String::toUpperCase)
                .flatMap(name -> splitString(name))
                .log();
    }

    public Flux<String> namesFluxFlatmapAsync(){
        return Flux.fromIterable(List.of("Gaurav", "Gautam", "Monika", "Siddhartha"))
                .map(String::toUpperCase)
                .flatMap(name -> splitStringWithDelay(name))
                .log();
    }

    public Flux<String> namesFluxConcatmap (){
        return Flux.fromIterable(List.of("Gaurav", "Gautam", "Monika", "Siddhartha"))
                .map(String::toUpperCase)
                .flatMap(name -> splitStringWithDelay(name))
                .log();
    }

    public Flux<String> splitString(String name){
        var array = name.split("");
        return Flux.fromArray(array);
    }

    public Flux<String> splitStringWithDelay(String name){
        var array = name.split("");
        var delay = new Random().nextInt(1000);
        return Flux.fromArray(array)
                .delayElements(Duration.ofMillis(delay));
    }

    public Mono<String> namesMono(){
        return Mono.just("Gautam").log();
    }

    public Mono<List<String>> namesMonoFlatmap(){

        return Mono.just("Gautam")
                .map(String::toUpperCase)
                .filter(name -> name.length()>3)
                .flatMap(this::splitStringMono)
                .log();
    }

    public Mono<List<String>> splitStringMono(String name){
        var array = name.split("");
        var list = List.of(array);
        return Mono.just(list);
    }

    public Flux<String> namesMonoFlatmapmany(){

        return Mono.just("Gautam")
                .map(String::toUpperCase)
                .filter(name -> name.length()>3)
                .flatMapMany(this::splitString)
                .log();
    }

    public Flux<String> namesFluxTransform(){
        Function<Flux<String>, Flux<String>> filtermap = name -> name.map(String::toUpperCase)
                .filter(s -> s.length() >10);
        return Flux.fromIterable(List.of("Gaurav", "Gautam", "Monika", "Siddhartha"))
                .transform(filtermap)
                .defaultIfEmpty("No Value Available")
                .log();
    }

    public Flux<String> namesFluxSwitchIfEmpty(){
        Function<Flux<String>, Flux<String>> filtermap = name -> name.map(String::toUpperCase)
                .filter(s -> s.length() >10);
        var defaultFlux = Flux.fromIterable(List.of("Default","Value"));
        return Flux.fromIterable(List.of("Gaurav", "Gautam", "Monika", "Siddhartha"))
                .transform(filtermap)
                .switchIfEmpty(defaultFlux)
                .log();
    }

    public Flux<String> explore_concat(){
        var abcFlux = Flux.just("A","B","C");
        var defFlux = Flux.just("D","E","F");
        return Flux.concat(abcFlux,defFlux).log();
    }

    public Flux<String> explore_concatWith(){
        var abcFlux = Flux.just("A","B","C");
        var defFlux = Flux.just("D","E","F");
        return abcFlux.concatWith(defFlux).log();
    }

    public Flux<String> explore_merge(){
        var abcFlux = Flux.just("A","B","C").delayElements(Duration.ofMillis(10));
        var defFlux = Flux.just("D","E","F");
        return Flux.merge(abcFlux,defFlux).log();
    }

    public Flux<String> explore_mergeWith(){
        var abcFlux = Flux.just("A","B","C").delayElements(Duration.ofMillis(10));
        var defFlux = Flux.just("D","E","F");
        return abcFlux.mergeWith(defFlux).log();
    }

    public Flux<String> explore_mergesequencial(){
        var abcFlux = Flux.just("A","B","C").delayElements(Duration.ofMillis(10));
        var defFlux = Flux.just("D","E","F");
        return Flux.mergeSequential(abcFlux,defFlux).log();
    }

    public Flux<String> explore_zip(){
        var abcFlux = Flux.just("A","B","C").delayElements(Duration.ofMillis(10));
        var defFlux = Flux.just("D","E","F");
        var _123Flux = Flux.just("1", "2", "3", "4");
        var _456Flux = Flux.just("4", "5", "6");
        return Flux.zip(abcFlux, defFlux, _123Flux, _456Flux)
                .map(t4 -> t4.getT1()+t4.getT2()+t4.getT3()+t4.getT4()).log();
        //return Flux.zip(abcFlux,defFlux, (f, s) -> f+s).log();
    }

    public Flux<String> explore_zipWith(){
        var abcFlux = Flux.just("A","B","C").delayElements(Duration.ofMillis(10));
        var defFlux = Flux.just("D","E","F");
        return abcFlux.zipWith(defFlux, (f,s) -> f+s).log();
    }

    public static void main(String[] args) {
        FluxAndMonoGeneratorService service = new FluxAndMonoGeneratorService();
        /**
         * onSubscribe([Synchronous Fuseable] FluxIterable.IterableSubscription)
         * request(unbounded)
         * onNext(Gaurav)
         * onNext(Gautam)
         * onNext(Monika)
         * onNext(Siddhartha)
         * onComplete()
         */
        service.namesFlux()
                .subscribe(name -> {System.out.println("Name is "+name);});
        /**
         * onSubscribe([Synchronous Fuseable] Operators.ScalarSubscription)
         * request(unbounded)
         * onNext(Gautam)
         * onComplete()
         */
        service.namesMono()
                .subscribe(name -> {System.out.println("Mono Name is "+name);});
    }
}
