pipeline {
  agent any
  stages {
    stage('Build') {
      agent any
      steps {
        sh 'echo "TESTING APPLICATION" &&  chmod 777 mvnw &&  ./mvnw --version && ./mvnw install -DskipTests'
      }
    }

    stage('Tests') {
      parallel {
        stage('Unit Test') {
          steps {
            sh 'echo "TESTING APPLICATION" &&  chmod 777 mvnw &&  ./mvnw --version && ./mvnw test'
          }
        }

        stage('SonarQube Test') {
          steps {
            sh './mvnw  sonar:sonar -Dsonar.host.url=http://192.168.35.116:9000 -Dlicense.skip=true'
          }
        }

      }
    }

    stage('Finish') {
      steps {
        echo 'Finish JOB'
      }
    }

  }
}