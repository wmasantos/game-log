pipeline {
  agent any
  stages {
    stage('TEST') {
      steps {
        sh 'echo "TESTING APPLICATION" && mvn --version && mvn test'
      }
    }

  }
}