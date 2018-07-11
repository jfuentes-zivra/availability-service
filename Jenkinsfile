pipeline {
  agent none
  stages {
    stage('Build') {
      agent {
        docker {
          image 'maven:3.5.4-jdk-8-alpine'
          args '-v $HOME/.m2:/root/.m2'
        }
      }

      steps {
        sh 'mvn -B -DskipTests clean package'

        archiveArtifacts(artifacts: 'target/availability-service-0.0.1-SNAPSHOT.jar', fingerprint: true)

      }
    }

    stage('Test') {
      agent {
        docker {
          image 'maven:3.5.4-jdk-8-alpine'
          args '-v $HOME/.m2:/root/.m2'
        }
      }

      steps {
        sh 'mvn test'

        junit 'target/surefire-reports/*.xml'

      }
    }
    stage('Nexus'){
      agent {
        docker {
          image 'maven:3.5.4-jdk-8-alpine'
          args '-v $HOME/.m2:${WORKSPACE}/.m2'
        }
      }

      steps {
      sh "ls -la .m2"
        sh "cat .m2/settings.xml"
        sh "mvn deploy -Dmaven.test.skip=true -s .m2/settings.xml"
      }

    }
    stage('Docker Image') {
      steps {
        script {
          docker.withRegistry('https://nexus.lab.zivra.com:6543', 'nexus3admin') {

            def myImage = docker.build("availability-service:${env.BUILD_ID}")
            
            myImage.tag("latest")

            myImage.push()

          }
        }

      }
    }
  }

}
