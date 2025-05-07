package com.example.activityservice.domain.ports.in;

public interface EventListenerPort {

     <T> void handle(T event);
}
