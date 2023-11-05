# **NametagHandler**

Hello there! Welcome to the NametagHandler, the tool that simplifies the process of changing a player's nametag (the name displayed above their head) in Minecraft plugins.

### **INSTALLATION**

To get started with the NametagHandler for your Minecraft plugin, follow these simple steps:

**1. 🔗 Download the latest JAR file of the NametagHandler on [RELEASE](https://github.com/Meruhz/NametagHandler/releases) section;**

**2. 📦 Add the downloaded JAR file to your plugin project's source code. Then, read the API documentation provided with the downloaded module source, and include the API in your Maven project by adding the following to your pom.xml file:**

```html
<dependency>
    <groupId>codes.meruhz.nametag</groupId>
    <artifactId>api</artifactId>
    <version>1.0-SNAPSHOT</version>
    <scope>compile</scope>
</dependency>
```
**3. 📚 After reloading Maven projects, you can import the NametagHandler library into your Java code for your Minecraft plugin development.**

### **DEVELOPERS**

If you want to customize the nametag functionality to your liking, you can do this in two ways:

1. Implement the base interface:

```java
public class CustomNametagApi implements NametagApi {

    // Implement all required methods and custom logic
}
```
2. Extend the abstract class **(recommended)**:

```java
public class CustomNametagApi extends AbstractNametagApi {

    // Override essential methods to customize nametag handling
}
```

### **HOW TO USE IT**

Prior to proceeding, it is imperative that you possess an unwavering sense of confidence and certainty, as any erroneous action could potentially result in a catastrophic error. Altering a player's nametag is a highly intricate task that demands both skill and an extensive understanding of the subject. Please adhere to the provided code below:

```
NametagHandler.handler().getNametagApi().setNametag(player.getUniqueId(), Nametag.of(ChatColor.GRAY, TextComponent.fromLegacyText("§c§lADMIN "), null));
```

Difficult, right? "But if I want to set the default name tag?" It's even more complicated! Just set the second parameter to null.

```
NametagHandler.handler().getNametagApi().setNametag(player.getUniqueId(), null);
```

All the methods you will need are in the API. You can access it using:

```
NametagHandler.handler().getNametagApi();
```

## CONTRIBUTIONS

**We would love your contributions to make this API even better. Feel free to submit pull requests, report issues, or suggest improvements. Your contribution is highly valued!**

## LICENSE

**This API is licensed under the MIT License. For all the details, please refer to the LICENSE file included in the API package.**

## CONTACT

**If you have any questions or need assistance, don't hesitate to get in touch with us! You can reach us via:**

✉️ **Email: [meruhz.contato@gmail.com]()**

👤 **Discord: [@meruhz]()**