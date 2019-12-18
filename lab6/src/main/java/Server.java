import akka.actor.ActorRef;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

public class Server {
    private ZooKeeper zoo;
    private ActorRef storage;
    public Server(ZooKeeper zoo, ActorRef storage){
        this.zoo = zoo;
        this.storage = storage;
    }

    public void createServer(String host, int port) throws KeeperException, InterruptedException {
        zoo.create("/servers/",(host + ":" + port).getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("Server created");
    }
}
