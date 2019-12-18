import akka.actor.ActorRef;
import org.apache.zookeeper.*;

import java.util.List;

public class Server {
    private ZooKeeper zoo;
    private ActorRef storage;
    public Server(ZooKeeper zoo, ActorRef storage){
        this.zoo = zoo;
        this.storage = storage;

    }

    private void watchChildren(WatchedEvent watchedEvent){
        
    }
    public void createServer(String host, int port) throws KeeperException, InterruptedException {
        zoo.create("/servers/",(host + ":" + port).getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("Server created");
    }

    public void close() throws KeeperException, InterruptedException {
        zoo.removeAllWatches("/servers", Watcher.WatcherType.Any,true);
    }

    public void  saveServers(List<String> servers){
        storage.tell(new PutSeverList(servers),ActorRef.noSender());
    }
}
