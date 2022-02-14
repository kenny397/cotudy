# backend 실행 방법




## Docker image build

backend root 경로에서

```bash
 gradle clean
 gradle bootJar
 docker build -t a105_backend .
```

를 입력하면, backend 이미지 빌드 결과물이 생깁니다. 이 결과물과 frontend 이미지와 docker compose를 통해 배포가 가능합니다.


## Openvidu 배포

---

- 오픈비두를 배포하기 root 권한을 얻어야 함

```bash
sudo su
```

- 오픈비두를 설치하기 위해 권장되는 경로인 `/opt`로 이동

```bash
cd /opt
```

- 오픈비두 설치

```bash
curl <https://s3-eu-west-1.amazonaws.com/aws.openvidu.io/install_openvidu_latest.sh> | bash
```

- 설치 후 오픈비두가 설치된 경로로 이동

```bash
$ cd openvidu
```

- 도메인 또는 퍼블릭IP와 오픈비두와 통신을 위한 환경설정

```bash
$ nano .env

# OpenVidu configuration
# ----------------------
# 도메인 또는 퍼블릭IP 주소
DOMAIN_OR_PUBLIC_IP=i6b106.p.ssafy.io

# 오픈비두 서버와 통신을 위한 시크릿
OPENVIDU_SECRET=[secret]

# Certificate type
CERTIFICATE_TYPE=letsencrypt

# 인증서 타입이 letsencrypt일 경우 이메일 설정
LETSENCRYPT_EMAIL=user@example.com

# HTTP port
HTTP_PORT=6442

# HTTPS port(해당 포트를 통해 오픈비두 서버와 연결)
HTTPS_PORT= 6443

```

- 설정 후 오픈비두 서버 실행(`ctrl + c`를 누르면 백그라운드로 실행됨)

```bash
$ ./openvidu start

Creating openvidu-docker-compose_coturn_1          ... done
Creating openvidu-docker-compose_app_1             ... done
Creating openvidu-docker-compose_kms_1             ... done
Creating openvidu-docker-compose_nginx_1           ... done
Creating openvidu-docker-compose_redis_1           ... done
Creating openvidu-docker-compose_openvidu-server_1 ... done

----------------------------------------------------

   OpenVidu Platform is ready!
   ---------------------------

   * OpenVidu Server: https://DOMAIN_OR_PUBLIC_IP/

   * OpenVidu Dashboard: https://DOMAIN_OR_PUBLIC_IP/dashboard/

----------------------------------------------------

## mysql 배포
```
docker pull mysql 
```
도커에 mysql 이미지를 설치하고
```
docker run -d -p 3306:3306 --name mysql-container -e MYSQL_ROOT_PASSWORD=1234ssafy! -v ~/mysqldata/:/var/lib/mysql mysql --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
```
를 실행하면 mysql 배포가 완료되었습니다.


