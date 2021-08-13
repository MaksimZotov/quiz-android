# Quiz
This is an app where you can play a quiz with other people on the network
# Server
A link to the [repository](https://github.com/MaksimZotov/quiz-server)

Please note that to connect to the server you have to specify a public ip address of a host where your server is running.<br/>
You can do this in createConnection(): [line 19](https://github.com/MaksimZotov/quiz-android/blob/master/app/src/main/java/com/maksimzotov/quiz/model/network/Server.kt)

Personally, I deployed the server on [Google Cloud Compute Engine](https://cloud.google.com/compute/?utm_source=yandex&utm_medium=cpc&utm_campaign=compute_rf_54905824&utm_content=text_1_9617856373&utm_term=compute%20engine_none__desktop&yclid=4654653743411529480)

Also you should know that questions and answers are taken from an external database.<br/>
You can change the database [here](https://github.com/MaksimZotov/quiz-server/blob/master/src/main/kotlin/questions/Database.kt)
# Video
A link to the [video](https://youtu.be/-rFHFjvCmCM)
# Built with:
MVVM<br/>
LiveData<br/>
DataBinding<br/>
Navigation Component<br/>
Google Cloud Compute Engine<br/>
MySQL Database (I used [sprinthost](https://cp.sprinthost.ru))
