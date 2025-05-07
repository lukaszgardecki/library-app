package com.example.activityservice.domain.ports;

public interface EventListenerPort {

     <T> void handle(T event);
}
