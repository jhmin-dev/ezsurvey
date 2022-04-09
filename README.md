# ezsurvey

- 간편하게 설문조사를 제작, 공유하는 사이트를 Spring Boot 기반으로 로컬에서 개발하는 1인 프로젝트
	+ 2022년 3월 14일부터 4월 11일까지 진행

## 개발 환경

- Spring Tools 4 for Eclipse
- Spring Boot 2.6.6.RELEASE
	+ Spring Session & Spring Data Redis
	+ Spring Data JPA & Querydsl
- Java 11
- Lombok
- Gradle

## 배포 환경

- Oracle Cloud Always Free Resources
	+ [VM.Standard.E2.1.Micro](https://docs.oracle.com/en-us/iaas/Content/Compute/References/computeshapes.htm#vmshapes__vm-standard)
	+ [Autonomous Data Warehouse](https://docs.oracle.com/en-us/iaas/Content/Database/Concepts/adboverview.htm#workloads)
- Ubuntu 20.04.4 LTS
- Apache Tomcat 9.0.31
- Redis server 5.0.7
- Oracle Database 19c