package {{ cookiecutter.basePackage }}.common.util;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * CIDR网段工具类
 */
public final class CidrUtil {

    private CidrUtil() {
    }

    /**
     * 获取网段内的所有IP
     *
     * @param cidr 网段
     */
    public static List<String> calculateIPList(String cidr) {
        List<String> ipList = new ArrayList<>();

        Long[] scope = getScopeIpLong(cidr);
        Long startAddress = scope[0];
        Long endAddress = scope[1];

        try {
            for (long i = startAddress + 1; i <= endAddress - 1; i++) {
                byte[] addressBytes = new byte[]{
                        (byte) (i >> 24),
                        (byte) (i >> 16),
                        (byte) (i >> 8),
                        (byte) i
                };
                InetAddress address = InetAddress.getByAddress(addressBytes);
                ipList.add(address.getHostAddress());
            }
        } catch (Exception ignored) {
            return ipList;
        }
        return ipList;
    }


    /**
     * 判断IP是否在网段内
     *
     * @param ip     IP
     * @param ipList 网段
     */
    public static boolean in(String ip, List<String> ipList) {
        for (String ipStr : ipList) {
            if (ipStr.equals(ip)) {
                return true;
            }

            if (ipStr.contains("/")) {
                boolean result = ipInCidr(ip, ipStr);
                if (result) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean notIn(String ip, List<String> ipList) {
        return !in(ip, ipList);
    }

    public static Long iptoLong(String ip) {
        String[] ips = ip.split("\\.");
        return 16777216L * Long.parseLong(ips[0]) + 65536L * Long.parseLong(ips[1]) +
                256 * Long.parseLong(ips[2]) + Long.parseLong(ips[3]);
    }

    public static String longToIp(Long ipLong) {
        return (ipLong >>> 24) +
                "." +
                ((ipLong & 0x00FFFFFF) >>> 16) +
                "." +
                ((ipLong & 0x0000FFFF) >>> 8) +
                "." +
                (ipLong & 0x000000FF);
    }

    /**
     * 获取网段开始IP和结束IP
     *
     * @param cidr 网段
     */
    public static Long[] getScopeIpLong(String cidr) {
        String[] parts = cidr.split("/");
        String ipAddress = parts[0];
        int prefixLength = Integer.parseInt(parts[1]);

        try {
            InetAddress startInetAddress = InetAddress.getByName(ipAddress);
            byte[] startAddressBytes = startInetAddress.getAddress();
            long startAddress = 0;
            for (byte b : startAddressBytes) {
                startAddress = startAddress << 8 | (b & 0xFF);
            }
            long endAddress = startAddress | ((1L << (32 - prefixLength)) - 1);
            return new Long[]{startAddress, endAddress};
        } catch (Exception ignored) {
            return new Long[]{};
        }
    }

    /**
     * 判断IP是否在某个IP段内
     *
     * @param ip   IP
     * @param cidr 网段
     * @return true-在， false-不在
     */
    public static boolean ipInCidr(String ip, String cidr) {
        Long[] scope = getScopeIpLong(cidr);
        Long startAddress = scope[0];
        Long endAddress = scope[1];

        try {
            long ipLong = iptoLong(ip);
            return ipLong >= startAddress && ipLong <= endAddress;
        } catch (Exception ignored) {
            return false;
        }
    }
}
