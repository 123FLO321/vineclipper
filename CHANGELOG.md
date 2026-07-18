# Changelog

All notable changes to VineClipper are documented here.
Format: [Keep a Changelog](https://keepachangelog.com/), versions match git tags.

## [Unreleased]

### Fixed
- Clipping an already-clipped vine no longer plays a sound or costs durability, matching vanilla shearing of fully grown kelp and cave vines (#2).
- Shears no longer lose durability in creative mode (#2).
- Shears now break properly at zero durability instead of going negative; Unbreaking and Mending now behave as on vanilla tools (#2).

### Changed
- Clipping plays the vanilla plant-snip sound on 1.18.2+ (the sheep-shear sound remains on 1.16.5/1.17.1, which predate it).
- Shears held in the offhand can now clip vines, as in vanilla.
- Clipping now fires the `item_used_on_block` advancement trigger and, on 1.19.4+, a `BLOCK_CHANGE` game event (sculk sensors react).

## [1.1.0] - 2026-07-18

### Added
- Support for all Architectury-supported Minecraft version groups, 1.16.5 through 26.2, on Fabric, Forge (≤1.20.4), and NeoForge (≥1.20.6).

### Changed
- VineClipper no longer requires the Architectury API to be installed.

## [1.0.1] - 2023

### Fixed
- Better support for modded vine blocks.

## [1.0.0] - 2023

### Added
- Initial release: use shears on a vine to stop it from growing.
