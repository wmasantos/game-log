pipeline {
  agent any
  stages {
    stage('TEST') {
      agent any
      steps {
        sh 'echo "TESTING APPLICATION" && sudo chmod 777 mvnw && sudo chown $USER mvnw &&mvn --version'
      }
    }

  }
}