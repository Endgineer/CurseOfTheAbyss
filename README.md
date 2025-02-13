# Curse of the Abyss

The sole purpose of this mod is to introduce the **Curse of the Abyss** from *Made in Abyss* into Minecraft, a phenomenon that is reminiscent of decompression sickness. If you ever feel like the deep underground isn't foreboding enough or that you want to experience what it feels like to deal with the Curse of the Abyss, then this mod is for you!

### The Abyss

<p align="center">
  <img src="https://github.com/Endgineer/CurseOfTheAbyss/blob/1.20.1/.vscode/abyss.png?raw=true" height="50%" width="50%">
</p>

The Abyss is an enormous vertical landscape divided into seven unique layers, defined to be the region of the Overworld known as the Deep Underground, whose mouth begins at `y = 0` and whose depth is unknown to this day. Over the span of many years, with a spirit of adventure for the unknown and countless legends luring them in, the world's only remaining unexplored chasm has swallowed up a great many people. To those who offer up their bodies and challenge the chasm for which even darkness is no match, the Abyss is said to provide all. Life and death, Curses and Blessings &mdash; all of it. At the end of their journey, what fate will they choose to meet?

### The Field

<p align="center">
  <img src="https://github.com/Endgineer/CurseOfTheAbyss/blob/1.20.1/.vscode/field.gif?raw=true" height="50%" width="50%">
</p>

The Abyss is permeated by a mysterious field, the very blood of the Abyss, that seems correlated with many of the eldritch phenomena surrounding the Abyss, most notably including the Curse of the Abyss, a set of afflictions that seem to grow more severe with depth. The concentration of this field as well as the afflictions that result from it seem to grow more severe with depth. Rumors say that the affictions do not seem to occur in delvers who are descending into the Abyss, only those who are attempting to ascend out of it. Despite this, prolonged exposure to the field seems to cause the minds and bodies of delvers to literally warp until they are beyond recognition.

<p align="center">
  <img src="https://github.com/Endgineer/CurseOfTheAbyss/blob/1.20.1/.vscode/strains.gif?raw=true">
</p>

The Strains of Ascension is the scientific name referring to the afflictions associated with the Curse of the Abyss. These strains, shown below along with their Minecraft manifestations, differ in each layer and become more severe with depth:
- 1st Layer: Dizziness and nausea (exhaustion of hunger and thirst)
- 2nd Layer: Numbness and headaches (throbbing blur effect)
- 3rd Layer: Hallucinations and vertigo (sanity loss)
- 4th Layer: Bleeding and pains (regenerable damage over time)
- 5th Layer: Loss of all senses (complete blindness and deafness)
- 6th Layer: Loss of humanity (unregenerable damage over time)
- 7th Layer: Certain and immediate death (instant death)

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

### Music Triggers

