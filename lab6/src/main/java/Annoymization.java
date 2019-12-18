import akka.actor.ActorRef;
import akka.http.javadsl.Http;
import akka.stream.ActorMaterializer;
import org.apache.zookeeper.ZooKeeper;
import org.asynchttpclient.AsyncHttpClient;

public class Annoymization {
    private AsyncHttpClient asyncHttpClient;
    private ActorRef storage;

    public Annoymization(AsyncHttpClient asyncHttpClient, ActorRef storage, ActorMaterializer materializer, ZooKeeper zoo, Http http) {

        this.asyncHttpClient = asyncHttpClient;
        this.storage = storage;
    }
}
