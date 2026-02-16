FROM jenkins/jenkins:lts

USER root

RUN apt-get update && apt-get install -y wget curl git maven && rm -rf /var/lib/apt/lists/*

ARG TARGETARCH

RUN if [ "$TARGETARCH" = "arm64" ]; then \
        URL="https://download.oracle.com/java/24/archive/jdk-24.0.2_linux-aarch64_bin.tar.gz"; \
    else \
        URL="https://download.oracle.com/java/24/archive/jdk-24.0.2_linux-x64_bin.tar.gz"; \
    fi && \
    wget -q $URL -O jdk.tar.gz && \
    tar -xvf jdk.tar.gz && \
    mv jdk-24* /opt/jdk24 && \
    rm jdk.tar.gz


USER jenkins