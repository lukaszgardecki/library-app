pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'develop', url: 'https://github.com/lukaszgardecki/library-app.git'
            }
        }
        
        stage('Build Backend') {
            steps {
                script {
                    withEnv(["JAVA_HOME=/opt/jdk24", "PATH=/opt/jdk24/bin:${env.PATH}"]) {
                        sh 'java -version'
                        sh 'mvn clean install -DskipTests -f backend/pom.xml'
                    }
                }
            }
        }
    }
}