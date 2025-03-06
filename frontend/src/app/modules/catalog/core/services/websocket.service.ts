import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Client, IMessage, StompHeaders } from '@stomp/stompjs';
import { AuthenticationService } from './authentication.service';
import { BookItemRequest } from '../../shared/models/book-item-request';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  private serverUrl = 'http://localhost:8080/ws';
  private stompClient: Client;
  private connectionStatus$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  constructor(
    private authService: AuthenticationService,
    private storageService: StorageService
  ) {
    this.authService.isLoggedIn$.subscribe({
      next: isLoggedIn => {
        if (isLoggedIn) {
          this.connect();
        } else {
          this.disconnect();
        }
      }
    });
  }

  private connect() {
    if (this.stompClient?.connected) {
      console.log('Already connected to WebSocket.');
      return;
    }
    this.stompClient = this.createStompClient();
    this.stompClient.activate();
  }

  private disconnect() {
    this.stompClient.deactivate();
  }

  private createStompClient(): Client {
    const headers: StompHeaders = {
      Authorization: `Bearer ${this.storageService.getAccessToken()}`
    };

    return new Client({
      brokerURL: this.serverUrl,
      connectHeaders: headers,
      debug: function (str) {
        // console.log(str);
      },
      reconnectDelay: 200,
      heartbeatIncoming: 0,
      heartbeatOutgoing: 20000,
      onConnect: () => {
        this.connectionStatus$.next(true);
      },
      onDisconnect: () => {
        this.connectionStatus$.next(false);
      },
    });
  }

  public subscribeToTopic<T>(topic: string): Observable<T> {
    return new Observable<T>(observer => {
        const connectionSubscription = this.connectionStatus$.subscribe({
            next: isConnected => {
              if (isConnected) {
                const stompSubscription = this.stompClient.subscribe(topic, (message: IMessage) => {
                    try {
                        const parsedMessage = JSON.parse(message.body) as T;
                        observer.next(parsedMessage);
                    } catch (error: any) {
                        console.error(`JSON parsing error: ${error.message}`);
                    }
                });

                return () => {
                    console.log(`Unsubscribing from topic: ${topic}`);
                    stompSubscription.unsubscribe();
                    connectionSubscription.unsubscribe();
                };
            } else {
                return;
            }
            },
            error: err => observer.error(`WebSocket connection error: ${err}`),
            complete: () => observer.complete()
        });

        return () => {
          connectionSubscription.unsubscribe();
      };
    });
  }

  sendToTopic(topic: string, item: BookItemRequest) {
    this.stompClient.publish({ destination: topic, body: JSON.stringify(item) });
  }
}
