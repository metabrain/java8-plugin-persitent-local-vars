java8-plugin-persitent-local-vars
=================================

A plugin for javac (openJDK8) that adds support for persistent local variables in Java (similar to the [C static keyword](http://stackoverflow.com/questions/5033627/static-variable-inside-of-a-function-in-c))

## Motivation

This project is, in a way, a proof-of-concept of the injection of new fields inside pre-existing classes during the compilation process.
During the elaboration of my master thesis, that also makes use of a javac plugin, injecting new fields (variable declarations) inside the pre-existing classes AST node would crash the JVM at runtime. The reason for this is that the offset field values used by the compiler are calculated after the initial parsing, thus, before the new field was injected. To fix this, the offset values have to be manually updated when the new field is injected. 

## What it is

Similar to the C99 *static* keyword, it serves to indicate a local variable that should persist through invocations to the method. Currently, that variable is initialized when :

- the class is loaded (for static functions) 
- or when the object is created (for instance methods)

In future iterations of the plugin, support for initialization only when the method is invoked for the first time might be supported.

As such, since this plugin can not change the javac parser, only the subsequently generated Abstract Syntax Tree, I resorted to annotation to achieve this purpose. The annotation is @Persistent (arguably not the best name, but had no better idea...).


##Requirements

- openJDK8 javac (since we need the -Xplugin:... flag)
- java 1.8 (recommended)
- java 1.5+ (you can compile with javac8 to lesser versions using -source 1.X -target 1.X . I managed to compile and run the test using -source 1.5 -target 1.5, and it's the minimum possible since you need 1.5 to support annotations. Even tho it runs correctly and the tests show up correct, I didn't develop with 1.5 in mind so beware...)


## How to use

You need to add the plugin *.jar* to the classpath using *-cp* and then request the usage of the plugin with *-Xplugin:Persistent_Plugin*. If everything is correct and the plugin is running, *"Plugin Running!"* should be outputed by javac.

    javac8 -cp .\persistentplugin-0.0.1-SNAPSHOT.jar -Xplugin:Persistent_Plugin *.java


## Example of usage

#### ...in static functions

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


#### ...in non-static functions

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


## Tested

- static methods
- non-static methods
- static methods in inner classes
- non-static methods in inner classes

#### ...not tested

- lambdas/closures with @Persistent variables
- annoynimous classes


## Future features / wishlist?

- See if it works on lambdas/closures and annonymous classes
