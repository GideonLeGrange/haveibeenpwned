# haveibeenpwned
A Java API for the account and password services provided by [';--have i been pwned?](https://haveibeenpwned.com)

This API provides an easy way of accessing the account and password verification services for https://haveibeenpwned.com. The user can check if accounts appear in any of the compromise datasets or if a password is known to be compromised. 

# Versions

![Java CI with Maven](https://github.com/GideonLeGrange/haveibeenpwned/workflows/Java%20CI%20with%20Maven/badge.svg)

It brings in support for using an API key as per [this documentation](https://haveibeenpwned.com/API/v3)

**The current version is 3.0.1**

It cleans up code around the User-Agent and corrects some documentation mistakes, while version 3.0 
brought in support for using an API key as per [this documentation](https://haveibeenpwned.com/API/v3)

**Version 2.0 is still available for download from GitHub and in Maven Central**

Version 2.0 brings the following changes: 
* The API supports padding for pwnd passwords (see https://www.troyhunt.com/enhancing-pwned-passwords-privacy-with-padding/)
* A builder pattern is added in place of the constructor to allow for configuration of the following:
  * The User-Agent (was configured as part of the constructor)
  * If padding (as per above) should be added 
  * A HTTP proxy with which to do the API calls 
  * The URL to use for pwnd passwords (advanced feature, don't use)
  * The URL to use for breach data (advanced feature, don't use)
* The builder pattern must be used 

**Version 1.1 is still available for download from GitHub and in Maven Central**

  
# Getting the API

Maven users can use the artifact from Maven Central with this dependency:

```xml
<dependency>
  <groupId>me.legrange</groupId>
  <artifactId>haveibeenpwned</artifactId>
  <version>3.0.1</version>
</dependency>
```

You can also download the pre-built jar file, or a zip or tar.gz file with the source for the latest release [here](https://github.com/GideonLeGrange/haveibeenpwned/releases/latest)

# Using the API

The purpose of the API is to see if an account (email address) or password has been listed as compromised on [';--have i been pwned?](https://haveibeenpwned.com)
To use the API you need to instantiate an instance of it, and then call one of the methods. I can't be (much) simpler:

```java
HaveIBeenPwndApi hibp = HaveIBeenPwndBuilder().create("Your-User-Agent").build();
```

But if you wish to configure the API, `HaveIBeenPwnedBuilder` allows you to set if you wish to add padding to
pwnd password checks [see this blog for details](https://www.troyhunt.com/enhancing-pwned-passwords-privacy-with-padding/),
and it allows you to change the pwnd password and breach URLs to use (for testing, don't do this). You can 
also set HTTP Proxy to use. 

For example, here we enable padding:

```java
        HaveIBeenPwndApi hibp = HaveIBeenPwndBuilder.create("Your-User-Agent")
                .addPadding(true)
                .build();
```

## Specifying the User-Agent 

The API requires the user to specify their User-Agent on creation. While I originally had a default User-Agent 
in the library, the `';--have i been pwned?`website states the following:

>The user agent should accurately describe the nature of the API consumer such that it can be clearly 
>identified in the request. Not doing so may result in the request being blocked.

Therefore, `HaveIBeenPwnedBuilder` forces the user to pass a User-Agent string in the `create()` call to
distinguish their application from others. 

## Examples 

In the following examples we assume the API has been instantiated and is called ```hibp```. 

### See if a specific account or password has been breached

To test if an account appears in breached data, do this:

```java
boolean pwned = hibp.isAccountPwned("youremail@goes.here");
System.out.printf("That email account %s pwned!\n", (pwned ? "is" : "isn't"));
```

To test if a password appears in breached data, try this:

```java 
boolean pwned = hibp.isPlainPasswordPwned("123456");
System.out.printf("That silly password %s pwned!\n", (pwned ? "is" : "isn't"));
```

Note that, even though the password is supplied in plain text, it is not transmitted to the remote API. The
password is SHA1 encoded, and only the first five digits of the SHA1 encoded data is sent through the network. 
Even this is encrypted in transmission. 

### Retrieving breach data 

You can retrieve detailed information on breaches by calling ```getAllBreachesForAccount()```. For example:

```java 
List<Breach> breaches = hibp.getAllBreachesForAccount("youremail@goes.here");
System.out.printf("You've been breached %d times\n", breaches.size());
breaches.forEach(System.out::println);
```

There is an overloaded version of ```getAllBreachesForAccount``` that allows for further fine tuning of results. Refer to the
source code and ';--have i been pwned? API documentation how this works. 

### Retrieving password hashes 

Most users should probably be happy to determine if a password is listed as compromised, but the actual data returned by the API 
can be obtained by calling ```searchByRange()``` Refer to the source code and ';--have i been pwned? API documentation to understand how this works. 

# References

* Be sure to visit https://haveibeenpwned.com and https://troyhunt.com 
* The ';--have i been pwned? API is documented here: https://haveibeenpwned.com/API/v2

# Licence

This library is released under the Apache 2.0 licence. See the [LICENSE](LICENSE) file
