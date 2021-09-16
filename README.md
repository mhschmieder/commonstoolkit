# iotoolkit
Placeholder for an evolving toolkit of I/O helper utilities and extensions for file types, mostly removed of any redundancies with Apache Commons I/O.

This toolkit has now been heavily fleshed out to include almost anything I would put in a commons suite, so the overall name may change soon as it is no longer I/O centric. I also reserve the right to delete entire classes, or member variables and methods, after further review for the appropriate level of abstraction and generality across domains. Right now, there is still a bit of focus on the lowest common denominators for scientific visualization, math, and physics.

The current code has not been compiled in its present state, so typos may have been made while generalizing earlier versions of the code that were in a different repository, and there may be some external dependencies not yet included in the Maven POM file.

I needed to rush this code onto GitHub as my current job is in a secure environment and can't allow USB thumb drives for quick copies of public open source code. I tried to make sure all the new package names and references are correct, at the very least, and added some comments about potentially decoupling some parts from external dependencies such as JavaFX, Apache Commons, etc.

As the overall file set is still fairly small, I am disinclined to break this up into multiple libraries at the moment, but may rename it to be more general.