[Music Triggers](https://github.com/TheComputerizer/Music-Triggers) can be used to evoke feelings of awe, dread, and more!

### Traveler's Titles

[Traveler's Titles](https://github.com/YUNG-GANG/Travelers-Titles) can be used to evoke feelings of awe, dread, and more- or to simply indicate that the player has reached a certain layer of the Abyss.

# Design / Documentation / Configuration

This section discusses the mathematical models and rationale behind the field to aid users in understanding how to configure the mod to their needs. But before we get ahead of ourselves, let's derive some useful functions. Let $\mathcal{A}$ represent the Abyss span. The first function we can derive is the depth gradient function $\mathcal{D}(y)$, which represents how deep into the Abyss the delver is. From this function, we can derive the layer function $\mathcal{L}(y)$ which represents the layer that the delver is at as well as the boundary function $\mathcal{B}(l)$ which represents the deepest y-value of the given layer $l$.

$$\mathcal{D}(y) = \min(\max(0, \frac{-y}{\mathcal{A}}), 1)$$

$$\mathcal{L}(y) = \lceil 7 \cdot \mathcal{D}(y) \rceil$$

$$\mathcal{B}(l) = l \cdot \frac{\mathcal{A}}{7}$$

Now it's time to *dive* deep. Inspired by the $hydrostatic\ pressure$ responsible for decompression sickness, the Abyss field was modelled as a 6D pressure field that mimics the formula of $hydrostatic\ pressure$, as shown below. There are some noteworthy differences, namely that each of the constituents are scalar fields and the Abyss' gravity field is simply 1 everywhere.

$$P(\xi, x, y, z, t, \tau) = \rho(\xi, x, y, z, t, \tau) \cdot 1 \cdot h(y)$$

The $\rho(\xi, x, y, z, t, \tau)$ function is the field's density or the concentration of the field at a given 6D point. The $h(y)$ function is the field's column depth or how much field influence is above the player's head. In terms of variables, we have the world seed $\xi$, the spatial coordinates $x$, $y$ and $z$, the universal time $t$ which cannot be affected and counts the age of the world, and the astronomical time $\tau$ which is affected by the `/time` command. Let's first conceptually look at how the field causes the curse. The field itself can be simplified into a 3D scalar field; we simply take a world with seed $\xi = 0$ frozen at creation time $t = 0$ and astronomical time $\tau = 0$. Then, to help simplify the analysis, we effectively take a single 2D cross-section of the 3D scalar field by looking at a specific y-level, say $y = \frac{\mathcal{A}}{2}$. Finally, we generate a surface plot of this 2D scalar field, treating $P(x, z) = 0$ as valleys and $P(x, y) = 1$ as peaks. The resulting fabric will appear similar to the figure below.

<p align="center">
  <img src="https://github.com/Endgineer/CurseOfTheAbyss/blob/1.20.1/.vscode/fabric.jpg?raw=true" height="50%" width="50%">
</p>

We can imagine that the 3D field is thus many of these fabric sheets stacked on top of each other, that these fabric sheets are invisible to the naked eye and permeable, and that each of these fabric sheets undulates with time. A delver can freely move horizontally within these sheets without concern. When a delver descents through the sheets, each sheet the delver passes through is permanently bound to the delver's soul, without visibly reacting. The risk of being afflicted by the curse begins when the delver attempts to ascend. Each sheet below the delver that is bound to them begins to stretch upwards, as if trying to pull the delver back down. The delver does not feel anything, but they accumulate a **longing** for the deepest sheet bound to their soul. When the maximum longing $L$ is exceeded, every **per-tick** distance ascended induces a stress $\sigma$ of $\Delta y$ on the delver.

$$\sigma(y, y^-) = \min(\max(0, \lceil y - L \rceil), 1) \cdot \max(0, y - y^-)$$

This field-induced stress causes the delver's body to manifest the strains of the **current** layer. Before we look into analyzing the strains model, it is important to note that all strains of ascension occur gradually and exhibit a delayed onset. We call the function that models the strain sustained by the delver over time the delver's strain function $\mathcal{S}(t)$. Accumulated stress is dissipated into strain every second by simply summing a distributed version of the stress with the player's current strain function. The specific distribution function used to distribute the stress is the lognormal cumulative distribution function $L(t)$ defined as follows:

$$L(t) = \int_{-\infty}^{t}\frac{1}{20(11-\frac{k}{20})\sqrt{2\pi}}e^{-\frac{\ln(11-\frac{k}{20})^2}{2}}dk$$

As it's cumbersome to provide individual stress values when modelling, we will have to model the delver's stress $\sigma$ using a stress signal $\sigma(t)$ which represents the stress sustained by the player at every tick. With this, we arrive at what we call the distributed stress $\Sigma(t)$, which is simply the convolution of $L(t)$ with $\sigma(t)$.

$$\Sigma(t) = \int_{-\infty}^{\infty}\sigma(k)L(t-k)dk$$

The distributed stress is the life force of the strain. It determines how much strain is on the delver and when that strain will affect the delver. But how this distributed stress manifests into a specific strain depends on what is called the strain's characteristic function $\mathcal{C}$. The behavior of the characteristic function will differ depending on whether the strain is in the deforming or nondeforming category. Nondeforming strain, denoted by $s$, is strain that results in status effects. For all nondeforming strains, given their respective configured lower and upper bounds $[a_l, b_l]$ for each layer $l$:

$$\mathcal{C}(\xi, x, y, z, t, \tau) = P(\xi, x, y, z, t, \tau) \cdot (\frac{-y\ mod\ (-\mathcal{B}(1))}{-\mathcal{B}(1)} \cdot (b_{\mathcal{L}(y)} - a_{\mathcal{L}(y)}) + a_{\mathcal{L}(y)})$$

Deforming strain, denoted by $\epsilon$, is the infamous strain that occurs below the defiance layer $D$ and causes curse damage. The characteristic function for deforming strain follows the mechanics stress-strain curve closely. This means there will be two regions of deforming strain, the elastic deformation range which occurs at and above the yield layer $Y$ and plastic deformation which occurs below the yield layer. The characteristic function of deforming strain is shown below, given elasticity modulus $\delta$ and strain hardening index $n$.

$$\mathcal{C}(\xi, x, y, z, t, \tau) = \min(\max(0, \lfloor \mathcal{L(y)} - D \rfloor), 1) \cdot P(\xi, x, y, z, t, \tau) \cdot (\frac{\mathcal{B}(D)-y}{\delta \mathcal{A}} + (\frac{y-\mathcal{B}(D)}{\mathcal{B}(Y-D)})^\frac{1}{n})$$
