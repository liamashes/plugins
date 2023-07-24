package org.ashes.plugins.sentinel.webmvc.ext;

import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * @author: ashes
 * @create: 2023-07-24 15:50
 * @description: initialize nacos datasource in SpringBoot
 */
@Component
public class NacosDataSourceInitializer {

    /**
     * according to {@code ModifyRulesCommandHandler}
     */
    private static final String FLOW_RULE_TYPE = "flow";
    private static final String DEGRADE_RULE_TYPE = "degrade";
    private static final String SYSTEM_RULE_TYPE = "system";
    private static final String AUTHORITY_RULE_TYPE = "authority";
    private final Map<String, NacosDataSourceExt> readableDataSourceMap = new HashMap<>(4);
    @Autowired
    private NacosDataSourceProperties properties;

    /**
     * initialize
     * 1. reload properties
     * 2. register datasource
     */
    public void init() {
        if (properties == null) {
            properties = new NacosDataSourceProperties();
        }

        initDSMap();

        FlowRuleManager.register2Property(readableDataSourceMap.get(FLOW_RULE_TYPE).getProperty());
        AuthorityRuleManager.register2Property(readableDataSourceMap.get(AUTHORITY_RULE_TYPE).getProperty());
        DegradeRuleManager.register2Property(readableDataSourceMap.get(DEGRADE_RULE_TYPE).getProperty());
        SystemRuleManager.register2Property(readableDataSourceMap.get(SYSTEM_RULE_TYPE).getProperty());
    }

    /**
     * datasource client for flow/authority/degrade/system rules
     */
    private void initDSMap() {
        readableDataSourceMap.put(FLOW_RULE_TYPE, new NacosDataSourceExt<>(
                properties.getProperties(),
                genGroupId(FLOW_RULE_TYPE),
                genDataId(FLOW_RULE_TYPE),
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
                })));
        readableDataSourceMap.put(DEGRADE_RULE_TYPE, new NacosDataSourceExt<>(
                properties.getProperties(),
                genGroupId(DEGRADE_RULE_TYPE),
                genDataId(DEGRADE_RULE_TYPE),
                source -> JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {
                })));
        readableDataSourceMap.put(SYSTEM_RULE_TYPE, new NacosDataSourceExt<>(
                properties.getProperties(),
                genGroupId(SYSTEM_RULE_TYPE),
                genDataId(SYSTEM_RULE_TYPE),
                source -> JSON.parseObject(source, new TypeReference<List<SystemRule>>() {
                })));
        readableDataSourceMap.put(AUTHORITY_RULE_TYPE, new NacosDataSourceExt<>(
                properties.getProperties(),
                genGroupId(AUTHORITY_RULE_TYPE),
                genDataId(AUTHORITY_RULE_TYPE),
                source -> JSON.parseObject(source, new TypeReference<List<AuthorityRule>>() {
                })));
    }

    /**
     * initialization process
     */
    @PostConstruct
    private void initialize() throws Exception {
        serialToSystem();
        init();
    }

    /**
     * serialize to system properties, dispensable in this case,
     */
    private void serialToSystem() {
        Properties prop = properties.getProperties();
        prop.keySet().forEach(k -> System.setProperty((String) k, prop.getProperty((String) k)));
    }

    /**
     * format: ${dataId}.${ruleType}.rules
     *
     * @param type rule type
     * @return dataId based on rule type
     */
    private String genDataId(String type) {
        return String.format("%s.%s.%s", properties.getDataId(), type, "rule");
    }

    /**
     * reserve for customization
     *
     * @param type rule type
     * @return groupId based on rule type
     */
    private String genGroupId(String type) {
        return properties.getGroupId();
    }

}
