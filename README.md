# nbs-client-test

## compile to jar command
First make sure maven is properly installed
command :
mvn clean package appassembler:assemble -Dmaven.test.skip=true -X

## IPFS SERVER FAST 
ipfs daemon --routing=dhtclient --enable-pubsub-experiment
ipfs pubsub peers bmJzaW8ubmV0 
# Version History
## Version 2.0.1
  - 聊天功能
  - 联系人名片
  - 增加节点信息显示
## Version 2.0.2
  - 增加文件下载功能