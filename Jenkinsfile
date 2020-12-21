pipeline {
  agent any
  stages {
    stage('Build') {
      agent any
      steps {
        slackSend(channel: 'deploy-ti', color: '#00FF00', message: "Building Project ${env.JOB_NAME}")
        sh 'echo "BUILDING APPLICATION" &&  chmod 777 mvnw &&  ./mvnw --version && ./mvnw install -DskipTests'
      }
    }

    stage('Tests') {
      parallel {
        stage('Unit Test') {
          steps {
            slackSend(channel: 'deploy-ti', color: '#00FF00', message: "Starting unit tests from ${env.JOB_NAME}")
            sh 'echo "TESTING APPLICATION" &&  chmod 777 mvnw &&  ./mvnw --version && ./mvnw test'
          }
        }

        stage('SonarQube Test') {
          steps {
            slackSend(channel: 'deploy-ti', color: '#00FF00', message: "Starting sonar tests from ${env.JOB_NAME}")
            sh './mvnw  sonar:sonar -Dsonar.projectKey=game-log-sonarqb -Dsonar.host.url=http://sonarqube:9000 -Dsonar.login=8cfd702b6cf5dcea3db5de5bc81c7938daeda711'
          }
        }

      }
    }

    stage('Finish') {
      steps {
        echo 'Finish JOB'
        slackSend(channel: 'deploy-ti', color: '#00FF00', message: "Job ${env.JOB_NAME} finished! :rocket:")
      }
    }

    stage('Choose') {
      steps {
        waitUntil(initialRecurrencePeriod: 1) {
          echo 'teste'
          echo 'message2'
        }

      }
    }

  }
}