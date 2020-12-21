pipeline {
  agent {
    docker {
      image 'maven'
    }

  }
  stages {
    stage('TEST') {
      agent {
        docker {
          image 'maven'
        }

      }
      steps {
        sh 'echo "TESTING APPLICATION" && mvn --version'
      }
    }

  }
}