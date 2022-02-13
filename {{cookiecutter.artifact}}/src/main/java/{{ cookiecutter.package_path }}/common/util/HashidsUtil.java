package {{ cookiecutter.basePackage }}.common.util;

import org.hashids.Hashids;

/**
 * 用于对数据表主键进行混淆
 */
public class HashidsUtil {

    public static final String salt = "{{ random_ascii_string(16) }}";

    public static final Hashids hashids = new Hashids(salt, 12);


    public static String encode(Long id) {
        return hashids.encode(id);
    }

    public static Long decode(String hash) {
        long[] key = hashids.decode(hash);
        // Parsing error
        if (key.length <= 0) {
            return null;
        }
        return key[0];
    }

}
