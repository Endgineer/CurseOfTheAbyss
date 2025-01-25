# Curse of the Abyss

The sole purpose of this mod is to introduce the **Curse of the Abyss** from *Made in Abyss* into Minecraft, a phenomenon that is reminiscent of decompression sickness. If you ever feel like the deep underground isn't foreboding enough or that you want to experience what it feels like to deal with the Curse of the Abyss, then this mod is for you! If you would like to learn more about the curse and do not mind minor **SPOILERS** from *Made in Abyss*, see the sections below.

<details>
  <summary>THE ABYSS</summary>

  <p align="center">
    <img src="https://github.com/Endgineer/CurseOfTheAbyss/blob/main/.vscode/Axy.png?raw=true" height="50%" width="50%">
  </p>
  
  The <b>Abyss</b> is a "gargantuan vertical landscape divided into seven unique layers". In Minecraft, this ancient labyrinth is defined to be the region of the Overworld known as the Deep Underground, whose mouth begins at `y = 0` and whose depth is unknown to this day. This mysterious formation mesmerized people. Valuable and dangerous primeval creatures and mysterious relics that are beyond comprehension beckoned adventurers looking to strike it rich. Over the span of many years, with a spirit of adventure for the unknown and countless legends luring them in, the world's only remaining unexplored chasm has swallowed up a great many people.
  > To those who offer up their bodies and challenge the chasm for which even darkness is no match, the Abyss is said to provide all. Life and death, Curses and Blessings &mdash; all of it. At the end of their journey, what fate will they choose to meet? <div align="right"> &mdash; Adage of the White Whistles </div>
  
  The Abyss is associated with feelings of loneliness, peril, and finality. To venture into the Abyss is to contend with sacrifice, making difficult isolating choices and dealing with the unlikelihood of messages making it to the surface. This is a place teeming with danger, beckoning delvers to be consumed by the warm darkness. This is a journey from which one is unlikely to return.
</details>

<details>
  <summary>THE FIELD</summary>

  <p align="center">
    <img src="https://github.com/Endgineer/CurseOfTheAbyss/blob/main/.vscode/Fxy.gif?raw=true" height="100%" width="100%">
  </p>
  
  The Abyss is permeated by a mysterious **Field**, described as the "very blood of the Abyss", that seems responsible for or correlated with many of the eldritch phenomena surrounding the Abyss. Though this Field is everyflowing, everchanging, and everpresent, its concentration and effectiveness seems to be stronger with depth. The Field seems to be responsible for causing the Curse of the Abyss, a set of afflictions that seem to grow more severe with depth. The Curse of the Abyss does not seem to affect delvers who are descending into the Abyss, but those who are attempting to ascend out of it. In a sense, the Field can be visualized as a stack of thin sheets layered on top of each other. When descending, a delver passes safely through the sheets. When ascending, the sheets stretch around the delver. After a certain distance ascended, when a certain maximum number of sheets are stretched around the delver, further ascension pierces the old bottom-most sheet as the new top-most sheet is stretched around the delver, causing the delver to be hit by the afflictions. These afflictions, known as the Strains of Ascension, imply that the deeper one delves into the Abyss, the more difficult it will be to ever return. But in addition to these afflictions, delvers suffer passive afflictions from spending time within the Abyss, as their minds and bodies are literally warped by it, eventually being consumed.
</details>

<details>
  <summary>THE STRAINS</summary>

  <p align="center">
    <img src="https://github.com/Endgineer/CurseOfTheAbyss/blob/main/.vscode/Sxy.gif?raw=true">
  </p>
  
  The **Strains** of Ascension is the scientific name referring to the afflictions associated with the Curse of the Abyss. These Strains, shown below along with their Minecraft equivalents, differ in each layer and become more severe with depth:
  - 1st Layer: Dizziness and nausea (exhaustion of hunger and thirst)
  - 2nd Layer: Numbness and headaches (throbbing blur effect)
  - 3rd Layer: Hallucinations and vertigo (sanity loss)
  - 4th Layer: Bleeding and pains (regenerable damage over time)
  - 5th Layer: Loss of all senses (complete blindness and deafness)
  - 6th Layer: Loss of humanity (unregenerable damage over time)
  - 7th Layer: Certain and immediate death (instant death)
  
  At the bottom of the 5th Layer is what is known as the Absolute Boundary of the Abyss, beyond which lies the 6th Layer- the Capital of the Unreturned. Delvers descending beyond the Absolute Boundary are said to have made their "last dive" and considered lost forever, as ascending from within the 6th Layer results in the loss of one's humanity.
