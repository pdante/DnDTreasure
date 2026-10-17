package com.verdantsoftware.lootforge.Tracker;

import android.app.AlertDialog;
import android.content.Context;

import com.verdantsoftware.lootforge.R;

/**
 * Centralizes the "campaign has been idle a while — start a new session?"
 * prompt that fires when the user kicks off a treasure or item generation.
 *
 * <p>Trigger contract:
 * <ul>
 *   <li>If {@link Campaign#getLastGeneratedAt()} == 0 (first generation
 *       ever for this campaign), no prompt — just stamp lastGeneratedAt
 *       and proceed.</li>
 *   <li>If elapsed since last generation is > 12 hours, show a
 *       non-cancellable dialog offering to bump the session number;
 *       the user can reject the bump and keep the current session.</li>
 *   <li>Either way, {@code lastGeneratedAt} is set to now and the
 *       campaign is saved, then {@code onResolved} runs.</li>
 * </ul>
 *
 * <p>Callers pass a {@link Runnable} that performs the actual generation
 * (so the bump can complete first if needed).
 */
public final class SessionBumpHelper {

    private static final long TWELVE_HOURS_MS = 12L * 60 * 60 * 1000;

    private SessionBumpHelper() {}

    public static void checkAndPromptBump(Context context,
                                          Campaign campaign,
                                          CampaignStore store,
                                          Runnable onResolved) {
        long now = System.currentTimeMillis();
        long last = campaign.getLastGeneratedAt();
        long elapsed = now - last;
        // Always stamp the new "last generated at" — they're generating now,
        // regardless of whether they bump the session.
        campaign.setLastGeneratedAt(now);

        if (last == 0 || elapsed <= TWELVE_HOURS_MS) {
            store.saveCampaign(campaign);
            onResolved.run();
            return;
        }

        int current = campaign.getCurrentSession();
        int proposed = current + 1;
        String elapsedStr = formatElapsed(elapsed);
        String msg = context.getString(R.string.session_bump_dialog_message,
                elapsedStr, current, proposed);
        new AlertDialog.Builder(context)
                .setTitle(R.string.session_bump_dialog_title)
                .setMessage(msg)
                .setPositiveButton(
                        context.getString(R.string.session_bump_yes, proposed),
                        (d, w) -> {
                            campaign.setCurrentSession(proposed);
                            store.saveCampaign(campaign);
                            onResolved.run();
                        })
                .setNegativeButton(
                        context.getString(R.string.session_bump_no, current),
                        (d, w) -> {
                            store.saveCampaign(campaign);
                            onResolved.run();
                        })
                .setCancelable(false)
                .show();
    }

    private static String formatElapsed(long ms) {
        long hours = ms / (60L * 60 * 1000);
        if (hours < 24) return hours + (hours == 1 ? " hour" : " hours");
        long days = hours / 24;
        return days + (days == 1 ? " day" : " days");
    }
}
