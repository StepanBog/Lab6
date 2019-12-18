import akka.Main;
import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import org.asynchttpclient.AsyncHttpClient;
import org.apache.log4j.Logger;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import scala.concurrent.Future;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.Directives.*;

import static org.asynchttpclient.Dsl.asyncHttpClient;
public class Lab6 {
    private ActorRef routerActor;

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {

        String host = args[0];
        int port = Integer.parseInt(args[1]);
        Logger log = Logger.getLogger(Main.class.getName());
        ZooKeeper zoo = new ZooKeeper("123.0.0.1:2181",3000, e->log.info(e.toString()));
        ActorSystem system = ActorSystem.create("lab6");
        ActorRef storage = system.actorOf((Props.create(StrageActor.class)));
        AsyncHttpClient asyncHttpClient = asyncHttpClient();
        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);
        Server server = new Server(zoo,storage);
        server.createServer(host,port);
        Annoymization app = new Annoymization(asyncHttpClient,storage,materializer,zoo,http);
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = app.createRoute().flow

        final CompletionStage<ServerBinding> binding = http.bindAndHandle(
                routeFlow,
                ConnectHttp.toHost("localhost", 8080),
                materializer
        );
        System.out.println("Server online at http://localhost:8080/\nPress RETURN to stop...");
        System.in.read();
        binding
                .thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate());

    }

}

