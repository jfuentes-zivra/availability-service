pipeline {
  agent {
    docker {
      image 'maven:3.5.4-jdk-8-alpine'
      args '-v $HOME/.m2:/root/.m2'
    }

  }
  stages {
    stage('Build') {
      steps {
        sh 'mvn -B -DskipTests clean package'
      }
    }
    stage('Test') {
      steps {
        sh 'mvn test'
      }
    }
    stage('Docker Image') {
      steps {
        script {
          docker.withRegistry('https://nexus.lab.zivra.com:6543', 'nexus3admin') {

            def myImage = docker.build("availability-service:${env.BUILD_ID}")

            myImage.push()
          }
        }

      }
    }
  }
  post {
    always {
      archiveArtifacts(artifacts: 'target/availability-service-0.0.1-SNAPSHOT.jar', fingerprint: true)
      junit 'target/surefire-reports/*.xml'

    }

  }
}