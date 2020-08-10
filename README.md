# Mobile automated regression checks

## Characteristics
- Configuration by using resources file.
  - Checks configuration
  - Logging configuration
  - Suite configuration in testng.xml file
- Integration with JIRA
- The checks will not run if there is not an Appium server running. This condition is programmatically verified.

## Requirements
- Android emulator (or a real device)
- iOS simulator (checks not tested on a real iOS device)
- Change configuration paths as needed:
  - appium.and.app.apk
  - appium.ios.app.app
  - visualchecks.baseline.path
  
## Execution
- Start Appium server
- Configure the mobile platform to run the checks:
  - appium.automation.name (Android|iOS)
- Launch tests (using the IDE or mvn)

## Available checks
The available checks are divided in four different categories
### Content checks
These checks verify the app UI components texts
### Navigation checks
These checks verify the app navigation possibilities
### Functional checks
These checks verify the app functional behaviour
### Visual checks
These checks verify the app UI components using screenshots.
The visual checks is executed against a "baseline" image collection.
This baseline collection is created the first time the visual checks are run (so the first run will not check anything)
If a comparison fails, an image with the differences is saved. Also a new Jira ticket is created

#### Configuration
The image comparison algorithm uses a threshold configured in the properties key:
-  visualchecks.match.threshold

The directories for the baseline and failed image comparisons are configured in the following properties keys:

- visualchecks.baseline.path
- visualchecks.baseline.prefix

#### Note
The two screen selected to run the visual checks shows us an important lesson:

- A screen with no (or very simple images) give excellent checking results
- A screen with many images (photo demo has 6 photographs) gives false positives, so the threshold value should be set by experimenting with the app until a valid result is found.

 #### JIRA ticket creation
 The Jira ticket creation is optional and needs two environment variables:
 - JIRA_USER
 - JIRA_TOKEN
 If this environment variables are not set, the tickets won´t be created.