</details>

# Recommendations / Integration / Configuration

This section discusses recommendations, integration, as well as configuration. The focus of this mod is solely to introduce the Field and the phenomena associated with it. Making the world's depths or the experience itself more Abyss-like (e.g. adding cave biomes, changing cave generation, adding mobs, scaling difficulty with depth, enhancing atmosphere) should be handled by other mods. This section hopefully provides ideas and examples on how this can be done.

### World Depth

Let's address the modification of the world's depth, as scale is arguably an essential striking feature of the Abyss. To do this, we must provide some sort of datapack. If we are not using a data-based terrain generation mod like Terralith, we must base our datapack off of vanilla Minecraft. Otherwise, we can just modify specific data files inside the Terralith jar file. Let's start with the vanilla case; the specific fields in the files that need to be modified to change the world depth are the following:

```
data/minecraft/dimension_type/overworld.json -> min_y, height
data/minecraft/worldgen/noise_settings/overworld.json -> min_y, height
```

The field `min_y` denotes the lowest point in the world; this should be a value between -64 (default for Minecraft's overworld) and -2016 (maximum supported by this mod) that is a multiple of 16. The field `height = 320 - min_y` should be calculated as shown, if you'd like to keep the default vanilla overworld height limit of 320. Keep in mind that the deeper/higher the world is, the more memory is required and the longer it'll take to generate/load/save chunks. What about for data-based mods like Terralith? This will depend on the mod and you're going to have to do your own testing. Open the mod jar as a zip file, look into every data file, change the `min_y` and `height` fields that affect the overworld dimension. For example, for Terralith, you modify the following:

```
data/minecraft/dimension_type/overworld.json -> min_y, height
data/minecraft/worldgen/noise_settings/copy.json -> min_y, height
data/minecraft/worldgen/noise_settings/no.json -> min_y, height
data/minecraft/worldgen/noise_settings/overworld.json -> min_y, height
data/minecraft/worldgen/overworld.json -> min_y, height
resources/terralith_default/data/minecraft/worldgen/noise_settings/overworld.json -> min_y, height
```

Finally, after setting your desired depth, **remember to configure the curseoftheabyss-common.toml config file** to ensure the Abyss span corresponds to the absolute value of the `min_y` you set.

### Sanity: Descent Into Madness

This mod adds integration with [Sanity: Descent Into Madness](https://github.com/croissantnova/SanityDescentIntoMadness), a (configurable) mod which is highly recommended to get the full experience. The player's sanity is affected in two ways in the Abyss. The Curse of the Abyss is known to permanently deform delvers both physically and mentally, especially over long periods of time. For the mental aspect, when spending time within the Abyss, the player accumulates `derangement`, which causes the player's max sanity to be permanently reduced until their next death. But permanent derangement isn't the only effect on a player's sanity. The strains of ascension in the deeper layers of the Abyss also cause active sanity loss; yet unlike derangement, said loss can be replenished as per the usual mechanics of Sanity: Descent Into Madness.

### Curios

This mod adds integration with [Curios](https://github.com/TheIllusiveC4/Curios), an overall great mod which many other mods add integration for. In this context, its installation allows the Star Compass to be equipped as a charm, allowing the player to see the Field indicator at all times on their overlay.

### Thirst Was Taken

This mod adds integration with [Thirst Was Taken](https://github.com/ghen-git/Thirst-Mod), my personal favorite thirst mod. Strains of ascension associated with vomiting will cause exhaustion on thirst (in addition to hunger) if this mod is installed.

### Scaled

Though [Scaled](https://github.com/Lyof429/Scaled) does not interact with this mod, it is still recommended for those that seek increasing difficulty with depth.