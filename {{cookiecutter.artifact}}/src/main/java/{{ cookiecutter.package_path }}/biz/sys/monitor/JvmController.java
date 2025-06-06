package {{ cookiecutter.basePackage }}.biz.sys.monitor;

import cn.hutool.system.SystemUtil;
import {{ cookiecutter.basePackage }}.common.aspect.LogOperation;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * 服务管理
 */
@Slf4j
@RestController
@RequestMapping("/sys")
public class JvmController {

    /**
     * 获取JVM信息
     */
    @LogOperation(name = "获取JVM信息", biz = "sys")
    @PreAuthorize("@ck.hasPermit('sys:jvm:get')")
    @GetMapping("/jvm")
    public R<JvmResponse> get() {
        JvmResponse jvmResponse = new JvmResponse();
        jvmResponse.setProcessId(getProcessId());
        log.info("{}", SystemUtil.getJvmSpecInfo());
        log.info("{}", SystemUtil.getJvmInfo());
        log.info("{}", SystemUtil.getJavaSpecInfo());
        log.info("{}", SystemUtil.getJavaInfo());
        log.info("{}", SystemUtil.getJavaRuntimeInfo());
        log.info("{}", SystemUtil.getOsInfo());
        log.info("{}", SystemUtil.getUserInfo());
        log.info("{}", SystemUtil.getHostInfo());
        log.info("{}", SystemUtil.getRuntimeInfo());
        return R.ok(jvmResponse);
    }

    /**
     * 获取进程ID
     */
    private static int getProcessId() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return Integer.parseInt(runtimeMXBean.getName().split("@")[0]);
    }

}
