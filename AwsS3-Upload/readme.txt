Do not forget to set the config.properties close to the final jar AwsS3-Upload-0.0.1-SNAPSHOT-jar-with-dependencies.jar

remember to replace inside the config.properties your credentials:

accessKey=
secretKey=

those are the credentiales which were created before in order to have permision over the bucket 

to tun this, just run
mvn clean compile package
go to the target folder and paste the config.properties, or move it to the folder you want to run it, but do not forget that config.properties file

now the way to use it:


1: first, execute the the jar, as java -jar AwsS3-Upload-0.0.1-SNAPSHOT-jar-with-dependencies.jar
2: give it the first parameter as the bucket name, it does not matter if you have more subfolders inside the bucket just keep in mind that the way to especify more subfolders in is with the character /
3: give it the path of the file you want to upload, if you are using windwos that's the way to do it

D:\\filename or
C:\\

if you are using linux systems it would be like /yourpath/yourfile

example:
java -jar AwsS3-Upload-0.0.1-SNAPSHOT-jar-with-dependencies.jar backupsdbmedics/db3 D:\\zpl-zbi2-pm-en-PROGRAMING_GUIDE_ZM400.pdf
