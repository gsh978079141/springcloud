使用EasyCode插件自动生成mybatis-plus代码步骤

## 安装

### Install from repositories

1. Settings >> Plugins >> Browse repositories...
2. Search plugin by keyword 'Easy Code' then install 'Easy Code' plugin
3. Restart to take effect.

## 添加数据源配置

Database >> DataSource  >> MySQL输入数据库连接信息

## 导入EasyCode模板配置(不想导入模板的话可以查看步骤五)

1. 利用别人导出的token导入（推荐） Settings >> Easy Code >> 导入模板，输入Token
2. 自己创建模板 新建Group: deepblue-edu，添加template，从目录templates中copy已有文件

## 生成代码

1. 选中需要生成代码的表，右击，选择 EasyCode-Generate Code
2. 选择需要生成模板的Group：deepblue-edu
3. 选择需要生成模板的Moudle
4. 选择需要生成的代码template， 目前支持（entity.java、converter.java、dto.java、mapper.java, mapper.xml）
5. 选择需要生成的包名和路径：需要选择到com.deepblue.edu.xxx，xxx是具体的业务模块的名称  
   生成的代码会默认把entity放在com.deepblue.edu.xxx.domain.entity中。  
   生成的代码会默认把converter放在com.deepblue.edu.xxx.domain.converter中。  
   生成的代码会默认把dto放在com.deepblue.edu.xxx.dto中。  
   生成的代码会默认把mapper的xml文件放在resources/mapper中。  
