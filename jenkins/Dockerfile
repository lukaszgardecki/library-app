FROM jenkins/jenkins:lts

USER root

RUN apt-get update && apt-get install -y wget curl git maven && rm -rf /var/lib/apt/lists/*

RUN wget https://download.oracle.com/java/24/archive/jdk-24.0.2_linux-x64_bin.tar.gz \
    && tar -xvf jdk-24.0.2_linux-x64_bin.tar.gz \
    && mv jdk-24.0.2 /opt/jdk24 \
    && rm jdk-24.0.2_linux-x64_bin.tar.gz

USER jenkins