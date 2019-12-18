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
                .match(Deleteserver.class,this::deleteServer)
                .build();
    }

    private  void putServers(PutSeverList putServers) {
        storage.clear();
        storage.addAll(putServers.getList());
    }

    public void getRandomServer(GetRandomServer getRandServ) {
        int i = rand.nextInt(storage.size());
        System.out.print(i);
            getSender().tell(
                    new ServerMsg(storage.get(i)), ActorRef.noSender());

    }

    public void deleteServer(Deleteserver server){
        this.storage.remove(server.getServer());
    }

}
