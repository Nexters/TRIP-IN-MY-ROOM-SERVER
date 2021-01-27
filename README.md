# TRIP-IN-MY-ROOM-SERVER

# Set-up
## development
#### Mysql 세팅
1. 로컬에 Mysql을 설치합니다.
2. mysql에 접속하여 DB(임시 DB 이름으로 "trip")를 생성합니다.
~~~
mysql -u root -p
create database trip;
~~~
3. DB가 생성되었는지 확인합니다.
~~~
show databases;
~~~
4. 새 유저를 생성하거나 유저에게 접근 권한을 줍니다.
~~~
// 새 유저 생성
grant all privileges on trip.* to ififif@localhost identified by 'ififif' with grant option;
flush privileges;

// 기존의 유저에게 권한 부여
Grant all privileges on trip.* to root@localhost;
~~~

## infra


