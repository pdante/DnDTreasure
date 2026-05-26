package com.verdantsoftware.lootforge.MagicItem2024;

import java.util.ArrayList;
import java.util.List;

public class MagicItem2024Table {

    private static class Entry {
        final int low;
        final int high;
        final String name;
        Entry(int low, int high, String name) {
            this.low = low;
            this.high = high;
            this.name = name;
        }
    }

    private final List<Entry> entries = new ArrayList<>();

    public MagicItem2024Table add(int low, int high, String name) {
        entries.add(new Entry(low, high, name));
        return this;
    }

    public String roll(int d100Roll) {
        int normalized = d100Roll == 0 ? 100 : d100Roll;
        for (Entry e : entries) {
            int high = e.high == 0 ? 100 : e.high;
            if (normalized >= e.low && normalized <= high) {
                return e.name;
            }
        }
        return entries.isEmpty() ? "" : entries.get(entries.size() - 1).name;
    }
}
