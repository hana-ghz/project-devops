def buildJar() {
    echo "building the application..."
    sh 'mvn clean install -Dmaven.test.skip=true'
}

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'docker-credentials', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t hanaghz/demo-app:${IMAGE_NAME} .'
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh 'docker push hanaghz/demo-app:${IMAGE_NAME}'
    }
}

def sonarTest() {
    mvn clean verify sonar:sonar \
  -Dsonar.projectKey=DevOpsProject \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=sqp_70c661afd85b650082d03565cc67cd2c3a12b7e7
    echo "Running sonarQube checks..."
    sh 'mvn clean verify sonar:sonar   -Dmaven.test.skip=true  -Dsonar.projectKey=DevOpsProject    -Dsonar.host.url=http://localhost:9000    -Dsonar.login=sqp_70c661afd85b650082d03565cc67cd2c3a12b7e7
}


def deployApp() {
    echo 'deploying the application...'
}

def pushToNexus() {
    echo "pushing the jar file to Nexus maven-snapshots repo..."
    sh 'mvn clean deploy -Dmaven.test.skip=true'
}


return this