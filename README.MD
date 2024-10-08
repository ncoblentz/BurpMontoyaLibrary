# MontoyaLibrary

_By [Nick Coblentz](https://www.linkedin.com/in/ncoblentz/)_

__The Montoya Library is made possible by [Virtue Security](https://www.virtuesecurity.com), the Application Penetration Testing consulting company I work for.__

## About

The __Montoya Library__ provides convenient features to help make building Burp Suite plugins a joy.

## How to Use It

Add the following to your `build.gradle.kts`
```gradle

//add the jitpack.io repository shown below (in addition to any others you require
repositories {
    mavenCentral()
    maven(url="https://jitpack.io") {
        content {
            includeGroup("com.github.milchreis")
            includeGroup("com.github.ncoblentz")
        }
    }
}

//add the following dependencies
dependencies {
    //other dependencies here
    implementation("com.github.ncoblentz:BurpMontoyaLibrary:0.1.27")
    implementation("com.github.milchreis:uibooster:1.21.1")
}
```

## Features

### Logger

Log Levels Supported:
- `DebugLogLevel`
- `WarnLogLevel`
- `ErrorLogLevel`

Example Usage:
```kotlin

private lateinit var Api: MontoyaApi

class MontoyaKotlinSessionAccessTokenHelper : BurpExtension {
    override fun initialize(api: MontoyaApi?) {
        
        Logger = MontoyaLogger(api, MontoyaLogger.DebugLogLevel)
        Logger.debugLog("Plugin Starting...")
        // various extension setup tasks here
        Logger.debugLog( "Finished")
    }
}
```

### Burp Project and Preference Level Persistence and UI Supporting it

This library can be built (`gradlew shadowJar`) and then added as an extension in Burp Suite. It will execute the following plugin: https://github.com/ncoblentz/BurpMontoyaLibrary/blob/master/src/main/java/com/nickcoblentz/montoya/settings/SettingsTest.java. That plugin demonstrates how to create Persistence Settings that are automatically supported by a User Interface. That user interface will be displayed when initially loading the plugin. It will also show by right-clicking an HTTP request and selecting Extensions &gt; Test Settings &gt; Settings. Another example of an extension using this library may be found at: https://github.com/ncoblentz/MontoyaKotlinSessionAccessTokenHelper 
> Note: if you wish to use this library directly as an extension in Burp Suite (to try it out), uncommen the code here first: https://github.com/ncoblentz/MontoyaKotlinSessionAccessTokenHelper. If you are simply using the library within your own extension, you do not need to take any action.

All Settings support either Project or Preference level persistence. The following settings types are supported:
- `Boolean`
- `Byte`
- `Integer`
- `Short`
- `Long`
- `String`
- `List<String>`

Example:

```kotlin
package com.nickcoblentz.montoya.settings;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.persistence.PersistedList;
import com.nickcoblentz.montoya.MontoyaLogger;
import de.milchreis.uibooster.model.Form;
import de.milchreis.uibooster.model.FormBuilder;
import java.util.List;

public class SettingsTest implements BurpExtension {
    private MontoyaApi Api;

    private static final String PluginName = "Test Settings";
    @Override
    public void initialize(MontoyaApi api) {
        Api=api;
        MontoyaLogger logger = new MontoyaLogger(api,MontoyaLogger.DebugLogLevel);
        logger.debugLog("Loading Plugin");
        api.extension().setName(PluginName);
        //ExtensionSetting testSetting1 = new ExtensionSetting(api,"Test1","montoyalibrary.test1","the value here!", ExtensionSettingSaveLocation.PROJECT);
        //ExtensionSetting testSetting2 = new ExtensionSetting(api,"Test2","montoyalibrary.test2","another value here!", ExtensionSettingSaveLocation.PREFERENCE);
        StringExtensionSetting testSetting1 = new StringExtensionSetting(api,"String Test1","montoyalibrary.test1","the value here!", ExtensionSettingSaveLocation.PROJECT);
        StringExtensionSetting testSetting2 = new StringExtensionSetting(api,"String Test2","montoyalibrary.test2","another value here!", ExtensionSettingSaveLocation.PREFERENCE);
        ByteExtensionSetting testSetting3 = new ByteExtensionSetting(api,"Byte Test","montoyalibrary.test3",Byte.valueOf((byte)36), ExtensionSettingSaveLocation.PREFERENCE);
        BooleanExtensionSetting testSetting4 = new BooleanExtensionSetting(api,"Boolean Test","montoyalibrary.test4",Boolean.valueOf(true),ExtensionSettingSaveLocation.PROJECT);
        IntegerExtensionSetting testSetting5 = new IntegerExtensionSetting(api,"Integer Test","montoyalibrary.test5",Integer.valueOf(3),ExtensionSettingSaveLocation.PROJECT);
        LongExtensionSetting testSetting6 = new LongExtensionSetting(api,"Long Test","montoyalibrary.test6",Long.valueOf("9223372036854775807"),ExtensionSettingSaveLocation.PROJECT);
        ShortExtensionSetting testSetting7 = new ShortExtensionSetting(api,"Short Test","montoyalibrary.test7",Short.valueOf((short)123),ExtensionSettingSaveLocation.PROJECT);
        ListStringExtensionSetting testSetting8 = new ListStringExtensionSetting(api,"List String Test","montoyalibrary.test8",List.of("Test 1","test 2", "test 3"),ExtensionSettingSaveLocation.PREFERENCE);

        List<GenericExtensionSetting> extensionSetting = List.of(testSetting1,testSetting2,testSetting3,testSetting4,testSetting5,testSetting6,testSetting7,testSetting8);
        GenericExtensionSettingsFormGenerator gen = new GenericExtensionSettingsFormGenerator(extensionSetting,PluginName);
        FormBuilder settingsFormBuilder = gen.getSettingsFormBuilder();
        Form settingsForm = settingsFormBuilder.run();
        api.userInterface().registerContextMenuItemsProvider(new ExtensionSettingsContextMenuProvider(api,settingsForm));
        logger.debugLog("Finished");
    }
}

```

![img.png](docs/examplesettingsui.png)

### Annotations - Append Notes

In Burp Suite, notes can be set on a particular HTTP request/response pair, but it replaces the entire note. This feature concatenates the previous notes with a new note:

Example:
```kotlin
override fun handleRequestToBeSent(interceptedRequest: InterceptedRequest?): ProxyRequestToBeSentAction {
    //some processing here
    interceptedRequest?.annotations().appendNotes("Some Note here")
    //finish processing
    return ProxyRequestToBeSentAction.continueWith(interceptedRequest)
}
```


### HttpRequest
#### Update Content Length Header and Send With Updated Content Length Header

There are the following new methods that update the "Content-Length" header. One method is a standalone update. The others send the request, but before sending it, it updates the header.

```kotlin
//example 1
httpRequest.withUpdatedContentLength() //update the content length header if already present
//example 2
httpRequest.withUpdatedContentLength(true) //add a content length header if not present
//example 3
api.http().sendRequestWithUpdatedContentLength(httpRequest,RequestOptions.requestOptions().withRedirectionMode(RedirectionMode.ALWAYS))
//example 4
api.http().sendRequestWithUpdatedContentLength(httpRequest)
```

#### Update a ParsedHttpParameter - useful for not GET/POST/Cookie/Header Values (JSON, XML, etc...)

Supported Insertion Modes:
```kotlin
enum class PayloadUpdateMode {
    REPLACE,
    PREPEND,
    INSERT_MIDDLE,
    APPEND
}
```
Example usage
```kotlin
for (parameter in currentRequest.parameters()) {
    if(parameter.type()== HttpParameterType.JSON)
        val modifiedRequest = currentRequest.withUpdatedParsedParameterValue(parameter, "", PayloadUpdateMode.REPLACE)
}
```

#### Update or Add a Header - Useful for not worring about if the header already exists

```kotlin
modifiedRequest = modifiedRequest.addOrUpdateHeader("Authorization","Bearer $token")
```

#### Update or Add a Cookie - Useful for not worring about if the header already exists

```kotlin
modifiedRequest = modifiedRequest.addOrUpdateCookie("token",token)
```

### Collaborator

#### Polling Helper and Interaction Filter

This feature will create a collaborator payload and provide the address through the Settings UI widget (Right-Click -> Extensions -> Collab Helper -> Settings). It will then begin to poll for interactions every 5 seconds. You can pass a lambda to the class to be notified of any interactions and handle it. One example is found at: https://github.com/ncoblentz/KotlinBurpCollabHelper/blob/master/src/main/kotlin/KotlinBurpCollabHelper.kt

Example code:
```kotlin
class KotlinBurpCollabHelper : BurpExtension {
    ...
    private lateinit var collabHelper: CollabHelper
    override fun initialize(api: MontoyaApi?) {
        requireNotNull(api) { "api : MontoyaApi is not allowed to be null" }

        ...

        // This creates or restores a CollaboratorClient and starts polling right away for interactions
        collabHelper = CollabHelper(api)
        
        // Builder pattern for filtering the interactions for the type of events you want, and providing a callback to act upon those filtered interactions
        val collabFilter = CollabHelperInteractionFilter()
            .withInteractionType(InteractionType.HTTP)
            .withHttpRequestPattern("myregexsearchvaluehere") // Consider using a setting that can be updated from the UI like in the example KotlinBurpCollabHelper.kt file
            .withInteractionHandler(::handleInteraction) // You must include this part, otherwise it won't call a callback. Point it at the handler inside of your own extension
        
        // Register the Filter's callback with collabHelper. It will then call your callback above
        collabHelper.interactionObservers.add(collabFilter::handleInteraction)
        
        val extensionSetting = mutableListOf(.. your settings here ...)
        extensionSetting.addAll(collabHelper.extensionSettings)
        val gen = GenericExtensionSettingsFormGenerator(extensionSetting, PLUGIN_NAME)

        val settingsFormBuilder: FormBuilder = gen.getSettingsFormBuilder()
        val settingsForm: Form = settingsFormBuilder.run()

        // Tell Burp we want a right mouse click context menu for accessing the settings
        api.userInterface().registerContextMenuItemsProvider(ExtensionSettingsContextMenuProvider(api, settingsForm))

        // When we unload this extension, include a callback that closes any Swing UI forms instead of just leaving them still open
        api.extension().registerUnloadingHandler(ExtensionSettingsUnloadHandler(settingsForm))

        ...
    }

    ...
    
    private fun handleInteraction(interaction: Interaction) {
        api.logging().logToOutput("A match was found!")
        // Only interactions matching the filter above will reach this point
        // do something with the interaction here
    }
}
```