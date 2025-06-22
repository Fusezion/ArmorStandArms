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
in addition to a custom tags like `<version>` and `<config:...>`

### Version Tag
Use this tag by simply supplying `<version>` in any of the messages.properties

### Config Tag
This tag is a bit more involved but allows free flexibility when existing config values (custom not supported) are provided.

The can be split between two types a simple one of `<config:number|integer|boolean|string:path>`,
an example would be `<config:boolean:debug-mode>` this returns a true/false value for if debug is enabled

However boolean type has one extra additional option which can be cut down to `<config:boolean:path:yes:no>`
if you have a custom value you want shown depending on them you can simply do `<config:boolean:debug-mode:'<#00ff00>YEZ':'<#ff0000>NOOOO'>`
and it'll format the result based on the input. This uses a mini message serializer too so everything should be supported.