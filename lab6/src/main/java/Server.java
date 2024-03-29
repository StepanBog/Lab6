import akka.actor.ActorRef;
import akka.testkit.TestActors;
import org.apache.zookeeper.*;

import java.util.List;
import java.util.stream.Collectors;

public class Server {
    private ZooKeeper zoo;
    private ActorRef storage;

    public Server(ZooKeeper zoo, ActorRef storage){
        this.zoo = zoo;
        this.storage = storage;
        watching(null);
    }

    private void watching(WatchedEvent watchedEvent) {
        if (watchedEvent != null)
            System.out.println(watchedEvent.toString());
        try{
            saveServers(zoo.getChildren("/servers",this::watching).stream()
                    .map(s->"/servers/" + s)
                    .collect(Collectors.toList()));
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }

    }
    public void createServer(String host, int port,String name) throws KeeperException, InterruptedException {
        zoo.create("/servers/" + name ,(host + ":" + port).getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("Server created");
    }

    public void close() throws KeeperException, InterruptedException {
        zoo.removeAllWatches("/servers", Watcher.WatcherType.Any,true);
    }

    public void  saveServers(List<String> servers){
        for (int i= 0;
            i < servers.size(); i++ ) {
            System.out.print(servers.get(i) + " ");

        }
        System.out.println();
        this.storage.tell(new PutSeverList(servers),ActorRef.noSender());
    }
}
