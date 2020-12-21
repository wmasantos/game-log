pipeline {
  agent any
  stages {
    stage('TEST') {
      agent any
      steps {
        sh 'echo "TESTING APPLICATION" &&  chown jenkins mvnw && ./mvnw --version'
      }
    }

  }
}