/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ashes.plugins.sentinel.webmvc.idea;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.annotation.aspectj.AbstractSentinelAspectSupport;
import com.alibaba.csp.sentinel.command.CommandRequest;
import com.alibaba.csp.sentinel.command.CommandResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Aspect for methods with {@link SentinelResource} annotation.
 *
 * @author Eric Zhao
 */
//@Component
//@Aspect
public class ModifyRulesAspect extends AbstractSentinelAspectSupport {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Autowired
//    private NacosDataSourceInjector injector;


    @Pointcut("execution(com.alibaba.csp.sentinel.command.CommandResponse com.alibaba.csp.sentinel.command.handler.*.handle(com.alibaba.csp.sentinel.command.CommandRequest))")
    public void pointcut() {
    }

    @AfterReturning(value = "pointcut()", returning = "result")
    public void afterReturning(JoinPoint jp, CommandResponse result) throws Throwable {

        logger.info("entering jp, {}", jp.toString());

        if (!result.isSuccess()) {
            return;
        }

        logger.info("detect update rules from sentinel-dashboard, prepare to push to Datasource");

        CommandRequest arg = (CommandRequest) jp.getArgs()[0];
        String type = arg.getParam("type");
//        injector.update(type);

    }
}
