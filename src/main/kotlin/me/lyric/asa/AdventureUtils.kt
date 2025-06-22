package me.lyric.asa

import me.lyric.asa.ArmorStandPlugin.Companion.instance
import me.lyric.asa.ArmorStandPlugin.Companion.pluginLogger
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.serializer.legacy.CharacterAndFormat

object AdventureUtils {

	@Suppress("DEPRECATION") /* Paper Deprecation of 'description' (plugin meta) */
	val VERSION_TAG: Tag = Placeholder.unparsed("version", instance.description.version).tag()

	val MINI_MESSAGE: MiniMessage
		get() = MiniMessage.builder().apply {
			debug { pluginLogger.info("[MiniMessage Debug] $it") }
			preProcessor { original ->
				var string = original
				if (string.startsWith("\"") && string.endsWith("\""))
					string = string.substring(1, string.length - 1)
				for (charFormat in CharacterAndFormat.defaults()) {
					string = string.replace(charFormat.character().toString(), "<${charFormat.format()}>")
				}
				return@preProcessor string
			}
			editTags {
				it.tag("version", VERSION_TAG)
			}
		}.build()

}