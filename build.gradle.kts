import gg.meza.stonecraft.mod

plugins {
    id("gg.meza.stonecraft")
}

if (mod.isForge) {
    loom {
        forge {
            mixinConfig("vineclipper.mixins.json")
        }
    }
}

publishMods {
    changelog = providers.environmentVariable("CHANGELOG")
        .orElse("See https://github.com/123FLO321/vineclipper/blob/main/CHANGELOG.md")
}
