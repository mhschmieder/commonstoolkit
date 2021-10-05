# CommonsToolkit
An evolving toolkit of common utilities in several categories, mostly removed of any redundancies with Apache Commons libraries, upon which this library depends.

As the overall file set is still fairly small, I am disinclined to break this up into multiple libraries at the moment, and also reserve the right to delete methods and classes as I continue to evaluate better alternatives that already exist in various Apache Commons libraries and elsewhere.

Note that some of this code may become unnecessary before long, as there is an existing JSR for Units and Unit Conversion, but I haven't had a chance to re-find it and check its status recently. I also need to add more units than the ones included here, which are the most common ones but aren't complete for all applications.

Until I publish to Maven Central or elswehere, I am attempting to provide pre-built JAR files for binary builds, source code, and Javadocs.

There has been yet another shift in how/where the proxy-vole library is published, so I will try to find time to upgrade that dependency library soon.
