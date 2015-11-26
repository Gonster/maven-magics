# maven-magics
maven plugin examples

LICENSE MIT

###Install

Go to maven-magics root directory and run `mvn install`.

###Running

Maven 3.2.3+ is used and the plugins is tested under this version of Maven. More details can be found in the following chapter.

###Brief Introduction

####1. cast-my-first-mojo-maven-plugin
A dummy plugin coded according to the [basic guide](http://maven.apache.org/guides/plugin/guide-java-plugin-development.html) on official site of Maven.  

####2. routes-maven-plugin
Currently it can aggregate `@RequestMapping` annotation defined in spring mvc controllers. After adding following plugin dependendcy to your pom.xml:

```xml
<plugin>
    <groupId>io.github.gonster</groupId>
    <artifactId>routes-maven-plugin</artifactId>
    <version>1.0-SNAPSHOT</version>
    <configuration>
        <bases>com.example.project.controller</bases>
    </configuration>
</plugin>
```

The package name rounded by `Bases` markup specifies where to find the java source code files.   
Run `routes:summary` ,then the result will print in the console and to the default output file `${project.basedir}/routes.txt`. 
