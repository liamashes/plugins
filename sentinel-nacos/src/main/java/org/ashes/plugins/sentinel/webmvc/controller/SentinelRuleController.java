package org.ashes.plugins.sentinel.webmvc.controller;

import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ashes
 * @create: 2023-07-21 17:20
 * @description:
 */
@RestController
public class SentinelRuleController {
    private final Logger logger = LoggerFactory.getLogger(SentinelRuleController.class);


    @GetMapping("/flowRule")
    @ResponseBody
    public String flowRule() {
        String s = FlowRuleManager.getRules().toString();
        logger.info("getting rules: {}", s);
        return s;
    }

}
