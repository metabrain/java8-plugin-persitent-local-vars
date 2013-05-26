java8-plugin-persitent-local-vars
=================================

A plugin for javac (openJDK8) that adds support for persistent local variables in Java (similar to the C static keyword in a method's scope)

WHAT IT IS
============

Similar to the C99 *static* keyword, it serves to indicate a local variable that should persist through invocations to the method. Currently, that variable is initialized when :

- the class is loaded (for static functions) 
- or when the object is created (for instance methods)

In future iterations of the plugin, support for initialization only when the method is invoked for the first time might be supported.

As such, since this plugin can not change the javac parser, only the subsequently generated Abstract Syntax Tree, I resorted to annotation to achieve this purpose. The annotation is @Persistent (arguably not the best name, but had no better idea...).

REQUIREMENTS
============
- openJDK8 javac (-source 1.8)

HOW TO USE
==========

You need to add the plugin *.jar* to the classpath using *-cp* and then request the usage of the plugin with *-Xplugin:Persistent_Plugin*. If everything is correct and the plugin is running, *"Plugin Running!"* should be print by javac.

    javac8 -cp .\persistentplugin-0.0.1-SNAPSHOT.jar -Xplugin:Persistent_Plugin *.java

EXAMPLE USAGE
=============

```java
class Clazz {

  public static int add() {
    @Persistent int num_invocations = 0 ;
    num_invocations++ ;
    (...)
    something(num_invocations);
    (...)
  }
}  
```     
    
...effectivelly, you can consider this code to be transformed into :

```java
class Clazz {

  private static int num_invocations_random_name = 0 ;
  
  public static int add() {
    num_invocations_random_name++ ;
    (...)
    something(num_invocations_random_name);
    (...)
  }

} 
```   
    
The variable is given a random sufix (currently this sufix is the name of the function where it is used) to avoid scope clashes with similar variables from other methods (two methods each with a @Persistent int counter = 0 ; would clach when moved to the class scope without renaming). Similarlly, if this method is not-static, e.g.: an instance method, the generated variable would not be static.


```java
class Clazz {

  public int add() { //NON-STATIC METHOD
    @Persistent int num_invocations = 0 ;
    num_invocations++ ;
    (...)
    something(num_invocations);
    (...)
  }

}    
```
    
...into

```java
class Clazz {
    
  private int num_invocations_random_name = 0 ;
  public int add() {
    num_invocations_random_name++ ;
    (...)
    something(num_invocations_random_name);
    (...)
  }

}  
```  
    
WHAT WAS TESTED
===============
- static methods
- non-static methods
- static methods in inner classes
- non-static methods in inner classes

WHAT WAS NOT TESTED
===================
-lambdas/closures with @Persistent variables
-annoynimous classes
    
FUTURE FEATURES / WISHLIST
==========================
- None at the moment?
