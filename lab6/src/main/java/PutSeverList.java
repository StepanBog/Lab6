import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PutSeverList {
    private List<String> seversList;

    PutSeverList(List<String> list){
        seversList = list;
    }

    public List<String> getList(){
        return seversList;
    }
}
