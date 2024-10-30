package com.dicoding.submissionfundamental1.ui.setting

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.dicoding.submissionfundamental1.ViewModelFactory
import com.dicoding.submissionfundamental1.databinding.FragmentSettingBinding
import com.dicoding.submissionfundamental1.worker.EventWorker
import java.util.concurrent.TimeUnit

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private var periodicWorkRequest: PeriodicWorkRequest? = null
    private lateinit var workerManager: WorkManager
    private lateinit var settingViewModel: SettingViewModel
    private lateinit var switchReminder: SwitchCompat
    private lateinit var switchThemeDarkmode: SwitchCompat
    private lateinit var notificationPermissionLauncher: ActivityResultLauncher<String>



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSettingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         switchThemeDarkmode = binding.switchThemeDarkmode
         switchReminder = binding.switchReminder

        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        settingViewModel = ViewModelProvider(this, ViewModelFactory(null, pref))[SettingViewModel::class.java]
        workerManager = WorkManager.getInstance(requireContext())


        settingViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
            switchThemeDarkmode.isChecked = isDarkModeActive
            AppCompatDelegate.setDefaultNightMode(
                if (isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        settingViewModel.getReminderSetting().observe(viewLifecycleOwner) { isReminderActive: Boolean ->
            switchReminder.isChecked = isReminderActive
        }

        switchThemeDarkmode.setOnCheckedChangeListener { _, isChecked ->
            settingViewModel.saveThemeSetting(isChecked)

        }

        notificationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                startPeriodicTask()
            } else {
                Toast.makeText(requireContext(), "Notification permission denied", Toast.LENGTH_SHORT).show()
                switchReminder.isChecked = false
            }
        }


        switchReminder.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveReminderSetting(isChecked)
            if (isChecked) {
                requestNotificationPermission()
            } else {
                cancelPeriodicTask()
            }
        }



    }


    private fun startPeriodicTask() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        periodicWorkRequest = PeriodicWorkRequest.Builder(EventWorker::class.java, 1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        workerManager.enqueue(periodicWorkRequest!!)

    }

    private fun cancelPeriodicTask() {
        periodicWorkRequest?.let {
            workerManager.cancelWorkById(it.id)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        } else {
            // Izin sudah diberikan, langsung mulai tugas jika reminder aktif
            if (switchReminder.isChecked && !switchThemeDarkmode.isChecked) {
                startPeriodicTask()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
    }

    override fun onPause() {
        super.onPause()
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }
}