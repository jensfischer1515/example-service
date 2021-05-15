#!groovy

def lib = library identifier: 'gradle-shared-lib@main', retriever: modernSCM(
        [$class       : 'GitSCMSource',
         remote       : 'https://github.com/jensfischer1515/jenkins-shared-lib-gradle.git',
         credentialsId: 'github-key'])


/*
def utils = library('mylib').com.mycorp.jenkins.Utils.new(this)
utils.handyStuff()
*/

//@Library('gradle-shared-lib') import org.example.pipeline.Gradle
//def gradle = new org.example.pipeline.Gradle(this)

pipeline {
    agent { docker { image 'openjdk:11-jdk' } }

    triggers {
        pollSCM('* * * * *')
    }

    parameters {
        string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?')
        text(name: 'BIOGRAPHY', defaultValue: '', description: 'Enter some information about the person')
        booleanParam(name: 'TOGGLE', defaultValue: true, description: 'Toggle this value')
        choice(name: 'CHOICE', choices: ['One', 'Two', 'Three'], description: 'Pick something')
        password(name: 'PASSWORD', defaultValue: 'SECRET', description: 'Enter a password')
    }

    stages {

// ==========================================================================================================================================================

        stage('BUILD') {
            environment {
                FOR_SEQUENTIAL = "some-value"
            }
            stages {
                stage('Init') {
                    steps {
                        script {
                            def gradle = lib.org.example.pipeline.Gradle.new(this)
                            gradle.wrapper('help')
                        }
                        echo 'Init'
                        gradlew('sleep')
                    }
                }
                stage('Deploy Service Resources') {
                    steps {
                        echo 'Deploy Service Resources'
                        gradlew('sleep')
                    }
                }
                stage('Build') {
                    steps {
                        echo 'Build'
                        script {
                            try {
                                gradlew('clean', 'build', '--refresh-dependencies')
                            } finally {
                                junit '**/build/test-results/test/*.xml'
                                //make the junit test results available in any case (success & failure)
                            }
                        }
                    }
                }
                stage('TestJob Starter Parallel') {
                    parallel {
                        stage('In Parallel 1') {
                            steps {
                                echo "In Parallel 1"
                            }
                        }
                        stage('In Parallel 2') {
                            steps {
                                echo "In Parallel 2"
                            }
                        }
                    }
                }
                stage('Style Check') {
                    when { environment name: 'USE_CHECK_JOBS', value: 'true' }
                    steps {
                        echo 'Style Check'
                        gradlew('sleep')
                    }
                }
            }
        }

// ==========================================================================================================================================================

        stage('CI') {
            when { branch 'main' }
            steps {
                echo 'Stage ci...'
                gradlew('deployCI', 'properties')
            }
        }

// ==========================================================================================================================================================

        stage('STAGING') {
            when { branch 'main' }
            steps {
                echo 'Stage staging...'
                gradlew('deployStaging', 'properties')
            }
        }

// ==========================================================================================================================================================

        stage('PRODUCTION') {
            when { branch 'main' }
            steps {
                echo 'Stage production...'
                gradlew('deployProduction', 'properties')
                sh './gradlew deployProduction'
            }
        }
    }

// ==========================================================================================================================================================

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

// ==========================================================================================================================================================

/*
def gradlew(String... args) {
    sh "./gradlew ${args.join(' ')} --console=verbose --info --stacktrace --daemon"
}
 */
