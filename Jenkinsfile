pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        slackSend(channel: 'deploy-ti', color: '#00FF00', message: "The Pipeline ${currentBuild.fullDisplayName} has been started, keep your :eyes: on it... Building Project")
        sh 'echo "BUILDING APPLICATION" &&  chmod 777 mvnw &&  ./mvnw --version && ./mvnw install -DskipTests'
      }
    }

    stage('Tests') {
      parallel {
        stage('Unit Test') {
          steps {
            slackSend(channel: 'deploy-ti', color: '#00FF00', message: "Starting unit tests from ${currentBuild.fullDisplayName}")
            sh 'echo "TESTING APPLICATION" &&  chmod 777 mvnw &&  ./mvnw --version && ./mvnw test'
          }
        }

        stage('SonarQube Test') {
          steps {
            slackSend(channel: 'deploy-ti', color: '#00FF00', message: "Starting sonar tests from ${currentBuild.fullDisplayName}")
            sh './mvnw  sonar:sonar -Dsonar.projectKey=game-log-sonarqb -Dsonar.host.url=http://sonarqube:9000 -Dsonar.login=8cfd702b6cf5dcea3db5de5bc81c7938daeda711'
          }
        }

      }
    }

    stage('Confirm TI') {
      steps {
        echo 'Pipeline checks environments'
        script {
          userInput = input(message: 'Proceed deploy to TI?', parameters: [[$class: 'BooleanParameterDefinition', defaultValue: true, description: '', name: 'Please confirm you agree with this']])
        }

      }
    }

    stage('Deploy TI') {
      when {
        expression {
          userInput == true
        }

      }
      steps {
        slackSend(channel: 'deploy-ti', color: '#0000FF', message: "Starting deploy in TI - ${currentBuild.fullDisplayName} :crossed_fingers:")
      }
    }

    stage('Confirm QA') {
      steps {
        script {
          gmud = input(message: 'What\'s GMUD?', id: 'GMUD', ok: 'Enviar', submitter: 'GMUD-123')
          echo ("GMUD: " + gmud)
        }

      }
    }

  }
  post {
    success {
      echo 'Pipeline executed with success'
      slackSend(channel: 'deploy-ti', color: '#00FF00', message: "Job ${env.JOB_NAME} finished! :rocket:")
    }

    failure {
      echo 'An error occur during the build process.'
      slackSend(channel: 'deploy-ti', color: '#FF0000', message: "Job ${env.JOB_NAME} finished with error :scream:")
    }

  }
}