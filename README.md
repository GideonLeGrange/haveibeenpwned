# haveibeenpwned
A Java API for the account and password services provided by [';--have i been pwned?](https://haveibeenpwned.com)

This API provides an easy way of accessing the account and password verification services for https://haveibeenpwned.com. The user can check if accounts appear in any of the compromise datasets or if a password is known to be compromised. 

# Versions

[![Build Status](https://travis-ci.org/GideonLeGrange/haveibeenpwned.svg?branch=master)](https://travis-ci.org/GideonLeGrange/haveibeenpwned)

It brings in support for using an API key as per [this documentation](https://haveibeenpwned.com/API/v3)

**The current version is 3.0**

It brings in support for using an API key as per [this documentation](https://haveibeenpwned.com/API/v3)

**Version 2.0 is still avaialble for download from GitHub and in Maven Central **

Version 2.0 brings the following changes: 
* The API supports padding for pwnd passwords (see https://www.troyhunt.com/enhancing-pwned-passwords-privacy-with-padding/)
* A builder pattern is added in place of the constructor to allow for configuration of the following:
  * The User-Agent (was configured as part of the constructor)
  * If padding (as per above) should be added 
  * A HTTP proxy with which to do the API calls 
  * The URL to use for pwnd passwords (advanced feature, don't use)
  * The URL to use for breach data (advanced feature, don't use)
* The builder pattern must be used 

**Version 1.1 is still avaialble for download from GitHub and in Maven Central**

  
# Getting the API

Maven users can use the artifact from Maven Central with this dependency:

```xml
<dependency>
  <groupId>me.legrange</groupId>
  <artifactId>haveibeenpwned</artifactId>
  <version>2.0</version>
</dependency>
```

You can also download the pre-built jar file, or a zip or tar.gz file with the source for the latest release [here](https://github.com/GideonLeGrange/haveibeenpwned/releases/latest)

# Using the API

The purpose of the API see if an account (email address) or password has been listed as compromised on [';--have i been pwned?](https://haveibeenpwned.com)
To use the API you need to instantiate an instance of it, and then call one of the methods. I can't be simpler:

```java
HaveIBeenPwndApi hibp = HaveIBeenPwndBuilder().create().build();
```

But if you wish to configure the API, `HaveIBeenPwnedBuilder` allows you to set if you wish to add padding to pwnd password checks [see this blog for details](https://www.troyhunt.com/enhancing-pwned-passwords-privacy-with-padding/),
what user-agent to use, and it even allows you to change the pwnd password and breach URLs to use (for testing, don't do this).

For example, here we enable padding and set the user-agent:

```java
        HaveIBeenPwndApi hibp = HaveIBeenPwndBuilder.create()
                .addPadding(true)
                .withUserAgent("Pwn-Checker-1.0")
                .build();
```


In the following examples we assume the API has been instantiated and is called ```hibp```. 

## See if a specific account or password has been breached

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

## Retrieving breach data 

You can retrieve detailed information on breaches by calling ```getAllBreachesForAccount()```. For example:

```java 
List<Breach> breaches = hibp.getAllBreachesForAccount("youremail@goes.here");
System.out.printf("You've been breached %d times\n", breaches.size());
breaches.forEach(System.out::println);
```

There is an overloaded version of ```getAllBreachesForAccount``` that allows for further fine tuning of results. Refer to the
source code and ';--have i been pwned? API documentation how this works. 

## Retrieving password hashes 

Most users should probably be happy to determine if a password is listed as compromised, but the actual data returned by the API 
can be obtained by calling ```searchByRange()``` Refer to the source code and ';--have i been pwned? API documentation to understand how this works. 

# References

* Be sure to visit https://haveibeenpwned.com and https://troyhunt.com 
* The ';--have i been pwned? API is documented here: https://haveibeenpwned.com/API/v2

# Licence

This library is released under the Apache 2.0 licence. See the [LICENSE](LICENSE) file
