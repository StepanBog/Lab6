import akka.NotUsed;
import akka.actor.ActorRef;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import org.apache.zookeeper.ZooKeeper;
import org.asynchttpclient.AsyncHttpClient;

import static akka.http.javadsl.server.Directives.get;
import static akka.http.javadsl.server.Directives.parameter;

public class Annoymization {
    private AsyncHttpClient asyncHttpClient;
    private ActorRef storage;
    private ActorMaterializer materializer;
    private ZooKeeper zoo;
    private Http http;

    public Annoymization(AsyncHttpClient asyncHttpClient, ActorRef storage, ActorMaterializer materializer, ZooKeeper zoo, Http http) {

        this.asyncHttpClient = asyncHttpClient;
        this.storage = storage;
        this.materializer = materializer;
        this.zoo = zoo;
        this.http = http;
    }

    public Flow<HttpRequest, HttpResponse, NotUsed> createRoute() {
        return concat(
                get(() -> parameter("url",url ->
                        parameter("count",count ->
                                ))
                )
        )
    }
}
