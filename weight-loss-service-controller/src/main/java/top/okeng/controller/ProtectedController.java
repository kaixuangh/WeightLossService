package top.okeng.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.okeng.rpc.response.RPCBaseResult;
import top.okeng.rpc.template.SOAProviderTemplate;

@RestController
@RequestMapping("/api/protected")
public class ProtectedController extends SOAProviderTemplate {

    @GetMapping("/hello")
    public RPCBaseResult<?> hello() {
        return execute(
                () -> "Hello from protected endpoint! Authentication successful!",
                SOAProviderTemplate::getFail
        );

    }

    @GetMapping("/user-info")
    public RPCBaseResult<?> getUserInfo() {
        // 这里可以获取当前认证用户的信息
        return execute(
                () -> "This is user-specific protected data",
                SOAProviderTemplate::getFail
        );
    }
}
