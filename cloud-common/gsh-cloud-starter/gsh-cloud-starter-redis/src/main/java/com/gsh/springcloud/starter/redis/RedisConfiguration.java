package com.gsh.springcloud.starter.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.ReadFrom;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * 自定义了 LettuceConnectionFactory bean的创建，
 * 该配置类需要在spring 的 RedisAutoConfiguration 自动配置创建前使用
 *
 * @author gsh
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(RedisAutoConfiguration.class)
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfiguration {


  /**
   * 构建RedisConnectionFactory
   * 支持单机和集群模式的配置
   *
   * @return LettuceConnectionFactory
   * @throws Exception
   */
  @Bean
  public RedisConnectionFactory createLettuceConnectionFactory(RedisProperties redisProperties) {
    log.info("创建LettuceConnectionFactory");
    try {
      String redisPropertiesStr = new ObjectMapper().writeValueAsString(redisProperties);
      log.info("redisProperties: {}", redisPropertiesStr);
      log.info("redisProperties.getCluster()==null: {}", redisProperties.getCluster() == null);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    if (redisProperties.getCluster() != null
            && !CollectionUtils.isEmpty(redisProperties.getCluster().getNodes())) {
      return createClusterConnectionFactory(redisProperties);
    }
    return createStandaloneConnectionFactory(redisProperties);
  }


  @Bean(name = "redisTemplate")
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
    ObjectMapper om = new ObjectMapper();
    // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
    om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会抛出异常
    om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
    jackson2JsonRedisSerializer.setObjectMapper(om);
    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
    // key采用String的序列化方式
    template.setKeySerializer(stringRedisSerializer);
    // hash的key也采用String的序列化方式
    template.setHashKeySerializer(stringRedisSerializer);
    // value序列化方式采用jackson
    template.setValueSerializer(jackson2JsonRedisSerializer);
    // hash的value序列化方式采用jackson
    template.setHashValueSerializer(jackson2JsonRedisSerializer);
    template.afterPropertiesSet();
    return template;
  }

//  @Bean
//  public RedissonClient redissonClient(RedisProperties redisProperties){
//    Config config = new Config();
//    SingleServerConfig serverConfig = config.useSingleServer()
//            .setAddress(redisProperties.getHost())
//            .setTimeout((int) redisProperties.getTimeout().toMillis())
//            .setConnectionPoolSize(redisProperties.getLettuce().getPool().getMaxActive())
//            .setConnectionMinimumIdleSize(redisProperties.getLettuce().getPool().getMinIdle());
//    if(!StringUtils.isEmpty(redisProperties.getPassword())) {
//      serverConfig.setPassword(redisProperties.getPassword());
//    }
//    return Redisson.create(config);
//  }


  private LettuceConnectionFactory createStandaloneConnectionFactory(RedisProperties redisProperties) {
    RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
    config.setHostName(redisProperties.getHost());
    config.setPort(redisProperties.getPort());
    config.setPassword(RedisPassword.of(redisProperties.getPassword()));
    config.setDatabase(redisProperties.getDatabase());
    return new LettuceConnectionFactory(config);
  }

  private LettuceConnectionFactory createClusterConnectionFactory(RedisProperties redisProperties) {

    List<String> clusterNodes = redisProperties.getCluster().getNodes();
    log.info("createClusterConnectionFactory， nodes: {}", clusterNodes.toArray());
    Set<RedisNode> nodes = new HashSet<>();
    clusterNodes.forEach(address -> nodes.add(new RedisNode(address.split(":")[0].trim(), Integer.valueOf(address.split(":")[1]))));

    // 设置redis-cluster配置
    RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
    clusterConfiguration.setClusterNodes(nodes);
    clusterConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
    if (redisProperties.getCluster().getMaxRedirects() != null) {
      clusterConfiguration.setMaxRedirects(redisProperties.getCluster().getMaxRedirects());
    }
    if (redisProperties.getPassword() != null) {
      clusterConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
    }

//    poolConfig.set
    // 配置用于开启自适应刷新和定时刷新。
    // 如自适应刷新不开启，Redis集群变更时将会导致连接异常
    ClusterTopologyRefreshOptions clusterTopologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
            // 开启周期刷新(默认60秒)
            .enablePeriodicRefresh(Duration.ofSeconds(60))
            // 开启自适应刷新
            .enableAdaptiveRefreshTrigger(
                    ClusterTopologyRefreshOptions.RefreshTrigger.ASK_REDIRECT,
                    ClusterTopologyRefreshOptions.RefreshTrigger.UNKNOWN_NODE)
            .build();
    ClusterClientOptions clusterClientOptions = ClusterClientOptions.builder()
            .topologyRefreshOptions(clusterTopologyRefreshOptions)
            //拓扑刷新
            .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
            .autoReconnect(true)
            .socketOptions(SocketOptions.builder().keepAlive(true).build())
            // 取消校验集群节点的成员关系
            .validateClusterNodeMembership(false)
            .build();

    GenericObjectPoolConfig<?> poolConfig = new GenericObjectPoolConfig<>();
    RedisProperties.Pool pool = redisProperties.getLettuce().getPool();
    if (pool != null) {
      poolConfig.setMaxTotal(pool.getMaxActive());
      poolConfig.setMaxIdle(pool.getMaxIdle());
      poolConfig.setMinIdle(pool.getMinIdle());
      if (pool.getTimeBetweenEvictionRuns() != null) {
        poolConfig.setTimeBetweenEvictionRunsMillis(pool.getTimeBetweenEvictionRuns().toMillis());
      }
      if (pool.getMaxWait() != null) {
        poolConfig.setMaxWaitMillis(pool.getMaxWait().toMillis());
      }
    } else {
      poolConfig.setMaxTotal(8);
      poolConfig.setMaxIdle(8);
      poolConfig.setMinIdle(1);
      poolConfig.setMaxWaitMillis(-1);
    }

    LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
            .poolConfig(poolConfig)
            .clientOptions(clusterClientOptions)
            .readFrom(ReadFrom.REPLICA_PREFERRED)
            .commandTimeout(redisProperties.getTimeout())
            .build();

    return new LettuceConnectionFactory(clusterConfiguration, clientConfig);
  }


//  @Bean(name = "stringRedisTemplate")
//  public StringRedisTemplate stringRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory){
//    StringRedisTemplate template = new StringRedisTemplate(lettuceConnectionFactory);
//    return template;
//  }


}

