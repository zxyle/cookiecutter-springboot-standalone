package {{ cookiecutter.basePackage }}.snippet;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;

public class Main {
    public static void main(String[] args) {
        // 返回2个值的pair
        ImmutablePair<String, String> props = new ImmutablePair<>("value1", "value2");
        System.out.println(props.getLeft());   // 输出 "value1"
        System.out.println(props.getRight());  // 输出 "value2"

        // 返回3个值的triple
        ImmutableTriple<String, Integer, Boolean> triple = new ImmutableTriple<>("value1", 42, true);
        System.out.println(triple.getLeft());   // 输出 "value1"
        System.out.println(triple.getMiddle()); // 输出 42
        System.out.println(triple.getRight());  // 输出 true
    }
}