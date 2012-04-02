[![][logo]][website]

Patternity
--------

Declare design intents in your code, and let tools automate your work


Abstract
--------

Declare all your design decisions (class stereotypes, responsibility layers, patterns...) in your code using annotations. 

Tools can then exploit these design declaration to perform code checks and verify allowed dependency according to predefined rules. Tools may also extract acccurate design documents, among other things.

Features
--------

Very first version, only checks one dependency rule at the moment:

- ValueObject classes must not reference any Entity


It is just a Maven plugin to add to your pom.

Roadmap
-------

Patternity wants to provide a comprehensive set of predefined annotations and their associated rules and tooling, that can then be extended with your own.


License
-------

Patternity is licenced under the Apache Software Licence v2.0.  
See http://www.apache.org/licenses/LICENSE-2.0 for the text of the licence.

Please feel free to contribute and use it.


[logo]: https://github.com/cyriux/Patternity/raw/master/patternity-maven-plugin/logo.png
[website]: https://github.com/cyriux/Patternity

