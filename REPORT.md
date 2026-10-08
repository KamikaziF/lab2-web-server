# Lab 2 Web Server -- Project Report

## What I specified

The main things I've decided to add was the error page with the status and path, the `time` path that returns the time of the machine, with the ability to define the timezone you want that time and making so the website works with http/s over TLS. To test the first 2, apart from just visiting the website to see if it works, I've also added new tests to ensure the behavior isn't changed.
For the last one, this requires manual verification

## What I changed

For the first feature, I've added the following files
```
/src/main/resources/templates/error.html
/src/test/kotlin/es/unizar/webeng/lab2/ErrorPageTest.kt
src/test/resources/application.yml
```
These files are only for adding the error page and the test

For the second path, these files were added:
```
/src/main/kotlin/es/unizar/webeng/lab2/TimeDTO.kt
/src/test/kotlin/es/unizar/webeng/lab2/TimeControllerTest.kt
```
This added the logic to obtain the time and declaring a path to return it to the client, as well as the test to test the feature

For the last one, these files were added
```
src/main/resources/application.yml
/src/main/resources/localhost.p12
```
There are also additional files created for the creation of the certificate, but are not shared due to security reasons

## Technical decisions

For the error page, I've decided to do a simple yet clear page, showing mainly the error status, the reason and the route you tried to access. The test for it required little modification from the provided one, just adding checks to ensure the status and path was given correctly.
Meanwhile, `/time` required additional modifications to the function as I've decided that it can accept zones and would return the time at that zone. To allow this behavior, I've added a parameter to the method of `now()` which is the zone, as well as making the controller have a parameter which the user can introduce to the URL path. The test has `TimeProvider` to set a fixed time, as well as having a predefined zone.
Finally, the HTTP/2 feature was done as the guide recommended, just adding the additional features of adding IP:127.0.0.1 and passing the name of the certificate to the alias.

## How I verified

While implementing each feature, `./gradlew check` was run to ensure every was running correctly, as well as checking manually to see if there is any visual issue. 
When I was changing the tests for the `/time`, it was failing because the conversion of the LocaleDateTime conversion to string was ignoring the seconds, while Springboot would always include the seconds, causing the test to fail. The fix was quite straightforward, adding a value to seconds so the string would be complete.
After implementing HTTP/2 over TLS, I've ran the `curl` commands to ensure everything was working, as well as accessing the website through https. Then I've ran `./gradlew check` and noticed that it failed in `ErrorPageTest.kt` because it couldn't connect trough TLS. After a lot of debugging, I've noticed `application.yml` file was misspelled as `applications.yml`. After fixing the typo, the tests passed successfully

## AI disclosure

- **Tools / skills:** Gemini Thinking 3.6
- **Purpose:** Generate HTML and helping with tests and debugging
- **Representative prompts:** "Generate the HTML for a simple error page", "The test is throwing this error", "How does ZoneTd work"
- **Affected files/sections:** error.html, ErrorPageTest.kt, TimeControllerTest.kt, TimeDTO.kt
- **Validation steps:** First I've read if the suggestions or code made sense, then ran `./gradlew check` to see if it works as intended. For the HTML, I've visually checked if it looked okay.
- **Citations:**: Some of the sources used to help me out in the making of this lab:
  - https://docs.oracle.com/javase/8/docs/api/java/time/ZoneId.html
- **Human-reviewed:** * Before integrating the code into the program, I've first checked if the code made sense and adapted correctly to the code I've had at that moment

