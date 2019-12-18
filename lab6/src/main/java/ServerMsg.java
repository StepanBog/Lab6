public class ServerMsg {
    private int randServer;
    public ServerMsg(String s) {
        randServer = Integer.parseInt(s);
    }

    public int getRandServer() {
        return randServer;
    }
}
