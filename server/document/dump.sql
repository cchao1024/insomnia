-- MySQL dump 10.13  Distrib 8.0.15, for osx10.14 (x86_64)
--
-- Host: 127.0.0.1    Database: insomnia
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- 创建数据库
DROP DATABASES  `insomnia`；
create database `insomnia` default character set utf8mb4;

use insomnia;


--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `comment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `post_id` bigint(20) unsigned NOT NULL,
  `post_user_id` bigint(20) unsigned NOT NULL,
  `comment_user_id` bigint(20) unsigned NOT NULL,
  `content` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `images` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '帖子图片列表',
  `like_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '点赞数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `review_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '评论数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6600022 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (6000014,10,28,30,'可口可乐看',NULL,0,'2019-05-27 03:29:48','2019-05-29 09:41:56',0),(6000015,5000015,5,4,'图片不错哦',NULL,16,'2019-05-29 09:16:00','2019-06-21 08:55:25',3),(6000016,5000015,5,3,'看到图，我就想歪了，我有罪',NULL,1,'2019-05-29 09:26:28','2019-06-05 06:42:37',1),(6600019,5000015,5,4,'擦擦擦得得','',0,'2019-05-30 02:37:06','2019-05-30 02:37:06',0),(6600020,5500016,3,5,'最后就是我了，我叫 诺敏楚仑乌罕札雅孟赫额尔德尼恩赫特古勒德日，\n我敬你一杯，开始吧。','',0,'2019-06-05 06:17:44','2019-06-05 06:20:03',1),(6600021,5500016,3,29,'初次见面，也没带什么见面礼，就送你们个汉族名字吧。你叫张三……','',0,'2019-06-05 06:22:24','2019-06-05 06:23:14',0);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fall_image`
--

DROP TABLE IF EXISTS `fall_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fall_image` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `src` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `width` int(11) DEFAULT NULL COMMENT '图片宽',
  `height` int(11) DEFAULT NULL COMMENT '图片高',
  `like_count` int(11) unsigned DEFAULT NULL COMMENT '点赞数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000044 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fall_image`
--

