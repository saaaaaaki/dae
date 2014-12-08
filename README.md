Developing an Akka Edge
chapter 2
Store and Retrieve a bookmark

===
##Run server
```
sbt
>container:start
```

##Store a bookmark
```
curl -d title="title of bookmark" -d url="http://url.of.bookmark"
```

then,remenber uuid.
```
Stored bookmark with uuid:hogehoge
```

##Retrieve a bookmark
Accsess "http://localhost:8080?uuid=hogehoge" with your brawser.
