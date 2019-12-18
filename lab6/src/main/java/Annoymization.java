import akka.actor.ActorRef;
import akka.http.javadsl.Http;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import org.apache.log4j.Logger;
import org.apache.zookeeper.ZooKeeper;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Request;
import org.asynchttpclient.Response;
import scala.compat.java8.FutureConverters;

import javax.annotation.processing.Completion;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;
import java.util.regex.Pattern;


import static akka.http.javadsl.server.Directives.*;
import static akka.http.javadsl.server.Directives.parameter;

public class Annoymization {
    private Logger log = Logger.getLogger(Annoymization.class.getName());
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
                                analyseUrlCount(url,Integer.parseInt(count))
                                ))));
    }

    private Route analyseUrlCount(String url, int count) {
        CompletionStage<Response> responce;
        if (count == 0)
            responce = fetch(asyncHttpClient.prepareGet(url).build());
        else
            responce = requestToRandomServer(url,count-1);

        return completeOKWithFutureString(responce.thenApply(Response::getResponseBody));
    }

    private CompletionStage<Response> requestToRandomServer(String url, int count) {
        return FutureConverters.toJava(Patterns.ask(storage,new GetRandomServer(),5000))
                .thenApply(s-> (ServerMsg)s)
                .thenApply(ser -> ser.getRandServer())
                .thenCompose(server -> fetch(createRequest(url)))
    }

    private CompletionStage<Response> fetch(Request request) {
        log.info(request.getUri());
        return asyncHttpClient.executeRequest(request).toCompletableFuture();
    }
}
