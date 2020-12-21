pipeline {
  agent any
  stages {
    stage('TEST') {
      steps {
        sh 'echo "TESTING APPLICATION" && chown $USER mvnw && chmod 777 mvnw && ./mvnw --version'
      }
    }

  }
}