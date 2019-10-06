# Five Hundred Pixels

This project uses the 500px API to show photos in a list with a click-thru details page.

It is broken up into several modules to better organize and encapsulate things like resources, as well as to
better define the boundaries of the project's dependencies.

The project has by no means full test coverage, but I have added some to the `photoshowcase` module. These use the
Espresso API and various other test APIs to simulate different states that the list of photos can find itself in.

The project uses the FragmentFactory API to make Fragment injection easy using dagger, coroutines to fetch its content, and most of the current Android Architecture Components.
