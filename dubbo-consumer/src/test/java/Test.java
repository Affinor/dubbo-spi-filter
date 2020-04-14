import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        List<Long> list = new ArrayList<>();
        list.add(10L);
        list.add(2L);
        list.add(3L);
        list.add(4L);
        list.add(7L);
        list.add(6L);
        list.add(5L);
        list.add(8L);
        list.add(9L);
        list.add(1L);
        list.add(15L);
        list.add(1L);
        list.add(11L);
        Long aLong = list.stream()
                .sorted(Comparator.comparingLong(Long::longValue))
                .skip((long) (list.size() * 0.90D)-1L)
                .limit((long) (list.size() * 0.90D))
                .findFirst()
                .get();
        System.out.println(aLong);
    }
}
