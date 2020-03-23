## Embedded Vaadin

A library that handles setting up Vaadin 14 in an embedded Jetty environment

## How do I include it in my Gradle project?

1. Add the jitpack repo to the repositories section

    ```
    maven { url 'https://jitpack.io' }
    ```

2. Add the dependency version [(replace x.y.z with the appropriate version from the JitPack site)](https://jitpack.io/#timmattison/embedded-vaadin)

    ```
    def embeddedVaadin = 'x.y.z'
    ```

3. Add the dependency to the dependencies section

    ```
    compile "com.github.timmattison:embedded-vaadin:$embeddedVaadin"
    ```

## How do I use it?

Examples coming soon...

## License

This library is licensed under the MIT license.
