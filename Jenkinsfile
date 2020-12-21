pipeline {
  agent any
  stages {
    stage('TEST') {
      agent {
        docker {
          image 'maven:3-alpine'
          args '-v /root/.m2:/root/.m2'
        }

      }
      steps {
        sh 'echo "TESTING APPLICATION" && sudo chmod 777 mvnw && sudo chown $USER mvnw &&mvn --version'
      }
    }

  }
}