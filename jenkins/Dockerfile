FROM jenkins/jenkins:lts

USER root

RUN apt-get update && apt-get install -y \
    wget \
    curl \
    git \
    && rm -rf /var/lib/apt/lists/*

RUN wget https://download.oracle.com/java/24/archive/jdk-24.0.2_linux-x64_bin.tar.gz \
    && tar -xvf jdk-24.0.2_linux-x64_bin.tar.gz \
    && mv jdk-24.0.2 /opt/jdk24 \
    && rm jdk-24.0.2_linux-x64_bin.tar.gz

ENV JAVA_HOME=/opt/jdk24
ENV PATH="${JAVA_HOME}/bin:${PATH}"

USER jenkins