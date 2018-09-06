pipeline{
  
    agent none
 
    stages{
        stage('Build') {
            agent{
                docker{
                    image 'maven:3-jdk-10'
                    args '-v ${HOME}/.m2:/.m2:z -e _JAVA_OPTIONS="-Duser.home=/"'
                }
            }          
            steps{
 
                sh 'mvn clean install -Dmaven.test.failure.ignore=true -U'
 
            }
        }
        stage('Test') {
            agent{
                docker{
                    image 'maven:3-jdk-10'
                    args '-v ${HOME}/.m2:/.m2:z -v ${HOME}/.sonar:/.sonar:z -e _JAVA_OPTIONS="-Duser.home=/"'
                }
            }
            steps{
                parallel(
                    SonarQube: {
                        withSonarQubeEnv('SonarQube') {
 
                            sh "mvn org.sonarsource.scanner.maven:sonar-maven-plugin:sonar"
                        }
                    },
                    JUnit: {
                        junit '**/target/surefire-reports/TEST-*.xml'
                        archive 'target/*.jar'
                    }
                )
            }
        }
 
        stage('Archive') {
            parallel{
                stage('Nexus'){
                    agent{
                        docker{
                            image 'maven:3-jdk-10'

                            args '-v ${HOME}/.m2:/.m2:z -e _JAVA_OPTIONS="-Duser.home=/"'
                        }
                    }
                    steps{
 
                        sh "mvn deploy -Dmaven.test.skip=true"
 
                    }
                }
                stage('Local'){
                    agent any
     
                    steps{
 
                        archiveArtifacts 'target/*.jar'
 
                    }
                }
 
            }
 
        }
        stage('Docker'){
            agent any
            steps{
                sh "echo POM artifactid ${env.POM_ARTIFACTID}"
                script{
                    docker.withRegistry('https://nexus.lab.zivra.com:3456', 'nexus3admin'){
 
                        def jobName = "${env.JOB_NAME}"
                        def imageName = jobName.substring(0, jobName.indexOf("/"))
 
                        def pom = readMavenPom file: 'pom.xml'
                         
                        def outputJar = pom.artifactId + "-" + pom.version + ".jar"
 
                        sh "echo jar name ${outputJar}"
                         
                        def myImage = docker.build(imageName, "--build-arg JAR_FILE=${outputJar} .")
 
                        myImage.push("${env.BUILD_ID}")
                        myImage.push('latest')

                    //    sh "docker -H qa-deploy1:2375 stop ${imageName} || true"
                    //    sh "docker -H qa-deploy1:2375 rm ${imageName} || true"
                    //    sh "docker -H qa-deploy1:2375 run -d --name ${imageName} -p 8087:8087 ${imageName}:latest"


                    }
                }
            }
        }
    }
    post{
        always{
            mail to: 'developers@lab.zivra.com',
                 subject: "${currentBuild.currentResult} - ${currentBuild.fullDisplayName}",
                 body: "${env.BUILD_URL}"
        }
    }
}
