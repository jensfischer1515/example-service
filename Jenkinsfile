pipeline {
    agent { docker { image 'gradle:6.8.3-jdk11-hotspot' } }
    stages {
        stage('build') {
            steps {
                sh 'gradle --version'
            }
        }
    }
}
