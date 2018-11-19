package com.gsh.springcloud.serviceorder.utils;
/**
 * Copyright (c) 2011-2016, hubin (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;


import java.io.File;
import java.util.*;

/**
 * @author gsh
 * @Title: MysqlGenerator
 * @Package com.gsh.springcloud.servicemember.utils
 * @Description:
 * @date 2018/9/6 16:44
 */
public class MabatisPlusGenerator {

    /*S 数据库连接信息*/
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/springcloud?useUnicode=true&characterEncoding=utf8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123";
    /**
     * 单个表名
     */
    private static String oneTable = "order";

    /*E 数据库连接信息*/

    /**
     * 文件路径
     */
    private static String packageName = "service-order";
    /**
     * 作者
     */
    private static String authorName = "gsh";

    /**
     * 单个table前缀
     */
    private static String prefix = "";
    private static File file = new File(packageName);
    private static String path = file.getAbsolutePath();
    /**
     * 代码生成绝对路径
     */
//    private static String path = "/Users/gdd/Desktop/WorkSpaces/Github_Projects/springcloud/service-good";

    public static void main(String[] args) {
        String[] tableNames = new String[1];
        //如果不是单个表
        if (oneTable.equals("")) {
            List<String> listTableNames = DatabaseUtil.getTableNames();
            tableNames = listTableNames.toArray(new String[listTableNames.size()]);
        } else {
            tableNames[0] = oneTable;
        }
        System.out.println(path);
        // 自定义需要填充的字段
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("ASDD_SS", FieldFill.INSERT_UPDATE));
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator().setGlobalConfig(
                // 全局配置
                new GlobalConfig()
                        //输出目录
                        .setOutputDir(path + "/src/main/java")
                        // 是否覆盖文件
                        .setFileOverride(true)
                        // 开启 activeRecord 模式
                        .setActiveRecord(true)
                        // XML 二级缓存
                        .setEnableCache(false)
                        // XML ResultMap
                        .setBaseResultMap(true)
                        // XML columList
                        .setBaseColumnList(true)
                        //生成后打开文件夹
                        .setOpen(false)
                        .setAuthor(authorName)
                        // 自定义文件命名，注意 %s 会自动填充表实体属性！
                        .setMapperName("%sMapper")
                        .setXmlName("%sMapper")
                        .setServiceName("%sService")
                        .setServiceImplName("%sServiceImpl")
//                        .setControllerName("%sController")
        ).setDataSource(
                // 数据源配置
                new DataSourceConfig()
                        // 数据库类型
                        .setDbType(DbType.MYSQL)
                        .setTypeConvert(new MySqlTypeConvert() {
                            // 自定义数据库表字段类型转换【可选】
                            public DbColumnType processTypeConvert(String fieldType) {
                                System.out.println("转换类型：" + fieldType);
//                                 if ( fieldType.toLowerCase().contains( "tinyint" ) ) {
//                                    return DbColumnType.BOOLEAN;
//                                 }
                                return (DbColumnType) super.processTypeConvert(new GlobalConfig(), fieldType);
                            }
                        })
                        .setDriverName(DRIVER)
                        .setUsername(USERNAME)
                        .setPassword(PASSWORD)
                        .setUrl(URL)
        ).setStrategy(
                // 策略配置
                new StrategyConfig()
                        // .setCapitalMode(true)// 全局大写命名
                        //.setDbColumnUnderline(true)//全局下划线命名
                        // 此处可以修改为您的表前缀
                        .setTablePrefix(new String[]{prefix})
                        // 表名生成策略
                        .setNaming(NamingStrategy.underline_to_camel)
//                        .setInclude(new String[] { table }) // 需要生成的表
                        // 需要生成的表
                        .setInclude(tableNames)
                        //.setRestControllerStyle(true)
                        //.setExclude(new String[]{"test"}) // 排除生成的表
                        // 自定义实体父类
//                         .setSuperEntityClass("com.gsh.springcloud.entiy")
                        // 自定义实体，公共字段
                        //.setSuperEntityColumns(new String[]{"test_id"})
                        .setTableFillList(tableFillList)
                        // 自定义 mapper 父类
                        // .setSuperMapperClass("com.baomidou.demo.TestMapper")
                        // 自定义 service 父类
                        // .setSuperServiceClass("com.baomidou.demo.LocalService")
                        // 自定义 service 实现类父类
                        // .setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl")
                        // 自定义 controller 父类
//                        .setSuperControllerClass("com.tdx."+packageName+".controller.AbstractController")
                        // 【实体】是否生成字段常量（默认 false）
                        // public static final String ID = "test_id";
                        // .setEntityColumnConstant(true)
                        // 【实体】是否为构建者模型（默认 false）
                        // public User setName(String name) {this.name = name; return this;}
                        // .setEntityBuilderModel(true)
//                 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
                        .setEntityLombokModel(true)
                // Boolean类型字段是否移除is前缀处理
                // .setEntityBooleanColumnRemoveIsPrefix(true)
                // .setRestControllerStyle(true)
                // .setControllerMappingHyphenStyle(true)
        ).setPackageInfo(
                // 包配置
                new PackageConfig()
                        //.setModuleName("User")
                        // 自定义包路径
                        .setParent("com.gsh.springcloud.serviceorder")
                        // 这里是控制器包名，默认 web
                        .setController("controller")
                        .setEntity("entity")
                        .setMapper("dao")
                        .setService("service")
                        .setServiceImpl("serviceImpl")
                //.setXml("mapper")
        ).setCfg(
                // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
                new InjectionConfig() {
                    @Override
                    public void initMap() {
                        Map<String, Object> map = new HashMap<>();
                        map.put("", this.getConfig().getGlobalConfig().getAuthor() + "-gsh");
                        this.setMap(map);
                    }
                }.setFileOutConfigList(Collections.<FileOutConfig>singletonList(new FileOutConfig("/templates/mapper.xml.vm") {
                    // 自定义输出文件目录
                    @Override
                    public String outputFile(TableInfo tableInfo) {
                        return path + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
                    }
                }))
        ).setTemplate(
                // 关闭默认 xml 生成，调整生成 至 根目录
                new TemplateConfig().setXml(null)
                // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
                // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
                // .setController("...");
                // .setEntity("...");
                // .setMapper("...");
                // .setXml("...");
                // .setService("...");
                // .setServiceImpl("...");
        );
        // 执行生成
        mpg.execute();

        // 打印注入设置，这里演示模板里面怎么获取注入内容【可无】
        // System.err.println(mpg.getCfg().getMap().get("abc"));
    }

}