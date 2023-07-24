package org.ashes.plugins.sentinel.webmvc.ext;

import com.alibaba.nacos.api.PropertyKeyConst;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author: ashes
 * @create: 2023-07-14 15:53
 * @description: configuration
 */
@Component
@ConfigurationProperties(prefix = "sentinel.ds.nacos")
public class NacosDataSourceProperties {
    private static final String GI_KEY = "GROUP_ID_KEY";
    private static final String DI_KEY = "DATA_ID_KEY";
    private String url;
    private String username;
    private String password;
    private String groupId;
    private String dataId;

    public NacosDataSourceProperties() {
        initFromSystem();
    }

    /**
     * 取预制的配置的属性，取出来构造对象
     */
    private void initFromSystem() {
        setUrl(System.getProperty(PropertyKeyConst.SERVER_ADDR));
        setUsername(System.getProperty(PropertyKeyConst.USERNAME));
        setPassword(System.getProperty(PropertyKeyConst.PASSWORD));
        setGroupId(System.getProperty(GI_KEY));
        setDataId(System.getProperty(DI_KEY));
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Properties getProperties() {
        Properties properties = new Properties();
        if (url == null) {
            initFromSystem();
        }
        properties.put(PropertyKeyConst.SERVER_ADDR, url);
        properties.put(PropertyKeyConst.USERNAME, username);
        properties.put(PropertyKeyConst.PASSWORD, password);
        properties.put(GI_KEY, groupId);
        properties.put(DI_KEY, dataId);

        return properties;
    }
}
