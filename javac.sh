
 mkdir algs4
 chmod 755 algs4

#Download the textbook libraries from algs4.jar and the Java wrapper scripts from javac-algs4 and java-algs4.

 wget http://algs4.cs.princeton.edu/code/algs4.jar
 wget http://algs4.cs.princeton.edu/linux/javac-algs4
 wget http://algs4.cs.princeton.edu/linux/java-algs4
 chmod 755 javac-algs4 java-algs4
 #mv javac-algs4 /usr/local/bin
 #mv java-algs4 /usr/local/bin

#Download DrJava from drjava.jar, the wrapper script from drjava, and the configuration file from .drjava.

 wget http://algs4.cs.princeton.edu/linux/drjava.jar
 wget http://algs4.cs.princeton.edu/linux/drjava
 wget http://algs4.cs.princeton.edu/linux/.drjava
 chmod 755 drjava
 #mv drjava /usr/local/bin
 #mv .drjava ~

#Download Checkstyle 7.4 from checkstyle.zip; our Checkstyle configuration file from checkstyle-algs4.xml; and the Checkstyle wrapper script from from checkstyle-algs4

 wget http://algs4.cs.princeton.edu/linux/checkstyle.zip
 wget http://algs4.cs.princeton.edu/linux/checkstyle-algs4.xml
 wget http://algs4.cs.princeton.edu/linux/checkstyle-suppressions.xml
 wget http://algs4.cs.princeton.edu/linux/checkstyle-algs4
 unzip checkstyle.zip
 chmod 755 checkstyle-algs4
 #mv checkstyle-algs4 /usr/local/bin

#Download Findbugs 3.0.1 from findbugs.zip; our Findbugs configuration file from findbugs.xml; and the Findbugs wrapper script from findbugs-algs4.

 wget http://algs4.cs.princeton.edu/linux/findbugs.zip
 wget http://algs4.cs.princeton.edu/linux/findbugs.xml
 wget http://algs4.cs.princeton.edu/linux/findbugs-algs4
 unzip findbugs.zip
 chmod 755 findbugs-algs4
 #mv findbugs-algs4 /usr/local/bin

#exports
export CLASSPATH=$CLASSPATH:/home/bcc/andrei/Documentos/java/algs4.jar


