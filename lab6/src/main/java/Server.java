import akka.actor.ActorRef;
import org.apache.zookeeper.ZooKeeper;

public class Server {
    private ZooKeeper zoo;
    private ActorRef storage;
    public Server(ZooKeeper zoo, ActorRef storage){
        this.zoo = zoo;
        this.storage = storage;
    }
}
