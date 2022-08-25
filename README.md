# Spring の Bean にまつわる実験場
お勉強用の実験プロジェクト

## 構成
```
(Project Root)
\_db ... テスト用DBのDDL
\_docker
    \_apache ... httpdを動かすcontainer用
    \_tomcat ... tomcatを動かすcontainer用
\_src
    \_main
        \_java
        \_resources
            \_application.properties ... 設定ファイル
            \_MyMapper.xml ... MyBatis用のmapperファイル
    \_test
```

## 起動方法
1. `docker-compose up`
1. `.\deploy.ps1`
1. `http://localhost:8080/bean-test/~`にアクセス。詳しいエンドポイントは各Controllerを参照。
4. containerを作ったばかりの時はmysqlにdatabaseもtableもないので以下を実行  
`docker-compose exec db bash -c "cat /scripts/*.sql | mysql"`

## Docker Compose の構成
- `db`：mysqlのDB。`/scripts`配下にDDLを置いてある。
- `apache`：httpdが動いてる。tomcatのプロキシの役割。AJPでtomcatと連携。
- `tomcat`：tomcatが動いている。`/usr/local/tomcat/work`にmavenのビルド成果物のディレクトリ(`./target`)をマウントしている。