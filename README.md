# 用到的数据库表数据
* 见工程下的 atguigu_jdbc.sql 文件
# 各个 module 说明
## jdbc-1
* 最基本的 JDBC 使用
## jdbc-2
* 数据库事务
## jdbc-3
* 通用 Dao
    * 考虑了事务
    * 未使用数据库连接池
    * 未使用 commons-dbutils 工具
* 有道云笔记上有使用了 druid 数据库连接池和 commons-dbutils 的通用 Dao 模板
## jdbc-4
* 数据库连接池
## jdbc-5
* commons-dbutils 的使用