---
title: LootForge — How to Use
---

# How to Use LootForge

LootForge is a D&D 5e treasure, item, and spell generator for DMs. It rolls on the 2014 or 2024 DMG tables and (optionally) tracks what you've handed out across a campaign.

## Choosing your rules edition

LootForge supports both **2014 (Classic)** and **2024 (New)** 5e rules. The two editions use different magic-item tables and different math for how much loot a party should see.

- **⋮ → 5e Rules Edition** to switch.
- The Tracker tab and the **Party Level** / **Theme** spinners only appear in 2024 mode.

## Treasure tab

Generates a treasure pile — coins, gems, art, and magic items.

1. Pick **Individual** (one monster's pocket change) or **Hoard** (the whole dragon's pile).
2. Set the **Challenge Level** of the encounter: `0-4`, `5-10`, `11-16`, or `17+`.
3. **2024 mode only:** pick your **Party Level** tier (1-4) and a **Theme** (Random, Arcana, Armaments, Implements, Relics).
4. Set **Times to Run** if you want several hoards at once. Each hoard is rendered in its own section with a divider, so you can hand them out separately or roll a session's worth in one click.
5. Tap **Generate Treasure**.

### What the result looks like

A dialog appears with the rolled loot:

- **Coins, gems, art** are listed at the top.
- **Items** appear as checkboxes — each one can be deselected if you'd rather not hand it to the party.
- Tap the **ⓘ** next to any magic item to look up its description (see [Item links](#item-links) below).

Two action buttons:

- **Copy and Dismiss** — copies the (filtered, checked-only) loot text to your clipboard.
- **Copy and Commit to Campaign** (2024 only) — same copy, plus the checked items are added to your Tracker.

## Items tab

Generates just magic items, no coins or gems. Use this when you want to drop a specific item between hoards.

The controls and result dialog are the same as Treasure — Tier + Theme in 2024 mode, Challenge Level in 2014 mode, and a count for how many items to roll.

## Spells tab

Generates a random spell. Useful when a Spell Scroll comes up in a hoard and you want to roll its content.

- Pick a **caster class** (Artificer, Bard, Cleric, Druid, Paladin, Ranger, Sorcerer, Warlock, Wizard) — or **Random**.
- Pick a **spell level** (0 = cantrip, 1-9 = leveled) — or **Random**.
- Tap **Generate Spell**.

If you pick an impossible combination (e.g. Paladin + Level 0), the dialog tells you the class has no spells at that level rather than rolling garbage.

## Tracker tab (2024 only)

Tracks magic items awarded vs the 2024 DMG's *Magic Items Awarded by Level* table.

- Each row is a **tier** (1-4); each column is a **rarity** (Common → Legendary).
- Each cell shows `current count / DMG target`.
- Counts over target appear in **red** as a soft warning. Nothing breaks; it's just a signal that you may be running a generous campaign.
- Tap any cell to manually edit the count.

### Managing campaigns

- **New** creates a fresh campaign.
- **Rename**, **Reset**, **Delete** do what they say.
- Switch the active campaign by tapping the campaign name in the toolbar.

### Awarded items history

**View Awarded Items** opens a per-item log. Two grouping modes:

- **By Session** (default) — items grouped under each session header. Empty sessions show "No items given" so you can see at a glance which game-nights were dry.
- **By Commit** — chronological, one group per "Copy and Commit" action.

Each item line shows its rarity, theme, and the session it was awarded in. Tap an item to strike it through (e.g. when the party sells or loses it). Tap **ⓘ** to look up its description.

## Campaigns and sessions

The toolbar shows the active campaign and current session:

> Campaign: My Campaign · **Session 7**

- Tap the underlined **Session 7** to manually edit the session number, or use the **−** / **+** buttons in the dialog.
- Tap anywhere else on the subtitle to switch campaigns.

### Auto-bump prompt

When you Copy-and-Commit more than 12 hours after the campaign's previous commit, the app prompts:

> Start a new session? It's been 3 days since this campaign's last loot. Bump from session 7 to session 8 before committing?

Accept to bump (new commits go into Session 8); decline to stay in Session 7. Each campaign tracks its own 12-hour clock independently.

## Item links {#item-links}

Every magic item in the result dialog and Awarded Items list has a small **ⓘ** icon. Tapping it opens a description on **dnd5e.wikidot.com**.

- **Blue ⓘ** — a direct wikidot page exists; you jump straight there.
- **Grey ⓘ** — wikidot doesn't have a dedicated page for this item yet (mostly 2024-DMG novelty items). The link still opens, but lands on wikidot's site search. If the item gets added to wikidot in the future, the link will start working without an app update.

## Removing ads

The free version shows a banner ad at the bottom of each tab.

- **⋮ → Remove Ads** is a one-time in-app purchase that hides the banner.
- **⋮ → Restore Purchase** brings back the entitlement after a reinstall or device switch.

---

Questions not covered here? See the **[FAQ](faq.html)**.

Found a bug or want a feature? **⋮ → Send feedback** opens an email.
