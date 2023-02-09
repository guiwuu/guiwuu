# docker notes

```
Notes of Docker
	docker-machine
		创建有加速器的docker机器
			docker-machine create --engine-registry-mirror=https://foo.mirror.aliyuncs.com -d virtualbox default
		查看机器配置
			docker-machine env default
		启用机器配置
			eval "$(docker-machine env default)"
		列出机器列表
			docker-machine ls
		登录机器
			docker ssh default
	basic
		查看运行中容器
			docker ps
		查看本地docker配置
			docker info
	image
		查找远程镜像
			docker search ubuntu
		查看本地镜像列表
			docker images
		查看本地镜像
			docker inspect ${IMAGE_ID}
		删除本地镜像
			docker rmi ${IMAGE_ID}
			docker rmi ${NAME}:${TAG}
				也可能是untag
			docker rmi -f ${IMAGE_ID}
		创建镜像
			docker build .
			docker build -f Dockerfile .
			docker build -t java_server
			docker build -t java_server:latest
		打tag
			docker tag ${IMAGE_ID} reg.docker.com/guiwuu/java_server
		仓库
			add
				修改配置文件：/etc/sysconfig/docker 增加 --insecure-registry reg.docker.com
			login
				sudo docker login --username=guiwuu reg.docker.com
			pull
				sudo docker pull reg.docker.com/guiwuu/foo
			push
				sudo docker tag [ImageId] reg.docker.com/guiwuu/foo
                sudo docker push reg.docker.com/guiwuu/foo
	container
		创建
			一闪而过
				docker run java_server
			指定容器名
				docker run --name test java_server
					名称不能重复
			终端交互
				docker run -it java_server
					
			后台常驻
				docker run -id java_server
			文件系统
				docker run -v ~/middleware:/middleware maven /bin/bash
		启停
			docker start test
			docker stop test
			
		管理
			docker exec -it ${CONTAINER_NAME] ${COMMAND}
				docker exec -it test ps aux
			docker top test
			docker attach test
	监控
		docker stats
	volumn
		docker volume ls
	clean up
		> 1.14
			docker system df
			docker system prune
				docker system prune -a(!!!)
		else
			docker rmi $(docker images | grep "<none>" | awk '{print $3}')
			docker ps -a | grep Exit | cut -d ' ' -f 1 | xargs docker rm
			docker volume ls -qf dangling=true
			docker rm $(docker ps -f 'status=exited' -q)
			docker rmi $(docker images -q)
```