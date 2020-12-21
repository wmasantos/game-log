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
            sh '''./mvnw  sonar:sonar -Dsonar.projectKey=game-log-sonarqb 
  -Dsonar.host.url=http://192.168.35.116:9000 
  -Dsonar.login=8cfd702b6cf5dcea3db5de5bc81c7938daeda711'''
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