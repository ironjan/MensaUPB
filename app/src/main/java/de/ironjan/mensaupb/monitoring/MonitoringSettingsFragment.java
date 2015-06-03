package de.ironjan.mensaupb.monitoring;

import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.piwik.sdk.Piwik;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ironjan.mensaupb.MensaUpbApplication;
import de.ironjan.mensaupb.R;

/**
 * Actual content of {@link de.ironjan.mensaupb.activities.About}.
 */
@SuppressWarnings("WeakerAccess")
@EFragment(R.layout.fragment_monitoring_settings)
public class MonitoringSettingsFragment extends Fragment {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass().getSimpleName());

//    // out out
//    CheckBox optOut = (CheckBox) findViewById(R.id.optOutCheckbox);
//    optOut.setChecked(((PiwikApplication) getApplication()).getGlobalSettings().isOptOut());
//    optOut.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            ((PiwikApplication) getApplication()).getGlobalSettings().setAppOptOut(((CheckBox) v).isChecked());
//        }
//    });

    @ViewById
    SwitchCompat switchMonitoringEnabled;

    @AfterViews
    void fetchSettings(){
        boolean optOut = getGlobalSettings().isOptOut();
        boolean monitoringEnabled = !optOut;
        switchMonitoringEnabled.setChecked(monitoringEnabled);
    }

    private Piwik getGlobalSettings() {
        return ((MensaUpbApplication) getActivity().getApplication())
                .getGlobalSettings();
    }

    @CheckedChange(R.id.switchMonitoringEnabled)
    void optOutChange(){
        boolean monitoringEnabled = switchMonitoringEnabled.isChecked();
        boolean optOut = !monitoringEnabled;
        getGlobalSettings().setAppOptOut(optOut);
    }
}
