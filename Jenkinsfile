pipeline {
  agent any
  stages {
    stage('TEST') {
      agent any
      steps {
        sh 'echo "TESTING APPLICATION" && ./mvnw --version'
      }
    }

  }
}