LOCK TABLES `fall_image` WRITE;
/*!40000 ALTER TABLE `fall_image` DISABLE KEYS */;
INSERT INTO `fall_image` VALUES (1000003,'image/2019-05/35.gif',220,220,0,'2019-05-07 03:44:40','2019-05-23 01:05:59'),(1000004,'image/2019-05/34.gif',800,450,0,'2019-05-07 03:44:40','2019-05-23 01:05:59'),(1000005,'image/2019-05/22.gif',500,500,0,'2019-05-07 03:44:40','2019-05-23 01:05:59'),(1000006,'image/2019-05/23.gif',500,500,0,'2019-05-07 03:44:40','2019-05-23 01:05:59'),(1000007,'image/2019-05/37.gif',500,500,0,'2019-05-07 03:44:40','2019-05-23 01:05:59'),(1000008,'image/2019-05/27.gif',1200,301,0,'2019-05-07 03:44:41','2019-05-23 01:05:59'),(1000009,'image/2019-05/26.gif',500,500,0,'2019-05-07 03:44:41','2019-05-23 01:05:59'),(1000010,'image/2019-05/32.gif',400,400,0,'2019-05-07 03:44:41','2019-05-23 01:05:59'),(1000011,'image/2019-05/24.gif',670,670,0,'2019-05-07 03:44:41','2019-05-23 01:05:59'),(1000012,'image/2019-05/30.gif',560,531,0,'2019-05-07 03:44:41','2019-05-23 01:05:59'),(1000013,'image/2019-05/31.gif',570,296,0,'2019-05-07 03:44:42','2019-05-23 01:05:59'),(1000014,'image/2019-05/25.gif',775,500,0,'2019-05-07 03:44:42','2019-05-23 01:05:59'),(1000015,'image/2019-05/56.gif',400,400,0,'2019-05-07 03:44:42','2019-05-23 01:05:59'),(1000016,'image/2019-05/43.gif',440,440,0,'2019-05-07 03:44:42','2019-05-23 01:05:59'),(1000017,'image/2019-05/7.gif',450,450,0,'2019-05-07 03:44:42','2019-05-23 01:05:59'),(1000018,'image/2019-05/41.gif',400,400,0,'2019-05-07 03:44:43','2019-05-23 01:05:59'),(1000019,'image/2019-05/40.gif',400,225,0,'2019-05-07 03:44:43','2019-05-23 01:05:59'),(1000020,'image/2019-05/6.gif',700,495,0,'2019-05-07 03:44:43','2019-05-23 01:05:59'),(1000021,'image/2019-05/50.gif',620,349,0,'2019-05-07 03:44:43','2019-05-23 01:05:59'),(1000022,'image/2019-05/44.gif',350,622,0,'2019-05-07 03:44:43','2019-05-23 01:05:59'),(1000023,'image/2019-05/45.gif',224,168,0,'2019-05-07 03:44:44','2019-05-23 01:05:59'),(1000024,'image/2019-05/51.gif',202,162,0,'2019-05-07 03:44:44','2019-05-23 01:05:59'),(1000025,'image/2019-05/47.gif',800,450,0,'2019-05-07 03:44:44','2019-05-23 01:05:59'),(1000026,'image/2019-05/53.gif',700,701,0,'2019-05-07 03:44:44','2019-05-23 01:05:59'),(1000027,'image/2019-05/52.gif',439,439,0,'2019-05-07 03:44:44','2019-05-23 01:05:59'),(1000028,'image/2019-05/46.gif',128,160,0,'2019-05-07 03:44:44','2019-05-23 01:05:59'),(1000029,'image/2019-05/48.gif',800,450,0,'2019-05-07 03:44:45','2019-05-23 01:05:59'),(1000030,'image/2019-05/49.gif',400,399,0,'2019-05-07 03:44:45','2019-05-23 01:05:59'),(1000031,'image/2019-05/59.gif',200,280,0,'2019-05-07 03:44:45','2019-05-23 01:05:59'),(1000032,'image/2019-05/58.gif',500,500,0,'2019-05-07 03:44:46','2019-05-23 01:05:59'),(1000033,'image/2019-05/8.gif',660,495,0,'2019-05-07 03:44:46','2019-05-23 01:05:59'),(1000034,'image/2019-05/9.gif',100,100,0,'2019-05-07 03:44:46','2019-05-23 01:05:59'),(1000035,'image/2019-05/14.gif',350,250,0,'2019-05-07 03:44:46','2019-05-23 01:05:59'),(1000036,'image/2019-05/28.gif',500,500,0,'2019-05-07 03:44:46','2019-05-23 01:05:59'),(1000037,'image/2019-05/29.gif',200,112,0,'2019-05-07 03:44:46','2019-05-23 01:05:59'),(1000038,'image/2019-05/15.gif',620,349,0,'2019-05-07 03:44:47','2019-05-23 01:05:59'),(1000039,'image/2019-05/17.gif',580,435,0,'2019-05-07 03:44:47','2019-05-23 01:05:59'),(1000040,'image/2019-05/16.gif',700,701,0,'2019-05-07 03:44:47','2019-05-23 01:05:59'),(1000041,'image/2019-05/13.gif',500,500,0,'2019-05-07 03:44:47','2019-05-23 01:05:59'),(1000042,'image/2019-05/39.gif',800,450,0,'2019-05-07 03:44:48','2019-05-23 01:05:59'),(1000043,'image/2019-05/38.gif',300,300,0,'2019-05-07 03:44:48','2019-05-23 01:05:59');
/*!40000 ALTER TABLE `fall_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fall_music`
--

DROP TABLE IF EXISTS `fall_music`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fall_music` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `singer` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `src` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `cover_img` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '专辑图',
  `name` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '帖子图片列表',
  `like_count` int(11) DEFAULT NULL COMMENT '点赞数',
  `play_count` int(11) DEFAULT NULL COMMENT '播放数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2000057 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fall_music`
--

LOCK TABLES `fall_music` WRITE;
/*!40000 ALTER TABLE `fall_music` DISABLE KEYS */;
INSERT INTO `fall_music` VALUES (2000003,'Dan Gibson,John Herberman','music/2019-05/1838333.mp3','/album/2019-05/27801000.jpg','Along Peaceful Shores',0,0,'2019-05-07 03:13:11','2019-05-23 02:06:22'),(2000004,'植地雅哉','music/2019-05/9434944.mp3','/album/2019-05/5895232.jpg','流れ星',0,0,'2019-05-07 03:15:24','2019-05-23 02:06:22'),(2000005,'群星','music/2019-05/3580346.mp3','/album/2019-05/9813317.jpg','献给爱丽丝',0,0,'2019-05-07 03:19:52','2019-05-23 02:06:22'),(2000006,'Donawhale','music/2019-05/6694893.mp3','/album/2019-05/333256.jpg','비오는 밤',0,0,'2019-05-07 03:19:57','2019-05-23 02:06:22'),(2000007,'zmi','music/2019-05/3932124.mp3','/album/2019-05/4322943.jpg','Fade',0,0,'2019-05-07 03:20:08','2019-05-23 02:06:22'),(2000008,'Dan Gibson','music/2019-05/4551610.mp3','/album/2019-05/7388760.jpg','Morning Light',0,0,'2019-05-07 03:20:20','2019-05-23 02:06:22'),(2000009,'David Arkenstone','music/2019-05/7538989.mp3','/album/2019-05/2186761.jpg','Gentle Rain',0,0,'2019-05-07 03:20:30','2019-05-23 02:06:22'),(2000010,'ForeverLive','music/2019-05/7486788.mp3','/album/2019-05/6083103.jpg','Волны',0,0,'2019-05-07 03:20:38','2019-05-23 02:06:22'),(2000011,'Dan Gibson,John Herberman','music/2019-05/4215229.mp3','/album/2019-05/756745.jpg','Wishful Thinking',0,0,'2019-05-07 03:20:50','2019-05-23 02:06:22'),(2000012,'植地雅哉','music/2019-05/952663.mp3','/album/2019-05/8732868.jpg','静寂',0,0,'2019-05-07 03:21:06','2019-05-23 02:06:22'),(2000013,'Дальше От Света','music/2019-05/5865724.mp3','/album/2019-05/43750.jpg','Liveride',0,1,'2019-05-07 03:21:06','2019-05-23 02:06:22'),(2000014,'Gentle flow ジェントル·フロー','music/2019-05/3865542.mp3','/album/2019-05/175447.jpg','神山純一',0,0,'2019-05-07 03:21:11','2019-05-23 02:06:22'),(2000015,'いろのみ','music/2019-05/8111165.mp3','/album/2019-05/2927781.jpg','子守呗',0,1,'2019-05-07 03:21:28','2019-05-23 02:06:22'),(2000016,'re：plus','music/2019-05/9073257.mp3','/album/2019-05/4462197.jpg','Moonscape',0,0,'2019-05-07 03:21:30','2019-05-23 02:06:22'),(2000017,'Richard Evans','music/2019-05/4614140.mp3','/album/2019-05/5672117.jpg','Watching the Shorebirds',0,1,'2019-05-07 03:21:36','2019-05-23 02:06:22'),(2000018,'植地雅哉','music/2019-05/970738.mp3','/album/2019-05/7554958.jpg','海上の星たち',0,2,'2019-05-07 03:21:51','2019-05-23 02:06:22'),(2000019,'いろのみ','music/2019-05/61776.mp3','/album/2019-05/9009384.jpg','夕凪',0,1,'2019-05-07 03:21:55','2019-05-23 02:06:22'),(2000020,'Federico Durand','music/2019-05/4708456.mp3','/album/2019-05/361571.jpg','La casa de los abuelos',0,2,'2019-05-07 03:22:01','2019-05-23 02:06:22'),(2000021,'ChenDjoy','music/2019-05/52584.mp3','/album/2019-05/1617715.jpg','「3D雷雨助眠」Good Night Lullaby',0,1,'2019-05-07 03:22:03','2019-05-23 02:06:22'),(2000022,'群星','music/2019-05/3938659.mp3','/album/2019-05/3026213.jpg','小步舞曲',0,0,'2019-05-07 03:22:05','2019-05-23 02:06:22'),(2000023,'FJORDNE','music/2019-05/201346.mp3','/album/2019-05/427475.jpg','after you',0,1,'2019-05-07 03:22:08','2019-05-23 02:06:22'),(2000024,'hideyuki hashimoto','music/2019-05/40316.mp3','/common/default/album.jpg','kaze',0,0,'2019-05-07 03:22:13','2019-05-23 02:06:22'),(2000025,'菅野よう子','music/2019-05/1335198.mp3','/album/2019-05/6929495.jpg','northern light',0,1,'2019-05-07 03:22:17','2019-05-23 02:06:22'),(2000026,'hideyuki hashimoto','music/2019-05/6043334.mp3','/common/default/album.jpg','manabiya',0,0,'2019-05-07 03:22:19','2019-05-23 02:06:22'),(2000027,'hideyuki hashimoto','music/2019-05/4909590.mp3','/common/default/album.jpg','hajimari',0,1,'2019-05-07 03:22:21','2019-05-23 02:06:22'),(2000028,'Lights ','music/2019-05/9445119.mp3','/album/2019-05/965521.jpg','Northern Lights',0,0,'2019-05-07 03:22:23','2019-05-23 02:06:22'),(2000029,'Federico Durand','music/2019-05/3851980.mp3','/album/2019-05/2096165.jpg','El pequeño huésped sigue dormido',0,0,'2019-05-07 03:22:36','2019-05-23 02:06:22'),(2000030,'王鹏飞','music/2019-05/3834414.mp3','/album/2019-05/3244247.jpg','海边漫步（纯音乐） MIX',0,0,'2019-05-07 03:22:37','2019-05-23 02:06:22'),(2000031,'音乐治疗','music/2019-05/3749577.mp3','/album/2019-05/4424556.jpg','Rising of the Dream',0,0,'2019-05-07 03:22:39','2019-05-23 02:06:22'),(2000032,'hideyuki hashimoto','music/2019-05/154699.mp3','/common/default/album.jpg','taiwa',0,0,'2019-05-07 03:22:41','2019-05-23 02:06:22'),(2000033,'音乐治疗','music/2019-05/2616459.mp3','/album/2019-05/708356.jpg','Tender Passion',0,0,'2019-05-07 03:22:44','2019-05-23 02:06:22'),(2000034,'hideyuki hashimoto','music/2019-05/7592349.mp3','/common/default/album.jpg','ki',0,0,'2019-05-07 03:22:45','2019-05-23 02:06:22'),(2000035,'Dan Gibson,John Herberman','music/2019-05/4051619.mp3','/album/2019-05/9480109.jpg','Hymn to the Old Growth',0,0,'2019-05-07 03:22:49','2019-05-23 02:06:22'),(2000036,'hideyuki hashimoto','music/2019-05/8505162.mp3','/common/default/album.jpg','yura yura',0,0,'2019-05-07 03:22:50','2019-05-23 02:06:22'),(2000037,'hideyuki hashimoto','music/2019-05/2937190.mp3','/common/default/album.jpg','kara kara',0,0,'2019-05-07 03:22:52','2019-05-23 02:06:22'),(2000038,'Laura Sullivan','music/2019-05/7488712.mp3','/album/2019-05/3042511.jpg','Morning In The Meadow',0,0,'2019-05-07 03:22:57','2019-05-23 02:06:22'),(2000039,'Dan Gibson,John Herberman','music/2019-05/5464335.mp3','/album/2019-05/4404107.jpg','Waterline',0,0,'2019-05-07 03:23:02','2019-05-23 02:06:22'),(2000040,'Dan Gibson','music/2019-05/5721823.mp3','/album/2019-05/5556649.jpg','Across the Circular Horizon',0,0,'2019-05-07 03:23:10','2019-05-23 02:06:22'),(2000041,'Zak Gott','music/2019-05/3789874.mp3','/album/2019-05/7015196.jpg','Ocean (Pacific)',0,0,'2019-05-07 03:23:12','2019-05-23 02:06:22'),(2000042,'hideyuki hashimoto','music/2019-05/1894809.mp3','/common/default/album.jpg','tsuki',0,0,'2019-05-07 03:23:14','2019-05-23 02:06:22'),(2000043,'Della','music/2019-05/2938702.mp3','/album/2019-05/9729698.jpg','飞舞之叶',0,0,'2019-05-07 03:23:17','2019-05-23 02:06:22'),(2000044,'いろのみ','music/2019-05/875774.mp3','/album/2019-05/1125383.jpg','雫',0,0,'2019-05-07 03:23:23','2019-05-23 02:06:22'),(2000045,'Frédéric François Chopin','music/2019-05/1441195.mp3','/album/2019-05/2279388.jpg','夜曲',0,0,'2019-05-07 03:23:26','2019-05-23 02:06:22'),(2000046,'ST.FRANK','music/2019-05/9893970.mp3','/album/2019-05/3578152.jpg','Star Gaze',0,0,'2019-05-07 03:23:29','2019-05-23 02:06:22'),(2000047,'Musette','music/2019-05/1040759.mp3','/album/2019-05/4854908.jpg','24 Maj',0,0,'2019-05-07 03:23:32','2019-05-23 02:06:22'),(2000048,'Andrew Fitzgerald','music/2019-05/2074849.mp3','/album/2019-05/6182535.jpg','In the Distance',0,0,'2019-05-07 03:23:38','2019-05-23 02:06:22'),(2000049,'Roberto Cacciapaglia','music/2019-05/894645.mp3','/album/2019-05/784121.jpg','Michael',0,0,'2019-05-07 03:23:41','2019-05-23 02:06:22'),(2000050,'いろのみ','music/2019-05/748623.mp3','/album/2019-05/9115351.jpg','木陰',0,1,'2019-05-07 03:23:46','2019-05-23 02:06:22'),(2000051,'Ludy','music/2019-05/7663167.mp3','/album/2019-05/630107.jpg','피아노바다',0,0,'2019-05-07 03:23:49','2019-05-23 02:06:22'),(2000052,'Richard Evans','music/2019-05/3744925.mp3','/album/2019-05/2305822.jpg','Sea Breeze',0,0,'2019-05-07 03:23:54','2019-05-23 02:06:22'),(2000053,'植地雅哉','music/2019-05/243968.mp3','/album/2019-05/3689941.jpg','The Tide',0,0,'2019-05-07 03:23:59','2019-05-23 02:06:22'),(2000054,'hideyuki hashimoto','music/2019-05/7364100.mp3','/common/default/album.jpg','uta',0,0,'2019-05-07 03:24:00','2019-05-23 02:06:22'),(2000055,'Federico Durand','music/2019-05/4124565.mp3','/album/2019-05/6155605.jpg','Los niños escriben poemas en tiras de papel rojo',0,0,'2019-05-07 03:24:02','2019-05-23 02:06:22'),(2000056,'Dan Gibson,John Herberman','music/2019-05/8978251.mp3','/album/2019-05/7281836.jpg','Moody Afternoon',0,0,'2019-05-07 03:24:13','2019-05-23 02:06:22');
/*!40000 ALTER TABLE `fall_music` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feed_back`
--

DROP TABLE IF EXISTS `feed_back`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `feed_back` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `content` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '反馈内容',
  `email` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系邮箱',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feed_back`
--

LOCK TABLES `feed_back` WRITE;
/*!40000 ALTER TABLE `feed_back` DISABLE KEYS */;
INSERT INTO `feed_back` VALUES (1,29,'hffhhchc','69598@qq.com','2019-05-09 06:34:43','2019-05-09 06:34:43');
/*!40000 ALTER TABLE `feed_back` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL,
  `content` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `images` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '帖子图片列表',
  `tags` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '帖子的Tag列表',
  `like_count` int(11) unsigned DEFAULT '0' COMMENT '点赞数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `review_count` int(11) unsigned DEFAULT '0' COMMENT '评论数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5500017 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (5000015,5,'上午打LOL，正是激烈的时候，有一队友喊语音：“兄弟们对不起，我要回去上课了，再见。”\n然后我们各种喷！“上个毛课啊，逃课没事的啦，打完再走嘛！”\n\n然后那队友来了句：“我特么知道逃课没事，但是我特么的是老师啊！！”','/upload/image/2019_05_29/23818.jpg',NULL,22,'2019-05-29 09:13:58','2019-06-21 08:55:01',5),(5500016,3,'“哥们儿，我们内蒙喝酒有个规矩。我先介绍一下今天桌上的几个朋友，然后咱们先喝一圈。喝完之后你能说出来他们的名字，就是你认我们这些朋友，我们自己喝一杯。要是你说不出来名字，就是情谊还没到，你自己喝一杯。\n准备好没? 先从你旁边的噶拉仓巴拉丹扎木苏日丹开始，再往下是乌勒吉德勒格列日图愣巴猜…”','',NULL,7,'2019-06-05 06:15:21','2019-06-21 08:54:19',2);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reply`
--

DROP TABLE IF EXISTS `reply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `reply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `post_id` bigint(20) NOT NULL,
  `comment_id` bigint(20) NOT NULL,
  `to_user_id` bigint(20) NOT NULL,
  `reply_user_id` bigint(20) NOT NULL,
  `content` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `images` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '帖子图片列表',
  `like_count` int(11) DEFAULT '0' COMMENT '点赞数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `comment_user_id` bigint(20) NOT NULL,
  `to_reply_id` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7600011 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reply`
--

LOCK TABLES `reply` WRITE;
/*!40000 ALTER TABLE `reply` DISABLE KEYS */;
INSERT INTO `reply` VALUES (7000001,8,2,4,4,'回复 9999 的 content','imagesdf',0,'2019-05-07 08:38:43','2019-05-29 09:42:24',0,0),(7000002,8,2,4,4,'回复 9999 的 content','imagesdf',0,'2019-05-07 08:38:43','2019-05-29 09:42:24',0,0),(7000003,12,0,29,29,'dhhdjffuf',NULL,0,'2019-05-10 06:32:34','2019-05-29 09:42:24',0,0),(7000004,12,0,29,29,'oooooloo',NULL,0,'2019-05-10 07:38:09','2019-05-29 09:42:24',0,0),(7000005,12,8,29,29,'oooooloo',NULL,0,'2019-05-10 07:40:52','2019-05-29 09:42:24',0,0),(7000006,5000015,6000015,4,3,'我替我表弟问下，你这些图片是哪里下载的？',NULL,2,'2019-05-29 09:18:57','2019-06-05 06:44:05',0,0),(7000007,5000015,6000016,3,4,'你表弟我认识，他说他没问过',NULL,23,'2019-05-29 09:27:49','2019-06-05 06:44:07',0,0),(7600008,5000015,6000015,3,4,'。。。这图。。。','',4,'2019-05-30 02:08:05','2019-06-05 06:44:06',4,7000006),(7600009,5000015,6000015,4,5,'表弟： 。。。','',1,'2019-05-30 06:48:37','2019-06-21 08:55:21',4,7600008),(7600010,5500016,6600020,5,4,'我好像近视了，你的用户名倒过来怎么读来着？','',0,'2019-06-05 06:20:03','2019-06-05 06:21:34',5,0);
/*!40000 ALTER TABLE `reply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `nick_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `avatar` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gender` tinyint(3) DEFAULT '2' COMMENT '0女 1男 2未知',
  `age` int(11) unsigned DEFAULT NULL,
  `get_like` int(11) unsigned NOT NULL DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `visitor` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'123456@yahoo.com','123456','123456','common/default/avatar.png',0,0,57,'2019-04-24 07:28:12','2019-05-30 05:53:12',0),(2,'666666@yahoo.com','666666','123456','common/default/avatar.png',0,0,3,'2019-04-24 07:28:12','2019-05-24 07:50:06',0),(3,'888888@qq.com','东山黄赌毒','123456','/avatar/2019_05_30/94115.jpg',1,45,10,'2019-04-24 07:28:12','2019-06-21 08:54:19',0),(4,'999999@qq.com','暗夜枫佑翼','123456','/avatar/2019_05_30/14472.jpg',0,25,45,'2019-04-24 07:28:12','2019-06-21 08:55:25',0),(5,'1000eee@qq.com','八级大狂风','123456','/avatar/2019_05_30/28881.jpg',1,23,24,'2019-04-24 07:28:12','2019-06-21 08:55:21',0),(19,'53929@qq.com','游客53929','123456','common/default/avatar.png',0,0,0,'2019-04-24 07:28:12','2019-05-07 10:11:59',1),(20,'75770@qq.com','游客75770','123456','common/default/avatar.png',0,0,0,'2019-04-24 07:28:12','2019-05-07 10:11:59',1),(21,'74357@qq.com','游客74357','123456','common/default/avatar.png',0,0,0,'2019-04-24 07:28:12','2019-05-07 10:11:59',1),(22,'67612@qq.com','游客67612','123456','common/default/avatar.png',0,0,0,'2019-04-24 07:28:12','2019-05-07 10:11:59',1),(23,'38286@qq.com','游客38286','123456','common/default/avatar.png',0,0,0,'2019-04-24 07:28:12','2019-05-07 10:11:59',1),(24,'71962@qq.com','游客71962','123456','common/default/avatar.png',0,0,0,'2019-05-07 09:42:52','2019-05-07 10:11:59',1),(25,'79419@qq.com','游客79419','123456','common/default/avatar.png',0,0,0,'2019-05-08 03:14:39','2019-05-08 03:14:39',1),(26,'19788@qq.com','游客19788','123456','common/default/avatar.png',0,0,0,'2019-05-08 06:40:20','2019-05-08 06:40:20',1),(28,'147852@163.com','陈成浩','123456','common/default/avatar.png',1,0,0,'2019-05-08 09:59:21','2019-05-09 03:50:44',0),(29,'360666@yahoo.com','360硬盘','123456','/avatar/2019_05_24/68336.jpg',1,0,48,'2019-05-09 03:42:50','2019-05-30 03:50:25',0),(30,'','游客71200','123456','common/default/avatar.png',0,0,0,'2019-05-27 03:27:51','2019-05-27 03:27:51',1),(31,'','游客56607','123456','common/default/avatar.png',0,0,0,'2019-06-21 03:55:57','2019-06-21 03:55:57',1),(32,'','游客36201','123456','common/default/avatar.png',0,0,0,'2019-06-21 08:30:36','2019-06-21 08:30:36',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wish`
--

DROP TABLE IF EXISTS `wish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `wish` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `wish_id` bigint(20) NOT NULL COMMENT '喜欢的图片id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wish`
--

LOCK TABLES `wish` WRITE;
/*!40000 ALTER TABLE `wish` DISABLE KEYS */;
INSERT INTO `wish` VALUES (2,1000041,'2019-05-23 03:20:16','2019-05-23 03:20:16',29),(3,1000023,'2019-05-23 06:05:42','2019-05-23 06:05:42',29),(4,2000003,'2019-05-24 08:31:31','2019-05-24 08:31:31',29),(5,2000004,'2019-05-27 03:28:19','2019-05-27 03:28:19',30),(6,2000004,'2019-05-27 03:28:32','2019-05-27 03:28:32',30),(7,1000041,'2019-05-30 06:57:18','2019-05-30 06:57:18',3),(8,1000041,'2019-05-30 06:57:19','2019-05-30 06:57:19',3);
/*!40000 ALTER TABLE `wish` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-28 17:23:46
