### 介绍

这是一个简单的示例，分为两个部分，一个是基于`python`的`web`服务，一个是`java`客户端，两者通过`http`通信。
可以根据需求进行任意的改造，加固。

### 服务端
需要部署在可以访问外网的服务器上，可以使用`docker`部署，也可以直接使用`python`运行。<br><br>
在`server`目录下，使用`python`编写，使用`flask`框架，可以使用`nginx`,或者`caddy`作为反向代理套tls，当然不介意也可以裸奔。

### 客户端
客户端可以使用任意语言实现，不限于java，只要能够实现`http`通信即可，本工程提供了`java`版本的客户端，可以直接使用。

##### 使用步骤

1. 下载本项目到本地。
2. 修改server目录下的`server.py`文件，修改`GPT_API_KEY`为自己的api-key，并上传到服务器。
3. 服务器端安装python3，pip3环境（自行解决安装问题），并且安装`flask`框架，命令如下：
```shell
pip3 install flask
```
4. 防火墙放行`12345`端口，命令如下：
```shell
firewall-cmd --zone=public --add-port=12345/tcp --permanent
firewall-cmd --reload
```
5. 进入server.python所在目录，执行`python3 server.py`，启动服务。
6. 当然也可以使用`docker`部署
