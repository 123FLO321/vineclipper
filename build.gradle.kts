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

// Fabric API's 1.16.x branch has two release-artifact quirks that break Stonecraft's
// generic Fabric dependency wiring:
// 1. It never published a fabric-gametest-api-v1 module (Minecraft's GameTest framework
//    wasn't wrapped by Fabric API until the 1.17 line), so Stonecraft's unconditional
//    modApi dependency on that module is unresolvable.
// 2. Every 1.16.x fabric-api release's POM pins a stray transitive compile dependency on
//    net.fabricmc:fabric-loader:0.10.5+build.213 (an artifact of how that branch was last
//    published). Loom treats it as a mod dependency and puts it on the runtime classpath,
//    where it collides with our declared loader_version at launch
//    ("duplicate fabric loader classes found on classpath").
// Both must be excluded for 1.16.5-fabric to resolve/run at all.
if (mod.isFabric && stonecutter.current.version == "1.16.5") {
    configurations.named("modApi") {
        exclude(group = "net.fabricmc.fabric-api", module = "fabric-gametest-api-v1")
        exclude(group = "net.fabricmc", module = "fabric-loader")
    }
}

publishMods {
    changelog = providers.environmentVariable("CHANGELOG")
        .orElse("See https://github.com/123FLO321/vineclipper/blob/main/CHANGELOG.md")
}
