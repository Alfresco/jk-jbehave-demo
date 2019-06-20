node {
    def mvnHome

    stage('Preparation') {
        git 'https://github.com/Alfresco/jk-jbehave-demo.git'
        mvnHome = tool 'M3'
    }

    stage('Build') {
        sh "'${mvnHome}/bin/mvn' clean install"
    }

    stage('Results') {

        junit '**/target/surefire-reports/*.xml'

        // publish html
        publishHTML (target: [
                allowMissing: false,
                alwaysLinkToLastBuild: false,
                keepAll: true,
                reportDir: 'target/site/serenity',
                reportFiles: 'index.html',
                reportName: "Serenity Report"
        ])
    }
}