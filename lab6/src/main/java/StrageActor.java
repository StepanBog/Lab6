import akka.actor.AbstractActor;

import java.util.ArrayList;
import java.util.Random;

public class StrageActor extends AbstractActor {
    private ArrayList<String> storage;
    private Random rand;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(GetRandomServer.class,this.getRandomServer());
    }

    private Object getRandomServer() {
    }

    StrageActor(){
        this.rand = new Random();
        this.storage = new ArrayList<>();
    }
}
