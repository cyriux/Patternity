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
We use [trello] for our roadmap, and [ideascale] to nurture ideas before they reach the roadmap; don't hesitate to join and contribute!

Continuous Integration
----------------------
Our continuous integration server is a [jenkins] thanks to [cloudbees]

License
-------

Patternity is licenced under the Apache Software Licence v2.0.  
See http://www.apache.org/licenses/LICENSE-2.0 for the text of the licence.

Please feel free to contribute and use it.


[logo]: https://github.com/cyriux/Patternity/raw/master/logo.png
[website]: https://github.com/cyriux/Patternity
[trello]: https://trello.com/b/WU8weVd0
[ideascale]: http://patternity.ideascale.com/
[jenkins]: https://patternity.ci.cloudbees.com
[cloudbees]: http://www.cloudbees.com/
