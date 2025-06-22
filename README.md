# ArmorStandArms

This plugin literally just adds arms onto armor stands. I've might have went a bit overboard for the joke but hey it works!

This plugin was thought up after talking to someone and them saying they could upload a 2 line [Skript](https://github.com/SkriptLang/Skript) to add arms to armor stands.

```
on spawn of armor stand:
    entity.setArms(true) # option 1 (Skript-Reflect)
    add nbt from "{ShowArms:1b}" to nbt of entity # option 2 (SkBee)
```

However, only adding that didn't seem like a good plugin, and felt overly basic so instead I added more there's commands, config and now message properties.

## Command - /armorstand toggle
Toggle adding arms to armor stand on place by doing
- Permission: `armorstand.command.toggle`

## Command - /armorstand info
Get an info message of the plugin. Why? zero clue just wanted to add it.
- Permission: `armorstand.command.info`

## Command - /armorstand reload
Reloads the config and message.properties of the plugin. Great for changing messages to your liking!
- Permission: `armorstand.command.reload`

# messages.properties
This plugin includes a messages.properties if you want to change the messages to meet you're needs
you can simply just change them in the file and restart or do `/armorstand reload` to quickly update them.

Everything in the `messages.properties` is loaded via minimessages, with full support for the default tags,
in addition to a custom `<version>` tag being added to allow easy access to the plugin's version.