import akka.actor.AbstractActor;
import akka.actor.ActorRef;

import java.util.ArrayList;
import java.util.Random;

public class StrageActor extends AbstractActor {
    private ArrayList<String> storage;
    private Random rand;

    StrageActor(){
        this.rand = new Random();
        this.storage = new ArrayList<>();
    }
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(GetRandomServer.class,this::getRandomServer)
                .match(PutSeverList.class,this:: putServers)
                .build();
    }

    private  void putServers(PutSeverList putServers) {
        storage.addAll(putServers.getList());
    }

    public void getRandomServer(GetRandomServer getRandServ) {
        getSender().tell(new ServerMsg(storage.get(rand.nextInt(storage.size()))), ActorRef.noSender());
    }

    public void deleteServer(Deleteserver server){
        storage.remove(server.getServer());
    }

}
