课程名称  数据库原理							 指导教师  姜燕



院 （系） 计算机科学与技术学部        		     专业班级  数据科学21-1



小组编号 14                    			     设计日期  2023.6.1



课程设计题目 图书管理系统



小组分工情况

| 学号         | 学生姓名 | 具体分工                                           | 成绩 |
| ------------ | -------- | -------------------------------------------------- | ---- |
| 202103160006 | 邹雪燕   |                                                    |      |
| 202103160007 | 周明源   | 编写前后端代码，定义数据库并进行功能测试和需求测试 |      |
| 202103160008 | 管小风   |                                                    |      |

# 数据库设计

## 需求分析

### 问题描述

图书管理系统是为图书馆或者其他借阅机构设计的一种软件系统，主要用于读者进行图书的借阅、归还、查询等操作，并辅助管理员进行图书的分类、管理、统计等工作。随着社会信息化的发展和数字化技术的进步，图书管理系统也逐渐向着智能化、自动化、网络化等方向发展。

通过图书管理系统，读者可以方便快捷地查询和借阅图书，管理员可以实现对图书的自动管理和快速统计，从而提高图书馆或者其他借阅机构的服务水平和效率。

### 系统功能描述

进行新书入库、现有图书信息修改以及删除

能够实现对读者基本信息的查询和编辑管理

能够实现借阅信息的查询功能

能够存储一定数量的图书信息,并方便有效的进行相应的书籍数据操作和管理，这主要包括：

- 图书信息的录入、删除及修改。
- 图书信息的多关键字检索查询。
- 图书的出借、返还。

### 业务用例图

