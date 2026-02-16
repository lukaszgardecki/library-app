pipeline {
    agent any

    tools {
        jdk 'jdk24'
        maven 'Maven3'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'develop', url: 'https://github.com/lukaszgardecki/library-app.git'
            }
        }
        
        stage('Build Backend') {
            steps {
                script {
                    def toolHome = tool 'jdk24'
                    def realJdkHome = "${toolHome}/jdk-24.0.2" 
                    
                    withEnv(["JAVA_HOME=${realJdkHome}", "PATH=${realJdkHome}/bin:${env.PATH}"]) {
                        sh 'java -version'
                        sh 'mvn install -N -f backend/pom.xml'
                    }
                }
            }
        }
    }
}