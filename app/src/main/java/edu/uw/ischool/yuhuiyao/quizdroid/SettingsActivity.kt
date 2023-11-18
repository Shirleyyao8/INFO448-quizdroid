package edu.uw.ischool.yuhuiyao.quizdroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import edu.uw.ischool.yuhuiyao.quizdroid.R
import android.util.Log
import android.widget.Toast


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager
            .beginTransaction()
            .replace(android.R.id.content, SettingsFragment())
            .commit()
    }


    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)

            // Retrieve the preferences
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

            // Set up the EditTextPreference for URL
            val urlPreference = findPreference<EditTextPreference>("pref_key_url")

            urlPreference?.text = sharedPreferences.getString("pref_key_url", "http://tednewardsandbox.site44.com/questions.json")

            urlPreference?.setOnPreferenceChangeListener { _, newValue ->
                // Save the new URL to SharedPreferences
                sharedPreferences.edit().putString("pref_key_url", newValue as String).apply()

                // Display a Toast with the current URL
                Toast.makeText(requireContext(), "Current URL: $newValue", Toast.LENGTH_SHORT).show()

                true
            }

            // Set up the EditTextPreference for interval
            val intervalPreference = findPreference<EditTextPreference>("pref_key_interval")
            intervalPreference?.text = sharedPreferences.getString("pref_key_interval", "60")
            intervalPreference?.setOnPreferenceChangeListener { _, newValue ->
                // Save the new interval to SharedPreferences
                sharedPreferences.edit().putString("pref_key_interval", newValue as String).apply()

                Toast.makeText(requireContext(), "Current interval: $newValue", Toast.LENGTH_SHORT).show()

                true
            }
        }
    }
}
