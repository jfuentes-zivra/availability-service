pipeline {
  agent none

  stages {
    stage('Build') {
      agent {     docker {
            image 'maven:3.5.4-jdk-8-alpine'
            args '-v $HOME/.m2:/root/.m2'
          }
      }
      steps {
        sh 'mvn -B -DskipTests clean package'

        // stash includes: 'target/*.jar', name: 'app'

      }
    }
    stage('Test') {
      agent{
        docker {
          image 'maven:3.5.4-jdk-8-alpine'
          args '-v $HOME/.m2:/root/.m2'
        }
      }
      steps {
        sh 'mvn test'
      }
    }
    stage('Docker Image') {
      agent any  // {label 'master' }
      steps {

        //  unstash 'app'

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