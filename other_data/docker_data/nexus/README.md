# nexus 配置及部署
## 一：基础配置：
### 1.新建阿里云proxy-repository
    name = aliyun-proxy
    url = http://maven.aliyun.com/nexus/content/groups/public
    新建完成后加入[maven public]
### 2.新建本地hosted-repository
    2.1:gsh-releases、gsh-snapshots->allow deploy -> 加入maven-public
### 3.修改maven setting.xml
    配置中：192.168.0.114:8081为nexus访问地址
```xml
<settings>
    <mirrors>
        <mirror>
            <!--Send all requests to the public group -->
            <id>gsh-nexus</id>
            <mirrorOf>*</mirrorOf>
            <!--      <url>http://nexus.gshyun.com/repository/maven-public/</url>-->
            <url>http://192.168.0.114:8081/repository/maven-public/</url>
        </mirror>
    </mirrors>

    <activeProfiles>
        <activeProfile>gsh-nexus</activeProfile>
    </activeProfiles>
    <profiles>
        <profile>
            <id>gsh-nexus</id>
            <repositories>
                <repository>
                    <id>central</id>
                    <url>http://192.168.0.114:8081/repository/maven-public/</url>
                    <releases>
                        <enabled>true</enabled>
                    </releases>
                    <snapshots>
                        <enabled>true</enabled>
                    </snapshots>
                </repository>
            </repositories>
            <!--      下载插件-->
            <pluginRepositories>
                <pluginRepository>
                    <id>central</id>
                    <url>http://192.168.0.114:8081/repository/maven-public/</url>
                    <!--          <url>http://central</url>-->
                    <releases>
                        <enabled>true</enabled>
                    </releases>
                    <snapshots>
                        <enabled>true</enabled>
                    </snapshots>
                </pluginRepository>
            </pluginRepositories>
        </profile>

    </profiles>

    <pluginGroups>
        <pluginGroup>org.sonatype.plugins</pluginGroup>
        <pluginGroup>com.spotify</pluginGroup>
    </pluginGroups>

    <!--  发布依赖配置-->
    <servers>
        <server>
            <id>gsh-nexus</id>
            <username>admin</username>
            <password>Wddgsh520.</password>
        </server>
        <server>
            <id>gsh-releases</id>
            <username>admin</username>
            <password>Wddgsh520.</password>
        </server>
        <server>
            <id>gsh-snapshots</id>
            <username>admin</username>
            <password>Wddgsh520.</password>
        </server>
    </servers>
</settings>
```

### 4.主/父-项目pom.xml配置
```xml
 <!-- nexus  私服配置-->
    <distributionManagement>
        <repository>
            <id>gsh-releases</id>
            <name>gsh-releases</name>
            <!--      <url>http://nexus.gshyun.com/repository/gsh-releases/</url>-->
            <url>http://192.168.0.114:8081/repository/gsh-releases/</url>
        </repository>
        <snapshotRepository>
            <id>gsh-snapshots</id>
            <name>gsh-snapshots</name>
            <url>http://192.168.0.114:8081/repository/gsh-snapshots/</url>
        </snapshotRepository>
    </distributionManagement>
```