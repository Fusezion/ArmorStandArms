package me.lyric.asa

import me.lyric.asa.ArmorStandPlugin.Companion.instance
import me.lyric.asa.ArmorStandPlugin.Companion.pluginLogger
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.text.serializer.legacy.CharacterAndFormat

object AdventureUtils {

	val config: ArmorStandConfig
		get() = ArmorStandPlugin.armorStandConfig

	@Suppress("DEPRECATION") /* Paper Deprecation of 'description' (plugin meta) */
	val VERSION_TAG: Tag = Placeholder.component("version", Component.text(instance.description.version)).tag()

	val CONFIG_TAG: TagResolver = TagResolver.resolver("config") { arguments, context ->
		if (!(arguments.hasNext())) throw context.newException("config tag is missing a config type argument", arguments)
		val type = arguments.pop().lowerValue()
		if (!arguments.hasNext()) throw context.newException("config tag is missing a path argument value", arguments)
		val path = arguments.pop().value()
		if (!config.config.contains(path)) throw context.newException("config tag was provided an invalid path of '$path'", arguments)
		val tagValue = when (type) {
			"string" -> Tag.inserting(MINI_MESSAGE.deserialize(config.config.getString(path)!!))
			"number" -> Tag.inserting(Component.text(config.config.getDouble(path)))
			"integer" -> Tag.inserting(Component.text(config.config.getInt(path)))
			"boolean" -> {
				val boolean = config.config.getBoolean(path)
				if (arguments.hasNext()) {
					val optionYes = arguments.pop().value().let { MINI_MESSAGE.deserialize(it) }
					if (!(arguments.hasNext())) throw context.newException("config tag was provided a boolean option for one of two outputs, please define one more.", arguments)
					val optionNo = arguments.pop().value().let { MINI_MESSAGE.deserialize(it) }
					return@resolver Tag.inserting(if (boolean) optionYes else optionNo)
				}
				return@resolver Tag.inserting(Component.text(boolean))
			}
			else -> throw context.newException("'$type' is not a valid config type of 'number', 'integer', 'boolean' or 'string'", arguments)
		}
		return@resolver tagValue
	}

	val MINI_MESSAGE: MiniMessage
		get() = MiniMessage.builder().apply {
			debug { if (config.debug) pluginLogger.info("[MiniMessage Debug] $it") else null }
			preProcessor { original ->
				var string = original
				if (string.startsWith("\"") && string.endsWith("\""))
					string = string.substring(1, string.length - 1)
				for (charFormat in CharacterAndFormat.defaults()) {
					if (charFormat.character() == 'r') {
						string = string.replace("&r", "<reset>")
						continue
					}
					string = string.replace("&${charFormat.character()}", "<${charFormat.format()}>")
				}
				return@preProcessor string
			}
			editTags {
				it.tag("version", VERSION_TAG)
				it.resolver(CONFIG_TAG)
			}
		}.build()

}