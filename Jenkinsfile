pipeline {
  agent any
  stages {
    stage('clone') {
      steps {
        git(url: 'https://github.com/cc0411/firstProject', branch: '$release_brach')
      }
    }
  }
}