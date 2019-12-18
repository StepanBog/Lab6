import akka.actor.ActorRef;
import akka.http.javadsl.Http;
import akka.http.javadsl.server.Route;
import akka.stream.ActorMaterializer;
import org.apache.zookeeper.ZooKeeper;
import org.asynchttpclient.AsyncHttpClient;

import static akka.http.javadsl.server.Directives.*;
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

    public Route createRoute() {
        return concat(
                get(() -> parameter("url",url ->
                        parameter("count",count ->
                                analyseUrlCount(url,count)
                                ))));
    }

    private Route analyseUrlCount(String url, String count) {
        if (count == 0)
            

    }
}
