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
        sh 'echo "TESTING APPLICATION" && mvn --version'
      }
    }

  }
}