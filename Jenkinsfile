#!groovy

pipeline {
    agent { docker { image 'openjdk:11-jdk' } }

    triggers {
        pollSCM('* * * * *')
    }
    stages {
        stage('build') {
            steps {
                echo 'Stage build...'
                script {
                    try {
                        gradlew('clean', 'build', '--refresh-dependencies')
                    } finally {
                        junit '**/build/test-results/test/*.xml' //make the junit test results available in any case (success & failure)
                    }
                }
            }
        }
        stage('ci') {
            steps {
                echo 'Stage ci...'
                gradlew('deployCI', 'properties')
            }
        }
        stage('staging') {
            steps {
                echo 'Stage staging...'
                gradlew('deployStaging', 'properties')
            }
        }
        stage('production') {
            steps {
                echo 'Stage production...'
                gradlew('deployProduction', 'properties')
                sh './gradlew deployProduction'
            }
        }
    }
    post {
        always {
            echo 'This will always run'
        }
        success {
            echo 'This will run only if successful'
        }
        failure {
            echo 'This will run only if failed'
        }
        unstable {
            echo 'This will run only if the run was marked as unstable'
        }
        changed {
            echo 'This will run only if the state of the Pipeline has changed'
            echo 'For example, if the Pipeline was previously failing but is now successful'
        }
    }
}

def gradlew(String... args) {
    sh "./gradlew ${args.join(' ')} --console=verbose --info --stacktrace --daemon"
}
