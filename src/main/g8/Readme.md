# $name;format="Camel"$ 

The `apidoc` definitions are documented in `conf/apidocDefinitions` and in `app/controllers/UserControllers.scala` files.
And the configuration in the `build.sbt` has been tweaked in order to serve the generated documentation.  
You can get more information on this [page][sbt-apidoc].

The different endpoint are documented under `/apidoc/index.html`, just run:
>sbt run

And open http://localhost:9000/apidoc/index.html in your browser.

[sbt-apidoc]: https://github.com/valydia/sbt-apidoc
