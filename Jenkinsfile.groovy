pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'echo "Hello World"'
                sh '''
                    echo "Multiline shell steps works too"
                    ls -lah
                '''
                //step([$class: 'Mailer', recipients: 'gevireddy@gmail.com'])
            }
        }
    stage('Test') {
            steps {
                sh 'echo "Fail!" '
            }
        }
    }
    //def to = 'gevireddy@gmail.com'
    //def content = '${JELLY_SCRIPT,template="html"}'
    //def subject = "${env.JOB_NAME} - Build #${env.BUILD_NUMBER} ${currentBuild.result}"
        post {
        always {
            echo 'This will always run'

        }
        success {
            echo 'This will run only if successful'
            emailext(body: '${JELLY_SCRIPT,template="html"}', mimeType: 'text/html',
                    replyTo: '$DEFAULT_REPLYTO', subject: "${env.JOB_NAME} - Build #${env.BUILD_NUMBER} ${currentBuild.result}",
                    to: 'gevireddy@gmail.com', attachLog: true )

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