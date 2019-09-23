# Five Hundred Pixels

This project uses the 500px API to show photos in a list with a click-thru details page.
Each commit has a description of what was incrementally added and things that I felt were worth pointing out to be noticed.

It is broken up into several modules to better organize and encapsulate things like resources, as well as to
better define the boundaries of the project's dependencies.

The project has by no means full test coverage, but I have added some to the `photoshowcase` module. These use the
Espresso API and various other test APIs to simulate different states that the list of photos can find itself in.

The project will not compile until a `keys.properties` file is added to the root folder, with a valid 500px consumer
key in the following format:

`five_hundred_pixels_api_key = "XXXXXXXXXXXXXXXXXXXXXXXXXX"`
