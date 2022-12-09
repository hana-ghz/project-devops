def buildJar() {
    echo "building the application..."
    sh 'mvn clean install -Dmaven.test.skip=true'
}

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'dockerhub-asma', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t asmaid/demo-app:${IMAGE_NAME} .'
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh 'docker push asmaid/demo-app:${IMAGE_NAME}'
    }
}

def sonarTest() {
    echo "Running sonarQube checks..."
    sh 'mvn clean verify sonar:sonar   -Dmaven.test.skip=true  -Dsonar.projectKey=project-devops   -Dsonar.host.url=http://172.17.0.2:9000   -Dsonar.login=sqp_114f7278795d345ede80d4d31a39c9bd382db6dd'
}


def deployApp() {
    echo 'deploying the application...'
}

def pushToNexus() {
   /* echo "pushing the jar file to Nexus maven-snapshots repo..."
    sh 'mvn clean deploy -Dmaven.test.skip=true' */
    nexusArtifactUploader artifacts: [[artifactId: 'project-devops', classifier: '', file: 'Uber.jar', type: 'jar']], credentialsId: '', groupId: 'com.example', nexusUrl: 'localhost:8081', nexusVersion: 'nexus3', protocol: 'http', repository: 'project_devops', version: '0.0.1-SNAPSHOT'
}


return this