![](https://image.itbaima.net/images/156/image-2023060614714178.png)

### 安全性与完整性方面的要求

认证和授权：只有授权用户可以访问数据库，并根据其角色和权限级别限制其可执行的操作。

完整性约束：在设计数据库模式时需要定义适当的完整性约束以确保数据的正确性和一致性。例如，定义外键约束以确保引用关系的完整性。

防止SQL注入攻击：应该使用参数化查询或存储过程来防止恶意攻击者通过输入恶意代码来破坏数据库。

加密：在传输和保存敏感信息（如密码）时，必须使用加密算法保护。

备份和恢复：定期备份数据库，并测试恢复流程以确保成功恢复数据。

### 数据字典

- 数据流图

  ![](https://image.itbaima.net/images/156/image-20230606142847905.png)

- 数据项

  | 数据项名 | 数据项含义说明                 | 数据类型 | 长度 | 取值范围    | 取值含义           | 数据项之间的联系 |
  | -------- | ------------------------------ | -------- | ---- | ----------- | ------------------ | ---------------- |
  | id       | 管理员编号，唯一标识每个管理员 | INT      |      |             |                    |                  |
  | username | 管理员用户名                   | VARCHAR  | 255  |             |                    | id→username      |
  | nickname | 管理员姓名                     | VARCHAR  | 255  |             |                    | id→nickname      |
  | password | 管理员密码                     | VARCHAR  | 255  |             |                    | id→password      |
  | bid      | 书籍ID，唯一标识每本图书       | INT      |      |             |                    |                  |
  | title    | 书籍标题                       | VARCHAR  | 255  |             |                    | bid→title        |
  | desc     | 书籍简介                       | VARCHAR  | 255  |             |                    | bid→desc         |
  | price    | 书籍价格                       | decimal  | 10   |             |                    | bid→price        |
  | sid      | 学号，唯一标识每名学生         | INT      |      |             |                    |                  |
  | name     | 姓名                           | VARCHAR  | 255  |             |                    | sid→name         |
  | sex      | 性别                           | ENUM     |      | ['男','女'] |                    | sid→sex          |
  | grade    | 年级                           | INT      |      |             | 2019代表2019级学生 | sid→grade        |
  | password | 学生密码                       | VARCHAR  | 255  |             |                    | sid→password     |
  | id       | 借阅ID                         | INT      |      |             |                    |                  |
  | time     | 借阅时间                       | DATATIME |      |             |                    | id→time          |

- 数据结构

  | 数据结构名 | 含义说明                           | 组成:{数据项或数据结构}              |
  | ---------- | ---------------------------------- | ------------------------------------ |
  | admin      | 管理员，定义了一个管理员的有关信息 | { id, username, nickname, password } |
  | book       | 书籍，定义了一本书籍的有关信息     | { bid, title, desc, price }          |
  | borrow     | 借阅信息，定义了读者借阅图书的信息 | { id, sid, bid, time }               |
  | student    | 学生，定义了一名学生的有关信息     | { sid, name, sex, grade, password}   |

## 概念结构设计

### 实体集

![](https://image.itbaima.net/images/156/image-20230606142178418.png)

![](https://image.itbaima.net/images/156/image-20230606146138256.png)![](https://image.itbaima.net/images/156/image-20230606148148620.png)

### ER图

![](https://image.itbaima.net/images/156/image-20230606142071080.png)

## 逻辑结构设计

### ER图向关系模式转换

![](https://image.itbaima.net/images/156/image-20230606141346537.png)

### 设计子模式

无需定义视图。

### 系统功能模块图

![](https://image.itbaima.net/images/156/image-2023060614770898.png)

## 物理结构设计

### 索引

通过建立唯一索引（B-tree）来维护数据完整性和准确性：

- 在borrow表中为(sid, bid)建立唯一索引，因为借阅信息中一本书只能对应一名学生。

  ```sql
  CREATE UNIQUE INDEX idx_borrow_sid_bid ON borrow (sid, bid);
  ```

- 在borrow表中为 bid 建立唯一索引，因为默认每种书只有一本，借阅信息中一本书只能出现一次。

  ```sql
  CREATE UNIQUE INDEX idx_borrow_bid ON borrow (bid);
  ```

## 数据库实施

### 数据库的创建

```sql
USE master;

IF EXISTS (SELECT *
            FROM sysdatabases
            WHERE name = 'book_manage')
BEGIN
    DROP DATABASE book_manage;
END;

CREATE DATABASE book_manage;

IF NOT EXISTS (SELECT *
                FROM syslogins
                WHERE name = 'dba')
BEGIN
    CREATE LOGIN dba WITH PASSWORD = '123456', DEFAULT_DATABASE = book_manage;
END

GO

USE DIMS;

CREATE USER dba FOR LOGIN dba WITH DEFAULT_SCHEMA = dbo;

EXEC sp_addrolemember 'db_owner', 'dba';
```

### 数据表的创建

```sql
create table admin
(
    id       int auto_increment
        primary key,
    username varchar(255) null,
    nickname varchar(255) null,
    password varchar(255) null
);

create table book
(
    bid    int auto_increment
        primary key,
    title  varchar(255)   null,
    `desc` varchar(255)   null,
    price  decimal(10, 2) null
);

create table student
(
    sid   int auto_increment
        primary key,
    name  varchar(255)      not null,
    sex   enum ('男', '女') not null,
    grade int               not null
);

create table borrow
(
    id   int auto_increment
        primary key,
    sid  int      null,
    bid  int      null,
    time datetime null,
    constraint f_bid
        unique (bid),
    constraint unique_sid_bid
        unique (sid, bid),
    constraint f_bid
        foreign key (bid) references book (bid),
    constraint f_sid
        foreign key (sid) references student (sid)
);

create definer = root@localhost trigger del
    before delete
    on student
    for each row
    DELETE FROM borrow WHERE sid = old.sid;

create definer = root@localhost trigger t
    before delete
    on student
    for each row
    DELETE FROM borrow WHERE old.sid = borrow.sid;
```

## 数据库运行和维护

数据库应用系统经过试运行后即可投入正式运行。

在数据库系统运行过程中必须不断地对其进行评估、调整与修改。

# 应用系统设计

## 技术选型

网络架构：B/S 模式 (易开发、易维护、易扩展)

前端管理页面模板：基于 Bootstrap 前端框架的管理系统页面模板

WEB 容器：Tomcat10

包管理工具：Maven

后端使用到的框架：Servlet+Mybatis+Thymeleaf

开发环境及工具：IntelliJ IDEA、Navicat、Google Chrome

存储数据库：MySQL数据库

生产服务环境：Windows 10

## 环境准备

### 配置 Java 环境

1. 下载 JDK 安装包：
   - [Java SE 官网](https://www.oracle.com/technetwork/java/javase/overview/index.html)
   - [Java SE 官网下载页面](https://www.oracle.com/technetwork/java/javase/downloads/index.html)

2. 配置环境变量

   | 变量      | 值                                 |
   | --------- | ---------------------------------- |
   | JAVA_HOME | C:\Program Files\Java\jdk1.8.0_231 |
   | JRE_HOME  | C:\Program Files\Java\jre1.8.0_231 |
   | Path      | ;%JAVA_HOME%\bin                   |

### 安装 IDEA

[IDEA官网](https://www.jetbrains.com/zh-cn/idea/)

### 配置 Tomcat 环境

1. 从官网下载相应的 Tomcat Binary Distributions 安装包文件 `32-bit/64-bit Windows Service Installer`。

   - [Tomcat 官网](http://tomcat.apache.org/)
   - [Tomcat 10 官网下载页面](https://tomcat.apache.org/download-90.cgi)

2. 双击运行下载的安装文件，按照提示一步一步安装并配置。

3. 配置环境变量：

   | 变量          | 值                                                     |
   | ------------- | ------------------------------------------------------ |
   | TOMCAT_HOME   | C:\Program Files\Apache Software Foundation\Tomcat 9.0 |
   | CATALINA_HOME | C:\Program Files\Apache Software Foundation\Tomcat 9.0 |
   | Path          | ;%TOMCAT_HOME%\bin;%CATALINA_HOME%\lib                 |

### 在 IDEA 中配置 Tomcat Server

[IDEA配置Tomcat Server](https://www.cnblogs.com/merray/p/12698810.html)

## 创建一个新的Web项目

新建一个名为 `BookManagerWeb` 的 `Jakarta EE`项目，模板选择 Web 应用程序，应用程序选择 Tomcat 10.1.7，构建系统选择 Maven。

![](https://image.itbaima.net/images/156/image-20230606148896835.png)

## Maven导包,配置pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.book</groupId>
    <artifactId>BookManagerWeb</artifactId>
    <version>1.0-SNAPSHOT</version>
    <name>BookManagerWeb</name>
    <packaging>war</packaging>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.target>1.8</maven.compiler.target>
        <maven.compiler.source>1.8</maven.compiler.source>
        <junit.version>5.9.2</junit.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>jakarta.servlet</groupId>
            <artifactId>jakarta.servlet-api</artifactId>
            <version>5.0.0</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>org.thymeleaf</groupId>
            <artifactId>thymeleaf</artifactId>
            <version>3.1.1.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.26</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.32</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.13</version>
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-war-plugin</artifactId>
                <version>3.3.2</version>
            </plugin>
        </plugins>
    </build>
</project>
```

# 测试及验收

## 界面测试

界面测试：反复不间断地点击各窗体连接，测试其连接情况是否达到预期效果。

发现的问题：无

## 功能测试

功能测试：对系统各功能模块逐一测试，尽最大可能地发现潜在 BUG 错误。

发现的问题及解决办法：

- 书籍管理中如果书籍处于已被借阅状态，点击删除信息则会出现 500 服务器内部错误，这是由于添加了外键约束，所以删除失败。

  可以在前端代码中加入判断，如果处于借阅状态则不出现删除信息的超链接，如果未被借阅则出现。

  ![](https://image.itbaima.net/images/156/image-202306062066014.png)

  加入以下代码：`th:unless="${book.getValue()}"`

  ![](https://image.itbaima.net/images/156/image-20230606207851885.png)

## 性能测试

性能测试：将程序以局域网的形式连接数据库，查看数据连接是否满足多用户的要求。

因为系统功能比较简单，所以无需性能测试。

## 需求测试

需求测试：根据需求分析的内容，给使用者进行试用，测试软件是否和当初设计的一样能满足各用户需求。

发现的问题：无

# 成果截图

![](https://image.itbaima.net/images/156/image-20230606149060086.png)

![](https://image.itbaima.net/images/156/image-20230606204822551.png)

![](https://image.itbaima.net/images/156/image-2023060620714723.png)

![](https://image.itbaima.net/images/156/image-20230606208560275.png)

![](https://image.itbaima.net/images/156/image-20230606207807255.png)

![](https://image.itbaima.net/images/156/image-20230606207913664.png)

# 课程设计心得

## 遇到的主要问题和解决方法

1. HTML 表单隐藏域怎么实现？

   参考：[HTML 表单隐藏域](https://blog.csdn.net/qq_36260974/article/details/88370624)

2. ER 图、UML 图及其他图找不到合适的平台来画。

   参考：飞书云文档。

3. 前端鉴权不太会。

## 体会和心得

在进行数据库课程设计时，我们遇到了不少实际问题，其中最常见的问题是数据查询效率较低。这个问题通常是由于数据库设计不当、索引缺失等原因导致的。为此，我们需要对数据库的设计进行深入分析，找出其中的问题，并据此优化数据库的设计和索引，以提高数据查询效率。

另外，在进行高级语言应用开发和数据库交互时，我们需要了解各种编程语言中的数据库API，如Java中的JDBC、Python中的SQLAlchemy等。这些API可以方便地与数据库进行交互，并提供各种操作数据库的接口。同时，我们还需了解如何安全地进行数据交换，例如防止SQL注入等攻击。

在进行web开发时，我们需要了解基本的web开发知识和技术，例如HTML、CSS、JavaScript、PHP等，同时还需要一定的服务器端开发经验。在这个过程中，我们需要注意网络安全问题，例如防止XSS攻击和CSRF攻击等。

一个好的数据库课程设计应该包括以下几个方面：

1. 实际问题：设计过程中要尽可能考虑实际问题，例如一个小型超市销售管理系统、一个酒店管理系统等等。这样设计出来的系统会更加实用，能够应对实际问题。
2. 数据库设计：在设计数据库时需要考虑到数据表之间的关系，关键字、索引等等。需要设计出一个完整、规范、易于维护的数据库，能够满足实际需求。
3. 编程语言：与数据库交互时，需要使用编程语言来操作数据库，例如使用Java、Python等。需要了解基本语法，以及如何使用数据库驱动连接数据库并进行操作。
4. Web开发：如果设计的系统需要通过Web访问，就需要对Web开发有一定的了解，包括HTML、CSS、JavaScript等前端技术，以及ASP.NET、Spring MVC等后端技术。

在完成数据库课程设计过程中，我们也遇到了不少问题和困难。例如：

1. 数据库设计不规范：最开始我们的数据库设计不规范，表与表之间的关系不够清晰，导致后期对数据的处理和查询出现了一定的难度。
2. 代码重构：在开发过程中，发现代码有一定的冗余和重复。需要进行重构，使代码更加规范、易于维护。
3. 性能优化：随着数据量的增大，我们发现部分操作效率较低。需要对代码进行性能优化，使代码运行更加快速、稳定。

总的来说，完成数据库课程设计需要具备一定的理论知识和实践能力。在实际操作过程中，我们需要不断地思考，尝试不同的解决方案，发现问题并及时解决。这样才能设计出高效、稳定、实用的系统，为日后的工作打下坚实的基础。
