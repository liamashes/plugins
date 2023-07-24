# 流控
## 规则：GET:/hello QPS:2
seq 5 | xargs -I F curl localhost:10000/hello

# 熔断
## 规则：GET:/hello 最大RT：88 比例阈值：0.3 最小请求数：5 统计时长：1000 ms
seq 10 | xargs -I F curl localhost:10000/hello

# 热点
## 参考 sentinel-demo-parameter-flow-control
## 规则：
## 资源名：com.alibaba.csp.sentinel.demo.spring.webmvc.controller.WebMvcTestController:apiFoo(java.lang.Long)
## 参数索引：0 单机阈值：2 统计窗口时长：
## 高级： 参数类型：long 参数值：2 限流阈值：3
seq 10 | xargs -I F curl localhost:10000/foo/1
seq 10 | xargs -I F curl localhost:10000/foo/1

# 系统
## 规则，参考页面配置
### load需要参考demo程序
seq 10 | xargs -I F curl localhost:10000/hello

# 授权
## 规则：
## 资源名：GET:/hello
## 流控应用：jack 授权类型：白名单
curl localhost:10000/hello

curl localhost:10000/hello -H "origin:jack"
