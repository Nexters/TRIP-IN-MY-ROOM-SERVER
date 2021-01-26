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
4. 유저에게 접근 권한을 줍니다.
~~~
Grant all privileges on trip.* to root@localhost;
~~~

## infra


