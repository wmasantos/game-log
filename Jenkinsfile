pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        slackSend(channel: 'deploy-ti', color: '#00FF00', message: "Start Building Project ${env.JOB_NAME}")
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

    stage('Confirm TI') {
      steps {
        echo 'Pipeline checks finished'
        script {
            userInput = input(message: 'Proceed deploy to TI?', parameters: [[$class: 'BooleanParameterDefinition', defaultValue: true, description: '', name: 'Please confirm you agree with this']])

            if (userInput) {
                echo ("IQA Sheet Path: "+userInput)
            }
            else {
                echo ("Test Info file path: "+userInput)
            }
        }
      }
    }

   stage('Deploy TI') {
     when {
       expression { userInput == true }
     }
     steps {
       slackSend(channel: 'deploy-ti', color: '#0000FF', message: "Starting deploy in TI")
     }
   }

    stage('Confirm QA') {
      steps {
        input(message: 'What\'s GMUD?', id: 'GMUD', ok: 'Enviar', submitter: 'GMUD-123')
        //error 'GMUD not found'
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