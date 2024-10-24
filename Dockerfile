FROM ubuntu:latest

# C++ 컴파일러 설치
RUN apt-get update && apt-get install -y g++

# 작업 디렉토리 설정
WORKDIR /usr/src/app

# 실행할 때 대기 상태를 유지하기 위해 Bash 실행
ENTRYPOINT ["tail", "-f", "/dev/null"]
