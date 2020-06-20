---
layout: page
title:  Configuration  
---


To use `scm` in your own code or in scripts, import `edu.holycross.shot.scm._`.  `scm` is available from jcenter, so if you are using sbt, include jcenter in your list of resolvers like this;

    resolvers += Resolver.jcenterRepo


and add `scm` to your list of dependencies

    libraryDependencies +=    "edu.holycross.shot" %%% "scm" % "VERSION"


Depending on what features of `scm` you use, you may also need to import one or more of the following libraries, all of which are available from jcenter:


    libraryDependencies ++= Seq(
      "edu.holycross.shot.cite" %%% "xcite" % "VERSION", // CTS and CITE URN classes
      "edu.holycross.shot" %%% "ohco2" % "VERSION",  // manipulation of OHCO2 texts
      "edu.holycross.shot" %%% "citeobj" % "VERSION", // CITE Collections
      "edu.holycross.shot" %%% "cex" % VERSION" // parser for CEX data
    )

>**Hint**: you can check the most recent published version of each library from the maven repository listing [here](https://bintray.com/neelsmith/maven).

On the JVM platform, converting local text repositories to CEX format or to `CiteLibrary` objects requires the `hocuspocus` java library, available from the nexus repository at beta.hppc.uh.edu.  That is, `import  edu.holycross.shot.hocuspocus._` in your code, and add this resolver to your build file:

    resolvers += "beta" at "http://beta.hpcc.uh.edu/nexus/content/repositories/releases"

In your list of depedencies in your build.sbt, add version 2.1.5 of the library:

        libraryDependencies +=   "edu.holycross.shot" % "hocuspocus" % "2.1.5"
