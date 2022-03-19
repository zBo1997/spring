package com.mashibing.selfAutowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class LianController {

    @Lian
    private LianService lianService;

    @Resource
    List<TestService> testServiceList;

    public void show(){
        lianService.show();
    }

    /**
     * 测试特殊注入
     */
    public void testSpecialInject(){
        testServiceList.forEach(TestService::show);
    }
}
