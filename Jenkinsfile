pipeline {
  agent any
  stages {
    stage('TEST') {
      agent any
      steps {
        sh 'echo "TESTING APPLICATION" && chmod 777 mvnw &&  chown jenkins mvnw && ./mvnw --version'
      }
    }

  }
}