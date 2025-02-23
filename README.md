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

$$P(\xi, x, y, z, t, \tau) = \rho(\xi, x, y, z, t, \tau) \cdot 1 \cdot \phi(y)$$

The $\rho(\xi, x, y, z, t, \tau)$ function is the field's density or the concentration of the field at a given 6D point. The $\phi(y)$ function is the field's column depth or how much field influence is above the player's head. In terms of variables, we have the world seed $\xi$, the spatial coordinates $x$, $y$ and $z$, the universal time $t$ which cannot be affected and counts the age of the world, and the astronomical time $\tau$ which is affected by the `/time` command. Let's first conceptually look at how the field causes the curse. The field itself can be simplified into a 3D scalar field; we simply take a world with seed $\xi = 0$ frozen at creation time $t = 0$ and astronomical time $\tau = 0$. Then, to help simplify the analysis, we effectively take a single 2D cross-section of the 3D scalar field by looking at a specific y-level, say $y = \frac{\mathcal{A}}{2}$. Finally, we generate a surface plot of this 2D scalar field, treating $P(x, z) = 0$ as valleys and $P(x, y) = 1$ as peaks. The resulting fabric will appear similar to the figure below.

<p align="center">
  <img src="https://github.com/Endgineer/CurseOfTheAbyss/blob/1.20.1/.vscode/fabric.jpg?raw=true" height="50%" width="50%">
</p>

We can imagine that the 3D field is thus many of these fabric sheets stacked on top of each other, that these fabric sheets are invisible to the naked eye and permeable, and that each of these fabric sheets undulates with time. A delver can freely move horizontally within these sheets without concern. When a delver descents through the sheets, each sheet the delver passes through is permanently bound to the delver's soul, without visibly reacting. The risk of being afflicted by the curse begins when the delver attempts to ascend. Each sheet below the delver that is bound to them begins to stretch upwards, as if trying to pull the delver back down. The delver does not feel anything, but they accumulate a **longing** for the deepest sheet bound to their soul. When the maximum longing $L$ is exceeded, every **per-tick** distance ascended induces a stress $\sigma$ of $\Delta y$ on the delver.

$$\sigma(y, y^-) = \min(\max(0, \lceil y - L \rceil), 1) \cdot \max(0, y - y^-)$$

This field-induced stress dissipates onto the delver's body in the form of strain, which occurs gradually and typically with a delayed onset. Mathematically speaking, each stress sustained during a tick is an impulse. This impulse is distributed into **per-second** strain using a strain kernel $K(t)$. If we generalize the delver's stress history into a stress signal $\sigma(t)$, we arrive at the general strain $\Sigma(t)$, which is simply the convolution of $\sigma(t)$ with $K(t)$.

$$\Sigma(t) = \int_{-\infty}^{\infty}\sigma(m)K(t-m)dm$$

The strain kernel is defined by the user in the mod's configuration file, depending on how they view the strain distribution to work, with the only conditions being the following:

$$\int_{0}^{\infty}K(t)dt = 1$$

$$K(t) \geq 0\ for\ t\geq0$$

The general strain manifests as the different strains of ascension, depending on the **current layer** that the delver is attempting to defy. The specific strain $S_L(t)$ pertaining to a layer $L$ is obtained by convolving the general strain $\Sigma(t)$ with the strain filter $F_L(t)$ of the specific strain.

$$S_L(t) = \int_{-\infty}^{\infty}\Sigma(m)F_L(t-m)dm$$

The strain filter is also defined by the user in the mod's configuration file, except that there are no conditions here and the user can vary the behavior of the strain filter with variables such as the delver's current depth and the field's strength at the delver's location. The specific strain $S_L(t)$ models the actual strains of the $Lth$ layer sustained by the delver per-second given their current state. These models for strain should be used to guide experimentation, since they suffice to simulate and understand how strain works ideally, not how the game actually calculates it. In reality, the calculations will involve an element of stochasticity due to translations made by the game and numerical errors due to approximations. With strain demystified, let's finally go back to the field's constituent functions, starting with the field density function:

$$\rho(\xi, x, y, z, t, \tau) = \min(\mathcal{D}(y) + \Xi(\xi, x, y, z, t, \tau) \cdot \frac{1-\mathcal{D}(y)}{1+\frac{63(1-\Psi(\tau))}{64}}, 1)$$

It might look scary, but it's not. The outer-most function ensures the field saturates at 1. The $\Xi(\xi, x, y, z, t, \tau)$ function is the multioctave noise function, defined below, where $\mathcal{O}_{3D}(\xi, x, y, z)$ is the OpenSimplex 3D noise function. The multioctave noise function is at the core of the field, responsible for the field's distinctive shape. We divide this function by a term which utilizes the moon presence function $\Psi(\tau)$, ensuring that the field exhibits its hottest nature during midnight on a full moon and its coldest nature during noon on a new moon. Finally, the $\mathcal{D}(y)$ and $1-\mathcal{D}(y)$ portions superimpose the field onto a minimum background gradient, ensuring that holes in the field are covered.

$$\Xi(\xi, x, y, z, t, \tau) = \sum_{\Omega\ =\ 0}^{6}\frac{|\mathcal{O}_{3D}(\xi, \frac{x}{T_{xz}2^\Omega}, \frac{y}{T_y2^\Omega} - \frac{t}{T_t}, \frac{z}{T_{xz}2^\Omega})|}{2^{6-\Omega}}$$

$$\Psi(\tau) = 2 |\frac{\tau - 114000}{192000} - \lfloor \frac{\tau - 18000}{192000} \rfloor| \cdot \max(0, \frac{48}{11} \cdot |\frac{\tau - 6000}{24000} - \lfloor \frac{\tau + 6000}{24000} \rfloor| - \frac{13}{11})$$

One important detail is the entanglement of time with y-value. The primary reason for this decision is to avoid using 4D noise, which seems prone to artifacts. But things work out, since the passage of time surrounding the Abyss seems heavily correlated with the depth that a delver is at. It also makes the field look like it's flowing out of the Abyss, which is exactly what we see in *Made in Abyss*. This leaves only the column depth function $\phi(y)$, which is defined below. It is essentially a sigmoid activation function that attempts to mimic the apparent field influence seen in Made in Abyss.

$$\phi(y) = \frac{1}{1 + e^{\frac{\ln 99}{\frac{512}{\mathcal{A}} + \frac{64}{7}} \cdot (\frac{64y}{\mathcal{A}}+\frac{64}{7})}}$$
